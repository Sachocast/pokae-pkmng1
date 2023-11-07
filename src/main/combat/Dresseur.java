package combat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import capacite.Attaque;
import capacite.Capacite;
import capacite.Echange;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IPokemon;
import pokedex.Pokedex;
import pokemon.Espece;
import pokemon.Pokemon;

/**
 * @author Royer Baptiste et Castillejos Sacha
 * 
 * Un dresseur possède un ranch et peut faire des combats de pokemon.
 * Cette classe implémente l'interface IDresseur.
 * 
 */

public class Dresseur implements IDresseur {
	
	private final static Logger LOGGER = Logger.getLogger("");

	/**
	 * Le nom du dresseur.
	 */
	protected String nom;
	/**
	 * Le niveau du dresseur.
	 */
	private int niveau;
	/**
	 * Le ranch du dresseur.
	 */
	private IPokemon[] ranch; 
	/**
	 * Constructeur de Dresseur.
	 * 
	 * @param nom est le nom du dresseur.
	 */
	public Dresseur(String nom, Pokedex pokedex) {
		this.nom = nom;
		this.niveau = 6;
		this.ranch = pokedex.engendreRanch();
		initCapacitesRanch();
	}
	
	/**
	 * Retourne le nom du dresseur
	 * 
	 * @return nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Retourne le niveau du dresseur.
	 * 
	 * @return niveau
	 */
	public int getNiveau() {
		return this.niveau;
	}

	/**
	 * Retourne un pokemon du ranch.
	 * 
	 * @param i est l'index du pokemon dans le ranch.
	 * 
	 * @return le pokemon qui correspond à l'index i
	 */
	public IPokemon getPokemon(int i) {
		return this.ranch[i];
	}
	
	/**
	 * Retourne le ranch du dresseur.
	 * 
	 * @return ranch
	 */
	public IPokemon[] getRanch() {
		return this.ranch;
	}

	/**
	 * Permet d'enseigner une capacité à un pokemon.
	 * 
	 * @param pokemon est le pokemon qui apprend la capacité.
	 * @param caps est la capacité à apprendre.
	 */
	public void enseigne(IPokemon pok, ICapacite[] caps) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Espece espece = (Espece) pok.getEspece();
		boolean apprendre = false;

		if(pok.getNiveau() == 1) {
			apprendre = true;
		}
		else {
			System.out.print("Voulez-vous remplacer une capacité à " + pok.getNom() + " ? [y][n] : " );
			String reponse = "";
			try {
				reponse = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(reponse.equals("y")) {
				apprendre = true;
			}
		}

		if(apprendre) {
			System.out.println("\n================ " + pok.getNom() + " apprend des capacités ================\n");
			for(ICapacite capa : espece.getCapSet()){
				int stop = 0;
				if(espece.getLvlLearnedCapSet().get(capa) != null){
					if(espece.getLvlLearnedCapSet().get(capa) <= this.niveau){
						System.out.print("Voulez-vous apprendre " + capa.getNom() + " à votre " + espece.getNom() + " ? [y][n] : ");
						String reponse;
						try {
							reponse = input.readLine();
							if(reponse.equals("y")){    
								if(pok.getCapacitesApprises()[3] != null){
									System.out.println();
									for(int i = 0; i<4; i++){
										System.out.println("[" + i + "] " + pok.getCapacitesApprises()[i].getNom());
									}
									System.out.print("\nVotre pokemon connaît déjà 4 capacités, laquelle remplacer ? [0][1][2][3] : ");
	
									while(stop == 0) {
										reponse = input.readLine();
										if(Integer.parseInt(reponse) < 0 || Integer.parseInt(reponse) > 3) {
											System.out.print("Réponse invalide, veillez choisir un de ces nombres [0][1][2][3] : ");
										}
										else {
											try {
												pok.remplaceCapacite(Integer.parseInt(reponse), capa);
											} catch (NumberFormatException e) {
												e.printStackTrace();
											} catch (Exception e) {
												e.printStackTrace();
											}
											stop = 1;
										}
									}
								}
								else{
									int i = 0;
									while(i < 4){
										if(pok.getCapacitesApprises()[i] == null){
											pok.getCapacitesApprises()[i] = capa;
											i = 4;
										}
										i++;
									}
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}    
		}
	}

	/**
	 * Le dresseur choisi les capacités qu'il veut sur ses pokemons lors de la création de son ranch
	 */
    public void initCapacitesRanch() {
        for(int i = 0; i < 6; i++) {
			enseigne(getPokemon(i), getPokemon(i).getEspece().getCapSet());
		}
    }

	/**
	 * Permet de soigner les pokemons du ranch.
	 */
	public void soigneRanch() {
		for(int i = 0; i < 6; i++) {
			this.ranch[i].soigne();
		}
	}

	/**
	 * Permet de mettre à jour le niveau d'un dresseur à la fin d'un combat
	 */
	public void niveauDresseurUpdate() {
		int nouveauNiveau = 0;

		for(int i = 0; i < 6; i++) {
			nouveauNiveau += this.getPokemon(i).getNiveau();
		}
		this.niveau = nouveauNiveau;
	}

	/**
	 * Permet de choisir un pokemon à envoyer au combat.
	 * 
	 * @return le pokemon à envoyer
	 */
	public IPokemon choisitCombattant() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		int stop = 0;

		System.out.println("\n================ Votre équipe ================\n");
		for(int i = 0; i < 6; i++) {
			System.out.println("[" + i + "] " + this.ranch[i].getNom());
		}
		System.out.print("\nQuel Pokemon souhaitez-vous envoyer en premier  ? [0][1][2][3][4][5] : ");

		try {
			while(stop == 0) {
				String reponse = input.readLine();
				
				if(reponse.equals("0") || reponse.equals("1") || reponse.equals("2") || reponse.equals("3") || reponse.equals("4") || reponse.equals("5")) {
					stop = 1;
					return ranch[Integer.parseInt(reponse)];
				}

				else {
					System.out.print("Réponse invalide, veillez choisir un de ces nombres [0, 1, 2, 3, 4, 5] : ");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.ranch[0];
	}

	/**
	 * Permet d'envoyer un pokemon en fonction du pokemon adverse.
	 * 
	 * @param pok est le pokemon adverse.
	 * @return le pokemon à envoyer
	 */
	public IPokemon choisitCombattantContre(IPokemon pok) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		int stop = 0;

		System.out.println("\n================ Votre équipe ================\n");
		for(int i = 0; i < 6; i++) {
			System.out.print("[" + i + "] " + this.ranch[i].getNom());
			if(this.ranch[i].getPourcentagePV() <= 0) {
				System.out.println(" (K.O)");
			}
			else {
				System.out.println();
			}
		}
		System.out.print("\nQuel Pokemon souhaitez-vous envoyer contre le " + pok.getNom() + " ennemi ? [0][1][2][3][4][5] : ");

		try {
			while(stop == 0) {
				String reponse = input.readLine();
				
				if(reponse.equals("0") || reponse.equals("1") || reponse.equals("2") || reponse.equals("3") || reponse.equals("4") || reponse.equals("5")) {
					if(ranch[Integer.parseInt(reponse)].getPourcentagePV() >= 1) {
						stop = 1;
						return ranch[Integer.parseInt(reponse)];
					}
					else {
						System.out.print("Ce Pokemon est K.O, veuillez en choisir un autre [0, 1, 2, 3, 4, 5] : ");
					}
				}

				else {
					System.out.print("Réponse invalide, veillez choisir un de ces nombres [0, 1, 2, 3, 4, 5] : ");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.ranch[0];
	}

	/**
	 * Permet au dresseur de choir l'action qu'il veut faire.
	 * 
	 * @param attaquant est le pokemon qui attaque.
	 * @param defenseur est le pokemon qui defend.
	 */
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
        LOGGER.info("Le pokemon attaquant choisit son attaque\n");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader inputCapacite = new BufferedReader(new InputStreamReader(System.in));
		int stop = 0, stop2 = 0, tailleReelle = Pokedex.tailleReelle(attaquant.getCapacitesApprises());

		System.out.println("\n================ Vos actions ================\n\n[0] Attaquer\n[1] Changer de Pokemon\n");
		System.out.print("Que voulez-vous faire ? [0][1] : ");

		try {
			while(stop == 0) {
				String reponse = input.readLine();
				
				if(reponse.equals("0")) {
					System.out.println("\n================ Les capacités de " + attaquant.getNom() + " ================\n");
					for(int i = 0; i < tailleReelle; i++) {
						System.out.println("[" + i + "] " + attaquant.getCapacitesApprises()[i].getNom() + " : " + attaquant.getCapacitesApprises()[i].getPP() + "/" + (new Capacite(attaquant.getCapacitesApprises()[i].getNom()).getPP()) + " PP");
					}
					System.out.print("\nQuelle capacité voulez-vous utiliser ? ");
					for(int i = 0; i < tailleReelle; i++) {
						System.out.print("[" + i + "]");
					}
					System.out.print(" : ");

					while(stop2 == 0) {
						String reponse2 = inputCapacite.readLine();
						if(reponse2.equals("0") || reponse2.equals("1") || reponse2.equals("2") || reponse2.equals("3")) {
							if(Integer.parseInt(reponse2) >= tailleReelle) {
								System.out.print("Réponse invalide, veillez choisir un de ces nombres ");
								for(int i = 0; i < tailleReelle; i++) {
									System.out.print("[" + i + "]");
								}
								System.out.print(" : ");
							}
							else {
								if(attaquant.getCapacitesApprises()[Integer.parseInt(reponse2)].getPP() <= 0) {
									System.out.println(attaquant.getNom() + " essaye de lancer " + attaquant.getCapacitesApprises()[Integer.parseInt(reponse2)].getNom() + " mais il n'a plus de PP !");
									return new Attaque((new Capacite("Lutte")));
								}
								return new Attaque((Capacite) attaquant.getCapacitesApprises()[Integer.parseInt(reponse2)]);
							}
						}
						else {
							System.out.print("Réponse invalide, veillez choisir un de ces nombres ");
								for(int i = 0; i < tailleReelle; i++) {
									System.out.print("[" + i + "]");
								}
								System.out.print(" : ");
						}
					}
					stop = 1;
					return null;
				}

				else if(reponse.equals("1")) {
					stop = 1;
					return new Echange(choisitCombattantContre(defenseur), attaquant);
				}

				else {
					System.out.print("Réponse invalide, veillez choisir un de ces nombres [0][1] : ");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Renvoie un ranch identique à celui du dresseur.
	 * 
	 */
	public IPokemon[] getRanchCopy() {
		IPokemon[] copyRanch = new IPokemon[6];

		for(int i = 0; i < 6; i++) {
			copyRanch[i] = ((Pokemon) ranch[i]).getClone();
		}

		return copyRanch;
	}
}
