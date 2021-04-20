package fr.istic.tools.scanexam;

import java.lang.reflect.Method;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class Test {
  public static void main(final String... args) {
    try {
      final Method method = Test.class.getDeclaredMethod("a", null);
      method.setAccessible(true);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
