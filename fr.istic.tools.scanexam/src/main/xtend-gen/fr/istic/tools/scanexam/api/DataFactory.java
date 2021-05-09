package fr.istic.tools.scanexam.api;

import fr.istic.tools.scanexam.core.Comment;
import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.GradeEntry;
import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.QuestionZone;
import fr.istic.tools.scanexam.core.StudentInformation;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.TextComment;
import java.util.List;

/**
 * Factory qui crée des objets de type Data: {@link Rectangle},   {@link StudentSheet},  {@link Grade},  {@link GradeScale}, {@link Question}
 * 
 * @author Antoine Degas
 */
@SuppressWarnings("all")
public class DataFactory {
  /**
   * Un rectangle permet de définir une zone sur l'interface graphique
   * @param x
   * @param y
   * @param width largeur du rectangle
   * @param height hauteur du rectangle
   * @return une instance d'objet de type {@link Rectangle}
   * @author Antoine Degas
   */
  public QuestionZone createRectangle(final float x, final float y, final float width, final float height) {
    QuestionZone _xblockexpression = null;
    {
      final QuestionZone rec = CoreFactory.eINSTANCE.createQuestionZone();
      rec.setX(x);
      rec.setY(y);
      rec.setWidth(width);
      rec.setHeigth(height);
      _xblockexpression = rec;
    }
    return _xblockexpression;
  }
  
  /**
   * @return une instance d'objet de type {@link StudentSheet}
   * @author Antoine Degas
   */
  public StudentSheet createStudentSheet(final int idSheet, final List<Integer> pages) {
    StudentSheet _xblockexpression = null;
    {
      final StudentSheet sheet = CoreFactory.eINSTANCE.createStudentSheet();
      sheet.setId(idSheet);
      sheet.getPosPage().addAll(pages);
      _xblockexpression = sheet;
    }
    return _xblockexpression;
  }
  
  /**
   * @return une instance d'objet de type {@link StudentInformation}
   * @author Théo Giraudet
   */
  public StudentInformation createStudentInformation(final String name, final String address) {
    StudentInformation _xblockexpression = null;
    {
      final StudentInformation infos = CoreFactory.eINSTANCE.createStudentInformation();
      infos.setName(name);
      infos.setEmailAddress(address);
      _xblockexpression = infos;
    }
    return _xblockexpression;
  }
  
  /**
   * @return une instance d'objet de type {@link Grade}
   * @author Antoine Degas
   */
  public Grade createGrade() {
    return CoreFactory.eINSTANCE.createGrade();
  }
  
  /**
   * @return une instance d'objet de type {@link GradeScale}
   * @author Antoine Degas
   */
  public GradeScale createGradeScale() {
    return CoreFactory.eINSTANCE.createGradeScale();
  }
  
  /**
   * @return une instance d'objet de type {@link GradeEntry}
   * @author Théo Giraudet
   */
  public GradeEntry createGradeEntry(final int id, final String desc, final float point) {
    GradeEntry _xblockexpression = null;
    {
      final GradeEntry scale = CoreFactory.eINSTANCE.createGradeEntry();
      scale.setId(id);
      scale.setHeader(desc);
      scale.setStep(point);
      _xblockexpression = scale;
    }
    return _xblockexpression;
  }
  
  /**
   * Une question contient un identifiant et un {@link GradeScale}
   * @param id identifiant de la question
   * @return une instance d'objet de type {@link Question}
   * @author Antoine Degas
   */
  public Question createQuestion(final int id) {
    Question _xblockexpression = null;
    {
      final Question question = CoreFactory.eINSTANCE.createQuestion();
      question.setId(id);
      _xblockexpression = question;
    }
    return _xblockexpression;
  }
  
  public Comment createTextComment(final int id, final String text, final float x, final float y, final float h, final float w, final float pointerX, final float pointerY, final int pageId) {
    TextComment _xblockexpression = null;
    {
      final TextComment annot = CoreFactory.eINSTANCE.createTextComment();
      annot.setX(x);
      annot.setY(y);
      annot.setPointerX(pointerX);
      annot.setPointerY(pointerY);
      annot.setId(id);
      annot.setText(text);
      annot.setPageId(pageId);
      _xblockexpression = annot;
    }
    return _xblockexpression;
  }
}
