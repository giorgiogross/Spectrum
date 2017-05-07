package de.spectrum.art;

import com.sun.istack.internal.Nullable;
import de.spectrum.App;
import de.spectrum.gui.MouseObserver;
import de.spectrum.gui.java.Component;
import de.spectrum.gui.processing.View;

import java.util.ArrayList;

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

    /**
     * This node will be rendered as soon as the currentFrame value of the root node from which this node originated
     * reaches this nodes' renderAtFrame value. If there is another node after this one with a lower renderAtFrame
     * value it will not be rendered before this node!
     *
     * This value will always be ignored if this is the root node.
     */
    private int renderAtFrame = 0;

    /**
     * Order in which artwork will be rendered, starting at 0.
     */
    private int layer = 0;

    /**
     * All next nodes after this node in the render graph
     */
    private ArrayList<Node> ptrNext;

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
    public void draw() {
        if(root != null && renderAtFrame < root.getCurrentFrame()) {
            // do not render this node yet
            return;
        }

        render();

        for(Node node : ptrNext) node.draw();
    }

    /**
     * Render all processing elements of this node
     */
    protected abstract void render();

    public void addNextNode(Node next) {
        this.ptrNext.add(next);
    }

    @Nullable
    public Component getMenuView () {
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

    protected void registerMouseObserver(MouseObserver observer) {
        this.mouseObservers.add(observer);
    }

    public ArrayList<MouseObserver> getMouseObservers() {
        return mouseObservers;
    }
}