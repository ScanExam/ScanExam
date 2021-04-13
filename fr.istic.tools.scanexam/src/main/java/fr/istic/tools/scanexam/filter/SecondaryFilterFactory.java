package fr.istic.tools.scanexam.filter;

import java.util.function.Supplier;

import fr.istic.tools.scanexam.filter.filter.BasicFilter;
import fr.istic.tools.scanexam.filter.filter.QuestionNotCorrectedFilter;

public enum SecondaryFilterFactory implements FilterFactory {
	
	QUESTION_NOT_CORRECTED_FILTER(QuestionNotCorrectedFilter::new);

	private final Supplier<BasicFilter<?>> filter;
	
	SecondaryFilterFactory(Supplier<BasicFilter<?>> filter) {
		this.filter = filter;
	}


	@Override
	public BasicFilter<?> createFilter() {
		return filter.get();
	}

}
