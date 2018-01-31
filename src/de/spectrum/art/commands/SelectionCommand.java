package de.spectrum.art.commands;

import de.spectrum.App;
import de.spectrum.art.CommandNode;
import de.spectrum.art.Node;
import de.spectrum.gui.java.UiCreationHelper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static de.spectrum.gui.java.UiCreationHelper.BORDER_SIZE;
import static de.spectrum.gui.java.UiCreationHelper.SETTINGS_UI_WIDTH;
import static de.spectrum.gui.java.UiCreationHelper.UI_ROW_HEIGHT;

/**
 * Command used as placeholder. Shows UI to select actual Command to be used with the attached Node.
 *
 * @author Giorgio Gross <lars.ordsen@gmail.com>
 */
public class SelectionCommand extends Command {
    public static final String DESCRIPTION = "Select Command";

    public SelectionCommand(App context, Node attachedNode) {
        super(context, attachedNode, DESCRIPTION);
    }

    @Override
    public Component getConfigurationPanel() {
        // call ((CommandNode)attachedNode).setSettingsView(...); wehn command selected
        Box panel = UiCreationHelper.createEmptyHorizontalBox();
        panel.setPreferredSize(new Dimension(SETTINGS_UI_WIDTH - BORDER_SIZE * 2, UI_ROW_HEIGHT * 2));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UI_ROW_HEIGHT * 2));
        panel.setOpaque(true);
        panel.setBackground(new Color(220,220,220));

        ArrayList<String> cmdNames = CommandRegistry.GetCommands();

        final JComboBox cbSelect = new JComboBox(cmdNames.toArray());
        cbSelect.addActionListener(e -> {
            try {
                ((CommandNode)attachedNode).setCommand(
                        CommandRegistry.GetClass((String)cbSelect.getSelectedItem())
                                .getDeclaredConstructor(App.class, Node.class).newInstance(context, attachedNode)
                );
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        });
        panel.add(cbSelect);

        JTextField frameNumInput = new JTextField("0");
        frameNumInput.setPreferredSize(new Dimension(100, UiCreationHelper.UI_ROW_HEIGHT));
        frameNumInput.setMaximumSize(new Dimension(100, UiCreationHelper.UI_ROW_HEIGHT));
        frameNumInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateRenderAtFrame();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateRenderAtFrame();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateRenderAtFrame();
            }

            private void updateRenderAtFrame() {
                String in = frameNumInput.getText();
                if(!in.matches("[0-9]+")) return;
                attachedNode.setRenderAtFrame(Integer.parseInt(in));
            }

        });
        panel.add(frameNumInput);

        return panel;
    }

    @Override
    public void execute() {
        // do nothing
    }
}
