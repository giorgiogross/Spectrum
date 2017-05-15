package de.spectrum.gui.processing;

import de.spectrum.App;
import de.spectrum.gui.OnFocusChangedListener;
import de.spectrum.gui.java.Component;

/**
 * Created by Giorgio on 13.05.17.
 */
public class CommandView extends View {
    private int id = -1;

    /**
     * View which shows the root of an command object.
     *
     * @param xCenter x position of the center
     * @param yCenter y position of the center
     * @param id      Node id to be displayed
     */
    public CommandView(int xCenter, int yCenter, int id, App context) {
        super(xCenter - 10, yCenter - 10, 20, 20, context);
        this.id = id;
    }

    @Override
    public void render() {
        context.stroke(255);
        context.noFill();
        context.ellipse(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth(), getHeight());

        context.textSize(8);
        context.fill(255,255,0);
        context.text("" + id, getX() + 1, getY() + getHeight() / 2 + 4);
    }

}
