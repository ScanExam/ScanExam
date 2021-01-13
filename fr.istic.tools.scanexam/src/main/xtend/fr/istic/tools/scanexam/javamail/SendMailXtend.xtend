package fr.istic.tools.scanexam.javamail

import fr.istic.tools.scanexam.javamail.SendMailTLS

class SendMailXtend extends SendMailTLS{
	def static void main(String[] arg){
		
		//Adresse mail cible du mail
		
		//Pour tester entrer votre adresse mail ici
		val user = "...@gmail.com"
		
		
		//Message a envoier - (changer la nature du message a envoyer)
		val message =
		"Bonjour 
		\n\n Ceci est un mail envoyer par ScanExam pour prochainement pouvoir vous envoyer votre note directement par mail.
		\n\n A bientôt"
		
		sendMail(user,message)
	}
}
