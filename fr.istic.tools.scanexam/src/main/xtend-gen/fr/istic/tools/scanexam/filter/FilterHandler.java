package fr.istic.tools.scanexam.filter;

import fr.istic.tools.scanexam.filter.filter.BasicFilter;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("all")
public class FilterHandler<T extends Object> {
  private final List<BasicFilter<T>> filterList = new LinkedList<BasicFilter<T>>();
}
