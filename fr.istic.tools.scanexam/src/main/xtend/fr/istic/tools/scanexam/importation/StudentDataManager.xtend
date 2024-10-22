package fr.istic.tools.scanexam.importation

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.ArrayList
import java.util.Arrays
import java.util.List
import java.util.logging.Logger
import org.apache.logging.log4j.LogManager
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory

/**
 * Gestion des informations de l'étudiant comme le nom et l'adresse mail
 */
class StudentDataManager {

	static val logger = LogManager.logger

	val static MAX_ROW = 1_048_576
	val static MAX_COLUMN = 16_384

	/**
	 * 
	 * Charge les données contenues dans le fichier permetant de lier les numéro étudiant ou nom étudiant à l'adresse mail
	 * @param path chemin d'accés au fichier, si non renseigné, le fichier par défaut est resources/mailing/studentData.xls
	 * @param startXY point situant le début des données pour lire le nom et prénom
	 * @author Arthur & Antoine
	 */
	def static List<List<String>> loadData(File file, String startXY) {
		val List<List<String>> studentsData = new ArrayList

		try(val wb = WorkbookFactory.create(new FileInputStream(file))) {
			// CharBuffer.wrap(startXY.)
			var sheet = wb.getSheetAt(0)

			// Convertion de la coordonnée de départ XY en couple d'entier
			var pair = StudentDataManager.parseCoords(startXY)
			var int x = pair.key
			var int y = pair.value
			logger.info("Starting parsing data from (" + x + ", " + y + ")")

			// Lecture d'une cellule
			var Row row = sheet.getRow(y)

			val formatter = new DataFormatter();
			// Parcourt notre tableau
			while (row !== null) {
				val String id = formatter.formatCellValue(row.getCell(x))
				val String lastName = formatter.formatCellValue(row.getCell(x + 1))
				val String firstName = formatter.formatCellValue(row.getCell(x + 2))
				val String mail = formatter.formatCellValue(row.getCell(x + 3))
				studentsData.add(Arrays.asList(id, lastName, firstName, mail))
				y++
				row = sheet.getRow(y);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("Datas loaded: " + studentsData.size + " students found")
		return studentsData
	}

	/**
	 * @param pos la position de la cellule
	 * @return vrai si la position en X de la cellule est valide, c'est-à-dire si celle-ci n'excède pas la limite de 16 384, faux sinon
	 */
	def static boolean isValidX(String pos) {
		val x = pos.split("(?<=[A-Z]++)").get(0)
		val xInt = parseX(x)
		return xInt < MAX_COLUMN
	}

	/**
	 * @param pos la position de la cellule
	 * @return vrai si la position en Y de la cellule est valide, c'est-à-dire si celle-ci n'excède pas la limite de 1 048 576, faux sinon
	 * @throw NumberFormatException si la position en Y n'est pas parsable en Int
	 */
	def static boolean isValidY(String pos) throws NumberFormatException {
		val y = pos.split("(?<=[A-Z]++)").get(1)
		val yInt = parseY(y)
		return yInt < MAX_ROW
	}

	/**
	 * Parse les coordonnées au format 'A1' en des coordonnées de tableaux '(0, 0)'
	 * @param data la position à parser
	 * @return une paire d'entier correspondant à la position parsée
	 * @throw IllegalArgumentException si les coordonnées excèdent les limites légales ({@link #isValidX isValidX}, {@link #isValidY isValidY})
	 * @throw NumberFormatException si la coordonnée Y n'est pas parsable en Int
	 */
	private def static Pair<Integer, Integer> parseCoords(String data) {
		val xy = data.split("(?<=[A-Z]++)")

		val x = parseX(xy.get(0))
		val y = parseY(xy.get(1))

		Logger.getGlobal.info("Conversion coord Excel vers Pair<Interger,Integer> terminé.")

		if (y >= MAX_ROW)
			throw new IllegalArgumentException(x + " exceeds the legal number of rows: " + MAX_ROW)

		if (x > MAX_COLUMN)
			throw new IllegalArgumentException(x + " exceeds the legal number of columns: " + MAX_COLUMN)
		return new Pair(x, y)
	}

	/**
	 * Parse la coordonnée X au format '[A-Z]+' en coordonnée de tableau
	 * @param x la coordonnée X à parser
	 * @return un entier correspondant à la position en X parsée
	 */
	private def static int parseX(String x) {
		var xInt = 0

		val xTemp = x.toLowerCase
		var i = 0
		while (xInt < MAX_COLUMN && i < xTemp.length)
			xInt += (xTemp.charAt(i) - 'a') * Math.pow(26, i++) as int

		return xInt
	}

	/**
	 * Parse la coordonnée Y au format '[0-9]+' en coordonnée de tableau
	 * @param y la coordonnée Y à parser
	 * @return un entier correspondant à la position en Y parsée
	 */
	private def static int parseY(String y) throws NumberFormatException {
		return Integer.parseInt(y) - 1
	}

}
