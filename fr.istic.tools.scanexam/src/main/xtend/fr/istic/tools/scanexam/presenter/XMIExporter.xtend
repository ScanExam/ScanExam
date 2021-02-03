package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.box.Box
import fr.istic.tools.scanexam.box.BoxList
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Arrays

class XMIExporter {
	// TODO add more implementation : 
	// grading zones
	// total pages 
	// scale
	//
	static String head
	static String body
	static String tail

	/** 
	 * @param list
	 */
	def private static void createBody(BoxList list) {
		var String s = ""
		for (Box box : list.getList()) {
			s = s + "\t<questions label=\"" + box.getTitle() + "\" defaultGradeIndex=\"NaN\" weight=\"NaN\"> \n";
			s = s + "\t\t<zone x=\"" + String.format("%.2f",box.getX()) + "\" y=\"" + String.format("%.2f",box.getY()) + "\" w=\"" + String.format("%.2f",box.getWidth()) + "\" h=\"" + String.format("%.2f",box.getWidth()) + "\" page=\""+box.getNbPage()+"\"/> \n" ;
			s = s + "\t\t<markzone NYI /> \n";
			s = s + "\t</questions> \n";
		}
		body = s
	}

	def private static void createHead(BoxList list) {
		head = "<?xml version=\"1.0\" encoding=\"ASCII\"?>\n"
		+"<scanexam:Exam xmi:version=\"2.0\" "
		+ "xmlns:xmi=\"http://www.omg.org/XMI\" "
		+ "xmlns:scanexam=\"fr.istic.tools.scanexam\" "
		+ "label=\"PFO_december_19\" "
		+ "folderPath=\"/Users/steven/Documents/ISTIC/2020-2021/PFO/Examen/dec19/CopiesScan/png/\" "
		+ "numberOfPages=\"6\" "
		+ "scale=\"42\"> \n";
	}

	def private static void createTail(BoxList list) {
		tail = "</scanexam:Exam>"
	}

	/** 
	 * @return
	 */
	def static String exportString() {
		return head + body + tail
	}

	/** 
	 */
	def private static void exportFile() throws IOException {
		var Path file = Paths::get("export.xml")
		Files::write(file, Arrays::asList(exportString().split("\n")), StandardCharsets::UTF_8)
	}

	/** 
	 * Creates a XMI file at the root of the project.
	 * @param boxList The BoxList to convert to XMI data.
	 * @return 	<b>true</b> if it succeeds.<br>
	 * <b>false</b> if it fails.
	 */
	def static boolean exportToXMI(BoxList boxList) {
		createHead(boxList)
		createBody(boxList)
		createTail(boxList)
		try {
			exportFile()
		} catch (IOException e) {
			e.printStackTrace()
			return false
		}

		return true
	}
}
