package fr.iutfbleau.projetIHM2020FI2.test;
import java.awt.event.*;

/**
* classe représentant le controlleur.
* cette classe implémente tous les Listener utilisé dans le projet.
* lorsqu'un event est appellé, le controlleur appelle les différentes méthodes corespondante du projet
* 
*/
public class Controller implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener{
	
	private Fenetre fen;
	public Controller(Fenetre fen){
		this.fen = fen;
	}
	
	// keyListener
	@Override
	public void keyPressed(KeyEvent e){
		this.fen.getCamera().keyPressed(e);
	}
	
	@Override
	public void keyTyped(KeyEvent arg0){}
	
	@Override
	public void keyReleased(KeyEvent e){
		this.fen.getCamera().keyReleased(e);
		this.fen.getActionDoor().keyReleased(e);
		this.fen.getCoffre().keyReleased(e);
		this.fen.getMenu().keyReleased(e);
	}
	
	
	//MouseMotionListener
	@Override
	public void mouseDragged(MouseEvent e){
		this.fen.getCoffre().mouseDragged(e);
	}
	
	@Override
	public void mouseMoved(MouseEvent e){}
	
	@Override
	public void mouseClicked(MouseEvent e){}
	
	@Override
	public void mouseEntered(MouseEvent e){}
	
	@Override
	public void mouseExited(MouseEvent e){}
	
	
	//MouseListener
	@Override
	public void mousePressed(MouseEvent e){
		this.fen.getCoffre().mousePressed(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		this.fen.getInventaireSac().mouseReleased(e);
		this.fen.getCoffre().mouseReleased(e);
	}
	
	
	//MouseWheelListener
	public void mouseWheelMoved(MouseWheelEvent e){
		this.fen.getInventaireSac().mouseWheelMoved(e);
	}
}