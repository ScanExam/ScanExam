package fr.istic.tools.scanexam.filter.filter;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.filter.filter.BasicFilter;
import fr.istic.tools.scanexam.filter.param.FilterParam;
import java.util.List;

@SuppressWarnings("all")
public class GraduationInProgressFilter implements BasicFilter<StudentSheet> {
  public List<FilterParam<?>> getParams() {
    return List.<FilterParam<?>>of();
  }
  
  public boolean test(final StudentSheet t) {
    return t.isGraded();
  }
}
