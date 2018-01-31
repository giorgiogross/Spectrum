package de.spectrum.gui.java;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * @author Giorgio Gross <lars.ordsen@gmail.com>
 */
public class UiCreationHelper {
    public static int SETTINGS_UI_WIDTH = 400;
    public static int SETTINGS_UI_HEIGHT = 500;
    public static int BORDER_SIZE = 10;
    public static int UI_ROW_HEIGHT = 28;


    public static Box createValueValuePanel(JLabel valueDescription, JLabel value, ActionListener deleteAction) {
        Box panel = Box.createHorizontalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UI_ROW_HEIGHT));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(valueDescription);
        panel.add(value);

        if(deleteAction != null) {
            JButton del = new JButton("X");
            del.addActionListener(deleteAction);
            panel.add(Box.createHorizontalGlue());
            panel.add(del);
        }

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }

    public static Box createValueInputFieldPanel(JLabel valueDescription, JTextField inputField) {
        Box panel = Box.createHorizontalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UI_ROW_HEIGHT));
        DefaultCaret caret = (DefaultCaret)inputField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        panel.add(valueDescription);
        panel.add(inputField);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }

    public static Box createNInputFieldPanel(JTextField ... jTextFields) {
        Box panel = Box.createHorizontalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UI_ROW_HEIGHT));

        for (JTextField tf : jTextFields) {
            DefaultCaret caret = (DefaultCaret)tf.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            panel.add(tf);
        }
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }

    public static Box createRightButtonPanel(String title, ActionListener listener) {
        Box panel = Box.createVerticalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UI_ROW_HEIGHT));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton button = new JButton(title);
        button.addActionListener(listener);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(button);

        return panel;
    }

    public static Box createHorizontalDivider(int height) {
        Box panel = Box.createVerticalBox();
        panel.add(Box.createVerticalStrut(height / 2));
        panel.add(new JSeparator(SwingConstants.HORIZONTAL));
        panel.add(Box.createVerticalStrut(height / 2));
        return panel;
    }

    public static Box createSettingsContainer(ActionListener onApplyListener, Box settingsInteractionPanel) {
        Box panel = Box.createVerticalBox();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        panel.add(settingsInteractionPanel);
        panel.add(UiCreationHelper.createRightButtonPanel("OK", onApplyListener));

        return panel;
    }

    public static Box createEmptyHorizontalBox() {
        Box panel = Box.createHorizontalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT * 2));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UI_ROW_HEIGHT));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    public static Box createEmptyVerticalBox() {
        Box panel = Box.createVerticalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT * 2));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UI_ROW_HEIGHT));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }
}
