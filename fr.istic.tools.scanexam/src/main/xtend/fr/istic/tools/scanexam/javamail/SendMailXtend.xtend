package fr.istic.tools.scanexam.javamail

class SendMailXtend  {
	
	//Remplissage des champ par défault
	
	
	//Adresse mail de l'expediteur
	var static sender = "thomas.guibert@etudiant.univ-rennes1.fr"
		
	//mot de passe de l'expediteur
	var static senderPassword= "Twinrai*39"
		
	//Adresse mail cible du mail
	var static recipient = "thomas.guibert0930@gmail.com"
		
	//Titre du mail
	var static titleMail = "A completer"
		
	//Message a envoier - (changer la nature du message a envoyer)
	var static messageMail =
	"A completer"

	//chemin vers la piece jointe - (de la forme C:\\Users\\... mettre "" quand il n'y a pas de piece jointe)
	var static pieceJointe = "C:\\Users\\MSI\\Downloads\\IA-TP1.pdf"
	
	
	
	def static void main(String[] arg){
		
		//Envoie du mail
		SendMailTls.sendMail(getSender(), getSenderPassword(), getRecipent(), getTitle(), getMessage(), getPieceJointe())
	}
	
	
	
	
	//Fonction envoie du mail
	def static sendMailXtend(){
		SendMailTls.sendMail(getSender(), getSenderPassword(), getRecipent(), getTitle(), getMessage(), getPieceJointe())
	}
	
	
	//Liste des getter et setter
	
	def static String getSender(){
		return sender
	}
	def static String setSender(String send){
		sender = send
	}
	
	def static String getSenderPassword(){
		return senderPassword
	}
	def static String setSenderPassword(String pass){
		senderPassword = pass
	}
	
	def static String getRecipent(){
		return recipient
	}
	def static String setRecipent(String recip){
		recipient = recip
	}
	
	def static String getTitle(){
		return titleMail
	}
	def static String setTitle(String title){
		titleMail = title
	}
	
	def static String getMessage(){
		return messageMail
	}
	def static String setMessage(String message){
		messageMail = message
	}
	
	def static String getPieceJointe(){
		return pieceJointe
	}
	def static String setPieceJointe(String piece){
		pieceJointe = piece
	}
}