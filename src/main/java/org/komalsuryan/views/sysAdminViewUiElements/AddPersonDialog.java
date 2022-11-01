package org.komalsuryan.views.sysAdminViewUiElements;

import com.toedter.calendar.JDateChooser;
import org.komalsuryan.Community;
import org.komalsuryan.Database;
import org.komalsuryan.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.Date;

public class AddPersonDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel personNameKeyLabel;
    private JLabel personCommunityKeyLabel;
    private JLabel personDateOfBirthLabel;
    private JLabel personSexKeyLabel;
    private JLabel personHeightKeyLabel;
    private JLabel personWeightKeyLabel;
    private JTextField personNameValueField;
    private JComboBox<String> personCommunityValueComboBox;
    private JLabel addPersonHeadingLabel;
    private JLabel personSsnKeyLabel;
    private JTextField personSsnValueField;
    private JPanel personDateOfBirthValuePanel;
    private JTextField personHeightValueField;
    private JRadioButton personMaleRadioButton;
    private JRadioButton personFemaleRadioButton;
    private JTextField personWeightValueField;

    public AddPersonDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        addPersonHeadingLabel.setText("Add Person");

        // labels
        personNameKeyLabel.setText("Name:");
        personSsnKeyLabel.setText("Identity:");
        personCommunityKeyLabel.setText("Community:");
        personDateOfBirthLabel.setText("Date of Birth:");
        personSexKeyLabel.setText("Sex:");
        personHeightKeyLabel.setText("Height (in cms):");
        personWeightKeyLabel.setText("Weight (in kgs):");

        // radio buttons
        personMaleRadioButton.setText("Female");
        personFemaleRadioButton.setText("Male");
        personFemaleRadioButton.setSelected(true);

        // JDateChooser
        JDateChooser personDateOfBirthValueChooser = new JDateChooser();
        personDateOfBirthValueChooser.setDateFormatString("MM-dd-yyyy");
        personDateOfBirthValuePanel.setLayout(new GridLayout());
        personDateOfBirthValuePanel.add(personDateOfBirthValueChooser);

        // combo box
        personCommunityValueComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllCommunities().stream().map(Community::getName).toArray(String[]::new)));

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

    public AddPersonDialog(Person person) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // heading
        addPersonHeadingLabel.setText("Edit Person");

        // labels
        personNameKeyLabel.setText("Name:");
        personSsnKeyLabel.setText("Identity:");
        personCommunityKeyLabel.setText("Community:");
        personDateOfBirthLabel.setText("Date of Birth:");
        personSexKeyLabel.setText("Sex:");
        personHeightKeyLabel.setText("Height (in cms):");
        personWeightKeyLabel.setText("Weight (in kgs):");

        // radio buttons
        personMaleRadioButton.setText("Female");
        personFemaleRadioButton.setText("Male");
        personFemaleRadioButton.setSelected(true);

        // JDateChooser
        JDateChooser personDateOfBirthValueChooser = new JDateChooser();
        personDateOfBirthValueChooser.setDateFormatString("MM-dd-yyyy");
        personDateOfBirthValuePanel.setLayout(new GridLayout());
        personDateOfBirthValuePanel.add(personDateOfBirthValueChooser);

        // combo box
        personCommunityValueComboBox.setModel(new DefaultComboBoxModel<>(new Database().getAllCommunities().stream().map(Community::getName).toArray(String[]::new)));

        // set values
        personNameValueField.setText(person.getName());
        personSsnValueField.setText(person.getSsNumber());
        personCommunityValueComboBox.setSelectedItem(new Database().getCommunity(person.getCommunityId()).getName());
        ((JDateChooser) personDateOfBirthValuePanel.getComponent(0)).setDate(java.sql.Date.valueOf(person.getDateOfBirth()));
        if (person.getSex().equals("Male")) {
            personMaleRadioButton.setSelected(true);
        } else {
            personFemaleRadioButton.setSelected(true);
        }
        personHeightValueField.setText(String.valueOf(person.getHeight()));
        personWeightValueField.setText(String.valueOf(person.getWeight()));

        // disable ssn field
        personSsnValueField.setEnabled(false);

        // buttons
        buttonOK.setText("Edit");
        buttonCancel.setText("Cancel");

        buttonOK.addActionListener(e -> onOK(person));

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
        String name = personNameValueField.getText();
        String ssn = personSsnValueField.getText();
        String community = (String) personCommunityValueComboBox.getSelectedItem();
        int communityId = new Database().getAllCommunities().stream().filter(c -> c.getName().equals(community)).findFirst().get().getId();
        Date date = ((JDateChooser) personDateOfBirthValuePanel.getComponent(0)).getDate();
        // convert date to LocalDate
        LocalDate dateOfBirth = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        // get the selected value from radio group
        String sex = personMaleRadioButton.isSelected() ? personMaleRadioButton.getText() : personFemaleRadioButton.getText();
        float height = Float.parseFloat(personHeightValueField.getText());
        float weight = Float.parseFloat(personWeightValueField.getText());

        // add person
        new Database().addPerson(new Person(ssn, name, communityId, dateOfBirth, sex, height, weight));

        dispose();
    }

    private void onOK(Person person) {
        // get values
        String name = personNameValueField.getText();
        String ssn = personSsnValueField.getText();
        String community = (String) personCommunityValueComboBox.getSelectedItem();
        int communityId = new Database().getAllCommunities().stream().filter(c -> c.getName().equals(community)).findFirst().get().getId();
        Date date = ((JDateChooser) personDateOfBirthValuePanel.getComponent(0)).getDate();
        // convert date to LocalDate
        LocalDate dateOfBirth = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        // get the selected value from radio group
        String sex = personMaleRadioButton.isSelected() ? personMaleRadioButton.getText() : personFemaleRadioButton.getText();
        float height = Float.parseFloat(personHeightValueField.getText());
        float weight = Float.parseFloat(personWeightValueField.getText());

        // update person
        person.setName(name);
        person.setSsNumber(ssn);
        person.setCommunityId(communityId);
        person.setDateOfBirth(dateOfBirth);
        person.setSex(sex);
        person.setHeight(height);
        person.setWeight(weight);

        new Database().updatePerson(person);

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
