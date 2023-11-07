package capacite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import interfaces.ICapacite;
import interfaces.ICategorie;
import interfaces.IType;
import pokedex.Categorie;
import pokedex.Type;

/**
 * @author Royer Baptiste et Castillejos Sacha
 * 
 * Une capacité est un type d'attaque que le pokemon peut utilser.
 * Cette classe implémente l'interface ICapacite.
 * 
 */
public class Capacite extends Attaque implements ICapacite{
	
	/**
	 * Le nom de la capacité.
	 */
    private String nom;
    /**
     * La précision avec laquel la capacité touche.
     */
    private double precision;
    /**
     * La puissance de la capacité.
     */
    private int puissance;
    /**
     * Le nombre de PP définit le nombre de fois ou la capacité peut être utilisé.
     */
    private int pp;
    /**
     * Le numéro de la capacité.
     */
    private int numCapacite;
    /**
     * La categorie de la capacité.
     */
	private ICategorie categorie;
	/**
	 * Le type de la capacité.
	 */
    private IType type;
    /**
     * le nombre de PP d'origine de la capacité.
     */
    private int ppMax;
    
    /**
     * Constructeur de la classe Capacite utilisant un String.
     *
     * @param nomCapacite est le nom de la capacité à créer.
     */
    public Capacite(String nomCapacite){
        File fichier = new File("docs/listeCapacites.csv");
		Scanner scan;
        try {
            scan = new Scanner(fichier).useDelimiter(";");
            scan.nextLine();
            while(scan.hasNextLine() == true){
                if(scan.next().equals(nomCapacite)) {
                    this.nom = nomCapacite; 
                    this.puissance = Integer.parseInt(scan.next());
                    this.precision = Double.parseDouble(scan.next());
                    this.pp = Integer.parseInt(scan.next());
                    this.ppMax = this.pp;
                    this.numCapacite = Integer.parseInt(scan.next());
                    this.categorie = new Categorie(scan.next());
                    this.type = new Type(scan.next());

                }
                scan.nextLine();
            }
            scan.close();
        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Constructeur de la classe Capacite utilisant un int.
     *
     * @param numeroCapacite est le numéro de la capacité à créer.
     */
    public Capacite(int numeroCapacite){
        File fichier = new File("docs/listeCapacites.csv");
		Scanner scan;
		int numligne = 0;

        try {
            scan = new Scanner(fichier).useDelimiter(";");

            while(numligne != numeroCapacite) {
                scan.nextLine();
                numligne++;
            }
            
            this.nom = scan.next(); 
            this.puissance = Integer.parseInt(scan.next());
            this.precision = Double.parseDouble(scan.next());
            this.pp = Integer.parseInt(scan.next());
            this.ppMax=this.pp;
            this.numCapacite = Integer.parseInt(scan.next());
            this.categorie = new Categorie(scan.next());
            this.type = new Type(scan.next());	
            scan.close();	
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *Retourne le nom de la capacité.
     *
     * @return le nom de la capacité.
     */
    public String getNom(){
        return this.nom;
    }

    /**
    *Retourne la précision de la capacité.
    *
    * @return la précision de la capacité.
    */
	public double getPrecision(){
        return this.precision;
    }

    /**
    *Retourne la puissance de la capacité.
    *
    * @return la puissance de la capacité.
    */
	public int getPuissance(){
        return this.puissance;
    }
	
    /**
    *Retourne le nombre de PP qu'il reste à la capacité.
    *
    * @return le nombre de PP qu'il reste à la capacité.
    */
    public int getPP(){
        return this.pp;
    }
	
    /**
    *Permet de modifier le nombre de PP que la capacité possède.
    *
    *@param PP est le nouveau nombre de PP possédé par la capacité.
    */
	public void setPP(int pp) {
		this.pp = pp;
	}
	
    /**
    *Retourne le numéro de la capacité.
    *
    * @return le numéro de la capacité.
    */
	public int getNumCapacite() {
		return this.numCapacite;
	}

    /**
    *Utilise le paramètre ppMax de la capacité pour restaurer le nombre de PP d'origine.
    *
    */
	public void resetPP(){
        this.pp = this.ppMax;
    }

    /**
    *Retourne la catégorie (spécial ou physique) de la capacité.
    *
    * @return la catégorie de la capacité.
    */
	public ICategorie getCategorie(){
        return this.categorie;
    }

    /**
    *Retourne le type de la capacité.
    *
    * @return la type de la capacité.
    */
	public IType getType(){
        return this.type;
    }
}
