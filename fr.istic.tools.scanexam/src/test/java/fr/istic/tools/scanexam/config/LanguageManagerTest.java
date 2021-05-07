package fr.istic.tools.scanexam.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.MissingResourceException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.beust.jcommander.internal.Lists;

public class LanguageManagerTest {

	/*
	 * Les fonctions getSupportedLocales et getCurrentLanguage seront considérées comme étant fonctionnelles (base de confiance)
	 */
	
	@BeforeAll
	static void init() {
		Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.ALL);
		
	}
	
	@BeforeEach
	void initEach() {
		Locale.setDefault(Locale.FRENCH);
	}
	
	private Object jailBreak(Class<?> clazz, String name, Object... args) {
		try {
			final Class<?>[] argClasses = Lists.newArrayList(args).stream()
					.map(o -> o.getClass())
					.toArray(Class<?>[]::new);
			Method method = clazz.getDeclaredMethod(name, argClasses);
			method.setAccessible(true);
			return method.invoke(clazz, args);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Test
	@DisplayName("init")
	void initTest() {
		jailBreak(LanguageManager.class, "init", Locale.getDefault(), "languages/");
		final Collection<Locale> locales = LanguageManager.getSupportedLocales();
		final Collection<Locale> expected = Arrays.asList(Locale.ENGLISH, Locale.FRENCH, new Locale("es", ""));
		assertTrue(locales.containsAll(expected) && expected.containsAll(locales));
		assertEquals(Locale.FRENCH, LanguageManager.getCurrentLanguage());
		assertEquals(Locale.ENGLISH, Locale.getDefault());
	}
	
	@Test
	@DisplayName("init avec une JVM configurée sur un langage supporté")
	void initTest2() {
		Locale.setDefault(new Locale("es", ""));
		jailBreak(LanguageManager.class, "init", Locale.getDefault(), "languages/");
		final Collection<Locale> locales = LanguageManager.getSupportedLocales();
		final Collection<Locale> expected = Arrays.asList(Locale.ENGLISH, Locale.FRENCH, new Locale("es", ""));
		
		assertTrue(locales.containsAll(expected) && expected.containsAll(locales));
		assertEquals(new Locale("es", ""), LanguageManager.getCurrentLanguage());
		assertEquals(Locale.ENGLISH, Locale.getDefault());
	}
	
	@Test
	@DisplayName("init avec une JVM configurée sur un Locale non supporté mais sur un langage supporté")
	void initTest3() {
		Locale.setDefault(new Locale("fr", "CA"));
		jailBreak(LanguageManager.class, "init", Locale.getDefault(), "languages/");
		final Collection<Locale> locales = LanguageManager.getSupportedLocales();
		final Collection<Locale> expected = Arrays.asList(Locale.ENGLISH, Locale.FRENCH, new Locale("es", ""));
		
		assertTrue(locales.containsAll(expected) && expected.containsAll(locales));
		assertEquals(Locale.FRENCH, LanguageManager.getCurrentLanguage());
		assertEquals(Locale.ENGLISH, Locale.getDefault());
	}
	
	@Test
	@DisplayName("init avec une JVM configurée sur un Locale non supporté mais sur un langage supporté")
	void initTest3_5() {
		Locale.setDefault(new Locale("fr", "fr"));
		jailBreak(LanguageManager.class, "init", Locale.getDefault(), "languages/");
		final Collection<Locale> locales = LanguageManager.getSupportedLocales();
		final Collection<Locale> expected = Arrays.asList(Locale.ENGLISH, Locale.FRENCH, new Locale("es", ""));
		
		assertTrue(locales.containsAll(expected) && expected.containsAll(locales));
		assertEquals(Locale.FRENCH, LanguageManager.getCurrentLanguage());
		assertEquals(Locale.ENGLISH, Locale.getDefault());
	}
	
	@Test
	@DisplayName("init avec une JVM configurée sur un langage non supporté")
	void initTest4() {
		Locale.setDefault(new Locale("de", ""));
		jailBreak(LanguageManager.class, "init", Locale.getDefault(), "languages/");
		final Collection<Locale> locales = LanguageManager.getSupportedLocales();
		final Collection<Locale> expected = Arrays.asList(Locale.ENGLISH, Locale.FRENCH, new Locale("es", ""));
		
		assertTrue(locales.containsAll(expected) && expected.containsAll(locales));
		assertEquals(Locale.ENGLISH, LanguageManager.getCurrentLanguage());
		assertEquals(Locale.ENGLISH, Locale.getDefault());
	}
	
	
	@Test
	@DisplayName("init sur un langage supporté")
	void changeTest1() {
		jailBreak(LanguageManager.class, "init", new Locale("es", ""), "languages/");
		assertEquals(new Locale("es", ""), LanguageManager.getCurrentLanguage());
	}
	
	@Test
	@DisplayName("init sur un Locale non supporté mais sur un langage supporté")
	void changeTest2() {
		jailBreak(LanguageManager.class, "init", new Locale("fr", "CA"), "languages/");
		assertEquals(new Locale("fr", ""), LanguageManager.getCurrentLanguage());
	}
	
	@Test
	@DisplayName("init sur un langage non supporté")
	void changeTest3() {
		jailBreak(LanguageManager.class, "init", new Locale("de", ""), "languages/");
		assertEquals(Locale.ENGLISH, LanguageManager.getCurrentLanguage());
	}
	
	@Test
	@DisplayName("change sur un langage null (précondition)")
	void changeTest4() {
		jailBreak(LanguageManager.class, "init", Locale.getDefault(), "languages/");
		assertThrows(NullPointerException.class, () -> jailBreak(LanguageManager.class, "change", new Object[] {null}));
	}
	
	@Test
	@DisplayName("translate sur des mots définis") 
	void translateTest1() {
		jailBreak(LanguageManager.class, "init", Locale.getDefault(), "languages/");
		assertEquals("pierre", LanguageManager.translate("stone"));
		assertEquals("jardin", LanguageManager.translate("garden"));
		assertEquals("raton laveur", LanguageManager.translate("raccoon"));
		assertNotNull(LanguageManager.getCurrentBundle());
		assertEquals("pierre", LanguageManager.getCurrentBundle().getString("stone"));
	}
	
	@Test
	@DisplayName("translate sur des mots définis d'une autre langue") 
	void translateTest2() {
		jailBreak(LanguageManager.class, "init", new Locale("es", ""), "languages/");
		assertEquals("piedra", LanguageManager.translate("stone"));
		assertEquals("jardín", LanguageManager.translate("garden"));
		assertEquals("mapache", LanguageManager.translate("raccoon"));
		assertNotNull(LanguageManager.getCurrentBundle());
		assertEquals("piedra", LanguageManager.getCurrentBundle().getString("stone"));
	}
	
	@Test
	@DisplayName("translate sur un mot non défini")
	void translateTest3() {
		jailBreak(LanguageManager.class, "init", Locale.getDefault(), "languages/");
		assertEquals("oyster", LanguageManager.translate("oyster"));
		assertNotNull(LanguageManager.getCurrentBundle());
		assertThrows(MissingResourceException.class, () -> LanguageManager.getCurrentBundle().getString("oyster"));
	}
	
	@Test
	@DisplayName("toLocale sur des entrées valides")
	void toLocaleTest1() {
		var language = LanguageManager.toLocale("fr_fr");
		assertTrue(language.isPresent());
		assertEquals(new Locale("fr", "fr"), language.get());
		language = LanguageManager.toLocale("fr");
		assertTrue(language.isPresent());
		assertEquals(new Locale("fr", ""), language.get());
		language = LanguageManager.toLocale("fr_ca");
		assertTrue(language.isPresent());
		assertEquals(new Locale("fr", "ca"), language.get());
	}
	
	@Test
	@DisplayName("toLocale sur des entrées non valides")
	void toLocaleTest2() {
		assertThrows(IllegalArgumentException.class, () -> LanguageManager.toLocale("fr_en_es"));
	}
}
