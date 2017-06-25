package de.spectrum.art;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Evaluate mathematical expressions.
 */
public class ExpressionEvaluationHelper {

    public static double Evaluate(String expression, PaintContext paintContext) {
        HashMap<String, Integer> intVars = paintContext.getIntVars();
        HashMap<String, Float> floatVars = paintContext.getFloatVars();

        ArrayList<String> vars = paintContext.getIntVars().keySet().stream().filter(expression::contains).collect(Collectors.toCollection(ArrayList::new));
        vars.addAll(paintContext.getFloatVars().keySet().stream().filter(expression::contains).collect(Collectors.toList()));

        ExpressionBuilder eb = new ExpressionBuilder(expression);
        eb.variables(vars.toArray(new String[vars.size()]));
        Expression e = eb.build();
        for(String var : paintContext.getIntVars().keySet()) {
            e.setVariable(var, paintContext.getIntVar(var));
        }
        for(String var : paintContext.getFloatVars().keySet()) {
            e.setVariable(var, paintContext.getFloatVar(var));
        }
        return e.evaluate();
    }

}
