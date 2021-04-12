package fr.istic.tools.scanexam.view.fx.component.validator;

import fr.istic.tools.scanexam.view.fx.component.validator.FormatValidator;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Validateur pour adresse mail
 * @author Théo Giraudet
 */
@SuppressWarnings("all")
public class EmailValidator implements FormatValidator {
  private final Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
  
  public Optional<String> validate(final String toValidate) {
    boolean _matches = this.pattern.matcher(toValidate).matches();
    if (_matches) {
      return Optional.<String>empty();
    } else {
      return Optional.<String>of("config.info.email");
    }
  }
}
