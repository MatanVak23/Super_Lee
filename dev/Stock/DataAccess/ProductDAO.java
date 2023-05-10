package Stock.DataAccess;

import Resource.Connect;
import Stock.Business.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProductDAO {
    private static ProductDAO instance = new ProductDAO();
    private static Map<String, Product> productMap;
    private static Connection connection;

    private ProductDAO(){
        productMap = new HashMap<>();
        connection = Connect.getConnection();

    }

    public static ProductDAO getInstance() {
        return instance;
    }
    public static Product getProduct(String catalogNumber){
        if(productMap.get(catalogNumber) == null){
            //read from the database
            lookForProduct(catalogNumber);
        }
        return productMap.get(catalogNumber);
    }
    private static void lookForProduct(String catalogNumber){
        int qr,storageQuantity,storeQuantity,minimumQuantity;
        String catalogNum, manufacturer,category,DamagedReason,storeLocation,storageLocation ;
        // store/storage location are like this :    "1 2"

        double weight, value,discount;
        Date expDate = new Date();

        try{
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery("SELECT * FROM Damaged WHERE catalog_number ==" + "'" + catalogNumber + "'");
            while(resultSet.next()){
                storageQuantity = resultSet.getInt("storageQuantity");
                storeQuantity = resultSet.getInt("storeQuantity");
                minimumQuantity = resultSet.getInt("MinimumQuantity");
                catalogNum = resultSet.getString("catalog_number");
                manufacturer = resultSet.getString("Manufacturer");
                category = resultSet.getString("Category");
                weight = resultSet.getDouble("Weight");
                value = resultSet.getDouble("Value");
                discount = resultSet.getDouble("ProductDiscount");
                storeLocation = resultSet.getString("StoreLocation"); // the name of the storeLocation field
                storageLocation = resultSet.getString("StorageLocation");// the name of the storageLocation field
                ExpDateDAO expDateDAO = ExpDateDAO.getInstance();
                HashMap<Integer,Date> exp = ExpDateDAO.readExpForCatalogNumber(catalogNumber);
                CategoryDAO categoryDAO = CategoryDAO.getInstance();
                AProductCategory productCategory = CategoryDAO.getCategory(category);
                DamagedProductDAO damagedProductDAO = DamagedProductDAO.getInstance();
                HashMap<Integer,String> damagedBarcodes = DamagedProductDAO.readDamagedForCatalogNumber(catalogNumber);
                if(productCategory == null){
                    System.out.println("problem with the category");
                }

                String productName = UniqueStringGenerator.convertBackToString(catalogNum);
                String[] words = productName.split(" "); // Split the string on whitespace

                String unit;
                String amount;
                StringBuilder productKind = new StringBuilder();
                AProductSubCategory aProductSubSubCategory;
                AProductCategory aProductSubCategory;

                if (words.length >= 2) {
                    unit = words[words.length - 1];
                    amount = words[words.length - 2];
                    aProductSubSubCategory = new AProductSubCategory(Double.parseDouble(amount), unit);
                    for(int i = 0 ; i < words.length -2; i ++){
                        productKind.append(words[i]);
                    }
                    aProductSubCategory = new AProductCategory(productKind.toString());
                }
                else {
                    System.out.println("problem with subcategory");
                    return;
                }
                String[] storeLocArr = storeLocation.split(" "),storageLocArr = storageLocation.split(" ");
                Location storeLoc,storageLoc;
                storeLoc = new Location(Integer.parseInt(storeLocArr[0]),Integer.parseInt(storeLocArr[1]));
                storageLoc = new Location(Integer.parseInt(storageLocArr[0]),Integer.parseInt(storageLocArr[1]));

                Product product = new Product(productCategory,aProductSubCategory,aProductSubSubCategory,storageLoc,storeLoc,manufacturer,storageQuantity+storageQuantity,minimumQuantity,weight,expDate);
                product.setExpirationDates(exp);
                product.setDamagedProducts(damagedBarcodes);
                product.setDiscount(discount);
                product.setValue(value);
                productMap.put(catalogNumber,product);

       }

        }catch (SQLException e){
            System.out.println("there is a problem with the database");
        }

    }
}
