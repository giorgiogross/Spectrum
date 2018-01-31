package de.spectrum.art;

import de.spectrum.art.toolbox.Cursor;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Contains all variables, cursors, colors etc of a root node which will be visible to child nodes. Can be passed from a
 * parent root node to a sub root node to e.g. use a different center location for painting.
 *
 * @author Giorgio Gross <lars.ordsen@gmail.com>
 */
public class PaintContext {
    private ArrayList<OnPaintContextChangedListener> listeners = new ArrayList<>();

    // variables available per default
    public static final String VAR_X_ROOT = "x_root";  // static x position of root node
    public static final String VAR_Y_ROOT = "y_root";  // static y position of root node
    public static final String VAR_X_LOC = "x_loc";  // local cursor x position
    public static final String VAR_Y_LOC = "y_loc";  // local cursor y position
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
        intVars.put(VAR_X_ROOT, getCursor().getxBase());
        intVars.put(VAR_X_LOC, 0);
        intVars.put(VAR_Y_ROOT, getCursor().getyBase());
        intVars.put(VAR_Y_LOC, 0);
        notifyPaintContextChanged();

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

    /**
     * Adds a new integer variable with id and value i or overrides an existing one with same id.
     *
     * @param id Identifier for variable
     * @param i  Value
     */
    public void addIntVar(String id, int i) {
        intVars.put(id, i);

        // update root position if necessary if existing static variable was overridden
        if (id.equals(VAR_X_ROOT)) {
            cursor.relocateBase(i, cursor.getyBase());
        }
        if (id.equals(VAR_Y_ROOT)) {
            cursor.relocateBase(cursor.getxBase(), i);
        }
        if (id.equals(VAR_X_LOC)) {
            getCursor().relocate(i, getCursor().getYRaw());
        }
        if (id.equals(VAR_Y_LOC)) {
            getCursor().relocate(getCursor().getXRaw(), i);
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
