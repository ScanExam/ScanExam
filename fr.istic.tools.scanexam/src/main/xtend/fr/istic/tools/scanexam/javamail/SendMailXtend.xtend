package fr.istic.tools.scanexam.javamail

class SendMailXtend  {
	def static void main(String[] arg){
		
		
		//Adresse mail de l'expediteur
		val exp = "A Compléter"
		
		//mot de passe de l'expediteur
		val expMdp= "A Compléter"
		
		
		
		//Adresse mail cible du mail
		val dest = "A Compléter"
		
		
		
		//Titre du mail
		val title = "ScanExam Envoie"
		
		//Message a envoier - (changer la nature du message a envoyer)
		val message =
		"Bonjour 
		Ceci est un mail envoyé par ScanExam pour prochainement pouvoir vous envoyer votre note directement par mail.
		À bientôt"
		
		SendMailTls.sendMail(exp,expMdp,dest,title,message)
		
	}
}
