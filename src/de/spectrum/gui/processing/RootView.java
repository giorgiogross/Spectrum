package de.spectrum.gui.processing;

import de.spectrum.App;

/**
 * View which shows the root of an command object.
 */
public class RootView extends View {

    /**
     * View which shows the root of an command object.
     *
     * @param xCenter x position of the center
     * @param yCenter y position of the center
     */
    public RootView(int xCenter, int yCenter, App context) {
        super(xCenter - 10, yCenter - 10, 20, 20, context);

    }

    @Override
    public void render() {
        context.stroke(255);
        context.noFill();
        context.ellipse(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth(), getHeight());
        context.ellipse(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth() - 4, getHeight() - 4);
    }
}
