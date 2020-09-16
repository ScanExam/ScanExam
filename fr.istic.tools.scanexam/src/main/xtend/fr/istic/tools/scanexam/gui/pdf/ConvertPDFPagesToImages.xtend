package fr.istic.tools.scanexam.gui.pdf

import java.awt.image.BufferedImage
import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.apache.pdfbox.tools.imageio.ImageIOUtil

@SuppressWarnings("unchecked") class ConvertPDFPagesToImages {
	def static void main(String[] args) {
		try {
			var String sourceDir = "./files/pfo_example.pdf"
			// Pdf files are read from this folder
			var String destinationDir = "./files/png/"
			// converted images from pdf document are saved here
			var File sourceFile = new File(sourceDir)
			var File destinationFile = new File(destinationDir)
			if (!destinationFile.exists()) {
				destinationFile.mkdir()
				System.out.println('''Folder Created -> «destinationFile.getAbsolutePath()»''')
			}
			if (sourceFile.exists()) {
				System.out.println('''Images copied to Folder: «destinationFile.getName()»''')
				var PDDocument document = PDDocument.load(sourceFile)
				var PDFRenderer pdfRenderer = new PDFRenderer(document)
				for (var int page = 0; page < document.getNumberOfPages(); {
					page = page + 1
				}) {
					System.out.println('''page «page»/«document.getNumberOfPages»''')
					var BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 150, ImageType.GRAY)
					// suffix in filename will be used as the file format
					ImageIOUtil.writeImage(bim, '''«destinationDir»page-«(page + 1)».png''', 300)
				}
				document.close()
				System.out.println('''Converted Images are saved at -> «destinationFile.getAbsolutePath()»''')
			} else {
				System.err.println('''«sourceFile.getName()» File not exists''')
			}
		} catch (Exception e) {
			e.printStackTrace()
		}

	}
}
