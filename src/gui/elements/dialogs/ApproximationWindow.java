package gui.elements.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.elements.ApproximationScreen;
import models.ViewModel;

public class ApproximationWindow extends JDialog{	
	private ViewModel viewModel;
	
	public ApproximationScreen screen;
	public JTextField textField;
	public JButton approximateButton;
	
	public ApproximationWindow(ViewModel viewModel) {
		this.viewModel = viewModel;
		setTitle("Approximation");
		setModal(true);
		setLayout(new BorderLayout());
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}
	
	public void initElements() {
		this.screen = new ApproximationScreen(viewModel);
		add(BorderLayout.CENTER, screen);;
		
		JPanel panel = new JPanel(new FlowLayout());
		
		panel.add(new JLabel("Please enter valid percentage: "));
		
		this.textField = new JTextField();
		textField.setPreferredSize(new Dimension(50, 25));
		panel.add(textField);
		
		this.approximateButton = new JButton("Approximate");
		panel.add(approximateButton);
		
		add(BorderLayout.SOUTH, panel);
	}
}
