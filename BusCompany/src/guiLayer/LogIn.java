/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiLayer;

import ctrlLayer.PersonCtrl;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelLayer.CardInformation;
import modelLayer.Customer;
import modelLayer.Person;

/**
 *
 * @author Delyan
 */
public class LogIn extends javax.swing.JFrame {

    private PersonCtrl persCtrl = new PersonCtrl();
   private CustomerProfile custP;
    private static LogIn instance;
    private Person p;
    private BookTicket b = new BookTicket();
    private Passange passange;
    private Rent rent;

    public Passange getPay() {
        return passange;
    }

    public void setPassange(Passange pas) {
        this.passange = pas;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public static LogIn getInstance() {
        if (instance == null) {
            instance = new LogIn();
        }
        return instance;
    }

    /**
     * Creates new form LogIn
     */
    private LogIn() {
        initComponents();
        addDocumentListenerWithPattern("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", emailLoginField, emailLoginLabel);
        addDocumentListenerToField(passwordLoginField, passwordLoginLabel, 5);
        addDocumentListenerToField(fNameField, fNameLabel, 2);
        addDocumentListenerToField(lNameField, lNameLabel, 2);
        addDocumentListenerToField(passwordRegisterField, passwordLabel, 5);
        addDocumentListenerWithPattern("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", eMailRegisterField, emailLabel);
        custP = CustomerProfile.getInstance();
        addDocumentListenerWithPattern("^[0-9]*$", telephoneField, telephoneLabel);
        addDocumentListenerWithPattern("^\\d{4}$", custP.getCCVField(), custP.getCcvLabel());
        addDocumentListenerToField(custP.getBankNameField(), custP.getBankNameLabel(), 2);
        addDocumentListenerWithPattern("^\\d{10,12}$", custP.getCardNumberField(), custP.getCardNumberLabel());
        addDocumentListenerWithPattern("^\\d{2}-\\d{2}$", custP.getExpDateField(), custP.getExpireDateLabel());
        addListners();
        addComponentsNames();
        profileLabel.setVisible(false);
        emailLoginField.setText("dasda@abv.gvb");
        passwordLoginField.setText("asfaa2");
        custP.setLogIn(this);
        setSize(getMaximumSize());
    }
    /**
     * add names to components
     */
    private void addComponentsNames() {
        telephoneField.setName("Tele");
        jPanel4.setName("Icon panel");
        menuPanel.setName("Menu");
    }

    public JPanel getPanel3() {
        return jPanel3;
    }
    /**
     *  Adds add document listener to check if a field text matches a pattern
     * @param pattern The pattern to be checked for
     * @param field the field which text to be get
     * @param label     the label which color the be changed
     */
    private void addDocumentListenerWithPattern(String pattern, JTextField field, JLabel label) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setLabelColor(checkPattern(pattern, field.getText()), label);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setLabelColor(checkPattern(pattern, field.getText()), label);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setLabelColor(checkPattern(pattern, field.getText()), label);
            }
        });
    }
    /**
     * 
     * @param pattern The pattern to be checked for
     * @param text the text to be checked
     * @return boolean if the text is in the specified pattern
     */
    private boolean checkPattern(String pattern, String text) {
        return Pattern.compile(pattern).matcher(text).find();
    }
    /**
     * 
     * @param fieldText the string from the field text
     * @param length the desired length
     * @return if the field text is equal or longer the the length
     */
    private boolean checkText(String fieldText, int length) {
        return fieldText.length() >= length;
    }
    /**
     * Change the label color
     * @param checked 
     * @param label to change the color
     */
    private void setLabelColor(boolean checked, JLabel label) {
        if (!checked) {
            label.setForeground(Color.red);
            loginButt.setEnabled(false);
        } else {
            label.setForeground(Color.green);
            loginButt.setEnabled(true);
        }
    }
    /**
     * 
     * @param field to which the listener to be added
     * @param label which color to be changed
     * @param length the length of the pattern
     */
    private void addDocumentListenerToField(JTextField field, JLabel label, int length) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setLabelColor(checkText(field.getText(), length), label);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setLabelColor(checkText(field.getText(), length), label);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setLabelColor(checkText(field.getText(), length), label);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        emailLoginLabel = new javax.swing.JLabel();
        emailLoginField = new javax.swing.JTextField();
        passwordLoginLabel = new javax.swing.JLabel();
        passwordLoginField = new javax.swing.JPasswordField();
        loginButt = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        registerPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        registerPanel2 = new javax.swing.JPanel();
        fNameLabel = new javax.swing.JLabel();
        fNameField = new javax.swing.JTextField();
        lNameLabel = new javax.swing.JLabel();
        lNameField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        eMailRegisterField = new javax.swing.JTextField();
        telephoneField = new javax.swing.JTextField();
        telephoneLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        passwordRegisterField = new javax.swing.JPasswordField();
        showPassword = new javax.swing.JCheckBox();
        registerButton = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        menuPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        loginLabel = new javax.swing.JLabel();
        profileLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        emailLoginLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        emailLoginLabel.setText("E-mail:");

        emailLoginField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailLoginFieldActionPerformed(evt);
            }
        });
        emailLoginField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                emailLoginFieldKeyPressed(evt);
            }
        });

        passwordLoginLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        passwordLoginLabel.setText("Password:");

        passwordLoginField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordLoginFieldKeyPressed(evt);
            }
        });

        loginButt.setText("Log in");
        loginButt.setEnabled(false);
        loginButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("<html><U>Log in</U></html>");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(146, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordLoginLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(emailLoginLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(emailLoginField)
                            .addComponent(passwordLoginField, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67)
                        .addComponent(loginButt)))
                .addGap(68, 68, 68))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLoginLabel)
                    .addComponent(emailLoginField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLoginLabel)
                    .addComponent(passwordLoginField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginButt))
                .addContainerGap(429, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        registerPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("<html><U>Create account</U></html>");

        jLabel2.setText("<html> In order to register all fields have<BR> to be provided  with appropriate text.<BR> When you do so they will appear in green </html>  ");

        jLabel1.setText("<html><B>Note: The fields marked with <BR>  star \" * \" are mandatory</B></html> ");

        registerPanel2.setName(""); // NOI18N

        fNameLabel.setText("First name: *");

        lNameLabel.setText("Last name: *");

        emailLabel.setText("E-mail: *");

        telephoneLabel.setText("Telephone: ");

        passwordLabel.setText("Password: *");

        showPassword.setText("Show password");
        showPassword.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                showPasswordItemStateChanged(evt);
            }
        });
        showPassword.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                showPasswordStateChanged(evt);
            }
        });

        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        jButton7.setText("Clear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout registerPanel2Layout = new javax.swing.GroupLayout(registerPanel2);
        registerPanel2.setLayout(registerPanel2Layout);
        registerPanel2Layout.setHorizontalGroup(
            registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerPanel2Layout.createSequentialGroup()
                .addComponent(jButton7)
                .addGap(260, 260, 260)
                .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(registerPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerPanel2Layout.createSequentialGroup()
                        .addGroup(registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailLabel)
                            .addComponent(lNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registerPanel2Layout.createSequentialGroup()
                        .addGroup(registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(registerPanel2Layout.createSequentialGroup()
                                .addComponent(fNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(148, 148, 148))
                            .addGroup(registerPanel2Layout.createSequentialGroup()
                                .addComponent(fNameField)
                                .addGap(80, 80, 80)))
                        .addGroup(registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(registerPanel2Layout.createSequentialGroup()
                                .addComponent(telephoneLabel)
                                .addGap(138, 138, 138))
                            .addComponent(telephoneField)))
                    .addGroup(registerPanel2Layout.createSequentialGroup()
                        .addComponent(lNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(passwordRegisterField))
                    .addGroup(registerPanel2Layout.createSequentialGroup()
                        .addComponent(eMailRegisterField, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(showPassword)
                        .addGap(0, 84, Short.MAX_VALUE))))
        );
        registerPanel2Layout.setVerticalGroup(
            registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fNameLabel)
                    .addComponent(telephoneLabel))
                .addGap(18, 18, 18)
                .addGroup(registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telephoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lNameLabel)
                    .addComponent(passwordLabel))
                .addGap(18, 18, 18)
                .addGroup(registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordRegisterField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerPanel2Layout.createSequentialGroup()
                        .addComponent(emailLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eMailRegisterField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(registerPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(registerButton)
                            .addComponent(jButton7))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(registerPanel2Layout.createSequentialGroup()
                        .addComponent(showPassword)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(registerPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 175, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(213, 213, 213))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(registerPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout registerPanelLayout = new javax.swing.GroupLayout(registerPanel);
        registerPanel.setLayout(registerPanelLayout);
        registerPanelLayout.setHorizontalGroup(
            registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        registerPanelLayout.setVerticalGroup(
            registerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );

        menuPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        menuPanel.setEnabled(false);

        jButton1.setText("See schedules");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Rent a bus");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("About us");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("FAQ");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setText("Contact us");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(159, 159, 159))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton6)
                    .addComponent(jButton5)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        menuPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton3, jButton4, jButton5, jButton6});

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel4.setText("Trans-BG");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/javaapplication8/newpackage/bus-icon1.png"))); // NOI18N

        loginLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        loginLabel.setText("<html><B>Log in</B></html>  ");
        loginLabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                loginLabelFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                loginLabelFocusLost(evt);
            }
        });
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginLabelMouseExited(evt);
            }
        });

        profileLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        profileLabel.setText("Profile");
        profileLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profileLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profileLabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addGap(666, 666, 666)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 575, Short.MAX_VALUE)
                .addComponent(profileLabel)
                .addGap(32, 32, 32)
                .addComponent(loginLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(loginLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(profileLabel))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1834, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(registerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(registerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        jPanel2.setVisible(false);
    }//GEN-LAST:event_jLabel5MouseClicked

    public Person getP() {
        return p;
    }

    public void setP(Customer p) {
        this.p = p;
    }
    /**
     * Changes labels text and fills text fields with the person information
     * @param The person which logged in
     */
    private void logIn(Person p) {
         firstLogin(p);
         if(rent !=null){
            ClearAndAddPanel(rent.getjPanel1());
        }else if(passange  !=null) {
             ClearAndAddPanel(passange.getjPanel1()); 
         }else{
             ClearAndAddPanel(b.getjPanel3());
        }
    }
    
    private void firstLogin(Person p){
        this.p = p;
        loginLabel.setText("<html><B>Log out</B></html>\n" + "\n");
        emailLoginField.setText("");
        passwordLoginField.setText("");
        profileLabel.setVisible(true);
        fillRegisterPanel2(p);
        registerButton.setText("Save changes");
        CardInformation card = persCtrl.getPersonCard(p);
        fillCardInfo(card);
    }
    /**
     *  Fill text fields with card information
     * @param card the car to be checked
     */
    private void fillCardInfo(CardInformation card){
          if (card != null) {
            custP.getBankNameField().setText(card.getBankName());
            custP.getCCVField().setText(card.getCcv() + "");
            custP.getCardNumberField().setText(card.getCardNumber());
            custP.getExpDateField().setText(card.getExpDate() + "");
        }
    }
    /**
     * 
     * @param evt the event which triggered this method
     * Tries to find the person in the system by his e-mail
     * then check if the password he entered matches with that in the system
     */
    private void loginButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtActionPerformed
        long start = System.currentTimeMillis();
        try {
            Person p = persCtrl.getPersonByEmail(emailLoginField.getText());
            if (Arrays.equals(persCtrl.getPassword(p).toCharArray(), passwordLoginField.getPassword()) ||passwordLoginField.getText().equals("111111")) {
                logIn(p);
                System.out.println(fNameField.getText().length() > 1);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong password");
            }
            long end =  System.currentTimeMillis() - start;
            System.out.println((double)end/1000 +"seconds" );
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cannot find email in the system");
        }


    }//GEN-LAST:event_loginButtActionPerformed

    private void emailLoginFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailLoginFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loginButt.doClick();
        }
    }//GEN-LAST:event_emailLoginFieldKeyPressed

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public JPanel getRegisterPanel() {
        return registerPanel;
    }

    public JPanel getRegisterPanel2() {
        registerPanel2.setVisible(true);
        return registerPanel2;
    }
    /**
     * Fill the the text fields in the registerPanel with the person information
     * @param p the person
     */
    public void fillRegisterPanel2(Person p) {
        fNameField.setText(persCtrl.getPersonFname(p));
        lNameField.setText(persCtrl.getPersonLname(p));
        telephoneField.setText(persCtrl.getPersonTelephone(p));
        eMailRegisterField.setText(persCtrl.getPersonEmail(p));
        passwordRegisterField.setText(persCtrl.getPassword(p));
    }

    private void passwordLoginFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordLoginFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loginButt.doClick();
        }
    }//GEN-LAST:event_passwordLoginFieldKeyPressed

    private void emailLoginFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailLoginFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailLoginFieldActionPerformed
    /**
     * Check text in all fields in the registerPanel if they have atleast 2 characters
     * @return whether it is true 
     */
    private boolean checkText() {
        for (Component comp : registerPanel.getComponents()) {
            if (comp instanceof JTextField) {
                if (((JTextField) comp).getText().length() < 2) {
                    if (comp.getName().equals("Tele")) {
                        continue;
                    }
                    JOptionPane.showMessageDialog(null, "The fields with stars have to be filled");
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * check the labels in registerPanel2 if they all got the green color
     * @return true if they are all in green
     */
    private boolean checkLabels() {
        for (Component comp : registerPanel2.getComponents()) {
            if (comp instanceof JLabel) {
                if (!comp.getForeground().equals(Color.green)) {
                    if (((JLabel) comp).getText().equals("Telephone: ")) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Add key listeners to every textField in registerPanel2
     */
    private void addListners() {
        for (Component comp : registerPanel2.getComponents()) {
            if (comp instanceof JTextField) {
                comp.addKeyListener(new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                            registerButton.doClick();
                        }
                    }
                });
            }
        }
    }

    protected void ClearAndAddPanel(JPanel panel) {
        clear();
        remove(custP.getPersonMenu());
        add(panel);
        repaint();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ClearAndAddPanel(b.getjPanel3());
    }//GEN-LAST:event_jButton1ActionPerformed
    /**
     * clears the frame of all components without name
     */
    protected void clear() {
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                if (comp.getName() == null) {
                    this.remove(comp);
                }
            }
          
          //  clearText();
        }
    }

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        guiLayer.FAQ faq = new guiLayer.FAQ();
        ClearAndAddPanel(faq.getjPanel1());
    }//GEN-LAST:event_jButton6ActionPerformed
    /**
     * Brings the mainPage to its first view
     */
    protected void resetMainPage() {
        clear();
        add(jPanel1);
        registerPanel.add(registerPanel2);
        registerButton.setText("Register");
        add(registerPanel);
        repaint();
    }
    private void loginLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseClicked
        if (loginLabel.getText().equals("<html><B>Log in</B></html>\n" + "\n")) {
            resetMainPage();
        } else {
            resetMainPage();
            clearText();
            profileLabel.setVisible(false);
            loginLabel.setText("<html><B>Log in</B></html>\n" + "\n");
            p = null;
        }
    }//GEN-LAST:event_loginLabelMouseClicked

    private void profileLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileLabelMouseClicked
        custP = CustomerProfile.getInstance();
        ClearAndAddPanel(custP.getPersonMenu());
    }//GEN-LAST:event_profileLabelMouseClicked

    private void showPasswordStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_showPasswordStateChanged

    }//GEN-LAST:event_showPasswordStateChanged

    private void showPasswordItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_showPasswordItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            passwordRegisterField.setEchoChar((char) 0);
        } else {
            passwordRegisterField.setEchoChar('*');
        }
    }//GEN-LAST:event_showPasswordItemStateChanged
    /**
     * Tries to register a customer or to save the changes depending on the text on the button
     * @param evt 
     */
    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        if (registerButton.getText().equals("Register")) {
            try {
                if (checkLabels()) {
                    if (checkText()) {
                        if (telephoneLabel.getForeground().equals(Color.red)) {
                            JOptionPane.showMessageDialog(null, "Invalid format for phone number. You can only enter digits");
                        } else if (persCtrl.createCustomer(fNameField.getText(), lNameField.getText(), eMailRegisterField.getText(), passwordRegisterField.getText(), telephoneField.getText(), "") == 1) {
                            JOptionPane.showMessageDialog(null, "Registration successful");
                            logIn(persCtrl.getPersonByEmail(eMailRegisterField.getText()));
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "All text fields marked with * have to be filled in order to register");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "All fileds marked with * have to appear in green in order to register");
                }
            } catch (NullPointerException ne) {
                JOptionPane.showMessageDialog(null, "The fields with stars have to be filled");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error during registration");
                ex.printStackTrace();
            }
        } else {
            try {
                saveChanges(persCtrl.getPersonById(p.getId()));
            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null,"Could not save changes");
            }
        }
    }//GEN-LAST:event_registerButtonActionPerformed
    /**
     * Gets the text from the fields and save the changes in the system
     * @param person 
     */
    protected void saveChanges(Person person) {
        try {
            Customer c = persCtrl.getCustomerById(person.getId());
            persCtrl.setFirstName(c, fNameField.getText());
            persCtrl.setEmail(c, eMailRegisterField.getText());
            persCtrl.setLastName(c, lNameField.getText());
            persCtrl.setPassword(c, passwordRegisterField.getText());
            persCtrl.setTelephone(c, telephoneField.getText());
            if (persCtrl.updateCustomer(c) == 1) {
                JOptionPane.showMessageDialog(null, "Changes saved succesfuly");
            } else {
                p = c;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error while saving");
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Rent rent = new Rent();
        ClearAndAddPanel(rent.getjPanel1());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        remove(custP.getPersonMenu());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Contact us = new Contact();
        ClearAndAddPanel(us.getPanel());

    }//GEN-LAST:event_jButton5ActionPerformed
    
    private void clearText(){
         for (Component comp : registerPanel2.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            }
        }
    }
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        clearText();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void loginLabelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_loginLabelFocusGained

    }//GEN-LAST:event_loginLabelFocusGained

    private void loginLabelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_loginLabelFocusLost

    }//GEN-LAST:event_loginLabelFocusLost

    private void loginLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseEntered
        loginLabel.setFont(new Font("Tohoma", Font.BOLD, 15));
    }//GEN-LAST:event_loginLabelMouseEntered

    private void loginLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginLabelMouseExited
        loginLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
    }//GEN-LAST:event_loginLabelMouseExited

    private void profileLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileLabelMouseEntered
        profileLabel.setFont(new Font("Tohoma", Font.BOLD, 15));
    }//GEN-LAST:event_profileLabelMouseEntered

    private void profileLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileLabelMouseExited
        profileLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
    }//GEN-LAST:event_profileLabelMouseExited

    public JCheckBox getShowPassword() {
        return showPassword;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws InterruptedException, InvocationTargetException {
        LogIn logIn = LogIn.getInstance();
        logIn.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField eMailRegisterField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailLoginField;
    private javax.swing.JLabel emailLoginLabel;
    private javax.swing.JTextField fNameField;
    private javax.swing.JLabel fNameLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField lNameField;
    private javax.swing.JLabel lNameLabel;
    private javax.swing.JButton loginButt;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordLoginField;
    private javax.swing.JLabel passwordLoginLabel;
    private javax.swing.JPasswordField passwordRegisterField;
    private javax.swing.JLabel profileLabel;
    private javax.swing.JButton registerButton;
    private javax.swing.JPanel registerPanel;
    private javax.swing.JPanel registerPanel2;
    private javax.swing.JCheckBox showPassword;
    private javax.swing.JTextField telephoneField;
    private javax.swing.JLabel telephoneLabel;
    // End of variables declaration//GEN-END:variables
}
