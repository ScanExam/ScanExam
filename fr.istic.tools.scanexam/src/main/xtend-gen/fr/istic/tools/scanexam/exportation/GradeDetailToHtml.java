package fr.istic.tools.scanexam.exportation;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.exportation.QuestionDetail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Converti les détails de la note en page HTML
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class GradeDetailToHtml {
  /**
   * Titre de l'examen
   */
  private String title;
  
  /**
   * Nom de l'étudiant
   */
  private String student;
  
  /**
   * Note de l'examen
   */
  private float grade;
  
  /**
   * Barème de l'examen
   */
  private float scale;
  
  /**
   * Liste des questions
   */
  private List<QuestionDetail> questions;
  
  /**
   * Nom du fichier css utilisé
   */
  private String cssName;
  
  /**
   * Constructeur
   * @param title   Titre de l'examen
   * @param student String student
   * @param grade   Note de l'examen
   * @param scale   Barème de l'examen
   */
  public GradeDetailToHtml(final String title, final String student, final float grade, final float scale) {
    this.title = title;
    this.student = student;
    this.grade = grade;
    this.scale = scale;
    ArrayList<QuestionDetail> _arrayList = new ArrayList<QuestionDetail>();
    this.questions = _arrayList;
  }
  
  /**
   * Ajoute une question aux détails de la note avec des points obtenus et manqués
   * @param name     Nom de la question
   * @param grade    Note attribué
   * @param scale    Barème de la question
   * @param achieved Points obtenus
   * @param missed   Points manqués
   */
  public void addQuestion(final String name, final double grade, final double scale, final Map<String, Float> achieved, final Map<String, Float> missed) {
    QuestionDetail _questionDetail = new QuestionDetail(name, grade, scale, achieved, missed);
    this.questions.add(_questionDetail);
  }
  
  /**
   * Ajoute une question aux détails de la note avec des points obtenus seulement
   * @param name     Nom de la question
   * @param grade    Note attribué
   * @param scale    Barème de la question
   * @param achieved Points obtenus
   */
  public void addQuestionAchievedOnly(final String name, final double grade, final double scale, final Map<String, Float> achieved) {
    HashMap<String, Float> _hashMap = new HashMap<String, Float>();
    this.addQuestion(name, grade, scale, achieved, _hashMap);
  }
  
  /**
   * Ajoute une question aux détails de la note avec des points manqués seulement
   * @param name   Nom de la question
   * @param grade  Note attribué
   * @param scale  Barème de la question
   * @param missed Points manqués
   */
  public void addQuestionMissedOnly(final String name, final double grade, final double scale, final Map<String, Float> missed) {
    HashMap<String, Float> _hashMap = new HashMap<String, Float>();
    this.addQuestion(name, grade, scale, _hashMap, missed);
  }
  
  /**
   * Retire une question aux détails de la note
   * @param i Index de la question à retirer
   */
  public void removeQuestion(final int i) {
    this.questions.remove(i);
  }
  
  /**
   * Converti les détails de la note en html
   * @return Détails de la note sous-forme d'html
   */
  public String toHtml() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<h1>");
    _builder.append(this.title);
    _builder.append(" – ");
    _builder.append(this.student);
    _builder.append("</h1>");
    String html = _builder.toString();
    String _html = html;
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("<h2>");
    String _translate = LanguageManager.translate("gradeDetail.note");
    _builder_1.append(_translate);
    _builder_1.append(this.grade);
    _builder_1.append("/");
    _builder_1.append(this.scale);
    _builder_1.append("</h2>");
    html = (_html + _builder_1);
    for (final QuestionDetail question : this.questions) {
      String _html_1 = html;
      String _html_2 = question.toHtml();
      html = (_html_1 + _html_2);
    }
    return html;
  }
  
  /**
   * Converti les détails de la note en page html
   * @return Détails de la note sous-forme de page html
   */
  public String toHtmlPage() {
    String html = "<!DOCTYPE html>";
    String _html = html;
    html = (_html + "<html><head><meta charset=\"utf-8\"></meta>");
    if (((this.cssName != null) && (this.cssName != ""))) {
      String _html_1 = html;
      html = (_html_1 + (("<link href=\"" + this.cssName) + "\" rel=\"stylesheet\" type=\"text/css\"></link>"));
    }
    String _html_2 = html;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<title>");
    _builder.append(this.title);
    _builder.append(" – ");
    _builder.append(this.student);
    _builder.append("</title>");
    html = (_html_2 + _builder);
    String _html_3 = html;
    html = (_html_3 + "</head><body>");
    String _html_4 = html;
    String _html_5 = this.toHtml();
    html = (_html_4 + _html_5);
    String _html_6 = html;
    html = (_html_6 + "</body></html>");
    return html;
  }
  
  /**
   * Retourne le nom du fichier css utilisé
   * @return Nom du fichier css utilisé
   */
  public String getCssName() {
    return this.cssName;
  }
  
  /**
   * Met à jour le nom du fichier css utilisé. Laisser vide pour ne pas utiliser de css
   * @param cssName Nom du fichier css avec son extension
   */
  public String setCssName(final String cssName) {
    return this.cssName = cssName;
  }
}
