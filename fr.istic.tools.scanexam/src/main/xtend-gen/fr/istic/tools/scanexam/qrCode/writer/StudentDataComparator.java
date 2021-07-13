package fr.istic.tools.scanexam.qrCode.writer;

import java.util.Comparator;
import java.util.List;

@SuppressWarnings("all")
public class StudentDataComparator implements Comparator<List<String>> {
  @Override
  public int compare(final List<String> o1, final List<String> o2) {
    final String firstString_o1 = o1.get(0);
    final String firstString_o2 = o2.get(0);
    return firstString_o1.compareTo(firstString_o2);
  }
}
