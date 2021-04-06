package fr.istic.tools.scanexam.filter.param

import javax.annotation.Nullable

class IntegerParam implements FilterParam<Integer> {
	
	val Integer min
	val Integer max
	val String nameCode
	
	var Integer value = null
	
	new(@Nullable Integer min, @Nullable Integer max, String nameCode) {
		this.min = min
		this.max = max
		this.nameCode = nameCode
	}
	
	override parse(String string) {
		try {
			val value = Integer.parseInt(string)
			if(value < min || value > max)
				return ParamParseResult.failed("filter.param.integer.out_of_bound")
			return ParamParseResult.succeed
		} catch(NumberFormatException e) {
			return ParamParseResult.failed("filter.param.integer.not_integer")
		}
	}
	
	override getStringPattern() {
		"[0-9]+"
	}
	
	override getValue() {
		value
	}
	
	override getNameCode() {
		nameCode
	}
	
}
