package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.QuestionZone;
import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesFactory;
import fr.istic.tools.scanexam.io.TemplateIo;
import fr.istic.tools.scanexam.services.ExamSingleton;
import fr.istic.tools.scanexam.services.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Classe servant de façade aux données concernant la création du modèle
 * @author Antoine Degas, Marius Lumbroso, Théo Giraudet
 */
@SuppressWarnings("all")
public class ServiceEdition extends Service {
  private CreationTemplate template;
  
  @Accessors
  private int questionId;
  
  /**
   * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
   * @param q Une Question
   * @param r Un Rectangle
   * @author degas
   */
  public int createQuestion(final int pdfPageIndex, final float x, final float y, final float heigth, final float width) {
    final Question question = CoreFactory.eINSTANCE.createQuestion();
    question.setId(this.questionId);
    question.setGradeScale(CoreFactory.eINSTANCE.createGradeScale());
    question.setZone(CoreFactory.eINSTANCE.createQuestionZone());
    QuestionZone _zone = question.getZone();
    _zone.setX(x);
    QuestionZone _zone_1 = question.getZone();
    _zone_1.setY(y);
    QuestionZone _zone_2 = question.getZone();
    _zone_2.setWidth(width);
    QuestionZone _zone_3 = question.getZone();
    _zone_3.setHeigth(heigth);
    ExamSingleton.getPage(pdfPageIndex).getQuestions().add(question);
    return this.questionId++;
  }
  
  /**
   * Redimensionne la zone d'une Question
   * @param id l'ID de la question dont la zone doit être redimensionnée
   * @param heigth la nouvelle hauteur de la zone
   * @param width la nouvelle largeur de la zone
   */
  public void rescaleQuestion(final int id, final float heigth, final float width) {
    final Question question = this.getQuestion(id);
    QuestionZone _zone = question.getZone();
    _zone.setWidth(width);
    QuestionZone _zone_1 = question.getZone();
    _zone_1.setHeigth(heigth);
  }
  
  /**
   * Déplace la zone d'une Question
   * @param id l'ID de la question dont la zone doit être déplacée
   * @param x la nouvelle position x de la zone
   * @param y la nouvelle position y de la zone
   */
  public void moveQuestion(final int id, final float x, final float y) {
    final Question question = this.getQuestion(id);
    QuestionZone _zone = question.getZone();
    _zone.setX(x);
    QuestionZone _zone_1 = question.getZone();
    _zone_1.setY(y);
  }
  
  /**
   * Renomme la Question
   * @param id l'ID de la question à renommer
   * @param name le nouveau nom de la question
   */
  public void renameQuestion(final int id, final String name) {
    final Question question = this.getQuestion(id);
    question.setName(name);
  }
  
  /**
   * Supprime une question
   * @param id l'ID de la question à supprimer
   */
  public void removeQuestion(final int id) {
    EList<Page> _pages = ExamSingleton.instance.getPages();
    for (final Page page : _pages) {
      EList<Question> _questions = page.getQuestions();
      for (final Question question : _questions) {
        int _id = question.getId();
        boolean _equals = (_id == id);
        if (_equals) {
          page.getQuestions().remove(question);
        }
      }
    }
  }
  
  /**
   * Modifie la note maximal que l'on peut attribuer a une question.
   * @param questionId, l'ID de la question a laquelle on veut modifier la note maximal possible
   * @param maxPoint, note maximal de la question question a ajouter
   */
  public void modifyMaxPoint(final int questionId, final float maxPoint) {
    final GradeScale scale = this.getQuestion(questionId).getGradeScale();
    if ((maxPoint > 0)) {
      scale.setMaxPoint(maxPoint);
    }
  }
  
  /**
   * Sauvegarde le fichier modèle d'examen sur le disque
   * @param path L'emplacement de sauvegarde du fichier
   * @param pdfOutputStream le contenu du fichier sous forme de Stream
   */
  public void save(final ByteArrayOutputStream outputStream, final File path) {
    try {
      final byte[] encoded = Base64.getEncoder().encode(outputStream.toByteArray());
      String _string = new String(encoded);
      this.template.setEncodedDocument(_string);
      outputStream.close();
      this.template.setExam(ExamSingleton.instance);
      TemplateIo.save(path, this.template);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Charge un fichier modèle d'examen a partir du disque
   * @params xmiPath L'emplacement du fichier.
   * @returns un flux vers le contenu du fichier si celui-ci a bien été ouvert, Optional.empty sinon
   */
  public Optional<ByteArrayInputStream> open(final String xmiPath) {
    final Optional<CreationTemplate> creationTemplate = TemplateIo.loadCreationTemplate(xmiPath);
    boolean _isPresent = creationTemplate.isPresent();
    if (_isPresent) {
      this.template = creationTemplate.get();
      ExamSingleton.instance = creationTemplate.get().getExam();
      final byte[] decoded = Base64.getDecoder().decode(creationTemplate.get().getEncodedDocument());
      final Function<Page, Integer> _function = (Page page) -> {
        return Integer.valueOf(page.getQuestions().size());
      };
      final BinaryOperator<Integer> _function_1 = (Integer acc, Integer num) -> {
        return Integer.valueOf(((acc).intValue() + (num).intValue()));
      };
      Integer _get = ExamSingleton.instance.getPages().stream().<Integer>map(_function).reduce(_function_1).get();
      int _plus = ((_get).intValue() + 1);
      this.questionId = _plus;
      ByteArrayInputStream _byteArrayInputStream = new ByteArrayInputStream(decoded);
      return Optional.<ByteArrayInputStream>of(_byteArrayInputStream);
    }
    return Optional.<ByteArrayInputStream>empty();
  }
  
  /**
   * Crée un nouveau modèle côté données
   * @param pageNumber le nombre de pages du modèle
   */
  @Override
  public void onDocumentLoad(final int pageNumber) {
    this.template = TemplatesFactory.eINSTANCE.createCreationTemplate();
    ExamSingleton.instance = CoreFactory.eINSTANCE.createExam();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, pageNumber, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Page page = CoreFactory.eINSTANCE.createPage();
        ExamSingleton.instance.getPages().add(page);
      }
    }
    this.questionId = 0;
  }
  
  /**
   * Retourne la zone associée à une question
   * @param index Index de la question //FIXME (useless?)
   * @author degas
   */
  public QuestionZone getQuestionZone(final int pageIndex, final int questionIndex) {
    return ExamSingleton.getQuestion(pageIndex, questionIndex).getZone();
  }
  
  @Pure
  public int getQuestionId() {
    return this.questionId;
  }
  
  public void setQuestionId(final int questionId) {
    this.questionId = questionId;
  }
}
