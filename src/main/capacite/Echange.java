package capacite;
import interfaces.IEchange;
import interfaces.IPokemon;
import pokemon.Pokemon;

/**
 * @author Royer Baptiste
 * Correspond à l'échange du Pokemon du combat avec un autre Pokemon du ranch.
 * Cette classe implémente l'interface IEchange.
 */
public class Echange extends Attaque implements IEchange{
	
	/**
	 * Le pokemon entrant est le pokemon envoyé par le dresseur sur le terrain devant remplacer le précédent.
	 */
	private Pokemon entrant; 

	/**
	 * Le pokemon sortant est le pokemon présent sur le terrain au moment de l'échange et qui laisse sa place à un autre.
	 */
	private Pokemon sortant; 

	public IPokemon getSortant() {
		return this.sortant;
	}
	
	public IPokemon getEntrant() {
		return this.entrant;
	}

	/**
	 * Constructeur de Echange.
	 */
	public Echange(IPokemon entrant, IPokemon sortant){
		//consructeur d'echange
		this.entrant = (Pokemon) entrant;
		this.sortant = (Pokemon) sortant;
		this.sortant.retireEchange();
	}

	/**
	 * Envoie le pokemon sur le terrain.
	 * 
	 * @param pok est le pokemon à envoyé sur le terrain.
	 */
	public void setPokemon(IPokemon pok){
		this.entrant = (Pokemon)pok;
    }

	/**
	 * Retire le pokemon du terrain et appel setPokemon pour en renvoyer un nouveau.
	 */
	public IPokemon echangeCombattant(){
		setPokemon(entrant);
		return this.entrant;
    }

}

