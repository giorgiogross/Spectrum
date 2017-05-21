package de.spectrum.art.toolbox;

import java.awt.*;

/**
 * Class to represent the currently used color, position, stroke and fill state.
 */
public class Cursor {
    //inti with default values
    private Color color = new Color(0, 0, 0, 0);

    private int xBase = 0;
    private int yBase = 0;
    private int x = 0;
    private int y = 0;

    private boolean isFill;
    private int strokeWidth;

    /**
     * Creates a new Cursor with the specified center coordinates.
     * @param xBase will be added to x coordinate
     * @param yBase will be added to y coordinate
     * @see #relocateBase(int, int)
     * @see #relocate(int, int)
     */
    public Cursor(int xBase, int yBase) {
        relocateBase(xBase, yBase);
    }

    public void relocateBase(int xBase, int yBase) {
        this.xBase = xBase;
        this.yBase = yBase;
    }

    public void relocate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setFill(boolean isFill) {
        this.isFill = isFill;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x + xBase;
    }

    public int getY() {
        return y + yBase;
    }

    public boolean isFill() {
        return isFill;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }
}
