package combat;

import java.util.Random;
import java.util.logging.Logger;

import capacite.Attaque;
import capacite.Echange;
import interfaces.IAttaque;
import interfaces.IPokemon;
import interfaces.ITour;

/**
 * @author Royer Baptiste et Castillejos Sacha
 * 
 * Lors d'un tour les actions qu'on choisit les dresseurs se produisent.
 * Cette classe implémente l'interface ITour.
 * 
 */

public class Tour implements ITour{

	private final static Logger LOGGER = Logger.getLogger("");

	/**
	 * Le pokémon qui participe au tour.
	 */
	private IPokemon pok1;
	/**
	 * L'action que le dresseur de pok1 a choisie.
	 */
	private IAttaque atk1;
	/**
	 * Le deuxieme pokémon qui participe au tour.
	 */
	private IPokemon pok2;
	/**
	 * L'action que le dresseur de pok2 a choisie.
	 */
	private IAttaque atk2;	

	/**
	 * Constructeur de Tour.
	 * 
	 * @param p1 est le pok1.
	 * @param a1 est atk1.
	 * @param p2 est le pok2.
	 * @param a2 est atk2.
	 */
	public Tour(IPokemon p1, IAttaque a1, IPokemon p2, IAttaque a2) {
		this.pok1 = p1;
		this.atk1 = a1;
		this.pok2 = p2;
		this.atk2 = a2;
	}

	public IPokemon getPok1() {
		return this.pok1;
	}

	public IPokemon getPok2() {
		return this.pok2;
	}

	/**
	 * C'est la méthode qui permet de lancer le tour, elle appelle toutes les méthodes qui permettent son déroulement.
	 */
	public void commence() {
		String typeAttaque1 = atk1.getClass().getName(); 
		String typeAttaque2 = atk2.getClass().getName(); 

		//Cas où les deux dresseurs choisissent d'attaquer
		if(typeAttaque1.equals("capacite.Attaque") && typeAttaque2.equals("capacite.Attaque")) {
			if(calculPriorite() == 1) {
				lancementAttaque((Attaque) atk1, pok1, pok2);
				if(pok2.estEvanoui() != true) { lancementAttaque((Attaque) atk2, pok2, pok1); }
			}
			else {
				lancementAttaque((Attaque) atk2, pok2, pok1);
				if(pok1.estEvanoui() != true) { lancementAttaque((Attaque) atk1, pok1, pok2); }
			}
		}

		//Cas où les deux dresseurs choisissent de changer leur pokemon
		else if(typeAttaque1.equals("capacite.Echange") && typeAttaque2.equals("capacite.Echange")) {
			//System.out.print(pok1.getNom() + " retourne vers son dresseur tandis que ");
			LOGGER.info(pok1.getNom() + " retourne vers son dresseur tandis que ");
			this.pok1 = ((Echange) atk1).echangeCombattant();
			//System.out.println(pok1.getNom() + " le remplace !");
			LOGGER.info(pok1.getNom() + " le remplace !");

			//System.out.print(pok2.getNom() + " retourne vers son dresseur tandis que ");
			LOGGER.info(pok2.getNom() + " retourne vers son dresseur tandis que ");
			this.pok2 = ((Echange) atk2).echangeCombattant();
			//System.out.println(pok2.getNom() + " le remplace !");
			LOGGER.info(pok2.getNom() + " le remplace !");
		}

		//Cas où le dresseur n°1 décide d'attaquer et où le dresseur n°2 décide de changer son pokemon
		else if(typeAttaque1.equals("capacite.Attaque") && typeAttaque2.equals("capacite.Echange")) {
			//System.out.print(pok2.getNom() + " retourne vers son dresseur tandis que ");
			LOGGER.info(pok2.getNom() + " retourne vers son dresseur tandis que ");
			pok2 = ((Echange) atk2).echangeCombattant();
			//System.out.println(pok2.getNom() + " le remplace !");
			LOGGER.info(pok2.getNom() + " le remplace !");
			lancementAttaque((Attaque) atk1, pok1, pok2);
		}

		//Cas où le dresseur n°2 décide d'attaquer et où le dresseur n°1 décide de changer son pokemon
		else if(typeAttaque1.equals("capacite.Echange") && typeAttaque2.equals("capacite.Attaque")) {
			//System.out.print(pok1.getNom() + " retourne vers son dresseur tandis que ");
			LOGGER.info(pok1.getNom() + " retourne vers son dresseur tandis que ");
			pok1 = ((Echange) atk1).echangeCombattant();
			//System.out.println(pok1.getNom() + " le remplace !");
			LOGGER.info(pok1.getNom() + " le remplace !");

			lancementAttaque((Attaque) atk2, pok2, pok1);
		}
		
		affichePV();
	}

	/**
	 * Retourne un int correspondant au pokemon devant attaquer en premier.
	 * 
	 * @return un int correspondant à l'odre d'attaque.
	 */
	public int calculPriorite() {
		//Méthode renovyant un int correspondant au pokemon devant attaquer en premier
		int vitessePok1 = this.pok1.getStat().getVitesse();
		int vitessePok2 = this.pok2.getStat().getVitesse();

		if(vitessePok1 > vitessePok2) {
			LOGGER.info(pok1.getNom() + " attaque en premier !");
			return 1;
		}

		else if(vitessePok2 > vitessePok1) {
			LOGGER.info(pok2.getNom() + " attaque en premier !");
			return 2;
		}
		
		//Si la méthode ne retourne rien jusqu'ici alors les deux pokemon ont la même vitesse, dans ce cas la le premier à jouer et tiré au hazard
	    Random random = new Random();
		int aleatoire = random.nextInt(1 + 1) + 1;

		if(aleatoire == 1) {
			LOGGER.info(pok1.getNom() + " gagne le speed tie");
			return 1;
		}
		
		else if (aleatoire == 2) {
			LOGGER.info(pok2.getNom() + " gagne le speed tie");
			return 2;
		}
		return 0;
	}
		
	/**
	 * Méthode qui permet de lancer l'attaque.
	 * 
	 * @param atk est l'attaque.
	 * @param attaquant est le pokémon qui attaque.
	 * @param cible est le pokémon qui subit l'attaque.
	 */
	public void lancementAttaque(Attaque atk, IPokemon attaquant, IPokemon cible) {
		double savePV = cible.getPourcentagePV();
		cible.subitAttaqueDe(attaquant, atk);
		double pourcentageSubit = savePV - cible.getPourcentagePV();

		//System.out.println("\n" + attaquant.getNom() + " utilise " + atk.getCapaciteUtilise().getNom() + " sur " + cible.getNom() + " !");
		LOGGER.info(attaquant.getNom() + " utilise " + atk.getCapaciteUtilise().getNom() + " sur " + cible.getNom() + " !");
		//System.out.println(cible.getNom() + " a perdu " + Math.round(pourcentageSubit) + "% de PV !");
		LOGGER.info(cible.getNom() + " a perdu " + Math.round(pourcentageSubit) + "% de PV !");
	}
	
	/**
	 * Affiche les PVs des deux pokémons.
	 */
	public void affichePV() {
		//System.out.println("\n" + this.pok1.getNom()+ " a " + Math.round(this.pok1.getPourcentagePV()) + "% de PV");
		LOGGER.info(this.pok1.getNom()+ " a " + Math.round(this.pok1.getPourcentagePV()) + "% de PV");
		//System.out.println(this.pok2.getNom() + " a " + Math.round(this.pok2.getPourcentagePV()) + "% de PV");
		LOGGER.info(this.pok2.getNom() + " a " + Math.round(this.pok2.getPourcentagePV()) + "% de PV");
	}

}
