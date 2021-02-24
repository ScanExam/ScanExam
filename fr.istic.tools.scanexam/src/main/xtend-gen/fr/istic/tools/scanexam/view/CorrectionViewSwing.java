package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.AdapterSwingPdfPanel;
import fr.istic.tools.scanexam.view.ImagePanel;
import fr.istic.tools.scanexam.view.PdfPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

/**
 * Vue swing de la fenêtre de correction d'examen
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class CorrectionViewSwing {
  /**
   * Controlleur liant les controlleurs du Pdf et des boîtes
   */
  private AdapterSwingPdfPanel pdfPresenter;
  
  /**
   * Fenêtre de correction d'examen
   */
  private JFrame window;
  
  /**
   * Barre de menu de la fenêtre
   */
  private JMenuBar menuBar;
  
  /**
   * Menu fichier de la bare de menu de la fenêtre
   */
  private JMenu mnFile;
  
  /**
   * Item fermer du menu ficher
   */
  private JMenuItem mntmClose;
  
  /**
   * Menu édition de la bare de menu de la fenêtre
   */
  private JMenu mnEdit;
  
  /**
   * Item supprimer du menu édition
   */
  private JMenuItem mntmDelete;
  
  /**
   * Menu aide de la bare de menu de la fenêtre
   */
  private JMenu mnHelp;
  
  /**
   * Item à propos du menu aide
   */
  private JMenuItem mntmAbout;
  
  /**
   * Panel des boutons principaux
   */
  private JPanel pnlMainBtn;
  
  /**
   * 1er bouton du panel des boutons principaux
   */
  private JButton btnMain1;
  
  /**
   * 2ème bouton du panel des boutons principaux
   */
  private JButton btnMain2;
  
  /**
   * Bouton crayon du panel des boutons principaux
   */
  private JButton btnPen;
  
  /**
   * Bouton gomme du panel des boutons principaux
   */
  private JButton btnEraser;
  
  /**
   * Bouton tampon du panel des boutons principaux
   */
  private JButton btnStamp;
  
  /**
   * Bouton épais du panel des boutons principaux
   */
  private JButton btnThicc;
  
  /**
   * Bouton fin du panel des boutons principaux
   */
  private JButton btnThinn;
  
  /**
   * 3ème bouton du panel des boutons principaux
   */
  private JButton btnMain3;
  
  /**
   * 4ème bouton du panel des boutons principaux
   */
  private JButton btnMain4;
  
  /**
   * 5ème bouton du panel des boutons principaux
   */
  private JButton btnMain5;
  
  /**
   * Bouton sauver du panel des boutons principaux
   */
  private JButton btnSave;
  
  /**
   * Bouton charger du panel des boutons principaux
   */
  private JButton btnLoad;
  
  /**
   * Bouton exporter du panel des boutons principaux
   */
  private JButton btnExp;
  
  /**
   * Panel de navigation entre les copies
   */
  private JPanel pnlPaper;
  
  /**
   * Label du numéro de la copie
   */
  private JLabel lblNbPaper;
  
  /**
   * Liste textuelle des copies
   */
  private JTextPane txtPnPapers;
  
  /**
   * Bouton copie précédente
   */
  private JButton btnPrevPaper;
  
  /**
   * Bouton copie suivante
   */
  private JButton btnNextPaper;
  
  /**
   * Panel pour séparer la navigation entre les copies et la note
   */
  private JSplitPane spltPnPaper;
  
  /**
   * Panel d'affichage de la note
   */
  private JPanel pnlNote;
  
  /**
   * Label "note acutelle"
   */
  private JLabel lblCurrentNote;
  
  /**
   * Label de la note de la copie
   */
  private JLabel lblNote;
  
  /**
   * Panel principal présentant la copie
   */
  private JPanel pnlPdf;
  
  /**
   * Panel de navigation entre les questions
   */
  private JPanel pnlQst;
  
  /**
   * Label de numéro de question
   */
  private JLabel lblNbQst;
  
  /**
   * Liste textuelle des questions
   */
  private JTextPane txtPnQst;
  
  /**
   * Panel pour séparer la liste des questions et les boutons de navigation
   */
  private JSplitPane spltPnQst;
  
  /**
   * Bouton question précédente
   */
  private JButton btnPrevQst;
  
  /**
   * Bouton question suivante
   */
  private JButton btnNextQst;
  
  /**
   * Panel pour voir l'énoncé d'une question
   */
  private JPanel pnlDown;
  
  /**
   * Bouton pour voir l'énoncé d'une question
   */
  private JButton btnDown;
  
  /**
   * Panel d'énoncé d'une question
   */
  private JPanel pnlContentDown;
  
  /**
   * Panel d'énoncé d'une question
   */
  private boolean contentDown;
  
  private JSplitPane mainSplitPane;
  
  /**
   * Constructeur
   */
  public CorrectionViewSwing(final AdapterSwingPdfPanel pdfPresenter) {
    this.pdfPresenter = pdfPresenter;
    this.initialize();
  }
  
  /**
   * Initialise la fenêtre
   */
  private void initialize() {
    String _translate = LanguageManager.translate("title.ScanExam");
    JFrame _jFrame = new JFrame(_translate);
    this.window = _jFrame;
    this.window.setBounds(100, 100, 1280, 720);
    this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JMenuBar _jMenuBar = new JMenuBar();
    this.menuBar = _jMenuBar;
    this.window.setJMenuBar(this.menuBar);
    JMenu _jMenu = new JMenu("File");
    this.mnFile = _jMenu;
    this.menuBar.add(this.mnFile);
    JMenuItem _jMenuItem = new JMenuItem("Close");
    this.mntmClose = _jMenuItem;
    this.mnFile.add(this.mntmClose);
    JMenu _jMenu_1 = new JMenu("Edit");
    this.mnEdit = _jMenu_1;
    this.menuBar.add(this.mnEdit);
    JMenuItem _jMenuItem_1 = new JMenuItem("Delete");
    this.mntmDelete = _jMenuItem_1;
    this.mnEdit.add(this.mntmDelete);
    JMenu _jMenu_2 = new JMenu("Help");
    this.mnHelp = _jMenu_2;
    this.menuBar.add(this.mnHelp);
    JMenuItem _jMenuItem_2 = new JMenuItem("About");
    this.mntmAbout = _jMenuItem_2;
    this.mnHelp.add(this.mntmAbout);
    Container _contentPane = this.window.getContentPane();
    BorderLayout _borderLayout = new BorderLayout(0, 0);
    _contentPane.setLayout(_borderLayout);
    JPanel _jPanel = new JPanel();
    this.pnlMainBtn = _jPanel;
    this.window.getContentPane().add(this.pnlMainBtn, BorderLayout.NORTH);
    BoxLayout _boxLayout = new BoxLayout(this.pnlMainBtn, BoxLayout.X_AXIS);
    this.pnlMainBtn.setLayout(_boxLayout);
    JButton _jButton = new JButton("Button");
    this.btnMain1 = _jButton;
    this.pnlMainBtn.add(this.btnMain1);
    JButton _jButton_1 = new JButton("Button");
    this.btnMain2 = _jButton_1;
    this.pnlMainBtn.add(this.btnMain2);
    Component hStrtMainBtn1 = Box.createHorizontalStrut(20);
    this.pnlMainBtn.add(hStrtMainBtn1);
    JButton _jButton_2 = new JButton("Pen");
    this.btnPen = _jButton_2;
    this.pnlMainBtn.add(this.btnPen);
    JButton _jButton_3 = new JButton("Eraser");
    this.btnEraser = _jButton_3;
    this.pnlMainBtn.add(this.btnEraser);
    JButton _jButton_4 = new JButton("Stamp");
    this.btnStamp = _jButton_4;
    this.pnlMainBtn.add(this.btnStamp);
    JButton _jButton_5 = new JButton("Thicc");
    this.btnThicc = _jButton_5;
    this.pnlMainBtn.add(this.btnThicc);
    JButton _jButton_6 = new JButton("Thinn");
    this.btnThinn = _jButton_6;
    this.pnlMainBtn.add(this.btnThinn);
    JButton _jButton_7 = new JButton("Button");
    this.btnMain3 = _jButton_7;
    this.pnlMainBtn.add(this.btnMain3);
    JButton _jButton_8 = new JButton("Button");
    this.btnMain4 = _jButton_8;
    this.pnlMainBtn.add(this.btnMain4);
    JButton _jButton_9 = new JButton("Button");
    this.btnMain5 = _jButton_9;
    this.pnlMainBtn.add(this.btnMain5);
    Component hStrtMainBtn2 = Box.createHorizontalStrut(20);
    this.pnlMainBtn.add(hStrtMainBtn2);
    JButton _jButton_10 = new JButton("Save");
    this.btnSave = _jButton_10;
    this.pnlMainBtn.add(this.btnSave);
    JButton _jButton_11 = new JButton("Load");
    this.btnLoad = _jButton_11;
    this.pnlMainBtn.add(this.btnLoad);
    JButton _jButton_12 = new JButton("Exp");
    this.btnExp = _jButton_12;
    this.pnlMainBtn.add(this.btnExp);
    JPanel _jPanel_1 = new JPanel();
    this.pnlPaper = _jPanel_1;
    this.window.getContentPane().add(this.pnlPaper, BorderLayout.WEST);
    BorderLayout _borderLayout_1 = new BorderLayout(0, 0);
    this.pnlPaper.setLayout(_borderLayout_1);
    JLabel _jLabel = new JLabel("Copie No.x");
    this.lblNbPaper = _jLabel;
    Font _font = new Font("Arial", Font.PLAIN, 14);
    this.lblNbPaper.setFont(_font);
    this.lblNbPaper.setHorizontalAlignment(SwingConstants.CENTER);
    this.pnlPaper.add(this.lblNbPaper, BorderLayout.NORTH);
    JTextPane _jTextPane = new JTextPane();
    this.txtPnPapers = _jTextPane;
    this.pnlPaper.add(this.txtPnPapers, BorderLayout.CENTER);
    JPanel _jPanel_2 = new JPanel();
    this.pnlNote = _jPanel_2;
    this.pnlPaper.add(this.pnlNote, BorderLayout.SOUTH);
    BorderLayout _borderLayout_2 = new BorderLayout(0, 0);
    this.pnlNote.setLayout(_borderLayout_2);
    JLabel _jLabel_1 = new JLabel("Note actuelle");
    this.lblCurrentNote = _jLabel_1;
    Font _font_1 = new Font("Arial", Font.PLAIN, 14);
    this.lblCurrentNote.setFont(_font_1);
    this.lblCurrentNote.setHorizontalAlignment(SwingConstants.CENTER);
    this.pnlNote.add(this.lblCurrentNote, BorderLayout.NORTH);
    JLabel _jLabel_2 = new JLabel("x/20");
    this.lblNote = _jLabel_2;
    Font _font_2 = new Font("Arial", Font.PLAIN, 14);
    this.lblNote.setFont(_font_2);
    this.lblNote.setHorizontalAlignment(SwingConstants.CENTER);
    this.pnlNote.add(this.lblNote, BorderLayout.CENTER);
    JSplitPane _jSplitPane = new JSplitPane();
    this.spltPnPaper = _jSplitPane;
    this.pnlNote.add(this.spltPnPaper, BorderLayout.SOUTH);
    JButton _jButton_13 = new JButton("<<");
    this.btnPrevPaper = _jButton_13;
    this.spltPnPaper.setLeftComponent(this.btnPrevPaper);
    JButton _jButton_14 = new JButton(">>");
    this.btnNextPaper = _jButton_14;
    this.spltPnPaper.setRightComponent(this.btnNextPaper);
    PdfPanel _pdfPanel = new PdfPanel(this.pdfPresenter);
    this.pnlPdf = _pdfPanel;
    this.window.getContentPane().add(this.pnlPdf, BorderLayout.CENTER);
    BorderLayout _borderLayout_3 = new BorderLayout(0, 0);
    this.pnlPdf.setLayout(_borderLayout_3);
    JPanel _jPanel_3 = new JPanel();
    this.pnlDown = _jPanel_3;
    this.pnlPdf.add(this.pnlDown, BorderLayout.SOUTH);
    BorderLayout _borderLayout_4 = new BorderLayout(0, 0);
    this.pnlDown.setLayout(_borderLayout_4);
    JButton _jButton_15 = new JButton("▲");
    this.btnDown = _jButton_15;
    this.btnDown.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        CorrectionViewSwing.this.showContentDown();
      }
    });
    InputStream inputContentDown = ResourcesUtils.getInputStreamResource("/logo.png");
    ImagePanel _imagePanel = new ImagePanel(inputContentDown);
    this.pnlContentDown = _imagePanel;
    Dimension _dimension = new Dimension(this.pnlContentDown.getSize().width, 180);
    this.pnlContentDown.setPreferredSize(_dimension);
    this.contentDown = false;
    JPanel _jPanel_4 = new JPanel();
    JSplitPane _jSplitPane_1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, _jPanel_4, this.pnlContentDown);
    this.mainSplitPane = _jSplitPane_1;
    this.pnlDown.add(this.btnDown, BorderLayout.NORTH);
    JPanel _jPanel_5 = new JPanel();
    this.pnlQst = _jPanel_5;
    this.window.getContentPane().add(this.pnlQst, BorderLayout.EAST);
    BorderLayout _borderLayout_5 = new BorderLayout(0, 0);
    this.pnlQst.setLayout(_borderLayout_5);
    JLabel _jLabel_3 = new JLabel("Question No.i");
    this.lblNbQst = _jLabel_3;
    Font _font_3 = new Font("Arial", Font.PLAIN, 14);
    this.lblNbQst.setFont(_font_3);
    this.lblNbQst.setHorizontalAlignment(SwingConstants.CENTER);
    this.pnlQst.add(this.lblNbQst, BorderLayout.NORTH);
    JTextPane _jTextPane_1 = new JTextPane();
    this.txtPnQst = _jTextPane_1;
    this.pnlQst.add(this.txtPnQst, BorderLayout.CENTER);
    JSplitPane _jSplitPane_2 = new JSplitPane();
    this.spltPnQst = _jSplitPane_2;
    this.pnlQst.add(this.spltPnQst, BorderLayout.SOUTH);
    JButton _jButton_16 = new JButton("<");
    this.btnPrevQst = _jButton_16;
    this.spltPnQst.setLeftComponent(this.btnPrevQst);
    JButton _jButton_17 = new JButton(">");
    this.btnNextQst = _jButton_17;
    this.spltPnQst.setRightComponent(this.btnNextQst);
  }
  
  /**
   * Ouvre ou ferme l'énoncé d'une question
   */
  public void showContentDown() {
    if (this.contentDown) {
      this.pnlDown.remove(this.mainSplitPane);
      this.btnDown.setText("▲");
      this.contentDown = false;
    } else {
      this.pnlDown.add(this.mainSplitPane, BorderLayout.CENTER);
      this.btnDown.setText("▼");
      this.contentDown = true;
    }
  }
  
  /**
   * GETTERS
   */
  public JFrame getWindow() {
    return this.window;
  }
}
