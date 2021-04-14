package fr.istic.tools.scanexam.presenter;

import java.io.File;

/**
 * Presenter pourla création d'un modèle d'examen
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class PresenterTemplateCreator {
  /**
   * Presenter de l'édition de l'examen
   */
  private final PresenterEdition presenter;
  
  /**
   * Nom du modèle
   */
  private String templateName;
  
  /**
   * Fichier du modèle
   */
  private File templateFile;
  
  /**
   * Constructeur
   * @param presenter Presenter de l'édition de l'examen
   */
  public PresenterTemplateCreator(final PresenterEdition presenter) {
    this.presenter = presenter;
  }
  
  /**
   * Crée le modèle à partir du pdf de l'examen
   * @param file Pdf de l'examen
   */
  public void createTemplate() {
    this.presenter.getPresenterPdf().create(this.templateName, this.templateFile);
  }
  
  /**
   * Met à jour le nom du modèle
   * @param templateFile Nouveau nom du modèle
   */
  public void setTemplateName(final String templateName) {
    this.templateName = templateName;
  }
  
  /**
   * Met à jour le fichier pdf de l'examen
   * @param templateFile Nouveau pdf de l'examen
   */
  public void setTemplateFile(final File templateFile) {
    this.templateFile = templateFile;
  }
  
  /**
   * Vérifie que le nom du modèle est valide
   * @param name Nom du modèle
   * @return true si le nom est valide, false sinon
   */
  public boolean checkName(final String name) {
    return true;
  }
  
  /**
   * Vérifie que le chemin du fichier du modèle est valide
   * @param filepath Chemin du fichier
   * @return true si le chemin du fichier est valide, false sinon
   */
  public boolean checkFilepath(final String filepath) {
    return true;
  }
}
