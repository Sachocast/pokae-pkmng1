/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IEspece.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IEspece {
	public IStat getBaseStat();				//stats de base
	public String getNom();
	public int getNiveauDepart();
	public int getBaseExp();
	public IStat getGainsStat();			//stats correspondant aux EV
	public ICapacite[] getCapSet();			//ensemble des capacités disponibles pour cette espèce
	public IEspece getEvolution(int niveau);  //renvoie null si aucune evolution possible
	public IType[] getTypes();				//une espece de pokemon peut avoir un ou deux types
}
