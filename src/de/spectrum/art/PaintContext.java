package de.spectrum.art;

import de.spectrum.art.toolbox.Cursor;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Contains all variables, cursors, colors etc of a root node which will be visible to child nodes. Can be passed from a
 * parent root node to a sub root node to e.g. use a different center location for painting.
 */
public class PaintContext {
    // basic variables
    public static final String VAR_X_STAT = "x_stat";
    public static final String VAR_Y_STAT = "y_stat";
    public static final String VAR_X_GLOB = "x_glob";
    public static final String VAR_Y_GLOB = "y_glob";
    public static final String VAR_X_LOC = "x_loc";
    public static final String VAR_Y_LOC = "y_loc";
    public static final String COLOR_BLACK = "cl_black";
    public static final String COLOR_WHITE = "cl_white";

    private Cursor cursor;
    private LinkedHashMap<String, Integer> intVars;
    private LinkedHashMap<String, Float> floatVars;
    private LinkedHashMap<String, Color> colors;

    private RootNode attachedNode;

    public PaintContext(RootNode rootNode, int xBase, int yBase) {
        this.attachedNode = rootNode;
        cursor = new Cursor(xBase, yBase);

        // todo remember to update cursor when x_stat or x_loc is changed through VarUpdateCmd.

        intVars = new LinkedHashMap<>();
        floatVars = new LinkedHashMap<>();
        colors = new LinkedHashMap<>();

        // init static vars
        addIntVar(VAR_X_STAT, getCursor().getxBase());
        addIntVar(VAR_X_GLOB, getCursor().getX());
        addIntVar(VAR_X_LOC, getCursor().getxBase() - getCursor().getX());
        addIntVar(VAR_Y_STAT, getCursor().getyBase());
        addIntVar(VAR_Y_GLOB, getCursor().getY());
        addIntVar(VAR_Y_LOC, getCursor().getyBase() - getCursor().getY());

        addColor(COLOR_BLACK, new Color(0, 0, 0, 255));
        addColor(COLOR_WHITE, new Color(255, 255, 255, 255));
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

        // update root node ui position if it is updated
        if(id.equals(VAR_X_STAT)) {
            attachedNode.updatePosition(i, cursor.getyBase());
            cursor.relocateBase(i, cursor.getyBase());
        }
        if(id.equals(VAR_Y_STAT)) {
            attachedNode.updatePosition(cursor.getxBase(), i);
            cursor.relocateBase(cursor.getxBase(), i);
        }
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
