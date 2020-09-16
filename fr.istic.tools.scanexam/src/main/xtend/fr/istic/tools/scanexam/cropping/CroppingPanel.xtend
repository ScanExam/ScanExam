package fr.istic.tools.scanexam.cropping

import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.RenderingHints
import java.awt.event.ActionEvent
import java.awt.image.BufferedImage
import java.awt.image.RasterFormatException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Optional
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel

class CroppingPanel extends JPanel {
	package BufferedImage image
	package Dimension size
	package Rectangle clip
	package Rectangle resizePatch
	package float zoom =1;
	package boolean showClip
	
	Iterable<BufferedImage> images
 
	new(Iterable<BufferedImage> images) {
		this.images=images;
		this.image = images.get(0)
		size = new Dimension(2048, 2000)
		showClip = false
		createClip()
	}  

	def Optional<String> getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	      .filter[it.contains(".")]
	      .map[it.substring(filename.lastIndexOf(".") + 1)];
	}
	
	def filewalk () {
		val walk = Files.walk(Paths.get("./"))

		walk.filter[Files::isRegularFile(it)].map[it.toString]
	}
	
	override protected void paintComponent(Graphics g) {
		super.paintComponent(g)
		var Graphics2D g2 = (g as Graphics2D)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
		
		
		var int x = (getWidth() - size.width) / 2
		var int y = (getHeight() - size.height) / 2
		
		g2.drawImage(image, x, y, (image.width/zoom).intValue, (image.height/zoom).intValue,this)
		if(clip === null) createClip()
		g2.setPaint(Color.red)
		g2.draw(clip)
		g2.setPaint(Color.green)
		g2.draw(resizePatch)
		println('''«clip»+«resizePatch»''')
	}


	def bound(int v, int min, int max) {
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
		repaint
	}
	def zoom() {
		zoom=zoom*0.8f
		println("zooming")
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
		val JCheckBox clipBox = new JCheckBox("show clip", showClip)
		clipBox.addActionListener([ActionEvent e|showClip = clipBox.isSelected() repaint()])
		var JButton clip = new JButton("clip image")
		clip.addActionListener([ActionEvent e|clipImage()])
		var JButton zoom = new JButton("zoom")
		zoom.addActionListener([ActionEvent e|zoom()])
		var JButton unzoom = new JButton("unzoom")
		unzoom.addActionListener([ActionEvent e|unzoom()])
		var JPanel panel = new JPanel()
		panel.add(clipBox)
		panel.add(clip)
		panel.add(zoom)
		panel.add(unzoom)
		return panel
	}

}

