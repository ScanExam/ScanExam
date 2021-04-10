package fr.istic.tools.scanexam.view.fx.component;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.fx.component.validator.FormatValidator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Représente un TextField à formattage.
 * Ce formattage peut être stricte (vérifié à chaque changement sur le composant) ou seulement testé lorsque le composant perd le focus.
 * Si le formattage est mauvais, le composant rentre en état de "Mauvais Format".
 * @author Théo Giraudet
 */
@SuppressWarnings("all")
public class FormattedTextField extends TextField {
  private BooleanProperty wrongFormatted = new SimpleBooleanProperty(this, "wrongFormatted", false);
  
  private BooleanProperty acceptEmpty = new SimpleBooleanProperty(this, "acceptEmpty", true);
  
  private StringProperty strictFormat = new SimpleStringProperty(this, "strictFormat", ".*");
  
  private StringProperty defaultValue = new SimpleStringProperty(this, "defaultValue", "");
  
  private final PseudoClass errorClass;
  
  private final Map<FormatValidator, Boolean> validators;
  
  private Pattern pattern;
  
  /**
   * Crée un nouveau FormattedTextField
   */
  public FormattedTextField() {
    super();
    this.pattern = Pattern.compile(".*");
    this.setDefaultValue("");
    this.setText(this.defaultValue.getValue());
    this.errorClass = PseudoClass.getPseudoClass("wrong-format");
    final ChangeListener<Boolean> _function = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      this.updateView();
    };
    this.wrongFormatted.addListener(_function);
    HashMap<FormatValidator, Boolean> _hashMap = new HashMap<FormatValidator, Boolean>();
    this.validators = _hashMap;
  }
  
  /**
   * @param value true si le composant accepte le mot vide, false sinon
   */
  public boolean getAcceptEmpty() {
    return (this.acceptEmpty.getValue()).booleanValue();
  }
  
  /**
   * @param value true si le composant doit accepter le mot vide, false sinon
   */
  public void setAcceptEmpty(final boolean value) {
    this.acceptEmpty.setValue(Boolean.valueOf(value));
  }
  
  /**
   * @return la propriété "accepte le mot vide" en lecture seule
   */
  public ReadOnlyBooleanProperty acceptEmptyProperty() {
    return this.acceptEmpty;
  }
  
  /**
   * @return true si le composant est mal formaté, false sinon
   */
  public boolean getWrongFormatted() {
    return (this.wrongFormatted.getValue()).booleanValue();
  }
  
  /**
   * @return true si le composant doit être mal formaté, false sinon
   */
  public void setWrongFormatted(final boolean value) {
    this.wrongFormatted.setValue(Boolean.valueOf(value));
  }
  
  /**
   * @return la propriété "est mal formaté" en lecture seule
   */
  public ReadOnlyBooleanProperty wrongFormattedProperty() {
    return this.wrongFormatted;
  }
  
  /**
   * @param formatter un regex (non null) que le contenu du composant doit respecté. Ce format est vérifié à chaque changement du contenu du composant
   * @throws IllegalArgumentException si le format stricte n'est pas respecté par la valeur {@link #setDefaultValue setDefaultValue}
   */
  public void setStrictFormat(final String formatter) {
    Objects.<String>requireNonNull(formatter);
    this.pattern = Pattern.compile(formatter);
    final UnaryOperator<TextFormatter.Change> _function = (TextFormatter.Change change) -> {
      TextFormatter.Change _xifexpression = null;
      boolean _matches = this.pattern.matcher(change.getText()).matches();
      if (_matches) {
        _xifexpression = change;
      } else {
        _xifexpression = null;
      }
      return _xifexpression;
    };
    TextFormatter<String> _textFormatter = new TextFormatter<String>(_function);
    this.setTextFormatter(_textFormatter);
    boolean _matches = this.pattern.matcher(this.defaultValue.getValue()).matches();
    boolean _not = (!_matches);
    if (_not) {
      throw new IllegalStateException("Default value does not respect the strict format");
    }
  }
  
  /**
   * @return le format strict du composant
   */
  public String getStrictFormat() {
    return this.strictFormat.getValue();
  }
  
  /**
   * @return la propriété "Format stricte" en lecture seule
   */
  public ReadOnlyStringProperty strictFormatProperty() {
    return this.strictFormat;
  }
  
  /**
   * @param value la valeur par défaut du composant (non null). Celui-ci remplacera automatiquement le contenu du composant si celui-ci est vide
   * La valeur par défaut doit respecter le formatage stricte si celui-ci a été défini {@link #setStrictFormat setStrictFormat}.
   * @throws IllegalArgumentException si le format stricte n'est pas respecté par la valeur
   */
  public void setDefaultValue(final String value) {
    Objects.<String>requireNonNull(value);
    boolean _matches = this.pattern.matcher(value).matches();
    if (_matches) {
      this.defaultValue.setValue(value);
      final ChangeListener<Boolean> _function = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
        if (((!(newVal).booleanValue()) && ((this.getText() == null) || com.google.common.base.Objects.equal(this.getText(), "")))) {
          this.setText(this.defaultValue.getValue());
        }
      };
      this.focusedProperty().addListener(_function);
      if (((this.getText() == null) || com.google.common.base.Objects.equal(this.getText(), ""))) {
        this.setText(this.defaultValue.getValue());
      }
    } else {
      throw new IllegalArgumentException("Default value does not respect the strict format");
    }
  }
  
  /**
   * @return la valeur par défaut du composant
   */
  public String getDefaultValue() {
    return this.defaultValue.getValue();
  }
  
  /**
   * @return la propriété "Valeur par défaut" en lecture seule
   */
  public ReadOnlyStringProperty defaultValueProperty() {
    return this.defaultValue;
  }
  
  /**
   * @param validator un FormatValidator (non null) à appliquer sur le contenu du composant lorsque celui-ci perd le focus.
   * Si un seul des FormatValidator refuse le contenu du composant, le composant rentre alors en état "Mauvais Format".
   * Si la valeur du composant est une chaîne vide, et que le composant accepte les chaînes vides, alors les Validators seront ignorés
   */
  public void addFormatValidator(final FormatValidator validator) {
    Objects.<FormatValidator>requireNonNull(validator);
    this.validators.put(validator, Boolean.valueOf(true));
    final ChangeListener<Boolean> _function = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      if ((!(newVal).booleanValue())) {
        boolean _not = (!((this.acceptEmpty.getValue()).booleanValue() && com.google.common.base.Objects.equal(this.getText(), "")));
        if (_not) {
          final Optional<String> result = validator.validate(this.getText());
          this.validators.put(validator, Boolean.valueOf(result.isEmpty()));
          final Consumer<String> _function_1 = (String msg) -> {
            this.onValidatorFail(msg);
          };
          result.ifPresent(_function_1);
        } else {
          this.validators.put(validator, Boolean.valueOf(true));
        }
        this.updateState();
      }
    };
    this.focusedProperty().addListener(_function);
  }
  
  /**
   * Recalcule l'état du composant, puis le met à jour
   */
  public void updateState() {
    final Function1<Boolean, Boolean> _function = (Boolean b) -> {
      return Boolean.valueOf((!(b).booleanValue()));
    };
    Boolean _findFirst = IterableExtensions.<Boolean>findFirst(this.validators.values(), _function);
    boolean _tripleNotEquals = (_findFirst != null);
    this.wrongFormatted.setValue(Boolean.valueOf(_tripleNotEquals));
  }
  
  public void updateView() {
    Boolean _value = this.wrongFormatted.getValue();
    if ((_value).booleanValue()) {
      this.pseudoClassStateChanged(this.errorClass, true);
      this.shake();
    } else {
      this.pseudoClassStateChanged(this.errorClass, false);
      this.setTooltip(null);
    }
  }
  
  /**
   * @param failMsg un message à afficher dans le Tooltip du composant en cas d'erreur
   */
  private void onValidatorFail(final String failMsg) {
    String _translate = LanguageManager.translate(failMsg);
    Tooltip _tooltip = new Tooltip(_translate);
    this.setTooltip(_tooltip);
  }
  
  /**
   * Déclenche une petite animation de secouement sur le composant courant
   */
  private void shake() {
    Duration _millis = Duration.millis(0);
    DoubleProperty _translateXProperty = this.translateXProperty();
    KeyValue _keyValue = new <Number>KeyValue(_translateXProperty, Integer.valueOf(0), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame = new KeyFrame(_millis, _keyValue);
    Duration _millis_1 = Duration.millis(100);
    DoubleProperty _translateXProperty_1 = this.translateXProperty();
    KeyValue _keyValue_1 = new <Number>KeyValue(_translateXProperty_1, Integer.valueOf((-10)), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_1 = new KeyFrame(_millis_1, _keyValue_1);
    Duration _millis_2 = Duration.millis(200);
    DoubleProperty _translateXProperty_2 = this.translateXProperty();
    KeyValue _keyValue_2 = new <Number>KeyValue(_translateXProperty_2, Integer.valueOf(10), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_2 = new KeyFrame(_millis_2, _keyValue_2);
    Duration _millis_3 = Duration.millis(300);
    DoubleProperty _translateXProperty_3 = this.translateXProperty();
    KeyValue _keyValue_3 = new <Number>KeyValue(_translateXProperty_3, Integer.valueOf((-10)), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_3 = new KeyFrame(_millis_3, _keyValue_3);
    Duration _millis_4 = Duration.millis(400);
    DoubleProperty _translateXProperty_4 = this.translateXProperty();
    KeyValue _keyValue_4 = new <Number>KeyValue(_translateXProperty_4, Integer.valueOf(10), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_4 = new KeyFrame(_millis_4, _keyValue_4);
    Duration _millis_5 = Duration.millis(500);
    DoubleProperty _translateXProperty_5 = this.translateXProperty();
    KeyValue _keyValue_5 = new <Number>KeyValue(_translateXProperty_5, Integer.valueOf((-10)), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_5 = new KeyFrame(_millis_5, _keyValue_5);
    Duration _millis_6 = Duration.millis(600);
    DoubleProperty _translateXProperty_6 = this.translateXProperty();
    KeyValue _keyValue_6 = new <Number>KeyValue(_translateXProperty_6, Integer.valueOf(10), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_6 = new KeyFrame(_millis_6, _keyValue_6);
    Duration _millis_7 = Duration.millis(700);
    DoubleProperty _translateXProperty_7 = this.translateXProperty();
    KeyValue _keyValue_7 = new <Number>KeyValue(_translateXProperty_7, Integer.valueOf((-10)), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_7 = new KeyFrame(_millis_7, _keyValue_7);
    Duration _millis_8 = Duration.millis(800);
    DoubleProperty _translateXProperty_8 = this.translateXProperty();
    KeyValue _keyValue_8 = new <Number>KeyValue(_translateXProperty_8, Integer.valueOf(10), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_8 = new KeyFrame(_millis_8, _keyValue_8);
    Duration _millis_9 = Duration.millis(900);
    DoubleProperty _translateXProperty_9 = this.translateXProperty();
    KeyValue _keyValue_9 = new <Number>KeyValue(_translateXProperty_9, Integer.valueOf((-10)), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_9 = new KeyFrame(_millis_9, _keyValue_9);
    Duration _millis_10 = Duration.millis(1000);
    DoubleProperty _translateXProperty_10 = this.translateXProperty();
    KeyValue _keyValue_10 = new <Number>KeyValue(_translateXProperty_10, Integer.valueOf(0), Interpolator.EASE_BOTH);
    KeyFrame _keyFrame_10 = new KeyFrame(_millis_10, _keyValue_10);
    final Timeline tl = new Timeline(_keyFrame, _keyFrame_1, _keyFrame_2, _keyFrame_3, _keyFrame_4, _keyFrame_5, _keyFrame_6, _keyFrame_7, _keyFrame_8, _keyFrame_9, _keyFrame_10);
    tl.setDelay(Duration.seconds(0.2));
    tl.play();
  }
}
