package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import fr.iutfbleau.projetIHM2020FI2.MNP.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

/**
* classe chargée de contenir et dessiner les différentes Indications.
* Elle permet les gestion de plusieurs de ces Indication
* @see fr.iutfbleau.projetIHM2020FI2.test.Indication
*/
public class IndicationHandler{
	
	private LinkedList<Indication> indications;
	private Fenetre fen;
	
	/**
	 * Initialise des différents attributs
     * @param fen la fenêtre
     */
	public IndicationHandler(Fenetre fen){
		this.indications = new LinkedList();
		this.fen = fen;
	}
	
	/**
	 * ajoute et active l'indication
     * @param ind l'indication à activer
     */
	public void add(Indication ind){
		ind.setActive(true);
		this.indications.add(ind);
	}
	
	/**
	 * Savoir si la LinkeList contient l'indication
	 * @param ind l'indication
     * @return true si la LinkeList contient l'indication
     */
	public boolean contains(Indication ind){
		return this.indications.contains(ind);
	}
	
	/**
	 * Désactiver l'indication
     * @param ind l'indication à désactiver
     */
	public void remove(Indication ind){
		ind.setActive(false);
		this.indications.remove(ind);
	}
	
	/**
	 * Dessine les Indications sur la fenêtre sur la fenêtre
     * @param image l'image de la fenêtre
     */
	public void drawIndications(BufferedImage image){
		Graphics2D g2d = image.createGraphics();
		int px = 20;
		int py = 40;
		int width = (int)((1/3.0)*fen.getWidth());
		int height = (int)((1/6.0)*fen.getHeight());
		if(height > width/4) height = width/4;
		if(height > 110) height = 110;
		if(width > height*5) width = height*5;
		
		int height2 = Math.round(0.90f * height);
		if((height-height2)%2 == 1) height2--;
		int inter2 = (height-height2)/2;
		int width2 = width - 2*inter2;
		
		int height3 = height2 - 2*inter2;
		int width3 = width2 - 2*inter2;
		
		int heightImg = Math.round(0.7f*height3);
		if((height3-heightImg)%2 == 1) heightImg--;
		int inter3 = (height3-heightImg)/2;
		
		int pSize = width3/18;
		if(pSize > 22) pSize=22;
		
		for(int i = 0; i < indications.size();i++){
			Indication indic = this.indications.get(i);
			
			if(indic.getIndicationUpdate() != null){
				indic.getIndicationUpdate().indicationUpdate(indic);
			}
			
			g2d.setColor(Color.BLACK);
			g2d.fillRoundRect(px,py + i*(10+height),width,height,20,20);
			
			g2d.setColor(Color.GRAY);
			g2d.fillRoundRect(px+inter2,py+inter2 + i*(10+height),width2,height2,20,20);
			
			g2d.setColor(Color.DARK_GRAY);
			g2d.fillRoundRect(px+2*inter2,py+2*inter2 + i*(10+height),width3,height3,20,20);
			
			g2d.drawImage(indic.getIcon(),px + 2*inter2 + inter3, py + 2*inter2 + inter3 + i*(10+height),heightImg,heightImg,null);
			
			g2d.setFont(new Font("Arial",Font.PLAIN,pSize));		
			g2d.setColor(indic.getColorTitle());
			g2d.drawString(indic.getTitle(),px + 2*inter2 + 2*inter3 + heightImg,py + 2*inter2 + inter3 + g2d.getFontMetrics().getAscent() + i*(10+height) );
		
			g2d.setColor(indic.getColorText());
			g2d.drawString(indic.getText(),px + 2*inter2 + 2*inter3 + heightImg,py + 2*inter2 + inter3 + g2d.getFontMetrics().getAscent()+  g2d.getFontMetrics().getHeight() + i*(10+height) );
		}
	}
	
}