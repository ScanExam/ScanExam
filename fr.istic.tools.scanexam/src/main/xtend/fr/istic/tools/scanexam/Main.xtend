package fr.istic.tools.scanexam

import fr.istic.tools.scanexam.config.ConfigurationManager
import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.launcher.Launcher
import fr.istic.tools.scanexam.launcher.LauncherFX
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.config.Configurator
import fr.istic.tools.scanexam.services.ServiceEdition
import fr.istic.tools.scanexam.services.ServiceGraduation

class Main 
{
	static val logger = LogManager.logger
	

	def static void main(String[] args)
	{
	
		Configurator.setAllLevels(LogManager.rootLogger.getName, Level.ALL)
		ConfigurationManager.init
		LanguageManager.init(ConfigurationManager.instance.language)

	
		val serviceEdition = new ServiceEdition();
		val serviceGraduation = new ServiceGraduation();
		LauncherFX.launchApp(serviceEdition,serviceGraduation)

	}
	
	
	
	
	
}