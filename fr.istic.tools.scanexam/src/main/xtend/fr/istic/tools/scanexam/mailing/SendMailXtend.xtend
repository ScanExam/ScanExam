package fr.istic.tools.scanexam.mailing

import fr.istic.tools.scanexam.services.api.Service

/**
 * @author Thomas Guibert
 */
class SendMailXtend  {
	
	//Remplissage des champ par défault
	
	//Adresse mail de l'expediteur
	var static sender = ""
		
	//mot de passe de l'expediteur
	var static senderPassword = ""
		
	//Numero anonymat ou nom présent sur la copie
	var static recipient = ""
		
	//Titre du mail
	var static titleMail = ""
		
	//Message a envoier - (changer la nature du message a envoyer)
	var static messageMail =""

	//chemin vers la piece jointe - (de la forme C:\\Users\\... mettre "" quand il n'y a pas de piece jointe)
	var static pieceJointe = ""

	
	def static void main(String[] arg){
		//Sauvegarde du fichier contenant les informations sur les mails		
		//SendMailTls.save(fichier)
		//Envoie du mail
		
		SendMailTls.sendMail1("testscanexam@gmail.com", "azerty35!" , "Arthur Lalande-Marchand", titleMail, messageMail, pieceJointe,"test2_pfo")
	}
	
	/**
	 * Fonction envoie du mail avec les valeur donnée par les getter
	 * 
	 */
	def sendMailXtend(){
		SendMailTls.sendMail(sender, senderPassword, recipient, titleMail, messageMail, pieceJointe,"ExamPfo")
	}
	
	/**
	 *Liste des getter et setter pour modifier est acceder au parametre 
	 * 
	 */
	
	def String getSender(){
		return sender
	}
	
	def String setSender(String send){
		sender = send
	}
	
	def String getSenderPassword(){
		return senderPassword
	}
	def String setSenderPassword(String pass){
		senderPassword = pass
	}
	
	def String getRecipent(){
		return recipient
	}
	def String setRecipent(String recip){
		recipient = recip
	}
	
	def String getTitle(){
		return titleMail
	}
	def String setTitle(String title){
		titleMail = title
	}
	
	def String getMessage(){
		return messageMail
	}
	def String setMessage(String message){
		messageMail = message
	}
	
	def String getPieceJointe(){
		return pieceJointe
	}
	
	def String setPieceJointe(String piece){
		pieceJointe = piece
	}
}