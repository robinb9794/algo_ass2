package gui.elements.menu;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import interfaces.GUIElement;
import interfaces.LoadingScreen;
import models.ViewModel;
import workers.GUIManager;
import workers.ImageContainerManager;

public class ImageChooser extends JFileChooser implements GUIElement{
	private ViewModel viewModel;
	
	public ImageChooser(ViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	public void init() {
		setMultiSelectionEnabled(true);
		setDialogTitle("Please select images:");
		setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg/gif", "jpg", "gif");
		setFileFilter(filter);		
		int returnValue = showOpenDialog(this);
		handleUserAction(returnValue);		
	}
	
	private void handleUserAction(int returnValue) {
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File[] selectedFiles = getSelectedFiles();
			if(alreadyLoadedImages()) {
				addMoreImages(selectedFiles);
			}
			else {
				viewModel.setSelectedFiles(selectedFiles);
				tellGUIManagerToBuild();
			}
		}
	}
	
	private boolean alreadyLoadedImages() {
		return viewModel.getLoadedImages().size() > 0;
	}
	
	private void addMoreImages(File[] selectedFiles) {
		ImageContainerManager.handleFiles(selectedFiles);
	}
	
	private void tellGUIManagerToBuild() {
		GUIManager.initUserInteractionHandler();
		GUIManager.initButtonContainer();
		GUIManager.initScreen();
		GUIManager.initPixelCoordinator();
		GUIManager.initImageContainer();	
	}
}
