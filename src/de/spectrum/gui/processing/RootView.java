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
        super(xCenter - 50, yCenter - 50, xCenter + 50, yCenter + 50, context);

    }

    @Override
    public void render() {

    }
}
