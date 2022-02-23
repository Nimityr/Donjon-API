package fr.iutfbleau.projetIHM2020FI2.test;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.lang.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;
import java.awt.Dimension;
/**
* C'est la classe centrale de l'interface graphique. Elle contient les différents objets de l'interface avec leur accesseurs ainsi que la boucle de rendu
*
*/
public class Fenetre extends JFrame implements Runnable{
	/**
	*	cette image est affiché sur la fenetre à intervale régulier
	*/
	private BufferedImage image;
	private Camera camera;
	private Map map;
	private Joueur j;
	private ActionDoor aDoor; 
	private Coffre coffre;
	private MiniMap minimap;	
	private InventaireSac inventaire;
	private IndicationHandler indHandler;
	private boolean run;
	private Menu menu;
	private Controller controller;
	
	/**
	 * Charge les textures, initialise les différents attributs et lance la boucle de rendu.
	 * 
     * @param j le joueur
     */
	public Fenetre(Joueur j){
		super("Jeu_Projet_IHM");
		this.setSize(800, 600);
		this.setMinimumSize(new Dimension(424,292));
		this.setLocationRelativeTo(null);
		
		Textures.load();
		this.j = j;
		
		this.map = new Map(this);	
		this.inventaire = new InventaireSac(this, this.j);
		this.aDoor = new ActionDoor(this);		
		this.camera = new Camera(this);
		this.coffre = new Coffre(this);
		this.minimap = new MiniMap(this);
		this.indHandler = new IndicationHandler(this);
		this.menu = new Menu(this);
		this.controller = new Controller(this);
		
		this.addMouseListener(this.controller);
		this.addKeyListener(this.controller);
		this.addMouseWheelListener(this.controller);
		this.addMouseMotionListener(this.controller);
		
		this.image =  new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);
		this.setVisible(true);
	
		System.out.println(	"Thread courrant = " + Thread.currentThread());		
		this.run();
	}

	/**
	 * met à jour l'écran de manière fluide (amélioration de l'apparence du jeu)
     */
	public void render() {
		if(this.getBufferStrategy() == null){
			this.createBufferStrategy(3);
		}
		BufferStrategy bs = this.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(this.image, 0, 0, this.image.getWidth(), this.image.getHeight(), null);
		bs.show();
	}	
	
	/**
	*  lance la boucle de rendu.
	*  Cette méthode actualise l'image et tente d'appeller la fonction render() 60 fois par seconde.
	*
	*  @see render()
	*  
    */
	public void run(){
		run = true;
		this.setFocusable(true);
		long temps = System.nanoTime();
		long tDif;
		long ns = 1000000000/60;
		while(run){
			tDif = System.nanoTime()-temps;		
			if(tDif >= ns){			
				temps = System.nanoTime();

				if(this.image.getWidth() != this.getWidth() || this.image.getHeight() != this.getHeight()){
					this.image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
				}
				if(coffre.isOpen()){
					this.map.PaintUpdate(this.image);
					this.indHandler.drawIndications(this.image);
					this.minimap.drawMiniMap(this.image);
					this.coffre.paintUpdate(this.image);
				}else{
					this.camera.update(tDif);
					this.aDoor.goInDoor(this.camera);
					this.map.PaintUpdate(this.image);
					this.indHandler.drawIndications(this.image);
					this.inventaire.drawInventaire(this.image);
					this.minimap.drawMiniMap(this.image);
					this.menu.drawMenu(this.image);
				}
				this.render();	
			}			
		}
	}
	
	/**
	 * permet d'arrêter la boucle de rendu
	 * 
     */
	public void stop(){
		run = false;
	}
	
	/**
	 * permet d'accéder à la camera
	 * @return Camera
     */
	public Camera getCamera(){
		return this.camera;
	}

	/**
	 * permet d'accéder au joueur
	 * @return le joueur
     */
	public Joueur getJoueur(){
		return this.j;
	}

	/**
	 * permet d'accéder à la map
	 * @return la map
     */
	public Map getMap(){
		return this.map;
	}

	/**
	 * permet d'accéder à l'objet ActionDoor
	 * @return l'ActionDoor
     */
	public ActionDoor getActionDoor(){
		return this.aDoor;
	}

	/**
	 * permet d'accéder au coffre
	 * @return le coffre
     */
	public Coffre getCoffre(){
		return this.coffre;
	}

	/**
	 * permet d'accéder à IndicationHandler
	 * @return l'IndicationHandler
     */
	public IndicationHandler getIndicationHandler(){
		return this.indHandler;
	}

	/**
	 * permet d'accéder à l'inventaire du joueur
	 * @return l'inventaire du joueur
     */
	public InventaireSac getInventaireSac(){
		return this.inventaire;
	}

	/**
	 * permet d'accéder au menu
	 * @return le menu
     */
	public Menu getMenu(){
		return this.menu;
	}

}