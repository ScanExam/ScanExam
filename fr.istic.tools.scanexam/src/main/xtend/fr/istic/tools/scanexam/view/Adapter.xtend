package fr.istic.tools.scanexam.view

/** 
 * Interface d'adaptateur général
 * @author Julien Cochet
 */
interface Adapter {
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * GETTER
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Renvoie l'adaptateur du correcteur d'examen
	 * @return correctorAdapter
	 */
	def CorrectorAdapter getCorrectorAdapter();
	
	/**
	 * Renvoie l'adaptateur de l'éditeur d'examen
	 * @return editorAdapter
	 */
	def EditorAdapter getEditorAdapter();
	
}
