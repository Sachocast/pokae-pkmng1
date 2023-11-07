package pokemon;

import java.util.logging.Logger;
import capacite.Capacite;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokemon;
import interfaces.IStat;
import pokedex.Pokedex;

/**
 * @author Royer Baptiste et Castillejos Sacha
 * 
 * Un pokémon constitue le ranch d'un dresseur.
 * Cette classe implémente l'interface IPokemon.
 * 
 */

public class Pokemon implements IPokemon {
    private final static Logger LOGGER = Logger.getLogger("");
    
	/**
	 * L'espece du pokémon.
	 */
    private IEspece espece;
    /**
     * Les stats du pokémon.
     */
    private IStat stat = new Stat();
    /**
     * L'experience du pokemon.
     */
    private double experience;
    /**
     * Le niveau du pokémon.
     */
    private int niveau;
    /**
     * l'id du pokémon.
     */
    private int id;
    /**
     * Le nom du pokémon.
     */
    private String nom;
    /**
     * Le pourcentage de PV du pokémon.
     */
    private double pourcentagePV = 100.0;
    /**
     * Le set de capacité que peut utilisé le pokémon en combat.
     */
    private ICapacite[] capacitesApprises = new Capacite[4];
    /**
     * Les EVs du pokémon.
     */
    private IStat evs = new Stat();
    /**
     * Les IVs du pokémon.
     */
    private IStat ivs = new Stat();
    /**
     * Le nombre d'échanges disponible pour un pokemon.
     */
    private int echangesDispo = 5;

    /**
     * Constructeur de Pokemon.
     * 
     * @param espece est le l'espece du pokémon.
     * @param pokedex un pokédex.
     */
    public Pokemon(String espece, Pokedex pokedex) {
        Stat ivs = new Stat();
        this.espece = pokedex.getInfo(espece);
        this.nom = this.espece.getNom();
        this.niveau = this.espece.getNiveauDepart();
        this.pourcentagePV = 100;
        this.experience = 0.8 * Math.pow(niveau, 3);
        
        ivs.initIvs();
        this.ivs = ivs;
        this.evs = new Stat(0, 0, 0, 0, 0);
        this.calculStats();
        this.soigne();
    }

    /**
     * Constructeur vide de Pokemon.
     * 
     * @param espece est le l'espece du pokémon.
     * @param pokedex un pokédex.
     */
    public Pokemon(){}

    /**
     * Calcule les stats du pokémon.
     */
    public void calculStats() {
        this.stat.setForce((((2 * (this.espece.getBaseStat().getForce() + this.ivs.getForce()) + (this.evs.getForce() / 4)) * this.niveau) / 100) + 5);
        this.stat.setDefense((((2 * (this.espece.getBaseStat().getDefense() + this.ivs.getDefense()) + (this.evs.getDefense() / 4)) * this.niveau) / 100) + 5);
        this.stat.setSpecial((((2 * (this.espece.getBaseStat().getSpecial() + this.ivs.getSpecial()) + (this.evs.getSpecial() / 4)) * this.niveau) / 100) + 5);
        this.stat.setVitesse((((2 * (this.espece.getBaseStat().getVitesse() + this.ivs.getVitesse()) + (this.evs.getVitesse() / 4)) * this.niveau) / 100) + 5);
        this.stat.setPV((((2 * (this.espece.getBaseStat().getPV() + this.ivs.getPV()) + (this.evs.getPV() / 4)) * this.niveau) / 100) + this.niveau + 10);
    }

    /**
     * Retourne les stats du pokémon.
     * 
     * @return les stats du pokémon
     */
    public IStat getStat() {
        return this.stat;
    }

    /**
     * Retourne l'experience du pokémon.
     * 
     * @return l'experience du pokémon
     */
	public double getExperience() {
        return this.experience;
    }
	
	/**
	 * Retourne le niveau du pokémon.
	 * 
	 * @returnle niveau du pokémon
	 */
    public int getNiveau() {
        return this.niveau;
    }

    /**
     * Retourne l'id du pokémon.
     * 
     * @return l'id du pokémon
     */
	public int getId() {
        return this.id;
    }

	/**
	 * Retourne le nom du pokémon.
	 * 
	 * @return le nom du pokémon
	 */
	public String getNom() {
        return this.nom;
    }

	/**
	 * Retourne le pourcentage de PV du pokémon.
	 * 
	 * @return le pourcentage de PV du pokémon
	 */
	public double getPourcentagePV() {
        return this.pourcentagePV;
    }
	
	/**
	 * Retourne l'espece du pokémon.
	 * 
	 * @return l'espece du pokémon
	 */
	public IEspece getEspece() {
        return this.espece;
    }

	/**
	 * Retourne le set de capacité que peut utilisé le pokémon en combat.
	 * 
	 *@retourne les capacités du pokémon.
	 */
    public ICapacite[] getCapacitesApprises(){
        return this.capacitesApprises;
    }

    /**
     * Retourne les EVS du pokémon.
     * 
     * @return les EVs
     */
    public IStat getEvs() {
        return this.evs;
    }

    /**
     * Retourne les IVs du pokémon.
     * 
     * @return les IVs
     */
    public IStat getIvs() {
        return this.ivs;
    }

    /**
     * Change le pourcentage de PV du pokémon.
     * 
     * @param pourcentagePV est la nouvelle valeur de pourcentagePV
     */
    private void setPourcentagePV(double pourcentagePV) {
        this.pourcentagePV = pourcentagePV;
    }

    /**
     * 
     * @param adversaire le pokemon adverse vaicu donnant les evs
     */
    public void gainEV(Pokemon adversaire) {
        this.evs.setPV(this.evs.getPV() + adversaire.getEspece().getGainsStat().getPV());
        this.evs.setForce(this.evs.getForce() + adversaire.getEspece().getGainsStat().getForce());
        this.evs.setDefense(this.evs.getDefense() + adversaire.getEspece().getGainsStat().getDefense());
        this.evs.setSpecial(this.evs.getSpecial() + adversaire.getEspece().getGainsStat().getSpecial());
        this.evs.setVitesse(this.evs.getVitesse() + adversaire.getEspece().getGainsStat().getVitesse());
    }

    public void retireEchange() {
        this.echangesDispo -= 1;
    }

    /**
     * Permet au pokémon de gagner de l'experience en fonction du pokémon qu'il affronte
     * 
     * @param est le pokémon adverse.
     */
	public void gagneExperienceDe(IPokemon pok) {
        if(this.niveau < 100) {
            this.experience += Math.round((1.5 * pok.getNiveau() * pok.getEspece().getBaseExp())/7);
            //System.out.println(this.getNom() + " gagne " + Math.round((1.5 * pok.getNiveau() * pok.getEspece().getBaseExp())/7) + " points d'expérience !");
            LOGGER.info(this.getNom() + " gagne " + Math.round((1.5 * pok.getNiveau() * pok.getEspece().getBaseExp())/7) + " points d'expérience !");
    
            if(aChangeNiveau() == true){
                this.niveau = Math.toIntExact(Math.round(Math.cbrt(this.experience/0.8)));
                //System.out.println(this.getNom() + " monte au niveau " + this.getNiveau() + " !");
                LOGGER.info(this.getNom() + " monte au niveau " + this.getNiveau() + " !");
                this.vaMuterEn(this.getEspece().getEvolution(0));
            }
        }
        this.calculStats();
    }
 
	/**
	 * Retourne si le pokémon a changé de niveau.
	 * 
	 * @return un boolean
	 */
    public boolean aChangeNiveau() {
        if(0.8 * Math.pow(this.niveau + 1, 3) <= this.experience){ 
            return true; 
        }
        else { return false; }
    }

    /**
     * Retourne si le pokémon peut évoluer.
     * 
     *  @return un boolean
     */
	public boolean peutMuter() {
        if(this.espece.getEvolution(this.niveau).getNiveauDepart() <= this.niveau){
            return true;
        }
        else { return false; }
    }

	/**
	 * Gére l'évolution du pokémon.
	 * 
	 * @param esp est l'espece qui correspond à l'évolution du pokémon.
	 */
	public void vaMuterEn(IEspece esp) {
        if(this.getEspece().getEvolution(0) != null) { 
            if(this.peutMuter() == true){
                System.out.println("!! " + this.getNom() + " évolue en " + esp.getNom() + " !!");
                this.espece = esp;
                this.nom = esp.getNom();
                this.calculStats();
            }
        }
    }
	
	/**
	 * Permet de remplacer une capacité si le pokémon en connait déjà quatre.
	 * 
	 * @param i est la capacité qui se fait remplacer.
	 * @param cap est la nouvelle capacité.
	 */
    public void remplaceCapacite(int i, ICapacite cap) throws Exception {
        this.capacitesApprises[i] = cap;
    }

    /**
     * Permet au pokémon d'apprendre un nouveau set de capacités.
     */
	public void apprendCapacites(ICapacite[] caps) {
        this.capacitesApprises = caps;
    }

	/**
	 * Gére l'action lorsque le pokémon subit une attaque.
	 * 
	 * @param pok est le pokémon qui subit l'attaque.
	 * @param atk est l'attaque que le pokémon subit. 
	 */
	public void subitAttaqueDe(IPokemon pok, IAttaque atk) {
        double pourcentageSubit = (double) atk.calculeDommage(pok, this) / this.getStat().getPV() * 100;
        atk.utilise();
        if(pourcentagePV - pourcentageSubit < 1) {setPourcentagePV(0);} 
        else {setPourcentagePV( pourcentagePV - pourcentageSubit); }
    }
	
	/**
	 * Gére les dégats de Recul de certaines capacités.
	 * 
	 * @param degats degats de recul subit.
	 */
    public void subitDegatsRecul(double degats) {
        double pourcentageSubit = degats / this.getStat().getPV() * 100;
        this.setPourcentagePV(pourcentagePV - pourcentageSubit);
    }

    /**
     * Retourne si le pokémon est évanoui ou non.
     * 
     * @return un boolean
     */
	public boolean estEvanoui() {
        if (this.pourcentagePV <= 0) { 
            return true;
        }

        if (this.echangesDispo <= 0) {
            return true;
        }
        return false;
    }
	
	/**
	 * Permet de soigner un pokémon.
	 */
	public void soigne() {
        setPourcentagePV(100.0);
        echangesDispo = 5;
    }

    /**
	 * Permet de cloner un pokémon.
	 */
    public Pokemon getClone() {
        //Tous les attributs Objets doivent être recréé pour ne pas que ceux du clone pointe sur ceux du "vrai" pokemon (ivs, evs, capacités ect...)
        Pokemon pokemon = new Pokemon();
        pokemon.espece = new Espece(((Espece) espece).getNoPokedex());
        pokemon.nom = nom;
        pokemon.niveau = niveau;
        pokemon.experience = experience;
        pokemon.niveau = niveau;
        pokemon.pourcentagePV = pourcentagePV;
        pokemon.stat = new Stat(stat.getPV(), stat.getForce(), stat.getDefense(), stat.getSpecial(), stat.getVitesse());
        pokemon.ivs = new Stat(ivs.getPV(), ivs.getForce(), ivs.getDefense(), ivs.getSpecial(), ivs.getVitesse());
        pokemon.evs = new Stat(evs.getPV(), evs.getForce(), evs.getDefense(), evs.getSpecial(), evs.getVitesse());

        for(int i = 0; i < 4; i++) {
            if(capacitesApprises[0] != null) {
                pokemon.capacitesApprises[0] = new Capacite(capacitesApprises[0].getNom());
            }
        }

        return pokemon;
    }
}
