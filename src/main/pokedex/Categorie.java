package pokedex;
import interfaces.ICategorie;

/**
 * @author Royer Baptiste et Castillejos Sacha
 * 
 * Il s'agit de la catégorie d'une capacité :
 *  - soit Physique
 *  - soit Special
 * Cette classe implémente ICategorie.
 * 
 */
public class Categorie implements ICategorie{
	/**
	 * C'est le nom de la Catégorie.
	 */
    private String nom;

    /**
     * Constructeur de Categorie.
     * 
     * @param nomCategorie est le nom de la categorie.
     */
    public Categorie(String nomCategorie) {
    	this.nom= nomCategorie;
    }
    
    /**
     * Retourne si true si la capacité est special ou false si elle est physique.
     * 
     * @returns un boolean.
     */
    public boolean isSpecial(){
    	if(this.nom.equals("Physique")) return false;
        return true; 
    }

    /**
     * Retourne le nom de la categorie.
     * 
     * @return le nom de la categorie.
     */
	public String getNom(){
        return this.nom;
    }
}
