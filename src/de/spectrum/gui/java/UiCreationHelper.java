package de.spectrum.gui.java;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * Created by Giorgio on 25.05.17.
 */
public class UiCreationHelper {
    public static int SETTINGS_UI_WIDTH = 350;
    public static int SETTINGS_UI_HEIGHT = 400;
    public static int BORDER_SIZE = 10;
    public static int UI_ROW_HEIGHT = 25;


    public static Box createValueValuePanel(JLabel valueDescription, JLabel value) {
        Box panel = Box.createHorizontalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(SETTINGS_UI_WIDTH * 2 - BORDER_SIZE * 2, UI_ROW_HEIGHT));

        panel.add(valueDescription);
        panel.add(value);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }

    public static Box createValueInputFieldPanel(JLabel valueDescription, JTextField inputField) {
        Box panel = Box.createHorizontalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(SETTINGS_UI_WIDTH * 2 - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        DefaultCaret caret = (DefaultCaret)inputField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        panel.add(valueDescription);
        panel.add(inputField);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }

    public static Box createInputFieldInputFieldPanel(JTextField valueDescription, JTextField inputField) {
        Box panel = Box.createHorizontalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(SETTINGS_UI_WIDTH * 2 - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        DefaultCaret caret = (DefaultCaret)valueDescription.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        DefaultCaret caret2 = (DefaultCaret)inputField.getCaret();
        caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        panel.add(valueDescription);
        panel.add(inputField);

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }

    public static Box createRightButtonPanel(String title, ActionListener listener) {
        Box panel = Box.createVerticalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setMaximumSize(new Dimension(SETTINGS_UI_WIDTH * 2 - BORDER_SIZE * 2, UI_ROW_HEIGHT));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton button = new JButton(title);
        button.addActionListener(listener);
        button.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(button);

        return panel;
    }

    public static Box createSettingsContainer(ActionListener onApplyListener, Box settingsInteractionPanel) {
        Box panel = Box.createVerticalBox();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        panel.add(settingsInteractionPanel);
        panel.add(UiCreationHelper.createRightButtonPanel("OK", onApplyListener));

        return panel;
    }

}
