package org.komalsuryan.views.sysAdminViewUiElements;

import org.komalsuryan.Community;
import org.komalsuryan.CommunityAdmin;
import org.komalsuryan.Database;
import org.komalsuryan.Person;

import javax.swing.*;
import java.awt.event.*;

public class AddCommunityAdminDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel addCommunityAdminHeading;
    private JComboBox<String> communityAdminCommunityComboBox;
    private JComboBox<String> communityAdminPersonComboBox;
    private JTextField communityAdminEmailField;
    private JPasswordField communityAdminPasswordField;
    private JLabel communityAdminPasswordLabel;
    private JLabel communityAdminEmailLabel;
    private JLabel communityAdminPersonLabel;
    private JLabel communityAdminSelectCommunityLabel;

    public AddCommunityAdminDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        addCommunityAdminHeading.setText("Add Community Admin");

        // labels
        communityAdminSelectCommunityLabel.setText("Select Community:");
        communityAdminPersonLabel.setText("Select Person:");
        communityAdminEmailLabel.setText("Email:");
        communityAdminPasswordLabel.setText("Password:");

        // combo boxes
        communityAdminCommunityComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllCommunities().stream().map(Community::getName).toArray(String[]::new)));
        // populate people combo box with people from selected community
        communityAdminCommunityComboBox.addActionListener(e -> {
            String selectedCommunity = (String) communityAdminCommunityComboBox.getSelectedItem();
            int communityId = new Database().getAllCommunities().stream().filter(community -> community.getName().equals(selectedCommunity)).findFirst().get().getId();
            communityAdminPersonComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllPeople().stream().filter(person -> person.getCommunityId() == communityId).map(Person::getName).toArray(String[]::new)));
        });

        // buttons
        buttonOK.setText("Add");
        buttonCancel.setText("Cancel");

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public AddCommunityAdminDialog(CommunityAdmin admin) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        addCommunityAdminHeading.setText("Edit Community Admin");

        // labels
        communityAdminSelectCommunityLabel.setText("Select Community:");
        communityAdminPersonLabel.setText("Select Person:");
        communityAdminEmailLabel.setText("Email:");
        communityAdminPasswordLabel.setText("Password:");

        // combo boxes
        communityAdminCommunityComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllCommunities().stream().map(Community::getName).toArray(String[]::new)));
        // populate people combo box with people from selected community
        communityAdminCommunityComboBox.addActionListener(e -> {
            String selectedCommunity = (String) communityAdminCommunityComboBox.getSelectedItem();
            int communityId = new Database().getAllCommunities().stream().filter(community -> community.getName().equals(selectedCommunity)).findFirst().get().getId();
            communityAdminPersonComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllPeople().stream().filter(person -> person.getCommunityId() == communityId).map(Person::getName).toArray(String[]::new)));
        });

        // values
        communityAdminCommunityComboBox.setSelectedItem(new Database().getCommunity(admin.getCommunityId()).getName());
        communityAdminPersonComboBox.setSelectedItem(new Database().getPerson(admin.getSsNumber()).getName());
        communityAdminEmailField.setText(admin.getEmail());
        communityAdminPasswordField.setText(admin.getPassword());

        // freeze the community and person combo boxes
        communityAdminCommunityComboBox.setEnabled(false);
        communityAdminPersonComboBox.setEnabled(false);

        // buttons
        buttonOK.setText("Edit");
        buttonCancel.setText("Cancel");

        buttonOK.addActionListener(e -> onOK(admin));

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // get values
        String selectedPerson = (String) communityAdminPersonComboBox.getSelectedItem();
        String email = communityAdminEmailField.getText();
        String password = new String(communityAdminPasswordField.getPassword());
        // get ids
        String personId = new Database().getAllPeople().stream().filter(person -> person.getName().equals(selectedPerson)).findFirst().get().getSsNumber();
        // add to database
        new Database().addCommunityAdmin(new CommunityAdmin(personId, email, password));
        dispose();
    }

    private void onOK(CommunityAdmin admin) {
        // get values
        String email = communityAdminEmailField.getText();
        String password = new String(communityAdminPasswordField.getPassword());

        // update admin
        admin.setEmail(email);
        admin.setPassword(password);

        // update database
        new Database().updateCommunityAdmin(admin);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
