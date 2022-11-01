package org.komalsuryan.views.sysAdminViewUiElements;

import org.komalsuryan.Database;
import org.komalsuryan.Hospital;

import javax.swing.*;

public class SysAdminHospitalBlock {
    private JPanel mainPanel;
    private JLabel hospitalNameLabel;
    private JPanel hospitalInfoPanel;
    private JButton editbutton;
    private JButton deleteButton;
    private JLabel hospitalCommunityValue;
    private JLabel hospitalLocationValue;
    private JLabel hospitalLocationLabel;
    private JLabel hospitalCommunityLabel;

    public SysAdminHospitalBlock(Hospital hospital) {
        // heading
        hospitalNameLabel.setText(hospital.getName());

        // info
        hospitalLocationLabel.setText("Location");
        hospitalLocationValue.setText(hospital.getLocation());
        hospitalCommunityLabel.setText("Community");
        hospitalCommunityValue.setText(new Database().getCommunity(hospital.getCommunityId()).getName());

        // buttons
        editbutton.setText("Edit");
        deleteButton.setText("Delete");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getEditButton() {
        return editbutton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
