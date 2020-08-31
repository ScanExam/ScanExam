package fr.istic.tools.scanexam.cropping

import java.awt.*
import java.awt.event.*
import java.awt.image.*
import java.io.*
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.event.MouseInputAdapter

import java.awt.FlowLayout
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.BoxLayout

class CroppingApp {
	def static void main(String[] args) throws IOException {
		
		var File file = new File("./files/scan/scan_sderrien_2019-12-18-18-20-06.jpg")

		val JFrame frame = new JFrame("Image Cropper");
		frame.setSize(1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new BoxLayout(frame,BoxLayout.Y_AXIS));
		
		val JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		
		frame.setContentPane(contentPane);
		
		var CroppingPanel test = new CroppingPanel(Tools.extractImageList(file))
		test.setSize(800, 400)
		var ClipMover mover = new ClipMover(test)
		test.addMouseListener(mover)
		test.addMouseMotionListener(mover)
		contentPane.add(new JScrollPane(test))
		contentPane.add(test.getUIPanel(), "South")
		frame.setSize(800, 400)
		frame.setLocation(200, 200)
		frame.setVisible(true)
	}
}
