package fr.istic.tools.scanexam.mailing

import java.util.List
import java.io.File
import java.io.FileReader
import java.io.BufferedReader
import java.io.PrintWriter
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFCell
import java.io.FileNotFoundException
import java.io.IOException
import java.util.HashMap
import java.util.Map
import fr.istic.tools.scanexam.services.Service
import java.util.logging.Logger


/**
 * Gestion des informations de l'étudiant comme le nom et l'adresse mail
 */
class StudentDataManager {
	
	 static Service service

	new(Service serv) {
		service = serv
	}
	
	
	var static Map<String,String> mapNomEtudiant = new HashMap();
 	
 	/**
 	 * Enregistre le chemin d'accés 
 	 * @Param files Fichier XLS contenant le nom étudiant (ou numéro étudiant) et l'adresse mail
 	 * @author Arthur & Antoine
 	 */
 	def static save(File files){
		var String chemin = files.absolutePath
		//var String nom = "nomExam" + ".txt"
		var String nom = service.examName + ".txt"
		var PrintWriter writer = new PrintWriter(nom,'UTF-8');
		writer.println(chemin)
		writer.close()
		loadData("A1")
 	}
	
	/**
	 * 
	 * Charge les données contenues dans le fichier permetant de lier les numéro étudiant ou nom étudiant à l'adresse mail
	 * @param path chemin d'accés au fichier, si non renseigné, le fichier par défaut est resources/mailing/studentData.xls
	 * @param startXY point situant le début des données pour lire le nom et prénom
	 * @author Arthur & Antoine
	 */
	 def static void loadData(String startXY){
	    try{
	    //Lecture fichier liant une copie à un élève
	    var File cheminInfo = new File(service.examName + ".txt")
	    //var File cheminInfo = new File("nomExam.txt")
	    var FileReader fx = new FileReader(cheminInfo)
	    var BufferedReader f = new BufferedReader(fx)
	    var File informationMail = new File(f.readLine + ".xls")
	    
	   // val files = ResourcesUtils.getInputStreamResource("mailing/anonymat_nom_mail.xls")
	    
        var POIFSFileSystem doc = new POIFSFileSystem(informationMail)
        var HSSFWorkbook wb = new HSSFWorkbook(doc)
        var HSSFSheet sheet = wb.getSheetAt(0)
        
        //Convertion de la coordonnée de départ XY en couple d'entier
        var pair = convertExcelCoord(startXY)
        var int x = pair.key
        var int y = pair.value
        Logger.getGlobal.info("Début du tableau dans le fichier XLS: " + x + " : " + y )
        
        //Lecture d'une cellule
        var HSSFRow row = sheet.getRow(y)
        var HSSFCell cell = row.getCell(x)   
        
        //Parcourt notre tableau
        var nom = ""
        var mail = ""
        while (cell.getStringCellValue() != "") {
            nom = cell.getStringCellValue()
            mail = row.getCell(y+1).stringCellValue
            mapNomEtudiant.put(nom,mail)
            x++;
          } 
        }
	    catch (FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	    Logger.getGlobal.info("Chargement des" + mapNomEtudiant.size + "nom & adresse mail terminée ")
	}
	
	/**
	 * @return Liste de tout les noms d'élèves contenu dans le fichier fourni par l'utilisateur
	 * @author Antoine
	 */
	def static List<String> getAllNames(){
		loadData("A0")
		return mapNomEtudiant.values.toList
	}
	
	/**
	 * @return Map de tout les noms d'élèves -> adresse mail, contenu dans le fichier fourni par l'utilisateur
	 * @author Antoine
	 */
	def static Map<String,String> getNameToMailMap(){
		loadData("A0")
		return mapNomEtudiant
	}
	
	def static Pair<Integer,Integer> convertExcelCoord(String data){
		var x = 0
		var y = 0
		data.toLowerCase
		if(data.length > 2){
			var i = 0
			var intZone = false
			while( i < data.length || intZone){
				var ch = data.charAt(i)
				if((ch >= 'a' && ch <= 'z') ){
					x+= ch - 'a'
				}
				else intZone = true
				i++
			}
			y = Integer.parseInt(data.substring(i,data.length)) - 1
		}  
		Logger.getGlobal.info("Conversion coord Excel vers Pair<Interger,Integer> terminé.")
		return new Pair(x,y)
	}
	
	
}