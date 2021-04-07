package fr.istic.tools.scanexam.mailing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.beust.jcommander.internal.Lists;

public class TestanonymousMail {
	
	//Nom donnée à l'examen
	String examName = "test1_pfo";
	String examName1 = "test2_pfo";
	
	File fichier = new File("src\\main\\resources\\mailing\\mailAnonyme");
	File fichier1 = new File("src\\main\\resources\\mailing\\mailNom");
	
	String pdf = "src\\main\\resources\\QRCode\\pfo_example.pdf";

	
	private Object jailBreak(Class<?> clazz, String name, Object... args) {
		try {
			final Class<?>[] argClasses = Lists.newArrayList(args).stream()
					.map(o -> o.getClass())
					.toArray(Class<?>[]::new);
			Method method = clazz.getDeclaredMethod(name, argClasses);
			method.setAccessible(true);
			return method.invoke(clazz, args);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Test
	@DisplayName("1:Sauvegarde du chemin du fichier contenant les mail")
	void test0() throws IOException {
		jailBreak(SendMailTls.class, "save1", fichier, examName);
		File cheminInfo = new File(examName + ".txt");
		FileReader fx = new FileReader(cheminInfo);
		BufferedReader f = new BufferedReader(fx);
		//Remplir le chemin ou est situé mailAnonyme
		Assertions.assertEquals(f.readLine(),"");
		f.close();
	}
	
	@Test
	@DisplayName("2:Sauvegarde du chemin du fichier contenant les mail")
	void test1() throws IOException {
		jailBreak(SendMailTls.class, "save1", fichier1, examName1);
		File cheminInfo = new File(examName1 + ".txt");
		FileReader fx = new FileReader(cheminInfo);
		BufferedReader f = new BufferedReader(fx);
		//Remplir le chemin ou est situé mailNom
		Assertions.assertEquals(f.readLine(),"");
		f.close();
	}
	
	@Test
	@DisplayName("Envoyer un mail avec numero anonymat")
	void test2() throws IOException {
		//Ajouter un mail, son mot de passe et un numero d'anonymat disponible dans mailAnonyme
		String mail = "";
		String mdp = "";
		String numAnonymat = "";
		jailBreak(SendMailTls.class, "sendMail1", mail, mdp, numAnonymat, "", "", "",examName);
	}
	
	@Test
	@DisplayName("Envoyer un mail avec Prenom/Nom")
	void test3() throws IOException {
		//Ajouter un mail, son mot de passe et un nom disponible dans mailNom
		String mail = "";
		String mdp = "";
		String nomCopie = "";
		jailBreak(SendMailTls.class, "sendMail1", mail, mdp, nomCopie, "", "", "",examName1);
	}
}
