package de.spectrum.gui.processing;

import de.spectrum.App;
import de.spectrum.gui.MouseObserver;
import de.spectrum.gui.OnFocusChangedListener;
import de.spectrum.gui.java.Component;

import java.util.ArrayList;

/**
 * View inclusive event handler for mouse events to represent a processing UI element.
 */
public abstract class View extends Component implements MouseObserver {
    protected App context;
    private ArrayList<View> children;
    private int width;
    private int height;
    private int x;
    private int y;

    private boolean isVisible = true;

    /**
     * Whether the view currently is focused. The render method can then perform the necessary steps to reveal this to
     * the user
     */
    private boolean isFocused = false;

    /**
     * False if onClick will be called even if another child view was clicked, true when onClick will always be called
     */
    private boolean interceptClickEvents = true;

    private ArrayList<OnClickListener> clickListeners;

    public View(int x, int y, int width, int height, App context) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        clickListeners = new ArrayList<OnClickListener>();
        children = new ArrayList<View>();
    }

    public void draw() {
        if(!isVisible) return;

        render();
        for (View v : children) v.draw();
    }

    /**
     * Draw the UI on the processing canvas
     */
    protected abstract void render();

    /**
     * Adds a new click listener to the listener array. This listener will be notified when the user clicks on this view
     *
     * @param listener
     */
    public void addOnClickListener(OnClickListener listener) {
        this.clickListeners.add(listener);
    }

    /**
     * Adds a new child view to this view. The last child view added will be rendered last (most on top) and will be
     * notified about click events first.
     *
     * @param view the new child view
     */
    public void addView(View view) {
        this.children.add(view);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public boolean isInterceptClickEvents() {
        return interceptClickEvents;
    }

    public void setInterceptClickEvents(boolean interceptClickEvents) {
        this.interceptClickEvents = interceptClickEvents;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public boolean mouseClicked(int x, int y) {
        if (this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y && isVisible) {
            // notify children
            for (int i = children.size() - 1; i >= 0; i--) {
                View child = children.get(i);
                if (child.mouseClicked(x, y) && child.isInterceptClickEvents()) return true;
            }

            // notify listeners
            for (OnClickListener clickListener : clickListeners)
                clickListener.onClick(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseHover(int x, int y) {
        return false;
        // todo
    }

    @Override
    public boolean mouseDragging(int x, int y) {
        return false;
        // todo
    }
}
