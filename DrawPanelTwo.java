/* File: DrawPanelTwo.java
 * Owner: 
 * Last Changed: 5/24/2018
 * Description: Creates the panel that the icons are dragged
 * to. Listeners were implemented to allow dropping the icons
 * into the panel and detecting mouse clicks to draw lines.
 */
 
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class DrawPanelTwo extends JPanel {
	private int count = 1;
	private int shapesClicked = 0;
	private LineListener boardListen;
	private ArrayList<Line> lines = new ArrayList<Line>();
	private JPanel container;
	
	public DrawPanelTwo() {
		// Container that holds actual board
		container = new JPanel();
		container.setBorder(new LineBorder(Color.BLACK));
		container.setOpaque(true);
		container.setBackground(Color.WHITE);
		container.setPreferredSize(new Dimension(850, 375));
		container.setLayout(null);
		
		// Margin settings
		setBorder(new EmptyBorder(0, 10, 10, 10));
		setPreferredSize(new Dimension(100, 400));
		
		// Handler for Drag and Drop operation that allows for transferring
		// data between various components
		TransferHandler transfer = new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
				// Check if the operation is a drop and if it is an image
                if (!support.isDrop() || !support.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean importData(TransferSupport support) {
				// Checks if can import icon
                if (!canImport(support)) {
                    return false;
                }
				// Transfers icon
                Transferable tansferable = support.getTransferable();
				Icon icon;
				try {
					icon = (Icon) tansferable.getTransferData(DataFlavor.imageFlavor);
				} catch (Exception e) {
					return false;
				}
				if (icon == null) {
					return false;
				}
				// Create new JLabel with icon on board
                add(new JLabel(icon));
                return true;
            }
        };

		// Attach listeners for drag and drop operation and drawing lines operation
        setTransferHandler(transfer);
		new BoardListener(container);
		boardListen = new LineListener(container);
		container.addMouseListener(boardListen);
		
		add(container);
	}
	
	class BoardListener extends DropTargetAdapter {
		private DropTarget dropTarget;
		private JPanel p;
		
		// Sets board panel as a drop target which means objects can be dropped into it
		public BoardListener(JPanel panel) {
			p = panel;
			dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
		}

		@Override
		public void drop(DropTargetDropEvent event) {
			// Get component to drop into, its location, and the icon
			DropTarget target = (DropTarget) event.getSource();
			Component ca = (Component) target.getComponent();
			Point dropPoint = ca.getMousePosition();
			Transferable tr = event.getTransferable();
			Icon icon;
			try {
				icon = (Icon) tr.getTransferData(DataFlavor.imageFlavor);
				// Create a new JLabel to transfer the object onto the panel
				if (icon != null) {
					JLabel image = new JLabel(icon);
					image.setSize(image.getPreferredSize());
					// Places the icon's center where the mouse cursor is
					image.setLocation((int) dropPoint.getX() - (image.getWidth() / 2), (int) dropPoint.getY() - (image.getHeight() / 2));
					// Attach listener for drawing lines
					image.addMouseListener(boardListen);
					// Adds icon to board
					p.add(image);
					p.revalidate();
					p.repaint();
					event.dropComplete(true);
				}
			} catch (Exception e) {
				event.rejectDrop();
			}
		}
		
	}
	
	class LineListener extends MouseAdapter {
		private JPanel p;
		private int x1, x2, y1, y2;
		
		public LineListener(JPanel panel) {
			p = panel;
		}
		
		@Override
		public void mousePressed(MouseEvent event) {
			// Check which click it is
			if (count == 1) {
				// Get coordinates of first click
				if (event.getSource() instanceof JLabel) {
					x1 = (int) SwingUtilities.convertPoint((Component) event.getSource(), event.getX(), event.getY(), p).getX();
					y1 = (int) SwingUtilities.convertPoint((Component) event.getSource(), event.getX(), event.getY(), p).getY();
					shapesClicked++;
				}
			} else {
				// Get coordinates of second click
				if (event.getSource() instanceof JLabel) {
					x2 = (int) SwingUtilities.convertPoint((Component) event.getSource(), event.getX(), event.getY(), p).getX();
					y2 = (int) SwingUtilities.convertPoint((Component) event.getSource(), event.getX(), event.getY(), p).getY();
					shapesClicked++;
				}
				if (shapesClicked == 2) {
					// Create line and add it to Line ArrayList
					Line line = new Line(x1, y1, x2, y2);
					lines.add(line);
					// Create a panel that draws all the lines
					JPanel linePanel = new JPanel() {
						@Override
						public void paintComponent(Graphics g) {
							super.paintComponent(g);
							g.setColor(Color.BLACK);
							// Loop through Line ArrayList and draw all the lines
							for (Line line : lines) {
								g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
							}
						}
					};
					// Set line panel options
					linePanel.setBounds(0, 0, 850, 375);
					linePanel.setOpaque(false);
					// Add line panel to board panel
					p.add(linePanel);
					shapesClicked = 0;
				}
				// Update board panel
				p.revalidate();
				p.repaint();
				count = 0;
			}
			count++;
		}
	}
	
	// Line Object
	class Line {
		private int x1, x2, y1, y2;
		public Line(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
		}
		
		public int getX1() {
			return x1;
		}
		
		public int getX2() {
			return x2;
		}
		
		public int getY1() {
			return y1;
		}
		
		public int getY2() {
			return y2;
		}
	}
}