package gui.elements.buttons;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import interfaces.buttons.ButtonField;
import models.ViewModel;

public abstract class SuperButton extends JButton implements ButtonField, ActionListener{
	protected static ViewModel viewModel;
	
	public SuperButton() {
		setOpaque(true);
		setBackground(Color.WHITE);
		addActionListener(this);
	}
	
	@Override
	public void enableButton(boolean enable) {
		setEnabled(enable);
	}
	
	public static void setViewModel(ViewModel viewModel) {
		SuperButton.viewModel = viewModel;
	}
	
	protected void setIconFromUrl(String url) {
		try {
			BufferedImage icon = ImageIO.read(new URL(url));
			setIcon(new ImageIcon(icon.getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}	
}
