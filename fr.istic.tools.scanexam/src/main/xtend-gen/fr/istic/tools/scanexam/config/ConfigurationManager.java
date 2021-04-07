package fr.istic.tools.scanexam.config;

import fr.istic.tools.scanexam.core.config.Config;
import fr.istic.tools.scanexam.core.config.ConfigFactory;
import fr.istic.tools.scanexam.core.config.ConfigPackage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * @author Marius Lumbroso
 * Représente la configuration de l'application.
 */
@SuppressWarnings("all")
public class ConfigurationManager {
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Chemin d'accès au fichier de configuration.
   */
  private static final String PATH = "config.xmi";
  
  /**
   * Instance unique de la configuration courante
   */
  public static Config instance;
  
  /**
   * Charge le fichier de configuration a partir du disque.
   * Si le fichier n'existe pas ou est corrompue, la
   * configuration par defaut est crée.
   */
  public static void init() {
    final Path path = Paths.get(ConfigurationManager.PATH);
    boolean _exists = Files.exists(path);
    if (_exists) {
      final Optional<Config> config = ConfigurationManager.load();
      boolean _isPresent = config.isPresent();
      if (_isPresent) {
        ConfigurationManager.instance = config.get();
        ConfigurationManager.logger.info("Configuration loaded.");
      } else {
        ConfigurationManager.instance = ConfigurationManager.create();
        ConfigurationManager.save();
      }
    } else {
      ConfigurationManager.instance = ConfigurationManager.create();
      ConfigurationManager.logger.info("Configuration created.");
      ConfigurationManager.save();
    }
    System.out.println(ConfigurationManager.instance.getLanguage());
  }
  
  /**
   * Genère la configuration par defaut.
   */
  public static Config create() {
    final Config config = ConfigFactory.eINSTANCE.createConfig();
    config.setLanguage(Locale.getDefault());
    return config;
  }
  
  /**
   * Charge la configuration courante a partir du disque.
   * Si le fichier est corrompu, la configuration par defaut
   * est chargée.
   */
  public static Optional<Config> load() {
    final ResourceSetImpl resourceSet = new ResourceSetImpl();
    final Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    final XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    _extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    resourceSet.getPackageRegistry().put(ConfigPackage.eNS_URI, ConfigPackage.eINSTANCE);
    Resource resource = null;
    try {
      resource = resourceSet.getResource(URI.createFileURI(ConfigurationManager.PATH), true);
    } catch (final Throwable _t) {
      if (_t instanceof Throwable) {
        return Optional.<Config>empty();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    EObject _get = resource.getContents().get(0);
    return Optional.<Config>ofNullable(((Config) _get));
  }
  
  /**
   * Sauvegarde la configuration courante (Instance)
   * sur le disque à l'emplacement PATH.
   */
  public static void save() {
    try {
      final ResourceSetImpl resourceSet = new ResourceSetImpl();
      final Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
      final XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
      _extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
      resourceSet.getPackageRegistry().put(ConfigPackage.eNS_URI, ConfigPackage.eINSTANCE);
      final Resource resource = resourceSet.createResource(URI.createFileURI(ConfigurationManager.PATH));
      resource.getContents().add(ConfigurationManager.instance);
      resource.save(null);
      ConfigurationManager.logger.info("Configuration saved.");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
