package fr.istic.tools.scanexam.javamail

class SendMailXtend  {
	def static void main(String[] arg){
		
		//Adresse mail de l'expediteur
		val SENDER = "A completer"
		
		
		//mot de passe de l'expediteur
		val SENDER_PASSWORD= "A completer"
		
	
		//Adresse mail cible du mail
		val RECIPIENT = "A completer"
		
	
		//Titre du mail
		val TITLE_MAIL = "A completer"
		
		
		//Message a envoier - (changer la nature du message a envoyer)
		val MESSAGE_MAIL =
		"A completer"

		//chemin vers la piece jointe - (de la forme C:\\Users\\... mettre null quand il n'y a pas de piece jointe)
		val PIECE_JOINTE = null
	//  "C:\\Users\\MSI\\Downloads\\IA-TP1.pdf"

		//Envoie du mail
		SendMailTls.sendMail(SENDER, SENDER_PASSWORD, RECIPIENT, TITLE_MAIL, MESSAGE_MAIL, PIECE_JOINTE)
	}
}
