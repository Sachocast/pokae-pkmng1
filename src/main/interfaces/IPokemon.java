/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IPokemon.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IPokemon {
	public IStat getStat();
	public double getExperience();
	public int getNiveau();
	public int getId();
	public String getNom();
	public double getPourcentagePV();
	
	public IEspece getEspece();
	public void vaMuterEn(IEspece esp);			//Modifie l'espèce du Pokemon en esp
	
	public ICapacite[] getCapacitesApprises();	//Tableau des capacités que le Pokemon peut utiliser
	public void apprendCapacites(ICapacite[] caps);	//Enseigne les capacités au Pokemon
	public void remplaceCapacite(int i, ICapacite cap) throws Exception; 
	
	public void gagneExperienceDe(IPokemon pok); //Met à jour l'exprérience de this suite à la défaite de pok
	public void subitAttaqueDe(IPokemon pok, IAttaque atk); //Met à jour les stats de this en tenant compte des dégats subits par l'attaque atk de pok
	
	
	public boolean estEvanoui();		//renvoie true si les pointes de vie du pokemonsont 0
	public boolean aChangeNiveau();		//renvoie true si le Pokemon vient de changer de niveau
	public boolean peutMuter();			//renvoie true si le Pokemon peut muter
	
	public void soigne();				//Remet les PV au maximum
}
