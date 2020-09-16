package fr.istic.tools.scanexam.gui.template

import fr.istic.tools.scanexam.utils.ScanExamXtendFactory
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils
import java.awt.EventQueue
import java.awt.GridLayout
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JTextField

/** 
 * @see https://stackoverflow.com/a/3002830/230513 
 */
package class CreateExamDialog 
{
	def private static void display() {
		var JTextField field1 = new JTextField(" ")
		var JButton button1 = new JButton("select")
		var JButton button2 = new JButton("select")
		val JPanel panel = new JPanel(new GridLayout(0, 1))
		panel.add(new JLabel("Name :"))
		panel.add(field1)
		val jLabel = new JLabel("Template file path :")
		panel.add(jLabel)
		panel.add(button1)
		val jLabel2 = new JLabel("Grades file path :")
		panel.add(jLabel2)
		panel.add(button2)
		val exam = ScanExamXtendFactory.exam("dummy")
		
		button1.addActionListener([ActionEvent e| 
//			exam.filepath=selectFileDialog(panel)
			jLabel.text= "Template file  :"+exam.folderPath
		])
		
		button2.addActionListener([ActionEvent e| 
//			exam.folderPath =selectFileDialog(panel)
			jLabel2.text= "Grades file  :"+exam.filepath
		])

		var int result = JOptionPane.showConfirmDialog(null, panel, "Create a new exam template", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.PLAIN_MESSAGE)
		if (result === JOptionPane.OK_OPTION) {
			System.out.println('''converting «exam.filepath» to png files''')
			val images  =ScanExamXtendUtils.convertPdfToPng(exam.filepath)
			exam.numberOfPages=images.size
			
				
		} else {
			System.out.println("Cancelled")
		}
	}


//	def static String selectFolderDialog(JPanel panel) {
//		val chooser = new JFileChooser(new java.io.File("."));
//		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		val returnVal = chooser.showOpenDialog(panel);
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			System.out.println("You chose to open this folder: " + chooser.getSelectedFile().getName());
//			return chooser.getSelectedFile().getName()
//		}
//		throw new UnsupportedOperationException('''Support not yet implemented''')
//		
//		
//	}

	
	def static void main(String[] args) {
		EventQueue.invokeLater([display()])
		ScanExamXtendUtils.convertPdfToPng("sample_form.pdf");
	}
}
