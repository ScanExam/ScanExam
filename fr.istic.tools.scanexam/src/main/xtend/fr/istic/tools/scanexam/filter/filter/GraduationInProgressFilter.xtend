package fr.istic.tools.scanexam.filter.filter

import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.filter.param.FilterParam
import java.util.List

class GraduationInProgressFilter implements BasicFilter<StudentSheet> {
	
	override List<FilterParam<?>> getParams() {
		return List.of()
	}
	
	override test(StudentSheet t) {
		t.isGraded
	}	
}