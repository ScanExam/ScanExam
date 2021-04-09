package fr.istic.tools.scanexam.view

import java.util.List
import fr.istic.tools.scanexam.presenter.PresenterGraduation

/** 
 * Interface d'adaptateur du correcteur d'examen 
 * @author Julien Cochet
 */
interface AdapterGraduation extends Adapter<PresenterGraduation> {
	
    def List<String> questionNames()
    
    /**
	 * @return next question
	 */
    def void nextQuestion()
    
    /**
     * @param question is the actual question
	 * @return previous question
	 */
    def void previousQuestion()

    def void thisQuestion(int index)
    
}
