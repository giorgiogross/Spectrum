package de.spectrum.art;

import de.spectrum.art.toolbox.Cursor;

import java.awt.*;
import java.util.HashMap;

/**
 * Contains all variables, cursors, colors etc of a root node which will be visible to child nodes. Can be passed from a
 * parent root node to a sub root node to e.g. use a different center location for painting.
 */
public class PaintContext {
    private Cursor cursor;
    private HashMap<String, Integer> intVars;
    private HashMap<String, Float> floatVars;
    private HashMap<String, Color> colors;

    public PaintContext (int xBase, int yBase) {
        cursor = new Cursor(xBase, yBase);

        intVars = new HashMap<String, Integer>();
        floatVars = new HashMap<String, Float>();
        colors = new HashMap<String, Color>();
    }

    public Cursor getCursor() {
        return cursor;
    }

    public HashMap<String, Integer> getIntVars() {
        return intVars;
    }

    public HashMap<String, Float> getFloatVars() {
        return floatVars;
    }

    public HashMap<String, Color> getColors() {
        return colors;
    }

    public void addColor(String id, Color c){
        colors.put(id, c);
    }

    public void addIntVar(String id, int i){
        intVars.put(id, i);
    }

    public void addFloatVar(String id, float f){
        floatVars.put(id, f);
    }

    public void removeColor(String id) {
        colors.remove(id);
    }

    public void removeIntVar(String id) {
        intVars.remove(id);
    }

    public void removeFloatVar(String id) {
        floatVars.remove(id);
    }

}
