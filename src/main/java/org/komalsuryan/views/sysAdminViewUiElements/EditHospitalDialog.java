package org.komalsuryan.views.sysAdminViewUiElements;

import org.komalsuryan.Community;
import org.komalsuryan.Database;
import org.komalsuryan.Hospital;

import javax.swing.*;
import java.awt.event.*;

public class EditHospitalDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel editHospitalHeadingLabel;
    private JLabel editHospitalNameKeyLabel;
    private JLabel editHospitalLocationKeyLabel;
    private JLabel editHospitalCommunityKeyLabel;
    private JTextField editHospitalNameValueField;
    private JTextField editHospitalLocationValueField;
    private JComboBox<String> editHospitalCommunityValueComboBox;
    private JCheckBox editHospitalIsClinicCheckbox;

    public EditHospitalDialog(Hospital hospital) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        editHospitalHeadingLabel.setText("Edit Hospital");

        // labels
        editHospitalNameKeyLabel.setText("Name");
        editHospitalLocationKeyLabel.setText("Location");
        editHospitalCommunityKeyLabel.setText("Community");

        // checkbox
        editHospitalIsClinicCheckbox.setText("Is Clinic");

        // value fields
        editHospitalNameValueField.setText(hospital.getName());
        editHospitalLocationValueField.setText(hospital.getLocation());
        // fill the combo-box with community names from getAllCommunities()
        editHospitalCommunityValueComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllCommunities().stream().map(Community::getName).toArray(String[]::new)));
        // set the selected item to the community of the hospital
        editHospitalCommunityValueComboBox.setSelectedItem(new Database().getCommunity(hospital.getCommunityId()).getName());
        // set the checkbox to the value of the hospital
        editHospitalIsClinicCheckbox.setSelected(hospital.isClinic());

        // buttons
        buttonOK.setText("Edit");
        buttonCancel.setText("Cancel");

        buttonOK.addActionListener(e -> onOK(hospital));

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

    private void onOK(Hospital hospital) {
        // get the values from the fields
        String name = editHospitalNameValueField.getText();
        String location = editHospitalLocationValueField.getText();
        String communityName = (String) editHospitalCommunityValueComboBox.getSelectedItem();
        boolean isClinic = editHospitalIsClinicCheckbox.isSelected();

        // update the hospital
        hospital.setName(name);
        hospital.setLocation(location);
        hospital.setCommunityId(new Database().getAllCommunities().stream().filter(c -> c.getName().equals(communityName)).findFirst().get().getId());
        hospital.setClinicStatus(isClinic);

        // update the database
        new Database().updateHospital(hospital);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void setEditHospitalCommunityValueComboBox(ComboBoxModel<String> model) {
        editHospitalCommunityValueComboBox.setModel(model);
    }
}
