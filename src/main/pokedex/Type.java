package pokedex;
import interfaces.IType;

/**
 * 
 * @author Baptiste Royer et Castillejos Sacha.
 * 
 * La classe Types.
 * Cette classe implemente IType.
 */
public class Type implements IType{
	/**
	 * Le nom du type.
	 */
    private String nom;

    /**
     * Constructeur de type.
     * 
     * @param nom est le nom du type à créer.
     */
    public Type(String nom){
        this.nom = nom;
    }

    /**
     * Retourne le nom du type.
     * 
     * @return le nom du type.
     */
    public String getNom(){
        return this.nom;
    }
}
