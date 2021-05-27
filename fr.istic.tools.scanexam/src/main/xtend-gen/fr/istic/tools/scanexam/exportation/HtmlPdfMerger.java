package fr.istic.tools.scanexam.exportation;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

/**
 * Ajoute du contenu html à un document pdf
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class HtmlPdfMerger {
  /**
   * Logger
   */
  private static final Logger LOGGER = LogManager.getLogger();
  
  /**
   * Chemin du pdf temporaire construit avec le html
   */
  private static final Path HTML_PDF_PATH = new Function0<Path>() {
    @Override
    public Path apply() {
      try {
        Path _absolutePath = Files.createTempFile("html", "pdf").toAbsolutePath();
        return _absolutePath;
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    }
  }.apply();
  
  /**
   * Assemble un fichier html et un document pdf en un nouveau document pdf
   * @param htmlFile  Fichier html à ajouter au pdf
   * @param pdfFile   Document pdf à ajouter au nouveau pdf
   * @param destPath  Chemin et nom du nouveau document pdf à générer (peut écraser l'ancien pdf)
   * @param htmlFirst Indique si le contenu du fichier html doit entre avant ou après le document pdf
   */
  public static void mergeHtmlFileWithPdf(final File htmlFile, final File pdfFile, final Path destPath, final boolean htmlFirst) {
    HtmlPdfMerger.createPdfFromHtmlFile(htmlFile, HtmlPdfMerger.HTML_PDF_PATH);
    File htmlPdf = HtmlPdfMerger.HTML_PDF_PATH.toFile();
    HtmlPdfMerger.mergeHtmlPdfWithPdf(htmlPdf, pdfFile, destPath, htmlFirst);
    htmlPdf.delete();
  }
  
  /**
   * Assemble du contenu html et un document pdf en un nouveau document pdf
   * @param htmlContent   Contenu html à ajouter au pdf
   * @param resourcesPath Chemin du dossier contenant les ressources (images, CSS, etc.) du html. Laisser null si aucune ressource
   * @param pdfFile       Document pdf à ajouter au nouveau pdf
   * @param destPath      Chemin et nom du nouveau document pdf à générer (peut écraser l'ancien pdf)
   * @param htmlFirst     Indique si le contenu du fichier html doit entre avant ou après le document pdf
   */
  public static void mergeHtmlContentWithPdf(final String htmlContent, final Path resourcesPath, final File pdfFile, final Path destPath, final boolean htmlFirst) {
    HtmlPdfMerger.createPdfFromHtmlContent(htmlContent, resourcesPath, HtmlPdfMerger.HTML_PDF_PATH);
    File htmlPdf = HtmlPdfMerger.HTML_PDF_PATH.toFile();
    HtmlPdfMerger.mergeHtmlPdfWithPdf(htmlPdf, pdfFile, destPath, htmlFirst);
    htmlPdf.delete();
  }
  
  /**
   * Créé un nouveau pdf à partir du contenu d'un fichier html
   * @param htmlFile Fichier html à convertir en pdf
   * @param destPath Chemin du document pdf à générer
   */
  public static void createPdfFromHtmlFile(final File htmlFile, final Path destPath) {
    PdfRendererBuilder builder = new PdfRendererBuilder();
    builder.withFile(htmlFile);
    HtmlPdfMerger.createPdfWithBuilder(builder, destPath);
  }
  
  /**
   * Créé un nouveau pdf à partir de contenu html
   * @param htmlContent   Html à convertir en pdf
   * @param resourcesPath Chemin du dossier contenant les ressources (images, CSS, etc.) du html. Laisser null si aucune ressource
   * @param destPath      Chemin du document pdf à générer
   */
  public static void createPdfFromHtmlContent(final String htmlContent, final Path resourcesPath, final Path destPath) {
    PdfRendererBuilder builder = new PdfRendererBuilder();
    if ((resourcesPath != null)) {
      File resourcesFolder = resourcesPath.toFile();
      builder.withHtmlContent(htmlContent, resourcesFolder.toURI().toString());
    } else {
      builder.withHtmlContent(htmlContent, null);
    }
    HtmlPdfMerger.createPdfWithBuilder(builder, destPath);
  }
  
  /**
   * Créé un nouveau pdf à partir du contenu d'un builder contant un fichier ou contenu html
   * @param builder  PdfRendererBuilder Builder contant déjà un fichier ou contenu html
   * @param destPath Chemin du document pdf à générer
   */
  private static void createPdfWithBuilder(final PdfRendererBuilder builder, final Path destPath) {
    builder.useFastMode();
    try {
      File _file = destPath.toFile();
      FileOutputStream _fileOutputStream = new FileOutputStream(_file);
      builder.toStream(_fileOutputStream);
    } catch (final Throwable _t) {
      if (_t instanceof FileNotFoundException) {
        final FileNotFoundException e = (FileNotFoundException)_t;
        e.printStackTrace();
        HtmlPdfMerger.LOGGER.error("Cannot find destination.");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    try {
      builder.run();
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
        HtmlPdfMerger.LOGGER.error("Cannot build pdf from html.");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Assemble un pdf construit à partir d'un html et un document pdf en un nouveau document pdf
   * @param htmlPdfFile Pdf construit à partir d'un html
   * @param pdfFile     Document pdf à ajouter au nouveau pdf
   * @param destPath    Chemin et nom du nouveau document pdf à générer (peut écraser l'ancien pdf)
   * @param htmlFirst   Indique si le contenu du fichier html doit entre avant ou après le document pdf
   */
  private static void mergeHtmlPdfWithPdf(final File htmlPdfFile, final File pdfFile, final Path destPath, final boolean htmlFirst) {
    if (htmlFirst) {
      HtmlPdfMerger.mergeTwoPdf(htmlPdfFile, pdfFile, destPath);
    } else {
      HtmlPdfMerger.mergeTwoPdf(pdfFile, htmlPdfFile, destPath);
    }
  }
  
  /**
   * Assemble 2 documents pdf en un nouveau document pdf. Les 2 pdf seront assemblé dans cet ordre : pdfFile1 puis pdfFile2.
   * @param pdfFile1 Premier document pdf
   * @param pdfFile2 Second document pdf
   * @param destPath Chemin du document pdf à générer (peut écraser un des deux pdf)
   */
  private static void mergeTwoPdf(final File pdfFile1, final File pdfFile2, final Path destPath) {
    PDFMergerUtility merger = new PDFMergerUtility();
    try {
      merger.addSource(pdfFile1);
    } catch (final Throwable _t) {
      if (_t instanceof FileNotFoundException) {
        final FileNotFoundException e = (FileNotFoundException)_t;
        e.printStackTrace();
        HtmlPdfMerger.LOGGER.error("Cannot find pdfFile1.");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    try {
      merger.addSource(pdfFile2);
    } catch (final Throwable _t) {
      if (_t instanceof FileNotFoundException) {
        final FileNotFoundException e = (FileNotFoundException)_t;
        e.printStackTrace();
        HtmlPdfMerger.LOGGER.error("Cannot find pdfFile2.");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    merger.setDestinationFileName(destPath.toString());
    try {
      merger.mergeDocuments(null);
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
        HtmlPdfMerger.LOGGER.error("Cannot merge pdfFile1 and pdfFile2.");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
}
