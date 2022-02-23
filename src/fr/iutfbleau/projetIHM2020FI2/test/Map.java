package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.lang.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;

/**
* Classe chargée de gérer l'affichage en 3D d'une pièce. Un algorithme de rayCasting est utilisé. <br/>
* Cet algorithme consiste à projeter un plan 2D en 3D. Cela est fait lançant
* des rayons à partir de la caméra sur 60°(angle de vision du joueur) puis en calculant le point d'impact du rayon avec un mure,
* on en déduit sa distance avec la caméra, permettant ensuite de calculer la taille du mure dans l'affichage.
* chaque rayon correspond à une colonne de pixels. Donc, pour une fenetre de 1920 pixels de largeur, 1920 rayons sont lancés ce qui peux faire lagger. <br/>
* plus d'info sur <a href="http://forums.mediabox.fr/wiki/tutoriaux/flashplatform/affichage/3d/raycasting/theorie/01-introduction">ce site</a>.
*/
public class Map{
	/**
	* Liste contenant les textures de chaque objet pouvant apparaitre sur la map
	*/
	private ArrayList<BufferedImage> textures;
	/**
	* représente le plan 2D de la map
	*/
	private int[][] map = 
	{
		{1,1,1,2,1,1,1},
		{1,0,0,0,0,0,1},
		{1,0,0,0,0,0,1},
		{2,0,0,0,0,0,2},
		{1,0,0,0,0,0,1},
		{1,0,0,0,0,0,1},
		{1,1,1,2,1,1,1}		
	};
	private int mapWidth = map[0].length;
	private int mapHeight = map.length;
	private Coord[] doorLocation;
	private Fenetre fen;
	
	/**
	 * Initialise les attributs de la classe
     * @param fen la fenetre
     */
	public Map(Fenetre fen){	
		this.fen = fen;	
		
		// enregistrement les emplacements potentiel de porte.
		this.doorLocation = new Coord[4];
		int count = 0;
		for(int x = 0; x < mapHeight;x++){
			for(int y = 0; y < mapWidth; y++){
				if(map[x][y] == 2){
					this.doorLocation[count++] = new Coord(x,y);
				}
			}
		}
		// chargement des textures
		this.textures = new ArrayList<BufferedImage>();
		this.textures.add(Textures.wall);
		this.textures.add(Textures.door);
		this.textures.add(Textures.openDoor);
				
	}
	
	/**
	 * permet d'accéder à la largeur de la map
	 * @return la largeur de la map
     */
	public int getWidth(){
		return this.mapHeight;
	}
	
	/**
	 * permet d'accéder à la hauteur de la map
	 * @return la hauteur de la map
     */
	public int getHeight(){
		return this.mapWidth;
	}
	
	/**
	 * permet d'accéder à la map
	 * @return le tableau bidimensionnel map
     */
	public int[][] getMap(){
		return this.map;
	}
	
	/**
	 * permet d'accéder à un block de la map selon les coordonnées x et y
	 *		0: vide
	 *		1: mure
	 *		2: mure avec porte fermé
	 *		3: mure avec porte ouverte
	 *
	 *
	 * @param x la coordonnée x
	 * @param y la coordonnée y
	 * @return le type du block
	 * @see getBlock(double x, double y)
     */
	public int getBlock(int x, int y){
		return this.map[x][y];
	}
	
	/**
	 * permet d'accéder à un block de la map selon les coordonnées x et y
	 *		0: vide
	 *		1: mure
	 *		2: mure avec porte fermé
	 *		3: mure avec porte ouverte
	 *
	 *
	 * @param x la coordonnée x
	 * @param y la coordonnée y
	 * @return le type du block
	 * @see getBlock(int x, int y)
     */
	public int getBlock(double x, double y){
		return this.map[(int)x][(int)y];
	}
	
	/**
	 * permet de modifier le type d'un block de la map selon les coordonnées x et y
	 * @param x la coordonnée x
	 * @param y la coordonnée y
	 * @param value la nouveau type du block
     */
	public void setBlock(int x, int y , int value){
		this.map[x][y] = value;
	}

	/**
	 * retourne les coordonnées x et y de toutes les portes de la pièce
	 * @return Coord[] doorLocations
     */
	public Coord[] getDoorLocation(){
		return this.doorLocation;
	}

	/**
	 * algorithme de rayCasting permettant de dessiner la map en 3D.
	 * @param image sur laquelle sera dessiné la map
     */	
	public void PaintUpdate(BufferedImage image){
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0,0,image.getWidth(),image.getHeight()/2);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0,image.getHeight()/2,image.getWidth(),image.getHeight());

		double distCameraEcran = (image.getWidth()/(2*Math.tan(Math.PI/6)));
		
		g2d.setColor(Color.WHITE);

		double xRayDir;
		double yRayDir;
		
		pieceUpdate();
		//	boucle de lancement des rayons	
		int count = 0;
		for(double i = (this.fen.getCamera().angleDir - Math.PI/6); i < (this.fen.getCamera().angleDir + Math.PI/6);i+=Math.PI/(3 * image.getWidth())){
			xRayDir = Math.cos(i);
			yRayDir = Math.sin(i);
			int offset;

			// gestion des intersection  horizontales
			double Xa = 0;
			double Xp = 1/yRayDir;
			if(yRayDir > 0){
				Xa = ( ((int)this.fen.getCamera().yPos)+1-this.fen.getCamera().yPos)/yRayDir;
			}else{
				Xa = ( ((int)this.fen.getCamera().yPos) -this.fen.getCamera().yPos)/yRayDir;
				Xp = -Xp;			
			}
			
			double x1 = this.fen.getCamera().xPos + xRayDir * Xa;
			double y1 = this.fen.getCamera().yPos + yRayDir * Xa;
			while((x1+ 0.01 * xRayDir) >= 0 && (y1+ 0.01 * yRayDir) >= 0 && (x1+ 0.01 * xRayDir) < mapWidth && (y1+ 0.01 * yRayDir) < mapHeight && this.map[(int)(x1 + 0.01 * xRayDir)][(int)(y1 + 0.01 * yRayDir)] == 0){			
				Xa += Xp;
				x1 = this.fen.getCamera().xPos + xRayDir * Xa;
				y1 = this.fen.getCamera().yPos + yRayDir * Xa;			
			}
			
			// gestion des intersections verticales
			Xa = 0;
			Xp = 1/xRayDir;
			if(xRayDir > 0){
				Xa = ( ((int)this.fen.getCamera().xPos)+1-this.fen.getCamera().xPos)/xRayDir;
			}else{
				Xa = ( ((int)this.fen.getCamera().xPos) -this.fen.getCamera().xPos)/xRayDir;	
				Xp = -Xp;			
			}
			double x2= this.fen.getCamera().xPos + xRayDir * Xa;
			double y2= this.fen.getCamera().yPos + yRayDir * Xa;

			while((x2+ 0.01 * xRayDir) >= 0 && (y2+ 0.01 * yRayDir) >= 0 && (x2+ 0.01 * xRayDir) < mapWidth && (y2+ 0.01 * yRayDir) < mapHeight && this.map[(int)(x2 + 0.01 * xRayDir)][(int)(y2 + 0.01 * yRayDir)] == 0){	
				Xa += Xp;
				x2 = this.fen.getCamera().xPos + xRayDir * Xa;
				y2 = this.fen.getCamera().yPos + yRayDir * Xa;	
			}
			
			// calcule et selection de la plus petite distance
			double x;
			double y;

			double dist = 0;
			int type;
			if(Point2D.distance(this.fen.getCamera().xPos, this.fen.getCamera().yPos,x1,y1) < Point2D.distance(this.fen.getCamera().xPos, this.fen.getCamera().yPos,x2,y2)){
				dist = (Math.sqrt(Math.pow(Math.abs(this.fen.getCamera().xPos-x1)*64,2)  +  Math.pow(Math.abs(this.fen.getCamera().yPos-y1)*64,2)));
				offset = (int)((x1-((int)x1))/(1.0/64.0));
				type =  this.map[(int)(x1 + 0.01 * xRayDir)][(int)(y1 + 0.01 * yRayDir)];
				x = x1;
				y = y1;
			}else{
				dist = (Math.sqrt(Math.pow(Math.abs(this.fen.getCamera().xPos-x2)*64,2)  +  Math.pow(Math.abs(this.fen.getCamera().yPos-y2)*64,2)));
				offset = (int)((y2-((int)y2))/(1.0/64.0));
				type = this.map[(int)(x2 + 0.01 * xRayDir)][(int)(y2 + 0.01 * yRayDir)];
				x=x2;
				y=y2;
			}

			// calcule de la taille et de l'emplacement de la colonne de pixel

			int taille =  (int)Math.round(distCameraEcran*64/(dist * Math.cos(Math.abs(i-this.fen.getCamera().angleDir))));
			int inter = (image.getHeight() - taille)/2;

			g2d.drawImage(this.textures.get(type-1),count,inter, count+1 ,inter+taille,offset,0,offset+1,64,null);
			count++;

			
			// selectionne le rayon avec l'angle le plus proche de celui de la direction de la caméra et regarde s'il entre en collision avec une porte
			if(Math.abs(i - this.fen.getCamera().angleDir) <= Math.PI/(3 * image.getWidth())/2){
				this.fen.getActionDoor().isWatchingDoor((int)(x+ 0.01 * xRayDir), (int)(y+ 0.01 * yRayDir ));
			}
		}
	}
	
	/**
	 * actualise la disposition des portes dans la pièce
	 * en récupérant la pièce dans la quelle est le joueur dans le modèle.
     */
	public void pieceUpdate(){	
		for(int i = 0; i < doorLocation.length;i++){
			Passage pass = this.fen.getJoueur().getPiece().getPassage(getDirection(doorLocation[i].getX(),doorLocation[i].getY()));
			if(pass == null){
				this.map[doorLocation[i].getX()][doorLocation[i].getY()] = 1;
			}else if(pass.getEtatPassage() == EtatPassage.OPEN){
				this.map[doorLocation[i].getX()][doorLocation[i].getY()] = 3;
			}else{
				this.map[doorLocation[i].getX()][doorLocation[i].getY()] = 2;
			}
		}	
	}

	/**
	 * retourne une Direction en fonction des coordonnées fournis en paramètre.
	 * @param x la coordonnée x
	 * @param y la coordonnée y
	 * @return la direction du joueur selon les coordonnées x et y
     */
	public Direction getDirection(int x, int y){
		if(y == 0){
			return Direction.NORD;
		}else if(x == getWidth()-1){
			return Direction.EST;
		}else if(x == 0){
			return Direction.OUEST;
		}else if(y == getHeight()-1){
			return Direction.SUD;
		}
		return null;
	}
	
}