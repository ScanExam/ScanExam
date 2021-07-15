package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.Comment;
import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.Exam;
import fr.istic.tools.scanexam.core.Grade;
import fr.istic.tools.scanexam.core.GradeEntry;
import fr.istic.tools.scanexam.core.GradeScale;
import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.QrCodeZone;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.QuestionZone;
import fr.istic.tools.scanexam.core.StudentInformation;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.TextComment;
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate;
import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesFactory;
import fr.istic.tools.scanexam.services.TemplateIo;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.utils.DataFactory;
import fr.istic.tools.scanexam.utils.Tuple3;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.ArrayExtensions;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

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
    try {
      final byte[] encoded = Base64.getEncoder().encode(pdfOutputStream.toByteArray());
      String _string = new String(encoded);
      this.graduationTemplate.setEncodedDocument(_string);
      pdfOutputStream.close();
      File _file = new File(path);
      TemplateIo.save(_file, this.graduationTemplate);
      this.saveEdition();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
      final Function1<StudentSheet, Integer> _function = (StudentSheet s) -> {
        return Integer.valueOf(s.getId());
      };
      final List<StudentSheet> sorted = IterableExtensions.<StudentSheet>toList(IterableExtensions.<StudentSheet, Integer>sortBy(this.graduationTemplate.getStudentsheets(), _function));
      this.graduationTemplate.getStudentsheets().clear();
      this.graduationTemplate.getStudentsheets().addAll(sorted);
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
  public boolean initializeCorrection(final Collection<StudentSheet> studentSheets, final Collection<Integer> failedPages, final Collection<StudentSheet> uncompleteStudentSheets) {
    this.graduationTemplate = TemplatesFactory.eINSTANCE.createCorrectionTemplate();
    try {
      for (final StudentSheet sheet : studentSheets) {
        this.initSheet(sheet);
      }
      final Function1<StudentSheet, Integer> _function = (StudentSheet s) -> {
        return Integer.valueOf(s.getId());
      };
      this.graduationTemplate.getStudentsheets().addAll(IterableExtensions.<StudentSheet, Integer>sortBy(studentSheets, _function));
      final Function1<Integer, Integer> _function_1 = (Integer a) -> {
        return a;
      };
      this.graduationTemplate.getFailedPages().addAll(IterableExtensions.<Integer, Integer>sortBy(failedPages, _function_1));
      final Function1<StudentSheet, Integer> _function_2 = (StudentSheet s) -> {
        return Integer.valueOf(s.getId());
      };
      this.graduationTemplate.getUncompleteStudentSheets().addAll(IterableExtensions.<StudentSheet, Integer>sortBy(uncompleteStudentSheets, _function_2));
      return true;
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * initialise les "grades" pour la studentsheet
   * @param sheet la copie de l'élève
   */
  public void initSheet(final StudentSheet sheet) {
    for (int i = 0; (i < this.getTemplatePageAmount()); i++) {
      {
        final Page examPage = this.getPage(i);
        for (int j = 0; (j < examPage.getQuestions().size()); j++) {
          {
            Grade grade = CoreFactory.eINSTANCE.createGrade();
            sheet.getGrades().add(grade);
          }
        }
      }
    }
  }
  
  @Override
  public int getAbsolutePageNumber(final int studentId, final int offset) {
    final Function1<StudentSheet, Boolean> _function = (StudentSheet x) -> {
      int _id = x.getId();
      return Boolean.valueOf((_id == studentId));
    };
    final Integer pageId = IterableExtensions.<StudentSheet>findFirst(this.getStudentSheets(), _function).getPosPage().get(offset);
    return (pageId).intValue();
  }
  
  /**
   * Définit la copie d'étudiant suivant la copie actuelle comme nouvelle copie courante
   * Si la copie courante est la dernière, va à la première page
   */
  @Override
  public void nextSheet() {
    int _size = this.getStudentSheets().size();
    boolean _lessThan = ((this.currentSheetIndex + 1) < _size);
    if (_lessThan) {
      this.currentSheetIndex++;
    } else {
      this.currentSheetIndex = 0;
    }
  }
  
  /**
   * Définit la copie d'étudiant précédant la copie actuelle comme nouvelle copie courante
   * Si la copie courante est la première, va à la dernière page
   */
  @Override
  public void previousSheet() {
    if ((this.currentSheetIndex > 0)) {
      this.currentSheetIndex--;
    } else {
      int _size = this.getStudentSheets().size();
      int _minus = (_size - 1);
      this.currentSheetIndex = _minus;
    }
  }
  
  /**
   * Associe un nouveau identifiant d'étudiant à la copie courante
   * @param id le nouvel identifiant d'étudiant
   */
  @Override
  public void assignStudentId(final String id) {
    ServiceImpl.logger.info(((("Renaming student :" + Integer.valueOf(this.currentSheetIndex)) + "with name :") + id));
    StudentSheet _get = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    _get.setStudentID(id);
    final List<String> studentInfos = this.getStudentInfos(id);
    int _size = studentInfos.size();
    boolean _greaterEqualsThan = (_size >= 3);
    if (_greaterEqualsThan) {
      StudentSheet _get_1 = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
      _get_1.setLastName(studentInfos.get(1));
      StudentSheet _get_2 = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
      _get_2.setFirstName(studentInfos.get(2));
    }
  }
  
  /**
   * Associe un nouveau nom de famille à l'étudiant de la copie courante
   * @param lastName Nouveau nom de famille de l'étudiant
   */
  @Override
  public void assignLastName(final String lastName) {
    ServiceImpl.logger.info(((("Renaming student" + Integer.valueOf(this.currentSheetIndex)) + "\'s lastname with :") + lastName));
    StudentSheet _get = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    _get.setLastName(lastName);
  }
  
  /**
   * Associe un nouveau prénom à l'étudiant de la copie courante
   * @param firstName Nouveau prénom de l'étudiant
   */
  @Override
  public void assignFirstName(final String firstName) {
    ServiceImpl.logger.info(((("Renaming student" + Integer.valueOf(this.currentSheetIndex)) + "\'s firstname with :") + firstName));
    StudentSheet _get = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    _get.setFirstName(firstName);
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
   * Retourne une liste triée non modifiable de tous les StudentSheets
   * @param order Elément servant au tri : 0 = studentID; 1 = lastName; 2 = firstName
   * @return Liste triée non modifiable de tous les StudentSheets
   */
  @Override
  public List<StudentSheet> getStudentSheetsOrderBy(final int order) {
    if ((this.graduationTemplate == null)) {
      return List.<StudentSheet>of();
    }
    final List<StudentSheet> sheets = (List<StudentSheet>)Conversions.doWrapArray(((StudentSheet[])Conversions.unwrapArray(this.graduationTemplate.getStudentsheets(), StudentSheet.class)).clone());
    Collections.<StudentSheet>sort(sheets, new Comparator<StudentSheet>() {
      @Override
      public int compare(final StudentSheet o1, final StudentSheet o2) {
        switch (order) {
          case 1:
            return o1.getLastName().compareTo(o2.getLastName());
          case 2:
            return o1.getFirstName().compareTo(o2.getFirstName());
          default:
            return o1.getStudentID().compareTo(o2.getStudentID());
        }
      }
    });
    return Collections.<StudentSheet>unmodifiableList(sheets);
  }
  
  /**
   * ajoute une page en plus dans une copie
   * @param id de la copie
   * @param numéro de la page à ajouter
   */
  @Override
  public void addPageInStudentSheet(final int id, final int page) {
    Collection<StudentSheet> temp = new ArrayList<StudentSheet>();
    Collections.<StudentSheet>addAll(temp, ((StudentSheet[])Conversions.unwrapArray(this.graduationTemplate.getUncompleteStudentSheets(), StudentSheet.class)));
    boolean complete = false;
    boolean found = false;
    int _size = temp.size();
    String _plus = ("temp size = " + Integer.valueOf(_size));
    InputOutput.<String>println(_plus);
    for (final StudentSheet sheet : temp) {
      {
        int _id = sheet.getId();
        String _plus_1 = ("sheet id : " + Integer.valueOf(_id));
        String _plus_2 = (_plus_1 + ", id : ");
        String _plus_3 = (_plus_2 + Integer.valueOf(id));
        InputOutput.<String>println(_plus_3);
        int _id_1 = sheet.getId();
        boolean _equals = (_id_1 == id);
        if (_equals) {
          found = true;
          sheet.getPosPage().add(Integer.valueOf(page));
          int i = 0;
          while ((i < sheet.getPosPage().size())) {
            {
              Integer _get = sheet.getPosPage().get(i);
              boolean _equals_1 = ((_get).intValue() == (-1));
              if (_equals_1) {
                sheet.getPosPage().remove(i);
                int _size_1 = sheet.getPosPage().size();
                int _plus_4 = (_size_1 + 1);
                i = _plus_4;
              }
              i++;
            }
          }
          Collections.<Integer>sort(sheet.getPosPage());
          boolean _contains = sheet.getPosPage().contains(Integer.valueOf((-1)));
          boolean _not = (!_contains);
          if (_not) {
            complete = true;
          }
        }
      }
    }
    if ((!found)) {
      final int[] pages = new int[this.getTemplatePageAmount()];
      int _templatePageAmount = this.getTemplatePageAmount();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _templatePageAmount, true);
      for (final Integer e : _doubleDotLessThan) {
        if (((e).intValue() == 0)) {
          pages[(e).intValue()] = page;
        } else {
          pages[(e).intValue()] = (-1);
        }
      }
      Collections.<Integer>sort(((List<Integer>)Conversions.doWrapArray(pages)));
      final DataFactory dF = new DataFactory();
      this.graduationTemplate.getUncompleteStudentSheets().add(dF.createStudentSheet(id, ((List<Integer>)Conversions.doWrapArray(pages))));
      boolean _contains = ArrayExtensions.contains(pages, (-1));
      boolean _not = (!_contains);
      if (_not) {
        complete = true;
      }
    }
    if (complete) {
      final Function1<StudentSheet, Boolean> _function = (StudentSheet s) -> {
        int _id = s.getId();
        return Boolean.valueOf((_id == id));
      };
      final StudentSheet completeSheet = ((StudentSheet[])Conversions.unwrapArray(IterableExtensions.<StudentSheet>filter(this.graduationTemplate.getUncompleteStudentSheets(), _function), StudentSheet.class))[0];
      this.initSheet(completeSheet);
      int _size_1 = this.graduationTemplate.getStudentsheets().size();
      String _plus_1 = ("studentsheets size " + Integer.valueOf(_size_1));
      InputOutput.<String>println(_plus_1);
      this.graduationTemplate.getStudentsheets().add(completeSheet);
      int _size_2 = this.graduationTemplate.getStudentsheets().size();
      String _plus_2 = ("studentsheets size " + Integer.valueOf(_size_2));
      InputOutput.<String>println(_plus_2);
    }
  }
  
  /**
   * @return l' indentifiant de l'etudiant dont l'ID de la copie est id si la copie existe, Optional.empty sinon
   * @param id l'ID de la copie
   */
  @Override
  public Optional<String> getStudentId(final int id) {
    Collection<StudentSheet> _studentSheets = this.getStudentSheets();
    for (final StudentSheet sheet : _studentSheets) {
      int _id = sheet.getId();
      boolean _tripleEquals = (_id == id);
      if (_tripleEquals) {
        return Optional.<String>ofNullable(sheet.getStudentID());
      }
    }
    return Optional.<String>empty();
  }
  
  /**
   * @return le nom de l'etudiant dont l'ID de la copie est id si la copie existe, Optional.empty sinon
   * @param id l'ID de la copie
   */
  @Override
  public Optional<String> getStudentLastName(final int id) {
    Collection<StudentSheet> _studentSheets = this.getStudentSheets();
    for (final StudentSheet sheet : _studentSheets) {
      int _id = sheet.getId();
      boolean _tripleEquals = (_id == id);
      if (_tripleEquals) {
        return Optional.<String>ofNullable(sheet.getLastName());
      }
    }
    return Optional.<String>empty();
  }
  
  /**
   * @return le prénom de l'etudiant dont l'ID de la copie est id si la copie existe, Optional.empty sinon
   * @param id l'ID de la copie
   */
  @Override
  public Optional<String> getStudentFirstName(final int id) {
    Collection<StudentSheet> _studentSheets = this.getStudentSheets();
    for (final StudentSheet sheet : _studentSheets) {
      int _id = sheet.getId();
      boolean _tripleEquals = (_id == id);
      if (_tripleEquals) {
        return Optional.<String>ofNullable(sheet.getFirstName());
      }
    }
    return Optional.<String>empty();
  }
  
  /**
   * Définit la copie courante à l'ID spécifié si cet ID est bien un ID valide. Ne fait rien sinon
   * @param id un ID de copie d'étudiant
   */
  @Override
  public void selectSheet(final int id) {
    int _size = this.graduationTemplate.getStudentsheets().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      int _id = this.graduationTemplate.getStudentsheets().get((i).intValue()).getId();
      boolean _equals = (_id == id);
      if (_equals) {
        this.currentSheetIndex = (i).intValue();
      }
    }
  }
  
  /**
   * @return le nombre de pages de l'Examen
   */
  @Override
  public int getPageAmount() {
    return this.getTemplatePageAmount();
  }
  
  /**
   * @return les pages qui n'ont pas été détectées avec un QRCode
   */
  @Override
  public Collection<Integer> getFailedPages() {
    return this.graduationTemplate.getFailedPages();
  }
  
  /**
   * @return le nombre de questions d'une copie d'étudiant
   */
  @Override
  public int numberOfQuestions() {
    int _xblockexpression = (int) 0;
    {
      int nbQuestion = 0;
      for (int i = 0; (i < this.editionTemplate.getExam().getPages().size()); i++) {
        int _nbQuestion = nbQuestion;
        int _size = this.editionTemplate.getExam().getPages().get(i).getQuestions().size();
        nbQuestion = (_nbQuestion + _size);
      }
      _xblockexpression = nbQuestion;
    }
    return _xblockexpression;
  }
  
  /**
   * Ajoute une nouvelle entrée à la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle ajouter l'entrée
   * @param desc la description de l'entrée
   * @param point le nombre de point de l'entrée
   * @return l'ID de l'entrée si celle-ci a pu être créée, Optional.empty sinon
   */
  @Override
  public Optional<Integer> addEntry(final int questionId, final String desc, final float point) {
    Optional<Integer> _xblockexpression = null;
    {
      final DataFactory factory = new DataFactory();
      final Question question = this.getQuestion(questionId);
      if ((question == null)) {
        return Optional.<Integer>empty();
      }
      GradeScale _gradeScale = question.getGradeScale();
      boolean _tripleEquals = (_gradeScale == null);
      if (_tripleEquals) {
        question.setGradeScale(factory.createGradeScale());
      }
      final GradeScale scale = question.getGradeScale();
      final GradeEntry entry = factory.createGradeEntry(this.gradeEntryId, desc, point);
      scale.getSteps().add(entry);
      int _plusPlus = this.gradeEntryId++;
      _xblockexpression = Optional.<Integer>of(Integer.valueOf(_plusPlus));
    }
    return _xblockexpression;
  }
  
  /**
   * Modifie une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle modifier l'entrée
   * @param gradeEntryId l'ID de l'entrée à modifier
   * @param desc la nouvelle description de l'entrée
   * @param point le nouveau nombre de point de l'entrée
   */
  @Override
  public void modifyEntry(final int questionId, final int gradeEntryId, final String desc, final float point) {
    final GradeScale scale = this.getQuestion(questionId).getGradeScale();
    final Function1<GradeEntry, Boolean> _function = (GradeEntry step) -> {
      int _id = step.getId();
      return Boolean.valueOf((_id == gradeEntryId));
    };
    final GradeEntry scaleEntry = IterableExtensions.<GradeEntry>findFirst(scale.getSteps(), _function);
    if ((scaleEntry != null)) {
      scaleEntry.setHeader(desc);
      scaleEntry.setStep(point);
    }
  }
  
  /**
   * Supprime une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle supprimer l'entrée
   * @param gradeEntryId l'ID de l'entrée à supprimer
   */
  @Override
  public void removeEntry(final int questionId, final int gradeEntryId) {
    final GradeScale scale = this.getQuestion(questionId).getGradeScale();
    final Function1<GradeEntry, Boolean> _function = (GradeEntry step) -> {
      int _id = step.getId();
      return Boolean.valueOf((_id == gradeEntryId));
    };
    final GradeEntry scaleEntry = IterableExtensions.<GradeEntry>findFirst(scale.getSteps(), _function);
    if ((scaleEntry != null)) {
      scale.getSteps().remove(scaleEntry);
    }
  }
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
   * @return une liste d'ID d'entrées pour la question de l'examen dont l'ID est <i>questionId</i>
   */
  @Override
  public List<Tuple3<Integer, String, Float>> getQuestionGradeEntries(final int questionId) {
    GradeScale _gradeScale = this.getQuestion(questionId).getGradeScale();
    boolean _tripleNotEquals = (_gradeScale != null);
    if (_tripleNotEquals) {
      EList<GradeEntry> _steps = this.getQuestion(questionId).getGradeScale().getSteps();
      String _plus = ("IN SERVICE : " + _steps);
      ServiceImpl.logger.info(_plus);
      final Function1<GradeEntry, Tuple3<Integer, String, Float>> _function = (GradeEntry entry) -> {
        return Tuple3.<Integer, String, Float>of(Integer.valueOf(entry.getId()), entry.getHeader(), Float.valueOf(entry.getStep()));
      };
      return ListExtensions.<GradeEntry, Tuple3<Integer, String, Float>>map(this.getQuestion(questionId).getGradeScale().getSteps(), _function);
    }
    return List.<Tuple3<Integer, String, Float>>of();
  }
  
  /**
   * Ajoute une entrée (GradeItem) à la note d'une question d'une copie si la valeur de l'entrée ne fait pas dépasser la note du barême de la question
   * @param questionId l'ID de la question à laquelle ajouter l'entrée
   * @param l'ID de l'entrée dans l'Examen
   * @return boolean indique si les points on bien été attribué
   */
  @Override
  public boolean assignGradeEntry(final int questionId, final int gradeEntryId) {
    Question _question = this.getQuestion(questionId);
    boolean _tripleEquals = (_question == null);
    if (_tripleEquals) {
      return false;
    }
    final Function1<GradeEntry, Boolean> _function = (GradeEntry entry) -> {
      int _id = entry.getId();
      return Boolean.valueOf((_id == gradeEntryId));
    };
    final GradeEntry gradeEntry = IterableExtensions.<GradeEntry>findFirst(this.getQuestion(questionId).getGradeScale().getSteps(), _function);
    if ((gradeEntry == null)) {
      return false;
    }
    boolean _validGradeEntry = this.validGradeEntry(questionId, gradeEntry, false);
    if (_validGradeEntry) {
      final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
      sheet.getGrades().get(questionId).getEntries().add(gradeEntry);
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Retire une entrée à la note d'une question d'une copie
   * @param questionId l'ID de la question à laquelle retirer l'entrée
   * @param l'ID de l'entrée dans l'Examen
   */
  @Override
  public boolean retractGradeEntry(final int questionId, final int gradeEntryId) {
    final EList<GradeEntry> entries = (((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex]).getGrades().get(questionId).getEntries();
    final Function1<GradeEntry, Boolean> _function = (GradeEntry entry) -> {
      int _id = entry.getId();
      return Boolean.valueOf((_id == gradeEntryId));
    };
    final GradeEntry gradeEntry = IterableExtensions.<GradeEntry>findFirst(entries, _function);
    boolean _validGradeEntry = this.validGradeEntry(questionId, gradeEntry, true);
    if (_validGradeEntry) {
      entries.remove(gradeEntry);
      return true;
    }
    return false;
  }
  
  /**
   * @param l'ID de la question à laquelle récupérer la liste d'entrées de note
   * @return une liste d'ID d'entrées sélectionnées dans le StudentSheet courant pour la question dont l'ID est <i>questionId</i>
   */
  @Override
  public List<Integer> getQuestionSelectedGradeEntries(final int questionId) {
    List<Integer> _xblockexpression = null;
    {
      int _size = this.getStudentSheets().size();
      int _minus = (_size - 1);
      boolean _greaterThan = (this.currentSheetIndex > _minus);
      if (_greaterThan) {
        return new ArrayList<Integer>();
      }
      final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
      int _size_1 = sheet.getGrades().size();
      int _minus_1 = (_size_1 - 1);
      boolean _greaterThan_1 = (questionId > _minus_1);
      if (_greaterThan_1) {
        return new ArrayList<Integer>();
      }
      final Function1<GradeEntry, Integer> _function = (GradeEntry entry) -> {
        return Integer.valueOf(entry.getId());
      };
      _xblockexpression = ListExtensions.<GradeEntry, Integer>map(sheet.getGrades().get(questionId).getEntries(), _function);
    }
    return _xblockexpression;
  }
  
  @Override
  public double getQuestionSelectedGradeEntriesTotalWorth(final int questionId) {
    float _xblockexpression = (float) 0;
    {
      int _size = this.getStudentSheets().size();
      int _minus = (_size - 1);
      boolean _greaterThan = (this.currentSheetIndex > _minus);
      if (_greaterThan) {
        return 0;
      }
      final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
      int _size_1 = sheet.getGrades().size();
      int _minus_1 = (_size_1 - 1);
      boolean _greaterThan_1 = (questionId > _minus_1);
      if (_greaterThan_1) {
        return 0;
      }
      float total = 0f;
      EList<GradeEntry> _entries = sheet.getGrades().get(questionId).getEntries();
      for (final GradeEntry entry : _entries) {
        {
          float _step = entry.getStep();
          String _plus = ("Adding" + Float.valueOf(_step));
          String _plus_1 = (_plus + "to ");
          String _plus_2 = (_plus_1 + Float.valueOf(total));
          ServiceImpl.logger.warn(_plus_2);
          float _step_1 = entry.getStep();
          float _plus_3 = (total + _step_1);
          total = _plus_3;
        }
      }
      ServiceImpl.logger.warn(("total is :" + Float.valueOf(total)));
      _xblockexpression = total;
    }
    return _xblockexpression;
  }
  
  /**
   * Vérification de la validité d'une note lorsque l'on ajoute un grandEntry
   * @param questionId l'ID de la question sur laquelle on souhaite modifier l'entrée
   * @param gradeAdd l'entrée que l'on souhaite modifier (non null)
   * @param removal si la modification consiste en un retrait de l'entrée (false) ou en un ajout (true)
   * @return vrai si le nouvelle note est valide, faux sinon
   * Pour être valide, la nouvelle note doit respecter les conditions suivantes :
   * <ul>
   * <li>Être inférieure ou égale à la note maximale possible pour la question</li>
   * <li>Ne pas être inferieure à 0</li>
   * </ul>
   */
  @Override
  public boolean validGradeEntry(final int questionId, final GradeEntry gradeAdd, final boolean removal) {
    Objects.<GradeEntry>requireNonNull(gradeAdd);
    Question _question = this.getQuestion(questionId);
    boolean _tripleEquals = (_question == null);
    if (_tripleEquals) {
      return false;
    }
    final float gradeMax = this.getQuestion(questionId).getGradeScale().getMaxPoint();
    final Function1<GradeEntry, Float> _function = (GradeEntry e) -> {
      return Float.valueOf(e.getStep());
    };
    final Function2<Float, Float, Float> _function_1 = (Float acc, Float grade) -> {
      return Float.valueOf(((acc).floatValue() + (grade).floatValue()));
    };
    Float currentGrade = IterableExtensions.<Float>reduce(ListExtensions.<GradeEntry, Float>map((((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex]).getGrades().get(questionId).getEntries(), _function), _function_1);
    if ((currentGrade == null)) {
      currentGrade = Float.valueOf(0f);
    }
    float _step = gradeAdd.getStep();
    float newGrade = ((currentGrade).floatValue() + _step);
    if (removal) {
      float _step_1 = gradeAdd.getStep();
      float _minus = ((currentGrade).floatValue() - _step_1);
      newGrade = _minus;
    }
    ServiceImpl.logger.info(Boolean.valueOf(((newGrade <= gradeMax) && (newGrade >= 0))));
    return ((newGrade <= gradeMax) && (newGrade >= 0));
  }
  
  /**
   * @return la note maximal que peut avoir l'étudiant avec les questions auxquelles il a répondu
   */
  @Override
  public float getCurrentMaxGrade() {
    final Function1<Pair<Integer, Grade>, Boolean> _function = (Pair<Integer, Grade> pair) -> {
      boolean _isEmpty = pair.getValue().getEntries().isEmpty();
      return Boolean.valueOf((!_isEmpty));
    };
    final Function1<Pair<Integer, Grade>, Optional<Question>> _function_1 = (Pair<Integer, Grade> pair) -> {
      return this.getQuestionFromIndex((pair.getKey()).intValue());
    };
    final Function1<Optional<Question>, Boolean> _function_2 = (Optional<Question> o) -> {
      boolean _isEmpty = o.isEmpty();
      return Boolean.valueOf((!_isEmpty));
    };
    final Function1<Optional<Question>, Float> _function_3 = (Optional<Question> o) -> {
      return Float.valueOf(o.get().getGradeScale().getMaxPoint());
    };
    final Function2<Float, Float, Float> _function_4 = (Float acc, Float n) -> {
      return Float.valueOf(((acc).floatValue() + (n).floatValue()));
    };
    return (Optional.<Float>ofNullable(
      IterableExtensions.<Float>reduce(IterableExtensions.<Optional<Question>, Float>map(IterableExtensions.<Optional<Question>>filter(IterableExtensions.<Pair<Integer, Grade>, Optional<Question>>map(IterableExtensions.<Pair<Integer, Grade>>filter(IterableExtensions.<Grade>indexed((((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex]).getGrades()), _function), _function_1), _function_2), _function_3), _function_4)).orElse(Float.valueOf(0f))).floatValue();
  }
  
  /**
   * Retourne la note actuelle de l'étudiant courant
   * @return la note actuelle de l'étudiant courant
   */
  @Override
  public float getCurrentGrade() {
    return (((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex]).computeGrade();
  }
  
  /**
   * Retourne le barème total de l'examen
   * @return le barème total de l'examen
   */
  @Override
  public float getGlobalScale() {
    float _xblockexpression = (float) 0;
    {
      float globalScale = 0.0f;
      EList<Page> _pages = this.editionTemplate.getExam().getPages();
      for (final Page page : _pages) {
        EList<Question> _questions = page.getQuestions();
        for (final Question question : _questions) {
          float _globalScale = globalScale;
          float _maxPoint = question.getGradeScale().getMaxPoint();
          globalScale = (_globalScale + _maxPoint);
        }
      }
      _xblockexpression = globalScale;
    }
    return _xblockexpression;
  }
  
  /**
   * Définit la liste des informations des étudiants (non null)
   * @param informations sous forme de liste contenant dans l'ordre, identifiant, nom, prénom et mail de l'étudiant
   */
  @Override
  public void setStudentInfos(final List<List<String>> informations) {
    Objects.<List<List<String>>>requireNonNull(informations);
    final DataFactory factory = new DataFactory();
    final Function1<List<String>, StudentInformation> _function = (List<String> e) -> {
      return factory.createStudentInformation(e.get(0), e.get(1), e.get(2), e.get(3));
    };
    final List<StudentInformation> listInfos = ListExtensions.<List<String>, StudentInformation>map(informations, _function);
    this.graduationTemplate.getInformations().clear();
    this.graduationTemplate.getInformations().addAll(listInfos);
  }
  
  /**
   * @return l'ensemble de tous les identifiants des étudiants chargés
   */
  @Override
  public Collection<String> getStudentIds() {
    List<String> _xblockexpression = null;
    {
      final Function1<StudentInformation, String> _function = (StudentInformation infos) -> {
        return infos.getUserId();
      };
      final List<String> list = IterableExtensions.<String>toList(ListExtensions.<StudentInformation, String>map(this.graduationTemplate.getInformations(), _function));
      _xblockexpression = list;
    }
    return _xblockexpression;
  }
  
  /**
   * @return l'ensemble de tous les noms de famille des étudiants chargés
   */
  @Override
  public Collection<String> getStudentLastNames() {
    List<String> _xblockexpression = null;
    {
      final Function1<StudentInformation, String> _function = (StudentInformation infos) -> {
        return infos.getLastName();
      };
      final List<String> list = IterableExtensions.<String>toList(ListExtensions.<StudentInformation, String>map(this.graduationTemplate.getInformations(), _function));
      _xblockexpression = list;
    }
    return _xblockexpression;
  }
  
  /**
   * @return l'ensemble de tous les prénoms des étudiants chargés
   */
  @Override
  public Collection<String> getStudentFirstNames() {
    List<String> _xblockexpression = null;
    {
      final Function1<StudentInformation, String> _function = (StudentInformation infos) -> {
        return infos.getFirstName();
      };
      final List<String> list = IterableExtensions.<String>toList(ListExtensions.<StudentInformation, String>map(this.graduationTemplate.getInformations(), _function));
      _xblockexpression = list;
    }
    return _xblockexpression;
  }
  
  /**
   * @return une liste contenant dans l'ordre, identifiant, nom, prénom et mail de l'étudiant
   */
  @Override
  public List<List<String>> getStudentInfos() {
    ArrayList<List<String>> _xblockexpression = null;
    {
      final ArrayList<List<String>> list = new ArrayList<List<String>>();
      final Consumer<StudentInformation> _function = (StudentInformation infos) -> {
        list.add(Arrays.<String>asList(infos.getUserId(), infos.getLastName(), infos.getFirstName(), infos.getEmailAddress()));
      };
      this.graduationTemplate.getInformations().forEach(_function);
      _xblockexpression = list;
    }
    return _xblockexpression;
  }
  
  /**
   * A partir de l'identifiant d'un étudiant, retourne, dans l'ordre, identifiant (le même que donné en paramètre), nom, prénom et mail de l'étudiant
   * @return Identifiant, nom, prénom et mail de l'étudiant
   */
  public List<String> getStudentInfos(final String studentId) {
    List<String> _xblockexpression = null;
    {
      int i = 0;
      final List<StudentInformation> infos = this.graduationTemplate.getInformations();
      List<String> info = new ArrayList<String>();
      while (((i < infos.size()) && info.isEmpty())) {
        {
          String _userId = infos.get(i).getUserId();
          boolean _equals = com.google.common.base.Objects.equal(_userId, studentId);
          if (_equals) {
            info = Arrays.<String>asList(infos.get(i).getUserId(), infos.get(i).getLastName(), infos.get(i).getFirstName(), 
              infos.get(i).getEmailAddress());
          }
          i++;
        }
      }
      _xblockexpression = info;
    }
    return _xblockexpression;
  }
  
  /**
   * Crée une nouvelle question et la zone associée
   * @param l'index de la page sur laquelle mettre la question
   * @param x la coordonnée X de la zone de la question
   * @param y la coordonnée Y de la zone de la question
   * @param height la hauteur de la zone de la question
   * @param width la longueur de la zone de la question
   */
  @Override
  public void createQrCode(final float x, final float y, final float height, final float width) {
    Exam _exam = this.editionTemplate.getExam();
    _exam.setQrCodeZone(CoreFactory.eINSTANCE.createQrCodeZone());
    final QrCodeZone qrCodeZone = this.editionTemplate.getExam().getQrCodeZone();
    qrCodeZone.setX(x);
    qrCodeZone.setY(y);
    qrCodeZone.setWidth(width);
    qrCodeZone.setHeight(height);
  }
  
  /**
   * Redimensionne la zone du qr code
   * @param heigth la nouvelle hauteur de la zone
   * @param width la nouvelle largeur de la zone
   */
  @Override
  public void rescaleQrCode(final float height, final float width) {
    final QrCodeZone qrCodeZone = this.editionTemplate.getExam().getQrCodeZone();
    qrCodeZone.setWidth(width);
    qrCodeZone.setHeight(height);
  }
  
  /**
   * Déplace la zone du qr code
   * @param x la nouvelle position x de la zone
   * @param y la nouvelle position y de la zone
   */
  @Override
  public void moveQrCode(final float x, final float y) {
    final QrCodeZone qrCodeZone = this.editionTemplate.getExam().getQrCodeZone();
    qrCodeZone.setX(x);
    qrCodeZone.setY(y);
  }
  
  private File editionFile;
  
  private int questionId;
  
  /**
   * Crée une nouvelle question et la zone associée
   * @param l'index de la page sur laquelle mettre la question
   * @param x la coordonnée X de la zone de la question
   * @param y la coordonnée Y de la zone de la question
   * @param heigth la hauteur de la zone de la question
   * @param width la longueur de la zone de la question
   * @return l'ID de la nouvelle question
   * @throw IllegalArgumentException si l'index de la page pointe vers une page qui n'existe pas
   * @author degas
   */
  @Override
  public int createQuestion(final int pdfPageIndex, final float x, final float y, final float heigth, final float width) {
    try {
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
      this.getPage(pdfPageIndex).getQuestions().add(question);
      return this.questionId++;
    } catch (final Throwable _t) {
      if (_t instanceof IndexOutOfBoundsException) {
        String _plus = (Integer.valueOf(pdfPageIndex) + " is not a valid Page Index");
        throw new IllegalArgumentException(_plus);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Redimensionne la zone d'une Question
   * @param id l'ID de la question dont la zone doit être redimensionnée
   * @param heigth la nouvelle hauteur de la zone
   * @param width la nouvelle largeur de la zone
   */
  @Override
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
  @Override
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
  @Override
  public void renameQuestion(final int id, final String name) {
    final Question question = this.getQuestion(id);
    question.setName(name);
  }
  
  /**
   * Supprime une question
   * @param id l'ID de la question à supprimer
   */
  @Override
  public boolean removeQuestion(final int id) {
    Question toRemove = null;
    EList<Page> _pages = this.editionTemplate.getExam().getPages();
    for (final Page page : _pages) {
      {
        EList<Question> _questions = page.getQuestions();
        for (final Question question : _questions) {
          int _id = question.getId();
          boolean _equals = (_id == id);
          if (_equals) {
            toRemove = question;
          }
        }
        if ((toRemove != null)) {
          page.getQuestions().remove(toRemove);
        }
      }
    }
    return (toRemove != null);
  }
  
  /**
   * Modifie la note maximal que l'on peut attribuer a une question.
   * @param questionId l'ID de la question a laquelle on veut modifier la note maximal possible
   * @param maxPoint note maximal de la question question a ajouter
   */
  @Override
  public void modifyMaxPoint(final int questionId, final float maxPoint) {
    final GradeScale scale = this.getQuestion(questionId).getGradeScale();
    if ((maxPoint >= 0)) {
      scale.setMaxPoint(maxPoint);
    }
  }
  
  /**
   * Sauvegarde sous le fichier modèle d'examen sur le disque
   * @param path L'emplacement de sauvegarde du fichier
   * @param pdfOutputStream le PDF sous forme de Stream
   */
  @Override
  public void saveEdition(final ByteArrayOutputStream outputStream, final File path) {
    try {
      final byte[] encoded = Base64.getEncoder().encode(outputStream.toByteArray());
      String _string = new String(encoded);
      this.editionTemplate.setEncodedDocument(_string);
      outputStream.close();
      TemplateIo.save(path, this.editionTemplate);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Sauvegarde le fichier modèle d'examen sur le disque
   */
  public void saveEdition() {
    if ((this.editionFile != null)) {
      TemplateIo.save(this.editionFile, this.editionTemplate);
    }
  }
  
  /**
   * Charge un fichier modèle d'examen a partir du disque
   * @params xmiPath L'emplacement du fichier.
   * @returns un flux vers le contenu du fichier si celui-ci a bien été ouvert, Optional.empty sinon
   */
  @Override
  public Optional<ByteArrayInputStream> open(final String xmiPath) {
    final Optional<CreationTemplate> creationTemplate = TemplateIo.loadCreationTemplate(xmiPath);
    boolean _isPresent = creationTemplate.isPresent();
    if (_isPresent) {
      this.editionTemplate = creationTemplate.get();
      final Exam exam = creationTemplate.get().getExam();
      final byte[] decoded = Base64.getDecoder().decode(creationTemplate.get().getEncodedDocument());
      final Function<Page, Integer> _function = (Page page) -> {
        return Integer.valueOf(page.getQuestions().size());
      };
      final BinaryOperator<Integer> _function_1 = (Integer acc, Integer num) -> {
        return Integer.valueOf(((acc).intValue() + (num).intValue()));
      };
      Integer _get = exam.getPages().stream().<Integer>map(_function).reduce(_function_1).get();
      int _plus = ((_get).intValue() + 1);
      this.questionId = _plus;
      File _file = new File(xmiPath);
      this.editionFile = _file;
      ByteArrayInputStream _byteArrayInputStream = new ByteArrayInputStream(decoded);
      return Optional.<ByteArrayInputStream>of(_byteArrayInputStream);
    }
    return Optional.<ByteArrayInputStream>empty();
  }
  
  /**
   * Rend la liste non modifiable des Questions Définies dans un Examen
   * @return List<Question>
   * @author degas
   */
  protected Collection<Question> getQuestions(final int pageId) {
    return Collections.<Question>unmodifiableCollection(this.editionTemplate.getExam().getPages().get(pageId).getQuestions());
  }
  
  /**
   * @param absoluteQuestionId la position absolue d'une question dans l'Examen
   * @return la Question associée à cette position si elle existe, Optional.empty sinon
   */
  protected Optional<Question> getQuestionFromIndex(final int absoluteQuestionId) {
    final Function1<Page, EList<Question>> _function = (Page p) -> {
      return p.getQuestions();
    };
    final Function1<Pair<Integer, Question>, Boolean> _function_1 = (Pair<Integer, Question> p) -> {
      Integer _key = p.getKey();
      return Boolean.valueOf(((_key).intValue() == absoluteQuestionId));
    };
    final Function<Pair<Integer, Question>, Question> _function_2 = (Pair<Integer, Question> q) -> {
      return q.getValue();
    };
    return Optional.<Pair<Integer, Question>>ofNullable(
      IterableExtensions.<Pair<Integer, Question>>findFirst(IterableExtensions.<Question>indexed(IterableExtensions.<Page, Question>flatMap(this.editionTemplate.getExam().getPages(), _function)), _function_1)).<Question>map(_function_2);
  }
  
  /**
   * @return le nombre de pages de l'Examen
   */
  @Override
  public int getTemplatePageAmount() {
    return this.editionTemplate.getExam().getPages().size();
  }
  
  /**
   * @param pageId l'ID de la page à récupérer
   * @return la Page dont l'ID est <i>pageId</i>
   */
  protected Page getPage(final int pageId) {
    return this.editionTemplate.getExam().getPages().get(pageId);
  }
  
  /**
   * @return vrai si un modèle d'examen est chargé, false sinon
   */
  @Override
  public boolean hasExamLoaded() {
    return ((this.editionTemplate != null) && (this.editionTemplate.getExam() != null));
  }
  
  /**
   * Met à jour le nom de l'examen
   * @param name Nouveau nom de l'examen
   */
  @Override
  public void setExamName(final String name) {
    Exam _exam = this.editionTemplate.getExam();
    _exam.setName(name);
  }
  
  @Override
  public Optional<QrCodeZone> getQrCodeZone() {
    return Optional.<QrCodeZone>ofNullable(this.editionTemplate.getExam().getQrCodeZone());
  }
  
  @Override
  public Optional<javafx.util.Pair<Float, Float>> getQrCodePosition() {
    boolean _isPresent = this.getQrCodeZone().isPresent();
    if (_isPresent) {
      float _x = this.getQrCodeZone().get().getX();
      float _y = this.getQrCodeZone().get().getY();
      javafx.util.Pair<Float, Float> _pair = new javafx.util.Pair<Float, Float>(Float.valueOf(_x), Float.valueOf(_y));
      return Optional.<javafx.util.Pair<Float, Float>>of(_pair);
    } else {
      return Optional.<javafx.util.Pair<Float, Float>>empty();
    }
  }
  
  /**
   * @param pageIndex l'ID d'une page
   * @return la liste des Questions sur la page dont l'ID est <i>pageIndex</i>
   */
  @Override
  public List<Question> getQuestionAtPage(final int pageIndex) {
    return this.getPage(pageIndex).getQuestions();
  }
  
  /**
   * @param l'ID de la Question
   * @return la Question du modèle correspondant à l'ID spécifié
   */
  @Override
  public Question getQuestion(final int id) {
    EList<Page> _pages = this.editionTemplate.getExam().getPages();
    for (final Page page : _pages) {
      {
        final Function1<Question, Boolean> _function = (Question question) -> {
          int _id = question.getId();
          return Boolean.valueOf((_id == id));
        };
        final Question question = IterableExtensions.<Question>findFirst(page.getQuestions(), _function);
        if ((question != null)) {
          return question;
        }
      }
    }
    return null;
  }
  
  /**
   * @return Identifiant de l'examen
   * @author degas
   */
  @Override
  public int getExamId() {
    return this.editionTemplate.getExam().getId();
  }
  
  /**
   * @return Nom de l'examen
   * @author degas
   */
  @Override
  public String getExamName() {
    return this.editionTemplate.getExam().getName();
  }
  
  /**
   * Crée et initialise un nouveau modèle d'Examen
   * @param pageNumber le nombre de pages du modèle
   */
  @Override
  public void initializeEdition(final int pageNumber) {
    this.editionTemplate = TemplatesFactory.eINSTANCE.createCreationTemplate();
    this.editionTemplate.setExam(CoreFactory.eINSTANCE.createExam());
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, pageNumber, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Page page = CoreFactory.eINSTANCE.createPage();
        final Consumer<Question> _function = (Question q) -> {
          q.setGradeScale(CoreFactory.eINSTANCE.createGradeScale());
        };
        page.getQuestions().forEach(_function);
        this.editionTemplate.getExam().getPages().add(page);
      }
    }
    this.questionId = 0;
  }
  
  @Override
  public int addNewAnnotation(final double x, final double y, final double width, final double height, final double pointerX, final double pointerY, final String text, final int questionId, final int pageId) {
    int _xblockexpression = (int) 0;
    {
      final DataFactory factory = new DataFactory();
      final Comment annot = factory.createTextComment(this.annotationId, text, ((float) x), ((float) y), ((float) width), 
        ((float) height), ((float) pointerX), ((float) pointerY), pageId);
      final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
      sheet.getGrades().get(questionId).getComments().add(annot);
      _xblockexpression = this.annotationId++;
    }
    return _xblockexpression;
  }
  
  public Comment getAnnotationWithId(final List<Comment> comments, final int annotId) {
    Object _xblockexpression = null;
    {
      for (final Comment c : comments) {
        int _id = c.getId();
        boolean _equals = (_id == annotId);
        if (_equals) {
          return c;
        }
      }
      _xblockexpression = null;
    }
    return ((Comment)_xblockexpression);
  }
  
  @Override
  public List<Integer> getAnnotationIds(final int questionId, final int studentId) {
    LinkedList<Integer> _xblockexpression = null;
    {
      LinkedList<Integer> result = new LinkedList<Integer>();
      final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
      final EList<Comment> comments = sheet.getGrades().get(questionId).getComments();
      for (final Comment c : comments) {
        result.add(Integer.valueOf(c.getId()));
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  @Override
  public String getAnnotationText(final int annotationId, final int questionId, final int studentId) {
    final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    final EList<Comment> comments = sheet.getGrades().get(questionId).getComments();
    final Comment annot = this.getAnnotationWithId(comments, annotationId);
    if ((annot != null)) {
      return ((TextComment) annot).getText();
    }
    return "Annotation not found";
  }
  
  @Override
  public double getAnnotationX(final int annotationId, final int questionId, final int studentId) {
    final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    final EList<Comment> comments = sheet.getGrades().get(questionId).getComments();
    final Comment annot = this.getAnnotationWithId(comments, annotationId);
    if ((annot != null)) {
      return annot.getX();
    }
    return 0;
  }
  
  @Override
  public double getAnnotationY(final int annotationId, final int questionId, final int studentId) {
    final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    final EList<Comment> comments = sheet.getGrades().get(questionId).getComments();
    final Comment annot = this.getAnnotationWithId(comments, annotationId);
    if ((annot != null)) {
      return annot.getY();
    }
    return 0;
  }
  
  @Override
  public double getAnnotationHeight(final int annotationId, final int questionId, final int studentId) {
    final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    final EList<Comment> comments = sheet.getGrades().get(questionId).getComments();
    final Comment annot = this.getAnnotationWithId(comments, annotationId);
    if ((annot != null)) {
      return annot.getX();
    }
    return 0;
  }
  
  @Override
  public double getAnnotationWidth(final int annotationId, final int questionId, final int studentId) {
    final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    final EList<Comment> comments = sheet.getGrades().get(questionId).getComments();
    final Comment annot = this.getAnnotationWithId(comments, annotationId);
    if ((annot != null)) {
      return annot.getX();
    }
    return 0;
  }
  
  @Override
  public double getAnnotationPointerX(final int annotationId, final int questionId, final int studentId) {
    final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    final EList<Comment> comments = sheet.getGrades().get(questionId).getComments();
    final Comment annot = this.getAnnotationWithId(comments, annotationId);
    if ((annot != null)) {
      return annot.getPointerX();
    }
    return 0;
  }
  
  @Override
  public double getAnnotationPointerY(final int annotationId, final int questionId, final int studentId) {
    final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    final EList<Comment> comments = sheet.getGrades().get(questionId).getComments();
    final Comment annot = this.getAnnotationWithId(comments, annotationId);
    if ((annot != null)) {
      return annot.getPointerY();
    }
    return 0;
  }
  
  @Override
  public void removeAnnotation(final int annotationId, final int questionId, final int studentId) {
    final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    final EList<Comment> comments = sheet.getGrades().get(questionId).getComments();
    final Comment annot = this.getAnnotationWithId(comments, annotationId);
    if ((annot != null)) {
      comments.remove(annot);
    }
  }
  
  @Override
  public void updateAnnotation(final double x, final double y, final double width, final double height, final double pointerX, final double pointerY, final String text, final int annotationId, final int questionId, final int studentId) {
    final StudentSheet sheet = ((StudentSheet[])Conversions.unwrapArray(this.getStudentSheets(), StudentSheet.class))[this.currentSheetIndex];
    final EList<Comment> comments = sheet.getGrades().get(questionId).getComments();
    final Comment annot = this.getAnnotationWithId(comments, annotationId);
    if ((annot != null)) {
      annot.setX(((float) x));
      annot.setY(((float) y));
      annot.setPointerX(((float) pointerX));
      annot.setPointerY(((float) pointerY));
      ((TextComment) annot).setText(text);
    }
  }
}
