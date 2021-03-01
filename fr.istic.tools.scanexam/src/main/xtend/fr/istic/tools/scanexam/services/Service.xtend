package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.QuestionZone
import java.awt.image.BufferedImage
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import javafx.embed.swing.SwingFXUtils
import org.apache.pdfbox.rendering.PDFRenderer
import java.util.ArrayList
import org.apache.pdfbox.pdmodel.PDPage

abstract class Service 
{
	/**
	 * Pdf chargé
	 */
	 @Accessors protected PDDocument document;

	/**
	 * Index de la page courante
	 */
	protected int pageIndex 
	
	/**
	 * @return Identifiant de l'examen
	 * @author degas
	 */
	def int getExamId()
	{
		return ExamSingleton.instance.id;
	}
	
	/**@return Nom de l'examen
	 * @author degas
	 */
	def String getExamName()
	{
		return ExamSingleton.instance.name;
	}
	
	
	def getCurrentPdfPage()
	{
		pageToImage(document.pages.get(pageIndex));
	}
	
	def pageToImage(PDPage page)
	{
		val renderer = new PDFRenderer(document);
		val bufferedImage = renderer.renderImageWithDPI(pageIndex, 300, ImageType.RGB);
		bufferedImage
	}
	/**
	 * Change la page courante par la page la suivant si elle existe (ne change rien sinon)
	 */
	def nextPage() 
	{
		if (pageIndex + 1 < document.pages.size) 
		{
			pageIndex++
		}

	}
	/**
	 * Change la page courante par la page la précédent si elle existe (ne change rien sinon)
	 */
	def previousPage() 
	{
		 if (pageIndex > 0) 
		 {
		 	pageIndex--;
		 }
	}


	/**
	 * @return le nombre de page du PDF courant
	 */
	protected def getCurrentPage()
	{
		return ExamSingleton.getPage(pageIndex);
	}
	/** Retourne la zone associée à une question
	 * @param index Index de la question
	 * @author degas
	 */
	def QuestionZone getQuestionZone(int index)
	{
		return ExamSingleton.getQuestion(pageIndex,index).zone
	}
	
	
	def void save(String path)
	
	def void open(String xmiFile)
	
	
	def int getPageNumber() {
		return document.pages.size
	}
	
	/**
	 * @return le numéro de la page courante dans le PDF courant
	 */
	def getCurrentPageNumber() {
		return pageIndex;
	}

}
