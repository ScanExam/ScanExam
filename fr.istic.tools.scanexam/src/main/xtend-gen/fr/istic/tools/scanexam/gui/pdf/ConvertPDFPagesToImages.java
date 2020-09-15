package fr.istic.tools.scanexam.gui.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("unchecked")
public class ConvertPDFPagesToImages {
  public static void main(final String[] args) {
    try {
      String sourceDir = "./files/pfo_example.pdf";
      String destinationDir = "./files/png/";
      File sourceFile = new File(sourceDir);
      File destinationFile = new File(destinationDir);
      boolean _exists = destinationFile.exists();
      boolean _not = (!_exists);
      if (_not) {
        destinationFile.mkdir();
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Folder Created -> ");
        String _absolutePath = destinationFile.getAbsolutePath();
        _builder.append(_absolutePath);
        System.out.println(_builder);
      }
      boolean _exists_1 = sourceFile.exists();
      if (_exists_1) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("Images copied to Folder: ");
        String _name = destinationFile.getName();
        _builder_1.append(_name);
        System.out.println(_builder_1);
        PDDocument document = PDDocument.load(sourceFile);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        {
          int page = 0;
          int _numberOfPages = document.getNumberOfPages();
          boolean _lessThan = (page < _numberOfPages);
          boolean _while = _lessThan;
          while (_while) {
            {
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("page ");
              _builder_2.append(page);
              _builder_2.append("/");
              int _numberOfPages_1 = document.getNumberOfPages();
              _builder_2.append(_numberOfPages_1);
              System.out.println(_builder_2);
              BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 150, ImageType.GRAY);
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append(destinationDir);
              _builder_3.append("page-");
              _builder_3.append((page + 1));
              _builder_3.append(".png");
              ImageIOUtil.writeImage(bim, _builder_3.toString(), 300);
            }
            page = (page + 1);
            int _numberOfPages_1 = document.getNumberOfPages();
            boolean _lessThan_1 = (page < _numberOfPages_1);
            _while = _lessThan_1;
          }
        }
        document.close();
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("Converted Images are saved at -> ");
        String _absolutePath_1 = destinationFile.getAbsolutePath();
        _builder_2.append(_absolutePath_1);
        System.out.println(_builder_2);
      } else {
        StringConcatenation _builder_3 = new StringConcatenation();
        String _name_1 = sourceFile.getName();
        _builder_3.append(_name_1);
        _builder_3.append(" File not exists");
        System.err.println(_builder_3);
      }
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
}
