package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Math; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Robot;
import java.awt.AWTException;

/**
* Classe chargée d'actualiser la position et la direction de la caméra
* en fonction des touches sur lesquelles l'utilisateur appuie.
* 
*/
public class Camera{
	
	/**
	* Boolean représentant les touches de contrôle de la caméra
	*/
	private boolean droite,gauche,z,q,s,d;
	
	/**
	* Cosinus de l'angle de direction de la caméra
	*/
	public double xDir;

	/**
	* Sinus de l'angle de direction de la caméra.
	*/
	public double yDir;

	/**
	* Angle de la direction de la caméra (radian).
	*/
	public double angleDir;

	/**
	* Coordonées de la caméra en X.
	*/
	public double xPos;

	/**
	* Coordonées de la caméra en Y.
	*/
	public double yPos;
	
	/**
	* Vitesse de déplacement de la caméra en case par (seconde/60)
	* value= {@value}
	*/
	private double vPos = 0.02;
	/**
	* Vitesse de rotation de la caméra en radian par (seconde/60).
	* value= {@value}
	*/
	private double vDir = 0.0547;
	
	private Fenetre fen;
	
	/**
     * Définit la position par défault du joueur ainsi que la direction de son regard
     *  @param fen référence vers Fenetre
     */
	public Camera(Fenetre fen){
		xPos = 3.5;
		yPos = 3.5;
		
		angleDir = 0;

		xDir = Math.cos(angleDir);
		yDir = Math.sin(angleDir);


		this.fen = fen;
	}
	
	/**
	 * Quand une touche est préssé, mets à true le boolean correspondant
     * @param e KeyEvent
     */
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			gauche = true;
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			droite = true;
		}else if(e.getKeyCode() == KeyEvent.VK_Z){
			z = true;
		}else if(e.getKeyCode() == KeyEvent.VK_Q){
			q = true;
		}else if(e.getKeyCode() == KeyEvent.VK_S){
			s = true;
		}else if(e.getKeyCode() == KeyEvent.VK_D){
			d = true;
		}
	}
	
	/**
	 * Quand une touche est relaché, mets à false le boolean correspondant
     * @param e KeyEvent
     */
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			gauche = false;
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			droite = false;
		}else if(e.getKeyCode() == KeyEvent.VK_Z){
			z = false;
		}else if(e.getKeyCode() == KeyEvent.VK_Q){
			q = false;
		}else if(e.getKeyCode() == KeyEvent.VK_S){
			s = false;
		}else if(e.getKeyCode() == KeyEvent.VK_D){
			d = false;
		}
	}
	
	/**
	 * Actualise la position et l'angle de la caméra.
	 * Cette fonction est appelé lors de chaque rendu.
	 * 
     * @param tDif représente l'intervalle en nano-seconde, entre le dernier rendu et le rendu en cours
     * @see fr.iutfbleau.projetIHM2020FI2.test.Fenetre.run()
     */
	public void update(long tDif){
		double t = tDif/ (1000000000/60);
		if(z){
			if(fen.getMap().getBlock((int)(xPos + xDir * (t * vPos)), (int)yPos) == 0){
				xPos += xDir * (t * vPos);
			}
			if(fen.getMap().getBlock((int)xPos,(int)(yPos + yDir * (t * vPos))) == 0){
				yPos += yDir * (t * vPos);
			}	
		}
		if(s){
			if(fen.getMap().getBlock((int)(xPos - xDir * (t * vPos)),(int)yPos) == 0){
				xPos -= xDir * (t * vPos);
			}
			if(fen.getMap().getBlock((int)xPos,(int)(yPos - yDir * (t * vPos))) == 0){
				yPos -= yDir * (t * vPos);
			}	
		}
		if(q){
			if(fen.getMap().getBlock((int)(xPos + yDir * (t * vPos)),(int)yPos) == 0){
				xPos += yDir * (t * vPos);
			}
			if(fen.getMap().getBlock((int)xPos,(int)(yPos - xDir * (t * vPos))) == 0){
				yPos -= xDir * (t * vPos);
			}	
		}
		
		if(d && fen.getMap().getBlock((int)(xPos - yDir * (t * vPos)),(int)(yPos + xDir * (t * vPos))) == 0){
			xPos -= yDir * (t * vPos);
			yPos += xDir * (t * vPos);
		}
		
		if(droite){
			angleDir = (angleDir + (t*vDir))%(2*Math.PI);
			xDir = Math.cos(angleDir);
			yDir = Math.sin(angleDir);
		}
		
		if(gauche){
			angleDir = (angleDir - (t*vDir))%(2*Math.PI);
			xDir = Math.cos(angleDir);
			yDir = Math.sin(angleDir);
		}
	}
	
}