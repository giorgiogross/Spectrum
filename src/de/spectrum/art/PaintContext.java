package de.spectrum.art;

import de.spectrum.art.toolbox.Cursor;

import java.awt.*;
import java.util.HashMap;

/**
 * Contains all variables, cursors, colors etc of a root node which will be visible to child nodes. Can be passed from a
 * parent root node to a sub root node to e.g. use a different center location for painting.
 */
public class PaintContext {
    // basic variables
    public static final String VAR_X_GLOB = "x_glob";
    public static final String VAR_Y_GLOB = "y_glob";
    public static final String VAR_X_LOC = "x_loc";
    public static final String VAR_Y_LOC = "y_loc";
    public static final String COLOR_BLACK = "cl_black";
    public static final String COLOR_WHITE = "cl_white";

    private Cursor cursor;
    private HashMap<String, Integer> intVars;
    private HashMap<String, Float> floatVars;
    private HashMap<String, Color> colors;

    public PaintContext (int xBase, int yBase) {
        cursor = new Cursor(xBase, yBase);

        intVars = new HashMap<String, Integer>();
        floatVars = new HashMap<String, Float>();
        colors = new HashMap<String, Color>();

        // init static vars
        addIntVar(VAR_X_GLOB, getCursor().getxBase());
        addIntVar(VAR_Y_GLOB, getCursor().getyBase());
        addIntVar(VAR_X_LOC, getCursor().getX());
        addIntVar(VAR_Y_LOC, getCursor().getY());
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

    public Integer getIntVar(String id) {
        return intVars.get(id);
    }

    public Float getFloatVar(String id) {
        return floatVars.get(id);
    }

    public Color getColor(String id) {
        return colors.get(id);
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
