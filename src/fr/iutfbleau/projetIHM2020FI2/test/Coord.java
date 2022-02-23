package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;

/**
* Classe représentant un couple de coordonné (x,y).
*/
public class Coord{
	
	private int x;
	private int y;
	
	/**
     * Constructeur.
     * Initialisation des variables
     * @param x coordonnée en x
     * @param y coordonnée en y
     */
	public Coord(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
     * @return la coordonnée x
     */
	public int getX(){
		return x;
	}
	
	/**
     * @return la coordonnée y
     */
	public int getY(){
		return y;
	}

	/**
	 * permet de modifier la coordonée x
     * @param x la coordonée en x
     */
	public void setX(int x){
		this.x = x;
	}

	/**
	 * permet de modifier la coordonée y
     * @param y la coordonée en y
     */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * permet de vérifier l'égalité d'un objet à la coordonnée (x, y)
     * @param b l'objet à comparer
     * @return vraie si les 2 objets sont identiques
     */
	public boolean equals(Object b){
		if(b instanceof Coord){
			Coord c = (Coord)b;
			if(this.x == c.getX() && this.y == c.getY()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * permet d'afficher les coordonées x et y
     * @return la chaîne de caractère qui contient les valeurs de x et y
     */
	public String toString(){
		return ("X="+x+"   Y="+y);
	}
	
}