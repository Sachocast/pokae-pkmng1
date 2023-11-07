/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IEchange.java
 */
package interfaces;

/**
 * @author Leo Donati
 * C'est un autre type d'attaque
 * Correspond à l'échange du Pokemon du combat avec un autre Pokemon du ranch
 */
public interface IEchange extends IAttaque {
	public void setPokemon(IPokemon pok); //choisit le Pokemon remplaçant
	public IPokemon echangeCombattant();  //active le remplacement (et renvoie l'ancien pokemon)	
}
