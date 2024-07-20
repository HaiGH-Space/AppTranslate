package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CaptureApp extends JFrame {
    private MainApp mainApp;
    private JButton btnDone;
    private JButton btnCancel;
    private Point startPoint;
    private Point endPoint;
    private Rectangle rectangle;
    public CaptureApp(MainApp mainApp) {
        this.mainApp = mainApp;
        init();
        addActionListener();
    }
    public void init(){
        this.setTitle("Capture App");
        this.setLayout(new FlowLayout());
        startPoint = new Point();
        endPoint = new Point();
        rectangle = new Rectangle();
        btnDone = new JButton("Done");
        btnCancel = new JButton("Cancel");
        this.add(btnDone);
        this.add(btnCancel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize);
        setBackground(new Color(0, 0, 0, 100));
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    public void addActionListener(){
        btnDone.addActionListener(e -> {
           callbackToMain();
        });
        btnCancel.addActionListener(e -> {
            mainApp.setState(JFrame.NORMAL);
            this.setVisible(false);
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                endPoint.x=e.getX();
                endPoint.y=e.getY();
                calculate();
                repaint();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                startPoint.x=e.getX();
                startPoint.y=e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                endPoint.x=e.getX();
                endPoint.y=e.getY();
                calculate();
                repaint();
            }
        });
    }
    public void callbackToMain(){
        mainApp.setSelectArea(rectangle);
        mainApp.callBack();
        this.setVisible(false);
    }
    public void calculate(){
        int topLeftX = Math.min(startPoint.x, endPoint.x);
        int topLeftY = Math.min(endPoint.y, startPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);
        rectangle.setRect(topLeftX, topLeftY, width, height);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

//    @Override
//    public void paintComponents(Graphics g) {
//        super.paintComponents(g);
//
//    }



}
