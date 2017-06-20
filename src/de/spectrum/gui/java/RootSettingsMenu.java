package de.spectrum.gui.java;

import de.spectrum.App;
import de.spectrum.art.*;
import de.spectrum.art.PaintContext;
import de.spectrum.gui.OnFocusChangedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by Giorgio on 19.06.17.
 */
public class RootSettingsMenu extends Component implements OnFocusChangedListener {
    private RootNode attachedNode;
    private App context;

    private NodeSettingsMenu settingsMenu;
    private PaintContext paintContext;
    private Box varsPanel;

    public RootSettingsMenu(App context, final RootNode attachedNode) {
        super(context, context.getSettingsFrame("ROOT" + attachedNode.getId()));
        this.context = context;
        this.attachedNode = attachedNode;
        this.paintContext = attachedNode.getPaintContext();

        final Box settingsInteractionPanel = Box.createVerticalBox();

        // todo add views to add vars, colors etc.
        /*
        Variables configuration
         */
        // Display existing variables
        varsPanel = Box.createVerticalBox();
        updateVarsPanel(varsPanel, paintContext);
        settingsInteractionPanel.add(varsPanel);

        // UI to add a new variable
        final JTextField varDescription = new JTextField();
        final JTextField varValue = new JTextField();
        settingsInteractionPanel.add(UiCreationHelper
                .createInputFieldInputFieldPanel(varDescription, varValue));

        settingsInteractionPanel.add(UiCreationHelper.createRightButtonPanel(
                "ADD",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String descr = varDescription.getText();
                        String val = varValue.getText();
                        if (descr.isEmpty() || val.isEmpty()) return;

                        if (val.matches("[-]?[0-9]+.[0-9]+")) {
                            Float varVal = Float.parseFloat(val);

                            paintContext.addFloatVar(descr, varVal);
                        } else if (val.matches("[-]?[0-9]+")) {
                            Integer varVal = Integer.parseInt(val);
                            paintContext.addIntVar(descr, varVal);
                        } else return;

                        varsPanel.removeAll();
                        updateVarsPanel(varsPanel, paintContext);
                        varDescription.setText("");
                        varValue.setText("");
                        getView().validate();
                    }
                })
        );

        settingsMenu = new NodeSettingsMenu(context, getView(),
                UiCreationHelper.createSettingsContainer(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                }, settingsInteractionPanel)
        );
    }

    private void updateVarsPanel(Box varsPanel, PaintContext pc) {
        HashMap<String, Integer> intVars = pc.getIntVars();
        HashMap<String, Float> floatVars = pc.getFloatVars();
        int numVars = intVars.size() + pc.getFloatVars().size();

        JLabel title = new JLabel("Variables");
        title.setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
        title.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        varsPanel.add(title);
        varsPanel.add(Box.createVerticalStrut(5));

        for (String key : intVars.keySet()) {
            varsPanel.add(UiCreationHelper
                    .createValueValuePanel(new JLabel(key + ": "), new JLabel("" + pc.getIntVar(key))));
        }
        for (String key : floatVars.keySet()) {
            varsPanel.add(UiCreationHelper
                    .createValueValuePanel(new JLabel(key + ": "), new JLabel("" + pc.getFloatVar(key))));
        }
    }

    @Override
    public void onFocusChanged(Component c) {
        if (c == null || !c.equals(attachedNode.getProcessingView())) {
            setFrameVisibility(false);
            return;
        }
        setFrameVisibility(true);
        validatePanels();
    }

    /**
     * Re-creates all content panels so that they show the latest data
     */
    private void validatePanels() {
        varsPanel.removeAll();
        updateVarsPanel(varsPanel, paintContext);

        getView().validate();
    }
}
