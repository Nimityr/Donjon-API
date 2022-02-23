package fr.iutfbleau.projetIHM2020FI2.test;

import fr.iutfbleau.projetIHM2020FI2.API.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.Math;

/**
* Classe chargée de gérer l'inventaire du coffre et les transferts d'objets.
*/
public class Coffre {
	
	private Fenetre fen;
	private boolean open;
	private Truc selected = null;
	private int invPx, invPy,invWidth,invHeight, jPy, jHeight;
	private Point mouse;
	
	public Coffre(Fenetre fen){
		this.fen = fen;
		this.open = false;
	}
	
	/**
	 * Ouvre ou ferme l'inventaire lorsque l'on appuie sur la touche I.
     * @param e KeyEvent
     */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_I){
			open = !open;
		}
	}

	/**
	 * permet de vérifier si l'inventaire du coffre est ouvert.
     * @return true si le coffre est ouvert
     */
	public boolean isOpen(){
		return this.open;
	}
	
	/**
	 * méthode appelée dans la boucle de rendu afin de dessiner l'inventaire.
     * @param image BufferedImage sur laquelle sera dessiner l'inventaire.
     * @see fr.iutfbleau.projetIHM2020FI2.test.Fenetre.run()
     */
	public void paintUpdate(BufferedImage image){
		Graphics2D g2d = image.createGraphics();
		// la largeur du cadre blanc est égal 60% du coté le plus petit de l'image
		int width =(int) ((0.6) * ((image.getWidth()<=image.getHeight())?image.getWidth():image.getHeight()) );
		// la largeur du cadre noir est égual à 95% de la largeur du cadre blanc
		invWidth = (int)(0.95 * width);
		// hauteur du cadre blanc
		int height = (int) (invWidth/6.0 *5);		
		// coordonnée X du point supérieur gauche du cadre
		int px = (image.getWidth() - width)/2;
		// coordonnée Y du point supérieur gauche du cadre
		int py = (image.getHeight() - height)/2;
		
		// cadre de l'inventaire général
		g2d.setColor(new Color(198,198,198));
		g2d.fillRoundRect(px,py,width,height,10,10);
		
		
		
		invPx = px + (width-invWidth)/2;
		invHeight = (int)(invWidth/6.0 * 3.0);
		int gInterY = (int)((height-(invWidth/6.0 * 4.0))/2.25);
		invPy = py + gInterY;
		
		g2d.setFont(new Font("Comic Sans MS",Font.PLAIN,(int)(0.7 * gInterY)));
		g2d.setColor(Color.BLACK);
		g2d.drawString(" Coffre :",invPx,py+gInterY/2+g2d.getFontMetrics().getDescent());
		
		// dessin du cadre noir de l'inventaire du coffre
		g2d.setColor(Color.BLACK);
		g2d.fillRect(invPx,invPy,invWidth,invHeight);
   
		// dessin de l'inventaire du coffre
		int c = (int)(0.90 *invWidth/6.0);
		int interX = Math.round(0.1f*invWidth/7.0f);
		int interY = Math.round(0.1f*invHeight/4.0f);		
		Iterator<Truc> trucs = this.fen.getJoueur().getPiece().getTrucs();
		for(int j = 0; j < 3; j++){
			for(int i = 0; i < 6; i++){
				Truc next = null;
				if(trucs.hasNext()){
					next = trucs.next();
				}
				drawInventoryCase(g2d,invPx+ (i+1)*interX + i*c,invPy + (j+1)*interY + j*c,c,next);
			}
		}
		
		// dessin de l'inventaire du joueur
		g2d.setColor(Color.BLACK);
		g2d.drawString(" Joueur :",invPx,invPy + invHeight + gInterY/2 + g2d.getFontMetrics().getDescent());
		jPy = py + 2*gInterY + invHeight;
		jHeight = 2*interY+c;
		g2d.fillRect(invPx,jPy,invWidth,jHeight);
		int bpy = py + 2* gInterY + invHeight + interY; 
		trucs = this.fen.getJoueur().getTrucs();
		for(int i = 0; i < 6; i++){
			Truc next = null;
			if(trucs.hasNext()){
				next = trucs.next();
			}
			drawInventoryCase(g2d,invPx+ (i+1)*interX + i*c, bpy,c,next);
		}
		
		if(selected != null){
			g2d.drawImage(Textures.items.get(selected.getTypeTruc()), (int)mouse.getX() -c/2, (int)mouse.getY() - c/2, c, c ,null);
		}
			
	}
	
	/**
	 * Dessine une case de l'inventaire
     * @param g2d Graphics2D
     * @param x coordonée en abscisse
     * @param y coordonée en ordonnée
     * @param c ecart
     * @param t Truc à dessiner
     */
	private void drawInventoryCase(Graphics2D g2d, int x,int y,int c, Truc t){	
		g2d.setColor(Color.GRAY);	
		g2d.fillRect(x,y,c,c);
		g2d.setColor(Color.DARK_GRAY);
		int ecart = Math.round(0.05f * c);
		ecart = ((ecart%2 == 1)?ecart+1:ecart);
		g2d.fillRect(x + ecart,y + ecart, c-2*ecart, c-2*ecart);
		if(t != null && t != selected){
			g2d.drawImage(Textures.items.get(t.getTypeTruc()), x + ecart, y + ecart, c-2*ecart, c-2*ecart ,null);
		}
	}
	
	/**
	 * Détecte le glissement de la souris
     * @param e MouseEvent
     */
	public void mouseDragged(MouseEvent e){
		if(isOpen() && selected != null){
			mouse = e.getPoint();
		}
	}

	/**
	 * Invoqué lorsqu'un bouton de la souris a été enfoncé sur un composant
     * @param e MouseEvent
     */
	public void mousePressed(MouseEvent e){	
		if(isOpen()){
			if(e.getX() > invPx && e.getX() < invPx+invWidth){			
				if(e.getY() > invPy && e.getY() < invPy + invHeight){					
					int n = ((e.getX()-invPx)/(invWidth/6)+1) + ((e.getY()-invPy)/(invWidth/6)) *6;
					Iterator<Truc> trucs = this.fen.getJoueur().getPiece().getTrucs();
					selected = iteratorGet(trucs,n);
					mouse = e.getPoint();
					//System.out.println("dragged inventaire coffre  " + n);					
				}else if(e.getY() > jPy && e.getY() < jPy + jHeight){
					int n = ((e.getX()-invPx)/(invWidth/6)+1);
					Iterator<Truc> trucs = this.fen.getJoueur().getTrucs();
					selected = iteratorGet(trucs,n);
					mouse = e.getPoint();
					//System.out.println("dragged inventaire Joueur  " + n);					
				}
			}
		}
	}

	/**
	 * Invoqué lorsqu'un bouton de la souris a été laché
     * @param e MouseEvent
     */
	public void mouseReleased(MouseEvent e){
		if(isOpen() && selected != null){
			if(e.getX() > invPx && e.getX() < invPx+invWidth){	
				if(e.getY() > invPy && e.getY() < invPy + invHeight){
					if(this.fen.getJoueur().containsTruc(selected)){
						this.fen.getJoueur().removeTruc(selected);
					}
					this.fen.getJoueur().getPiece().addTruc(selected);
				}else if(e.getY() > jPy && e.getY() < jPy + jHeight){
					if(this.fen.getJoueur().getPiece().containsTruc(selected)){
						this.fen.getJoueur().getPiece().removeTruc(selected);
					}
					this.fen.getJoueur().addTruc(selected);
				}
			}
			selected = null;
		}
	}
	
	/**
	 * Obtient le n ème élément de l'iterateur
     * @param it Iterator<E>
     * @param n le n ème élément
     * @return le n ème élément
     */	
	public static <E> E iteratorGet(Iterator<E> it, int n){
		E res = null;
		while(it.hasNext() && n > 0){
			res = it.next();
			n--;
		}
		if(n > 0){
			return null;
		}
		return res;
	}
}