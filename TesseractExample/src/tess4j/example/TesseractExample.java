package tess4j.example;

import java.io.File;

import org.marvinproject.image.transform.rotate.Rotate;

import marvin.image.MarvinImageMask;
import marvin.gui.*;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinAttributes;
import marvin.util.MarvinPluginLoader;
import net.sourceforge.tess4j.*;

public class TesseractExample {
    public static void main(String[] args) {
    	File testFile = new File("eurotext.bmp");
    	TesseractAnalyzer myTess = new TesseractAnalyzer();
    	String output1 = myTess.OCRResult(testFile);
    	String output2 = myTess.OptimizedOCR(testFile);
    	System.out.println(output1);
    	System.out.println(output2);
        //System.setProperty("jna.library.path", "32".equals(System.getProperty("sun.arch.data.model")) ? "lib/win32-x86" : "lib/win32-x86-64");
    	File imageFile = new File("0430170942.jpg");
        MarvinImage image = MarvinImageIO.loadImage("0430170942.jpg");
        //MarvinImagePanel panel = new MarvinImagePanel();
        MarvinImagePlugin imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.transform.rotate.jar");
        //imagePlugin.load();
        MarvinImageMask imageMask = new MarvinImageMask(image.getWidth(),image.getHeight());
        MarvinAttributes attributes = new MarvinAttributes();
        imagePlugin.load();
        MarvinImagePanel tempPanel = new MarvinImagePanel();
        imagePlugin.setImagePanel(tempPanel);
        imagePlugin.getAttributes().set("RotateAngle",0,"rotateTextField","0");
        //String CLOCKWISE90 = "90";
        //System.out.println(attributes.getValues());
        //for(Object field : imagePlugin.getAttributes().getValues())
        //	System.out.println(field.toString());//imagePlugin.getAttribute(field.toString()));
        //System.out.println(imagePlugin.getAttribute("RotateAngle").toString());
        MarvinImage backupImage = image.clone();
        //MarvinImage image2 = new MarvinImage(image.getWidth(), image.getHeight());
        //MarvinImage image3 = new MarvinImage(image.getWidth(), image.getHeight());
        //imagePlugin.process(backupImage, backupImage);
        imagePlugin.process(image, backupImage, attributes, imageMask, false);
        //rotate(image2,backupImage,0);
        //Rotate newRotation = new Rotate();
        //newRotation.process(image2,image2);
        backupImage.update();
        System.out.println(image.getHeight());
        MarvinImageIO.saveImage(backupImage, "new0430170942.jpg");
        MarvinImage backup2 = backupImage.clone();
        MarvinImagePlugin noiseReducer = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.restoration.noiseReduction");
        noiseReducer.process(backupImage, backup2);
        MarvinImageIO.saveImage(backup2, "new0430170942.jpg");
        File imageFile2 = new File("new0430170942.jpg");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        //ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        //File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
        //instance.setDatapath(tessDataFolder.getParent());

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
            System.out.println("Time for Rotation!");
            result = instance.doOCR(imageFile2);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}