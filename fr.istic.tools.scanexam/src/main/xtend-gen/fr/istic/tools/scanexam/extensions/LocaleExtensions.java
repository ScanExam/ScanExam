package fr.istic.tools.scanexam.extensions;

import java.util.Locale;

@SuppressWarnings("all")
public class LocaleExtensions {
  /**
   * @param un locale
   * @return le displayname du locale dans sa propre langue
   */
  public static String capitalizeDisplayName(final Locale locale) {
    final String name = locale.getDisplayName(locale);
    String _upperCase = name.substring(0, 1).toUpperCase();
    String _lowerCase = name.substring(1).toLowerCase();
    return (_upperCase + _lowerCase);
  }
}
