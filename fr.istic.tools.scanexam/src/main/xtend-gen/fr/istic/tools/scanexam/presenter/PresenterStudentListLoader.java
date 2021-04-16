package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.mailing.StudentDataManager;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import java.io.File;
import java.util.Map;
import java.util.function.Function;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Classe pour charger la liste des élèves
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class PresenterStudentListLoader {
  /**
   * METHODES
   */
  public enum LoadState {
    SUCCESS,
    
    X_NOT_VALID,
    
    Y_NOT_VALID;
  }
  
  private final ServiceGraduation service;
  
  public PresenterStudentListLoader(final ServiceGraduation graduation) {
    this.service = graduation;
  }
  
  /**
   * Envoie les informations au service
   * @param file Chemin du fichier contenant la liste des étudiants
   * @param firstCell Première case à prendre en compte
   * @return un LoadState représentant l'état terminal du chargement des données
   */
  public PresenterStudentListLoader.LoadState loadFile(final File file, final String firstCell) {
    boolean _isValidX = StudentDataManager.isValidX(firstCell);
    boolean _not = (!_isValidX);
    if (_not) {
      return PresenterStudentListLoader.LoadState.X_NOT_VALID;
    } else {
      boolean _isValidY = StudentDataManager.isValidY(firstCell);
      boolean _not_1 = (!_isValidY);
      if (_not_1) {
        return PresenterStudentListLoader.LoadState.Y_NOT_VALID;
      }
    }
    StudentDataManager.loadData(file, firstCell);
    this.service.setStudentListPath(file.getAbsolutePath());
    this.service.setStudentListShift(firstCell);
    return PresenterStudentListLoader.LoadState.SUCCESS;
  }
  
  /**
   * @return le nombre de paires parsée par StudentDataManager, -1 si aucune n'a été parsée
   */
  public int getNumberPair() {
    final Function<Map<String, String>, Integer> _function = (Map<String, String> map) -> {
      return Integer.valueOf(map.size());
    };
    return (StudentDataManager.getNameToMailMap().<Integer>map(_function).orElse(Integer.valueOf((-1)))).intValue();
  }
  
  /**
   * @return la liste des données parsées sous forme de String. Chaîne vide si aucune données n'a été parsée
   */
  public String getStudentList() {
    final Function<Map<String, String>, String> _function = (Map<String, String> map) -> {
      final Function1<Map.Entry<String, String>, String> _function_1 = (Map.Entry<String, String> entry) -> {
        String _key = entry.getKey();
        String _plus = (_key + " - ");
        String _value = entry.getValue();
        return (_plus + _value);
      };
      return IterableExtensions.join(IterableExtensions.<Map.Entry<String, String>, String>map(map.entrySet(), _function_1), "\n");
    };
    return StudentDataManager.getNameToMailMap().<String>map(_function).orElse("");
  }
  
  /**
   * @return le path vers le fichier contenant la liste des étudiants. Chaîne vide si celui n'est pas défini
   */
  public String getStudentListPath() {
    return "";
  }
  
  /**
   * @return la première case à prendre en compte dans le fichier contenant la liste des étudiants
   */
  public String getStudentListShift() {
    return "A1";
  }
}
