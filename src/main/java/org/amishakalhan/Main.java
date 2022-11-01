package org.amishakalhan;

import org.amishakalhan.views.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // write your code here
        JFrame jFrame = new JFrame("Hospital Management System");
        try {
            jFrame.setContentPane(new LoginView(jFrame).getMainPanel());
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setLocationRelativeTo(null);
            jFrame.pack();
            jFrame.setVisible(true);
        } catch (Exception e) {
            // create a dialog to show the error
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}