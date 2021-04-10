package fr.istic.tools.scanexam.view.fx.component

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.view.fx.component.validator.FormatValidator
import java.util.HashMap
import java.util.Map
import java.util.Objects
import java.util.Optional
import java.util.regex.Pattern
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyStringProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.css.PseudoClass
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.control.Tooltip
import javafx.util.Duration

/**
 * Représente un TextField à formattage.
 * Ce formattage peut être stricte (vérifié à chaque changement sur le composant) ou seulement testé lorsque le composant perd le focus.
 * Si le formattage est mauvais, le composant rentre en état de "Mauvais Format".
 * @author Théo Giraudet
 */
class FormattedTextField extends TextField {
	
	var BooleanProperty wrongFormatted = new SimpleBooleanProperty(this, "wrongFormatted", false)
	var BooleanProperty acceptEmpty = new SimpleBooleanProperty(this, "acceptEmpty", true)
	var StringProperty strictFormat = new SimpleStringProperty(this, "strictFormat", ".*")
	var StringProperty defaultValue = new SimpleStringProperty(this, "defaultValue", "")
	val PseudoClass errorClass
	val Map<FormatValidator, Boolean> validators
	var Pattern pattern
	
	/**
	 * Crée un nouveau FormattedTextField
	 */
	new() {
		super()
		pattern = Pattern.compile(".*")
		defaultValue = ""
		text = defaultValue.value
		errorClass = PseudoClass.getPseudoClass("wrong-format")
		wrongFormatted.addListener(obs, oldVal, newVal | updateView)
		validators = new HashMap<FormatValidator, Boolean>
	}
	
	/**
	 * @param value true si le composant accepte le mot vide, false sinon
	 */
	def boolean getAcceptEmpty() {
		return acceptEmpty.value
	}
	
	/**
	 * @param value true si le composant doit accepter le mot vide, false sinon
	 */
	def void setAcceptEmpty(boolean value) {
		acceptEmpty.value = value
	}
	
	/**
	 * @return la propriété "accepte le mot vide" en lecture seule
	 */
	def ReadOnlyBooleanProperty acceptEmptyProperty() {
		return acceptEmpty
	}
	
	
	/**
	 * @return true si le composant est mal formaté, false sinon
	 */
	def boolean getWrongFormatted() {
		return wrongFormatted.value
	}
	
	/**
	 * @return true si le composant doit être mal formaté, false sinon
	 */
	def void setWrongFormatted(boolean value) {
		wrongFormatted.value = value
	}
	
	/**
	 * @return la propriété "est mal formaté" en lecture seule
	 */
	def ReadOnlyBooleanProperty wrongFormattedProperty() {
		return wrongFormatted
	}
	
	
	/**
	 * @param formatter un regex (non null) que le contenu du composant doit respecté. Ce format est vérifié à chaque changement du contenu du composant
	 * @throws IllegalArgumentException si le format stricte n'est pas respecté par la valeur {@link #setDefaultValue setDefaultValue}
	 */
	def setStrictFormat(String formatter) {
		Objects.requireNonNull(formatter)
		pattern = Pattern.compile(formatter)
		this.textFormatter = new TextFormatter<String> [ change | pattern.matcher(change.text).matches() ? change : null]
		if(!pattern.matcher(defaultValue.value).matches)
			throw new IllegalStateException("Default value does not respect the strict format")
	}
	
	/**
	 * @return le format strict du composant
	 */
	def String getStrictFormat() {
		return strictFormat.value
	}
	
	/**
	 * @return la propriété "Format stricte" en lecture seule
	 */
	def ReadOnlyStringProperty strictFormatProperty() {
		return strictFormat
	}
	
	/**
	 * @param value la valeur par défaut du composant (non null). Celui-ci remplacera automatiquement le contenu du composant si celui-ci est vide
	 * La valeur par défaut doit respecter le formatage stricte si celui-ci a été défini {@link #setStrictFormat setStrictFormat}.
	 * @throws IllegalArgumentException si le format stricte n'est pas respecté par la valeur
	 */
	def setDefaultValue(String value) {
		Objects.requireNonNull(value)
		if(pattern.matcher(value).matches) {
			defaultValue.value = value
			this.focusedProperty.addListener[ obs, oldVal, newVal | !newVal && (text === null || text == "") ? this.text = defaultValue.value]
			if(text === null || text == "")
				text = defaultValue.value
		} else
			throw new IllegalArgumentException("Default value does not respect the strict format")
	}
	
	/**
	 * @return la valeur par défaut du composant
	 */
	def String getDefaultValue() {
		return defaultValue.value
	}
	
	/**
	 * @return la propriété "Valeur par défaut" en lecture seule
	 */
	def ReadOnlyStringProperty defaultValueProperty() {
		return defaultValue
	}
	
	/**
	 * @param validator un FormatValidator (non null) à appliquer sur le contenu du composant lorsque celui-ci perd le focus.
	 * Si un seul des FormatValidator refuse le contenu du composant, le composant rentre alors en état "Mauvais Format".
	 * Si la valeur du composant est une chaîne vide, et que le composant accepte les chaînes vides, alors les Validators seront ignorés
	 */
	def addFormatValidator(FormatValidator validator) {
		Objects.requireNonNull(validator)
		validators.put(validator, true)
		focusedProperty.addListener[obs, oldVal, newVal| !newVal ? {
				if(!(acceptEmpty.value &&  text == "")) {
					val Optional<String> result = validator.validate(text)
					validators.put(validator, result.empty)
					result.ifPresent(msg | onValidatorFail(msg))
				} else
					validators.put(validator, true)
				updateState
			}
		]
	}
	
	
	/**
	 * Recalcule l'état du composant, puis le met à jour
	 */
	def updateState() {
		wrongFormatted.value = validators.values.findFirst[b | !b] !== null
	}
	
	def updateView() {
		if (wrongFormatted.value) {
			this.pseudoClassStateChanged(errorClass, true)
			shake()
		} else {
			this.pseudoClassStateChanged(errorClass, false)
			tooltip = null
		}
	}		
	
	/**
	 * @param failMsg un message à afficher dans le Tooltip du composant en cas d'erreur
	 */
	private def onValidatorFail(String failMsg) {
		tooltip = new Tooltip(LanguageManager.translate(failMsg))
	}
	
	
	/** 
	 * Déclenche une petite animation de secouement sur le composant courant
	 * */
	private def void shake() {
		val tl = new Timeline(
			new KeyFrame(Duration.millis(0), new KeyValue(this.translateXProperty(), 0, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(100), new KeyValue(this.translateXProperty(), -10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(200), new KeyValue(this.translateXProperty(), 10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(300), new KeyValue(this.translateXProperty(), -10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(400), new KeyValue(this.translateXProperty(), 10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(500), new KeyValue(this.translateXProperty(), -10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(600), new KeyValue(this.translateXProperty(), 10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(700), new KeyValue(this.translateXProperty(), -10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(800), new KeyValue(this.translateXProperty(), 10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(900), new KeyValue(this.translateXProperty(), -10, Interpolator.EASE_BOTH)),
			new KeyFrame(Duration.millis(1000), new KeyValue(this.translateXProperty(), 0, Interpolator.EASE_BOTH))
		)
		tl.setDelay(Duration.seconds(0.2))
		tl.play()
	}
}