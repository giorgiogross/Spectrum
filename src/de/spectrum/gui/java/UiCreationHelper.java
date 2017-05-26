package de.spectrum.gui.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Giorgio on 25.05.17.
 */
public class UiCreationHelper {
    public static int SETTINGS_UI_WIDTH = 350;
    public static int SETTINGS_UI_HEIGHT = 400;
    public static int BORDER_SIZE = 10;
    public static int UI_ROW_HEIGHT = 30;

    public static JPanel createValueInputFieldPanel(JLabel valueDescription, JTextArea inputField) {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;

        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT - BORDER_SIZE * 2));
        layoutConstraints.weightx = 0.3;
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        panel.add(valueDescription, layoutConstraints);
        layoutConstraints.weightx = 0.7;
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 0;
        panel.add(inputField, layoutConstraints);

        return panel;
    }

    public static JPanel createSettingsContainer(ActionListener onApplyListener, String title, JPanel settingsInteractionPanel) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel(title));

        panel.add(settingsInteractionPanel);

        JButton apply = new JButton("OK");
        apply.addActionListener(onApplyListener);
        panel.add(apply);

        return panel;
    }

}
