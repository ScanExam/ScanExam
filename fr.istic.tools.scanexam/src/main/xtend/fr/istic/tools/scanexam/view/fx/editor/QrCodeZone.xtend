package fr.istic.tools.scanexam.view.fx.editor

/**
 * Donnée de la zone contenant le qr code
 * @author Julien Cochet
 */
class QrCodeZone {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	/* Zone du qr code */
	Box zone

	/* Échelle de la zone du qr code */
	float scale

	// ----------------------------------------------------------------------------------------------------
	/*
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Constructeur
	 * @param zone Zone du qr code
	 */
	new(Box zone) {
		this.zone = zone
		this.scale = 1.0f
		this.zone.setupEvents(this.getType())
		setFocus(true)
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * GETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Revoie la zone du qr code
	 * @return Zone du qr code
	 */
	def Box getZone() {
		return this.zone
	}

	/**
	 * Revoie l'échelle de la zone du qr code
	 * @return Échelle de la zone du qr code
	 */
	def float getScale() {
		return this.scale
	}

	/**
	 * Revoie le type de box de la zone du qr code
	 * @return BoxType.QR
	 */
	def BoxType getType() {
		return BoxType.QR
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * SETTERS
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Sélectionne ou non la zone du qr code
	 * @param True si l'on veut sélectionner la zone, false sinon
	 */
	def void setFocus(boolean focus) {
		this.zone.focus = focus
	}

	/**
	 * Met à jour l'échelle de la zone du qr code
	 * @return Nouvelle échelle de la zone du qr code
	 */
	def void setScale(float scale) {
		this.scale = scale
	}

}
