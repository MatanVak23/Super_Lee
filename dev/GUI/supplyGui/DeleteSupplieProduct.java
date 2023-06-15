package GUI.supplyGui;

import Supplier_Module.Business.Supplier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Pattern;

public class DeleteSupplieProduct extends JPanel {
    private EditSupplierAgreemantGui parent;
    private JPanel mainPanel;
    private Supplier supplier;
    public DeleteSupplieProduct(EditSupplierAgreemantGui editSupplierAgreemantGui, Supplier supplier1){
        this.parent = editSupplierAgreemantGui;
        this.supplier =supplier1;

        setLayout(new BorderLayout());

        // Create main panel
        Image background = null;
        try {
            background = ImageIO.read(getClass().getResource("/GUI/pictures/background.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Image finalBackground = background;
        mainPanel = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(finalBackground, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0; // Column index
        titleConstraints.gridy = 0; // Row index
        titleConstraints.anchor = GridBagConstraints.NORTH; // Align in the top vertically
        titleConstraints.insets = new Insets(10, 0, 10, 0); // Add some top and bottom margin


        JLabel titleLabel = new JLabel("Delete Supplier Product");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        centerPanel.add(titleLabel, titleConstraints);

        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel enterNumberLabel = new JLabel("Please enter the supplier Product name you want to delete:");
        JTextField numberField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        deletePanel.add(enterNumberLabel);
        deletePanel.add(numberField);
        deletePanel.add(submitButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.5; // Place components vertically in the middle
        gbc.anchor = GridBagConstraints.NORTH; // Center-align components
        centerPanel.add(deletePanel, gbc);

        // Add action listener for the submit button
        submitButton.addActionListener(e -> {
            String product_name = numberField.getText();
            // Perform delete action based on the supplier number
            Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
            if(!pattern.matcher(product_name).matches()){
                JOptionPane.showMessageDialog(null, "invalid input!", "ERROR",  JOptionPane.ERROR_MESSAGE);
            }
            else if(product_name.equals( " " )){//check if the supplier has this product
                JOptionPane.showMessageDialog(null, "there is no "+ product_name + " that "+ this.supplier.getCard().getSupplier_name() + "can supply", "ERROR",  JOptionPane.ERROR_MESSAGE);
            }
            else if(product_name.equals("this product is in period order")){
                JOptionPane.showMessageDialog(null, "this supplier has period order!", "ERROR",  JOptionPane.ERROR_MESSAGE);
            }
            else {
                centerPanel.remove(deletePanel);
                GridBagConstraints gbcDetails = new GridBagConstraints();
                gbcDetails.gridx = 0;
                gbcDetails.gridy = 1;
                gbcDetails.weighty = 0.5; // Place component vertically in the middle
                gbcDetails.anchor = GridBagConstraints.CENTER; // Center-align component
                JLabel supplierDetails = new JLabel("product details");
                centerPanel.add(supplierDetails, gbcDetails);;

                // Example: Print a message on the panel
                JTextArea messageArea = new JTextArea();
                messageArea.setEditable(false);
                messageArea.append("Are you sure you want to delete  " + product_name + "?\n");
                JButton yesButton = new JButton("Yes");
                JButton noButton = new JButton("No");

                JPanel confirmationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                confirmationPanel.add(messageArea);
                confirmationPanel.add(yesButton);
                confirmationPanel.add(noButton);

                GridBagConstraints gbcConfirmation = new GridBagConstraints();
                gbcConfirmation.gridx = 0;
                gbcConfirmation.gridy = 2;
                gbcConfirmation.weighty = 0.5; // Place component at the bottom
                gbcConfirmation.anchor = GridBagConstraints.PAGE_END; // Align component to the bottom
                gbcConfirmation.insets = new Insets(10, 0, 10, 0); // Add some spacing
                centerPanel.add(confirmationPanel, gbcConfirmation);

                // Add action listener for the yes button
                yesButton.addActionListener(yesEvent -> {
                    // Perform delete confirmation action
                    JOptionPane.showMessageDialog(null, "product " + product_name + " has been deleted.");
                });

                // Add action listener for the no button
                noButton.addActionListener(noEvent -> {
                    // Perform cancel action
                    JOptionPane.showMessageDialog(null, "Deletion canceled.");
                });

                // Refresh the panel to show the confirmation panel
                revalidate();
                repaint();
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton);

//        mainPanel.add(bottomPanel, BorderLayout.WEST);
//        add(mainPanel,BorderLayout.CENTER);
        mainPanel.add(centerPanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.showDefaultPanelFromChild();
            }
        });
    }

}
