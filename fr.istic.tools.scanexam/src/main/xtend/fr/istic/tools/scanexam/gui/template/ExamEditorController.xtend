package fr.istic.tools.scanexam.gui.template

import java.awt.Point
import java.awt.event.MouseEvent
import javax.swing.event.MouseInputAdapter

class ExamEditorController extends MouseInputAdapter {
	package QuestionZoneSelectionPanel cropping
	package Point offset
	package boolean dragging
	package boolean resizing 
	
	enum STATE {IDLE, MOVING, RESIZING}
	
	STATE state;
	
	new(QuestionZoneSelectionPanel c) {
		cropping = c
		offset = new Point()
		dragging = false
		dragging = resizing
	}

	override void mousePressed(MouseEvent e) {
		var Point p = e.getPoint()
		if (cropping.clip.contains(p)) {
			offset.x = p.x - cropping.clip.x
			offset.y = p.y - cropping.clip.y
			dragging = true
			val clip =cropping.clip
			if (p.x> (clip.x +clip.width-10)) {
				if (p.y> (clip.y +clip.height-10)) {
					println("resizing !")
					resizing=true
				}
			}
		}
	}

	override void mouseReleased(MouseEvent e) {
		dragging = false
		resizing = false
	}

	override void mouseDragged(MouseEvent e) {
		if (dragging) {
			var int x = e.getX() - offset.x
			var int y = e.getY() - offset.y
			println('''at  «x»x«y»''')
			if (!resizing) {
				println('''moving to «x»x«y»''')
				cropping.setClip(x, y)
			} else {
				val clip = cropping.clip
				val width = e.getX() - clip.x 
				val height = e.getY() -  clip.y 
				
				cropping.resizeClip(width, height)
			}
			
			
		}
	}
}
