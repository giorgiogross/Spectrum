package de.spectrum.gui.java;

import de.spectrum.App;
import de.spectrum.art.CommandNode;
import de.spectrum.art.Node;
import de.spectrum.art.RootNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Giorgio on 15.05.17.
 */
public class NodeAdder extends Component {
    private Node attachedNode;
    private ArrayList<Node> allChildNodes;
    private ArrayList<RootNode> allRootNodes;

    public NodeAdder(Node attachedNode, App context, JFrame ui) {
        super(context, ui);
        this.attachedNode = attachedNode;

        getView().setLocation(
                attachedNode.getProcessingView().getX() + attachedNode.getProcessingView().getWidth() * 2,
                attachedNode.getProcessingView().getY()
        );

        FlowLayout layout = new FlowLayout();
        getView().setLayout(layout);

        ArrayList<String> possibleNextNodes = new ArrayList<String>();
        possibleNextNodes.add("New Node");
        allChildNodes = new ArrayList<Node>();
        allChildNodes = attachedNode.getRootNode().getAllNodes(allChildNodes);
        allChildNodes.remove(0); // get rid of the root node
        for(Node n : allChildNodes) {
            possibleNextNodes.add("Child " + n.getId());
        }
        allRootNodes = context.getRootNodes();
        for(Node n : allRootNodes) {
            possibleNextNodes.add("Root " + n.getId());
        }

        final JComboBox cbNodeSelect = new JComboBox(possibleNextNodes.toArray());
        getView().add(cbNodeSelect);

        JButton bApply = new JButton("OK");
        bApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selIdx = cbNodeSelect.getSelectedIndex();
                switch (selIdx){
                    case 0:
                        CommandNode cmdNode = new CommandNode(NodeAdder.this.attachedNode.getRootNode(), NodeAdder.this.context);
                        NodeAdder.this.attachedNode.addNextNode(cmdNode);
                        NodeAdder.this.attachedNode.getRootNode().rearrangeChildNodes(new ArrayList<Node>());
                        break;
                    default:
                        Node next = (selIdx - 1 < allChildNodes.size())? allChildNodes.get(selIdx - 1) : allRootNodes.get(selIdx - 1 - allChildNodes.size());
                        NodeAdder.this.attachedNode.addNextNode(next);
                        NodeAdder.this.attachedNode.getRootNode().rearrangeChildNodes(new ArrayList<Node>());
                }

                setFrameVisibility(false);
            }
        });
        getView().add(bApply);
        getView().getRootPane().setDefaultButton(bApply);
    }
}
