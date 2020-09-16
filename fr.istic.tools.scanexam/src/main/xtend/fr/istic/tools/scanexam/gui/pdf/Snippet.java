package fr.istic.tools.scanexam.gui.pdf;

/*import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.swing.JComponent;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;
*/

public class Snippet { //extends JComponent{
/*	// byte array containing the PDF file content
	private byte[] bytes = null;
	private int pageIndex;


	// some more code

	@Override
	public void paintComponent(Graphics g) {
		int pageindex = 1;
		PDFFile pdfFile;
		try {
			pdfFile = new PDFFile(ByteBuffer.wrap(this.bytes));
			PDFPage page = pdfFile.getPage(pageIndex);
			Paper paper = new Paper();
			int formatOrientation = page.getAspectRatio() > 1 ? PageFormat.LANDSCAPE
								: PageFormat.PORTRAIT;
			if(formatOrientation == PageFormat.LANDSCAPE) {
				paper.setSize(page.getHeight(), page.getWidth());
			}else {
				paper.setSize(page.getWidth(), page.getHeight());
			}				
			PageFormat pageFormat = new PageFormat();
			pageFormat.setPaper(paper);
			pageFormat.setOrientation(formatOrientation);

			Graphics2D g2d = (Graphics2D)g.create();
			Rectangle imgbounds = new Rectangle(0, 0, (int)pageFormat.getWidth(),
							(int)pageFormat.getHeight());
			PDFRenderer renderer = new PDFRenderer(page, g2d, imgbounds, null, Color.WHITE);
			try {
				page.waitForFinish();
			}
			catch (InterruptedException e) {
				// some exception handling
			}
			renderer.run();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
*/
}

