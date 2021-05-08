package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate;
import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.io.TemplateIo;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Classe servant de façade aux données concernant la correction
 * @author Antoine Degas, Marius Lumbroso, Théo Giraudet, Thomas Guibert
 */
@SuppressWarnings("all")
public class ServiceImpl implements ServiceGraduation, ServiceEdition {
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Index de la page courante du modèle d'exam
   */
  protected int pageIndex;
  
  /**
   * La page actuelle de l'examen
   */
  private int currentSheetIndex;
  
  private int gradeEntryId;
  
  private int annotationId;
  
  /**
   * Fichier du template de l'édition d'examen (Fichier de méta données sur le sujet d'examen)
   */
  private CreationTemplate editionTemplate;
  
  /**
   * Fichier du template de correction d'examen
   * (Fichier de méta données sur les corrections de copies déja effectués)
   */
  private CorrectionTemplate graduationTemplate;
  
  /**
   * Sauvegarde le fichier de correction d'examen sur le disque.
   * @param path L'emplacement de sauvegarde du fichier.
   * @param pdfOutputStream le contenu du fichier sous forme de Stream
   */
  @Override
  public void saveCorrectionTemplate(final String path, final ByteArrayOutputStream pdfOutputStream) {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method saveEdition(ByteArrayOutputStream, File) is not applicable for the arguments (ServiceImpl)"
      + "\nType mismatch: cannot convert implicit first argument from ServiceImpl to ByteArrayOutputStream");
  }
  
  /**
   * Charge un fichier de correction d'examen a partir du disque.
   * @params path le fichier
   * @returns Un inputStream vers le PDF si le template a bien pu être chargé, Optional.empty sinon
   */
  @Override
  public Optional<InputStream> openCorrectionTemplate(final File xmiFile) {
    final Optional<CorrectionTemplate> correctionTemplate = TemplateIo.loadCorrectionTemplate(xmiFile.getAbsolutePath());
    boolean _isPresent = correctionTemplate.isPresent();
    if (_isPresent) {
      this.graduationTemplate = correctionTemplate.get();
      final byte[] decoded = Base64.getDecoder().decode(this.graduationTemplate.getEncodedDocument());
      ByteArrayInputStream _byteArrayInputStream = new ByteArrayInputStream(decoded);
      return Optional.<InputStream>of(_byteArrayInputStream);
    }
    return Optional.<InputStream>empty();
  }
  
  /**
   * Crée une nouvelle correction à partir d'une liste de StudentSheets
   * @params studentSheets une liste de StudenSheet
   * @returns "true" si la correction a pu être créée, "false" sinon
   */
  @Override
  public boolean initializeCorrection(final Collection<StudentSheet> studentSheets) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method getPage(int) is undefined"
      + "\nquestions cannot be resolved"
      + "\nsize cannot be resolved");
  }
  
  @Override
  public int getAbsolutePageNumber(final int studentId, final int offset) {
    final Function1<StudentSheet, Boolean> _function = (StudentSheet x) -> {
      int _id = x.getId();
      return Boolean.valueOf((_id == studentId));
    };
    final Integer pageId = IterableExtensions.<StudentSheet>findFirst(this.getStudentSheets(), _function).getPosPage().get(0);
    return ((pageId).intValue() + offset);
  }
  
  /**
   * Définit la copie d'étudiant suivant la copie actuelle comme nouvelle copie courante
   * Si la copie courante est la dernière, ne fait rien
   */
  @Override
  public void nextSheet() {
    int _size = this.getStudentSheets().size();
    boolean _lessThan = ((this.currentSheetIndex + 1) < _size);
    if (_lessThan) {
      this.currentSheetIndex++;
    }
  }
  
  /**
   * Définit la copie d'étudiant précédant la copie actuelle comme nouvelle copie courante
   * Si la copie courante est la première, ne fait rien
   */
  @Override
  public void previousSheet() {
    if ((this.currentSheetIndex > 0)) {
      this.currentSheetIndex--;
    }
  }
  
  /**
   * Associe un nouveau identifiant d'étudiant à la copie courante
   * @param id le nouvel identifiant d'étudiant
   */
  @Override
  public void assignStudentName(final String id) {
    ServiceImpl.logger.info(((("Renaming student :" + Integer.valueOf(this.currentSheetIndex)) + "with name :") + id));
    StudentSheet _get = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    _get.setStudentName(id);
  }
  
  /**
   * @return la liste non modifiable de tous les StudentSheets
   */
  @Override
  public Collection<StudentSheet> getStudentSheets() {
    if ((this.graduationTemplate == null)) {
      return List.<StudentSheet>of();
    }
    return Collections.<StudentSheet>unmodifiableList(this.graduationTemplate.getStudentsheets());
  }
  
  /**
   * @return le nom de l'etudiant dont l'ID de la copie est id si la copie existe, Optional.empty sinon
   * @param id l'ID de la copie
   */
  @Override
  public Optional<String> getStudentName(final int id) {
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'<\'"
      + "\nno viable alternative at input \'<\'"
      + "\nmissing \'}\' at \'===\'"
      + "\nno viable alternative at input \'===\'"
      + "\nno viable alternative at input \'=\'"
      + "\nmissing \'}\' at \'>\'"
      + "\nno viable alternative at input \'>\'"
      + "\nno viable alternative at input \'>\'"
      + "\nextraneous input \':\' expecting \'}\'"
      + "\n>>> cannot be resolved."
      + "\n=== cannot be resolved."
      + "\nThe method or field < is undefined"
      + "\nThe method or field < is undefined"
      + "\nThe method or field HEAD is undefined"
      + "\nThe method or field branch is undefined"
      + "\nThe method or field of is undefined"
      + "\nThe method or field https is undefined"
      + "\nThis expression is not allowed in this context, since it doesn\'t cause any side effects."
      + "\n<<< cannot be resolved"
      + "\n=== cannot be resolved"
      + "\n>>> cannot be resolved"
      + "\n> cannot be resolved");
  }
}
