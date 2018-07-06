/* File: DrawFrame.java
 * Owner: 
 * Last Changed: 5/24/2018
 * Description: Creates the window frame and adds the two
 * panels to the frame.
 */

import java.awt.*;
import javax.swing.*;

public class DrawFrame {
	public static void main(String[] args) {
		// Create frame object and set layout
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create panel objects
		DrawPanelOne shapePanel = new DrawPanelOne();
		DrawPanelTwo workspacePanel = new DrawPanelTwo();
		
		// Add panels to frame
		frame.add(shapePanel, BorderLayout.PAGE_START);
		frame.add(workspacePanel, BorderLayout.PAGE_END);
		
		// Set frame settings
		frame.setSize(900, 600);
		frame.setVisible(true);
	}
}