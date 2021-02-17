package fr.istic.tools.scanexam.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Une classe utilitaire pour obtenir les ressources du programme
 * @author Théo Giraudet
 */
@SuppressWarnings("all")
public class ResourcesUtils {
  /**
   * @param relativePath le chemin relatif non null vers la ressource en partant du répertoire <i>src/main/resources</i>
   * @return un inputStream si une ressource a bien été trouvée, null sinon
   * @throw NullPointerException si <b>relativePath</b> est null
   */
  public static InputStream getInputStreamResource(final String relativePath) {
    return ResourcesUtils.class.getResourceAsStream(relativePath);
  }
  
  /**
   * @param relativePath le chemin relatif non null vers la ressource en partant du répertoire <i>src/main/resources</i>
   * @return une chaîne de caractères correspondant au contenu du fichier pointé par <b>relativePath</b>, null si le fichier n'a pas été trouvé
   * @throw NullPointerException si <b>relativePath</b> est null
   */
  public static String getTextResource(final String relativePath) {
    final Function<Stream<String>, String> _function = (Stream<String> s) -> {
      return s.collect(Collectors.joining("\n"));
    };
    return ResourcesUtils.getFileLines(relativePath).<String>map(_function).orElse(null);
  }
  
  /**
   * @param relativeFolderPath le chemin relatif non null vers le dossier ressource en partant du répertoire <i>src/main/resources</i>
   * @return une Collection de String contenant les noms de tous les fichiers se trouvant dans le dossier ressource pointé par <b>relativeFolderPath</b> ou null si le répertoire n'a pas été trouvé.
   * @throw NullPointerException si <b>relativePath</b> est null
   */
  public static Collection<String> getFolderContentNames(final String relativeFolderPath) {
    final Function<Stream<String>, Set<String>> _function = (Stream<String> s) -> {
      return s.collect(Collectors.<String>toSet());
    };
    return ResourcesUtils.getFileLines(relativeFolderPath).<Set<String>>map(_function).orElse(null);
  }
  
  /**
   * @param relativePath le chemin relatif vers la ressource en partant du répertoire <i>src/main/resources</i>
   * @return un flux composé de toutes les lignes du fichier. Dans ce cas d'un répertoire, chaque élément est le nom d'un fichier contenu dans celui-ci ou null si le fichier n'a pas été trouvé.
   */
  private static Optional<Stream<String>> getFileLines(final String relativePath) {
    Optional<Stream<String>> _xblockexpression = null;
    {
      final InputStream stream = ResourcesUtils.getInputStreamResource(relativePath);
      Optional<Stream<String>> _xifexpression = null;
      if ((stream == null)) {
        _xifexpression = Optional.<Stream<String>>empty();
      } else {
        InputStreamReader _inputStreamReader = new InputStreamReader(stream);
        _xifexpression = Optional.<Stream<String>>of(new BufferedReader(_inputStreamReader).lines());
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
