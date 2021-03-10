package fr.istic.tools.scanexam.filter;

import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.filter.Filter;

@SuppressWarnings("all")
public class NotCorrectedFilter implements Filter<Grade> {
  @Override
  public boolean keepValue(final Grade value) {
    return value.getEntries().isEmpty();
  }
}
