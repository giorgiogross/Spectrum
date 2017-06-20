package de.spectrum.gui.java;

import de.spectrum.App;
import de.spectrum.art.*;
import de.spectrum.art.PaintContext;
import de.spectrum.gui.OnFocusChangedListener;

import javax.swing.*;
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
    private JPanel varsPanel;

    public RootSettingsMenu(App context, final RootNode attachedNode) {
        super(context, context.getSettingsFrame());
        this.context = context;
        this.attachedNode = attachedNode;

        // init static vars
        final de.spectrum.art.PaintContext pc = attachedNode.getPaintContext();
        pc.addIntVar("x_glob", pc.getCursor().getxBase());
        pc.addIntVar("y_glob", pc.getCursor().getyBase());
        pc.addIntVar("x_loc", pc.getCursor().getX());
        pc.addIntVar("y_loc", pc.getCursor().getY());

        final JPanel settingsInteractionPanel = new JPanel();
        settingsInteractionPanel.setLayout(new BoxLayout(settingsInteractionPanel, BoxLayout.Y_AXIS));

        // todo add views to add vars, colors etc.
        /*
        Variables configuration
         */
        varsPanel = new JPanel();
        updateVarsPanel(varsPanel, pc);
        settingsInteractionPanel.add(varsPanel);
        final JTextField varDescription = new JTextField();
        final JTextField varValue = new JTextField();
        settingsInteractionPanel.add(UiCreationHelper
                .createInputFieldInputFieldPanel(varDescription, varValue));
        Box addVarBox = Box.createVerticalBox();
        JButton addVarButton = new JButton("Add");
        addVarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descr = varDescription.getText();
                String val = varValue.getText();
                if(descr.isEmpty() || val.isEmpty()) return;

                if(val.matches("[-]?[0-9]+.[0-9]+")) {
                    Float varVal = Float.parseFloat(val);

                    pc.addFloatVar(descr, varVal);
                } else if(val.matches("[-]?[0-9]+")) {
                    Integer varVal = Integer.parseInt(val);
                    pc.addIntVar(descr, varVal);
                }

                varsPanel.removeAll();
                updateVarsPanel(varsPanel, pc);
                varDescription.setText("");
                varValue.setText("");
                getView().validate();
            }
        });
        // addVarButton.setAlignmentX(java.awt.Component.RIGHT_ALIGNMENT);
        addVarBox.add(Box.createHorizontalGlue());
        addVarBox.add(addVarButton);
        settingsInteractionPanel.add(addVarBox);

        settingsMenu = new NodeSettingsMenu(context, getView(),
                UiCreationHelper.createSettingsContainer(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                }, "ROOT" + attachedNode.getId(), settingsInteractionPanel)
        );
    }

    private void updateVarsPanel(JPanel varsPanel, PaintContext pc) {
        varsPanel.setLayout(new BoxLayout(varsPanel, BoxLayout.PAGE_AXIS));

        HashMap<String, Integer> intVars = pc.getIntVars();
        HashMap<String, Float> floatVars = pc.getFloatVars();
        int numVars = intVars.size() + pc.getFloatVars().size();

        for(String key : intVars.keySet()) {
            varsPanel.add(UiCreationHelper
                    .createValueValuePanel(new JLabel(key + ": "), new JLabel("" + pc.getIntVar(key))));
        }
        for(String key : floatVars.keySet()) {
            varsPanel.add(UiCreationHelper
                    .createValueValuePanel(new JLabel(key + ": "), new JLabel("" + pc.getFloatVar(key))));
        }
    }

    @Override
    public void onFocusChanged(Component c) {
        if(c == null || !c.equals(attachedNode.getProcessingView())) {
            setFrameVisibility(false);
            return;
        }
        setFrameVisibility(true);
    }
}
