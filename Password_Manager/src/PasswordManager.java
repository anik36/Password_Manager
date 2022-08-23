/*
  Project for JAVA DSA: 'Password Manager'
       Features:
            1)Strong and secure Password Generator
            2)Advanced Password Encryption(hashing)
            3)Password Storing
            4)Password Searching
            5)Password Deletion
  @Developer (Aniket Shinde)
  @version (1.0)
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordManager implements ActionListener {
    //Store password class reference
    HashTablePassword data = new HashTablePassword(15,0.5F,0);

    JFrame frame;
    JFrame frame2;
    Container container1, container2;
    JLabel lAccount, lPassword;
    JTextArea encryptPasswordArea, generatePasswordArea, searchPasswordArea;
    JButton PasswordGenerateButton, PasswordEncryptButton, PasswordStoreButton, PasswordSearchButton, AccountAddButton, PasswordDeleteButton;
    JTextField tAccount, tPassword;

    @Override
    public void actionPerformed(ActionEvent e) { }

    //Frame Settings
    public static void frameGUI(JFrame frame){
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
    }

    //Container Settings
    public static void containerGUI(Container container){
        container.setVisible(true);
        container.setBackground(Color.decode("#262937"));
        container.setLayout(null);
    }

    //Buttons Settings
    public static void buttonsGUI(JButton button){
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
        Font font = new Font("Arial", Font.PLAIN, 15);
        button.setFont(font);
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        button.setCursor(cursor);
    }

    //GUI of Store Password Module
    public void StorePasswordGUI(){
        frame2 = new JFrame("Store your password");
        frame2.setBounds(1400,700,600,500);
        frame2.setSize(500,400);
        frameGUI(frame2);
        container2=frame2.getContentPane();
        containerGUI(container2);
        Font font = new Font("Arial", Font.BOLD, 20);

        //Account textField and label
        lAccount = new JLabel("Enter Account Name");
        lAccount.setForeground(Color.decode("#B7CDE2"));
        lAccount.setBounds(100,23,480,50);
        lAccount.setFont(font);
        container2.add(lAccount);

        tAccount = new JTextField();
        tAccount.setBounds(100,70,300,80);
        tAccount.setFont(font);
        tAccount.setForeground(Color.DARK_GRAY);
        container2.add(tAccount);

        //Account password textField and Label
        lPassword = new JLabel("Enter Account Password");
        lPassword.setForeground(Color.decode("#B7CDE2"));
        lPassword.setBounds(100, 160, 480, 50);
        lPassword.setFont(font);
        container2.add(lPassword);

        tPassword = new JTextField();
        tPassword.setBounds(100,200,300,80);
        tPassword.setFont(font);
        tPassword.setForeground(Color.DARK_GRAY);
        container2.add(tPassword);

        AccountAddButton = new JButton("Store");
        AccountAddButton.setBounds(170, 290, 150, 50);
        container2.add(AccountAddButton);
        buttonsGUI(AccountAddButton);
    }

    //For Password generator and encryption
    public void textArea(String password, JTextArea textArea){
        textArea.setText(password);
        Font font = new Font("Arial", Font.BOLD, 20);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setCaretPosition(0);
        textArea.setEditable(false);
        textArea.setFont(font);
    }
    PasswordManager() {
        frame = new JFrame("Password Manager");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("image/password-manager.png"));
        //frame.setBounds(300,100,700,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(580, 630);
        frameGUI(frame);

        container1 = frame.getContentPane();
        containerGUI(container1);

        //Generator Button settings
        PasswordGenerateButton = new JButton("Generate Password");
        PasswordGenerateButton.setBounds(160, 80, 220, 70);
        container1.add(PasswordGenerateButton);
        buttonsGUI(PasswordGenerateButton);

        //---------------------------------------------------------------------------------------------------
        //Password Generating logic
        PasswordGenerateButton.addActionListener(e -> {
            if (PasswordGenerateButton == e.getSource()) {
                try {
                    int length = Integer.parseInt(JOptionPane.showInputDialog("Enter the password length"));
                    if (length > 8) {
                        //Password Generator class reference
                        PasswordGenerator passwordGenerator = new PasswordGenerator();
                        String password = passwordGenerator.generatePassword(length);
                        generatePasswordArea = new JTextArea(5, 4);
                        textArea(password, generatePasswordArea);
                        JOptionPane.showMessageDialog(container1, new JScrollPane(generatePasswordArea),
                                "Copy your password", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(container1, "Password length must be greater than 8",
                                "Invalid Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(container1, "EXIT!",
                            "EXIT!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //---------------------------------------------------------------------------------------------------------

        //Encryption Button
        PasswordEncryptButton = new JButton("Encrypt Password");
        buttonsGUI(PasswordEncryptButton);
        PasswordEncryptButton.setBounds(160, 180, 220, 70);
        container1.add(PasswordEncryptButton);
        PasswordEncryptButton.addActionListener(e -> {
            if (PasswordEncryptButton == e.getSource()) {
                try {
                    String simplePassword = JOptionPane.showInputDialog("Enter Your Password");
                    if (!simplePassword.isEmpty()) {
                        byte[] salt = PasswordEncryptor.getSalt();
                        String encryptPassword = PasswordEncryptor.get_SHA_1_SecurePassword(simplePassword, salt);
                        //textArea adding into Panel
                        encryptPasswordArea = new JTextArea(7, 4);
                        textArea(encryptPassword, encryptPasswordArea);
                        JOptionPane.showMessageDialog(container1, new JScrollPane(encryptPasswordArea),
                                "Copy your Encrypted Password", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(container1, "Please enter your password!");
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(container1, "EXIT!",
                            "EXIT!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //-----------------------------------------------------------------------------------------------------
        //Storing Password using hashTable
        PasswordStoreButton = new JButton("Store Password");
        PasswordStoreButton.setBounds(160, 280, 220, 70);
        container1.add(PasswordStoreButton);
        buttonsGUI(PasswordStoreButton);
        //Store Password Action
        PasswordStoreButton.addActionListener(e -> {
            if (PasswordStoreButton == e.getSource()) {
                try {
                    StorePasswordGUI();
                    //Action on store button
                    AccountAddButton.addActionListener(e4 -> {
                        if (AccountAddButton == e4.getSource()) {
                            String accountName = tAccount.getText();
                            String accountPassword = tPassword.getText();
                            if (accountName.isEmpty() && accountPassword.isEmpty()) {
                                JOptionPane.showMessageDialog(container2, "Unable to store your password!!!",
                                        "ERROR!", JOptionPane.ERROR_MESSAGE);
                            } else {
                                //calling put method of the hashtablePassword class
                                data.addAccount(accountName, accountPassword);
                                JOptionPane.showMessageDialog(container2, "Account added successfully!");
                                tAccount.setText(null);
                                tPassword.setText(null);
                            }
                        }
                    });
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(container2, "EXIT!", "EXIT!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //-----------------------------------------------------------------------------------------------------
        //Searching Password Logic
        PasswordSearchButton = new JButton("Search Password");
        buttonsGUI(PasswordSearchButton);
        PasswordSearchButton.setBounds(160, 380, 220, 70);
        container1.add(PasswordSearchButton);
        PasswordSearchButton.addActionListener(e -> {
            if (PasswordSearchButton == e.getSource()) {
                try {
                    String accountName = JOptionPane.showInputDialog("Enter Your Account Name");
                    //TODO
                    if (!accountName.isEmpty()) {
                        Object password = data.getAccount(accountName.toLowerCase());
                        if (password != null) {
                            searchPasswordArea = new JTextArea(4, 5);
                            textArea(String.valueOf(password), searchPasswordArea);
                            JOptionPane.showMessageDialog(container1, new JScrollPane(searchPasswordArea),
                                    "Copy your password", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(container1, "Account Not Found!!!");
                        }
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(container1, "EXIT!",
                            "EXIT!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //----------------------------------------------------------------------------------------------------
        //Deleting password logic
        PasswordDeleteButton = new JButton("Delete Password");
        buttonsGUI(PasswordDeleteButton);
        PasswordDeleteButton.setBounds(160, 480, 220, 70);
        container1.add(PasswordDeleteButton);
        PasswordDeleteButton.addActionListener(e -> {
            if (PasswordDeleteButton == e.getSource()) {
                try {
                    String accountName = JOptionPane.showInputDialog("Enter Your Account Name");
                    //TODO
                    if (!accountName.isEmpty()) {
                        data.removeAccount(accountName.toLowerCase());
                        JOptionPane.showMessageDialog(container1, "Account Deleted Successfully!");
                    } else {
                        JOptionPane.showMessageDialog(container1, "Account Not Found!!!",
                                "INFO", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(container1, "Account Not Found or Already Deleted!",
                            "EXIT!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
 //-----------------------------------------------------------------------------------------------------
        //Main method
 public static void main(String[] args) {
     //HomeScreen class
     new HomeScreen();
     try{
         UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
         new PasswordManager();
     }catch (Exception e){
         e.printStackTrace();
     }
 }
}
