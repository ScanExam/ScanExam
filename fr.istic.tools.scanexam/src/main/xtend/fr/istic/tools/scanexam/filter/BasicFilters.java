package fr.istic.tools.scanexam.filter;

import java.util.function.Supplier;

import fr.istic.tools.scanexam.filter.filter.BasicFilter;
import fr.istic.tools.scanexam.filter.filter.GradeFilter;

public enum BasicFilters {
	
	GRADE_FILTER(GradeFilter::new, "filter.grade_filter.name");

	private final Supplier<BasicFilter<?>> filter;
	private final String name;
	
	BasicFilters(Supplier<BasicFilter<?>> filter, String name) {
		this.filter = filter;
		this.name = name;
	}

	public BasicFilter<?> getFilter() {
		return filter.get();
	}

	public String getName() {
		return name;
	}
}
