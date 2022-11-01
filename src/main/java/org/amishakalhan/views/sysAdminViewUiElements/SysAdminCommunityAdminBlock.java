package org.amishakalhan.views.sysAdminViewUiElements;

import org.amishakalhan.CommunityAdmin;
import org.amishakalhan.Database;

import javax.swing.*;

public class SysAdminCommunityAdminBlock {
    private JLabel communityAdminNameLabel;
    private JLabel communityAdminIdLabel;
    private JLabel communityAdminCommunityName;
    private JLabel communityAdminEmailLabel;
    private JPanel mainPanel;
    private JLabel communityAdminIdValueLabel;
    private JLabel communityAdminCommunityValueLabel;
    private JLabel communityAdminEmailValueLabel;
    private JButton editcommunityAdminButton;
    private JButton deletecommunityAdminButton;

    public SysAdminCommunityAdminBlock(CommunityAdmin admin) {
        // heading
        communityAdminNameLabel.setText(admin.getName());

        // labels
        communityAdminIdLabel.setText("ID:");
        communityAdminCommunityName.setText("Community:");
        communityAdminEmailLabel.setText("Email:");

        // values
        communityAdminIdValueLabel.setText(String.valueOf(admin.getId()));
        communityAdminCommunityValueLabel.setText(new Database().getCommunity(admin.getCommunityId()).getName());
        communityAdminEmailValueLabel.setText(admin.getEmail());

        // buttons
        editcommunityAdminButton.setText("View/Edit");
        deletecommunityAdminButton.setText("Delete");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getEditButton() {
        return editcommunityAdminButton;
    }

    public JButton getDeleteButton() {
        return deletecommunityAdminButton;
    }
}
