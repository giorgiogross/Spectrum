package de.spectrum.gui.java;

import de.spectrum.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel which shows the main UI controls to control fps, current frame, deletion, recreation, play, pause and related
 * global actions.
 */
public class AppController extends Component {
    private JButton bPlay;
    private JButton bPause;
    private JButton bDelete;
    private JButton bRevert;
    private JButton bFPSUp;
    private JButton bFPSDown;
    private JLabel lFPS;
    private JButton bExport;

    public AppController (App context, JFrame ui) {
        super(context, ui);

        FlowLayout layout = new FlowLayout();
        getView().setLayout(layout);

        bPlay = new JButton("Play");
        bPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.this.context.onPlay();
            }
        });
        getView().add(bPlay);

        bPause = new JButton("Pause");
        bPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.this.context.onPause();
            }
        });
        getView().add(bPause);

        bDelete = new JButton("NEW");
        bDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.this.context.onDelete();
            }
        });
        getView().add(bDelete);

        /*
        bRevert = new JButton("Revert Frame");
        bRevert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.this.context.onRevertOneFrame();
            }
        });
        getView().add(bRevert);
        */

        bFPSUp = new JButton("+");
        bFPSUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.this.context.onSetFPS(AppController.this.context.getFPS() + 1);
            }
        });
        getView().add(bFPSUp);

        lFPS = new JLabel("FPS: " + AppController.this.context.getFPS());
        getView().add(lFPS);

        bFPSDown = new JButton("-");
        bFPSDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.this.context.onSetFPS(AppController.this.context.getFPS() - 1);
            }
        });
        getView().add(bFPSDown);

        bExport = new JButton("EXPORT JPEG");
        bExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppController.this.context.onExport();
            }
        });
        getView().add(bExport);

    }

    public void updateFPSPNumber() {
        lFPS.setText("FPS: " + context.getFPS());
    }
}
