package fr.istic.tools.scanexam.presenter

import java.awt.image.BufferedImage
import java.io.File

/** 
 * Interface des presenters
 * @author Julien Cochet
 */
interface Presenter {
	
	def void create(File file)
	
	def BufferedImage getCurrentPdfPage()
	
}
