package fr.istic.tools.scanexam.filter.param;

import javax.annotation.Nullable;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class IntegerParam implements FilterParam<Integer> {
  private final Integer min;
  
  private final Integer max;
  
  private final String nameCode;
  
  private Integer value = null;
  
  public IntegerParam(@Nullable final Integer min, @Nullable final Integer max, final String nameCode) {
    this.min = min;
    this.max = max;
    this.nameCode = nameCode;
  }
  
  @Override
  public ParamParseResult parse(final String string) {
    try {
      final int value = Integer.parseInt(string);
      if (((value < (this.min).intValue()) || (value > (this.max).intValue()))) {
        return ParamParseResult.failed("filter.param.integer.out_of_bound");
      }
      return ParamParseResult.succeed();
    } catch (final Throwable _t) {
      if (_t instanceof NumberFormatException) {
        return ParamParseResult.failed("filter.param.integer.not_integer");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  @Override
  public String getStringPattern() {
    return "[0-9]+";
  }
  
  @Override
  public Integer getValue() {
    return this.value;
  }
  
  @Override
  public String getNameCode() {
    return this.nameCode;
  }
}
