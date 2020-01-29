package fr.istic.tools.scanexam.gui

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.RenderingHints
import java.awt.event.*
import java.awt.geom.AffineTransform
import java.awt.image.*
import java.awt.image.BufferedImage
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Iterator
import java.util.List
import java.util.Optional
import java.util.stream.Stream
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.event.MouseInputAdapter
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl
import org.eclipse.emf.common.util.URI
import java.util.Map
import java.util.HashMap
import fr.istic.tools.scanexam.GradingData
import fr.istic.tools.scanexam.utils.ScanExamXtendFactory
import fr.istic.tools.scanexam.utils.ScanExamExcelBackend
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils
import fr.istic.tools.scanexam.gui.ScanExamController

class ScanExamPanel extends JPanel {

	package Dimension size
	package BufferedImage clip
	package Rectangle resizePatch
	package float zoom = 1;
	package boolean showClip
	ScanExamController control;

	new(ScanExamController control) {
		size = new Dimension(2048, 2000)
		this.control = control
		control.loadCurrentPage
		control.setPanel(this)
		updateQuestionZone
		println('''zone «control.currentQuestion.zone»''')

	}

	def updateQuestionZone() {
		clip = control.extractQuestionZone
		clip = scale(clip, BufferedImage.TYPE_BYTE_GRAY, size.width, size.height, 0.5, 0.5)
		repaint
	}

	static def BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth,
		double fHeight) {
		var BufferedImage dbi = null;
		if (sbi !== null) {
			dbi = new BufferedImage(dWidth, dHeight, imageType);
			val Graphics2D g = dbi.createGraphics();
			val AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
			g.drawRenderedImage(sbi, at);
		}
		return dbi;
	}

	override protected void paintComponent(Graphics g) {
		try {
			super.paintComponent(g)
			var Graphics2D g2 = (g as Graphics2D)
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
			g2.drawImage(clip, 10, 40, this)
			val q = control.currentQuestion
			println('''«q.markZone»''')
			//g2.setColor(Color.PINK);
			//g2.fillRect(q.markZone.x,q.markZone.y,80,60)
			//g2.fillRect(30,q.markZone.y+90,80,60)
			//g2.setColor(Color.GREEN);
			//g2.fillRect(30,80,80,60)
			//g2.setColor(Color.BLUE);
			g2.setFont(new Font("Helvetica", Font.BOLD, 20));
	
			g2.drawString("Question:"+ q.label, 40, 30)

			//g2.drawString("grades:"+q.grades.toString, 235, 30)
	
			g2.drawString("index="+control.currentGradeIndex, 490, 30)
	
			g2.drawString("grade:"+control.currentGradeValue, 600, 30)

			g2.drawString("file:"+new File(control.currentQuestionGrade.filename).name, 750, 30)
	
			g2.setFont(new Font("Arial", Font.BOLD, 24));
			var offsety = 80
			var index=0

			g2.setColor(Color.WHITE);
			g2.fillRect(20,70, 80, control.currentQuestion.grades.size*30+30)
			g2.setColor(Color.BLACK);
			
			val start = Math.max(0,control.currentGradeIndex-5).intValue
			val end = Math.min(control.currentGradeIndex+5,control.currentQuestion.grades.size).intValue
			if (control.currentQuestionGrade.validated) {
				
				for (i :start..<end) {
					if (i== control.currentGradeIndex) {
						g2.setColor(Color.RED);
					} else {
						g2.setColor(Color.DARK_GRAY);
					}
					g2.drawString(control.currentQuestion.grades.get(i), 50, offsety)
					offsety += 30
					index++
				}
			} else {
				for (i :start..<end) {
					if (i== control.currentGradeIndex) {
						g2.setColor(Color.ORANGE);
					} else {
						g2.setColor(Color.DARK_GRAY);
					}
					g2.drawString(control.currentQuestion.grades.get(i), 50, offsety)
					offsety += 30
					index++
				}
			}
			g2.setColor(Color.BLACK);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, exception.stackTrace, "InfoBox: ", JOptionPane.ERROR_MESSAGE);
		}
	}

	override Dimension getPreferredSize() {
		return size
	}

}
