package fr.istic.tools.scanexam.view.fx.component.validator;

import fr.istic.tools.scanexam.view.fx.component.validator.FormatValidator;
import java.io.File;
import java.util.Optional;

/**
 * Validateur pour vérifier que le chemin d'accès pointe bien vers un fichier existant
 */
@SuppressWarnings("all")
public class ValidFilePathValidator implements FormatValidator {
  private final String suffix;
  
  /**
   * @param endWith le string par lequel le Path doit finir
   */
  public ValidFilePathValidator(final String suffix) {
    this.suffix = suffix;
  }
  
  /**
   * Construit un nouveau Validateur de chemin d'accès
   */
  public ValidFilePathValidator() {
    this("");
  }
  
  @Override
  public Optional<String> validate(final String toValidate) {
    final File file = new File(toValidate);
    boolean _exists = file.exists();
    boolean _not = (!_exists);
    if (_not) {
      return Optional.<String>of("studentlist.info.fileNotExist");
    } else {
      if (((!file.isFile()) || (!toValidate.endsWith(this.suffix)))) {
        return Optional.<String>of("studentlist.info.fileNotValid");
      } else {
        return Optional.<String>empty();
      }
    }
  }
}
