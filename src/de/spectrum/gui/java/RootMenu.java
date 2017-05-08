package de.spectrum.gui.java;

import de.spectrum.art.Node;
import de.spectrum.gui.OnFocusChangedListener;

import javax.swing.*;

/**
 * Created by Giorgio on 08.05.17.
 */
public class RootMenu extends Component implements OnFocusChangedListener {
    private Node attachedNode;

    public RootMenu(JFrame ui, Node attachedNode) {
        super(ui);
        this.attachedNode = attachedNode;
    }

    @Override
    public void onFocusChanged(Component c) {
        if(c == null || !c.equals(attachedNode.getProcessingView())) {
            setFrameVisibility(false);
            return;
        }
        setFrameVisibility(true);
    }
}
