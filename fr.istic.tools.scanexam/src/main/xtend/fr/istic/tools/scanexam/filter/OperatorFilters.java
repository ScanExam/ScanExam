package fr.istic.tools.scanexam.filter;

import fr.istic.tools.scanexam.filter.filter.AndFilter;
import fr.istic.tools.scanexam.filter.filter.OperatorFilterSupplier;
import fr.istic.tools.scanexam.filter.filter.OperatorFilter;
import fr.istic.tools.scanexam.filter.filter.OrFilter;

public enum OperatorFilters {
	
	AND_FILTER(AndFilter::new, "filter.and_filter.name"),
	OR_FILTER(OrFilter::new, "filter.or_filter.name");

	private final OperatorFilterSupplier filter;
	private final String name;
	
	OperatorFilters(OperatorFilterSupplier filter, String name) {
		this.filter = filter;
		this.name = name;
	}

	public <T> OperatorFilter<T> getFilter() {
		return filter.<T>get();
	}

	public String getName() {
		return name;
	}
}
