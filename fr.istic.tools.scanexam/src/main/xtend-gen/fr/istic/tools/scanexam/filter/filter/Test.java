package fr.istic.tools.scanexam.filter.filter;

import java.nio.CharBuffer;
import java.util.function.IntUnaryOperator;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Test {
  public static void main(final String... args) {
    final String toParse = "AA22";
    final String[] xy = toParse.split("(?<=[A-Z]++)");
    final IntUnaryOperator _function = (int c) -> {
      return (c - 'A');
    };
    final int x = CharBuffer.wrap(xy[0]).chars().map(_function).sum();
    final int y = Integer.parseInt(xy[1]);
    InputOutput.<String>println(((("x: " + Integer.valueOf(x)) + ", y: ") + Integer.valueOf(y)));
  }
}
