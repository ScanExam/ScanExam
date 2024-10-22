package fr.istic.tools.scanexam.config

import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.util.Collection
import java.util.Collections
import java.util.HashSet
import java.util.Locale
import java.util.MissingResourceException
import java.util.Objects
import java.util.Optional
import java.util.ResourceBundle
import java.util.stream.Collectors
import javax.annotation.Nullable
import org.apache.logging.log4j.LogManager

import static java.util.Locale.*

/**
 * Classe permettant de gérer le langage d'affichage de l'application en se basant sur les {@link Locale Locale}.<br/>
 * La fonction {@link #init init} doit au moins être appelée une fois par le programme avant d'appeler une quelconque autre opération de la classe.
 * @author Théo Giraudet
 * @see Locale
 * @see ResourceBundle
 */
class LanguageManager {
	
	static val logger = LogManager.logger
	static var path = "langs/"
	static val prefixFileName = "ScanExam"
	static val extFileName = "properties"
	static val langCodePattern =  "_[a-zA-Z]{2,8}(_[a-zA-Z]{2}|[0-9]{3})?"

	static val locales = new HashSet<Locale>
	
	static var Locale currentLocale = null
	static var ResourceBundle currentBundle = null


	/**
	 * Charge les différents {@link Locale} supportés pour l'application, définie le langage de l'interface par le langage de l'environnement (si celui-ci est supporté) ou
	 * par le langage en paramètre si celui-ci est spécifié et supporté.
	 * Définie le langage par défaut de l'application sur Locale.ENGLISH.<br/>
	 * Pour qu'une langage soit supporté, il faut que celui-ci soit représenté par un fichier <code>/langs/ScanExam_&ltcode langage&gt.properties</code>
	 * @param language la langue de l'application (peut être null)
	 */
	def static void init(@Nullable Locale language) {
		init(language, path)
	}
	
	/**
	 * Charge les différents {@link Locale} supportés pour l'application, définie le langage de l'interface par le langage de l'environnement (si celui-ci est supporté) ou
	 * par le langage en paramètre si celui-ci est spécifié et supporté.
	 * Définie le langage par défaut de l'application sur Locale.ENGLISH.<br/>
	 * Pour qu'une langage soit supporté, il faut que celui-ci soit représenté par un fichier <code>/langs/ScanExam_&ltcode langage&gt.properties</code> et qu'elle
	 * soit déclarée dans le fichier <code>Language_Registry</code>.
	 * @param language la langue de l'application (peut être null)
	 * @param path le chemin d'accès vers le dossier contenant les fichiers langues
	 */
	private def static void init(@Nullable Locale language, String path) {
		LanguageManager.path = path
		logger.info("Pre-loading languages...")
		
		val currentLocal = language === null ? Locale.^default : language
		
		val namePattern = prefixFileName + langCodePattern + "\\." + extFileName
		val names = ResourcesUtils.getFolderContentNames(path + "Language_Registry")
		val badFileNames = new HashSet<String>
		
		// Ajout de l'anglais pour langage par défaut en cas d'absence d'une langue choisie
		locales.add(new Locale("en"))
		Locale.^default = Locale.ENGLISH
		
		for(String name: names) {
			if(name.matches(namePattern)) {
				val langCode = name.substring(prefixFileName.length + 1, name.length - extFileName.length - 1).split("_")
				val lang = langCode.get(0)
				val country = langCode.length > 1 ? langCode.get(langCode.length - 1) : ""
				locales.add(new Locale(lang, country))
			} else if(!name.matches(prefixFileName + "\\." + extFileName))
				badFileNames.add(name)
		}
		
		if(locales.size > 0)
			logger.info(locales.stream.map(l | l.displayName).collect(Collectors.joining(", ")) + " pre-loaded.")
		
		if(badFileNames.size > 0)
			logger.info(String.join(", ", badFileNames) + " ignored (bad file names).")
			
		// La langue par défaut de l'interface est celle correspondant à la langue de l'ordinateur de l'utilisateur
		change(currentLocal)
	}
	
	/**
	 * @return une Collection non modifiable des Locale supportés par le programme 
	 */
	def static Collection<Locale> getSupportedLocales() {
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
	private def static void change(Locale language) {
		Objects.requireNonNull(language)
		val newLocale = if(locales.contains(language))
							language
						else
							locales.findFirst[l | l.language.equals(language.language)]
		currentLocale = newLocale === null ? Locale.^default : newLocale
		if(!currentLocale.equals(language))
			logger.info('''«language.displayName» is not supported, fallback to «currentLocale.displayName».''')
		currentBundle = ResourceBundle.getBundle(path + prefixFileName, currentLocale, typeof(ResourcesUtils).classLoader)
		logger.info('''Change language to «currentLocale.displayName».''')
	}
	
	/**
	 * @return le {@link Locale Locale} courant de l'application
	 * @see Locale Locale
	 */
	def static Locale getCurrentLanguage() {
		currentLocale
	}
	
	/**
	 * @return le {@link ResourceBundle ResourceBundle} courant de l'application
	 * @see ResourceBundle
	 */
	def static ResourceBundle getCurrentBundle() {
		currentBundle
	}
	
	/**
	 * @param un code à traduire (non null)
	 * @return une chaîne de caractères correspondant à la traduction de <b>code</b> dans le langage courant de l'application ou <b>code</b> si aucune traduction n'est définie pour ce code.
	 * @throw NullPointerException si <b>code</b> est null
	 */
	def static String translate(String code) {
		try {
			currentBundle.getString(code)
		} catch(MissingResourceException e) {
			logger.warn(code + " not found for " + currentLocale.displayName + ".")
			return code
		}
	}
	
	/**
	 * Convertit un String en {@link Locale}
	 * @param un Locale sous forme de String valide (non null), par exemple "fr" ou "fr_FR"
	 * @return le Locale si <i>language a pu être parsé<i>, Optional.empty sinon
	 * @throw IllegalArgumentException si le <i>language</i> n'est pas un Locale valide
	 */
	def static Optional<Locale> toLocale(String language) {
		Objects.requireNonNull(language, "Language can not be null")
		val String[] splitted = language.split("_")
		val locale = 
			if(splitted.size == 1)
				new Locale(splitted.get(0), "")
			else if(splitted.size == 2)
				new Locale(splitted.get(0), splitted.get(1))
			else
				throw new IllegalArgumentException(String.format("'%s' is not a valid locale syntax", language))
		return Optional.ofNullable(locale)
			
	}
}