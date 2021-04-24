package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.view.fx.graduation.Grader.GradeItem
import javafx.fxml.FXML
import javafx.scene.web.HTMLEditor
import javafx.stage.Stage

class HTMLView {
	@FXML HTMLEditor html
	static public GradeItem item
	
	/* Permet de ne pouvoir qu'un seul éditeur à la fois */
	static public boolean isHTMLEditorOpen = false
	
	static public Stage stage

	@FXML
	private def onMouseClicked() {
		//label.setText(html.getHtmlText());
	    //webEngine = webView.getEngine();
		//webEngine.loadContent(html.getHtmlText());
		var htmlText = html.getHtmlText()
		
		if(htmlText.contains("contenteditable=\"true\""))
		    htmlText=htmlText.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
		    
		if(htmlText.contains("body "))
		    htmlText=htmlText.replace("body ", "body style=\"height: 130; max-width: 130; padding-right: 10;\" ");
		/*
		if(htmlText.contains("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">") && htmlText.contains("</body></html>")) {
		    htmlText=htmlText.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\">", "");
		    htmlText=htmlText.replace("</body></html>", "");
		}
		
		if(htmlText.contains("<p>"))
		    htmlText=htmlText.replace("<p>", "<p style=\"word-wrap: normal\" >");
		
		
		if(htmlText.contains("<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p>") && htmlText.contains("</p></body></html>")) {
		    htmlText=htmlText.replace("<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p>", "");
		    htmlText=htmlText.replace("</p></body></html>", "");
		}*/
		
		
		item.changeText(htmlText)
		println(htmlText)
		stage.close()
	}
	
}