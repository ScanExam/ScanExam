package fr.istic.tools.scanexam.sessions;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.core.templates.TemplatesPackage;
import fr.istic.tools.scanexam.sessions.Session;
import java.io.File;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Representer l'état courant de l'interface graphique
 * doit elle être stoquée dans une classe faisant parti de l'API ?
 * 
 * 
 * Factory -> on créer une session en passant la précedente en paramètre.
 */
@SuppressWarnings("all")
public class CreationSession extends Session {
  private PDDocument document;
  
  private CreationTemplate template;
  
  /**
   * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
   * @param q Une Question
   * @param r Un Rectangle
   * @author degas
   */
  public void addQuestion(final Question q) {
    this.getPage().getQuestions().add(q);
  }
  
  /**
   * Supprime une question
   * @param index Index de la question à supprimer
   * 
   * @author degas
   */
  public void removeQuestion(final int index) {
    this.getPage().getQuestions().remove(index);
  }
  
  @Override
  public void save(final String path) {
    try {
      this.template.setExam(super.exam);
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
  public void open(final File xmiFile) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public void create(final String pdfPath) {
    throw new Error("Unresolved compilation problems:"
      + "\nExamImpl cannot be resolved.");
  }
}
