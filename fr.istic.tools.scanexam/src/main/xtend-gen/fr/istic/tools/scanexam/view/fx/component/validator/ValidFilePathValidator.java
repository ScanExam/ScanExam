package fr.istic.tools.scanexam.view.fx.component.validator;

import fr.istic.tools.scanexam.view.fx.component.validator.FormatValidator;
import java.io.File;
import java.util.Optional;

/**
 * Validateur pour vérifier que le chemin d'accès pointe bien vers un fichier existant
 */
@SuppressWarnings("all")
public class ValidFilePathValidator implements FormatValidator {
  @Override
  public Optional<String> validate(final String toValidate) {
    final File file = new File(toValidate);
    boolean _exists = file.exists();
    boolean _not = (!_exists);
    if (_not) {
      return Optional.<String>of("studentlist.info.fileNotExist");
    } else {
      boolean _isFile = file.isFile();
      boolean _not_1 = (!_isFile);
      if (_not_1) {
        return Optional.<String>of("studentlist.info.fileNotValid");
      } else {
        return Optional.<String>empty();
      }
    }
  }
}
