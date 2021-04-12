package fr.istic.tools.scanexam.filter.filter

import fr.istic.tools.scanexam.core.Grade
import java.util.List

class QuestionNotCorrectedFilter implements BasicFilter<Grade> {
	
	override getParams() {
		List.of()
	}
	
	override test(Grade t) {
		t.entries.isEmpty
	}
	
}