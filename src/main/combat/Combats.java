package combat;

import java.util.logging.Level;
import java.util.logging.Logger;

import capacite.Attaque;
import interfaces.IAttaque;
import interfaces.ICombat;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;
import pokemon.Espece;

/**
 * @author Royer Baptiste et Castillejos Sacha
 * 
 * Lors d'un combat s'affrontent les 6 pokémons des 2 dresseurs.
 * Chaque dresseur ne peut avoir que un pokémon sur le terrain.
 * Le combat se termine lorsque un des dresseurs n'as plus de pokémon en état de se battre.
 * Cette classe implémente l'interface ICombat.
 * 
 */
public class Combats implements ICombat {
	private final static Logger LOGGER = Logger.getLogger("");

	/**
	 * Le premier dresseur du combat.
	 */
	private Dresseur dresseur1;
	/**
	 * Le deuxieme dresseur du combat.
	 */
	private Dresseur dresseur2;
	/**
	 * Le pokemon du dresseur1.
	 */
	private IPokemon pokemon1;
	/**
	 * Le pokemon du dresseur2.
	 */
	private IPokemon pokemon2;
	/**
	 * Le vainqueur du combat.
	 */
	private Dresseur vainqueur;
	/**
	 * Le perdant du combat.
	 */
	private Dresseur perdant;
	
	/**
	 * Constructeur de Combats.
	 * 
	 * @param d1 est le dresseur1.
	 * @param d2 est le dresseur2.
	 */
	public Combats(Dresseur d1, Dresseur d2) {
		this.dresseur1 = d1;
		this.dresseur2 = d2;
	}
	
	/**
	 * C'est la méthode qui permet de lancer le combat, elle appelle toutes les méthodes qui permettent son déroulement.
	 */
	public void commence() {
		int compteurTour = 0;
        LOGGER.setLevel(Level.INFO);

		montreEquipe(dresseur1);
		montreEquipe(dresseur2);
		
        LOGGER.info("\n=================== Début du combat ==================\n");

		this.pokemon1 = this.dresseur1.choisitCombattant();
		
		//System.out.println(dresseur1.getNom() + " envoie " + pokemon1.getNom() + " !");
		LOGGER.info(dresseur1.getNom() + " envoie " + pokemon1.getNom() + " !");
		this.pokemon2 = this.dresseur2.choisitCombattant();
		//System.out.println(dresseur2.getNom() + " envoie " + pokemon2.getNom() + " !");
		LOGGER.info(dresseur2.getNom() + " envoie " + pokemon2.getNom() + " !");

		while((this.dresseurDispo(this.dresseur1) == true) && (this.dresseurDispo(this.dresseur2) == true)) {
			//System.out.println("\n=================== Tour " + compteurTour + " ==================\n");
	        LOGGER.info("\n=================== Tour " + compteurTour + " ==================\n");

			if(dresseur1.getClass().getName().equals("combat.Prudente")) {((Prudente) dresseur1).setRanchAdverse(dresseur2.getRanchCopy());}
			if(dresseur2.getClass().getName().equals("combat.Prudente")) {((Prudente) dresseur2).setRanchAdverse(dresseur1.getRanchCopy());}

			Attaque attaque1 = (Attaque) dresseur1.choisitAttaque(pokemon1, pokemon2);
			Attaque attaque2 = (Attaque) dresseur2.choisitAttaque(pokemon2, pokemon1);

			Tour nouveauTour = (Tour) nouveauTour(this.pokemon1, attaque1, this.pokemon2, attaque2);
			nouveauTour.commence();
			this.pokemon1 = nouveauTour.getPok1();
			this.pokemon2 = nouveauTour.getPok2();
			compteurTour++;

			if((this.dresseurDispo(this.dresseur1) == true) && (this.dresseurDispo(this.dresseur2) == true)) {
				this.verifPokemon();
			}	

			else {
				this.verifPokemon();
				this.termine();
				return;
			}
		}
	}

	/**
	 * Retourne le dresseur1.
	 * 
	 * @return dresseur1
	 */
	public IDresseur getDresseur1() {
		return this.dresseur1;
	}

	/**
	 * Retourne le dresseur2.
	 * 
	 * @return dresseur2
	 */
	public IDresseur getDresseur2() {
		return this.dresseur2;
	}

	/**
	 * Montre l'équipe du joueur entré en paramètre.
	 * 
	 * @param dresseur Dresseur dont l'équipe est affiché soit dans les logs soit dans la console
	 */
	public void montreEquipe(Dresseur dresseur) {
		System.out.println("\n ========== Equipe de " + dresseur.getNom() + " ===========\n");
		LOGGER.info("\n========== Equipe de " + dresseur.getNom() + " ===========\n");
		for(int i = 0; i < 6; i++) {
			if(dresseur.getPokemon(i).estEvanoui()) {
				System.out.print("[K.O]");

			}
			else {
				System.out.print("[" + Math.round(dresseur.getPokemon(i).getPourcentagePV()) + "%]");

			}
			System.out.println("[lvl " + dresseur.getPokemon(i).getNiveau() + "] " + dresseur.getPokemon(i).getNom());
			LOGGER.info(dresseur.getPokemon(i).getNom());

		}
	}

	/**
	 * Vérifie que le pokémon sur le terrain de chaque dresseur est en capacité de combattre
	 */
 	public void verifPokemon() {
		//Vérifie que le pokemon sur le terrain de chaque dresseur est en capacité de combattre
		if(this.pokemon1.estEvanoui() == true) {
			//System.out.println("\n" + this.pokemon1.getNom() + " n'est plus en état de se battre !");
			LOGGER.info(this.pokemon1.getNom() + " n'est plus en état de se battre !");
			int copieNiveau = this.pokemon2.getNiveau();
			this.pokemon2.gagneExperienceDe(this.pokemon1);

			if(copieNiveau != this.pokemon2.getNiveau()) {
				Espece espece = (Espece) this.pokemon2.getEspece();
				dresseur2.enseigne(this.pokemon2, espece.getCapSetAt(this.pokemon2.getNiveau()));
			}
			if(dresseurDispo(this.dresseur1)) {
				this.pokemon1 = this.dresseur1.choisitCombattantContre(this.pokemon2);
				//System.out.println(this.dresseur1.getNom() + " envoie " + this.pokemon1.getNom() + " !");
				LOGGER.info(this.dresseur1.getNom() + " envoie " + this.pokemon1.getNom() + " !");
			}
		}

		if(this.pokemon2.estEvanoui() == true) {
			//System.out.println("\n" + this.pokemon2.getNom() + " n'est plus en état de se battre !");
			LOGGER.info(this.pokemon2.getNom() + " n'est plus en état de se battre !");
			int copieNiveau = this.pokemon1.getNiveau();
			this.pokemon1.gagneExperienceDe(this.pokemon2);

			if(copieNiveau != this.pokemon1.getNiveau()) {
				Espece espece = (Espece) this.pokemon1.getEspece();
				this.dresseur1.enseigne(this.pokemon1, espece.getCapSetAt(this.pokemon1.getNiveau()));
			}
			if(dresseurDispo(this.dresseur2)) {
				this.pokemon2 = this.dresseur2.choisitCombattantContre(this.pokemon1);
				//System.out.println(this.dresseur2.getNom() + " envoie " + this.pokemon2.getNom() + " !");
				LOGGER.info(this.dresseur2.getNom() + " envoie " + this.pokemon2.getNom() + " !");
			}
		}
	}

	/**
	 * Vérifie qu'il reste des pokémon capables de ce battre dans l'équipe du dresseur.
	 * 
	 * @param dresseur est le dresseur à qui on vérifie les pokémon.
	 * 
	 * @return false si il reste des pokémons, sinon true
	 */
	public boolean dresseurDispo(Dresseur dresseur) {
		int compteurKO = 0;

		for(int i = 0; i < 6; i++) {
			if(dresseur.getRanch()[i].estEvanoui()) {
				compteurKO++;
			}
		}
		return (compteurKO < 6);
	}

	/**
	 * Créer un nouveau tour
	 * 
	 * @param pok1 est le pokemon du dresseur1 qui participe à ce tour.
	 * @param atk1 est l'attaque qui sera déclenchée dans ce tour par le dresseur1.
	 * @param pok2 est le pokemon du dresseur2 qui participe à ce tour.
	 * @param atk2 est l'attaque qui sera déclenchée dans ce tour par le dresseur2.
	 * 
	 * @return le tour créer
	 */
	public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {
		Tour tour = new Tour(pok1, atk1, pok2, atk2);
		return tour;
	}
	
	/**
	 * Affiche et enregistre le résultat du combat.
	 */
	public void termine() {

		if(this.dresseurDispo(this.dresseur1) == false) {
			this.vainqueur = this.dresseur2;
			this.perdant = this.dresseur1;
			System.out.println("\n" + this.vainqueur.getNom() + " remporte le combat contre " + this.perdant.getNom() + " !");
			LOGGER.info("\n" + this.vainqueur.getNom() + " remporte le combat contre " + this.perdant.getNom() + " !");
		}

		else {
			this.vainqueur = this.dresseur1;
			this.perdant = this.dresseur2;
			System.out.println("\n" + this.vainqueur.getNom() + " remporte le combat contre " + this.perdant.getNom() + " !");
			LOGGER.info("\n" + this.vainqueur.getNom() + " remporte le combat contre " + this.perdant.getNom() + " !");
		}

		montreEquipe(dresseur1);
		montreEquipe(dresseur2);
		dresseur1.niveauDresseurUpdate();
		dresseur2.niveauDresseurUpdate();
		dresseur1.soigneRanch();
		dresseur2.soigneRanch();
	}
	
	/**
	 * Retourne le vainqueur
	 * @return this.vainqueur
	 */
	public Dresseur getVainqueur() {
		return this.vainqueur;
	}
}
