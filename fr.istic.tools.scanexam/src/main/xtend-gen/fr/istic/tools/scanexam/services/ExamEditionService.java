package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.QuestionZone;
import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesPackage;
import fr.istic.tools.scanexam.services.ExamSingleton;
import fr.istic.tools.scanexam.services.Service;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Representer l'état courant de l'interface graphique
 * doit elle être stoquée dans une classe faisant parti de l'API ?
 * 
 * 
 * Factory -> on créer une session en passant la précedente en paramètre.
 */
@SuppressWarnings("all")
public class ExamEditionService extends Service {
  private CreationTemplate template;
  
  private String currentPdfPath;
  
  private int questionId;
  
  /**
   * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
   * @param q Une Question
   * @param r Un Rectangle
   * @author degas
   */
  public int createQuestion(final float x, final float y, final float heigth, final float width) {
    final Question question = CoreFactory.eINSTANCE.createQuestion();
    question.setId(this.questionId);
    question.setZone(CoreFactory.eINSTANCE.createQuestionZone());
    this.questionId++;
    this.getCurrentPage().getQuestions().add(question.getId(), question);
    return this.questionId;
  }
  
  public void updateQuestion(final int id, final float x, final float y, final float heigth, final float width) {
    final Question question = this.getCurrentPage().getQuestions().get(id);
    QuestionZone _zone = question.getZone();
    _zone.setX(x);
    QuestionZone _zone_1 = question.getZone();
    _zone_1.setY(y);
    QuestionZone _zone_2 = question.getZone();
    _zone_2.setWidth(width);
    QuestionZone _zone_3 = question.getZone();
    _zone_3.setHeigth(heigth);
  }
  
  public Question removeQuestion(final int id) {
    return this.getCurrentPage().getQuestions().remove(id);
  }
  
  @Override
  public void save(final String path) {
    try {
      this.template.setPdfPath(this.currentPdfPath);
      this.template.setExam(ExamSingleton.instance);
      final ResourceSetImpl resourceSet = new ResourceSetImpl();
      final Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
      final XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
      _extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
      resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);
      final Resource resource = resourceSet.createResource(URI.createFileURI(path));
      resource.getContents().add(this.template);
      resource.save(null);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public void open(final String xmiPath) {
    try {
      final Optional<CreationTemplate> creationTemplate = ExamEditionService.load(xmiPath);
      boolean _isPresent = creationTemplate.isPresent();
      if (_isPresent) {
        this.template = creationTemplate.get();
        ExamSingleton.instance = creationTemplate.get().getExam();
        String _pdfPath = creationTemplate.get().getPdfPath();
        final File pdfFile = new File(_pdfPath);
        final PDDocument document = PDDocument.load(pdfFile);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static Optional<CreationTemplate> load(final String path) {
    final ResourceSetImpl resourceSet = new ResourceSetImpl();
    final Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    final XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    _extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);
    Resource resource = null;
    try {
      resource = resourceSet.getResource(URI.createFileURI(path), true);
    } catch (final Throwable _t) {
      if (_t instanceof Throwable) {
        return Optional.<CreationTemplate>empty();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    EObject _get = resource.getContents().get(0);
    return Optional.<CreationTemplate>ofNullable(((CreationTemplate) _get));
  }
  
  public void create(final File file) {
    try {
      ArrayList<BufferedImage> _arrayList = new ArrayList<BufferedImage>();
      this.pages = _arrayList;
      final PDDocument document = PDDocument.load(file);
      ExamSingleton.instance = CoreFactory.eINSTANCE.createExam();
      final PDFRenderer renderer = new PDFRenderer(document);
      int _size = IterableExtensions.size(document.getPages());
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        {
          final BufferedImage bufferedImage = renderer.renderImageWithDPI((i).intValue(), 300, ImageType.RGB);
          this.pages.add(bufferedImage);
          ExamSingleton.instance.getPages().add(CoreFactory.eINSTANCE.createPage());
        }
      }
      this.currentPdfPath = file.getAbsolutePath();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public void nextPage() {
    int _size = this.pages.size();
    boolean _lessThan = ((this.pageIndex + 1) < _size);
    if (_lessThan) {
      this.pageIndex++;
    }
  }
  
  @Override
  public void previousPage() {
    if ((this.pageIndex > 0)) {
      this.pageIndex--;
    }
  }
}
