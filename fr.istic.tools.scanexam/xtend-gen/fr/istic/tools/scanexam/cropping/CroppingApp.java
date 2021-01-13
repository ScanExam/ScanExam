package fr.istic.tools.scanexam.cropping;

import fr.istic.tools.scanexam.cropping.ClipMover;
import fr.istic.tools.scanexam.cropping.CroppingPanel;
import fr.istic.tools.scanexam.cropping.Tools;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("all")
public class CroppingApp {
  public static void main(final String[] args) throws IOException {
    File file = new File("./files/scan/scan_sderrien_2019-12-18-18-20-06.jpg");
    final JFrame frame = new JFrame("Image Cropper");
    frame.setSize(1000, 1000);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    BoxLayout _boxLayout = new BoxLayout(frame, BoxLayout.Y_AXIS);
    frame.setLayout(_boxLayout);
    final JPanel contentPane = new JPanel();
    BoxLayout _boxLayout_1 = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
    contentPane.setLayout(_boxLayout_1);
    frame.setContentPane(contentPane);
    LinkedList<BufferedImage> _extractImageList = Tools.extractImageList(file);
    CroppingPanel test = new CroppingPanel(_extractImageList);
    test.setSize(800, 400);
    ClipMover mover = new ClipMover(test);
    test.addMouseListener(mover);
    test.addMouseMotionListener(mover);
    JScrollPane _jScrollPane = new JScrollPane(test);
    contentPane.add(_jScrollPane);
    contentPane.add(test.getUIPanel(), "South");
    frame.setSize(800, 400);
    frame.setLocation(200, 200);
    frame.setVisible(true);
  }
}
