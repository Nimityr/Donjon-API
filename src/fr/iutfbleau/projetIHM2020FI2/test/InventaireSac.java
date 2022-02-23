package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.*;

/**
* classe chargée de gérer l'inventaire du joueur en bas de l'écran ainsi que l'utilisation d'item.
*/
public class InventaireSac{
	private Fenetre d;
	private Joueur j;
	private int count, nbCle, nbItem, itemWidth, itemHeight;

	/**
     * Constructeur.
     * Initialisation des variables
     */
	public InventaireSac(Fenetre d, Joueur j){
		this.d = d;
		this.j = j;
		count = 0;
		itemWidth = d.getWidth()/16;
		itemHeight = d.getHeight()/16-2;
	}

	/**
	 * Invoqué lorsque la molette de la souris est tournée.
     * @param e MouseWheelEvent
     */
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() < 0){
			if(count <= 260)
	        	count += 65;
			else{
				count = 0;
			}
	    }	
	    else{
	        if(count >= 65)
	        	count -= 65;
			else{
				count = 325;
			}
	    }
	    selectItem();
	}


	/**
	 * Invoqué lorsque le clique de la souris est laché
     * @param e MouseEvent
     */
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON3 && !this.d.getCoffre().isOpen()){
			Iterator<Truc> goods = j.getTrucs();
			Truc t = Coffre.iteratorGet(goods, count/65+1);
			if(t == null){
				return;
			}else if(!(Objects.equals(t.getTypeTruc(),TypeTruc.CLE)))
				j.agir(t);
			else if(d.getActionDoor().getLookDoor()){
				d.getActionDoor().getPassage().agir(t);
			}
		}
	}

	/**
	 * permet de dessiner l'inventaire du joueur et ses différents Trucs
     * @param image BufferedImage qui permet de dessiner sur la fenêtre
     */
	public void drawInventaire(BufferedImage image){
		Graphics2D g2d = image.createGraphics();
		BufferedImage img = buildInventaire();
		g2d.drawImage(img, (d.getWidth()-395)/2, d.getHeight()-70, 395, 70, null);

		BufferedImage selected = selectItem();
		g2d.drawImage(selected, (d.getWidth()-395)/2+count, d.getHeight()-75, 75, 75, null);
		if(j.combienTrucs() > 0){
			nbItem = 0;
			nbCle = 0;
			Iterator<Truc> goods = j.getTrucs();			
			while(goods.hasNext()){
				Truc t = goods.next();
				g2d.drawImage(Textures.items.get(t.getTypeTruc()), ((d.getWidth()-395)/2+10)+nbItem*65, d.getHeight()-60, itemWidth-2, itemHeight+11, null);
	            nbItem++;
			}
		}
	}

	/**
	 * permet de construire l'inventaire du joueur
     * @return une BufferedImage qui est le dessin de l'inventaire
     */
	public BufferedImage buildInventaire(){
		BufferedImage inventaire;
		inventaire = new BufferedImage(395, 70, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gInventaire = inventaire.createGraphics();
		gInventaire.setColor(Color.BLACK);
		gInventaire.fillRect(0, 0, inventaire.getWidth(), inventaire.getHeight());
		for(int i = 0; i < itemWidth*7; i += itemWidth+15){
			gInventaire.setColor(Color.GRAY);
			gInventaire.fillRect(i+4, 4, itemWidth+12, itemWidth+12);
			gInventaire.setColor(new Color(0f, 0f, 0f, .3f));
			gInventaire.fillRect(i+10, 10, itemWidth-2, itemHeight+15);
		}
		return inventaire;
	}

	/**
	 * permet de séléctionner le Truc courant
     * @return une BufferedImage qui est le dessin de la case séléctionnée
     */
	public BufferedImage selectItem(){
		BufferedImage select;
		select = new BufferedImage(75, 75, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gSelect = select.createGraphics();
		Stroke s = gSelect.getStroke();
		gSelect.setStroke(new BasicStroke(10));
		gSelect.setColor(Color.BLACK);
		gSelect.drawRect(0, 0, select.getWidth(), select.getHeight());
		gSelect.setStroke(new BasicStroke(6));
		gSelect.setColor(Color.WHITE);
		gSelect.drawRect(8, 9, select.getWidth()-16, select.getHeight()-18);
		gSelect.setColor(Color.LIGHT_GRAY);
		gSelect.fillRect(11, 12, 53, 51);
		return select;
	}

	/**
	 * permet d'accéder au nombre de clé que possède le joueur dans son inventaire
     * @return le nombre de clé
     */
	public int getNbCle(){
		return nbCle;
	}
}