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

	/* Controlleur de l'édition du sujet maître */
	ControllerFxEdition controller

	// ----------------------------------------------------------------------------------------------------
	/*
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Constructeur
	 * @param zone Zone du qr code
	 * @param controller Controlleur de l'édition du sujet maître
	 */
	new(Box zone, ControllerFxEdition controller) {
		this.zone = zone
		this.scale = 1.0f
		this.zone.setupEvents(this.getType())
		this.controller = controller
		addToModel
		setFocus(true)
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * MÉTHODE
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Ajoute les coordonnées de la zone qr code dans le modèle
	 */
	def addToModel() {
		controller.createQrCode(zone.x / controller.maxX, zone.y / controller.maxY, zone.height / controller.maxY,
			zone.width / controller.maxX)
	}

	/**
	 * Met à jour les coordonnées de la zone qr code dans le modèle
	 */
	def void updateInModel() {
		controller.moveQrCode(zone.x / controller.maxX, zone.y / controller.maxY)
		controller.resizeQrCode(zone.height / controller.maxY, zone.width / controller.maxX)
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
