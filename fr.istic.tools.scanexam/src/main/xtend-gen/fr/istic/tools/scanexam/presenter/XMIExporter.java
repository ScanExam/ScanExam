package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.box.Box;
import fr.istic.tools.scanexam.box.BoxList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class XMIExporter {
  private static String head;
  
  private static String body;
  
  private static String tail;
  
  /**
   * @param list
   */
  private static void createBody(final BoxList list) {
    String s = "";
    List<Box> _list = list.getList();
    for (final Box box : _list) {
      {
        String _title = box.getTitle();
        String _plus = ((s + "\t<questions label=\"") + _title);
        String _plus_1 = (_plus + "\" defaultGradeIndex=\"NaN\" weight=\"NaN\"> \n");
        s = _plus_1;
        String _format = String.format("%.2f", Double.valueOf(box.getX()));
        String _plus_2 = ((s + "\t\t<zone x=\"") + _format);
        String _plus_3 = (_plus_2 + "\" y=\"");
        String _format_1 = String.format("%.2f", Double.valueOf(box.getY()));
        String _plus_4 = (_plus_3 + _format_1);
        String _plus_5 = (_plus_4 + "\" w=\"");
        String _format_2 = String.format("%.2f", Double.valueOf(box.getWidth()));
        String _plus_6 = (_plus_5 + _format_2);
        String _plus_7 = (_plus_6 + "\" h=\"");
        String _format_3 = String.format("%.2f", Double.valueOf(box.getWidth()));
        String _plus_8 = (_plus_7 + _format_3);
        String _plus_9 = (_plus_8 + "\" page=\"");
        int _nbPage = box.getNbPage();
        String _plus_10 = (_plus_9 + Integer.valueOf(_nbPage));
        String _plus_11 = (_plus_10 + "\"/> \n");
        s = _plus_11;
        s = (s + "\t\t<markzone NYI /> \n");
        s = (s + "\t</questions> \n");
      }
    }
    XMIExporter.body = s;
  }
  
  private static void createHead(final BoxList list) {
    XMIExporter.head = ((((((("<?xml version=\"1.0\" encoding=\"ASCII\"?>\n" + "<scanexam:Exam xmi:version=\"2.0\" ") + "xmlns:xmi=\"http://www.omg.org/XMI\" ") + "xmlns:scanexam=\"fr.istic.tools.scanexam\" ") + "label=\"PFO_december_19\" ") + "folderPath=\"/Users/steven/Documents/ISTIC/2020-2021/PFO/Examen/dec19/CopiesScan/png/\" ") + "numberOfPages=\"6\" ") + "scale=\"42\"> \n");
  }
  
  private static void createTail(final BoxList list) {
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
  public static boolean exportToXMI(final BoxList boxList) {
    XMIExporter.createHead(boxList);
    XMIExporter.createBody(boxList);
    XMIExporter.createTail(boxList);
    try {
      XMIExporter.exportFile();
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return true;
  }
}
