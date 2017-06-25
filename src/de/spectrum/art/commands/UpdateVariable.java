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
 * Created by Giorgio on 26.06.17.
 */
public class UpdateVariable extends Command implements OnPaintContextChangedListener{
    public static final String DESCRIPTION = "Variable Manipulation";

    private Component content;
    private String target = null;
    private String value = null;

    public UpdateVariable(App context, Node attachedNode) {
        super(context, attachedNode, DESCRIPTION);
        paintContext.register(this);
    }

    @Override
    public Component getConfigurationPanel() {
        Box panel = Box.createHorizontalBox();

        ArrayList<String> vars = new ArrayList<>(paintContext.getIntVars().keySet());
        vars.addAll(paintContext.getFloatVars().keySet());

        JComboBox varSel = new JComboBox(vars.toArray());
        JTextField input = new JTextField();
        panel.add(varSel);
        panel.add(input);

        content = UiCreationHelper.createSettingsContainer(e -> {
            target = (String) varSel.getSelectedItem();
            value = input.getText();
        }, panel);
        return content;
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
        // todo update combo box
    }
}
