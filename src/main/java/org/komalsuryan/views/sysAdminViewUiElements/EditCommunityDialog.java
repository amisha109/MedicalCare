package org.komalsuryan.views.sysAdminViewUiElements;

import org.komalsuryan.Community;
import org.komalsuryan.Database;

import javax.swing.*;
import java.awt.event.*;

public class EditCommunityDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel editCommunityHeadingLabel;
    private JLabel editCommunityNameKeyLabel;
    private JLabel editCommunityCityKeyLabel;
    private JLabel editCommunityStateKeyLabel;
    private JLabel editCommunityZipKeyLabel;
    private JTextField editCommunityNameValueField;
    private JTextField editCommunityCityValueField;
    private JComboBox<String> editCommunityStateComboBox;
    private JTextField editCommunityZipValueField;

    public EditCommunityDialog(Community community) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        editCommunityHeadingLabel.setText("Edit Community");

        // labels
        editCommunityNameKeyLabel.setText("Name");
        editCommunityCityKeyLabel.setText("City");
        editCommunityStateKeyLabel.setText("State");
        editCommunityZipKeyLabel.setText("Zip Code");

        // values
        editCommunityNameValueField.setText(community.getName());
        editCommunityCityValueField.setText(community.getCity());
        // fill the combo box with states
        editCommunityStateComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
        }));
        editCommunityStateComboBox.setSelectedItem(community.getState());
        editCommunityZipValueField.setText(String.valueOf(community.getZipCode()));

        // buttons
        buttonOK.setText("Edit");
        buttonCancel.setText("Cancel");

        buttonOK.addActionListener(e -> onOK(community));

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

    private void onOK(Community community) {
        // get the values from the fields
        String name = editCommunityNameValueField.getText();
        String city = editCommunityCityValueField.getText();
        String state = (String) editCommunityStateComboBox.getSelectedItem();
        String zipCode = editCommunityZipValueField.getText();

        // update the values in the community object
        community.setName(name);
        community.setCity(city);
        community.setState(state);
        community.setZipCode(zipCode);

        // update the community in the database
        new Database().updateCommunity(community);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
