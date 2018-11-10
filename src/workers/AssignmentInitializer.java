package workers;
import models.ViewModel;
import workers.GUIManager;

public class AssignmentInitializer {		
	public static void main(String args[]) {
		new AssignmentInitializer().initialize();
	}
	
	private void initialize() {
		ViewModel viewModel = new ViewModel("Assignment 02 - Robin Beyer", 800, 600);
		GUIManager.init(viewModel);
		GUIManager.startWork();
	}
}
