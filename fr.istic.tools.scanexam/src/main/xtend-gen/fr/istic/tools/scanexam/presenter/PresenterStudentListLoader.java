package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.mailing.StudentDataManager;
import fr.istic.tools.scanexam.services.ServiceGraduation;
import java.io.File;

/**
 * Classe pour charger la liste des élèves
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class PresenterStudentListLoader {
  private final ServiceGraduation service;
  
  /**
   * METHODES
   */
  public PresenterStudentListLoader(final ServiceGraduation graduation) {
    this.service = graduation;
  }
  
  /**
   * Envoie les informations au service
   * @param file Chemin du fichier contenant la liste des étudiants
   * @param firstCell Première case à prendre en compte
   * @return true si le fichier a bien pu être chargé, false sinon
   */
  public boolean loadFile(final File file, final String firstCell) {
    boolean _xblockexpression = false;
    {
      StudentDataManager.loadData(file, firstCell);
      this.service.setStudentListPath(file.getAbsolutePath());
      this.service.setStudentListShift(firstCell);
      _xblockexpression = true;
    }
    return _xblockexpression;
  }
}
