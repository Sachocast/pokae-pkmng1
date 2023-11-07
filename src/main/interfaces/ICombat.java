/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * ICombat.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface ICombat {
	public void commence();				// lance le combat
	public IDresseur getDresseur1();	//Récupère le premier dresseur
	public IDresseur getDresseur2();	//Récupère le second dresseur
	public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2); //Crée un tour du combat
	public void termine();				//affiche le bilan du combat et l'enregistre
}
