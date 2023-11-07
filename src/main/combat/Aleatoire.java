package combat;
import java.util.concurrent.ThreadLocalRandom;

import capacite.Attaque;
import capacite.Capacite;
import capacite.Echange;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IPokemon;
import interfaces.IStrategy;
import pokemon.Pokemon;
import pokedex.Pokedex;
import pokemon.Espece;

/**
 * @author Royer Baptiste et Castillejos Sacha
 * 
 * Cette classe gère une IA aléatoire
 * Cette classe implémente l'interface IStrate  et est une sous-classe de Dresseur.
 */

public class Aleatoire extends Dresseur implements IStrategy{

	/**
	 * Constructeur de Aleatoire
	 * 
	 * @param nom est le nom du dresseur.
     * @param pokedex est le pokedex de l'ia
	 */
    public Aleatoire(String nom, Pokedex pokedex) {
        super(nom, pokedex);
    }

	/**
	 * Permet d'enseigner une capacité à un pokemon.
	 * 
	 * @param pokemon est le pokemon qui apprend la capacité.
	 * @param caps est la capacité à apprendre.
	 */
    public void enseigne(IPokemon pok, ICapacite[] caps) {
    }

	/**
	 * Permet de choisir un pokemon à envoyer au combat.
	 * 
	 * @return le pokemon à envoyer
	 */
    public IPokemon choisitCombattant() {
        //On choisit au hasard un pokemon à envoyer
        int randP = (int) (Math.random() * 6);
        return this.getPokemon(randP);
    }

	/**
	 * Permet d'envoyer un pokemon en fonction du pokemon adverse.
	 * 
	 * @param pok est le pokemon adverse.
	 * @return le pokemon à envoyer
	 */
    public IPokemon choisitCombattantContre(IPokemon pok) {
        //On choisit au hasard un pokemon de l'équipe à envoyer, comme il s'agit de l'IA aléatoire cette méthode est la même qui choisitCombattant()
        int stop = 0, randP = 7;
        while(stop == 0) {
            randP = (int) (Math.random() * 6);
            if(this.getPokemon(randP).estEvanoui() == false) {
                stop = 1;
            }
        }
        return this.getPokemon(randP);
    }

	/**
	 * Permet au dresseur de choiir l'action qu'il veut faire.
	 * 
	 * @param attaquant est le pokemon qui attaque.
	 * @param defenseur est le pokemon qui defend.
	 * 
	 * @return l'attaque
	 */
    public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
        //On selectionne au hasard une action a effectuer (Attaquer ou Echanger)
        int randomAttaque = ThreadLocalRandom.current().nextInt(0, 4);
        int randomCapacite = 0, tailleReelle = Pokedex.tailleReelle(attaquant.getCapacitesApprises()), compteurKO = 0;

        for(int i = 0; i < 6; i++) {
            if(this.getPokemon(i).estEvanoui()) {
                compteurKO++;
            }
        }

        //Si on tombe sur l'échange alors on envoie au hasard un Pokemon de l'équipe
        if(randomAttaque == 0 && compteurKO < 5) { 
            return new Echange(choisitCombattantContre(defenseur), attaquant);
        }

        //Si on tombe sur l'attaque alors on envoie au hasard une capacité disponible
        if(tailleReelle > 1) {
            randomCapacite = (int) (Math.random() * tailleReelle);
        }
        else {
            return new Attaque((Capacite) attaquant.getCapacitesApprises()[0]);
        }

        return new Attaque((Capacite) attaquant.getCapacitesApprises()[randomCapacite]);
    }

    /**
	 * Renvoie un ranch identique à celui du dresseur.
	 * 
	 */
	public IPokemon[] getRanchCopy() {
		IPokemon[] copyRanch = new IPokemon[6];

		for(int i = 0; i < 6; i++) {
			copyRanch[i] = ((Pokemon) getPokemon(i)).getClone();
		}

		return copyRanch;
	}

    /**
	 * Le dresseur choisi les capacités qu'il veut sur ses pokemons lors de la création de son ranch
	 */
    public void initCapacitesRanch() {
        for(int i = 0; i < 6; i++) {
            Pokemon pok = (Pokemon) getPokemon(i);
            Capacite[] capsLearnable = (Capacite[]) ((Espece) pok.getEspece()).getCapSetAt(1);
            Capacite[] capsLearned = (Capacite[]) pok.getCapacitesApprises();
            int lenghtLearnable = Pokedex.tailleReelle(capsLearnable);

            while(Pokedex.tailleReelle(capsLearnable) > 0 && Pokedex.tailleReelle(capsLearned) < 4) {
                try {
                    int randI = (int) (Math.random() * lenghtLearnable);
                    if(capsLearnable[randI] != null) {
                        if(Pokedex.tailleReelle(pok.getCapacitesApprises()) != 4) {
                            pok.remplaceCapacite(Pokedex.tailleReelle(pok.getCapacitesApprises()), capsLearnable[randI]);
                            capsLearnable[randI] = null;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
		}
    }
}
