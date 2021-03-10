package fr.istic.tools.scanexam.utils

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Collection
import java.util.Optional
import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * Une classe utilitaire pour obtenir les ressources du programme
 * @author Théo Giraudet
 */
class ResourcesUtils {
	
	/**
	 * @param relativePath le chemin relatif non null vers la ressource en partant du répertoire <i>src/main/resources</i>
	 * @return un inputStream si une ressource a bien été trouvée, null sinon
	 * @throw NullPointerException si <b>relativePath</b> est null
	 */
	static def InputStream getInputStreamResource(String relativePath) {
		typeof(ResourcesUtils).classLoader.getResourceAsStream(relativePath)
	}
	
	/**
	 * @param relativePath le chemin relatif non null vers la ressource en partant du répertoire <i>src/main/resources</i>
	 * @return une chaîne de caractères correspondant au contenu du fichier pointé par <b>relativePath</b>, null si le fichier n'a pas été trouvé
	 * @throw NullPointerException si <b>relativePath</b> est null
	 */
	static def String getTextResource(String relativePath) {
		getFileLines(relativePath).map(s | s.collect(Collectors.joining("\n"))).orElse(null)
	}
	
	
	/**
	 * @param relativeFolderPath le chemin relatif non null vers le dossier ressource en partant du répertoire <i>src/main/resources</i>
	 * @return une Collection de String contenant les noms de tous les fichiers se trouvant dans le dossier ressource pointé par <b>relativeFolderPath</b> ou null si le répertoire n'a pas été trouvé.
	 * @throw NullPointerException si <b>relativePath</b> est null
	 */
	static def Collection<String> getFolderContentNames(String relativeFolderPath) {
		getFileLines(relativeFolderPath).map(s | s.collect(Collectors.toSet)).orElse(null)
	}
	
	/**
	 * @param relativePath le chemin relatif vers la ressource en partant du répertoire <i>src/main/resources</i>
	 * @return un flux composé de toutes les lignes du fichier. Dans ce cas d'un répertoire, chaque élément est le nom d'un fichier contenu dans celui-ci ou null si le fichier n'a pas été trouvé.
	 */
	private static def Optional<Stream<String>> getFileLines(String relativePath) {
		val stream = (getInputStreamResource(relativePath))
		stream === null ? Optional.empty : Optional.of(new BufferedReader(new InputStreamReader(stream)).lines)
	}
}