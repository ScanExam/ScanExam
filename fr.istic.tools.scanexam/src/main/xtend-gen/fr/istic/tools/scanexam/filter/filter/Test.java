package fr.istic.tools.scanexam.filter.filter;

import fr.istic.tools.scanexam.filter.OperatorFilters;
import fr.istic.tools.scanexam.filter.filter.OperatorFilter;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Test {
  public static void main(final String... args) {
    final Predicate<Integer> _function = (Integer i) -> {
      return ((i).intValue() < 10);
    };
    final Predicate<Integer> max = _function;
    final Predicate<Integer> _function_1 = (Integer i) -> {
      return (((i).intValue() % 2) == 0);
    };
    final Predicate<Integer> even = _function_1;
    final OperatorFilter<Integer> and = OperatorFilters.AND_FILTER.<Integer>getFilter();
    and.addFilters(List.<Predicate<Integer>>of(max, even));
    final List<Integer> list = List.<Integer>of(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(19), Integer.valueOf(20), Integer.valueOf(4));
    InputOutput.<List<Integer>>println(list.stream().filter(and).collect(Collectors.<Integer>toList()));
  }
}
