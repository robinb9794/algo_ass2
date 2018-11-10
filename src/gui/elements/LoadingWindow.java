package gui.elements;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interfaces.LoadingScreen;

public class LoadingWindow extends JFrame implements LoadingScreen{		
	private String title;
	private String message;
	
	@Override
	public void init() {  
		setTitle(title);
		setDefaultCloseOperation(0);
        setPreferredSize(new Dimension(300, 80)); 
		setDefaultCloseOperation(0);
		JPanel center = new JPanel();
		center.add(new JLabel(message), BorderLayout.CENTER);
        add(center);
        setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	@Override
	public void setValues(String title, String message) {
		this.title = title;
		this.message = message;
	}

	@Override
	public void closeScreen() {
		try {
			Thread.sleep(2000);
			dispose();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}	
}
