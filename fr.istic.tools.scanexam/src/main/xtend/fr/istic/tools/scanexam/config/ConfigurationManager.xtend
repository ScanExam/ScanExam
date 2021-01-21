package fr.istic.tools.scanexam.config

import fr.istic.tools.scanexam.core.config.Config
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.Resource

import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.common.util.URI
import fr.istic.tools.scanexam.core.config.ConfigPackage
import java.util.logging.Logger
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path

/**
 * @author Marius Lumbroso
 * Représente la configuration de l'application.
 */
class ConfigurationManager 
{
	static final Logger logger = Logger.getGlobal();
	
	/**
	 * Chemin d'accès au fichier de configuration.
	 */
	static final String PATH = "config.xmi"
	/**
	 * Instance unique de la configuration courante 
	 */
	static Config Instance 
	
	def static void init()
	{
		val path = Paths.get(PATH);
		
		if (Files.exists(path)) 
		{  
			Instance = load();
			logger.info("Configuration loaded.");
		}  
		else  
		{ 
		 //	Instance = create();
			logger.info("Configuration created.");
			save();
		}	
	}
	
/* 	def static Config create() 
	{
		val config = ConfigFactoryImpl.init().createConfig(); <----- le soucis est ici 
		return config;
	}
	*/
	
	/**
	 * Charge la configuration courante a partir du disque.
	 * !!! IO Exception n'est jamais soulevée ? !!! 
	 */
	def static Config load()
	{
		val resourceSet = new ResourceSetImpl();
   		val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    	val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    	_extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    	
    	resourceSet.getPackageRegistry().put(ConfigPackage.eNS_URI, ConfigPackage.eINSTANCE);
    	val resource = resourceSet.getResource(URI.createFileURI(PATH), true);
    	
    	return resource.getContents().get(0) as Config; 
	}
	/**
	 * Sauvegarde la configuration courante sur le disque.
	 * !!! IO Exception n'est jamais soulevée ? !!! 
	 */
	def static void save()
	{
		val resourceSet = new ResourceSetImpl();
    	val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    	val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    	
    	_extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    	resourceSet.getPackageRegistry().put(ConfigPackage.eNS_URI, ConfigPackage.eINSTANCE);
    	
    	val resource = resourceSet.createResource(URI.createFileURI(PATH));
    	resource.getContents().add(Instance);
    	resource.save(null);
    	
    	logger.info("Configuration saved.");
	}
}