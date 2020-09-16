package fr.istic.tools.scanexam.gui.template

import java.awt.Graphics
import java.awt.Image
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.JLabel

package class ImageLabel extends JLabel {
	Image _myimage

	new(String text) {
		super(text)
	}

	override void setIcon(Icon icon) {
		super.setIcon(icon)
		if (icon instanceof ImageIcon) {
			_myimage = ((icon as ImageIcon)).getImage()
		}
	}

	override void paint(Graphics g) {
		g.drawImage(_myimage, 0, 0, this.getWidth(), this.getHeight(), null)
	}
}
