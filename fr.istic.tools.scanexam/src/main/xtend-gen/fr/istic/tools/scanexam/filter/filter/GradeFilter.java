package fr.istic.tools.scanexam.filter.filter;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.filter.filter.BasicFilter;
import fr.istic.tools.scanexam.filter.param.FilterParam;
import fr.istic.tools.scanexam.filter.param.IntegerParam;
import java.util.List;

@SuppressWarnings("all")
public class GradeFilter implements BasicFilter<StudentSheet> {
  private final FilterParam<Integer> minBound;
  
  private final FilterParam<Integer> maxBound;
  
  public GradeFilter() {
    IntegerParam _integerParam = new IntegerParam(Integer.valueOf(0), null, "filter.grade_filter.min");
    this.minBound = _integerParam;
    IntegerParam _integerParam_1 = new IntegerParam(Integer.valueOf(0), null, "filter.grade_filter.max");
    this.maxBound = _integerParam_1;
  }
  
  @Override
  public List<FilterParam<?>> getParams() {
    return List.<FilterParam<?>>of(this.minBound, this.maxBound);
  }
  
  @Override
  public boolean test(final StudentSheet t) {
    return ((t.computeGrade() >= (this.minBound.getValue()).intValue()) && (t.computeGrade() <= (this.maxBound.getValue()).intValue()));
  }
}
