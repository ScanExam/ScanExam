package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.box.BoxList;
import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.view.EditorAdapter;
import fr.istic.tools.scanexam.view.swing.AdapterSwingPdfAndBoxPanel;
import fr.istic.tools.scanexam.view.swing.EditorViewSwing;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Controlleur swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class EditorAdapterSwing implements EditorAdapter {
  /**
   * Presenter de la création d'exman
   */
  private EditorPresenter editorPresenter;
  
  /**
   * Vue de la création d'exman
   */
  private EditorViewSwing view;
  
  /**
   * Présentateur du pdf
   */
  private AdapterSwingPdfAndBoxPanel adapterPdfAndBox;
  
  /**
   * Constructeur
   */
  public EditorAdapterSwing() {
    InputStream pdfInput = null;
    BoxList _boxList = new BoxList();
    AdapterSwingPdfAndBoxPanel _adapterSwingPdfAndBoxPanel = new AdapterSwingPdfAndBoxPanel(1280, 720, pdfInput, _boxList);
    this.adapterPdfAndBox = _adapterSwingPdfAndBoxPanel;
    EditorViewSwing _editorViewSwing = new EditorViewSwing(this.adapterPdfAndBox);
    this.view = _editorViewSwing;
    this.addActionListeners();
    this.view.getWindow().setVisible(true);
  }
  
  /**
   * Ajoute les action listeners aux boutons de la vue
   */
  private void addActionListeners() {
    JMenuItem _mnItemSave = this.view.getMnItemSave();
    _mnItemSave.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
      }
    });
    JMenuItem _mnItemLoad = this.view.getMnItemLoad();
    _mnItemLoad.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        try {
          EditorAdapterSwing.this.openFile();
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    });
    JMenuItem _mnItemCreate = this.view.getMnItemCreate();
    _mnItemCreate.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
      }
    });
    JMenuItem _mnItemClose = this.view.getMnItemClose();
    _mnItemClose.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
      }
    });
    JMenuItem _mnItemSession = this.view.getMnItemSession();
    _mnItemSession.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
      }
    });
  }
  
  /**
   * Ouvre un fichier pdf
   */
  public void openFile() throws IOException, ClassNotFoundException {
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf file(.pdf)", "pdf");
    JFileChooser fc = new JFileChooser();
    fc.setDialogTitle("Open your file");
    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    File _file = new File(".");
    fc.setCurrentDirectory(_file);
    fc.setFileFilter(filter);
    int result = fc.showOpenDialog(this.view.getWindow());
    if ((result == JFileChooser.CANCEL_OPTION)) {
    } else {
      if ((result == JFileChooser.APPROVE_OPTION)) {
        File selectedFile = fc.getSelectedFile();
        String path = selectedFile.getAbsolutePath();
        InputStream pdfInput = new FileInputStream(path);
        this.adapterPdfAndBox.setPDF(pdfInput, 0);
      }
    }
  }
  
  @Override
  public void setPresenter(final EditorPresenter presenter) {
    this.editorPresenter = presenter;
  }
}
