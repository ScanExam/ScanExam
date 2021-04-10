package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.presenter.PresenterStudentSheetLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

@SuppressWarnings("all")
public class ControllerFxStudentSheetLoader {
  /**
   * RadioButton de l'option "Utiliser le modèle chargé"
   */
  @FXML
  private RadioButton rbUseLoaded;
  
  /**
   * RadioButton de l'option "Charger un nouveau modèle"
   */
  @FXML
  private RadioButton rbLoadModel;
  
  /**
   * HBox avec les nœuds à activer si deuxième RadioButton sélectionné
   */
  @FXML
  private HBox hBoxLoad;
  
  /**
   * TextField de la saisie du path pour le nouveau modèle
   */
  @FXML
  private TextField txtFldFile;
  
  /**
   * Button de chargement du modèle
   */
  @FXML
  private Button btnBrowse;
  
  /**
   * Button de validation du formulaire
   */
  @FXML
  private Button btnOk;
  
  private PresenterStudentSheetLoader presStudentListLoader;
  
  /**
   * Initialise le composant avec le presenter composé en paramètre
   * @param loader le presenter
   */
  public void initialize(final PresenterStudentSheetLoader loader) {
    this.presStudentListLoader = loader;
    this.hBoxLoad.disableProperty().bind(this.rbLoadModel.selectedProperty().not());
  }
  
  @FXML
  public Object quit() {
    return null;
  }
  
  @FXML
  public Object saveAndQuit() {
    return null;
  }
}
