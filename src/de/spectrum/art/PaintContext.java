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
    private ArrayList<OnPaintContextChangedListener> listeners = new ArrayList<>();

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

    public void addColor(String id, Color c) {
        colors.put(id, c);
        notifyPaintContextChanged();
    }

    public void addIntVar(String id, int i) {
        intVars.put(id, i);

        // update root position if necessary
        if (id.equals(VAR_X_STAT)) {
            cursor.relocateBase(i, cursor.getyBase());
            addIntVar(VAR_X_GLOB, getCursor().getX());
        }
        if (id.equals(VAR_Y_STAT)) {
            cursor.relocateBase(cursor.getxBase(), i);
            addIntVar(VAR_Y_GLOB, getCursor().getY());
        }
        if (id.equals(VAR_X_GLOB)) {
            getCursor().relocate(i, getCursor().getY());
            addIntVar(VAR_X_LOC, getCursor().getxBase() - getCursor().getX());
        }
        if (id.equals(VAR_Y_GLOB)) {
            getCursor().relocate(getCursor().getX(), i);
            addIntVar(VAR_Y_LOC, getCursor().getyBase() - getCursor().getY());
        }
        if (id.equals(VAR_X_LOC)) {
            getCursor().relocate(i, getCursor().getY());
        }
        if (id.equals(VAR_Y_LOC)) {
            getCursor().relocate(i, getCursor().getY());
        }

        notifyPaintContextChanged();
    }

    public void addFloatVar(String id, float f) {
        floatVars.put(id, f);
        notifyPaintContextChanged();
    }

    public void removeColor(String id) {
        colors.remove(id);
        notifyPaintContextChanged();
    }

    public void removeIntVar(String id) {
        intVars.remove(id);
        notifyPaintContextChanged();
    }

    public void removeFloatVar(String id) {
        floatVars.remove(id);
        notifyPaintContextChanged();
    }

    public void register(OnPaintContextChangedListener listener) {
        this.listeners.add(listener);
    }

    public void unregister(OnPaintContextChangedListener listener) {
        this.listeners.remove(listener);
    }

    public void notifyPaintContextChanged() {
        for (OnPaintContextChangedListener listener : listeners)
            if(listener != null) listener.onPaintContextChanged();
    }

}
