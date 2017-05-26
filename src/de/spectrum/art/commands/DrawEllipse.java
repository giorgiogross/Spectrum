package de.spectrum.art.commands;

import de.spectrum.App;
import de.spectrum.art.Node;
import de.spectrum.gui.java.UiCreationHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Giorgio on 20.05.17.
 */
public class DrawEllipse extends Command {
    public static final String DESCRIPTION = "Ellipse";

    private int width = 0;
    private int height = 0;

    public DrawEllipse(App context, Node attachedNode) {
        super(context, attachedNode);
    }

    @Override
    public JPanel getConfigurationPanel() {
        JPanel settingsInteractionPanel = new JPanel();
        settingsInteractionPanel.setLayout(new BoxLayout(settingsInteractionPanel, BoxLayout.Y_AXIS));

        final JTextArea widthInput = new JTextArea();
        final JTextArea heightInput = new JTextArea();
        settingsInteractionPanel.add(UiCreationHelper
                .createValueInputFieldPanel(new JLabel("Width:"), widthInput));
        settingsInteractionPanel.add(UiCreationHelper
                .createValueInputFieldPanel(new JLabel("Height:"), heightInput));

        return UiCreationHelper.createSettingsContainer(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        }, DESCRIPTION, settingsInteractionPanel);
    }

    @Override
    public void execute() {
        context.stroke(paintContext.getCursor().getColor().getRGB());

        if(paintContext.getCursor().isFill()) context.fill(paintContext.getCursor().getColor().getRGB());
        else context.noFill();

        context.ellipse(
                paintContext.getCursor().getX(),
                paintContext.getCursor().getY(),
                width,
                height
        );
    }
}
