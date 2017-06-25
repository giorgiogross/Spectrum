package de.spectrum.gui.java;

import de.spectrum.App;
import de.spectrum.art.Node;
import de.spectrum.art.RootNode;
import de.spectrum.gui.OnFocusChangedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Giorgio on 08.05.17.
 */
public class RootMenu extends Component implements OnFocusChangedListener {
    private RootNode attachedNode;

    private JButton bPlay;
    private JButton bRevert;
    private JButton bLayerUp;
    private JButton bLayerDown;
    private JLabel lLayer;

    public RootMenu(App context, final RootNode attachedNode) {
        super(context, context.getRootMenuFrame());
        this.context = context;
        this.attachedNode = attachedNode;
        validateLocation();

        FlowLayout layout = new FlowLayout();
        getView().setLayout(layout);

        bPlay = new JButton("∆");
        bPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RootMenu.this.context.onPlay(attachedNode);
            }
        });
        getView().add(bPlay);

        bRevert = new JButton("«");
        bRevert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RootMenu.this.context.onRevertOneFrame(attachedNode);
            }
        });
        getView().add(bRevert);

        bLayerUp = new JButton("+");
        bLayerUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RootMenu.this.context.onSetLayer(attachedNode, attachedNode.getLayer() + 1);
            }
        });
        getView().add(bLayerUp);

        lLayer = new JLabel("" + attachedNode.getLayer());
        getView().add(lLayer);

        bLayerDown = new JButton("-");
        bLayerDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RootMenu.this.context.onSetLayer(attachedNode, attachedNode.getLayer() - 1);
            }
        });
        getView().add(bLayerDown);

    }

    @Override
    public void validateLocation() {
        getView().setLocation(
                attachedNode.getProcessingView().getX() + attachedNode.getProcessingView().getWidth() / 2 - ui.getWidth() / 2,
                attachedNode.getProcessingView().getY() - ui.getHeight()
        );
    }

    public void updateLayerNumber() {
        lLayer.setText("" + attachedNode.getLayer());
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
