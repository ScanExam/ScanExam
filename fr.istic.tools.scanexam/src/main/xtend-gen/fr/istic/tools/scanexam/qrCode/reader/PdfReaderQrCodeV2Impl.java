package fr.istic.tools.scanexam.qrCode.reader;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import fr.istic.tools.scanexam.qrCode.QrCodeType;
import fr.istic.tools.scanexam.qrCode.reader.Copie;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeImpl;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

/**
 * Classe lisant la deuxième version des qr codes (avec préfixe)
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class PdfReaderQrCodeV2Impl extends PdfReaderQrCodeImpl {
  /**
   * CONSTRUCTEUR
   */
  public PdfReaderQrCodeV2Impl(final InputStream input, final String docPath, final int nbPages, final Pair<Float, Float> qrPos) {
    super(input, docPath, nbPages, qrPos);
  }
  
  /**
   * Analyse les qr codes, corrige les problèmes de numérisation (ex: à l'envers) et lie les pages aux élèves
   * @param pdDoc Document à analyser
   * @param docPath Chemin du document à analyser
   * @param pdfRenderer Rendu du document à analyser
   * @param startPages Première page à analyser
   * @param endPages Dernière page à analyser
   * @param qrPos Position où devrait ce trouver le qr code
   */
  @Override
  public void readQRCodeImage(final PDDocument pdDoc, final String docPath, final PDFRenderer pdfRenderer, final int startPages, final int endPages, final Pair<Float, Float> qrPos) throws IOException {
    ArrayList<Integer> _arrayList = new ArrayList<Integer>();
    this.pagesMalLues = _arrayList;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(startPages, endPages, true);
    for (final Integer page : _doubleDotLessThan) {
      {
        BufferedImage bim = pdfRenderer.renderImageWithDPI((page).intValue(), 250, ImageType.GRAY);
        final LuminanceSource source = new BufferedImageLuminanceSource(bim);
        HybridBinarizer _hybridBinarizer = new HybridBinarizer(source);
        final BinaryBitmap bitmap = new BinaryBitmap(_hybridBinarizer);
        try {
          int numCopie = (-1);
          int numPageInSubject = (-1);
          String studentName = null;
          final QRCodeMultiReader multiReader = new QRCodeMultiReader();
          final Result[] results = multiReader.decodeMultiple(bitmap);
          for (final Result result : results) {
            boolean _startsWith = result.getText().startsWith(Integer.valueOf(QrCodeType.SHEET_PAGE).toString());
            if (_startsWith) {
              final String[] items = this.sheetPageQrCode(result, bim, pdDoc, (page).intValue());
              int _size = ((List<String>)Conversions.doWrapArray(items)).size();
              int _minus = (_size - 2);
              numCopie = Integer.parseInt(items[_minus]);
              int _size_1 = ((List<String>)Conversions.doWrapArray(items)).size();
              int _minus_1 = (_size_1 - 1);
              numPageInSubject = Integer.parseInt(items[_minus_1]);
            } else {
              boolean _startsWith_1 = result.getText().startsWith(Integer.valueOf(QrCodeType.STUDENT).toString());
              if (_startsWith_1) {
                studentName = this.studentQrCode(result);
              }
            }
          }
          if (((numCopie >= 0) && (numPageInSubject >= 0))) {
            final Copie cop = new Copie(numCopie, (page).intValue(), numPageInSubject, studentName);
            synchronized (this.sheets) {
              this.addCopie(cop);
            }
          } else {
            this.pagesMalLues.add(page);
            this.logger.error(("Cannot read QRCode in page " + page));
          }
        } catch (final Throwable _t) {
          if (_t instanceof ArrayIndexOutOfBoundsException || _t instanceof NotFoundException) {
            final Exception e = (Exception)_t;
            this.pagesMalLues.add(page);
            this.logger.error(("Cannot read QRCode in page " + page), e);
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        } finally {
          this.treatedSheets++;
        }
        bim.flush();
        System.gc();
      }
    }
  }
  
  private String[] sheetPageQrCode(final Result result, final BufferedImage bim, final PDDocument pdDoc, final int page) {
    final Pattern pattern = Pattern.compile("_");
    final String[] items = pattern.split(result.getText());
    final float orientation = this.qrCodeOrientation(result);
    final Pair<Float, Float> position = this.qrCodePosition(result, orientation, bim.getWidth(), bim.getHeight());
    if (((orientation <= (-0.5f)) || (orientation >= 0.5f))) {
      this.rotatePdf(pdDoc, this.docPath, page, orientation);
    }
    if ((((this.qrPos.getKey()).floatValue() >= 0.0f) && ((this.qrPos.getValue()).floatValue() >= 0.0f))) {
      Float _key = this.qrPos.getKey();
      Float _key_1 = position.getKey();
      final float diffX = ((_key).floatValue() - (_key_1).floatValue());
      Float _value = position.getValue();
      Float _value_1 = this.qrPos.getValue();
      final float diffY = ((_value).floatValue() - (_value_1).floatValue());
      if (((((diffX <= (-0.01f)) || (diffX >= 0.01f)) || (diffY <= (-0.01f))) || (diffY >= 0.01f))) {
        this.repositionPdf(pdDoc, this.docPath, page, diffX, diffY);
      }
    }
    return items;
  }
  
  private String studentQrCode(final Result result) {
    final Pattern pattern = Pattern.compile("_");
    final String[] items = pattern.split(result.getText());
    int _size = ((List<String>)Conversions.doWrapArray(items)).size();
    int _minus = (_size - 1);
    return items[_minus];
  }
}
