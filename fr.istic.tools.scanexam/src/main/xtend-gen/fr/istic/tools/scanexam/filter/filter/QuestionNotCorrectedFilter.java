package fr.istic.tools.scanexam.filter.filter;

import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.filter.filter.BasicFilter;
import fr.istic.tools.scanexam.filter.param.FilterParam;
import java.util.List;

@SuppressWarnings("all")
public class QuestionNotCorrectedFilter implements BasicFilter<Grade> {
  public List<FilterParam<?>> getParams() {
    return List.<FilterParam<?>>of();
  }
  
  public boolean test(final Grade t) {
    return t.getEntries().isEmpty();
  }
}
