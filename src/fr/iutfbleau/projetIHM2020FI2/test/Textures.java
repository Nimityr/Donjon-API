package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.EnumMap;

/**
* Classe chargée de contenir les références static vers chacune des textures afin qu'elles soient accessible partout et chargé qu'une seul fois.
*/
public class Textures{
	
	
	public static BufferedImage wall;
	public static BufferedImage door;
	public static BufferedImage openDoor;
	public static BufferedImage e;
	public static BufferedImage eau;
	public static BufferedImage alcool;
	public static BufferedImage cle;
	public static BufferedImage goodies;
	public static BufferedImage menu;
	public static BufferedImage clicDroit;
	public static BufferedImage help;
	public static EnumMap<TypeTruc, BufferedImage> items;
	
	/**
     * méthode permettant de charger les textures.
     */
	public static void load(){		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try{
			wall = ImageIO.read(loader.getResourceAsStream("images/texture.png"));
			door = ImageIO.read(loader.getResourceAsStream("images/door.png"));
			openDoor = ImageIO.read(loader.getResourceAsStream("images/openDoor.png"));
			e = ImageIO.read(loader.getResourceAsStream("images/E.png"));
			eau = ImageIO.read(loader.getResourceAsStream("images/bouteille_eau.png"));
			alcool = ImageIO.read(loader.getResourceAsStream("images/bouteille_rhum.png"));
			cle = ImageIO.read(loader.getResourceAsStream("images/cle.png"));
			goodies = ImageIO.read(loader.getResourceAsStream("images/tresor.png"));
			menu = ImageIO.read(loader.getResourceAsStream("images/menu.png"));
			clicDroit = ImageIO.read(loader.getResourceAsStream("images/clicDroit.png"));
			help = ImageIO.read(loader.getResourceAsStream("images/help.png"));
		}catch(IOException e){
			System.out.println("error : " + e.getMessage());
		}
		items = new EnumMap(TypeTruc.class);
		items.put(TypeTruc.EAU,eau);
		items.put(TypeTruc.ALCOOL,alcool);
		items.put(TypeTruc.CLE,cle);
		items.put(TypeTruc.GOODIES,goodies);
	}
	
}