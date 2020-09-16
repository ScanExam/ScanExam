package fr.istic.tools.scanexam.cropping

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.Iterator
import java.util.LinkedList
import javax.imageio.ImageIO
import javax.imageio.ImageReader
import javax.imageio.stream.ImageInputStream

class Tools {
	
		/**
	 * scale image
	 * 
	 * @param sbi image to scale
	 * @param imageType type of image
	 * @param dWidth width of destination image
	 * @param dHeight height of destination image
	 * @param fWidth x-factor for transformation / scaling
	 * @param fHeight y-factor for transformation / scaling
	 * @return scaled image
	 */
	static def BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
	   var BufferedImage dbi = null;
	    if(sbi != null) {
	        dbi = new BufferedImage(dWidth, dHeight, imageType);
	        val Graphics2D g = dbi.createGraphics();
	        val AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
	        g.drawRenderedImage(sbi, at);
	    }
	    return dbi;
	}
	  
	static def extractImageList(File file) {
		var LinkedList<BufferedImage> images=new LinkedList() 
		try (var ImageInputStream in=ImageIO.createImageInputStream(file)){
			var Iterator<ImageReader> readers=ImageIO.getImageReaders(in) 
			if (!readers.hasNext()) {
				throw new AssertionError('''No reader for file «file»''')
			}
			var ImageReader reader=readers.next() 
			reader.setInput(in) 
			println("Start ")
			// It's possible to use reader.getNumImages(true) and a for-loop here.
			// However, for many formats, it is more efficient to just read until there's no
			// more images in the stream.
			try {
				var int i=0 
				while (true) {
					println("Cropping image "+i)
					var BufferedImage img = reader.read(i++).getSubimage(0, 0, 2048, 700) // fill in the corners of the desired crop location here
					println("Scaling image "+i)
					//img = scale(img,BufferedImage.TYPE_BYTE_GRAY, 700,230,0.3,0.3)
					var BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(),BufferedImage.TYPE_INT_RGB)
					var Graphics g = copyOfImage.createGraphics()
					println("Drawing image "+i)
					g.drawImage(img, 0, 0, null)
					images.add(copyOfImage) 
				}
			} catch (IndexOutOfBoundsException expected) {
				println("Done ")
				reader.dispose() 
				return images
			}
		} catch (IOException e) {
			e.printStackTrace() 
		}
		
	}
	
}
