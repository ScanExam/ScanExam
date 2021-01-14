package fr.istic.tools.scanexam.javamail

class SendMailXtend  {
	def static void main(String[] arg){
		
		//Adresse mail cible du mail
		
		//Pour tester entrer votre adresse mail ici
		//val user = "...@gmail.com"
		val user = "...@gmail.com"
		
		
		//Message a envoier - (changer la nature du message a envoyer)
		val message =
		"Bonjour 
		Ceci est un mail envoyé par ScanExam pour prochainement pouvoir vous envoyer votre note directement par mail.
		À bientôt"
		
		SendMailTls.sendMail(user,message)
	}
}
