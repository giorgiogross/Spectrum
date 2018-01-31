package de.spectrum.gui;

import de.spectrum.art.RootNode;

/**
 * @author Giorgio Gross <lars.ordsen@gmail.com>
 */
public interface OnMenuActionListener {

    void onRevertOneFrame();
    void onRevertOneFrame(RootNode rootNode);

    void onPlay();
    void onPlay(RootNode rootNode);

    void onPause();
    void onPause(RootNode rootNode);

    void onDelete();
    void onDelete(RootNode rootNode);
    void onSetLayer(RootNode rootNode, int layer);

    void onSetFPS(int newFPS);
    void onSetFrameNumber(int num);
    void onExport();
    void onSafe();

    void onEditSettings();
    void onEditSettings(RootNode node);

}
