package fr.istic.tools.scanexam.javamail

class SendMailXtend  {
	def static void main(String[] arg){
		
		//Adresse mail de l'expediteur
		val SENDER = "thomas.guibert10@orange.fr"
		
		
		//mot de passe de l'expediteur
		val SENDER_PASSWORD= "Pokemon10"
		
	
		//Adresse mail cible du mail
		val RECIPIENT = "thomas.guibert0930@gmail.com"
		
	
		//Titre du mail
		val TITLE_MAIL = "ScanExam Envoie"
		
		
		//Message a envoier - (changer la nature du message a envoyer)
		val MESSAGE_MAIL =
		"Bonjour 
		Ceci est un mail envoyé par ScanExam pour prochainement pouvoir vous envoyer votre note directement par mail.
		À bientôt"

		//chemin vers la piece jointe - (de la forme C:\\Users\\...)
		val PIECE_JOINTE = "C:\\Users\\MSI\\Downloads\\IA-TP1.pdf"


		//Envoie du mail
		SendMailTls.sendMail(SENDER, SENDER_PASSWORD, RECIPIENT, TITLE_MAIL, MESSAGE_MAIL, PIECE_JOINTE)
	}
}
