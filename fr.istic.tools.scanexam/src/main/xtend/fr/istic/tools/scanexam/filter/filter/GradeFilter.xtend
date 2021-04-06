package fr.istic.tools.scanexam.filter.filter

import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.filter.param.FilterParam
import fr.istic.tools.scanexam.filter.param.IntegerParam
import java.util.List

class GradeFilter implements BasicFilter<StudentSheet> {

	val FilterParam<Integer> minBound;
	val FilterParam<Integer> maxBound;

	new() {
		this.minBound = new IntegerParam(0, null, "filter.grade_filter.min")
		this.maxBound = new IntegerParam(0, null, "filter.grade_filter.max")
	}

	override getParams() {
		List.of(minBound, maxBound)
	}
	
	override test(StudentSheet t) {
		t.computeGrade >= minBound.value && t.computeGrade <= maxBound.value
	}
	
}
