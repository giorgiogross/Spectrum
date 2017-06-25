package de.spectrum.gui.java;

import de.spectrum.App;
import de.spectrum.art.*;
import de.spectrum.art.PaintContext;
import de.spectrum.gui.OnFocusChangedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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
    private Box colorsPanel;

    public RootSettingsMenu(App context, final RootNode attachedNode, PaintContext paintCtx) {
        super(context, context.getSettingsFrame("ROOT" + attachedNode.getId()));
        this.context = context;
        this.attachedNode = attachedNode;
        this.paintContext = paintCtx;

        final Box settingsInteractionPanel = Box.createVerticalBox();

        /*
        Variables configuration
         */
        // Display existing variables
        varsPanel = Box.createVerticalBox();
        updateVarsPanel(varsPanel);
        settingsInteractionPanel.add(varsPanel);

        // UI to add a new variable
        final JTextField varDescription = new JTextField();
        final JTextField varValue = new JTextField();
        settingsInteractionPanel.add(UiCreationHelper
                .createNInputFieldPanel(varDescription, varValue));
        settingsInteractionPanel.add(UiCreationHelper.createRightButtonPanel(
                "ADD",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String descr = varDescription.getText();
                        String val = varValue.getText();
                        if (descr.isEmpty() || val.isEmpty()) return;

                        if (val.matches("[-]?[0-9]+")) {
                            Integer varVal = Integer.parseInt(val);
                            paintContext.addIntVar(descr, varVal);
                        } else if (val.matches("[-]?[0-9]+.[0-9]+")) {
                            Float varVal = Float.parseFloat(val);
                            paintContext.addFloatVar(descr, varVal);
                        } else return;

                        varDescription.setText("");
                        varValue.setText("");
                        validatePanels();
                    }
                })
        );

        settingsInteractionPanel.add(UiCreationHelper.createHorizontalDivider(10));

        colorsPanel = Box.createVerticalBox();
        updateColorsPanel(colorsPanel);
        settingsInteractionPanel.add(colorsPanel);

        final JTextField colorDescription = new JTextField();
        final JTextField colorR = new JTextField();
        final JTextField colorG = new JTextField();
        final JTextField colorB = new JTextField();
        final JTextField colorA = new JTextField();
        settingsInteractionPanel.add(UiCreationHelper
                .createNInputFieldPanel(colorDescription, colorR, colorG, colorB, colorA));
        settingsInteractionPanel.add(UiCreationHelper.createRightButtonPanel(
                "ADD",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String descr = colorDescription.getText();
                        String[] vals = {colorR.getText(), colorG.getText(), colorB.getText(), colorA.getText()};

                        boolean isOneWrong = false;
                        for(String v : vals) {
                            if(v.isEmpty() || !v.matches("\\b(1?[0-9]{1,2}|2[0-4][0-9]|25[0-5])\\b")) {
                                isOneWrong = true;
                                break;
                            }
                        }
                        if (descr.isEmpty() || isOneWrong)
                            return;

                        paintContext.addColor(descr, new Color(
                                Integer.parseInt(vals[0]),
                                Integer.parseInt(vals[1]),
                                Integer.parseInt(vals[2]),
                                Integer.parseInt(vals[3])
                        ));

                        colorDescription.setText("");
                        colorR.setText("");
                        colorG.setText("");
                        colorB.setText("");
                        colorA.setText("");
                        validatePanels();
                    }
                })
        );

        settingsMenu = new NodeSettingsMenu(context, getView(),
                UiCreationHelper.createSettingsContainer(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        settingsMenu.getView().dispatchEvent(new WindowEvent(settingsMenu.getView(), WindowEvent.WINDOW_CLOSING));
                    }
                }, settingsInteractionPanel)
        );
    }

    private void updateColorsPanel(Box colorsPanel) {
        HashMap<String, Color> colors = paintContext.getColors();

        JLabel title = new JLabel("Colors");
        title.setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
        title.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        colorsPanel.add(title);
        colorsPanel.add(Box.createVerticalStrut(5));

        for (final String key : colors.keySet()) {
            Color c = paintContext.getColor(key);
            ActionListener delListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paintContext.removeColor(key);
                    validatePanels();
                }
            };
            colorsPanel.add(UiCreationHelper.createValueValuePanel(
                    new JLabel(key + ": "),
                    new JLabel(c.getRed() + " - " + c.getGreen() + " - " + c.getBlue() + " - " + c.getAlpha()),
                    delListener
            ));
        }
    }

    private void updateVarsPanel(Box varsPanel) {
        HashMap<String, Integer> intVars = paintContext.getIntVars();
        HashMap<String, Float> floatVars = paintContext.getFloatVars();
        int numVars = intVars.size() + paintContext.getFloatVars().size();

        JLabel title = new JLabel("Variables");
        title.setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
        title.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        varsPanel.add(title);
        varsPanel.add(Box.createVerticalStrut(5));

        for (final String key : intVars.keySet()) {
            ActionListener delListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paintContext.removeIntVar(key);
                    validatePanels();
                }
            };
            varsPanel.add(UiCreationHelper
                    .createValueValuePanel(new JLabel(key + ": "), new JLabel("" + paintContext.getIntVar(key)), delListener));
        }
        for (final String key : floatVars.keySet()) {
            ActionListener delListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paintContext.removeFloatVar(key);
                    validatePanels();
                }
            };
            varsPanel.add(UiCreationHelper
                    .createValueValuePanel(new JLabel(key + ": "), new JLabel("" + paintContext.getFloatVar(key)), delListener));
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
        updateVarsPanel(varsPanel);

        colorsPanel.removeAll();
        updateColorsPanel(RootSettingsMenu.this.colorsPanel);

        getView().validate();
    }
}
