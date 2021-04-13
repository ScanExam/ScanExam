package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.services.ServiceGraduation;
import java.util.Objects;

/**
 * Presenter used to import and export xmi files from the view
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterImportExportXMI {
  private final ServiceGraduation service;
  
  /**
   * Constructor
   * @param graduation instance of ServiceGraduation (not null)
   */
  public PresenterImportExportXMI(final ServiceGraduation graduation) {
    Objects.<ServiceGraduation>requireNonNull(graduation);
    this.service = graduation;
  }
  
  /**
   * utilisé pour charger une sauvegarde de correction d'examen
   * Charge un fichier de correction d'examen a partir du disque.
   * @params pathXmiFile L'emplacement du fichier.
   * @returns "true" si le fichier a bien été chargé, "false"
   */
  public boolean loadExamCorrectionTemplate(final String pathXmiFile) {
    Objects.<String>requireNonNull(pathXmiFile);
    boolean _openCorrectionTemplate = this.service.openCorrectionTemplate(pathXmiFile);
    if (_openCorrectionTemplate) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * utilisé pour charger une sauvegarde de création d'examen
   * Charge un fichier de creation d'examen a partir du disque.
   * @params pathXmiFile L'emplacement du fichier.
   * @returns "true" si le fichier a bien été chargé, "false"
   */
  public boolean loadExamCreationTemplate(final String pathXmiFile) {
    Objects.<String>requireNonNull(pathXmiFile);
    boolean _openCreationTemplate = this.service.openCreationTemplate(pathXmiFile);
    if (_openCreationTemplate) {
      return true;
    } else {
      return false;
    }
  }
}
