package fr.istic.tools.scanexam.filter;

import java.util.Set;
import java.util.function.Supplier;

import fr.istic.tools.scanexam.filter.filter.BasicFilter;
import fr.istic.tools.scanexam.filter.filter.GradeFilter;
import fr.istic.tools.scanexam.filter.filter.GraduationInProgressFilter;

public enum BasicFilterFactory implements FilterFactory {
	
	GRADE_FILTER(GradeFilter::new, "filter.grade.name"),
	GRADUATION_IP_FILTER(GraduationInProgressFilter::new, "filter.graduation_in_progress_name", SecondaryFilterFactory.QUESTION_NOT_CORRECTED_FILTER);

	private final Supplier<BasicFilter<?>> filter;
	private final String name;
	private final Set<FilterFactory> children;
	
	BasicFilterFactory(Supplier<BasicFilter<?>> filter, String name, FilterFactory... children) {
		this.filter = filter;
		this.name = name;
		this.children = Set.of(children);
	}

	public String getName() {
		return name;
	}

	@Override
	public BasicFilter<?> createFilter() {
		return filter.get();
	}
}
