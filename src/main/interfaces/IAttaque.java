/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IAttaque.java
 */
package interfaces;

/**
 * @author Leo Donati
 * Une attaque est une action du Pokemon durant une bataille.
 * Il y a deux types d'attaques :
 * - les capacités (interface ICapacity)
 * - les échanges (interface IEchange)
 */
public interface IAttaque {
	//renvoie le nombre de points de vie qu'il faut enlever au receveur
	int calculeDommage(IPokemon lanceur, IPokemon receveur); 	
	
	//fait diminuer de 1 le nombre restant de fois où l'attaque peut être utilisée
	void utilise();
}
