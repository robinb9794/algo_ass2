package workers;

import interfaces.View;
import models.LoadedImage;
import models.ViewModel;

public class PixelCoordinator {
	private static ViewModel viewModel;
	private static View gui;
	
	public static void init(ViewModel viewModel, View gui) {
		PixelCoordinator.viewModel = viewModel;
		PixelCoordinator.gui = gui;
	}
	
	public static void setSourcePixels(int[] pixels) {
		for(int i = 0; i < viewModel.getScreenWidth(); i++) {
			for(int j = 0; j < viewModel.getScreenHeight(); j++) {
				int index = getPixelIndex(i, j);
				setSingleSourcePixel(index, pixels[index]);
			}
		}
	}
	
	public static void setTargetPixels(int[] pixels) {
		for(int i = 0; i < viewModel.getScreenWidth(); i++) {
			for(int j = 0; j < viewModel.getScreenHeight(); j++) {
				int index = getPixelIndex(i, j);
				setSingleTargetPixel(index, pixels[index]);
			}
		}
	}
	
	public static void setSourceAndTargetPixels(int[] pixels) {
		for(int i = 0; i < viewModel.getScreenWidth(); i++) {
			for(int j = 0; j < viewModel.getScreenHeight(); j++) {
				int index = getPixelIndex(i, j);
				setSingleSourcePixel(index, pixels[index]);
				setSingleTargetPixel(index, pixels[index]);
			}
		}
	}
	
	public static int getSinglePixelFromImage(LoadedImage image, int index) {
		return image.getGrabbedPixels()[index];
	}
	
	public static void resetSourcePixels() {
		for(int i = 0; i < viewModel.getScreenWidth(); i++) {
			for(int j = 0; j < viewModel.getScreenHeight(); j++) {
				int index = getPixelIndex(i, j);
				setSingleSourcePixel(index, 0);
			}
		}
	}
	
	public static void resetTargetPixels() {
		for(int i = 0; i < viewModel.getScreenWidth(); i++) {
			for(int j = 0; j < viewModel.getScreenHeight(); j++) {
				int index = getPixelIndex(i, j);
				setSingleTargetPixel(index, 0);
			}
		}
	}
	
	public static boolean pixelIndexIsInScreenArea(int index) {
		return index >= 0 && index <= viewModel.getScreenWidth() * viewModel.getScreenHeight();
	}
	
	public static int getSingleSourcePixel(int index) {
		return viewModel.getSourcePixels()[index];
	}
	
	public static int getSingleTargetPixel(int index) {
		return viewModel.getTargetPixels()[index];
	}
	
	public static void setSingleSourcePixel(int index, int value) {
		viewModel.getSourcePixels()[index] = value;
	}
	
	public static void setSingleTargetPixel(int index, int value) {
		viewModel.getTargetPixels()[index] = value;
	}	
	
	public static int colorShuffle(int pix1, int pix2, int p) {
		int red = singleShuffle((pix1 >> 16) & 255, (pix2 >> 16) & 255, p);
		int green = singleShuffle((pix1 >> 8) & 255, (pix2 >> 8) & 255, p);
		int blue = singleShuffle((pix1) & 255, (pix2) & 255, p);
		return (255 << 24) | (red << 16) | (green << 8) | blue;
	}
	
	public static int singleShuffle(int part1, int part2, int p) {
		return part1 + (part2 - part1) * p / 100;
	}
	
	public static int getPixelIndex(int i, int j) {
		return j * viewModel.getScreenWidth() + i;
	}
}
