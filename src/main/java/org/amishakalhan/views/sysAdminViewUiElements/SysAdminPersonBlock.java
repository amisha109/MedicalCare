package org.amishakalhan.views.sysAdminViewUiElements;

import org.amishakalhan.Database;
import org.amishakalhan.Person;

import javax.swing.*;
import java.time.LocalDate;

public class SysAdminPersonBlock {
    private JLabel personNameLabel;
    private JPanel mainPanel;
    private JLabel personIdLabel;
    private JLabel personCommunityName;
    private JLabel personAgeLabel;
    private JLabel personIdValueLabel;
    private JLabel personCommunityValueLabel;
    private JLabel personAgeValueLabel;
    private JButton editPersonButton;
    private JButton deletePersonButton;

    public SysAdminPersonBlock(Person person) {
        personNameLabel.setText(person.getName());
        personIdLabel.setText("Identity:");
        personIdValueLabel.setText(String.valueOf(person.getSsNumber()));
        personCommunityName.setText("Community:");
        personCommunityValueLabel.setText(String.valueOf(new Database().getCommunity(person.getCommunityId()).getName()));
        // get age from date of birth
        LocalDate today = LocalDate.now();
        int age = today.getYear() - person.getDateOfBirth().getYear();
        if (today.getMonthValue() < person.getDateOfBirth().getMonthValue()) {
            age--;
        } else if (today.getMonthValue() == person.getDateOfBirth().getMonthValue() && today.getDayOfMonth() < person.getDateOfBirth().getDayOfMonth()) {
            age--;
        }
        personAgeLabel.setText("Age:");
        personAgeValueLabel.setText(String.valueOf(age));
        editPersonButton.setText("View/Edit");
        deletePersonButton.setText("Delete");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getEditButton() {
        return editPersonButton;
    }

    public JButton getDeleteButton() {
        return deletePersonButton;
    }
}
