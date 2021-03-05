package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.box.BoxList;
import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.view.EditorAdapter;
import fr.istic.tools.scanexam.view.swing.AdapterSwingPdfAndBoxPanel;
import fr.istic.tools.scanexam.view.swing.EditorViewSwing;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

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
    BoxList _boxList = new BoxList();
    AdapterSwingPdfAndBoxPanel _adapterSwingPdfAndBoxPanel = new AdapterSwingPdfAndBoxPanel(1280, 720, this.editorPresenter, _boxList);
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
    JMenuItem _mnItemNew = this.view.getMnItemNew();
    _mnItemNew.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        try {
          EditorAdapterSwing.this.openPdf();
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    });
    JMenuItem _mnItemSave = this.view.getMnItemSave();
    _mnItemSave.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        try {
          EditorAdapterSwing.this.saveXmi();
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    });
    JMenuItem _mnItemLoad = this.view.getMnItemLoad();
    _mnItemLoad.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        try {
          EditorAdapterSwing.this.loadXmi();
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    });
    JMenuItem _mnItemClose = this.view.getMnItemClose();
    _mnItemClose.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        EditorAdapterSwing.this.getPresenter().close();
      }
    });
    JButton _btnPrev = this.view.getBtnPrev();
    _btnPrev.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        EditorAdapterSwing.this.getPresenter().previousPdfPage();
        EditorAdapterSwing.this.adapterPdfAndBox.refreshPdf();
        EditorAdapterSwing.this.view.setCurrentPage(EditorAdapterSwing.this.getPresenter().getCurrentPdfPageNumber());
      }
    });
    JButton _btnNext = this.view.getBtnNext();
    _btnNext.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        EditorAdapterSwing.this.getPresenter().nextPdfPage();
        EditorAdapterSwing.this.adapterPdfAndBox.refreshPdf();
        EditorAdapterSwing.this.view.setCurrentPage(EditorAdapterSwing.this.getPresenter().getCurrentPdfPageNumber());
      }
    });
  }
  
  /**
   * Ouvre un fichier pdf
   */
  public void openPdf() throws IOException, ClassNotFoundException {
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
        this.editorPresenter.create(selectedFile);
        this.adapterPdfAndBox.refreshPdf();
        this.view.getCmbBxPage().removeAll();
        int _totalPdfPageNumber = this.editorPresenter.getTotalPdfPageNumber();
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(1, _totalPdfPageNumber, true);
        for (final Integer i : _doubleDotLessThan) {
          this.view.getCmbBxPage().addItem(i);
        }
        JComboBox<Integer> _cmbBxPage = this.view.getCmbBxPage();
        _cmbBxPage.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(final ActionEvent e) {
            EditorAdapterSwing.this.getPresenter().goToPage(EditorAdapterSwing.this.view.getCmbBxPage().getSelectedIndex());
            EditorAdapterSwing.this.adapterPdfAndBox.refreshPdf();
            EditorAdapterSwing.this.view.setCurrentPage(EditorAdapterSwing.this.getPresenter().getCurrentPdfPageNumber());
          }
        });
        this.view.setCurrentPage(this.getPresenter().getCurrentPdfPageNumber());
      }
    }
  }
  
  /**
   * Sauvegarde le fichier
   */
  public void saveXmi() throws IOException, ClassNotFoundException {
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Xmi file(.xmi)", "xmi");
    JFileChooser fc = new JFileChooser();
    fc.setDialogTitle("Save your file");
    File _file = new File(".");
    fc.setCurrentDirectory(_file);
    fc.setFileFilter(filter);
    int ret = fc.showSaveDialog(null);
    if ((ret == JFileChooser.APPROVE_OPTION)) {
      this.getPresenter().save(fc.getSelectedFile().getPath());
    }
  }
  
  /**
   * Charge le fichier
   */
  public void loadXmi() throws IOException, ClassNotFoundException {
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Xmi file(.xmi)", "xmi");
    JFileChooser fc = new JFileChooser();
    fc.setDialogTitle("Load your file");
    File _file = new File(".");
    fc.setCurrentDirectory(_file);
    fc.setFileFilter(filter);
    int ret = fc.showSaveDialog(null);
    if ((ret == JFileChooser.APPROVE_OPTION)) {
      this.getPresenter();
      this.getPresenter().load(fc.getSelectedFile().getPath());
    }
  }
  
  @Override
  public void setPresenter(final EditorPresenter presenter) {
    this.editorPresenter = presenter;
    this.adapterPdfAndBox.presenterPdf = presenter;
    this.adapterPdfAndBox.setPresenterQst(presenter.getPresenterQuestionZone());
  }
  
  @Override
  public EditorPresenter getPresenter() {
    return this.editorPresenter;
  }
}
