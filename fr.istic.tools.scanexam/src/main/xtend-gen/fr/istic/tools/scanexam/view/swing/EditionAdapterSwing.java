package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.launcher.LauncherSwing;
import fr.istic.tools.scanexam.presenter.PresenterEdition;
import fr.istic.tools.scanexam.presenter.SelectionStateMachine;
import fr.istic.tools.scanexam.view.AdapterEdition;
import fr.istic.tools.scanexam.view.swing.AdapterSwingPdfAndBoxPanel;
import fr.istic.tools.scanexam.view.swing.BoxList;
import fr.istic.tools.scanexam.view.swing.EditionViewSwing;
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
public class EditionAdapterSwing implements AdapterEdition {
  /**
   * Presenter de la création d'exman
   */
  private PresenterEdition editorPresenter;
  
  /**
   * Vue de la création d'exman
   */
  private EditionViewSwing view;
  
  /**
   * Présentateur du pdf
   */
  private AdapterSwingPdfAndBoxPanel adapterPdfAndBox;
  
  /**
   * Constructeur
   */
  public EditionAdapterSwing() {
    BoxList _boxList = new BoxList();
    AdapterSwingPdfAndBoxPanel _adapterSwingPdfAndBoxPanel = new AdapterSwingPdfAndBoxPanel(1280, 720, this.editorPresenter, _boxList);
    this.adapterPdfAndBox = _adapterSwingPdfAndBoxPanel;
    EditionViewSwing _editionViewSwing = new EditionViewSwing(this.adapterPdfAndBox);
    this.view = _editionViewSwing;
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
          EditionAdapterSwing.this.openPdf();
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
          EditionAdapterSwing.this.saveXmi();
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    });
    JMenuItem _mnItemSwap = this.view.getMnItemSwap();
    _mnItemSwap.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        EditionAdapterSwing.this.swapVerGraduation();
      }
    });
    JMenuItem _mnItemLoad = this.view.getMnItemLoad();
    _mnItemLoad.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        try {
          EditionAdapterSwing.this.loadXmi();
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    });
    JMenuItem _mnItemClose = this.view.getMnItemClose();
    _mnItemClose.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        EditionAdapterSwing.this.getPresenter().close();
      }
    });
    JButton _btnQuestionArea = this.view.getBtnQuestionArea();
    _btnQuestionArea.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        SelectionStateMachine.setState(SelectionStateMachine.CREATE);
      }
    });
    JButton _btnMoveCam = this.view.getBtnMoveCam();
    _btnMoveCam.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        SelectionStateMachine.setState(SelectionStateMachine.IDLE);
      }
    });
    JButton _btnPrev = this.view.getBtnPrev();
    _btnPrev.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        EditionAdapterSwing.this.getPresenter().getPresenterPdf().previousPdfPage();
        EditionAdapterSwing.this.adapterPdfAndBox.refreshPdf();
        EditionAdapterSwing.this.view.setCurrentPage(EditionAdapterSwing.this.getPresenter().getPresenterPdf().currentPdfPageNumber());
      }
    });
    JButton _btnNext = this.view.getBtnNext();
    _btnNext.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        EditionAdapterSwing.this.getPresenter().getPresenterPdf().nextPdfPage();
        EditionAdapterSwing.this.adapterPdfAndBox.refreshPdf();
        EditionAdapterSwing.this.view.setCurrentPage(EditionAdapterSwing.this.getPresenter().getPresenterPdf().currentPdfPageNumber());
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
        this.editorPresenter.getPresenterPdf().create(selectedFile);
        this.adapterPdfAndBox.refreshPdf();
        this.view.getCmbBxPage().removeAll();
        int _pdfPageCount = this.editorPresenter.getPresenterPdf().getPdfPageCount();
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(1, _pdfPageCount, true);
        for (final Integer i : _doubleDotLessThan) {
          this.view.getCmbBxPage().addItem(i);
        }
        JComboBox<Integer> _cmbBxPage = this.view.getCmbBxPage();
        _cmbBxPage.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(final ActionEvent e) {
            EditionAdapterSwing.this.getPresenter().getPresenterPdf().goToPdfPage(EditionAdapterSwing.this.view.getCmbBxPage().getSelectedIndex());
            EditionAdapterSwing.this.adapterPdfAndBox.refreshPdf();
            EditionAdapterSwing.this.view.setCurrentPage(EditionAdapterSwing.this.getPresenter().getPresenterPdf().currentPdfPageNumber());
          }
        });
        this.view.setCurrentPage(this.getPresenter().getPresenterPdf().currentPdfPageNumber());
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
      this.getPresenter().save(fc.getSelectedFile());
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
  
  /**
   * Swap vers graduation
   */
  public void swapVerGraduation() {
    this.view.getWindow().setVisible(false);
    LauncherSwing.swapToGraduator(this.view);
  }
  
  @Override
  public void setPresenter(final PresenterEdition presenter) {
    this.editorPresenter = presenter;
    this.adapterPdfAndBox.presenterPdf = presenter;
    this.adapterPdfAndBox.setPresenterQst(presenter.getPresenterQuestionZone());
  }
  
  @Override
  public PresenterEdition getPresenter() {
    return this.editorPresenter;
  }
}
