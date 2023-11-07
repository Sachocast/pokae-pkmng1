/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IPokedex.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IPokedex {
	public IPokemon[] engendreRanch();			//Renvoie un tableau de 6 Pokemon au hasard
	public IEspece getInfo(String nomEspece);	//Renvoie une instance de l'espèce de Pokemon dont on fournit le nom
	public Double getEfficacite(IType attaque, IType defense); //Calcule l'efficacité d'un type d'attaque sur un type de cible
	public ICapacite getCapacite(String nomCapacite);   //Renvoie une instance de la capacité appelée nomCapacite
	public ICapacite getCapacite(int nunCapacite);
}
