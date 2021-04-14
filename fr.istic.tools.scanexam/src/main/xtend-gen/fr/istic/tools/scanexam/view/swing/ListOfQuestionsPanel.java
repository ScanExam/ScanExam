package fr.istic.tools.scanexam.view.swing;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Classe pour afficher la liste des questions en swing
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class ListOfQuestionsPanel extends JPanel {
  /**
   * AdapterSwingBox Adaptateur gérant la boîte
   */
  private AdapterSwingBox adapterBox;
  
  /**
   * Constructeur
   * @param AdapterSwingBox Adaptateur gérant la boîte
   */
  public ListOfQuestionsPanel(final AdapterSwingBox adapterBox) {
    this.adapterBox = adapterBox;
    BoxLayout _boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
    this.setLayout(_boxLayout);
  }
  
  /**
   * Ajoute une question
   * @param Box Boîte lié à la question
   */
  public void addQst(final Box box) {
    JPanel pnlQst = new QuestionPanel(box, this.adapterBox, this);
    this.add(pnlQst);
    this.updateUI();
  }
}
