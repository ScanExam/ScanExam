package fr.istic.tools.scanexam.gui.template

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
import fr.istic.tools.scanexam.utils.ScanExamXtendFactory
import fr.istic.tools.scanexam.instances.PFOExams
import javax.swing.filechooser.FileNameExtensionFilter
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils
import fr.istic.tools.scanexam.Exam

class QuestionSelectionApp {
	def static frame(Exam exam) {
		
		// "./files/scan/scan_sderrien_2019-12-18-18-20-06.tiff"
		//var File file = new File("./files/scan/Lenna.png")
		val JFrame frame = new JFrame("Trombinoscope ISTIC");
		frame.setSize(1000, 1000);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new BoxLayout(frame,BoxLayout.Y_AXIS));
		
		val JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		
		frame.setContentPane(contentPane);
		
		var QuestionZoneSelectionPanel test = new QuestionZoneSelectionPanel(exam)
		test.setSize(800, 400)
		var ExamEditorController mover = new ExamEditorController(test)
		test.addMouseListener(mover)
		test.addMouseMotionListener(mover)
		contentPane.add(new JScrollPane(test))
		contentPane.add(new JScrollPane(new QuestionViewer))
		contentPane.add(test.getUIPanel(), "South")
		frame.setSize(800, 400)
		frame.setLocation(200, 200)
		frame.setVisible(true)
		frame
	}
	
	def static void main(String[] args) throws IOException {
		var JTextField field1 = new JTextField(" ")
		var JButton button1 = new JButton("select")
		//var JButton button2 = new JButton("select")
		val JPanel panel = new JPanel(new GridLayout(0, 1))
		panel.add(new JLabel("Name :"))
		panel.add(field1)
		val jLabel = new JLabel("Template file path :")
		panel.add(jLabel)
		panel.add(button1)
//		val jLabel2 = new JLabel("Grades file path :")
//		panel.add(jLabel2)
//		panel.add(button2)
		val exam = ScanExamXtendFactory.exam("dummy")
		
		button1.addActionListener([ActionEvent e| 
			exam.filepath=selectFileDialog(panel)
			jLabel.text= "Template file  :"+exam.filepath
		])
		
//		button2.addActionListener([ActionEvent e| 
//			exam.filepath =selectFileDialog(panel)
//			jLabel2.text= "Grades file  :"+exam.filepath
//		])

		var int result = JOptionPane.showConfirmDialog(null, panel, "Create a new exam template", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.PLAIN_MESSAGE)
		if (result === JOptionPane.OK_OPTION) {
			System.out.println('''converting «exam.filepath» to png files''')
//			val images  =ScanExamXtendUtils.convertPdfToPng(exam.filepath)
//			exam.numberOfPages=images.size
			new JDialog(frame(exam))
				
		} else {
			System.out.println("Cancelled")
		}
	}
	
	def static String selectFileDialog(JPanel panel) {
		val chooser = new JFileChooser(new java.io.File("."));
		val filter = new FileNameExtensionFilter("Adobde PDF", "pdf");
		chooser.setFileFilter(filter);
		val returnVal = chooser.showOpenDialog(panel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			return chooser.getSelectedFile().getName()
		}
		""
	}
	
	
}
