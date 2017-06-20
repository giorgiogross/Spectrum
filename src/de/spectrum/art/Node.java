package de.spectrum.art;

import com.sun.istack.internal.Nullable;
import de.spectrum.App;
import de.spectrum.gui.MouseObserver;
import de.spectrum.gui.java.Component;
import de.spectrum.gui.processing.View;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Node of the art rendering graph. The render graph is the linked list which determines when a certain rule will be
 * rendered. Nodes can encapsulate rules, switches, mathematical expressions and more and will be rendered as soon as
 * the currentFrame value of the root node from which this node originated reaches this nodes' renderAtFrame value.
 */
public abstract class Node {
    /**
     * The main window
     */
    protected App context;

    /**
     * Root node. There are all global variables. If this node is the root then this field will be null.
     */
    protected RootNode root;

    private Component menuView;
    private Component settingsView;
    private View processingView;

    private ArrayList<MouseObserver> mouseObservers;

    private boolean isPaused = false;
    private boolean isMarkedAsDeleted = false;

    /**
     * This node will be rendered as soon as the currentFrame value of the root node from which this node originated
     * reaches this nodes' renderAtFrame value. If there is another node after this one with a lower renderAtFrame
     * value it will not be rendered before this node!
     * <p>
     * This value will always be ignored if this is the root node.
     */
    private int renderAtFrame = 0;

    /**
     * Order in which artwork will be rendered, starting at 0.
     */
    private int layer = 0;

    /**
     * Number will be shown in the processing ui to identify the node
     */
    private int id = -1;

    private String label = "Node";

    /**
     * All next nodes after this node in the render graph
     */
    protected ArrayList<Node> ptrNext;

    public Node(RootNode root, App context) {
        this.mouseObservers = new ArrayList<MouseObserver>();
        this.ptrNext = new ArrayList<Node>();
        this.root = root;
        this.context = context;
    }

    /**
     * Called by the render graph. If this node should be rendered render() is called. Afterwards the draw method of all
     * other nodes will be called. If this is the root node these steps will always be executed.
     */
    public void draw(ArrayList<Node> scannedNodes) {
        if (root != null && renderAtFrame < root.getCurrentFrame()) {
            // do not render this node yet
            return;
        }

        render();

        for (Node node : ptrNext) {
            if(scannedNodes.contains(node)) continue;
            scannedNodes.add(node);

            node.draw(scannedNodes);
        }
    }

    /**
     * Render all processing elements of this node
     */
    protected abstract void render();

    /**
     * Adds a new node to the child nodes array
     *
     * @param next the new child node to be added
     */
    public void addNextNode(Node next) {
        this.ptrNext.add(next);
    }

    /**
     * Updates the visibility of this node and of all child nodes
     *
     * @param isVisible the new visibility state
     */
    public void setChildNodeVisibility(boolean isVisible, ArrayList<Node> scannedNodes) {
        // update own visibility
        getProcessingView().setVisible(isVisible);

        // tell child nodes to update their visibility
        for (Node n : ptrNext) {
            if(scannedNodes.contains(n)) continue;
            scannedNodes.add(n);

            // exclude root nodes. Visibility of root nodes should only be changed with a mouse click
            if(n.getRootNode() == null) continue;

            n.setChildNodeVisibility(isVisible, scannedNodes);
        }
    }

    /**
     * Draws the processing ui and tells all child nodes to draw their UI. This also pays respect to the visibility of
     * the UI components. For rendering, the view of each node's UI view will be called.
     */
    public void drawUI(ArrayList<Node> scannedNodes) {
        View procView = getProcessingView();
        procView.draw();

        for (Iterator<Node> iter = ptrNext.iterator(); iter.hasNext(); ) {
            Node n = iter.next();

            if (n.isMarkedAsDeleted()) {
                ptrNext.remove(n);
                return;
            }

            if (n.getProcessingView().isVisible() && this.getProcessingView().isVisible()) {
                View foreignProcView = n.getProcessingView();
                context.stroke(255);
                context.noFill();
                context.line(procView.getX() + procView.getWidth() / 2, procView.getY() + procView.getHeight(),
                        foreignProcView.getX() + foreignProcView.getWidth() / 2, foreignProcView.getY());
                context.fill(255);
                context.rect(foreignProcView.getX() + foreignProcView.getWidth() / 2 - 2, foreignProcView.getY() - 2, 4, 4);
            }

            if(scannedNodes.contains(n)) continue;
            scannedNodes.add(n);

            n.drawUI(scannedNodes);
        }
    }

    @Nullable
    public Component getMenuView() {
        return menuView;
    }

    protected void setMenuView(Component menuView) {
        this.menuView = menuView;
    }

    @Nullable
    public Component getSettingsView() {
        return settingsView;
    }

    protected void setSettingsView(Component settingsView) {
        this.settingsView = settingsView;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        // todo remember to not execute variables manipulation and frame number increasement when paused
        isPaused = paused;
    }

    @Nullable
    public View getProcessingView() {
        return processingView;
    }

    protected void setProcessingView(View processingView) {
        this.processingView = processingView;
    }

    public int getRenderAtFrame() {
        return renderAtFrame;
    }

    public void setRenderAtFrame(int renderAtFrame) {
        this.renderAtFrame = renderAtFrame;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    protected void registerMouseObserver(MouseObserver observer) {
        this.mouseObservers.add(observer);
    }

    public ArrayList<MouseObserver> getMouseObservers(ArrayList<Node> scannedNodes) {
        ArrayList<MouseObserver> allObservers = new ArrayList<MouseObserver>();

        allObservers.addAll(mouseObservers);
        for (Node n : ptrNext) {
            if (scannedNodes.contains(n)) continue;
            scannedNodes.add(n);

            // exclude root node
            if(n.getRootNode() == null) continue;

            // concat all mouse observer arrays
            allObservers.addAll(n.getMouseObservers(scannedNodes));
        }
        return allObservers;
    }

    public boolean isMarkedAsDeleted() {
        return isMarkedAsDeleted;
    }

    /**
     * Re-calculate the location of the child nodes of this view (based on the processing coordinates of the nodes'
     * processing view) and sets the calculated location to the child nodes. Then calls their rearrangeChildNodes()
     * method so that they can update the location of their child nodes accordingly.
     * <p>
     * Warning: Assumes that all child views have the same pixel width as this nodes' processing view!
     * </p>
     */
    public void rearrangeChildNodes(ArrayList<Node> scannedNodes) {
        if (ptrNext.size() == 0) return;

        // if this is the root node we will still have to add it:
        if(!scannedNodes.contains(this)) scannedNodes.add(this);

        int margin = processingView.getWidth() * 2;
        int[] subTreeWidth = new int[ptrNext.size()];
        for (int i = 0; i < ptrNext.size(); i++) {
            subTreeWidth[i] = ptrNext.get(i).getSubTreeWidth(new ArrayList<Node>());
        }

        int x = processingView.getX();
        int y = processingView.getY() + processingView.getWidth() * 2;

        // store all nodes which are marked scanned in the following for loop but still neet to rearrange their child nodes
        ArrayList<Node> unscannedNodes = new ArrayList<Node>();
        // draw the child nodes sequentially
        for (int i = 0; i < ptrNext.size(); i++) {
            Node n = ptrNext.get(i);
            if (scannedNodes.contains(n)) continue;
            scannedNodes.add(n);

            // never rearrange root nodes. Their location can only be changed by dragging & dropping with the mouse
            if(n.getRootNode() == null) continue;

            n.getProcessingView().setX(x);
            n.getProcessingView().setY(y);

            x += subTreeWidth[i] * margin;
            unscannedNodes.add(n);
        }

        for(Node n : unscannedNodes) {
            n.rearrangeChildNodes(scannedNodes);
        }
    }

    /**
     * Searches through the rendering three to find the greates amount of child nodes a node has. Can be used to ensure
     * that sub trees don't overlap.
     *
     * @param scannedNodes all nodes which were already scanned
     * @return width or the new number of child nodes, if it's larger than width
     */
    protected int getSubTreeWidth(ArrayList<Node> scannedNodes) {
        int width = 0;

        if (ptrNext.size() == 0) width = 1;
        else for (Node n : ptrNext) {
            if (scannedNodes.contains(n)) continue;
            scannedNodes.add(n);

            // exclude root node
            if(n.getRootNode() == null) continue;

            width += n.getSubTreeWidth(scannedNodes);
        }

        return width;
    }

    public void delete() {
        // delete this node
        isMarkedAsDeleted = true;

        // delete all child nodes
        for (Node n : ptrNext) {
            // exclude root node..
            if (!n.isMarkedAsDeleted() && n.getRootNode() != null) n.delete();
        }
    }

    public RootNode getRootNode() {
        return root;
    }

    public ArrayList<Node> getAllNodes(ArrayList<Node> scannedNodes) {
        if (scannedNodes.contains(this)) return scannedNodes;
        scannedNodes.add(this);

        for (Node n : ptrNext) {
            scannedNodes = n.getAllNodes(scannedNodes);
        }
        return scannedNodes;
    }

    public void hideUI(ArrayList<Node> scannedNodes) {
        hideCustomUI();

        for(Node n : ptrNext) {
            if(scannedNodes.contains(n)) continue;
            scannedNodes.add(n);

            // exclude root node
            if(n.getRootNode() == null) continue;

            n.hideUI(scannedNodes);
        }
    }

    protected abstract void hideCustomUI();
}
