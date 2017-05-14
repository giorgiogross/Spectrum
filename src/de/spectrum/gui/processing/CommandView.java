package de.spectrum.gui.processing;

import de.spectrum.App;
import de.spectrum.gui.OnFocusChangedListener;
import de.spectrum.gui.java.Component;

/**
 * Created by Giorgio on 13.05.17.
 */
public class CommandView extends View {

    /**
     * View which shows the root of an command object.
     *
     * @param xCenter x position of the center
     * @param yCenter y position of the center
     */
    public CommandView(int xCenter, int yCenter, App context) {
        super(xCenter - 10, yCenter - 10, 20, 20, context);

    }

    @Override
    public void render() {
        context.stroke(255);
        context.noFill();
        context.ellipse(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth(), getHeight());
    }

}
