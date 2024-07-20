package model;
import java.awt.image.BufferedImage;
import java.io.File ;
import java.util.concurrent.TimeUnit;

import net.sourceforge.tess4j.Tesseract;


public class I2T {
    static Tesseract tesseract = new Tesseract();

    static {
        tesseract.setDatapath("./src/main/resources/tessdata");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);

    }
    public static String I2T(BufferedImage bufferedImage){
        try {
            return tesseract.doOCR(bufferedImage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "No recognized image";
    }
    public static String I2T(String filePath){
        try {
            return tesseract.doOCR(new File(filePath));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "No recognized image";
    }
    public static String I2T(File file){
        try {
            return tesseract.doOCR(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "No recognized image";
    }
}
