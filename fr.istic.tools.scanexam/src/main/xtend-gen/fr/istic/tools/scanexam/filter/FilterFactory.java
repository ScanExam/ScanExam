package fr.istic.tools.scanexam.filter;

import fr.istic.tools.scanexam.filter.filter.BasicFilter;

@SuppressWarnings("all")
public interface FilterFactory {
  BasicFilter<?> createFilter();
}
