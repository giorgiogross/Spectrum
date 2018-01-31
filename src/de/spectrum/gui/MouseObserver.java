package de.spectrum.gui;

/**
 * Observer wchich is notified about mouse events
 *
 * @author Giorgio Gross <lars.ordsen@gmail.com>
 */
public interface MouseObserver {

    /**
     * Mouse was clicked
     * @param x     x position of click
     * @param y     y position of click
     * @return  true if the event is intercepted
     */
    boolean mouseClicked(int x, int y);
    boolean mouseHover(int x, int y);
    boolean mouseDragging(int x, int y);
}
