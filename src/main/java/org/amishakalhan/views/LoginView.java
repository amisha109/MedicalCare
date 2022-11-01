package org.amishakalhan.views;

import org.amishakalhan.Database;
import org.amishakalhan.UserTypes;

import javax.swing.*;

public class LoginView {
    private JPanel mainPanel;
    private JLabel loginPanelHeading;
    private JTextField loginEmailValue;
    private JPasswordField loginPasswordValue;
    private JLabel loginEmailLabel;
    private JLabel loginPasswordLabel;
    private JComboBox<String> loginTypesComboBox;
    private JLabel loginAsLabel;
    private JButton loginButton;
    private JButton browseAnonButton;

    private Database db;

    public LoginView(JFrame currentJframe) {
        db = new Database();
        loginPanelHeading.setText("Welcome to the Hospital Management System. Login to continue");
        loginEmailLabel.setText("Email: ");
        loginPasswordLabel.setText("Password: ");
        loginAsLabel.setText("Login as: ");
        // populate the combo box with UserTypes Enum
        for (UserTypes type : UserTypes.values()) {
            loginTypesComboBox.addItem(type.toString());
        }
        loginTypesComboBox.setSelectedItem(UserTypes.PATIENT.toString());

        loginButton.setText("Login");
        browseAnonButton.setText("Browse without logging in");

        loginButton.addActionListener(e -> onLogin(currentJframe));
        browseAnonButton.addActionListener(e -> onBrowseAnon(currentJframe));
    }

    private void onLogin(JFrame currentJframe) {
        // get values
        String email = loginEmailValue.getText();
        String password = loginPasswordValue.getText();
        String userType = (String) loginTypesComboBox.getSelectedItem();
        UserTypes type = UserTypes.valueOf(userType);

        // validate values
        int userId = new Database().validate(email, password, type);

        if (userId == -1) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        if (type == UserTypes.SYSTEM_ADMIN) {
            JFrame systemAdminFrame = new JFrame("System Admin");
            systemAdminFrame.setContentPane(new SysAdminView().getMainPanel());
            systemAdminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            systemAdminFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            systemAdminFrame.pack();
            systemAdminFrame.setVisible(true);
            currentJframe.dispose();
        } else if (type == UserTypes.COMMUNITY_ADMIN) {
            JFrame communityAdminFrame = new JFrame("Community Admin");
            communityAdminFrame.setContentPane(new CommunityAdminView(db.getCommunityAdmin(userId)).getMainPanel());
            communityAdminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            communityAdminFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            communityAdminFrame.pack();
            communityAdminFrame.setVisible(true);
            currentJframe.dispose();
        } else if (type == UserTypes.DOCTOR) {
            JFrame doctorFrame = new JFrame("Doctor");
            doctorFrame.setContentPane(new DoctorView(db.getDoctor(userId)).getMainPanel());
            doctorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            doctorFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            doctorFrame.pack();
            doctorFrame.setVisible(true);
        } else if (type == UserTypes.PATIENT) {
//            currentJframe.setContentPane(new PatientView(userId).getMainPanel());
        }
    }

    private void onBrowseAnon(JFrame currentJframe) {
        JFrame anonFrame = new JFrame("Browse Anonymously");
        anonFrame.setContentPane(new PersonView().getMainPanel());
        anonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        anonFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        anonFrame.pack();
        anonFrame.setVisible(true);
        currentJframe.dispose();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
