package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import java.lang.*;
import java.util.*;
import java.awt.Stroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Font;
import java.awt.event.*;

/**
* classe chargée de gérer les action avec les portes.
*/
public class ActionDoor implements IndicationUpdate{

	private Fenetre fen;
	private boolean lookDoor;
	private int x = 0, y = 0, nbCle;
	private Passage pass;
	private Indication annonce;

	/**
     * @return true si le joueur regarde la porte.
     */
	public boolean getLookDoor(){
		return lookDoor;
	}


	/**
     * permet d'accéder au passage que le joueur regarde.
     * @return le passage que le joueur regarde
     */
	public Passage getPassage(){
		return pass;
	}
	
	/**
     * Constructeur.
     * Initialisation des variables
     * @param fen
     */
	public ActionDoor(Fenetre fen){
		this.fen = fen;
		this.lookDoor = false;
		this.annonce = new Indication(this);
		this.annonce.setColorTitle(Color.YELLOW);
		this.annonce.setColorText(Color.WHITE);
		this.annonce.setIcon(Textures.e);
	}

	/**
	 * Remplit le contenu de l'annonce
     * @param ind Indication retournée
     */
	@Override
	public void indicationUpdate(Indication ind){
		if(pass.getEtatPassage().equals(EtatPassage.LOCKED)){
			this.annonce.setTitle("La porte est verouillée");
			this.annonce.setText("Utilisez une clé");
			this.annonce.setIcon(Textures.clicDroit);
		}else{
			if(pass.getEtatPassage().equals(EtatPassage.OPEN)){
				this.annonce.setTitle("Fermer la porte");
			}else if(pass.getEtatPassage().equals(EtatPassage.CLOSED)){
				this.annonce.setTitle("Ouvrir la porte");
			}
			this.annonce.setText("appuyez sur \"E\"");
			this.annonce.setIcon(Textures.e);
		}
	}

	/**
     * @param x coordonnée en abscisse du point d'impact du rayon ayant l'angle le plus proche de la direction de la caméra
     * @param y coordonnée en ordonnée du point d'impact du rayon ayant l'angle le plus proche de la direction de la caméra
     * @return  true si le joueur regarde une porte
     */
	public boolean isWatchingDoor(int x, int y){
		
		if(this.fen.getMap().getBlock(x,y) > 1){
			lookDoor = true;
			this.x = x;
			this.y = y;
			pass = this.fen.getJoueur().getPiece().getPassage(fen.getMap().getDirection(x,y));	
			if(!annonce.isActive()){
				this.fen.getIndicationHandler().add(annonce);
			}
			return true;
		}

		lookDoor = false;
		if(annonce.isActive()){
			this.fen.getIndicationHandler().remove(annonce);
		}
		return false;
	}


	/**
	 * détection d'une touche lâchée
     * @param e KeyEvent
     */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_E && lookDoor){
			pass.agir(null);	
		}
	}
	
	/**
     * permet au joueur de se déplacer d'une pièce à une autre en passant par une porte
     * @param  cam camera
     */
	public void goInDoor(Camera cam){
		for(Coord c : fen.getMap().getDoorLocation()){
			double dist = Math.sqrt(Math.pow(Math.abs(c.getX()+0.5 - cam.xPos),2) + Math.pow(Math.abs(c.getY()+0.5 - cam.yPos),2));
			if(dist < Math.sqrt(0.34)){
				Direction dir = fen.getMap().getDirection(c.getX(),c.getY());
				Passage pass = this.fen.getJoueur().getPiece().getPassage(dir);
				if(pass != null && fen.getMap().getBlock(this.x,this.y) == 3){
					this.fen.getJoueur().setPiece(pass.getAutrePiece(this.fen.getJoueur().getPiece()));
					for(Coord c2: fen.getMap().getDoorLocation()){
						if(pass.equals(fen.getJoueur().getPiece().getPassage(fen.getMap().getDirection(c2.getX(),c2.getY())))){
							cam.yPos = c2.getY()+0.5;
							cam.xPos = c2.getX()+0.5;
							if(c2.getX()==(fen.getMap().getWidth()-1)){
								cam.xPos = c2.getX()-1;
							}else if (c2.getY()==(fen.getMap().getHeight()-1)){
								cam.yPos = c2.getY()-1;
							}else if(c2.getX()==0){
								cam.xPos = c2.getX()+1.5;
							}else if(c2.getY()==0){
								cam.yPos = c2.getY()+1.5;
							}
							break;
						}
					}
				}
				return;
			}
		}
	}
	
	
}