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

    private String inWidth = "";
    private String inHeight = "";

    public DrawEllipse(App context, Node attachedNode) {
        super(context, attachedNode, DESCRIPTION);
    }

    @Override
    public Component getConfigurationPanel() {
        Box settingsInteractionPanel = Box.createVerticalBox();

        final JTextField widthInput = new JTextField();
        final JTextField heightInput = new JTextField();
        settingsInteractionPanel.add(UiCreationHelper
                .createValueInputFieldPanel(new JLabel("Width:"), widthInput));
        settingsInteractionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        settingsInteractionPanel.add(UiCreationHelper
                .createValueInputFieldPanel(new JLabel("Height:"), heightInput));

        return UiCreationHelper.createSettingsContainer(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inWidth = widthInput.getText();
                inHeight = heightInput.getText();
            }
        }, settingsInteractionPanel);
    }

    @Override
    public void execute() {
        context.stroke(paintContext.getCursor().getColor().getRGB());

        if(paintContext.getCursor().isFill()) context.fill(paintContext.getCursor().getColor().getRGB());
        else context.noFill();

        // todo parse the mathematical functions and set width, height and position accordingly
        try {
            width = Integer.parseInt(inWidth);
            height = Integer.parseInt(inHeight);
        } catch (NumberFormatException e) {
            // abort
            return;
        }

        context.ellipse(
                paintContext.getCursor().getX(),
                paintContext.getCursor().getY(),
                width,
                height
        );
    }
}
