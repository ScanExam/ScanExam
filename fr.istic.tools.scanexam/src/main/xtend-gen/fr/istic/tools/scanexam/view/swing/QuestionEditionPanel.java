package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.box.Box;
import fr.istic.tools.scanexam.config.LanguageManager;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Classe Swing pour éditer une zone de quesition
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class QuestionEditionPanel extends JPanel {
  /**
   * Boîte liée à la question
   */
  private Box box;
  
  /**
   * Label affichant le titre de la question
   */
  private JLabel lblTitle;
  
  /**
   * Panel contenant les boutons
   */
  private JPanel pnlButtons;
  
  /**
   * Bouton pour déplacer la zone de question
   */
  private JButton btnMove;
  
  /**
   * Bouton pour redimensionner la zone de question
   */
  private JButton btnResize;
  
  /**
   * Bouton pour supprimer la zone de question
   */
  private JButton btnDelete;
  
  /**
   * Constructeur
   */
  public QuestionEditionPanel(final Box box) {
    this.box = box;
    JSplitPane splitPane = new JSplitPane();
    splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    String _title = this.box.getTitle();
    JLabel _jLabel = new JLabel(_title);
    this.lblTitle = _jLabel;
    splitPane.setLeftComponent(this.lblTitle);
    JPanel _jPanel = new JPanel();
    this.pnlButtons = _jPanel;
    splitPane.setRightComponent(this.pnlButtons);
    FlowLayout _flowLayout = new FlowLayout(FlowLayout.CENTER, 5, 5);
    this.pnlButtons.setLayout(_flowLayout);
    String _translate = LanguageManager.translate("label.tool.moveZone");
    JButton _jButton = new JButton(_translate);
    this.btnMove = _jButton;
    this.pnlButtons.add(this.btnMove);
    String _translate_1 = LanguageManager.translate("label.tool.resizeZone");
    JButton _jButton_1 = new JButton(_translate_1);
    this.btnResize = _jButton_1;
    this.pnlButtons.add(this.btnResize);
    String _translate_2 = LanguageManager.translate("menu.edit.delete");
    JButton _jButton_2 = new JButton(_translate_2);
    this.btnDelete = _jButton_2;
    this.pnlButtons.add(this.btnDelete);
    this.add(splitPane);
  }
}
