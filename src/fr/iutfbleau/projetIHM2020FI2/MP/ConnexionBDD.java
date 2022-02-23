package fr.iutfbleau.projetIHM2020FI2.MP;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import java.util.*;
import java.sql.*;

public class ConnexionBDD{
    protected static Connection cnx;
	
    protected static void init() {
        try{
            Class.forName("org.mariadb.jdbc.Driver");
        } catch(ClassNotFoundException e){
            System.out.println("Driver indisponible !" + e.getMessage());
        }

        try{
            cnx = DriverManager.getConnection("jdbc:mariadb://dwarves.iut-fbleau.fr/lacaste", "lacaste", "LANnjaaC77972");
        } catch(SQLException e){
            System.out.println("Echec de la connexion !" + e.getMessage());
        }
    }
	
	protected static void createTable(){
		try{
			cnx.prepareStatement(
				"CREATE TABLE IF NOT EXISTS Pieces ( "+
					"id int PRIMARY KEY,"+ 
					"visited boolean NOT NULL DEFAULT 0,"+ 
					"joueur boolean NOT NULL DEFAULT 0"+ 
				");").executeQuery();
			
			cnx.prepareStatement(
				"CREATE TABLE IF NOT EXISTS Passages ("+
					"id int PRIMARY KEY,"+
					"dir1 varchar(10) NOT NULL CHECK (dir1 IN ('NORD','SUD','OUEST','EST')),"+
					"piece1 int NOT NULL REFERENCES Pieces(id) ,"+
					"dir2 varchar(10) NOT NULL CHECK (dir2 IN ('NORD','SUD','OUEST','EST')),"+
					"piece2 int NOT NULL REFERENCES Pieces(id),"+
					"etat varchar(20) NOT NULL DEFAULT 'fermé' CHECK (etat IN ('OPEN','CLOSED','LOCKED'))"+
				");").executeQuery();
				
			cnx.prepareStatement(
				"CREATE TABLE IF NOT EXISTS Trucs ( "+
					"contientTruc_id int NOT NULL,"+ 
					"typeTruc varchar(20) NOT NULL CHECK (typetruc IN ('CLE','ALCOOL','EAU','GOODIES')),"+ 
					"description varchar(200) NOT NULL"+
				");").executeQuery();
				
		}catch(SQLException e){
			System.out.println("Echec de la connexion !" + e.getMessage());
		}
	}
	
	protected static void clear(){
		try{
			cnx.prepareStatement("DELETE FROM Passages").executeQuery();
			cnx.prepareStatement("DELETE FROM Pieces").executeQuery();
			cnx.prepareStatement("DELETE FROM Trucs").executeQuery();
		}catch(SQLException e){
			System.out.println("Echec de la connexion !" + e.getMessage());
		}
	}
	
	
}