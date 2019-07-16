package com.edugroupe.firstJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.edugroupe.firstJdbc.dao.ActeurDao;
import com.edugroupe.firstJdbc.metier.Acteur;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args )
	{

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection bdd = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/base_java_1",
					"root",
					"");

			System.out.println("Super We are connected to DataBase :-)");
			ActeurDao acteurDao = new ActeurDao(bdd);

			List<Acteur> acteurs = acteurDao.findAll();
			acteurs.stream().forEach(a -> System.out.println(a));

			System.out.println("----------------------");
			Acteur acteur = acteurDao.findOne(5);
			System.out.print(acteur);

			Scanner reader = new Scanner(System.in);
			while(true) {
				System.out.println("Que souhaitez vous faire ?");
				System.out.println("1 - Lister les acteurs");
				System.out.println("2 - Editer un acteur");
				System.out.println("3 - Creer un acteur");
				System.out.println("4 - Effacer un acteur");
				System.out.println("5 - Quitter");
				int choix = Integer.parseInt(reader.nextLine());

				switch (choix) {
				case 1:
					listerActeur(acteurDao);
					break;
				case 2 :
					editerActeur(acteurDao);
					break;

				case 3:
					insererActeur(acteurDao);
					break;
				case 4:
					deleteActeur(acteurDao);
					break;
				}

				if(choix == 5) {
					System.out.println("au revoir !");
					break;
				}
			}


		}catch (ClassNotFoundException e) {e.printStackTrace();} 
		catch (SQLException e) {e.printStackTrace();}

	}

	private static void listerActeur(ActeurDao acteurDao) {
		acteurDao.findAll().stream().forEach(a -> System.out.println(a));
	}

	private static void supprActeur(int id) {
	}


	private static void editerActeur(ActeurDao acteurDao) {
		listerActeur(acteurDao);
		System.out.println("Quel acteur souhaitez vous editer (id) ?");
		Scanner reader = new Scanner(System.in);
		int id =Integer.parseInt( reader.nextLine());
		Acteur a = acteurDao.findOne(id);
		if(a==null) {
			System.out.println("acteur inconnu");
			return;
		}
			System.out.println("Nouveau nom (vide pour ne pas changer'"+a.getNom()+"') ?");
			String saisie = reader.nextLine();
			if(saisie.length()> 0)
				a.setNom(saisie);
			System.out.println("Nouveau prenom (vide pour ne pas changer'"+a.getPrenom()+"') ?");
			saisie = reader.nextLine();
			if(saisie.length()> 0)
				a.setPrenom(saisie);
			System.out.println("Nouvel email (vide pour ne pas changer'"+a.getEmail()+"') ?");
			saisie = reader.nextLine();
			if(saisie.length()> 0)
				a.setEmail(saisie);
			acteurDao.save(a);
	}
	
	private static void insererActeur(ActeurDao acteurDao) {
		Scanner reader = new Scanner(System.in);
		Acteur a = new Acteur();
		System.out.println("nouveau nom");
		String saisie = reader.nextLine();
		a.setNom(saisie);
		System.out.println("nouveau prenom");
		 saisie = reader.nextLine();
		 a.setPrenom(saisie);
		System.out.println("nouvel email");
		 saisie = reader.nextLine();
		 a.setEmail(saisie);
		
		acteurDao.save(a);
	}
	
	private static void deleteActeur(ActeurDao acteurDao) {
		listerActeur(acteurDao);
		Scanner reader = new Scanner(System.in);
		System.out.println("Quel acteur souhaitez-vous supprimer (id) ?");
		int saisie = Integer.parseInt(reader.nextLine());
		acteurDao.delete(saisie);
		
	}


}
