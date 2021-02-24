package fr.istic.tools.scanexam

import fr.istic.tools.scanexam.config.ConfigurationManager
import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.launcher.LauncherFX
import fr.istic.tools.scanexam.launcher.LauncherSwing
import java.util.Objects
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.config.Configurator
import fr.istic.tools.scanexam.services.ExamEditionService
import fr.istic.tools.scanexam.presenter.PresenterVueCreation

class Main 
{
	static val logger = LogManager.logger
	
	/**
	 * Library graphique à utiliser par défaut. 
	 */
	static val DEFAULT_LIB = GraphicLib.SWING;
	
	def static void main(String[] args)
	{
		
		Configurator.setAllLevels(LogManager.rootLogger.getName, Level.ALL)
		ConfigurationManager.init
		LanguageManager.init(ConfigurationManager.instance.language)

		val service = new ExamEditionService();
		val presenter = new PresenterVueCreation(service);
		
		launchView(getUiLib(args))
		
	}
	
	def static void launchView(GraphicLib graphicLib) {
		switch(graphicLib) {
			case GraphicLib.JAVAFX : LauncherFX.launchApp(null)
			case GraphicLib.SWING : LauncherSwing.launchApp()
		} 
	}	
	
	
	/**
	 * Parse les arguments d'entrée du programme et retourne l'ID correspondant à la library graphique choisie.<br/>
	 * Déclenche l'arrêt du programme si il y a plus d'argument spécifié ou que l'argument spécifié n'est pas un argument valide.
	 * @param args un tableau d'arguments (non null)
	 * @return {@link GraphicLib}.JAVAFX si JavaFX est choisi, {@link GraphicLib}.Swing si Swing est choisi, {@link #DEFAULT_LIB DEFAULT_LIB} si <b>args</b> est vide.
	 */
	def static GraphicLib getUiLib(String... args) {
		Objects.requireNonNull(args)
		if(args.length == 1) {
			if(args.get(0).equals("-javafx"))
				return GraphicLib.JAVAFX
			else if(args.get(0).equals("-swing"))
				return GraphicLib.SWING
			else {
				logger.error("Argument " + args.get(0) + " is not a valid argument!")
				System.exit(0)
			}
		} else if(args.length > 1){
			logger.error("Too many arguments! Only '-javafx' or '-swing' are accepted.")
			System.exit(0)
		}
		return DEFAULT_LIB
	}
	
	/**
	 * Représente la library graphique choisi par l'utilisateur pour le programme
	 */
	enum GraphicLib {
		JAVAFX, SWING
	}
}