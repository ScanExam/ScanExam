package fr.istic.tools.scanexam

import fr.istic.tools.scanexam.config.ConfigurationManager
import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.launcher.LauncherFX
import fr.istic.tools.scanexam.services.ServiceImpl
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.config.Configurator

class Main 
{
	
	def static void main(String[] args)
	{
	
		Configurator.setAllLevels(LogManager.rootLogger.getName, Level.ALL)
		ConfigurationManager.init
		LanguageManager.init(ConfigurationManager.instance.language)

	
		val service = new ServiceImpl();
		LauncherFX.launchApp(service, service)

	}
	
}