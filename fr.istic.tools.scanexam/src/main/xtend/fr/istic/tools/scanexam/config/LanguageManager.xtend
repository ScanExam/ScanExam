package fr.istic.tools.scanexam.config

import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.util.Collection
import java.util.Collections
import java.util.HashSet
import java.util.Locale
import java.util.Objects
import java.util.ResourceBundle
import java.util.logging.Logger
import java.util.stream.Collectors

import static java.util.Locale.*

/**
 * Classe permettant de gérer le langage d'affichage de l'application en se basant sur les {@link Locale Locale}.
 * @author Théo Giraudet
 * @see Locale
 * @see ResourceBundle
 */
class LanguageManager {
	
	static val logger = Logger.getGlobal;
	static val path = "langs/"
	static val prefixFileName = "ScanExam"
	static val extFileName = "properties"
	static val langCodePattern =  "_[a-zA-Z]{2,8}(_[a-zA-Z]{2}|[0-9]{3})?"

	static val locales = new HashSet<Locale>
	
	static var Locale currentLocale = null
	static var ResourceBundle currentBundle = null

	/**
	 * Charge les différents {@link Locale} supportés pour l'application, définie le langage de l'interface par le langage de l'environnement (si celui-ci est supporté)
	 * puis définie le langage par défaut de l'application sur Locale.ENGLISH.<br/>
	 * Pour qu'une langage soit supporté, il faut que celui-ci soit représenté par un fichier <code>/langs/ScanExam_&ltcode langage&gt.properties</code>
	 */
	static def void init() {
		logger.info("Pre-loading languages...")
		
		val currentLocal = Locale.^default
		
		val namePattern = prefixFileName + langCodePattern + "\\." + extFileName
		val names = ResourcesUtils.getFolderContentNames("/" + path)
		val badFileNames = new HashSet<String>
		
		// Ajout de l'anglais pour langage par défaut en cas d'absence d'une langue choisie
		locales.add(new Locale("en"))
		Locale.^default = Locale.ENGLISH
		
		for(String name: names) {
			if(name.matches(namePattern)) {
				val langCode = name.substring(prefixFileName.length + 1, name.length - extFileName.length - 1).split("_")
				val lang = langCode.head
				val country = langCode.length > 1 ? langCode.last : ""
				locales.add(new Locale(lang, country))
			} else if(!name.matches(prefixFileName + "\\." + extFileName))
				badFileNames.add(name)
		}
		
		if(locales.size > 0)
			logger.info(locales.stream.map(l | l.displayName).collect(Collectors.joining(", ")) + " pre-loaded.")
		
		if(badFileNames.size > 0)
			logger.info(String.join(", ", badFileNames) + " ignored (bad file names).")
			
		// La langue par défaut de l'interface est celle correspondant à la langue de l'ordinateur de l'utilisateur
		LanguageManager.change(currentLocal)
	}
	
	/**
	 * @return une Collection non modifiable des Locale supportés par le programme 
	 */
	static def Collection<Locale> getSupportedLocales() {
		Collections.unmodifiableCollection(locales)
		
	}
	
	/** Change la langue de l'application par celle représentée par <b>language</b> si celle-ci est supportée par le programme.<br/>
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
	static def void change(Locale language) {
		Objects.requireNonNull(language)
		val newLocale = if(locales.contains(language))
							language
						else
							locales.findFirst[l | l.language.equals(language.language)]
		currentLocale = newLocale === null ? Locale.^default : newLocale
		currentBundle = ResourceBundle.getBundle(path + prefixFileName, currentLocale)
	}
	
	/**
	 * @return le {@link Locale Locale} courant de l'application
	 * @see Locale Locale
	 */
	static def Locale getCurrentLanguage() {
		currentLocale
	}
	
	/**
	 * @return le {@link ResourceBundle ResourceBundle} courant de l'application
	 * @see ResourceBundle
	 */
	static def ResourceBundle getCurrentBundle() {
		currentBundle
	}
	
	/**
	 * @param un code à traduire (non null)
	 * @return une chaîne de caractères correspondant à la traduction de <b>code</b> dans le langage courant de l'application
	 * @throw NullPointerException si <b>code</b> est null
	 * @throw MissingResourceException si aucune traduction n'a été trouvé pour <b>code</b> dans le langage courant
	 */
	static def String translate(String code) {
		currentBundle.getString(code)
	}
}