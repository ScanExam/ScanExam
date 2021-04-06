package fr.istic.tools.scanexam.filter.filter

import fr.istic.tools.scanexam.filter.OperatorFilters
import java.util.List
import java.util.function.Predicate
import java.util.stream.Collectors

class Test {

	def static void main(String... args) {
		val Predicate<Integer> max = [Integer i | i < 10]
		val Predicate<Integer> even = [Integer i | i % 2 == 0]
		val and = OperatorFilters.AND_FILTER.filter
		and.addFilters(List.of(max, even))
		val list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 4)
		println(list.stream.filter(and).collect(Collectors.toList))
	}

}
