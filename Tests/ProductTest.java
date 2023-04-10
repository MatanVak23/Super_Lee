import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    AProductCategory category = new AProductCategory("Milk Products");
    AProductCategory subCategory = new AProductCategory("Milk 3%");
    AProductSubCategory subSubCategory = new AProductSubCategory(1,"Litter") ;
    Location storeLocation = new Location(3,3);
    Location storageLocation = new Location(2,2);
    Date expDate = new Date(2023, Calendar.DECEMBER,12);

    Product product = new Product(category,subCategory,subSubCategory,storageLocation,storeLocation,"Yotvata",40,1,1.2,expDate);



    @Test
    void getName() {
        assertEquals(product.getName(),"Milk 3% 1.0 Litter");
    }

    @Test
    void getCategory() {
        assertEquals(product.getCategory(),category);
    }

    @Test
    void getSubCategoryName() {
        assertEquals(product.getSubCategoryName(),subCategory);
    }

    @Test
    void getSubSubCategory() {
        assertEquals(product.getSubSubCategory(),subSubCategory);
    }

    @Test
    void getStoreLocation() {
        assertEquals(product.getStoreLocation(),storeLocation);
    }

    @Test
    void setStoreLocation() {
        Location newLocation = new Location(11,11);
        product.setStoreLocation(newLocation);
        assertNotEquals(product.getStoreLocation().getLocation()[0],storeLocation.getLocation()[0]);
        assertNotEquals(product.getStoreLocation().getLocation()[1],storeLocation.getLocation()[1]);

        assertEquals(product.getStoreLocation().getLocation()[0],newLocation.getLocation()[0]);
        assertEquals(product.getStoreLocation().getLocation()[1],newLocation.getLocation()[1]);

    }

    @Test
    void getStorageLocation() {
        assertEquals(product.getStorageLocation(),storageLocation);
    }

    @Test
    void setStorageLocation() {
        Location newStorageLocation = new Location(1,1);
        product.setStorageLocation(newStorageLocation);
        assertNotEquals(product.getStorageLocation(),storageLocation);
        assertEquals(product.getStorageLocation(),newStorageLocation);


    }

    @Test
    void getManufacturer() {
        assertEquals(product.getManufacturer(),"Yotvata");
    }

    @Test
    void setManufacturer() {
        product.setManufacturer("Tnuva");
        assertNotEquals(product.getManufacturer(),"Yotvata");
        assertEquals(product.getManufacturer(), "Tnuva");
    }




    @Test
    void addToStorage() {
        product.addToStorage(30);
        assertNotEquals(product.getStorageQuantity() + product.getStoreQuantity(),40);
        assertEquals(product.getStorageQuantity(),40);
        assertEquals(product.getStorageQuantity() + product.getStoreQuantity(),70);
    }


    @Test
    void setMinimumQuantity() {

        assertEquals(product.getMinimumQuantity(),1);
        product.setMinimumQuantity(5);
        assertNotEquals(product.getMinimumQuantity(),10);
        assertEquals(product.getMinimumQuantity(),5);

    }


    @Test
    void setWeight() {
        assertEquals(product.getWeight(),1.2);
        product.setWeight(1.5);
        assertNotEquals(product.getWeight(),1.2);
        assertEquals(product.getWeight(),1.5);
    }



    @Test
    void setDiscount() {
        assertEquals(product.getDiscount(),0);
        product.setDiscount(50);
        assertNotEquals(product.getDiscount(),0);
        assertEquals(product.getDiscount(),50);
        assertEquals(product.getExpiration(1),expDate);
        assertEquals(product.getExpirationDates().get(1),expDate);

        assertFalse(product.getUniqueProduct(1111));
        assertTrue(product.getUniqueProduct(1));
        assertTrue(product.getUniqueProduct(40));
        product.addMoreItemsToProduct(30,new Date(2024, Calendar.MAY,20));
        assertTrue(product.getUniqueProduct(50));       // added 30 to storage
        assertFalse(product.getUniqueProduct(71));
    }


    @Test
    void setPrice() {
        product.setPrice(5.5);
        assertEquals(product.getPrice(),5.5);
    }

    @Test
    void getExpiration() {
        //assertEquals(product.getExpiration(1),expDate);
    }


    @Test
    void getExpirationDates() {
       //  assertEquals(product.getExpirationDates().get(1),expDate);
   }



    @Test
    void getUniqueProduct() {

//        assertFalse(product.getUniqueProduct(1111));
//        assertTrue(product.getUniqueProduct(1));
//        assertTrue(product.getUniqueProduct(40));
//        assertTrue(product.getUniqueProduct(50));       // added 30 to storage
//        assertFalse(product.getUniqueProduct(71));

    }


    @Test
    void markAsDamaged() {
        product.markAsDamaged(23,"not good milk");
        String oldmap = product.getDamagedProducts().toString();

        product.markAsDamaged(32, "another not good milk");
        String newMap = product.getDamagedProducts().toString();

        assertNotEquals(oldmap.toString(),newMap.toString());


    }
}