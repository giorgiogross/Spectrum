package de.spectrum.gui.processing.buttons;

import de.spectrum.App;
import de.spectrum.gui.processing.View;
import processing.core.PApplet;

/**
 * Created by Giorgio on 07.05.17.
 */
public class PlusButton extends View {

    public PlusButton(int x, int y, int width, int height, App context) {
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
        context.line(getX() + getWidth() / 2, getY(), getX() + getWidth() / 2, getY() + getHeight());
        context.line(getX(), getY() + getHeight() / 2, getX() + getWidth(), getY() + getHeight() / 2);
    }
}
