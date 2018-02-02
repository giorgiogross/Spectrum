# Spectrum
Visual interface for creation of generative art with the Processing framework

![Gif showcasing app..](https://github.com/ordsen/Spectrum/blob/master/showcase/showcase1.gif)

### How does it work?

This app allows creating renderings with the processing framework without writing any code. 
The main idea here is that components that will be rendered / animated follow certain cutomizable rules. 
Rules are attached to each other through a tree structure. As the user builds that tree structure (visually)
he specifies each rule as a tree node. The tree edges describe the control flow starting at the root node.
When rendering, each rule is executed sequentially just like the order in the tree implies. 
Rendering happens once per frame for each root node.

The root node specifies the context of the rendered component. This includes available colors, variables and a cursor. 
Existing rules are "Draw Elipse" and "Update Variable". Those rules accept parameters like "width" for "Draw Ellipse" or
"function" for "Update Variable". Basically, you can just type in the variables available through the root node and thus modify
them each frame through your own custom function.

If you try it out **press 'm' to show/hide the menu** and 'e' to expoprt your artwork.

### How to run?

**Just-wanna-check-it-out-way (just to run the app)**<br>
Go to the [releases](https://github.com/ordsen/Spectrum/releases) and download the latest jar. Launch it by double clicking.

**Do-it-yourself-way (if you want to compile the app on your own/modify code/etc)**<br>
This project relies on the [Processing framework](https://processing.org).
You need to download the processing app first and then add its *Processing.app/Contents/Java/core/library/core.jar* as a dependency to your Java project (e.g. through your IDE, more information [here](https://www.processing.org/tutorials/eclipse/)).
Once you've done that you should be able to execute the app through your IDE.

<hr>

### Thats nice! But..?
This is not a ready to use tool yet (in fact, it contains just the basic code structure and basic demo functionalities) 
and I had to lay down work on this project for now. Feel free to fork, modify or contribute.

### Future features (I probably won't have time for :/   )
 * Adding more rules like "Create new root node", "If-Else", "Draw Line", "Draw Rect", ...
 * Allow building loops into the tree
 * Add option to update / modify the background during rendering
