package fr.istic.tools.scanexam.mailing

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Optional
import java.util.logging.Logger
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory

/**
 * Gestion des informations de l'étudiant comme le nom et l'adresse mail
 */
class StudentDataManager {

	var static Map<String, String> mapNomEtudiant = new HashMap();

	/**
	 * Enregistre le chemin d'accés 
	 * @Param files Fichier XLS contenant le nom étudiant (ou numéro étudiant) et l'adresse mail
	 * @author Arthur & Antoine
	 */
	/*def static save(File files) {
		var String chemin = files.absolutePath
		// var String nom = "nomExam" + ".txt"
		var String nom = service.examName + ".txt"
		var PrintWriter writer = new PrintWriter(nom, 'UTF-8');
		writer.println(chemin)
		writer.close()
		loadData("A1")
	}*/

	/**
	 * 
	 * Charge les données contenues dans le fichier permetant de lier les numéro étudiant ou nom étudiant à l'adresse mail
	 * @param path chemin d'accés au fichier, si non renseigné, le fichier par défaut est resources/mailing/studentData.xls
	 * @param startXY point situant le début des données pour lire le nom et prénom
	 * @author Arthur & Antoine
	 */
	def static void loadData(File file, String startXY) {
		try {
			//CharBuffer.wrap(startXY.)
			val wb = WorkbookFactory.create(new FileInputStream(file));
			var sheet = wb.getSheetAt(0)

			// Convertion de la coordonnée de départ XY en couple d'entier
			var pair = convertExcelCoord(startXY)
			var int x = pair.key
			var int y = pair.value
			Logger.getGlobal.info("Début du tableau dans le fichier XLSX: " + x + " : " + y)

			// Lecture d'une cellule
			var Row row = sheet.getRow(y)

			// Parcourt notre tableau
			while (row !== null) {
				val cell = row.getCell(x)
				val nom = cell.getStringCellValue()
				val mail = row.getCell(x + 1).stringCellValue
				StudentDataManager.mapNomEtudiant.put(nom, mail)
				y++
				row = sheet.getRow(y);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.getGlobal.info("Chargement des" + mapNomEtudiant.size + "nom & adresse mail terminée ")
	}

	/**
	 * @return Liste de tout les noms d'élèves contenu dans le fichier fourni par l'utilisateur, null si aucun fichier fourni
	 * @author Antoine
	 */
	def static Optional<List<String>> getAllNames() {
		return mapNomEtudiant.isEmpty ? Optional.empty : Optional.of(mapNomEtudiant.values.toList)
	}

	/**
	 * @return Map de tout les noms d'élèves -> adresse mail, contenu dans le fichier fourni par l'utilisateur
	 * @author Antoine
	 */
	def static Optional<Map<String, String>> getNameToMailMap() {
		return mapNomEtudiant.isEmpty ? Optional.empty : Optional.of(mapNomEtudiant)
	}

	def static Pair<Integer, Integer> convertExcelCoord(String data) {
		val xy = data.split("(?<=[A-Z]++)") 
		var x = 0
		var y = 0
		try {
			y = Integer.parseInt(xy.get(1)) - 1
		} catch(NumberFormatException e) {
			e.printStackTrace
		}
		
		val xTemp = xy.get(0).toLowerCase
		var i = 0
		while(x <= 16_384 && i < xTemp.length)
			x += (xTemp.charAt(i) - 'a') * Math.pow(26, i++) as int
			
		Logger.getGlobal.info("Conversion coord Excel vers Pair<Interger,Integer> terminé.")
		
		if(y >= 1_048_576)
			throw new IllegalArgumentException(x + " exceeds the legal number of rows: 1 048 576")
		
		if(x > 1_048_576)
			throw new IllegalArgumentException(x + " exceeds the legal number of columns: 16 384")
		return new Pair(x, y)
	}

}
