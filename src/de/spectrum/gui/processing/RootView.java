package de.spectrum.gui.processing;

import de.spectrum.App;
import de.spectrum.gui.OnFocusChangedListener;
import de.spectrum.gui.java.Component;

/**
 * View which shows the root of an command object.
 */
public class RootView extends View implements OnFocusChangedListener {
    private int rootId = -1;

    /**
     * View which shows the root of an command object.
     *  @param xCenter x position of the center
     * @param yCenter y position of the center
     * @param rootId
     */
    public RootView(int xCenter, int yCenter, int rootId, App context) {
        super(xCenter - 10, yCenter - 10, 20, 20, context);
        this.rootId = rootId;
    }

    @Override
    public void render() {
        context.stroke(255);
        context.noFill();
        context.ellipse(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth(), getHeight());
        // inner circle
        if(isFocused()) context.fill(80);
        else context.noFill();
        context.ellipse(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth() - 4, getHeight() - 4);

        context.textSize(8);
        context.fill(0,255,255);
        context.text("" + rootId, getX() + 3, getY() + getHeight() / 2 + 4);
    }

    @Override
    public void onFocusChanged(Component c) {
        if(c == null || !c.equals(this)) {
            setFocused(false);
            return;
        }
        setFocused(true);
    }
}
