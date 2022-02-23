package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
* classe chargée d'afficher le menu d'aide aux contrôles.
*/
public class Menu{
	private Fenetre d;
	private boolean open;
	private Indication ind;
	
	/**
     * Constructeur
     * @param d affiche le menu sur l'écran
     */
	public Menu(Fenetre d){
		this.d = d;
		open = false;
		ind = new Indication(Color.YELLOW,"Ouvrir l'aide aux contrôles",Color.WHITE,"appuyez sur \"Echap\"",Textures.help);
		this.d.getIndicationHandler().add(ind);
	}

	/**
     * Ouvre ou ferme le menu lorsque l'on appuie sur "Echap"
     * @param e KeyEvent
     */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			open = !open;
			if(this.d.getIndicationHandler().contains(ind)){
				this.d.getIndicationHandler().remove(ind);
			}
		}
	}

	/**
     * Affiche le menu
     * @param image affiche le menu sur la fenêtre
     */
	public void drawMenu(BufferedImage image){
		if(open){
			Graphics2D g2d = image.createGraphics();
			g2d.drawImage(Textures.menu, 0, 0, d.getWidth(), d.getHeight(), null);
		}
	}
}