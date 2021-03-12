package fr.istic.tools.scanexam.mailing

/**
 * @author Thomas Guibert
 */
class SendMailXtend  {
	
	//Remplissage des champ par défault
	
	//Adresse mail de l'expediteur
	var static sender = "A completer"
		
	//mot de passe de l'expediteur
	var static senderPassword= "A completer"
		
	//Adresse mail cible du mail
	var static recipient = "A completer"
		
	//Titre du mail
	var static titleMail = "A completer"
		
	//Message a envoier - (changer la nature du message a envoyer)
	var static messageMail ="A completer"

	//chemin vers la piece jointe - (de la forme C:\\Users\\... mettre "" quand il n'y a pas de piece jointe)
	var static pieceJointe = "A completer"


	
	def static void main(String[] arg){
		
		//Envoie du mail
		SendMailTls.sendMail(sender, senderPassword , recipient, titleMail, messageMail, pieceJointe)
	}
	
	
	
	
	/**
	 * Fonction envoie du mail avec les valeur donnée par les getter
	 * 
	 */
	def sendMailXtend(){
		SendMailTls.sendMail(sender, senderPassword, recipient, titleMail, messageMail, pieceJointe)
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