package fr.istic.tools.scanexam.cropping;

import com.google.common.base.Objects;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Tools {
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
  public static BufferedImage scale(final BufferedImage sbi, final int imageType, final int dWidth, final int dHeight, final double fWidth, final double fHeight) {
    BufferedImage dbi = null;
    boolean _notEquals = (!Objects.equal(sbi, null));
    if (_notEquals) {
      BufferedImage _bufferedImage = new BufferedImage(dWidth, dHeight, imageType);
      dbi = _bufferedImage;
      final Graphics2D g = dbi.createGraphics();
      final AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
      g.drawRenderedImage(sbi, at);
    }
    return dbi;
  }
  
  public static LinkedList<BufferedImage> extractImageList(final File file) {
    LinkedList<BufferedImage> images = new LinkedList<BufferedImage>();
    try (ImageInputStream in = new Function0<ImageInputStream>() {
      @Override
      public ImageInputStream apply() {
        try {
          return ImageIO.createImageInputStream(file);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    }.apply()) {
      Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
      boolean _hasNext = readers.hasNext();
      boolean _not = (!_hasNext);
      if (_not) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("No reader for file ");
        _builder.append(file);
        throw new AssertionError(_builder);
      }
      ImageReader reader = readers.next();
      reader.setInput(in);
      InputOutput.<String>println("Start ");
      try {
        int i = 0;
        while (true) {
          {
            InputOutput.<String>println(("Cropping image " + Integer.valueOf(i)));
            int _plusPlus = i++;
            BufferedImage img = reader.read(_plusPlus).getSubimage(0, 0, 2048, 700);
            InputOutput.<String>println(("Scaling image " + Integer.valueOf(i)));
            int _width = img.getWidth();
            int _height = img.getHeight();
            BufferedImage copyOfImage = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_RGB);
            Graphics g = copyOfImage.createGraphics();
            InputOutput.<String>println(("Drawing image " + Integer.valueOf(i)));
            g.drawImage(img, 0, 0, null);
            images.add(copyOfImage);
          }
        }
      } catch (final Throwable _t) {
        if (_t instanceof IndexOutOfBoundsException) {
          InputOutput.<String>println("Done ");
          reader.dispose();
          return images;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return null;
  }
}
