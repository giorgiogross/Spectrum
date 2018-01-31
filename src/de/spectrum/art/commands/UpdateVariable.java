package de.spectrum.art.commands;

import de.spectrum.App;
import de.spectrum.art.ExpressionEvaluationHelper;
import de.spectrum.art.Node;
import de.spectrum.art.OnPaintContextChangedListener;
import de.spectrum.gui.java.UiCreationHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Giorgio Gross <lars.ordsen@gmail.com>
 */
public class UpdateVariable extends Command implements OnPaintContextChangedListener{
    public static final String DESCRIPTION = "Variable Manipulation";

    private Component content;
    private JComboBox varSel;

    private String target = null;
    private String value = null;
    private ArrayList<String> vars;

    public UpdateVariable(App context, Node attachedNode) {
        super(context, attachedNode, DESCRIPTION);
        paintContext.register(this);
    }

    @Override
    public Component getConfigurationPanel() {
        Box panel = UiCreationHelper.createEmptyHorizontalBox();

        varSel = new JComboBox<String>();
        JTextField input = new JTextField();
        panel.add(varSel);
        panel.add(input);

        updateVarsView();

        content = UiCreationHelper.createSettingsContainer(e -> {
            selectVariable(varSel.getSelectedIndex(), input.getText());
            attachedNode.getSettingsView().setFrameVisibility(false);
        }, panel);
        return content;
    }

    private void selectVariable(int selectedIndex, String input) {
        if(selectedIndex == 0) target = null;
        else target = (String) varSel.getItemAt(selectedIndex);
        value = input;
    }

    private void updateVarsView() {
        vars = new ArrayList<>(paintContext.getIntVars().keySet());
        vars.addAll(paintContext.getFloatVars().keySet());
        vars.add(0, "None");

        String oldSelection = (String) varSel.getSelectedItem();
        int oldIndex = varSel.getSelectedIndex();

        varSel.setModel(new DefaultComboBoxModel<>(vars.toArray()));
        if(oldIndex > 0) {
            int idx = vars.indexOf(oldSelection);
            if (idx == -1) {
                // element was deleted, remove selection
                selectVariable(0, null);
            } else {
                // update selected index, no changes needed for cached values...
                varSel.setSelectedIndex(idx);
            }
        } else {
            selectVariable(0, null);
        }
    }

    @Override
    public void execute() {
        if(target == null || value == null || value.isEmpty()) return;

        if(paintContext.getIntVar(target) != null) {
            paintContext.addIntVar(target, (int) ExpressionEvaluationHelper.Evaluate(value, paintContext));
        } else {
            paintContext.addFloatVar(target, (float) ExpressionEvaluationHelper.Evaluate(value, paintContext));
        }
    }

    @Override
    public void onPaintContextChanged() {
        updateVarsView();
    }
}
