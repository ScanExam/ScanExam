package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.presenter.GraduationPresenter
import java.util.List

/** 
 * Interface d'adaptateur du correcteur d'examen 
 * @author Julien Cochet
 */
interface GraduationAdapter extends Adapter<GraduationPresenter> {
	
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
