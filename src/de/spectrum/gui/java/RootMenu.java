package de.spectrum.gui.java;

import de.spectrum.App;
import de.spectrum.art.Node;
import de.spectrum.gui.OnFocusChangedListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Giorgio on 08.05.17.
 */
public class RootMenu extends Component implements OnFocusChangedListener {
    private Node attachedNode;
    private App context;

    public RootMenu(App context, Node attachedNode) {
        super(context.getRootMenuFrame());
        this.context = context;
        this.attachedNode = attachedNode;

        // todo add proper ui code here

        JButton bPlay = new JButton("PLAY");
        bPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RootMenu.this.context.onPlay();
            }
        });
        getView().add(bPlay);
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
