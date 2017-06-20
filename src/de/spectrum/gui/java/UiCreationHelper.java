package de.spectrum.gui.java;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Giorgio on 25.05.17.
 */
public class UiCreationHelper {
    public static int SETTINGS_UI_WIDTH = 350;
    public static int SETTINGS_UI_HEIGHT = 400;
    public static int BORDER_SIZE = 10;
    public static int UI_ROW_HEIGHT = 25;


    public static JPanel createValueValuePanel(JLabel valueDescription, JLabel value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(SETTINGS_UI_WIDTH * 2 - BORDER_SIZE * 2, UI_ROW_HEIGHT));

        panel.add(valueDescription);
        panel.add(value);

        return panel;
    }

    public static JPanel createValueInputFieldPanel(JLabel valueDescription, JTextField inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(SETTINGS_UI_WIDTH * 2 - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        DefaultCaret caret = (DefaultCaret)inputField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        panel.add(valueDescription);
        panel.add(inputField);

        return panel;
    }

    public static JPanel createInputFieldInputFieldPanel(JTextField valueDescription, JTextField inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(SETTINGS_UI_WIDTH * 2 - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        DefaultCaret caret = (DefaultCaret)valueDescription.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        DefaultCaret caret2 = (DefaultCaret)inputField.getCaret();
        caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        panel.add(valueDescription);
        panel.add(inputField);

        return panel;
    }

    public static JPanel createSettingsContainer(ActionListener onApplyListener, String title, JPanel settingsInteractionPanel) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(title));
        panel.add(Box.createRigidArea(new Dimension(0, 8)));

        panel.add(settingsInteractionPanel);

        JButton apply = new JButton("OK");
        apply.addActionListener(onApplyListener);
        panel.add(apply);

        return panel;
    }

}
