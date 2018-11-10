package workers.actions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import workers.ImageContainerManager;
import workers.SuperUserInteractionHandler;

public class SaveActionHandler extends SuperUserInteractionHandler{
	public static void handle() {
		BufferedImage imageFromTargetArray = getImageFromIntArray();
		File tmp = createTmpFile(imageFromTargetArray);
		ImageContainerManager.handleImage(tmp);
		ImageContainerManager.reloadImageBar();
		if(userIsMorphing())
			ImageContainerManager.blockImageBar();
		deleteTmpFile(tmp);
		showInfoDialog("Success!", "Image was successfully added to the bar.");
	}
	
	private static BufferedImage getImageFromIntArray() {
		int screenWidth = viewModel.getScreenWidth();
		int screenHeight = viewModel.getScreenHeight();
		BufferedImage imageFromTargetArray = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		imageFromTargetArray.setRGB(0,  0,  screenWidth, screenHeight, viewModel.getTargetPixels(), 0, screenWidth);
		return imageFromTargetArray;
	}
	
	private static File createTmpFile(BufferedImage imageFromTargetArray) {
		File outputFile = new File("tmp.jpg");
		try {
			ImageIO.write(imageFromTargetArray, "jpg", outputFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return outputFile;
	}
	
	private static void deleteTmpFile(File tmp) {
		try {
			Files.delete(tmp.toPath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
