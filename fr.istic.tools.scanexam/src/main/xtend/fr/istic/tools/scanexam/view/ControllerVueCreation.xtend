package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.presenter.PresenterVueCreation
import java.util.Objects

/**
 * General controller for the Creation view
 * @author Benjamin Danlos
 */
class ControllerVueCreation {
	/**
	 * Presenter to send the requests to to interact with the model
	 */
	PresenterVueCreation presenter
	
	/**
	 * Controller used by the JavaFX view
	 */
	ControllerFX controllerFX
	
	/**
	 * Controller used by the Swing view
	 */
    ControllerSwing controllerSwing
    
    /**
	 * setter for the PresenterVueCreation attribute
	 * @param {@link PresenterVueCreation} pres instance of the presenter (not null) 
	 */
    def setPresenterVueCreation(PresenterVueCreation pres){
    	Objects.requireNonNull(pres)
    	presenter = pres
    }
    /**
	 * @return current {@link PresenterVueCreation} 
	 */
    def getPresenterVueCreation(){
    	presenter
    }
    
    /**
	 * setter for the ControllerFX attribute
	 * @param {@link ControllerFX} pres instance of the Java FX Controller (not null) 
	 */
    def setControllerFX(ControllerFX contr){
    	Objects.requireNonNull(contr)
    	controllerFX=contr
    }
    /**
	 * @return current Java FX controller {@link ControllerFX} 
	 */
    def getControllerFX(){
    	controllerFX
    }
    
    /**
	 * setter for the ControllerSwing attribute
	 * @param {@link ControllerSwing} pres instance of the Swing Controller (not null) 
	 */
    def setControllerSwing(ControllerSwing contr){
    	Objects.requireNonNull(contr)
    	controllerSwing=contr
    }
    /**
	 * @return current Swing controller {@link ControllerSwing} 
	 */
    def getControllerSwing(){
    	controllerSwing
    }
    
    
}