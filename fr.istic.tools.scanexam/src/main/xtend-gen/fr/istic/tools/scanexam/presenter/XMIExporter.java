package fr.istic.tools.scanexam.presenter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@SuppressWarnings("all")
public class XMIExporter {
  private static String head;
  
  private static String body;
  
  private static String tail;
  
  /**
   * @param list
   */
  private static void createBody(final /* BoxList */Object list) {
    throw new Error("Unresolved compilation problems:"
      + "\nBox cannot be resolved to a type."
      + "\ngetList cannot be resolved"
      + "\ngetTitle cannot be resolved"
      + "\ngetX cannot be resolved"
      + "\ngetY cannot be resolved"
      + "\ngetWidth cannot be resolved"
      + "\ngetWidth cannot be resolved"
      + "\ngetNbPage cannot be resolved");
  }
  
  private static void createHead(final /* BoxList */Object list) {
    XMIExporter.head = ((((((("<?xml version=\"1.0\" encoding=\"ASCII\"?>\n" + "<scanexam:Exam xmi:version=\"2.0\" ") + "xmlns:xmi=\"http://www.omg.org/XMI\" ") + "xmlns:scanexam=\"fr.istic.tools.scanexam\" ") + "label=\"PFO_december_19\" ") + "folderPath=\"/Users/steven/Documents/ISTIC/2020-2021/PFO/Examen/dec19/CopiesScan/png/\" ") + "numberOfPages=\"6\" ") + "scale=\"42\"> \n");
  }
  
  private static void createTail(final /* BoxList */Object list) {
    XMIExporter.tail = "</scanexam:Exam>";
  }
  
  /**
   * @return
   */
  public static String exportString() {
    return ((XMIExporter.head + XMIExporter.body) + XMIExporter.tail);
  }
  
  private static void exportFile() throws IOException {
    Path file = Paths.get("export.xml");
    Files.write(file, Arrays.<String>asList(XMIExporter.exportString().split("\n")), StandardCharsets.UTF_8);
  }
  
  /**
   * Creates a XMI file at the root of the project.
   * @param boxList The BoxList to convert to XMI data.
   * @return 	<b>true</b> if it succeeds.<br>
   * <b>false</b> if it fails.
   */
  public static boolean exportToXMI(final /* BoxList */Object boxList) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method createHead(BoxList) from the type XMIExporter refers to the missing type BoxList"
      + "\nThe method createBody(BoxList) from the type XMIExporter refers to the missing type BoxList"
      + "\nThe method createTail(BoxList) from the type XMIExporter refers to the missing type BoxList");
  }
}
