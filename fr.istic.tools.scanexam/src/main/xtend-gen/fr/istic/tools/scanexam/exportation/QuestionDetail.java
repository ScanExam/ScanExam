package fr.istic.tools.scanexam.exportation;

import fr.istic.tools.scanexam.config.LanguageManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Classe contenant toutes les informations lié à une question
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class QuestionDetail {
  /**
   * Nom de la question
   */
  private String name;
  
  /**
   * Note attribuée
   */
  private double grade;
  
  /**
   * Barème de la question
   */
  private double scale;
  
  /**
   * Points obtenus (commentaire associé, et nombre de points)
   */
  private Map<String, Float> achieved;
  
  /**
   * Points manqués (commentaire associé, et nombre de points)
   */
  private Map<String, Float> missed;
  
  /**
   * Constructeur
   * @param name     Nom de la question
   * @param grade    Note attribué
   * @param scale    Barème de la question
   * @param achieved Points obtenus
   * @param missed   Points manqués
   */
  public QuestionDetail(final String name, final double grade, final double scale, final Map<String, Float> achieved, final Map<String, Float> missed) {
    this.name = name;
    this.grade = grade;
    this.scale = scale;
    HashMap<String, Float> _hashMap = new HashMap<String, Float>(achieved);
    this.achieved = _hashMap;
    HashMap<String, Float> _hashMap_1 = new HashMap<String, Float>(missed);
    this.missed = _hashMap_1;
  }
  
  /**
   * Converti les informations de la question en contenu de tableau html
   * @return Informations de la question sous-forme de contenu de tableau html
   */
  public String toHtml() {
    String html = "";
    if (((this.achieved.size() > 0) || (this.missed.size() > 0))) {
      String _html = html;
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<h3>");
      _builder.append(this.name);
      _builder.append(" : ");
      _builder.append(this.grade);
      _builder.append("/");
      _builder.append(this.scale);
      _builder.append("</h3>");
      html = (_html + _builder);
      int _size = this.achieved.size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        String _html_1 = html;
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("<p>");
        String _translate = LanguageManager.translate("gradeDetail.achievedPoints");
        _builder_1.append(_translate);
        _builder_1.append("</p>");
        html = (_html_1 + _builder_1);
        String _html_2 = html;
        html = (_html_2 + "<table><tbody class=\"achieved\">");
        Set<Map.Entry<String, Float>> _entrySet = this.achieved.entrySet();
        for (final Map.Entry<String, Float> entry : _entrySet) {
          String _html_3 = html;
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("<tr><td>");
          String _key = entry.getKey();
          _builder_2.append(_key);
          _builder_2.append("</td><td>+");
          Float _value = entry.getValue();
          _builder_2.append(_value);
          String _translate_1 = LanguageManager.translate("gradeDetail.pointAbbreviation");
          _builder_2.append(_translate_1);
          _builder_2.append("</td></tr>");
          html = (_html_3 + _builder_2);
        }
        String _html_4 = html;
        html = (_html_4 + "</tbody></table>");
      }
      int _size_1 = this.missed.size();
      boolean _greaterThan_1 = (_size_1 > 0);
      if (_greaterThan_1) {
        String _html_5 = html;
        StringConcatenation _builder_3 = new StringConcatenation();
        String _translate_2 = LanguageManager.translate("gradeDetail.missedPoints");
        _builder_3.append(_translate_2);
        html = (_html_5 + _builder_3);
        String _html_6 = html;
        html = (_html_6 + "<table><tbody class=\"missed\">");
        Set<Map.Entry<String, Float>> _entrySet_1 = this.missed.entrySet();
        for (final Map.Entry<String, Float> entry_1 : _entrySet_1) {
          String _html_7 = html;
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("<tr><td>");
          String _key_1 = entry_1.getKey();
          _builder_4.append(_key_1);
          _builder_4.append("</td><td>");
          Float _value_1 = entry_1.getValue();
          _builder_4.append(_value_1);
          String _translate_3 = LanguageManager.translate("gradeDetail.pointAbbreviation");
          _builder_4.append(_translate_3);
          _builder_4.append("</td></tr>");
          html = (_html_7 + _builder_4);
        }
        String _html_8 = html;
        html = (_html_8 + "</tbody></table>");
      }
    }
    return html;
  }
}
