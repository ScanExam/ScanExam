package fr.istic.tools.scanexam.mailing;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.services.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Gestion des informations de l'étudiant comme le nom et l'adresse mail
 */
@SuppressWarnings("all")
public class StudentDataManager {
  private static Service service;
  
  public StudentDataManager(final Service serv) {
    StudentDataManager.service = serv;
  }
  
  private static Map<String, String> mapNomEtudiant = new HashMap<String, String>();
  
  /**
   * Enregistre le chemin d'accés
   * @Param files Fichier XLS contenant le nom étudiant (ou numéro étudiant) et l'adresse mail
   * @author Arthur & Antoine
   */
  public static void save(final File files) {
    try {
      String chemin = files.getAbsolutePath();
      String _examName = StudentDataManager.service.getExamName();
      String nom = (_examName + ".txt");
      PrintWriter writer = new PrintWriter(nom, "UTF-8");
      writer.println(chemin);
      writer.close();
      StudentDataManager.loadData("A1");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Charge les données contenues dans le fichier permetant de lier les numéro étudiant ou nom étudiant à l'adresse mail
   * @param path chemin d'accés au fichier, si non renseigné, le fichier par défaut est resources/mailing/studentData.xls
   * @param startXY point situant le début des données pour lire le nom et prénom
   * @author Arthur & Antoine
   */
  public static void loadData(final String startXY) {
    try {
      String _examName = StudentDataManager.service.getExamName();
      String _plus = (_examName + ".txt");
      File cheminInfo = new File(_plus);
      FileReader fx = new FileReader(cheminInfo);
      BufferedReader f = new BufferedReader(fx);
      String _readLine = f.readLine();
      String _plus_1 = (_readLine + ".xls");
      File informationMail = new File(_plus_1);
      POIFSFileSystem doc = new POIFSFileSystem(informationMail);
      HSSFWorkbook wb = new HSSFWorkbook(doc);
      HSSFSheet sheet = wb.getSheetAt(0);
      Pair<Integer, Integer> pair = StudentDataManager.convertExcelCoord(startXY);
      int x = (pair.getKey()).intValue();
      int y = (pair.getValue()).intValue();
      Logger.getGlobal().info(((("Début du tableau dans le fichier XLS: " + Integer.valueOf(x)) + " : ") + Integer.valueOf(y)));
      HSSFRow row = sheet.getRow(y);
      HSSFCell cell = row.getCell(x);
      String nom = "";
      String mail = "";
      while ((!Objects.equal(cell.getStringCellValue(), ""))) {
        {
          nom = cell.getStringCellValue();
          mail = row.getCell((y + 1)).getStringCellValue();
          StudentDataManager.mapNomEtudiant.put(nom, mail);
          x++;
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
   * @return Liste de tout les noms d'élèves contenu dans le fichier fourni par l'utilisateur
   * @author Antoine
   */
  public static List<String> getAllNames() {
    StudentDataManager.loadData("A0");
    return IterableExtensions.<String>toList(StudentDataManager.mapNomEtudiant.values());
  }
  
  /**
   * @return Map de tout les noms d'élèves -> adresse mail, contenu dans le fichier fourni par l'utilisateur
   * @author Antoine
   */
  public static Map<String, String> getNameToMailMap() {
    StudentDataManager.loadData("A0");
    return StudentDataManager.mapNomEtudiant;
  }
  
  public static Pair<Integer, Integer> convertExcelCoord(final String data) {
    int x = 0;
    int y = 0;
    data.toLowerCase();
    int _length = data.length();
    boolean _greaterThan = (_length > 2);
    if (_greaterThan) {
      int i = 0;
      boolean intZone = false;
      while (((i < data.length()) || intZone)) {
        {
          char ch = data.charAt(i);
          if (((ch >= 'a') && (ch <= 'z'))) {
            int _x = x;
            x = (_x + (ch - 'a'));
          } else {
            intZone = true;
          }
          i++;
        }
      }
      int _parseInt = Integer.parseInt(data.substring(i, data.length()));
      int _minus = (_parseInt - 1);
      y = _minus;
    }
    Logger.getGlobal().info("Conversion coord Excel vers Pair<Interger,Integer> terminé.");
    return new Pair<Integer, Integer>(Integer.valueOf(x), Integer.valueOf(y));
  }
}
