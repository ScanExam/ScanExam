package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.presenter.PresenterGradeScale;
import fr.istic.tools.scanexam.presenter.PresenterPdf;
import fr.istic.tools.scanexam.presenter.PresenterQRCode;
import fr.istic.tools.scanexam.presenter.PresenterQuestionZone;
import fr.istic.tools.scanexam.services.ServiceEdition;
import fr.istic.tools.scanexam.view.Adapter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;
import java.util.Optional;

/**
 * Class defining the presenter for the exam creation view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterEdition implements Presenter {
  /**
   * Bidirectional associations with the concrete presenters
   * and main controller of the view
   */
  private PresenterQRCode presQRCode;
  
  private PresenterQuestionZone presQuestionZone;
  
  private PresenterGradeScale presMarkingScheme;
  
  private ServiceEdition service;
  
  private PresenterPdf presPdf;
  
  private Adapter<PresenterEdition> adapter;
  
  private PresenterEdition(final ServiceEdition service) {
    Objects.<ServiceEdition>requireNonNull(service);
    this.service = service;
    PresenterPdf _presenterPdf = new PresenterPdf(service, this);
    this.presPdf = _presenterPdf;
    PresenterQuestionZone _presenterQuestionZone = new PresenterQuestionZone(service, this);
    this.presQuestionZone = _presenterQuestionZone;
    PresenterGradeScale _presenterGradeScale = new PresenterGradeScale(service, this);
    this.presMarkingScheme = _presenterGradeScale;
  }
  
  public PresenterEdition(final Adapter<PresenterEdition> adapter, final ServiceEdition service) {
    this(service);
    Objects.<Adapter<PresenterEdition>>requireNonNull(adapter);
    this.adapter = adapter;
  }
  
  /**
   * @return current {@link PresenterQRCode}
   */
  public PresenterQRCode getPresenterQRCode() {
    return this.presQRCode;
  }
  
  /**
   * @return current {@link PresenterQuestionZone}
   */
  public PresenterQuestionZone getPresenterQuestionZone() {
    return this.presQuestionZone;
  }
  
  /**
   * @return current {@link PresenterMarkingScheme}
   */
  public PresenterGradeScale getPresenterMarkingScheme() {
    return this.presMarkingScheme;
  }
  
  public PresenterPdf getPresenterPdf() {
    return this.presPdf;
  }
  
  public void save(final File path) {
    final ByteArrayOutputStream outputStream = this.presPdf.getPdfOutputStream();
    this.service.save(outputStream, path);
  }
  
  public boolean load(final String path) {
    final Optional<ByteArrayInputStream> stream = this.service.open(path);
    boolean _isPresent = stream.isPresent();
    if (_isPresent) {
      this.presPdf.create(stream.get());
    }
    return stream.isPresent();
  }
  
  public void close() {
    System.exit(0);
  }
  
  public int getQuestionId() {
    return this.service.getQuestionId();
  }
}
