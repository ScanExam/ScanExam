package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesPackage;
import fr.istic.tools.scanexam.services.ExamSingleton;
import fr.istic.tools.scanexam.services.Service;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import org.apache.pdfbox.pdmodel.PDDocument;
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
  
  /**
   * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
   * @param q Une Question
   * @param r Un Rectangle
   * @author degas
   */
  public void addQuestion(final Question q) {
    this.getCurrentPage().getQuestions().add(q);
  }
  
  /**
   * Supprime une question
   * @param index Index de la question à supprimer
   * 
   * @author degas
   */
  public void removeQuestion(final int index) {
    this.getCurrentPage().getQuestions().remove(index);
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
        this.setDocument(PDDocument.load(pdfFile));
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
      this.setDocument(PDDocument.load(file));
      ExamSingleton.instance = CoreFactory.eINSTANCE.createExam();
      int _size = IterableExtensions.size(this.getDocument().getPages());
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        ExamSingleton.instance.getPages().add(CoreFactory.eINSTANCE.createPage());
      }
      this.currentPdfPath = file.getAbsolutePath();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
