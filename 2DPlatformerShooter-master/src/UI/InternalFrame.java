package UI;

import javax.swing.*;

/* Used by InternalFrameDemo.java. */
// Understood from https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
// Represents a class that makes a internal frame
public class InternalFrame extends JInternalFrame {
    static int openFrameCount = 0;
    static final int xOffset = 500;
    static final int yOffset = 250;

    //EFFECTS: creates a internal frame with the resizable, closable, maximizable, and iconifiable set as true and
    // size as wished
    public InternalFrame() {
        super("Document #" + (++openFrameCount),
                true,
                true,
                true,
                true);
        setSize(300,100);
        setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
    }
}
