package capacite;
import java.util.Random;
import interfaces.IAttaque;
import interfaces.IPokemon;
import pokedex.Pokedex;
import pokemon.Pokemon;

/**
 * @author Royer Baptiste
 * Une attaque est une action du Pokemon durant une bataille.
 * Cette classe implémente l'interface IAttaque.
 */
public class Attaque implements IAttaque{
	/**
	 * La capacité utilisé lors de l'attaque.
	 */
	private Capacite capaciteUtilise;
	/**
	 * Ce sont les dégâts que fera l'attaque.
	 */
	private int dommage;
	
	/**
	 * Constructeur vide d'Attaque.
	 */
	public Attaque(){};
	
	/**
	 * Constructeur d'Attaque.
	 * 
	 * @param capa est la capacité qui est utilisé lors de l'attaque.
	 */
	public Attaque(Capacite capa) {
		this.capaciteUtilise = capa;
	};

	/**
	 * Retourne la capacité utilisé lors de l'attaque.
	 * 
	 * @return La capacité utilisé.
	 */
	public Capacite getCapaciteUtilise(){
		return this.capaciteUtilise;
	}
    
	/**
	 * Retourne les dommages de l'attaque.
	 * 
	 * @return les dommages de l'attaque.
	 */
	public int getDommage(){
		return this.dommage;
	}

	/**
	 * Vérifie si l'attaque a atteint sa cible ou non en fonction de la précision de sa capacité.
	 * 
	 * @return true ou false.
	 */
    public boolean getReussite() {
    	boolean reussite = true;
    	double start = 0;
    	double end = 1;
    	double random = new Random().nextDouble();
    	double result = start + (random * (end - start));

    	if(result > this.capaciteUtilise.getPrecision()) {
    		reussite = false;
    	}
    	return reussite;
    }

    /**
     * Retourne les dégâts supplémentaires liés au STAB. 
     * Le STAB est une mécanique qui multiplie par 1.5 les dégâts d'une capacité si son type correspond a celui de son lanceur.
     * 
     * @param lanceur est le pokemon qui lance l'attaque.
     * @return 1.5 si l'attaque a un STAB sinon 1.0.
     */
    public Double stab(IPokemon lanceur) {
		String typeCapacite = this.capaciteUtilise.getType().getNom();
		String type1Pokemon = lanceur.getEspece().getTypes()[0].getNom();
		String type2Pokemon = lanceur.getEspece().getTypes()[1].getNom();

    	if(typeCapacite.equals(type1Pokemon) || typeCapacite.equals(type2Pokemon)) {
    		return 1.5;
    	}

    	else return 1.0;
    }
    
    /**
     * Retourne la statistique d'attaque ou de special du lanceur en fonction de la catégorie de la capacite utilisée.
     * 
     * @param lanceur est le pokemon qui lance l'attaque.
     * @return la statistique d'attaque ou de special du lanceur en fonction de la catégorie de la capacite utilisée.
     */
    public int getAtt(IPokemon lanceur) {

		if(this.capaciteUtilise.getCategorie().isSpecial() == true) {
			return lanceur.getStat().getSpecial();
		}

		else {
			return lanceur.getStat().getForce();
		}
    }
    
    /**
     * Retourne la statistique de defense ou de special du receveur en fonction de la catégorie de la capacite utilisée.
     * 
     * @param receveur est le pokemon qui reçoit l'attaque.
     * @return la statistique de defense ou de special du receveur en fonction de la catégorie de la capacite utilisée.
     */
    public int getDef(IPokemon receveur) {

		if(this.capaciteUtilise.getCategorie().isSpecial() == true) {
			return receveur.getStat().getSpecial();
		}

		else {
			return receveur.getStat().getDefense();
		}
    }

    /**
     * Vérification et traitement des cas particuliers.
     * 
     * @param lanceur est le pokemon qui lance l'attaque.
     * @param receveur est le pokemon qui reçoit l'attaque.
     * @return true si la capacite utilisé lors de l'attaque est spéciale.
     */
    public boolean casParticuliers(IPokemon lanceur, IPokemon receveur) {
    	boolean verification = false;

    	if(this.capaciteUtilise.getNom().equals("Croc Fatal")) {
			
    		if(receveur.getPourcentagePV() > 1) {
    			this.dommage = Math.toIntExact(Math.round(receveur.getStat().getPV() * receveur.getPourcentagePV() / 200));
    		}

    		else this.dommage = 1;
			verification = true;
    	}

    	else if(this.capaciteUtilise.getNom().equals("Vague Psy")){
    		double start = 0, end = 10, random = new Random().nextDouble(), X = start + (random * (end - start));
    		
			this.dommage = (int) ((lanceur.getNiveau()) * (X + 5) / 10);
			verification = true;
    	}

    	else if(this.capaciteUtilise.getNom().equals("Patience")){ //Impossible à coder pour le moment car on ne possède pas de systeme de tour
			verification = true;
    	}

    	else if(this.capaciteUtilise.getNom().equals("Ombre Nocturne")){
    		this.dommage = lanceur.getNiveau();
			verification = true;
    	}  

    	else if(this.capaciteUtilise.getNom().equals("Draco-Rage")){
    		this.dommage = 40;
			verification = true;
    	}

    	else if(this.capaciteUtilise.getNom().equals("Frappe Atlas")){
    		this.dommage = lanceur.getNiveau();
			verification = true;
    	}

    	else if(this.capaciteUtilise.getNom().equals("Sonic Boom")) {
    		this.dommage = 20;
    		verification = true;
    	}

		else if(this.capaciteUtilise.getNom().equals("Guillotine") || this.capaciteUtilise.getNom().equals("Abîme")) {
    		this.dommage = receveur.getStat().getPV();
    		verification = true;
    	}

		else if(this.capaciteUtilise.getNom().equals("Lutte")) {
    		this.dommage = (int) (((((lanceur.getNiveau() * 0.4 + 2) * getAtt(lanceur) * this.capaciteUtilise.getPuissance()) / (getDef(receveur) * 50)) + 2)); // Le STAB et l'efficacité n'affectent pas Lutte, le calcul peut donc se faire ici
			((Pokemon) lanceur).subitDegatsRecul(this.dommage / 2);
			verification = true;
    	}

    	else if(this.capaciteUtilise.getNom().equals("Riposte")) { //Impossible à coder pour le moment car on ne possède pas de systeme de tour
    	}

    	return verification;
    }
    
    /**
     * Retourne un int correspondant aux dégats d'une attaque d'un pokemon sur un autre.
     * 
     * @param lanceur est le pokemon qui lance l'attaque.
     * @param receveur est le pokemon qui reçoit l'attaque.
     * @return un int correspondant aux dégats d'une attaque d'un pokemon sur un autre.
     */
    public int calculeDommage(IPokemon lanceur, IPokemon receveur){ //Renvoie un int correspondant aux dégats d'une attaque d'un pokemon sur un autre
    	
		if(casParticuliers(lanceur, receveur) == false){ //On check si la capacite a des effets particulier (OHKO, lutte ect...) car le traitement de ces capacites a déjà été fait plus haut	
			boolean reussite = getReussite(); //On check si l'attaque a atteint sa cible ou non
			Double efficacite = Pokedex.calculDoubleEfficacite(this.capaciteUtilise.getType(), receveur.getEspece().getTypes()); //On calcul l'efficacité de la capacite en fonction de son type et de celui du pokemon qui la reçoit
			Double stab = stab(lanceur); //On met les potentiels dégâts supplémentaires du STAB dans une variable 

			if (efficacite == 0.0) { //On vérifie si la capacite peut toucher ou non son adversaire en fonction de son type (ex : Tranche n'affectera pas un pokemon de type Spectre)
				this.dommage = 0;
				//System.out.println("Aucun effet");
			}

			else {
				if(reussite == false){ //On regarde si la capacite a touché son adversaire ou non
					return this.dommage = 0;
				} 

				//Enfin on calcul les dégâts
				this.dommage = (int) (((((lanceur.getNiveau() * 0.4 + 2) * getAtt(lanceur) * this.capaciteUtilise.getPuissance()) / (getDef(receveur) * 50)) + 2) * (efficacite * stab));
			}
		}
		return this.dommage;
    }
    
    /**
     * Retire 1 PP à la capacité utilisé.
     */
	public void utilise(){
		this.capaciteUtilise.setPP(this.capaciteUtilise.getPP() - 1);
    }
	
}
