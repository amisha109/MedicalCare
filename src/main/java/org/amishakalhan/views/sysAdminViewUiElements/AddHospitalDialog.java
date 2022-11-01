package org.amishakalhan.views.sysAdminViewUiElements;

import org.amishakalhan.Community;
import org.amishakalhan.Database;
import org.amishakalhan.Hospital;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddHospitalDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel addHospitalHeadingLabel;
    private JLabel addHospitalNameKeyLabel;
    private JLabel addHospitalLocationKeyLabel;
    private JLabel addHospitalCommunityKeyLabel;
    private JTextField addHospitalNameValueField;
    private JComboBox<String> addHospitalCommunityValueComboBox;
    private JTextField addHospitalLocationValueField;
    private JCheckBox addHospitalIsClinicCheckbox;

    public AddHospitalDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        addHospitalHeadingLabel.setText("Add New Hospital/Clinic");

        // key labels
        addHospitalNameKeyLabel.setText("Name");
        addHospitalLocationKeyLabel.setText("Location");
        addHospitalCommunityKeyLabel.setText("Select Community");

        // value fields
        addHospitalCommunityValueComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllCommunities().stream().map(Community::getName).toArray(String[]::new)));
        addHospitalIsClinicCheckbox.setText("This is a clinic");

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

    public static void main(String[] args) {
        AddHospitalDialog dialog = new AddHospitalDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
        // get values
        try {
            String name = addHospitalNameValueField.getText();
            String location = addHospitalLocationValueField.getText();
            String community = (String) addHospitalCommunityValueComboBox.getSelectedItem();
            int communityId = new Database().getAllCommunities().stream().filter(c -> c.getName().equals(community)).findFirst().get().getId();
            boolean isClinic = addHospitalIsClinicCheckbox.isSelected();
            new Database().addHospital(new Hospital(name, location, communityId, isClinic));
        } catch (Exception e) {
            // show dialog with error message
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void setAddHospitalCommunityValueComboBox(ComboBoxModel<String> model) {
        addHospitalCommunityValueComboBox.setModel(model);
    }
}
