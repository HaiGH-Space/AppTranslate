package view;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import model.APITranslate;
import model.I2T;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TranslateApp extends JFrame {
    @Setter
    private boolean isInversionColor = false;
    private String text = "Translating";
    private String pathFile = "./src/main/java/screenshot.png";
    private MainApp mainApp;
    private APITranslate apiTranslate;
    private Robot robot;
    private Timer timer ;
    public TranslateApp(MainApp mainApp) {
        setTitle("Translate");
        apiTranslate = new APITranslate();
        this.mainApp=mainApp;
        try{
            robot = new Robot();
        }catch (Exception e){
            robot = null;
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setBackground(new Color(0, 0, 0, 0));
        this.setSize(screenSize);
    }
    private void autoCap(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                BufferedImage image = robot.createScreenCapture(mainApp.getSelectArea());
                if (isInversionColor){
                    for (int x = 0; x < image.getWidth(); x++) {
                        for (int y = 0; y < image.getHeight(); y++) {
                            int pixel = image.getRGB(x, y);
                            int alpha = (pixel >> 24) & 0xff;
                            int red = 255 - ((pixel >> 16) & 0xff);
                            int green = 255 - ((pixel >> 8) & 0xff);
                            int blue = 255 - (pixel & 0xff);
                            image.setRGB(x, y, (alpha << 24) | (red << 16) | (green << 8) | blue);
                        }
                    }
                }
//                File file = new File(pathFile);
//                ImageIO.write(image,"png",file);
//                String text = I2T.I2TNewOCR(file);
                String text = I2T.I2T(image);

                if (!text.isBlank()) {
                    renderText(APITranslate.translate(text));
                }

            }
        },0,400);
    }

    public void start(){
        apiTranslate.config(mainApp.getTxtFrom().getText(), mainApp.getTxtTo().getText());
        autoCap();
    }



    public void renderText(String text) {
        this.text = text;
        this.repaint();
    }
    public void stopTrans(){
        timer.cancel();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        g.drawString(text, x,mainApp.getSelectArea().y - 10);
    }

}
