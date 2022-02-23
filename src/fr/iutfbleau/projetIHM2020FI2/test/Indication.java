package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import fr.iutfbleau.projetIHM2020FI2.MNP.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

/**
* Cette classe est chargée de modéliser une Indication
* Les Indications sont les cadre informatifs placés en haut à gauche de la fenetre.
*/
public class Indication{


	private Color c1;
	private String title;
	private Color c2;
	private String txt;
	private BufferedImage icon;
	private boolean active;
	private IndicationUpdate update;
	
	/**
	 * Constructeur
	 * Création d'une Indication
	 * @param colorTitle couleur du titre
	 * @param title titre de l'indication
	 * @param colorText Coueleur du corps de l'indication
	 * @param icon icone à gauche de l'indication
     */
	public Indication(Color colorTitle, String title, Color colorText, String text, BufferedImage icon){
		this.c1 = colorTitle;
		this.title = title;
		this.c2 = colorText;
		this.txt = text;
		this.icon = icon;
		this.active = false;
	}
	
	/**
	 * Constructeur
	 * Création d'une Indication
	 * @param update implémentation de l'interface IndicationUpdate dont la méthode sera appelée avant chaque dessin.
	 * @see fr.iutfbleau.projetIHM2020FI2.test.IndicationUpdate
     */
	public Indication(IndicationUpdate update){
		this.update = update;
	}
	
	/**
	 * permet d'accéder au titre de l'indication
	 * @return le titre de l'indication
     */
	public String getTitle(){
		return this.title;
	}

	/**
	 * permet d'accéder au texte de l'indication
	 * @return le texte de l'indication
     */
	public String getText(){
		return this.txt;
	}

	/**
	 * permet d'accéder à la couleur du titre de l'indication
	 * @return la couleur du titre de l'indication
     */
	public Color getColorTitle(){
		return this.c1;
	}

	/**
	 * permet d'accéder à la couleur du texte de l'indication
	 * @return le la couleur du texte de l'indication
     */
	public Color getColorText(){
		return this.c2;
	}

	/**
	 * permet d'accéder à l'icône de l'indication
	 * @return l'icône de l'indication
     */
	public BufferedImage getIcon(){
		return this.icon;
	}

	/**
	 * permet de savoir si l'indication est active
	 * @return true si l'indication est active
     */
	public boolean isActive(){
		return this.active;
	}

	/**
	 * permet d'accéder au titre de l'indication
	 * @return le titre de l'indication
     */
	public IndicationUpdate getIndicationUpdate(){
		return this.update;
	}
	
	/**
	 * permet de modifier le titre de l'indication
	 * @param title le titre de l'indication
     */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * permet de modifier le texte de l'indication
	 * @param text le texte de l'indication
     */
	public void setText(String text){
		this.txt = text;
	}

	/**
	 * permet de modifier la couleur du titre de l'indication
	 * @param c la couleur du titre de l'indication
     */
	public void setColorTitle(Color c){
		this.c1 = c;
	}

	/**
	 * permet de modifier la couleur du texte de l'indication
	 * @param c la couleur du texte de l'indication
     */
	public void setColorText(Color c){
		this.c2 = c;
	}

	/**
	 * permet de modifier l'icône de l'indication
	 * @param icon l'icône de l'indication
     */
	public void setIcon(BufferedImage icon){
		this.icon = icon;
	}

	/**
	 * permet de modifier l'activation de l'indication
	 * @param active l'activation de l'indication
     */
	public void setActive(boolean active){
		this.active = active;
	}

	/**
	 * permet de modifier IndicationUpdate
	 * @param update l'IndicationUpdate
     */
	public void setIndicationUpdate(IndicationUpdate update){
		this.update = update;
	}
	
}