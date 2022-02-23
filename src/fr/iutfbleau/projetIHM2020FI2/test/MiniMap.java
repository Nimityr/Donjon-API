package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import java.awt.Stroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.BasicStroke;

/**
* Classe chargée de gérer la miniMap
*/
public class MiniMap{
	
	private Fenetre fen;
	private HashMap<Piece,Coord> position;
	private int nbPieceH,nbPieceV;
	
	/**
     * Initialise les différents attributs et lance l'analyse du monde.
     * @param fen la fenêtre principale
     */
	public MiniMap(Fenetre fen){
		this.fen = fen;
		this.position = new HashMap();
		analyse();
		CoordProcessing();
	}
	
	/**
	* modifie les coordonées des pièces de sorte qu'elle soit toutes positive.
	* récupère le nombre maximum de pièces alignés verticalement et horizontalement.
	*/
	private void CoordProcessing(){
		int minX=0,minY=0,maxX=0,maxY=0;
		for(Piece p : position.keySet()){
			Coord c = position.get(p);
			if(c.getX() > maxX){
				maxX = c.getX();
			}
			if(c.getY() > maxY){
				maxY = c.getY();
			}
			if(c.getX() < minX){
				minX = c.getX();
			}
			if(c.getY() < minY){
				minY = c.getY();
			}
		}		
		int addX= Math.abs(minX);
		int addY = Math.abs(minY);	
		
		for(Piece p : position.keySet()){
			Coord c = position.get(p);
			c.setX(c.getX()+addX);
			c.setY(c.getY()+addY);
		}
		nbPieceH = maxX+addX+1;
		nbPieceV = maxY+addY+1;
	}
	
	/**
    * Attribution des coordonnées à chaque pièce en commencant par c'elle ou est placé le joueur.
    * elle sera de coordonnée (0,0)
    */
	private void analyse(){
		ArrayList<Piece> visited = new ArrayList();
		LinkedList<Piece> openList = new LinkedList();
		
		openList.add(this.fen.getJoueur().getPiece());
		position.put(openList.peek(), new Coord(0,0));
		visited.add(this.fen.getJoueur().getPiece());
		
		while(!openList.isEmpty()){
			Piece p = openList.poll();
			Coord pc = position.get(p);
			
			Passage pass = null;
			if((pass = p.getPassage(Direction.NORD)) != null){
				Piece voisin = pass.getAutrePiece(p);
				if(!visited.contains(voisin)){
					openList.add(voisin);
					position.put(voisin,new Coord(pc.getX(),pc.getY()-1));
					visited.add(voisin);
				}
			}
			if((pass = p.getPassage(Direction.SUD)) != null){
				Piece voisin = pass.getAutrePiece(p);
				if(!visited.contains(voisin)){
					openList.add(voisin);
					position.put(voisin,new Coord(pc.getX(),pc.getY()+1));
					visited.add(voisin);
				}
			}
			if((pass = p.getPassage(Direction.OUEST)) != null){
				Piece voisin = pass.getAutrePiece(p);
				if(!visited.contains(voisin)){
					openList.add(voisin);
					position.put(voisin,new Coord(pc.getX()-1,pc.getY()));
					visited.add(voisin);
				}
			}
			if((pass = p.getPassage(Direction.EST)) != null){
				Piece voisin = pass.getAutrePiece(p);
				if(!visited.contains(voisin)){
					openList.add(voisin);
					position.put(voisin,new Coord(pc.getX()+1,pc.getY()));
					visited.add(voisin);
				}
			}
		}
	}
	
	/**
     * Dessine la mini-map
     * @param image image sur laquelle on dessine
     */
	public void drawMiniMap(BufferedImage image){
		Graphics2D g2d = image.createGraphics();
		
		int cWidth = 300;
		int cHeight = 300;
		if((1/4.0f)*image.getWidth() <= (1/4.0f)*image.getHeight() && (1/4.0f)*image.getWidth() < cWidth){
			cWidth = Math.round((1/4.0f)*image.getWidth());
			cHeight = Math.round((1/4.0f)*image.getWidth());
		}else if((1/4.0f)*image.getHeight() < (1/4.0f)*image.getWidth() && (1/4.0f)*image.getHeight() < cWidth ){
			cWidth = Math.round((1/4.0f)*image.getHeight());
			cHeight = Math.round((1/4.0f)*image.getHeight());
		}
		int cpx = image.getWidth() - cWidth - 20;
		int cpy = 40;
		
		g2d.setColor(Color.BLUE);
		g2d.fillRect(cpx,cpy,cWidth,cHeight);
		
		int mWidth = Math.round(0.95f * cWidth);
		int mHeight = Math.round(0.95f * cHeight);
		int mpx = (cWidth-mWidth)/2 + cpx;
		int mpy = (cHeight-mHeight)/2 + cpy;
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(mpx,mpy,mWidth,mHeight);
		
		int cote = 0;
		if(0.90f*mWidth/nbPieceH < 0.90f*mHeight/nbPieceV){
			cote = Math.round(0.90f*mWidth/nbPieceH);
		}else{
			cote = Math.round(0.90f*mHeight/nbPieceV);
		}
		int interX = (int)Math.round((mWidth-cote*nbPieceH)/(nbPieceH+1.0));
		int interY = (int)Math.round((mHeight-cote*nbPieceV)/(nbPieceV+1.0));
		int coteInterieur = (int)(0.95f * cote);
		if((cote-coteInterieur)%2 == 1){
			coteInterieur--;
		}
		int mure = (cote-coteInterieur)/2;
		for(Piece p : position.keySet()){
			Coord c = position.get(p);
			int px = mpx + c.getX()*(cote+interX)+interX;
			int py = mpy + c.getY()*(cote+interY)+interY;
			if(this.fen.getJoueur().isVisited(p)){
				g2d.setColor(Color.BLACK);
				g2d.fillRect(px,py,cote,cote);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.fillRect((px=px+mure),(py=py+mure),coteInterieur,coteInterieur);
			}
			if(this.fen.getJoueur().getPiece().equals(p)){
				g2d.setColor(Color.RED);
				int diametre = (int)((coteInterieur/(this.fen.getMap().getWidth()-2)));
				g2d.fillOval(
					(int)(px + Math.round((this.fen.getCamera().xPos-1) * (coteInterieur/(this.fen.getMap().getWidth()-2))))-diametre/2,
					(int)(py + Math.round((this.fen.getCamera().yPos-1) * (coteInterieur/(this.fen.getMap().getHeight()-2))))-diametre/2,
					diametre,
					diametre
				);
			}
		}
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Comic Sans MS",Font.BOLD,(int)(4 * (mpy-cpy))));
		g2d.drawString("N",cpx + (cWidth - g2d.getFontMetrics().charWidth('N'))/2, cpy + g2d.getFontMetrics().getDescent() );
		g2d.drawString("S",cpx + (cWidth - g2d.getFontMetrics().charWidth('N'))/2, cpy + cHeight);
		
		g2d.drawString("O",cpx - g2d.getFontMetrics().charWidth('O')/2 , cpy + cHeight/2 );
		g2d.drawString("E",cpx +cWidth - g2d.getFontMetrics().charWidth('O')/2 , cpy + cHeight/2 );
	}
}