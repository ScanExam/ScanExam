package fr.istic.tools.scanexam.mailing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Gestion des informations de l'étudiant comme le nom et l'adresse mail
 */
@SuppressWarnings("all")
public class StudentDataManager {
  private static Map<String, String> mapNomEtudiant = new HashMap<String, String>();
  
  private static final Logger logger = LogManager.getLogger();
  
  private static final int MAX_ROW = 1_048_576;
  
  private static final int MAX_COLUMN = 16_384;
  
  /**
   * Charge les données contenues dans le fichier permetant de lier les numéro étudiant ou nom étudiant à l'adresse mail
   * @param path chemin d'accés au fichier, si non renseigné, le fichier par défaut est resources/mailing/studentData.xls
   * @param startXY point situant le début des données pour lire le nom et prénom
   * @author Arthur & Antoine
   */
  public static void loadData(final File file, final String startXY) {
    try (final Workbook wb = new Function0<Workbook>() {
      @Override
      public Workbook apply() {
        try {
          FileInputStream _fileInputStream = new FileInputStream(file);
          return WorkbookFactory.create(_fileInputStream);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    }.apply()) {
      Sheet sheet = wb.getSheetAt(0);
      Pair<Integer, Integer> pair = StudentDataManager.parseCoords(startXY);
      int x = (pair.getKey()).intValue();
      int y = (pair.getValue()).intValue();
      StudentDataManager.logger.info((((("Starting parsing data from (" + Integer.valueOf(x)) + ", ") + Integer.valueOf(y)) + ")"));
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
    int _size = StudentDataManager.mapNomEtudiant.size();
    String _plus = ("Datas loaded: " + Integer.valueOf(_size));
    String _plus_1 = (_plus + " pairs found");
    StudentDataManager.logger.info(_plus_1);
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
      _xifexpression = Optional.<List<String>>of(IterableExtensions.<String>toList(StudentDataManager.mapNomEtudiant.keySet()));
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
      _xifexpression = Optional.<Map<String, String>>of(Collections.<String, String>unmodifiableMap(StudentDataManager.mapNomEtudiant));
    }
    return _xifexpression;
  }
  
  /**
   * @param pos la position de la cellule
   * @return vrai si la position en X de la cellule est valide, c'est-à-dire si celle-ci n'excède pas la limite de 16 384, faux sinon
   */
  public static boolean isValidX(final String pos) {
    final String x = pos.split("(?<=[A-Z]++)")[0];
    final int xInt = StudentDataManager.parseX(x);
    return (xInt < StudentDataManager.MAX_COLUMN);
  }
  
  /**
   * @param pos la position de la cellule
   * @return vrai si la position en Y de la cellule est valide, c'est-à-dire si celle-ci n'excède pas la limite de 1 048 576, faux sinon
   * @throw NumberFormatException si la position en Y n'est pas parsable en Int
   */
  public static boolean isValidY(final String pos) throws NumberFormatException {
    final String y = pos.split("(?<=[A-Z]++)")[1];
    final int yInt = StudentDataManager.parseY(y);
    return (yInt < StudentDataManager.MAX_ROW);
  }
  
  /**
   * Parse les coordonnées au format 'A1' en des coordonnées de tableaux '(0, 0)'
   * @param data la position à parser
   * @return une paire d'entier correspondant à la position parsée
   * @throw IllegalArgumentException si les coordonnées excèdent les limites légales ({@link #isValidX isValidX}, {@link #isValidY isValidY})
   * @throw NumberFormatException si la coordonnée Y n'est pas parsable en Int
   */
  private static Pair<Integer, Integer> parseCoords(final String data) {
    final String[] xy = data.split("(?<=[A-Z]++)");
    final int x = StudentDataManager.parseX(xy[0]);
    final int y = StudentDataManager.parseY(xy[1]);
    java.util.logging.Logger.getGlobal().info("Conversion coord Excel vers Pair<Interger,Integer> terminé.");
    if ((y >= StudentDataManager.MAX_ROW)) {
      String _plus = (Integer.valueOf(x) + " exceeds the legal number of rows: ");
      String _plus_1 = (_plus + Integer.valueOf(StudentDataManager.MAX_ROW));
      throw new IllegalArgumentException(_plus_1);
    }
    if ((x > StudentDataManager.MAX_COLUMN)) {
      String _plus_2 = (Integer.valueOf(x) + " exceeds the legal number of columns: ");
      String _plus_3 = (_plus_2 + Integer.valueOf(StudentDataManager.MAX_COLUMN));
      throw new IllegalArgumentException(_plus_3);
    }
    return new Pair<Integer, Integer>(Integer.valueOf(x), Integer.valueOf(y));
  }
  
  /**
   * Parse la coordonnée X au format '[A-Z]+' en coordonnée de tableau
   * @param x la coordonnée X à parser
   * @return un entier correspondant à la position en X parsée
   */
  private static int parseX(final String x) {
    int xInt = 0;
    final String xTemp = x.toLowerCase();
    int i = 0;
    while (((xInt < StudentDataManager.MAX_COLUMN) && (i < xTemp.length()))) {
      int _xInt = xInt;
      char _charAt = xTemp.charAt(i);
      int _minus = (_charAt - 'a');
      int _plusPlus = i++;
      double _pow = Math.pow(26, _plusPlus);
      int _multiply = (_minus * ((int) _pow));
      xInt = (_xInt + _multiply);
    }
    return xInt;
  }
  
  /**
   * Parse la coordonnée Y au format '[0-9]+' en coordonnée de tableau
   * @param y la coordonnée Y à parser
   * @return un entier correspondant à la position en Y parsée
   */
  private static int parseY(final String y) throws NumberFormatException {
    int _parseInt = Integer.parseInt(y);
    return (_parseInt - 1);
  }
}
