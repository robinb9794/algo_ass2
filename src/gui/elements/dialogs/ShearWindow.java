package gui.elements.dialogs;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

public class ShearWindow extends JDialog{
	public JButton shearXLeft, shearXRight;
	public JButton shearYUp, shearYDown;
	
	public ShearWindow() {
		setTitle("Shear");
		setModal(true);
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(300, 80));
	}
	
	public void initButtons() {
		BufferedImage buttonIcon;
		try {
			shearXLeft = new JButton();
			buttonIcon = ImageIO.read(new URL("https://image.freepik.com/free-icon/arrow-bold-left-ios-7-interface-symbol_318-34824.jpg"));
			shearXLeft.setIcon(new ImageIcon(buttonIcon.getScaledInstance(16,  16, Image.SCALE_SMOOTH)));
			add(shearXLeft);
			
			shearXRight = new JButton();
			buttonIcon = ImageIO.read(new URL("https://image.freepik.com/free-icon/arrow-bold-right-ios-7-symbol_318-35504.jpg"));
			shearXRight.setIcon(new ImageIcon(buttonIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
			add(shearXRight);
			
			shearYUp = new JButton();
			buttonIcon = ImageIO.read(new URL("https://image.freepik.com/free-icon/up-arrow_318-123025.jpg"));
			shearYUp.setIcon(new ImageIcon(buttonIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
			add(shearYUp);
			
			shearYDown = new JButton();
			buttonIcon = ImageIO.read(new URL("https://image.freepik.com/free-icon/side-down_318-125102.jpg"));
			shearYDown.setIcon(new ImageIcon(buttonIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
			add(shearYDown);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
