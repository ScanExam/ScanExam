package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.presenter.SelectionStateMachine;
import fr.istic.tools.scanexam.view.swing.AdapterSwingBox;
import fr.istic.tools.scanexam.view.swing.Box;
import fr.istic.tools.scanexam.view.swing.ListOfQuestionsPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe pour gérer graphiquement une question en swing
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class QuestionPanel extends JPanel {
  /**
   * Bouton "supprimer"
   */
  private JButton btnRemove;
  
  /**
   * Bouton "déplacer"
   */
  private JButton btnMove;
  
  /**
   * Bouton "redimensionner"
   */
  private JButton btnResize;
  
  /**
   * Constructeur
   * @param Box Boîte lié à la question
   * @param AdapterSwingBox Adaptateur gérant la boîte
   * @param ListOfQuestionsPanel Liste visuelle des questions
   */
  public QuestionPanel(final Box box, final AdapterSwingBox adapterBox, final ListOfQuestionsPanel listQstPanel) {
    BoxLayout _boxLayout = new BoxLayout(this, BoxLayout.X_AXIS);
    this.setLayout(_boxLayout);
    String _title = box.getTitle();
    String _plus = (_title + " ");
    JLabel lblQstName = new JLabel(_plus);
    this.add(lblQstName);
    String _translate = LanguageManager.translate("menu.edit.delete");
    JButton _jButton = new JButton(_translate);
    this.btnRemove = _jButton;
    String _translate_1 = LanguageManager.translate("label.tool.moveZone");
    JButton _jButton_1 = new JButton(_translate_1);
    this.btnMove = _jButton_1;
    String _translate_2 = LanguageManager.translate("label.tool.resizeZone");
    JButton _jButton_2 = new JButton(_translate_2);
    this.btnResize = _jButton_2;
    this.addActionListeners(box, adapterBox, this, listQstPanel);
    this.add(this.btnRemove);
    this.add(this.btnMove);
    this.add(this.btnResize);
  }
  
  /**
   * Ajoute les action listeners aux boutons de la vue
   * @param Box Boîte lié à la question
   * @param AdapterSwingBox Adaptateur gérant la boîte
   * @param QuestionPanel JPanel de la question
   * @param ListOfQuestionsPanel Liste visuelle des questions
   */
  private void addActionListeners(final Box box, final AdapterSwingBox adapterBox, final QuestionPanel qstPanel, final ListOfQuestionsPanel listQstPanel) {
    this.btnRemove.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        adapterBox.deleteBox(box);
        listQstPanel.remove(qstPanel);
        listQstPanel.updateUI();
      }
    });
    this.btnMove.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        SelectionStateMachine.setState(SelectionStateMachine.MOVE);
      }
    });
    this.btnResize.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        SelectionStateMachine.setState(SelectionStateMachine.RESIZE);
      }
    });
  }
}
