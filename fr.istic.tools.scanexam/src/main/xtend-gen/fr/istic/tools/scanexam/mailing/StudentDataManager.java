package fr.istic.tools.scanexam.mailing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Gestion des informations de l'étudiant comme le nom et l'adresse mail
 */
@SuppressWarnings("all")
public class StudentDataManager {
  private static Map<String, String> mapNomEtudiant = new HashMap<String, String>();
  
  /**
   * Charge les données contenues dans le fichier permetant de lier les numéro étudiant ou nom étudiant à l'adresse mail
   * @param path chemin d'accés au fichier, si non renseigné, le fichier par défaut est resources/mailing/studentData.xls
   * @param startXY point situant le début des données pour lire le nom et prénom
   * @author Arthur & Antoine
   */
  public static void loadData(final File file, final String startXY) {
    try {
      FileInputStream _fileInputStream = new FileInputStream(file);
      final Workbook wb = WorkbookFactory.create(_fileInputStream);
      Sheet sheet = wb.getSheetAt(0);
      Pair<Integer, Integer> pair = StudentDataManager.convertExcelCoord(startXY);
      int x = (pair.getKey()).intValue();
      int y = (pair.getValue()).intValue();
      Logger.getGlobal().info(((("Début du tableau dans le fichier XLSX: " + Integer.valueOf(x)) + " : ") + Integer.valueOf(y)));
      Row row = sheet.getRow(y);
      while ((row != null)) {
        {
          final Cell cell = row.getCell(x);
          final String nom = cell.getStringCellValue();
          final String mail = row.getCell((x + 1)).getStringCellValue();
          StudentDataManager.mapNomEtudiant.put(nom, mail);
          y++;
          row = sheet.getRow(y);
        }
      }
    } catch (final Throwable _t) {
      if (_t instanceof FileNotFoundException) {
        final FileNotFoundException e = (FileNotFoundException)_t;
        e.printStackTrace();
      } else if (_t instanceof IOException) {
        final IOException e_1 = (IOException)_t;
        e_1.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    Logger _global = Logger.getGlobal();
    int _size = StudentDataManager.mapNomEtudiant.size();
    String _plus = ("Chargement des" + Integer.valueOf(_size));
    String _plus_1 = (_plus + "nom & adresse mail terminée ");
    _global.info(_plus_1);
  }
  
  /**
   * @return Liste de tout les noms d'élèves contenu dans le fichier fourni par l'utilisateur, null si aucun fichier fourni
   * @author Antoine
   */
  public static Optional<List<String>> getAllNames() {
    Optional<List<String>> _xifexpression = null;
    boolean _isEmpty = StudentDataManager.mapNomEtudiant.isEmpty();
    if (_isEmpty) {
      _xifexpression = Optional.<List<String>>empty();
    } else {
      _xifexpression = Optional.<List<String>>of(IterableExtensions.<String>toList(StudentDataManager.mapNomEtudiant.values()));
    }
    return _xifexpression;
  }
  
  /**
   * @return Map de tout les noms d'élèves -> adresse mail, contenu dans le fichier fourni par l'utilisateur
   * @author Antoine
   */
  public static Optional<Map<String, String>> getNameToMailMap() {
    Optional<Map<String, String>> _xifexpression = null;
    boolean _isEmpty = StudentDataManager.mapNomEtudiant.isEmpty();
    if (_isEmpty) {
      _xifexpression = Optional.<Map<String, String>>empty();
    } else {
      _xifexpression = Optional.<Map<String, String>>of(StudentDataManager.mapNomEtudiant);
    }
    return _xifexpression;
  }
  
  public static Pair<Integer, Integer> convertExcelCoord(final String data) {
    final String[] xy = data.split("(?<=[A-Z]++)");
    int x = 0;
    int y = 0;
    try {
      int _parseInt = Integer.parseInt(xy[1]);
      int _minus = (_parseInt - 1);
      y = _minus;
    } catch (final Throwable _t) {
      if (_t instanceof NumberFormatException) {
        final NumberFormatException e = (NumberFormatException)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    final String xTemp = (xy[0]).toLowerCase();
    int i = 0;
    while (((x <= 16_384) && (i < xTemp.length()))) {
      int _x = x;
      char _charAt = xTemp.charAt(i);
      int _minus = (_charAt - 'a');
      int _plusPlus = i++;
      double _pow = Math.pow(26, _plusPlus);
      int _multiply = (_minus * ((int) _pow));
      x = (_x + _multiply);
    }
    Logger.getGlobal().info("Conversion coord Excel vers Pair<Interger,Integer> terminé.");
    if ((y >= 1_048_576)) {
      String _plus = (Integer.valueOf(x) + " exceeds the legal number of rows: 1 048 576");
      throw new IllegalArgumentException(_plus);
    }
    if ((x > 1_048_576)) {
      String _plus_1 = (Integer.valueOf(x) + " exceeds the legal number of columns: 16 384");
      throw new IllegalArgumentException(_plus_1);
    }
    return new Pair<Integer, Integer>(Integer.valueOf(x), Integer.valueOf(y));
  }
}
