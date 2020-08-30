package fr.istic.tools.scanexam.gui.template

import static extension fr.istic.tools.scanexam.utils.ScanExamXtendUtils.*

import java.awt.event.*
import java.awt.image.*
import java.io.*
import javax.imageio.ImageIO
import javax.swing.event.MouseInputAdapter
import java.awt.image.BufferedImage
import java.util.stream.Stream
import java.nio.file.Path
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Optional
import javax.swing.JLabel
import fr.istic.tools.scanexam.Exam
import javax.swing.event.ListSelectionListener
import javax.swing.event.ListSelectionEvent
import javax.swing.JList
import java.awt.Dimension
import java.awt.Rectangle
import java.util.List
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.Graphics
import javax.swing.JPanel
import java.awt.Color
import javax.swing.ImageIcon
import javax.swing.JOptionPane
import javax.swing.JButton
import javax.swing.JTextField
import java.awt.BorderLayout

class QuestionZoneSelectionPanel extends JPanel implements ListSelectionListener{
	package BufferedImage image
	package Dimension size
	package Rectangle clip
	package Rectangle resizePatch
	package float zoom =1;
	package boolean showClip
	
	JLabel label
	
	JLabel etiquette
	
	JList<String> liste
	List<File> imageFiles
	int pageindex = 0
 
	new(Exam exam) {
		imageFiles = convertPdfToPng(exam.filepath).toList
		image = ImageIO.read(imageFiles.get(pageindex))
		size = new Dimension(2048, 2000)
		createClip()
	}  

	
	override protected void paintComponent(Graphics g) {
		super.paintComponent(g)
		var Graphics2D g2 = (g as Graphics2D)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
		
		
		
		g2.drawImage(image, x, y, (image.width/zoom).intValue, (image.height/zoom).intValue,this)
		if(clip === null) createClip()
		g2.setPaint(Color.red)
		g2.draw(clip)
		g2.setPaint(Color.green)
		g2.draw(resizePatch)
		println('''«clip»+«resizePatch»''')
	}


	def private bound(int v, int min, int max) {
		Math.min(Math.max(v,min),max)
	}
	
	def void setClip(int x, int y) {
		// keep clip within raster
		print('''«clip» goes to ''')
		clip.setLocation(bound(x,0,size.width), bound(y,0,size.height))
		println('''«clip» ''')
		updatePatch
		repaint()
	}

	def unzoom() {
		println("zooming")
		zoom=zoom*1.2f
		label.text = "Zoom:"+zoom
		repaint
	}
	def zoom() {
		zoom=zoom*0.8f
		println("zooming")
		label.text = "Zoom:"+zoom
		repaint
	}

	def void resizeClip(int w, int h) {
		// keep clip within raster
		clip.width=bound(w,20,size.width)
		clip.height=bound(h,20,size.height)
		println('''resizing to «w»x«h» => «clip.width»x«clip.height»''')
		updatePatch
		repaint()
	}

	override Dimension getPreferredSize() {
		return size
	}


	def updatePatch() {
		resizePatch = new Rectangle(10, 10)
		resizePatch.x = clip.x+clip.width-10
		resizePatch.y = clip.y+clip.height-10
		
	}
	def private void createClip() {
		clip = new Rectangle(140, 140)
		clip.x = clip.width / 2
		clip.y = clip.height/ 2
		updatePatch
		
	}

	def private void nextPage() {
		pageindex=(pageindex+1)%(imageFiles.size)
		image = ImageIO.read(imageFiles.get(pageindex))
		
	}

	def private void clipImage() {
		var BufferedImage clipped = null
		try {
			var int w = clip.width
			var int h = clip.height
			var int x0 = (getWidth() - size.width) / 2
			var int y0 = (getHeight() - size.height) / 2
			var int x = clip.x - x0
			var int y = clip.y - y0
			clipped = image.getSubimage(x, y, w, h)
		} catch (RasterFormatException rfe) {
			System.out.println('''raster format error: «rfe.getMessage()»''')
			return;
		}

		var JLabel label = new JLabel(new ImageIcon(clipped))
		JOptionPane.showMessageDialog(this, label, "clipped image", JOptionPane.PLAIN_MESSAGE)
	}

	def JPanel getUIPanel() {
		label= new JLabel("Zoom level :"+zoom)
		var JButton clip = new JButton("clip image")
		clip.addActionListener([ActionEvent e|clipImage()])
		var JButton nextPage = new JButton("next page")
		clip.addActionListener([ActionEvent e|nextPage()])
		var JTextField QuestionId= new JTextField("     ")
		var JButton zoom = new JButton("zoom in")
		zoom.addActionListener([ActionEvent e|zoom()])
		var JButton unzoom = new JButton("zoom out")
		unzoom.addActionListener([ActionEvent e|unzoom()])
		
//		val choix = #[" Pierre", " Paul", " Jacques", " Lou", " Marie"];
// 		liste = new JList(choix);
//		etiquette = new JLabel(" ");

// 		liste.addListSelectionListener(this);
	//	add(etiquette, BorderLayout.WEST);
//		add(liste, BorderLayout.EAST);
		setVisible(true);
		var JPanel panel = new JPanel()
		panel.add(nextPage)
		panel.add(clip)
		panel.add(QuestionId)
		panel.add(label)
		panel.add(zoom)
		panel.add(unzoom)
		return panel
	}
	
 	override valueChanged(ListSelectionEvent e) {
		etiquette.setText(liste.getSelectedValue());
	}

}

