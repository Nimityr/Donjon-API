package fr.iutfbleau.projetIHM2020FI2.MP;
import fr.iutfbleau.projetIHM2020FI2.API.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 * Implémentation non persistante d'un Joueur
 */
public class JoueurP extends ContientTrucsP implements Joueur{

    //piece actuelle
    private Piece p;

    // conteneur de Pièces.
    private List<Piece> cerveau;
    
    /**
    * Constructeur
    * 
    * On utilise un ensemble LinkedList pour gérer la chronologie.
    *
    * Le joueur n'est pas dans une pièce à sa création.
    * @see setPiece() 
    */
    public JoueurP(PassagePieceFactory ppf, TrucFactory tf){
        super();
        this.cerveau = new LinkedList<Piece>();
		ConnexionBDD.init();
		ConnexionBDD.createTable();
		int retour = 0;
		if(this.hasPartie()){
			retour = JOptionPane.showConfirmDialog(null, "Souhaitez-vous reprendre votre partie", "Reprise", JOptionPane.YES_NO_OPTION);
		}
		if(retour == 0){
			this.loadPartie(ppf,tf);
		}else{
			ConnexionBDD.clear();
		}
   }

	public boolean hasPartie(){
		try{
			PreparedStatement pst = ConnexionBDD.cnx.prepareStatement("SELECT COUNT(id) FROM Pieces");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				if(rs.getInt(1) > 0){
					return true;
				}
			}
		}catch(SQLException e){
			System.out.println("Echec de la connexion !" + e.getMessage());
		}
		return false;
	}
	
	public void loadPartie(PassagePieceFactory ppf, TrucFactory tf){
		System.out.println("chargement de la partie");
		ArrayList<Piece> pieces = new ArrayList();
		try{
			PreparedStatement pst = ConnexionBDD.cnx.prepareStatement("SELECT * FROM Pieces ORDER BY id ASC");
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Piece p = ppf.newPiece();
				pieces.add(p);
				if(rs.getBoolean(2)){
					this.cerveau.add(p);
				}
				if(rs.getBoolean(3)){
					this.p = p;
				}
			}
			
			pst = ConnexionBDD.cnx.prepareStatement("SELECT * FROM Passages ORDER BY id ASC");
			rs = pst.executeQuery();
			while(rs.next()){
				Passage pass = ppf.newPassage(Direction.valueOf(rs.getString(2)), pieces.get(rs.getInt(3)), Direction.valueOf(rs.getString(4)), pieces.get(rs.getInt(5)));
				pass.setEtatPassage(EtatPassage.valueOf(rs.getString(6)));
			}
			
			pst = ConnexionBDD.cnx.prepareStatement("SELECT * FROM Trucs");
			rs = pst.executeQuery();
			while(rs.next()){
				if(rs.getInt(1) == -1){
					super.addTruc(tf.newTruc(TypeTruc.valueOf(rs.getString(2)),rs.getString(3)));
				}else{
					((PieceP)pieces.get(rs.getInt(1))).addTrucMP(tf.newTruc(TypeTruc.valueOf(rs.getString(2)),rs.getString(3)));
				}
			}
			
		}catch(SQLException e){
			System.out.println("Echec de la connexion !" + e.getMessage());
		}
	}
	
	@Override
    public boolean addTruc(Truc t){
    	if(super.addTruc(t)){
			try{
				PreparedStatement pst = ConnexionBDD.cnx.prepareStatement("INSERT INTO Trucs (contientTruc_id,typeTruc,description) VALUES (?,?,?)");
				pst.setInt(1,-1);
				pst.setString(2,t.getTypeTruc().name());
				pst.setString(3,t.getDescription());
				pst.executeQuery();
			}catch(SQLException e){
				System.out.println("Echec de la connexion !" + e.getMessage());
			}
			return true;
		}	
		return false;
    }
	
	@Override
    public boolean removeTruc(Truc t){
    	if(super.removeTruc(t)){
			try{
				PreparedStatement pst = ConnexionBDD.cnx.prepareStatement("DELETE FROM Trucs WHERE contientTruc_id=? AND typeTruc=? AND description=? LIMIT 1");
				pst.setInt(1,-1);
				pst.setString(2,t.getTypeTruc().name());
				pst.setString(3,t.getDescription());
				pst.executeQuery();
			}catch(SQLException e){
				System.out.println("Echec de la connexion !" + e.getMessage());
			}
			return true;
		}
		return false;
    }
	
	
   /** 
     * @return la piece dans laquelle le joueur se trouve
     * (null si la pièce n'est pas renseignée)
    */
   @Override
    public Piece getPiece(){
    	return this.p;
    }
	
	/** 
     * Met à jour la piece dans laquelle le joueur se trouve
     * Demande l'ajout de l'ancienne pièce au "cerveau".
     * NB. il n'y a pas de vérification dans Joueur.
    */
   @Override
    public void setPiece(Piece next){
    	if (this.getPiece() != null) {
            this.addVisited(this.getPiece());
		}
    	this.p=next;
		try{
			PreparedStatement pst = ConnexionBDD.cnx.prepareStatement("UPDATE Pieces SET joueur=? WHERE id=?");
			pst.setBoolean(1,true);
			pst.setInt(2,((PieceP)this.p).getId());
			pst.executeQuery();
		}catch(SQLException err){
			System.out.println("Echec de la connexion !" + err.getMessage());
		}
    }
    
    /**
     * @return les pieces que le joueur a deja visité.
     */
    @Override
    public Iterator<Piece> getVisited(){
    	return this.cerveau.iterator();
    }
    
    /**
     * Ajoute la Piece si nécessaire (si l'itérateur retourné par 
     * getVisited ne permet pas d'itérer sur la Piece).
     * Une pièce devient visitée quand on la quitte.
     * @param  p Pièce qu'on vient de (re)visiter
     * @throws NullPointerException la Piece ne peut pas être null
     * @return vrai si il fallait l'ajouter et faux sinon.
     */
    @Override
    public boolean addVisited(Piece p){
    	Objects.requireNonNull(p,"On ne peut pas ajouter une pièce null.");
		try{
			PreparedStatement pst = ConnexionBDD.cnx.prepareStatement("UPDATE Pieces SET visited=?, joueur=? WHERE id=?");
			pst.setBoolean(1,true);
			pst.setBoolean(2,false);
			pst.setInt(3,((PieceP)p).getId());
			pst.executeQuery();
		}catch(SQLException err){
			System.out.println("Echec de la connexion !" + err.getMessage());
		}
    	return this.cerveau.add(p);
    }
    

    /**
 	* Teste si piece a été visitée
 	* @param  p pièce
 	* @throws NullPointerException si la pièce est null 
 	* @return vrai si la pièce est connue.
 	*/
    @Override
    public boolean isVisited(Piece p){
    	Objects.requireNonNull(p,"On ne peut pas connaître une pièce null.");
    	return this.cerveau.contains(p);
    }
	
	/**
     * Accesseur pour la description textuelle du sac à dos.
     * @return description
     */
    @Override
    public String getDescription(){
        // Stringbuilder is the most efficient method of building a String like datastructure incrementally. 
        StringBuilder sb = new StringBuilder("");
        int goodies = super.combienTrucs();
        if (goodies == 0)
        	sb.append("\n"+"Le sac à dos ne contient pas d'objet. ");
        else if (goodies == 1)	
        	sb.append("\n"+"Le sac à dos contient un objet : ");
        else sb.append("\n"+ "Le sac à dos contient "+ super.combienTrucs()+" objets : ");
        Iterator<Truc> goods = super.getTrucs();
        while (goods.hasNext()){
        	Truc t = goods.next();
        	sb.append("\n _ " + t.getDescription() +".");
        }
        sb.append("\n");
        return sb.toString();
    }
    /**
     * Le joueur non persistent n'est pas tout à fait comme le buveur du petit prince.
     * Il ne boit pas du rhum pour oublier qu'il en boit mais pour oublier les pièces qu'il connaît.
     * @param  t un truc que le joueur doit posséder
     * @throws IllegalStateException  sinon
     * @return vrai si l'objet a un effet sur le joueur
     */
    @Override
    public boolean agir (Truc t){
    	if (t == null){
    		return false;
    	}
    	else if (!super.containsTruc(t))
    		throw new IllegalStateException("le joueur ne porte pas l'objet");
        else if (Objects.equals(t.getTypeTruc(),TypeTruc.ALCOOL)){
        	this.removeTruc(t);
        	this.cerveau.clear();
			try{
				PreparedStatement pst = ConnexionBDD.cnx.prepareStatement("UPDATE Pieces SET visited=?");
				pst.setBoolean(1,false);
				pst.executeQuery();
			}catch(SQLException err){
				System.out.println("Echec de la connexion !" + err.getMessage());
			}
        	return true;
        }
        else if (Objects.equals(t.getTypeTruc(),TypeTruc.EAU)){
        	this.removeTruc(t);
        	return true;
        }
        else return false;
    }
}
