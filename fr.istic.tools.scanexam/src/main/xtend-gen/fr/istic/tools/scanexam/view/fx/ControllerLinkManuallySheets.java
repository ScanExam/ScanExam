package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.view.fx.FailedPageItemList;
import fr.istic.tools.scanexam.view.fx.PdfManager;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * Controlleur de l'UI qui permet de réassigner des pages à des copies manuellement
 * @author Romain Caruana
 */
@SuppressWarnings("all")
public class ControllerLinkManuallySheets {
  /**
   * VARIABLES
   */
  @FXML
  private ScrollPane listPane;
  
  @FXML
  private ImageView pageImageView;
  
  private ServiceGraduation service;
  
  private int indexCurrentPage;
  
  private List<Integer> failedPages;
  
  private PDDocument document;
  
  private PDFRenderer pdfRenderer;
  
  private FailedPageItemList pageItemList;
  
  /**
   * Initialise la fenêtre
   */
  public void init(final ServiceGraduation serviceGraduation, final PdfManager pdfManager) {
    try {
      this.service = serviceGraduation;
      this.failedPages = List.<Integer>of(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(7));
      this.indexCurrentPage = 0;
      this.document = PDDocument.load(pdfManager.getPdfInputStream());
      PDFRenderer _pDFRenderer = new PDFRenderer(this.document);
      this.pdfRenderer = _pDFRenderer;
      this.updateImageView();
      FailedPageItemList _failedPageItemList = new FailedPageItemList(this);
      this.pageItemList = _failedPageItemList;
      this.listPane.setContent(this.pageItemList);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public List<Integer> getFailedPages() {
    return this.failedPages;
  }
  
  public int getIndexCurrentPage() {
    return this.indexCurrentPage;
  }
  
  public int setIndexCurrentPage(final int i) {
    return this.indexCurrentPage = i;
  }
  
  /**
   * Méthode qui sauvegarde les modifications et quitte la fenêtre
   */
  public void saveAndQuit() {
    InputOutput.<String>println("save and quit");
  }
  
  /**
   * Méthode qui quitte la fenêtre sans sauvegarder
   */
  public void cancelAndQuit(final InputEvent e) {
    InputOutput.<String>println("cancel and quit");
    Object _source = e.getSource();
    final Node source = ((Node) _source);
    Window _window = source.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  /**
   * Méthode qui ignore la page sélectionnée et la retire du champ d'action
   */
  public void ignorePage() {
    InputOutput.<String>println("ignore page");
    List<Integer> temp = new ArrayList<Integer>();
    int _size = this.failedPages.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      if (((i).intValue() != this.indexCurrentPage)) {
        temp.add(this.failedPages.get((i).intValue()));
      }
    }
    int _size_1 = this.failedPages.size();
    boolean _greaterThan = (_size_1 > 1);
    if (_greaterThan) {
      this.failedPages = temp;
    } else {
      ArrayList<Integer> _arrayList = new ArrayList<Integer>();
      this.failedPages = _arrayList;
    }
    int _size_2 = this.failedPages.size();
    boolean _greaterThan_1 = (_size_2 > 0);
    if (_greaterThan_1) {
      InputOutput.<String>println("on continue");
    } else {
      this.indexCurrentPage = (-1);
    }
    this.updateStatement();
  }
  
  /**
   * Méthode qui affiche la page suivante
   */
  public void nextPage() {
    if ((this.indexCurrentPage != (-1))) {
      int _size = this.failedPages.size();
      boolean _greaterThan = (_size > 1);
      if (_greaterThan) {
        int _size_1 = this.failedPages.size();
        int _minus = (_size_1 - 1);
        boolean _lessThan = (this.indexCurrentPage < _minus);
        if (_lessThan) {
          this.indexCurrentPage++;
        } else {
          this.indexCurrentPage = 0;
        }
        InputOutput.<String>println("next");
        this.updateStatement();
      }
    }
  }
  
  /**
   * Méthode qui affiche la page précédente
   */
  public void previousPage() {
    if ((this.indexCurrentPage != (-1))) {
      int _size = this.failedPages.size();
      boolean _greaterThan = (_size > 1);
      if (_greaterThan) {
        if ((this.indexCurrentPage > 0)) {
          this.indexCurrentPage--;
        } else {
          int _size_1 = this.failedPages.size();
          int _minus = (_size_1 - 1);
          this.indexCurrentPage = _minus;
        }
        InputOutput.<String>println("prev");
        this.updateStatement();
      }
    }
  }
  
  /**
   * Méthode qui met à jour l'état courant de la fenêtre
   */
  public void updateStatement() {
    this.pageItemList.updateList();
    this.updateImageView();
  }
  
  /**
   * Affiche l'image de la page
   */
  public void updateImageView() {
    try {
      int _size = this.failedPages.size();
      boolean _greaterEqualsThan = (this.indexCurrentPage >= _size);
      if (_greaterEqualsThan) {
        this.indexCurrentPage = 0;
        this.pageItemList.updateList();
      }
      BufferedImage bim = null;
      if ((this.indexCurrentPage != (-1))) {
        bim = this.pdfRenderer.renderImageWithDPI((this.failedPages.get(this.indexCurrentPage)).intValue(), 300, ImageType.RGB);
      }
      this.pageImageView.setImage(SwingFXUtils.toFXImage(bim, null));
      this.pageImageView.setFitHeight(650);
      this.pageImageView.setFitWidth(500);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
