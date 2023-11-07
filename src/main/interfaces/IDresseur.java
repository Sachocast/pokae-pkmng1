/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IDresseur.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IDresseur {
	public String getNom();			//Nom du dresseur
	public int getNiveau();			//Niveau du dresseur
	
	public IPokemon getPokemon(int i); //Récupère le i-eme Pokemon du Ranch
	
	public void enseigne(IPokemon pok, ICapacite[] caps); //Donne au pokemon pok les capacites caps
	public void soigneRanch();		//Redonne à tous les pokemon du Ranch leur PV max
	
	public IPokemon choisitCombattant();	//Choisit le premier Pokemon pour combattre
	public IPokemon choisitCombattantContre(IPokemon pok); //Choisit le Pokemon pour combattre contre pok
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur); //Choisit l'attaque à utiliser contre le pokemon defenseur

	public IPokemon[] getRanchCopy(); 		//Récupère une copie (clone) de chaque pokemon du ranch du dresseur
}
