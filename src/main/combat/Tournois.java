package combat;

import java.util.Random;
import java.util.Scanner;

import pokedex.Pokedex;

public class Tournois {
	/**
	 * Tableau composer des participants du tour 1
	 */
	private Dresseur[] tour1 = new Dresseur[32];
	/**
	 * Tableau composer des participants du tour 2
	 */
	private Dresseur[] tour2 = new Dresseur[16];
	/**
	 * Tableau composer des participants du tour 3
	 */
	private Dresseur[] tour3 = new Dresseur[8];
	/**
	 * Tableau composer des participants du tour 4
	 */
	private Dresseur[] tour4 = new Dresseur[4];
	/**
	 * Tableau composer des participants du tour 5
	 */
	private Dresseur[] tour5 = new Dresseur[2];
	/**
	 * vainqueur
	 */
	private Dresseur[] vainqueur = new Dresseur[1];
	
	/**
	 * Constructeur du tournoi
	 */
	public Tournois() {
		Pokedex pokedex = new Pokedex();
		int i=0;
		Scanner sc = new Scanner(System.in);
			int joueurs = cbJoueurs();
			for(i=0; i<joueurs; i++) {
				String str = null;
				System.out.println("Comment s'appelle votre dresseur ?");
				str = sc.nextLine();
				this.tour1[i] = new Dresseur(str, pokedex);
			}
		
		for(int j = i ; i<32; i++) {
			this.tour1[i] = new Aleatoire("dresseur " + i, pokedex);
			j = j;
		}
	}
	/**
	 * Méthode qui permet le déroulement du tournoi
	 */
	public void commence() {
		this.melanger();
		this.affiche(this.tour1);
		this.tour(this.tour1, this.tour2);
		this.affiche(this.tour2);
		this.tour(this.tour2,this.tour3);
		this.affiche(this.tour3);
		this.tour(this.tour3,this.tour4);
		this.affiche(this.tour4);
		this.tour(this.tour4,this.tour5);
		this.affiche(this.tour5);
		this.tour(this.tour5,this.vainqueur);
		this.affiche(this.vainqueur);
	}
	/**
	 * Retourne le nombre de joueurs réels participant au tournois.
	 * 
	 * @return le nombre de joueurs réels participant au tournois
	 */
	public int cbJoueurs() {
		int nbJoueurs = 0;
		String str = null;
		boolean verification = false;
		while(!verification) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Combien de joueurs ? Max 32");
		str = sc.nextLine();
		nbJoueurs = Integer.valueOf(str);
		sc.close();
		if(nbJoueurs<=32) verification = true;
		}
		return nbJoueurs;
	}
	
	/**
	 * Mélange les participants du tournois.
	 */
	public void melanger() {
		Random rand = new Random();
		
		for (int i = 0; i < this.tour1.length; i++) {
			int randomIndexToSwap = rand.nextInt(this.tour1.length);
			Dresseur temp = this.tour1[randomIndexToSwap];
			this.tour1[randomIndexToSwap] = this.tour1[i];
			this.tour1[i] = temp;
		}
	}
	
	/**
	 * Affiche les participants du tour.
	 * @param tour est le tour concerner par l'affichage.
	 */
	public void affiche(Dresseur[] tour) {
		for(int i=0; i<tour.length; i++) {
			System.out.println();
			System.out.print(tour[i].getNom()+" ");
		}
	}
	
	/**
	 * Permet le déroulement du tour.
	 * @param tab1 est la liste des participants qui vont combatre
	 * @param tab2 est la liste des participants ayant gagné le combat
	 */
	public void tour(Dresseur[] tab1, Dresseur[] tab2) {
		int j =0;
		for(int i=0; i<tab1.length; i+=2) {
			Combats c = new Combats(tab1[i], tab1[i+1]);
			c.commence();
			tab2[j] = c.getVainqueur();
			j++;
		}
	}
	
}
