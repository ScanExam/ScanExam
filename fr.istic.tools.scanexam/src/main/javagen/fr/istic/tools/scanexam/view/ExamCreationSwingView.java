package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.controller.PdfAndBoxPresenterSwing;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * Vue swing de la fenêtre de création d'examen
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class ExamCreationSwingView {
  /**
   * Controlleur liant les controlleurs du Pdf et des boîtes
   */
  private PdfAndBoxPresenterSwing pdfPresenter;
  
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
   * Menu édition de la bare de menu de la fenêtre
   */
  private JMenu mnEdit;
  
  /**
   * Menu aide de la bare de menu de la fenêtre
   */
  private JMenu mnHelp;
  
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
  
  private File pdfFileSelected;
  
  /**
   * Constructeur
   */
  public ExamCreationSwingView(final PdfAndBoxPresenterSwing pdfPresenter) {
    this.pdfPresenter = pdfPresenter;
    this.initialize();
  }
  
  /**
   * Initialise la fenêtre
   */
  private void initialize() {
    throw new Error("Unresolved compilation problems:"
      + "\nEmptyBorder cannot be resolved.");
  }
  
  /**
   * GETTERS
   */
  public JFrame getWindow() {
    return this.window;
  }
}
