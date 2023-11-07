/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IStrategy.java
 */
package interfaces;

/**
 * @author Leo Donati
 * Une stratégie est utilisée par les dresseurs non humains (IA) pour prendre les décisions 
 * Un DresseurIA possède une référence sur une IStrategy à qui il délègue la prise de décision
 * Un dresseur humain n'utilise pas IStrategy
 * 
 * Chaque méthode de IStrategy correspond à la méthode homonyme de IDresseur
 */
public interface IStrategy {
	public IPokemon choisitCombattant();
	public IPokemon choisitCombattantContre(IPokemon pok);
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur);
	public void initCapacitesRanch();   // méthode qui choisit les capacités à enseigner à chaque Pokemon du Ranch
}
