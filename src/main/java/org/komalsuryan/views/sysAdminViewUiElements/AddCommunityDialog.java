package org.komalsuryan.views.sysAdminViewUiElements;

import org.komalsuryan.Community;
import org.komalsuryan.Database;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddCommunityDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel addCommunityHeadingLabel;
    private JTextField addCommunityNameValueField;
    private JTextField addCommunityCityValueField;
    private JComboBox<String> addCommunityStateComboBox;
    private JTextField addCommunityZipValueField;
    private JLabel addCommunityNameKeyLabel;
    private JLabel addCommunityCityKeyLabel;
    private JLabel addCommunityStateKeyLabel;
    private JLabel addCommunityZipKeyLabel;

    public AddCommunityDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        addCommunityHeadingLabel.setText("Add New Community");

        // key labels
        addCommunityNameKeyLabel.setText("Name");
        addCommunityCityKeyLabel.setText("City");
        addCommunityStateKeyLabel.setText("State");
        addCommunityZipKeyLabel.setText("Zip Code");

        // value fields
        addCommunityStateComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
        }));

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

    private void onOK() {
        // add your code here
        try {
            String name = addCommunityNameValueField.getText();
            String city = addCommunityCityValueField.getText();
            String state = (String) addCommunityStateComboBox.getSelectedItem();
            String zip = addCommunityZipValueField.getText();
            new Database().addCommunity(new Community(name, city, state, zip));
        } catch (Exception e) {
            // show a dialog with error message
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
