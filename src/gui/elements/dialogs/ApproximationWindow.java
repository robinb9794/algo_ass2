package gui.elements.dialogs;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JSlider;

import gui.elements.ApproximationScreen;
import models.ViewModel;

public class ApproximationWindow extends JDialog{	
	private ViewModel viewModel;
	
	public ApproximationScreen screen;
	public JSlider slider;
	
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
	
	public void initScreenAndSlider() {
		this.screen = new ApproximationScreen(viewModel);
		add(BorderLayout.CENTER, screen);
		
		this.slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		add(BorderLayout.SOUTH, slider);
	}
}
