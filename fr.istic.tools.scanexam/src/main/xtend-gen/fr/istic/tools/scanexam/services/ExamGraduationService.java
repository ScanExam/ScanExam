package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.CoreFactory;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.services.ExamSingleton;
import fr.istic.tools.scanexam.services.Service;
import java.io.File;
import java.util.Set;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ExamGraduationService extends Service {
  private int currentSheetIndex;
  
  private int currentQuestionIndex;
  
  private Set<StudentSheet> studentSheets;
  
  private Set<StudentSheet> visibleSheets;
  
  @Override
  public void save(final String path) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void open(final String xmiFile) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public int nextPage() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public int previousPage() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void create(final File file) {
    try {
      this.document = PDDocument.load(file);
      ExamSingleton.instance = CoreFactory.eINSTANCE.createExam();
      int _size = IterableExtensions.size(this.document.getPages());
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        ExamSingleton.instance.getPages().add(CoreFactory.eINSTANCE.createPage());
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
