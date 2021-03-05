package fr.istic.tools.scanexam.presenter;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Interface des presenters
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public interface Presenter {
  void create(final File file);
  
  BufferedImage getCurrentPdfPage();
  
  int getCurrentPdfPageNumber();
}
