package de.spectrum.gui.processing.buttons;

import de.spectrum.App;
import de.spectrum.gui.processing.View;
import processing.core.PApplet;

/**
 * Created by Giorgio on 07.05.17.
 */
public class DeleteButton extends View {
    public DeleteButton(int x, int y, int width, int height, App context) {
        super(x, y, width, height, context);
    }

    @Override
    public void render() {
        context.stroke(0);
        context.fill(0);
        context.ellipse(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth(), getHeight());
        context.stroke(255);
        context.noFill();
        context.ellipse(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth(), getHeight());
        context.line(getX() + getWidth() / 4, getY() + getWidth() / 4, getX() + 3 * getWidth() / 4, getY() + 3 * getHeight() / 4);
        context.line(getX() + getWidth() / 4, getY() + 3 * getWidth() / 4, getX() + 3 * getWidth() / 4, getY() + getHeight() / 4);
    }
}
