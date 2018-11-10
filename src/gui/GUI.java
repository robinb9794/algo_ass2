package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import interfaces.GUIElement;
import interfaces.View;
import models.ViewModel;
import workers.DrawingInteractionInterlayer;

public class GUI extends JFrame implements View{
	private ViewModel viewModel;
	
	private KeyListener keyListener;
	
	public GUI(ViewModel viewModel) {
		super();
		this.viewModel = viewModel;
	}
	
	@Override
	public void init() {
		setTitle(viewModel.getGUITitle());
		setPreferredSize(new Dimension(viewModel.getGUIWidth(), viewModel.getGUIHeight()));
		setLayout(new BorderLayout());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				viewModel.setUserHasClosedGUI(true);
				DrawingInteractionInterlayer.closeColorWindow();
				dispose();
			}
		});
		setResizable(false);
	}
	
	@Override
	public void addElement(String position, GUIElement guiElement) {
		getContentPane().add(position, (JComponent) guiElement);
	}
	
	@Override
	public void setMenuBar(GUIElement menuBar) {
		setJMenuBar((JMenuBar) menuBar);
	}
	
	@Override
	public void packAndShow() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	@Override
	public void reorder() {
		pack();
		validate();
	}
	
	@Override
	public void reloadScreen() {
		repaint();
	}
}
