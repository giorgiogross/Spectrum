package de.spectrum.gui;

import de.spectrum.art.RootNode;

/**
 * Created by Giorgio on 09.05.17.
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
