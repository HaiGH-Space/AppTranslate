package view;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;


@Getter
@Setter
public class MainApp extends JFrame {
    private boolean isStartTrans = false;
    private Rectangle selectArea;
    private JButton btnSelectArea;
    private JButton btnStartTrans;
    private JTextField txtFrom;
    private JTextField txtTo;
    private JComboBox<String> cbColor;
    private MainApp frame;
    private TranslateApp translateApp;
    private CaptureApp captureApp ;
    public MainApp() {
        frame = this;
        init();
        setClickListener();


        // Bây giờ khi gọi JOptionPane, nó sẽ không hiển thị

    }
    public void init(){
        this.setTitle("Translate G");
        selectArea = new Rectangle();
        translateApp = new TranslateApp(this);
        translateApp.setVisible(false);
        captureApp = new CaptureApp(this);
        captureApp.setVisible(false);
        String[] data = {"black","white"};
        cbColor = new JComboBox<>(data);
        cbColor.setSelectedIndex(1);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnSelectArea = new JButton("Select your area");
        btnStartTrans = new JButton("Start trans");
        btnStartTrans.setEnabled(false);
        txtFrom = new JTextField(8);
        txtFrom.setText("en");
        txtTo = new JTextField(8);
        txtTo.setText("vi");
        this.add(new JLabel("From: "));
        this.add(txtFrom);
        this.add(new JLabel("To: "));
        this.add(txtTo);
        this.add(btnSelectArea);
        this.add(btnStartTrans);
        this.add(new JLabel("Text color: "));
        this.add(cbColor);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 125);
        this.setLocationRelativeTo(null);

        this.setVisible(true);

    }
    public void setClickListener(){

        btnSelectArea.addActionListener(e -> {
            btnStartTrans.setEnabled(false);
            captureApp.setVisible(true);
            frame.setState(JFrame.ICONIFIED);
        });
        btnStartTrans.addActionListener(e -> {
            if (isStartTrans){
                isStartTrans = false;
                btnStartTrans.setText("Start trans");
                translateApp.setVisible(false);
                translateApp.stopTrans();
            }else{
                isStartTrans = true;
                btnStartTrans.setText("Stop trans");
                frame.setState(JFrame.ICONIFIED);
                translateApp.setVisible(true);
                translateApp.setInversionColor(cbColor.getSelectedItem()!="black");
                translateApp.start();
            }

        });
    }

    public void callBack(){
        frame.setState(JFrame.NORMAL);
        btnStartTrans.setEnabled(true);
    }

}
