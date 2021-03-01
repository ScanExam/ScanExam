package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.swing.AdapterSwingPdfAndBoxPanel;
import fr.istic.tools.scanexam.view.swing.PdfAndBoxPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Vue swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class EditorViewSwing {
  /**
   * Controlleur liant les controlleurs du Pdf et des boîtes
   */
  private AdapterSwingPdfAndBoxPanel adapterPdfAndBoxPanel;
  
  /**
   * Fenêtre de correction d'examen
   */
  private JFrame window;
  
  /**
   * Panel père
   */
  private JPanel pnlMain;
  
  /**
   * Barre de menu de la fenêtre
   */
  private JMenuBar menuBar;
  
  /**
   * Menu fichier de la bare de menu de la fenêtre
   */
  private JMenu mnFile;
  
  /**
   * Bouton pour créer un examen
   */
  private JMenuItem mnItemNew;
  
  /**
   * Bouton pour sauver un examen
   */
  private JMenuItem mnItemSave;
  
  /**
   * Bouton pour charger un examen
   */
  private JMenuItem mnItemLoad;
  
  /**
   * Bouton pour fermer un examen
   */
  private JMenuItem mnItemClose;
  
  /**
   * Menu édition de la bare de menu de la fenêtre
   */
  private JMenu mnEdit;
  
  /**
   * Bouton de suppression
   */
  private JMenuItem mnItemDelete;
  
  /**
   * Menu aide de la bare de menu de la fenêtre
   */
  private JMenu mnHelp;
  
  /**
   * Bouton "A propos"
   */
  private JMenuItem mnItemAbout;
  
  /**
   * Panel des boutons principaux
   */
  private JPanel pnlButtons;
  
  /**
   * Bouton "Question"
   */
  private JButton btnQuestionArea;
  
  /**
   * Bouton "ID"
   */
  private JButton btnIdArea;
  
  /**
   * Bouton "QR"
   */
  private JButton btnQrArea;
  
  /**
   * Bouton de mouvement de caméra
   */
  private JButton btnMoveCam;
  
  /**
   * Panel de navigation entre les questions
   */
  private JPanel pnlQst;
  
  /**
   * Label "Questions :"
   */
  private JLabel lblQst;
  
  /**
   * Liste des questions
   */
  private JList<String> listQst;
  
  /**
   * Panel pour les pages
   */
  private JPanel pnlPage;
  
  /**
   * Label "Pages :"
   */
  private JLabel lblPage;
  
  /**
   * Liste des pages
   */
  private JComboBox<Integer> cmbBxPage;
  
  /**
   * Panel de navigation entre les pages
   */
  private JPanel pnlNavPage;
  
  /**
   * Bouton page précédente
   */
  private JButton btnPrev;
  
  /**
   * Numéro de la page actuelle
   */
  private JLabel lblNumPage;
  
  /**
   * Bouton page suivante
   */
  private JButton btnNext;
  
  /**
   * Panel principal présentant la copie
   */
  private JPanel pnlPdf;
  
  /**
   * Constructeur
   */
  public EditorViewSwing(final AdapterSwingPdfAndBoxPanel adapterPdfAndBoxPanel) {
    this.adapterPdfAndBoxPanel = adapterPdfAndBoxPanel;
    this.initialize();
  }
  
  /**
   * Initialise la fenêtre
   */
  private void initialize() {
    String _translate = LanguageManager.translate("scanexam");
    JFrame _jFrame = new JFrame(_translate);
    this.window = _jFrame;
    this.window.setBounds(100, 100, 1280, 720);
    this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JMenuBar _jMenuBar = new JMenuBar();
    this.menuBar = _jMenuBar;
    this.window.setJMenuBar(this.menuBar);
    String _translate_1 = LanguageManager.translate("menu.file");
    JMenu _jMenu = new JMenu(_translate_1);
    this.mnFile = _jMenu;
    String _translate_2 = LanguageManager.translate("menu.file.new");
    JMenuItem _jMenuItem = new JMenuItem(_translate_2);
    this.mnItemNew = _jMenuItem;
    this.mnFile.add(this.mnItemNew);
    this.mnFile.addSeparator();
    String _translate_3 = LanguageManager.translate("menu.file.save");
    JMenuItem _jMenuItem_1 = new JMenuItem(_translate_3);
    this.mnItemSave = _jMenuItem_1;
    this.mnFile.add(this.mnItemSave);
    String _translate_4 = LanguageManager.translate("menu.file.load");
    JMenuItem _jMenuItem_2 = new JMenuItem(_translate_4);
    this.mnItemLoad = _jMenuItem_2;
    this.mnFile.add(this.mnItemLoad);
    this.mnFile.addSeparator();
    String _translate_5 = LanguageManager.translate("menu.file.close");
    JMenuItem _jMenuItem_3 = new JMenuItem(_translate_5);
    this.mnItemClose = _jMenuItem_3;
    this.mnFile.add(this.mnItemClose);
    this.menuBar.add(this.mnFile);
    String _translate_6 = LanguageManager.translate("menu.edit");
    JMenu _jMenu_1 = new JMenu(_translate_6);
    this.mnEdit = _jMenu_1;
    String _translate_7 = LanguageManager.translate("menu.edit.delete");
    JMenuItem _jMenuItem_4 = new JMenuItem(_translate_7);
    this.mnItemDelete = _jMenuItem_4;
    this.mnEdit.add(this.mnItemDelete);
    this.menuBar.add(this.mnEdit);
    String _translate_8 = LanguageManager.translate("menu.help");
    JMenu _jMenu_2 = new JMenu(_translate_8);
    this.mnHelp = _jMenu_2;
    String _translate_9 = LanguageManager.translate("menu.help.about");
    JMenuItem _jMenuItem_5 = new JMenuItem(_translate_9);
    this.mnItemAbout = _jMenuItem_5;
    this.mnHelp.add(this.mnItemAbout);
    this.menuBar.add(this.mnHelp);
    JPanel _jPanel = new JPanel();
    this.pnlMain = _jPanel;
    EmptyBorder _emptyBorder = new EmptyBorder(5, 5, 5, 5);
    this.pnlMain.setBorder(_emptyBorder);
    this.window.setContentPane(this.pnlMain);
    BorderLayout _borderLayout = new BorderLayout(0, 0);
    this.pnlMain.setLayout(_borderLayout);
    JPanel _jPanel_1 = new JPanel();
    this.pnlButtons = _jPanel_1;
    this.pnlMain.add(this.pnlButtons, BorderLayout.NORTH);
    BoxLayout _boxLayout = new BoxLayout(this.pnlButtons, BoxLayout.X_AXIS);
    this.pnlButtons.setLayout(_boxLayout);
    JButton _jButton = new JButton("Question area");
    this.btnQuestionArea = _jButton;
    this.pnlButtons.add(this.btnQuestionArea);
    JButton _jButton_1 = new JButton("ID area");
    this.btnIdArea = _jButton_1;
    this.pnlButtons.add(this.btnIdArea);
    JButton _jButton_2 = new JButton("QR area");
    this.btnQrArea = _jButton_2;
    this.pnlButtons.add(this.btnQrArea);
    JButton _jButton_3 = new JButton("Move Camera");
    this.btnMoveCam = _jButton_3;
    this.pnlButtons.add(this.btnMoveCam);
    JPanel _jPanel_2 = new JPanel();
    this.pnlQst = _jPanel_2;
    this.pnlMain.add(this.pnlQst, BorderLayout.WEST);
    BorderLayout _borderLayout_1 = new BorderLayout(0, 0);
    this.pnlQst.setLayout(_borderLayout_1);
    JLabel _jLabel = new JLabel("Questions:");
    this.lblQst = _jLabel;
    this.pnlQst.add(this.lblQst, BorderLayout.NORTH);
    JList<String> _jList = new JList<String>();
    this.listQst = _jList;
    this.pnlQst.add(this.listQst, BorderLayout.CENTER);
    JPanel _jPanel_3 = new JPanel();
    this.pnlPage = _jPanel_3;
    this.pnlQst.add(this.pnlPage, BorderLayout.SOUTH);
    BorderLayout _borderLayout_2 = new BorderLayout(0, 0);
    this.pnlPage.setLayout(_borderLayout_2);
    JLabel _jLabel_1 = new JLabel("Pages :");
    this.lblPage = _jLabel_1;
    this.pnlPage.add(this.lblPage, BorderLayout.NORTH);
    JComboBox<Integer> _jComboBox = new JComboBox<Integer>();
    this.cmbBxPage = _jComboBox;
    this.pnlPage.add(this.cmbBxPage, BorderLayout.CENTER);
    JPanel _jPanel_4 = new JPanel();
    this.pnlNavPage = _jPanel_4;
    this.pnlPage.add(this.pnlNavPage, BorderLayout.SOUTH);
    BorderLayout _borderLayout_3 = new BorderLayout(0, 0);
    this.pnlNavPage.setLayout(_borderLayout_3);
    JButton _jButton_4 = new JButton("Prev");
    this.btnPrev = _jButton_4;
    this.pnlNavPage.add(this.btnPrev, BorderLayout.WEST);
    JLabel _jLabel_2 = new JLabel(".");
    this.lblNumPage = _jLabel_2;
    this.pnlNavPage.add(this.lblNumPage, BorderLayout.CENTER);
    JButton _jButton_5 = new JButton("Next");
    this.btnNext = _jButton_5;
    this.pnlNavPage.add(this.btnNext, BorderLayout.EAST);
    PdfAndBoxPanel _pdfAndBoxPanel = new PdfAndBoxPanel(this.adapterPdfAndBoxPanel);
    this.pnlPdf = _pdfAndBoxPanel;
    this.pnlMain.add(this.pnlPdf, BorderLayout.CENTER);
  }
  
  /**
   * Envoie la fenêtre principale
   * @return window
   */
  public JFrame getWindow() {
    return this.window;
  }
  
  public JMenuItem getMnItemNew() {
    return this.mnItemNew;
  }
  
  public JMenuItem getMnItemSave() {
    return this.mnItemSave;
  }
  
  public JMenuItem getMnItemLoad() {
    return this.mnItemLoad;
  }
  
  public JMenuItem getMnItemClose() {
    return this.mnItemClose;
  }
  
  public JMenuItem getMnItemDelete() {
    return this.mnItemDelete;
  }
  
  public JMenuItem getMnItemAbout() {
    return this.mnItemAbout;
  }
  
  public JButton getBtnQuestionArea() {
    return this.btnQuestionArea;
  }
  
  public JButton getBtnIdArea() {
    return this.btnIdArea;
  }
  
  public JButton getBtnQrArea() {
    return this.btnQrArea;
  }
  
  public JButton getBtnMoveCam() {
    return this.btnMoveCam;
  }
  
  public JButton getBtnPrev() {
    return this.btnPrev;
  }
  
  public JButton getBtnNext() {
    return this.btnNext;
  }
  
  /**
   * SETTERS
   */
  public void setLblNumPage(final int numPage) {
    this.lblNumPage.setText(Integer.valueOf(numPage).toString());
  }
}
