package fr.istic.tools.scanexam.config

import fr.istic.tools.scanexam.core.config.Config
import fr.istic.tools.scanexam.core.config.ConfigFactory
import fr.istic.tools.scanexam.core.config.ConfigPackage
import java.nio.file.Files
import java.nio.file.Paths
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import java.util.Optional
import java.util.Locale
import org.apache.logging.log4j.LogManager

/**
 * @author Marius Lumbroso
 * Représente la configuration de l'application.
 */
class ConfigurationManager 
{
	static val logger = LogManager.logger
	
	/**
	 * Chemin d'accès au fichier de configuration.
	 */
	static final String PATH = "config.xmi"
	/**
	 * Instance unique de la configuration courante 
	 */
	public static Config instance 
	
	/**
	 * Charge le fichier de configuration a partir du disque.
	 * Si le fichier n'existe pas ou est corrompue, la 
	 * configuration par defaut est crée.
	 */
	def static void init()
	{
		val path = Paths.get(PATH);
		
		if (Files.exists(path)) 
		{
			val config = load();
			
			if (config.present)
			{
				instance = config.get
				logger.info("Configuration loaded.");
			}
			else
			{	
			//	logger.log(Level.WARNING , "Unable to load configuration. Using default."); // TODO
				instance = create()
				save()
			}
		}  
		else  
		{ 
		 	instance = create();
			logger.info("Configuration created.");
			save();
		}
	}
	/**
	 * Genère la configuration par defaut.
	 */
 	def static Config create() 
	{
		val config = ConfigFactory.eINSTANCE.createConfig();
		config.language = Locale.^default
		return config;
	}
	
	
	/**
	 * Charge la configuration courante a partir du disque.
	 * Si le fichier est corrompu, la configuration par defaut 
	 * est chargée.
	 */
	def static Optional<Config> load()
	{
		val resourceSet = new ResourceSetImpl();
   		val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    	val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    	_extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    	
    	resourceSet.getPackageRegistry().put(ConfigPackage.eNS_URI, ConfigPackage.eINSTANCE);
    	
    	var Resource resource = null;
    	
    	try
    	{
    		 resource = resourceSet.getResource(URI.createFileURI(PATH), true);
    	}
    	catch (Throwable ex)
    	{
    	 	return Optional.empty;
    	}
    	
    	return Optional.ofNullable(resource.getContents().get(0) as Config); 
	}
	/**
	 * Sauvegarde la configuration courante (Instance) 
	 * sur le disque à l'emplacement PATH.
	 *  
	 */
	def static void save()
	{
		val resourceSet = new ResourceSetImpl();
    	val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    	val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    	
    	_extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    	resourceSet.getPackageRegistry().put(ConfigPackage.eNS_URI, ConfigPackage.eINSTANCE);
    	
    	val resource = resourceSet.createResource(URI.createFileURI(PATH));
    	
    	resource.getContents().add(instance);
    
    	resource.save(null);
    	
    	logger.info("Configuration saved.");
	}
}