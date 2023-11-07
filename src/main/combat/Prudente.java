package combat;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public class Prudente extends Dresseur implements IStrategy{
    private final static Logger LOGGER = Logger.getLogger("");
    /**
	 * Le ranch de l'équipe adverse.
	 */
    private IPokemon[] ranchAdverse;

	/**
	 * Constructeur de Prudente
	 * 
	 * @param nom est le nom du dresseur.
     * @param pokedex est le pokedex de l'ia
	 */
    public Prudente(String nom, Pokedex pokedex) {
        super(nom, pokedex);
        ConfigLog.setup();
        LOGGER.setLevel(Level.INFO);
    }

    /**
	 * Setter du ranchAdverse
	 * 
	 * @param ranchAdverse est une copy du ranch de l'adversaire
	 */
    public void setRanchAdverse(IPokemon[] ranchAdverse) {
        this.ranchAdverse = ranchAdverse;
    }

    /**
	 * Permet d'enseigner une capacité à un pokemon.
	 * 
	 * @param pokemon est le pokemon qui apprend la capacité.
	 * @param caps est la capacité à apprendre.
	 */
    public void enseigne(IPokemon pokemon, ICapacite[] caps) {
    }

    /**
     * Choisit le premier pokemon à envoyer au combat
     * @return le pokemon choisit pour aller au front
     */
    public IPokemon choisitCombattant() {
        //On choisit au hasard un pokemon à envoyer
        int randP = (int) (Math.random() * 6);
        return this.getPokemon(randP);
    }

    /**
     * Choisit le pokemon à envoyer au combat quand un de nos pokemon est tombé KO
     * @param pok le pokemon adverse contre lequel notre pokemon sera envoyé
     * @return Le pokemon choisi pour aller au front contre un certain pokemon
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
     * Choisit l'action à effectuer en fonction de l'état actuel du jeu, cette méthode prend en compte le fait que l'adversaire effectue un coup parfait et renvoie donc le coup qui minimise l'effet de celui adverse
     * @param attaquant notre pokemon actuellement au combat
     * @param defenseur le pokemon adverse actuellement au combat
     * @return l'action idéale pour contrer le coup parfait de l'adversaire
     */
    public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
        ArrayList<double[]> coupsAdverses = Prudente.evaluationP(defenseur, attaquant, ranchAdverse, getRanch());
        double[] efficaciteAdverse = new double[8]; 

        for(int i = 0; i < coupsAdverses.size(); i++) {
            efficaciteAdverse[i] = Prudente.moyenne(coupsAdverses.get(i));
        }

        int iMeilleurCoupAdverse = Prudente.minMax(efficaciteAdverse, 1);
        int iMeilleurCoupIA = Prudente.minMax(coupsAdverses.get(iMeilleurCoupAdverse), 0);
        if(iMeilleurCoupIA < 4) {return new Attaque((Capacite) attaquant.getCapacitesApprises()[iMeilleurCoupIA]);}
        else  {return new Echange(getPokemon(iMeilleurCoupIA - 4), attaquant);}
    }

    /**
	 * Renvoie un ranch identique à celui du dresseur.
	 * @return un ranch identique à celui du dresseur.
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

    /**
     * Cette méthode renvoie une liste d'indicateurs sur l'état du jeu après ce tour 
     * @param attaquant le pokemon de l'IA actuellement au combat
     * @param defenseur le pokemon adverse actuellement au combat
     * @param ranchAllie l'équipe de l'IA
     * @param ranchAdverse l'équipe de l'adversaire
     * @return une liste d'environ 81 indicateurs (plus le nombre retourné est grand, plus l'IA a de chance de gagner et inversement)
     */
    public static ArrayList<double[]> evaluationP(IPokemon attaquant, IPokemon defenseur, IPokemon[] ranchAllie, IPokemon[] ranchAdverse) {
        int tailleLearnedA = Pokedex.tailleReelle(attaquant.getCapacitesApprises());
        int tailleLearnedD = Pokedex.tailleReelle(defenseur.getCapacitesApprises());
        ArrayList<double[]> c1 = new ArrayList<double[]>();

        for(int i = 0; i < 8; i++) { //On remplit notre ArrayList avec 8 tableau de double
            c1.add(new double[9]);
            for(int j = 0; j < 9; j++) { //Pour chaque tableau de double on initialise les valeurs à -1, si plus tard elle n'est pas modifié on pourra alors ne pas la compter dans le calcul de la moyenne car en effet si on remplissait le tableau avec des zéros ces derniers ne seraient pas différenciable avec ceux des indicateurs de partie 0
                c1.get(i)[j] = -1;
            }
        }

        //Si les deux joueurs attaquent
        for(int i = 0; i < tailleLearnedA; i++) {
            for(int j = 0; j < tailleLearnedD; j++) {
                double pourcentageInflige1 = 0, pourcentageInflige2 = 0;
                Attaque atk1 = new Attaque((Capacite) attaquant.getCapacitesApprises()[i]);
                Attaque atk2 = new Attaque((Capacite) defenseur.getCapacitesApprises()[j]);

                if(attaquant.getStat().getVitesse() > defenseur.getStat().getVitesse()) {
                    pourcentageInflige1 = (double) atk1.calculeDommage(attaquant, defenseur) / defenseur.getStat().getPV() * 100;
                    if(pourcentageInflige1 > 100) {pourcentageInflige1 = 100;}
                    if(defenseur.getPourcentagePV() - pourcentageInflige1 > 0) {
                        pourcentageInflige2 = (double) atk2.calculeDommage(defenseur, attaquant) / attaquant.getStat().getPV() * 100;
                        if(pourcentageInflige2 > 100) {pourcentageInflige2 = 100;}
                    }
                }
                else {
                    pourcentageInflige2 = (double) atk2.calculeDommage(defenseur, attaquant) / attaquant.getStat().getPV() * 100;
                    if(pourcentageInflige2 > 100) {pourcentageInflige2 = 100;}
                    if(attaquant.getPourcentagePV() - pourcentageInflige2 > 0) {
                        pourcentageInflige1 = (double) atk1.calculeDommage(attaquant, defenseur) / defenseur.getStat().getPV() * 100;
                        if(pourcentageInflige1 > 100) {pourcentageInflige1 = 100;}
                    }
                }
                double etat = (calculHPTotal(ranchAllie) - pourcentageInflige2) / ((calculHPTotal(ranchAllie) - pourcentageInflige2) + (calculHPTotal(ranchAdverse) - pourcentageInflige1));
                c1.get(i)[j] = etat;
                //System.out.println("Etat AA " + " | " + i + "/" + j + " | " + etat + " attaquant " + attaquant.getNom() + " utilise " + attaquant.getCapacitesApprises()[i].getNom() + " contre " + defenseur.getNom() + " qui utilise " + defenseur.getCapacitesApprises()[j].getNom()  + " // " + attaquant.getStat().getVitesse() + " " + defenseur.getStat().getVitesse());
                LOGGER.info("Etat AA " + " | " + i + "/" + j + " | " + etat + " attaquant " + attaquant.getNom() + " utilise " + attaquant.getCapacitesApprises()[i].getNom() + " contre " + defenseur.getNom() + " qui utilise " + defenseur.getCapacitesApprises()[j].getNom()  + " // " + attaquant.getStat().getVitesse() + " " + defenseur.getStat().getVitesse());
            }  
            //System.out.println(); 
            LOGGER.info("\n");
        }
        
        //Si l'adversaire change de pokemon
        for(int i = 0; i < tailleLearnedA; i++) {
            for(int j = 0; j < 6; j++) {
                if(ranchAdverse[j] != defenseur) {
                    if(ranchAdverse[j].getPourcentagePV() > 0) {
                        Attaque atk1 = new Attaque((Capacite) attaquant.getCapacitesApprises()[i]);
                        double pourcentageInflige = (double) atk1.calculeDommage(attaquant, ranchAdverse[j]) / ranchAdverse[j].getStat().getPV() * 100;
                        if(pourcentageInflige > 100) {pourcentageInflige = 100;}
                        double etat = calculHPTotal(ranchAllie) / (calculHPTotal(ranchAllie) + calculHPTotal(ranchAdverse) - pourcentageInflige);
                        c1.get(i)[j + 3] = etat;
                        //System.out.println("Etat AE " + " | " + (i + 3) + "/" + j + " | " + etat + " " + attaquant.getNom() + " utilise " + attaquant.getCapacitesApprises()[i].getNom() + " contre " + defenseur.getNom() + " qui change pour " + ranchAdverse[j].getNom());
                        LOGGER.info("Etat AE " + " | " + (i + 3) + "/" + j + " | " + etat + " " + attaquant.getNom() + " utilise " + attaquant.getCapacitesApprises()[i].getNom() + " contre " + defenseur.getNom() + " qui change pour " + ranchAdverse[j].getNom());
                    }
                }
            }
            //System.out.println();
            LOGGER.info("\n");
        }

        //Si l'ia change de pokemon
        for(int i = 0; i < tailleLearnedD; i++) {
            for(int j = 0; j < 6; j++) {
                if(ranchAllie[j] != attaquant) {
                    if(ranchAllie[j].getPourcentagePV() > 0) {
                        Attaque atk2 = new Attaque((Capacite) defenseur.getCapacitesApprises()[i]);
                        double pourcentageInflige = (double) atk2.calculeDommage(defenseur, ranchAllie[j]) / ranchAllie[j].getStat().getPV() * 100;
                        if(pourcentageInflige > 100) {pourcentageInflige = 100;}
                        double etat = (calculHPTotal(ranchAllie) - pourcentageInflige) / (calculHPTotal(ranchAllie) + calculHPTotal(ranchAdverse) - pourcentageInflige);
                        c1.get(i + 4)[j] = etat;
                        //System.out.println("Etat EA " + " | " + (i + 7) + "/" + j + " | " + etat + " " + attaquant.getNom() + " change pour " + ranchAllie[j].getNom() + " contre " + defenseur.getNom() + " qui utilise " + defenseur.getCapacitesApprises()[i].getNom());
                        LOGGER.info("Etat EA " + " | " + (i + 7) + "/" + j + " | " + etat + " " + attaquant.getNom() + " change pour " + ranchAllie[j].getNom() + " contre " + defenseur.getNom() + " qui utilise " + defenseur.getCapacitesApprises()[i].getNom());
                    }
                }
                else {
                    c1.get(i + 4)[j] = -1;
                }
            }
            //System.out.println();
            LOGGER.info("\n");
        }
        return c1;
    }

    /**
     * Retourne un indicateur faisant la moyenne de tous les indicateur pour une action certaine action
     * @param indicateurs la liste des indicateurs d'une même action (ex : l'attaque Tranche de Rattata quand l'adversaire change pour un autre pokemon ou qu'il effectue une certaine attaque)
     * @return retourne par exemple la moyenne des 9 indicateurs de l'état du jeu après l'attaque Tranche de Rattata
     */
    public static double moyenne(double[] indicateurs) {
        double total = 0;
        int compteur = 0;

        for(int i = 0; i < 9; i++) { //Méthode de calcul de moyenne classique
            if(indicateurs[i] != -1) { //Si la valeur vaut -1 c'est qu'elle n'est pas à prendre en compte car si on n'avait laissé 0 nous n'aurions pas pu deviner si il s'agit d'un indicateur valant 0 ou d'une valeur non itilialisé de notre tableau
                total += indicateurs[i];
                compteur++;
            }
        }

        return total / compteur;
    }

    /**
     * Permet de connaître le coup qui nous donne le plus de chance de victoire ou celui qui nous en donne le moins parmis une liste entrée en paramètre
     * @param indicateurs la liste des indicateurs d'une certaine attaque
     * @param minOrMax permet de choisir si l'on veut retourner l'indice de la valeur min ou l'incide de la valeur max de cette liste d'indicateurs
     * @return l'indice de la valeur min ou de la valeur max de la liste d'indicateurs entrée en paramètre
     */
    public static int minMax(double[]  indicateurs, int minOrMax) {
        double min = 1, max = 0;
        int iMin = 0, iMax = 0;

        for(int i = 0; i < 8; i++) {
            if(indicateurs[i] != -1) {
                if(indicateurs[i] > max) {
                    max = indicateurs[i];
                    iMax = i;
                }
                if(indicateurs[i] < min) {
                    min = indicateurs[i];
                    iMin = i;
                }
            }
        }
        if(minOrMax == 0) { //Selon la valeur de minOrMax entrée en paramètre on renvoie soit l'indice du min soit l'indice du max
            return iMin;
        }
        return iMax;
    }

    /**
     * Renvoie un indicateur de la situation actuelle du jeu nous permettant de comparer les chances de victoire de chacun des dresseurs
     * @param ranch le ranch dont on veut connaître la vitalité
     * @return un indicateur de la situation de jeu
     */
    private static double calculHPTotal(IPokemon[] ranch) {
        double total = 0;
        for(int i = 0; i < 6; i++) {
            total += ranch[i].getPourcentagePV();
        }
        return total;
    }
}
