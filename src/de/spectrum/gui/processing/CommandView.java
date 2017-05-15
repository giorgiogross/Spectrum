package de.spectrum.gui.processing;

import de.spectrum.App;

/**
 * Created by Giorgio on 13.05.17.
 */
public class CommandView extends View {
    private int commandId = -1;

    /**
     * View which shows the root of an command object.
     *
     * @param xCenter x position of the center
     * @param yCenter y position of the center
     * @param commandId      Node commandId to be displayed
     */
    public CommandView(int xCenter, int yCenter, int commandId, App context) {
        super(xCenter - 10, yCenter - 10, 20, 20, context);
        this.commandId = commandId;
    }

    @Override
    public void render() {
        context.stroke(255);
        context.noFill();
        context.ellipse(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth(), getHeight());

        context.textSize(8);
        context.fill(255,255,0);
        context.text("" + commandId, getX() + 1, getY() + getHeight() / 2 + 4);
    }

}
