package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.swing.AdapterSwingPdfAndBoxPanel;
import fr.istic.tools.scanexam.view.swing.PdfAndBoxPanel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collections;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;

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
  private JPanel contentPane;
  
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
   * Panel gauche
   */
  private JPanel pnlLeft;
  
  /**
   * Label de sélection
   */
  private JLabel lblThing;
  
  /**
   * ComboBox de box thing
   */
  private JComboBox<String> cmbBxThing;
  
  /**
   * Label de sélection d'examen vide
   */
  private JLabel lblBlankExam;
  
  /**
   * ComboBox d'examen vide
   */
  private JComboBox<String> cmbBxBlankExam;
  
  /**
   * Panel principal à gauche
   */
  private JPanel pnlLeftMain;
  
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
    this.contentPane = _jPanel;
    EmptyBorder _emptyBorder = new EmptyBorder(5, 5, 5, 5);
    this.contentPane.setBorder(_emptyBorder);
    this.window.setContentPane(this.contentPane);
    BorderLayout _borderLayout = new BorderLayout(0, 0);
    this.contentPane.setLayout(_borderLayout);
    JPanel _jPanel_1 = new JPanel();
    this.pnlButtons = _jPanel_1;
    this.contentPane.add(this.pnlButtons, BorderLayout.NORTH);
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
    JSplitPane splitPane = new JSplitPane();
    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    this.contentPane.add(splitPane, BorderLayout.WEST);
    JPanel _jPanel_2 = new JPanel();
    this.pnlLeft = _jPanel_2;
    splitPane.setLeftComponent(this.pnlLeft);
    GridBagLayout gblPnlLeft = new GridBagLayout();
    gblPnlLeft.columnWidths = ((int[]) ((int[])Conversions.unwrapArray(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(100), Integer.valueOf(0))), int.class)));
    gblPnlLeft.rowHeights = ((int[]) ((int[])Conversions.unwrapArray(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(22), Integer.valueOf(0))), int.class)));
    gblPnlLeft.columnWeights = ((double[]) ((double[])Conversions.unwrapArray(Collections.<Double>unmodifiableList(CollectionLiterals.<Double>newArrayList(Double.valueOf(0.0), Double.valueOf(Double.MIN_VALUE))), double.class)));
    gblPnlLeft.rowWeights = ((double[]) ((double[])Conversions.unwrapArray(Collections.<Double>unmodifiableList(CollectionLiterals.<Double>newArrayList(Double.valueOf(0.0), Double.valueOf(0.0), Double.valueOf(0.0), Double.valueOf(0.0), Double.valueOf(Double.MIN_VALUE))), double.class)));
    this.pnlLeft.setLayout(gblPnlLeft);
    JLabel _jLabel = new JLabel("Selected Thing :");
    this.lblThing = _jLabel;
    this.lblThing.setHorizontalAlignment(SwingConstants.LEFT);
    GridBagConstraints gbcPnlLeft = new GridBagConstraints();
    gbcPnlLeft.fill = GridBagConstraints.BOTH;
    Insets _insets = new Insets(0, 0, 5, 0);
    gbcPnlLeft.insets = _insets;
    gbcPnlLeft.gridx = 0;
    gbcPnlLeft.gridy = 0;
    this.pnlLeft.add(this.lblThing, gbcPnlLeft);
    JComboBox<String> _jComboBox = new JComboBox<String>();
    this.cmbBxThing = _jComboBox;
    GridBagConstraints gbcCmbBxThing = new GridBagConstraints();
    gbcCmbBxThing.fill = GridBagConstraints.BOTH;
    Insets _insets_1 = new Insets(0, 0, 5, 0);
    gbcCmbBxThing.insets = _insets_1;
    gbcCmbBxThing.gridx = 0;
    gbcCmbBxThing.gridy = 1;
    this.pnlLeft.add(this.cmbBxThing, gbcCmbBxThing);
    JLabel _jLabel_1 = new JLabel("Selected Blank Exam :");
    this.lblBlankExam = _jLabel_1;
    this.lblBlankExam.setHorizontalAlignment(SwingConstants.LEFT);
    GridBagConstraints gbcLblBlankExam = new GridBagConstraints();
    gbcLblBlankExam.fill = GridBagConstraints.BOTH;
    Insets _insets_2 = new Insets(0, 0, 5, 0);
    gbcLblBlankExam.insets = _insets_2;
    gbcLblBlankExam.gridx = 0;
    gbcLblBlankExam.gridy = 2;
    this.pnlLeft.add(this.lblBlankExam, gbcLblBlankExam);
    JComboBox<String> _jComboBox_1 = new JComboBox<String>();
    this.cmbBxBlankExam = _jComboBox_1;
    GridBagConstraints gbcCmbBxBlankExam = new GridBagConstraints();
    gbcCmbBxBlankExam.fill = GridBagConstraints.BOTH;
    gbcCmbBxBlankExam.gridx = 0;
    gbcCmbBxBlankExam.gridy = 3;
    this.pnlLeft.add(this.cmbBxBlankExam, gbcCmbBxBlankExam);
    JPanel _jPanel_3 = new JPanel();
    this.pnlLeftMain = _jPanel_3;
    splitPane.setRightComponent(this.pnlLeftMain);
    PdfAndBoxPanel _pdfAndBoxPanel = new PdfAndBoxPanel(this.adapterPdfAndBoxPanel);
    this.pnlPdf = _pdfAndBoxPanel;
    this.contentPane.add(this.pnlPdf, BorderLayout.CENTER);
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
  
  /**
   * Envoie le bouton de chargement de pdf
   * @return mnItemLoad
   */
  public JMenuItem getMnItemLoad() {
    return this.mnItemLoad;
  }
  
  public JMenuItem getMnItemClose() {
    return this.mnItemSave;
  }
}
