package fr.istic.tools.scanexam.services.api;

import fr.istic.tools.scanexam.services.api.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Optional;

@SuppressWarnings("all")
public interface ServiceEdition extends Service {
  /**
   * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
   * @param q Une Question
   * @param r Un Rectangle
   * @author degas
   */
  int createQuestion(final int pdfPageIndex, final float x, final float y, final float heigth, final float width);
  
  /**
   * Redimensionne la zone d'une Question
   * @param id l'ID de la question dont la zone doit être redimensionnée
   * @param heigth la nouvelle hauteur de la zone
   * @param width la nouvelle largeur de la zone
   */
  void rescaleQuestion(final int id, final float heigth, final float width);
  
  /**
   * Déplace la zone d'une Question
   * @param id l'ID de la question dont la zone doit être déplacée
   * @param x la nouvelle position x de la zone
   * @param y la nouvelle position y de la zone
   */
  void moveQuestion(final int id, final float x, final float y);
  
  /**
   * Renomme la Question
   * @param id l'ID de la question à renommer
   * @param name le nouveau nom de la question
   */
  void renameQuestion(final int id, final String name);
  
  /**
   * Supprime une question
   * @param id l'ID de la question à supprimer
   * @return true si l'élément a bien été supprimé, false sinon
   */
  boolean removeQuestion(final int id);
  
  /**
   * Modifie la note maximal que l'on peut attribuer a une question.
   * @param questionId l'ID de la question a laquelle on veut modifier la note maximal possible
   * @param maxPoint note maximal de la question question a ajouter
   */
  void modifyMaxPoint(final int questionId, final float maxPoint);
  
  /**
   * Sauvegarde le fichier modèle d'examen sur le disque
   * @param path L'emplacement de sauvegarde du fichier
   * @param pdfOutputStream le contenu du fichier sous forme de Stream
   */
  void save(final ByteArrayOutputStream outputStream, final File path);
  
  /**
   * Charge un fichier modèle d'examen a partir du disque
   * @param xmiPath L'emplacement du fichier XMI
   * @returns un flux vers le contenu du fichier si celui-ci a bien été ouvert, Optional.empty sinon
   */
  Optional<ByteArrayInputStream> open(final String xmiPath);
  
  /**
   * Crée et initialise un nouveau modèle d'Examen
   * @param pageNumber le nombre de pages du modèle
   */
  void initializeEdition(final int pageNumber);
}
