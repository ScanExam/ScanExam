package fr.istic.tools.scanexam.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LanguageManagerTest {

	/*
	 * Les fonctions getSupportedLocales et getCurrentLanguage seront considérées comme étant fonctionnelles (base de confiance)
	 */
	

	@BeforeAll
	static void init() {
		Logger.getGlobal().setLevel(Level.OFF);
	}
	
	@BeforeEach
	void initEach() {
		Locale.setDefault(Locale.FRENCH);
	}
	
	@Test
	@DisplayName("init")
	void initTest() {
		LanguageManager.init();
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
		LanguageManager.init();
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
		LanguageManager.init();
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
		LanguageManager.init();
		final Collection<Locale> locales = LanguageManager.getSupportedLocales();
		final Collection<Locale> expected = Arrays.asList(Locale.ENGLISH, Locale.FRENCH, new Locale("es", ""));
		
		assertTrue(locales.containsAll(expected) && expected.containsAll(locales));
		assertEquals(Locale.ENGLISH, LanguageManager.getCurrentLanguage());
		assertEquals(Locale.ENGLISH, Locale.getDefault());
	}
	
	
	@Test
	@DisplayName("change sur un langage supporté")
	void changeTest1() {
		LanguageManager.init();
		LanguageManager.change(new Locale("es", ""));
		assertEquals(new Locale("es", ""), LanguageManager.getCurrentLanguage());
	}
	
	@Test
	@DisplayName("change sur un Locale non supporté mais sur un langage supporté")
	void changeTest2() {
		LanguageManager.init();
		LanguageManager.change(new Locale("fr", "CA"));
		assertEquals(new Locale("fr", ""), LanguageManager.getCurrentLanguage());
	}
	
	@Test
	@DisplayName("change sur un langage non supporté")
	void changeTest3() {
		LanguageManager.init();
		LanguageManager.change(new Locale("de", ""));
		assertEquals(Locale.ENGLISH, LanguageManager.getCurrentLanguage());
	}
	
	@Test
	@DisplayName("change sur un langage null (précondition)")
	void changeTest4() {
		LanguageManager.init();
		assertThrows(NullPointerException.class, () -> LanguageManager.change(null));
	}
	
	
	@Test
	@DisplayName("translate sur des mots définis") 
	void translateTest1(){
		assertEquals("pierre", LanguageManager.translate("stone"));
		assertEquals("jardin", LanguageManager.translate("garden"));
		assertEquals("raton laveur", LanguageManager.translate("raccoon"));
		
		LanguageManager.change(new Locale("es", ""));
		assertEquals("piedra", LanguageManager.translate("stone"));
		assertEquals("jardín", LanguageManager.translate("garden"));
		assertEquals("mapache", LanguageManager.translate("raccoon"));
	}
	
	@Test
	@DisplayName("translate sur un mot non défini")
	void translateTest2() {
		assertEquals("oyster", LanguageManager.translate("oyster"));
	}
}
