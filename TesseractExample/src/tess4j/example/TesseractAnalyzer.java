package tess4j.example;

import java.io.File;
import java.util.*;

import org.marvinproject.image.transform.rotate.Rotate;

import marvin.image.MarvinImageMask;
import marvin.gui.*;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinAttributes;
import marvin.util.MarvinPluginLoader;
import net.sourceforge.tess4j.*;

public class TesseractAnalyzer {
	public TesseractAnalyzer()
	{	}
	
	public String OCRResult(File file)
	{
		String result = "OCR Failed";
		File myFile = file;
		ITesseract instance = new Tesseract();  // JNA Interface Mapping
		try {
            result = instance.doOCR(myFile);
            //String result = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            //String result = "OCR Failed";
        }
		return result;
		
	}
	
	public String OptimizedOCR(File myFile)
	{
		String file = myFile.getName();
		MarvinImage image = MarvinImageIO.loadImage(file);
        //MarvinImagePanel panel = new MarvinImagePanel();
        MarvinImagePlugin imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.transform.rotate.jar");
        //imagePlugin.load();
        MarvinImageMask imageMask = new MarvinImageMask(image.getWidth(),image.getHeight());
        MarvinAttributes attributes = new MarvinAttributes();
        imagePlugin.load();
        MarvinImagePanel tempPanel = new MarvinImagePanel();
        imagePlugin.setImagePanel(tempPanel);
        imagePlugin.getAttributes().set("RotateAngle",0,"rotateTextField","0");
        MarvinImage backupImage = image.clone();
        //MarvinImage image2 = new MarvinImage(image.getWidth(), image.getHeight());
        //MarvinImage image3 = new MarvinImage(image.getWidth(), image.getHeight());
        //imagePlugin.process(backupImage, backupImage);
        imagePlugin.process(image, backupImage, attributes, imageMask, false);
        //rotate(image2,backupImage,0);
        //Rotate newRotation = new Rotate();
        //newRotation.process(image2,image2);
        backupImage.update();
        //System.out.println(image.getHeight());
        MarvinImageIO.saveImage(backupImage, file);
        MarvinImage backup2 = backupImage.clone();
        MarvinImagePlugin noiseReducer = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.restoration.noiseReduction");
        noiseReducer.process(backupImage, backup2);
        MarvinImageIO.saveImage(backup2, file);
        File imageFile2 = new File(file);
        return OCRResult(imageFile2);
	}
}
