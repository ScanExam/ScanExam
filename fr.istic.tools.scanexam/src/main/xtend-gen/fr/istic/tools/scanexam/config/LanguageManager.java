package fr.istic.tools.scanexam.config;

import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Classe permettant de gérer le langage d'affichage de l'application en se basant sur les {@link Locale Locale}.<br/>
 * La fonction {@link #init init} doit au moins être appelée une fois par le programme avant d'appeler une quelconque autre opération de la classe.
 * @author Théo Giraudet
 * @see Locale
 * @see ResourceBundle
 */
@SuppressWarnings("all")
public class LanguageManager {
  private static final Logger logger = LogManager.getLogger();
  
  private static final String path = "langs/";
  
  private static final String prefixFileName = "ScanExam";
  
  private static final String extFileName = "properties";
  
  private static final String langCodePattern = "_[a-zA-Z]{2,8}(_[a-zA-Z]{2}|[0-9]{3})?";
  
  private static final HashSet<Locale> locales = new HashSet<Locale>();
  
  private static Locale currentLocale = null;
  
  private static ResourceBundle currentBundle = null;
  
  /**
   * Charge les différents {@link Locale} supportés pour l'application, définie le langage de l'interface par le langage de l'environnement (si celui-ci est supporté) ou
   * par le langage en paramètre si celui-ci est spécifié et supporté.
   * Définie le langage par défaut de l'application sur Locale.ENGLISH.<br/>
   * Pour qu'une langage soit supporté, il faut que celui-ci soit représenté par un fichier <code>/langs/ScanExam_&ltcode langage&gt.properties</code>
   * @param language la langue de l'application (peut être null)
   */
  public static void init(@Nullable final Locale language) {
    LanguageManager.logger.info("Pre-loading languages...");
    Locale _xifexpression = null;
    if ((language == null)) {
      _xifexpression = Locale.getDefault();
    } else {
      _xifexpression = language;
    }
    final Locale currentLocal = _xifexpression;
    final String namePattern = (((LanguageManager.prefixFileName + LanguageManager.langCodePattern) + "\\.") + LanguageManager.extFileName);
    final Collection<String> names = ResourcesUtils.getFolderContentNames(("/" + LanguageManager.path));
    final HashSet<String> badFileNames = new HashSet<String>();
    Locale _locale = new Locale("en");
    LanguageManager.locales.add(_locale);
    Locale.setDefault(Locale.ENGLISH);
    for (final String name : names) {
      boolean _matches = name.matches(namePattern);
      if (_matches) {
        int _length = LanguageManager.prefixFileName.length();
        int _plus = (_length + 1);
        int _length_1 = name.length();
        int _length_2 = LanguageManager.extFileName.length();
        int _minus = (_length_1 - _length_2);
        int _minus_1 = (_minus - 1);
        final String[] langCode = name.substring(_plus, _minus_1).split("_");
        final String lang = IterableExtensions.<String>head(((Iterable<String>)Conversions.doWrapArray(langCode)));
        String _xifexpression_1 = null;
        int _length_3 = langCode.length;
        boolean _greaterThan = (_length_3 > 1);
        if (_greaterThan) {
          _xifexpression_1 = IterableExtensions.<String>last(((Iterable<String>)Conversions.doWrapArray(langCode)));
        } else {
          _xifexpression_1 = "";
        }
        final String country = _xifexpression_1;
        Locale _locale_1 = new Locale(lang, country);
        LanguageManager.locales.add(_locale_1);
      } else {
        boolean _matches_1 = name.matches(((LanguageManager.prefixFileName + "\\.") + LanguageManager.extFileName));
        boolean _not = (!_matches_1);
        if (_not) {
          badFileNames.add(name);
        }
      }
    }
    int _size = LanguageManager.locales.size();
    boolean _greaterThan_1 = (_size > 0);
    if (_greaterThan_1) {
      final Function<Locale, String> _function = (Locale l) -> {
        return l.getDisplayName();
      };
      String _collect = LanguageManager.locales.stream().<String>map(_function).collect(Collectors.joining(", "));
      String _plus_1 = (_collect + " pre-loaded.");
      LanguageManager.logger.info(_plus_1);
    }
    int _size_1 = badFileNames.size();
    boolean _greaterThan_2 = (_size_1 > 0);
    if (_greaterThan_2) {
      String _join = String.join(", ", badFileNames);
      String _plus_2 = (_join + " ignored (bad file names).");
      LanguageManager.logger.info(_plus_2);
    }
    LanguageManager.change(currentLocal);
  }
  
  /**
   * @return une Collection non modifiable des Locale supportés par le programme
   */
  public static Collection<Locale> getSupportedLocales() {
    return Collections.<Locale>unmodifiableCollection(LanguageManager.locales);
  }
  
  /**
   * Change la langue de l'application par celle représentée par <b>language</b> si celle-ci est supportée par le programme.<br/>
   * Si un {@link Locale} définie pour l'application est strictement égal (par la fonction equals) à <b>language</b>, alors ce <i>Locale</i> devient le <i>Locale</i> courant.
   * Sinon si un {@link Locale} supporté par l'application a pour code de langage (voir l'attribut <b>language</b> de {@link Locale}) le même que <b>language</b>,  alors ce <i>Locale</i> devient le <i>Locale</i> courant.
   * Sinon, le <i>Locale</i> courant devient {@link java.util.Locale.ENGLISH Locale.ENGLISH}.<br/>
   * Par exemple, si l'application supporte <i>en</i> et <i>fr_FR</i> alors :
   * <ul>
   * <li>Choisir <i>fr_FR</i> pour <i>Locale</i> passera l'application sur le <i>Locale</i> <i>fr_FR</i></li>
   * <li>Choisir <i>fr_CA</i> pour <i>Locale</i> passera l'application sur le <i>Locale</i> <i>fr_FR</i> car le langage (<i>fr</i>) est commun</li>
   * <li>Choisir <i>es_ES</i> pour <i>Locale</i> passera l'application sur le <i>Locale</i> <i>en</i> car aucun <i>Locale</i> strictement égal ou ayant le même langage n'a été trouvé</li>
   * </ul>
   * @param language un Locale (non null)
   * @see Locale Locale
   * @see Locale#getLanguage()
   * @see Locale#ENGLISH Locale.ENGLISH
   * @throw NullPointerException si <b>language<b/> est null
   */
  private static void change(final Locale language) {
    Objects.<Locale>requireNonNull(language);
    Locale _xifexpression = null;
    boolean _contains = LanguageManager.locales.contains(language);
    if (_contains) {
      _xifexpression = language;
    } else {
      final Function1<Locale, Boolean> _function = (Locale l) -> {
        return Boolean.valueOf(l.getLanguage().equals(language.getLanguage()));
      };
      _xifexpression = IterableExtensions.<Locale>findFirst(LanguageManager.locales, _function);
    }
    final Locale newLocale = _xifexpression;
    Locale _xifexpression_1 = null;
    if ((newLocale == null)) {
      _xifexpression_1 = Locale.getDefault();
    } else {
      _xifexpression_1 = newLocale;
    }
    LanguageManager.currentLocale = _xifexpression_1;
    boolean _equals = LanguageManager.currentLocale.equals(language);
    boolean _not = (!_equals);
    if (_not) {
      StringConcatenation _builder = new StringConcatenation();
      String _displayName = language.getDisplayName();
      _builder.append(_displayName);
      _builder.append(" is not supported, fallback to ");
      String _displayName_1 = LanguageManager.currentLocale.getDisplayName();
      _builder.append(_displayName_1);
      _builder.append(".");
      LanguageManager.logger.info(_builder);
    }
    LanguageManager.currentBundle = ResourceBundle.getBundle((LanguageManager.path + LanguageManager.prefixFileName), LanguageManager.currentLocale, ResourcesUtils.class.getClassLoader());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("Change language to ");
    String _displayName_2 = LanguageManager.currentLocale.getDisplayName();
    _builder_1.append(_displayName_2);
    _builder_1.append(".");
    LanguageManager.logger.info(_builder_1);
  }
  
  /**
   * @return le {@link Locale Locale} courant de l'application
   * @see Locale Locale
   */
  public static Locale getCurrentLanguage() {
    return LanguageManager.currentLocale;
  }
  
  /**
   * @return le {@link ResourceBundle ResourceBundle} courant de l'application
   * @see ResourceBundle
   */
  public static ResourceBundle getCurrentBundle() {
    return LanguageManager.currentBundle;
  }
  
  /**
   * @param un code à traduire (non null)
   * @return une chaîne de caractères correspondant à la traduction de <b>code</b> dans le langage courant de l'application ou <b>code</b> si aucune traduction n'est définie pour ce code.
   * @throw NullPointerException si <b>code</b> est null
   */
  public static String translate(final String code) {
    String _xtrycatchfinallyexpression = null;
    try {
      _xtrycatchfinallyexpression = LanguageManager.currentBundle.getString(code);
    } catch (final Throwable _t) {
      if (_t instanceof MissingResourceException) {
        String _displayName = LanguageManager.currentLocale.getDisplayName();
        String _plus = ((code + " not found for ") + _displayName);
        String _plus_1 = (_plus + ".");
        LanguageManager.logger.warn(_plus_1);
        return code;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return _xtrycatchfinallyexpression;
  }
}
