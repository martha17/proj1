/* File: DrawPanelOne.java
 * Owner: 
 * Last Changed: 5/24/2018
 * Description: Creates the panel that holds the icons
 * and implements a listener to allow for the drag operation.
 */
 
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.File;

public class DrawPanelOne extends JPanel {
	public DrawPanelOne() {
		String path = "src/";
		File file = new File(path + "circle.png");
		
		// Container that holds the actual shapes
		JPanel container = new JPanel();
		container.setBorder(new LineBorder(Color.BLACK));
		container.setOpaque(true);
		container.setBackground(Color.WHITE);
		container.setPreferredSize(new Dimension(850, 110));
		
		// Margin settings
		setBorder(new EmptyBorder(10, 10, 0, 10));
		setPreferredSize(new Dimension(100, 130));
		
		// Check if image files are in the src folder or in the working directory.
		// This is done to make behavior of the program consistent between compiling
		// and running on an IDE and compiling and running in the terminal.
		if (!(file.exists()))
			path = "";
		
		// Create shapes
		JLabel circle = new JLabel(new ImageIcon(path + "circle.png"));
		JLabel triangle = new JLabel(new ImageIcon(path + "triangle.png"));
		JLabel rectangle = new JLabel(new ImageIcon(path + "rectangle.png"));
		JLabel star = new JLabel(new ImageIcon(path + "star.png"));
		
		// Attach DragGestureListener to shapes
		ImageDragListener listener = new ImageDragListener();
		DragSource circleds = new DragSource();
		circleds.createDefaultDragGestureRecognizer(circle, DnDConstants.ACTION_COPY_OR_MOVE, listener);
		DragSource triangleds = new DragSource();
		triangleds.createDefaultDragGestureRecognizer(triangle, DnDConstants.ACTION_COPY_OR_MOVE, listener);
		DragSource rectangleds = new DragSource();
		rectangleds.createDefaultDragGestureRecognizer(rectangle, DnDConstants.ACTION_COPY_OR_MOVE, listener);
		DragSource stards = new DragSource();
		stards.createDefaultDragGestureRecognizer(star, DnDConstants.ACTION_COPY_OR_MOVE, listener);
		
		// Add shapes to container
		container.add(circle);
		container.add(triangle);
		container.add(rectangle);
		container.add(star);
		
		add(container);
	}
	
	// DragGestureListener
	class ImageDragListener implements DragGestureListener {
		public void dragGestureRecognized(DragGestureEvent dge) {
			// Get the shape and its icon
			JLabel shape = (JLabel) dge.getComponent();
			final Icon icon = shape.getIcon();
			
			// Make Transferable object to transfer image to another panel
			Transferable transferable = new Transferable() {
				@Override
				public DataFlavor[] getTransferDataFlavors() {
					return new DataFlavor[]{DataFlavor.imageFlavor};
				}

				@Override
				public boolean isDataFlavorSupported(DataFlavor flavor) {
					if (!isDataFlavorSupported(flavor)) {
						return false;
					}
					return true;
				}

				@Override
				public Object getTransferData(DataFlavor flavor) {
					return icon;
				}
			};
		
			dge.startDrag(null, transferable);
		}
	}
}