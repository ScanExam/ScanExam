package fr.istic.tools.scanexam.filter.filter;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.filter.filter.BasicFilter;
import fr.istic.tools.scanexam.filter.param.FilterParam;
import java.util.List;

@SuppressWarnings("all")
public class GraduationInProgressFilter implements BasicFilter<StudentSheet> {
  @Override
  public List<FilterParam<?>> getParams() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public boolean test(final StudentSheet t) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
