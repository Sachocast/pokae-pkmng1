package pokedex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import capacite.Capacite;
import interfaces.ICapacite;
import interfaces.IEspece;
import interfaces.IPokedex;
import interfaces.IPokemon;
import interfaces.IType;
import pokemon.Espece;
import pokemon.Pokemon;

/**
 * @author Royer Baptiste et Castillejos Sacha
 * 
 * Un pokedex contient toutes les informations sur les especes et les capacités.
 * 
 */

public class Pokedex implements IPokedex{
	/**
	 * HashMap qui contient la liste de toutes les especes rangées par numéro.
	 */
    private HashMap<Integer, Espece> listeEspece = new HashMap<Integer, Espece>();
	/**
	 * HashMap qui contient la liste de toutes les capacités rangées par numéro.
	 */
    private HashMap<String, Capacite> listeCapacite = new HashMap<String, Capacite>();

    /**
     * Constructeur de Pokedex.
     */
    public Pokedex(){
        for(int i = 1; i < 152; i++){
            this.listeEspece.put(i, new Espece(i));
        }

        for(int i = 1; i < 111; i++){
            Capacite capacite = new Capacite(i);
            this.listeCapacite.put(capacite.getNom(), capacite);
        } 
    }

    /**
     * Retourne la HashMap contenant la liste des especes.
     * 
     * @return la liste des especes
     */
    public HashMap<Integer, Espece> getListeEspece(){
        return this.listeEspece;
    }

    /**
     * Retourne la HashMap contenant la liste des capacités.
     * 
     * @return la liste des capacités
     */
    public HashMap<String, Capacite> getListeCapacite(){
        return this.listeCapacite;
    }

    /**
     * Retourne une especes.
     * 
     * @param nomEspece est le nom de l'espece recherché.
     * @return l'espece recherché
     */
    public IEspece getInfo(String nomEspece) {
        for(int i = 1; i < 152; i++){
            if(this.listeEspece.get(i).getNom().equals(nomEspece)){
                Espece poke = this.listeEspece.get(i);
                return poke;
            }
        }
        throw new RuntimeException("!! Le pokemon n'a pas été trouvé !!");
    }

    /**
     * Retourne une especes.
     * 
     * @param noPokedex0 est le numéro de l'espece recherché.
     * @return l'espece recherché
     */
    public IEspece getInfo(int noPokedex) {
		for(int i = 1; i < 152; i++){
            if(this.listeEspece.get(i).getNoPokedex() == noPokedex){
                Espece poke = this.listeEspece.get(i);
                return poke;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Retourne la colonne dans le csv du type passé en paramètre.
     * 
     * @param type est le type recherché.
     * @return un numéro correspondant à la colonne
     */
	public static int getColonneType(String type) {
		int compteur = 0;
		String compare = "";
		
		try (Scanner scan = new Scanner(new File("docs/efficacites.csv")).useDelimiter(";")){
			scan.next();

			while(!compare.equals(type) && scan.hasNext() == true){
				compare = scan.next();
				compteur++;
			}
			scan.close();

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		return compteur; 
	} 

	public Double getEfficacite(IType attaque, IType defense) {
		return null;
	}

	/**
	 * Renvoie l'efficacité d'un type sur un autre.
	 * 
	 * @param attaque est le type qui attaque.
	 * @param defense est le type qui defend.
	 * .
	 * @return un double representant l'éfficacité
	 */
	public static Double calculEfficacite(IType attaque, IType defense) {
		//Renvoie l'efficacité d'un type sur un autre
		int ligneTypeDef = getColonneType(defense.getNom()), i = 0;
		int colonneTypeAtk = getColonneType(attaque.getNom());
		double valeurEfficacite = 0.0;

		try (Scanner scan = new Scanner(new File("docs/efficacites.csv")).useDelimiter(";")){
			scan.nextLine();

			while(i < ligneTypeDef - 1 && scan.hasNextLine() == true) {
				scan.nextLine();
				i++;
			}	
			
			scan.next();
			i = 0;

			while(i < colonneTypeAtk && scan.hasNext() == true) {
				valeurEfficacite = Double.parseDouble(scan.next());
				i++;
			}
			
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
		return valeurEfficacite;
    }
	
	/**
	 * Renvoie l'efficacité d'un type sur une combinaison de deux autres.
	 * 
	 * @param attaque est le type qui attaque.
	 * @param defense est le type qui defend.
	 * 
	 * @return un double representant l'éfficacité
	 */
	public static Double calculDoubleEfficacite(IType attaque, IType[] defense) {
		//Renvoie l'efficacité d'un type sur une combinaison de deux autres
		Double efficacitePremierType = 0.0, efficaciteDeuxiemeType = 0.0, efficaciteFinale = 0.0;
		
		if(defense[1].getNom().equals("")) { //Si le deuxième type n'existe pas on appelle simplement la méthode getEfficacite 
			efficaciteFinale = calculEfficacite(attaque, defense[0]);
		}

		else {
			efficacitePremierType = calculEfficacite(attaque, defense[0]);
			efficaciteDeuxiemeType = calculEfficacite(attaque, defense[1]);
			efficaciteFinale = efficacitePremierType * efficaciteDeuxiemeType;
		}
	return efficaciteFinale;
	}
	
	/**
	 * Retourne une capacité.
	 * 
	 * @param nom est le nom de la capacité.
	 * @return une capacité.
	 * @throws IndexOutOfBoundsException
	 */
	public ICapacite getCapacite(String nom) throws IndexOutOfBoundsException {

        for(Capacite capa : this.listeCapacite.values()){
			System.out.println(capa.getNom());
			if(capa.getNom().equals(nom)){
				return capa;
			}
		}
		throw new IndexOutOfBoundsException("!! Capacite non trouvé !!");
    }

	/**
	 * Retourne une capacité.
	 * 
	 * @param n est le numéro de la capacité.
	 * @return une capacité
	 */
	public ICapacite getCapacite(int n) {
		for(Capacite capa : this.listeCapacite.values()){
			if(capa.getNumCapacite() == n){
				return capa;
			}
		}
		throw new IndexOutOfBoundsException("!! Capacite non trouvé !!");
    }
	
	/**
	 * Retourne la liste des especes de pokemon dont le niveau de départ est 1.
	 * @return la liste des especes de pokemon dont le niveau de départ est 1
	 */
	public Espece[] getLvl1Espece() {
		//Retourne la liste des especes de pokemon dont le niveau de départ est 1
		int nbPokemonLvl1 = 0;
		Espece[] listePokeLvl1 = new Espece[100];

		for(Espece espece : this.listeEspece.values()){
			if(espece.getNiveauDepart() == 1){
				listePokeLvl1[nbPokemonLvl1] = espece;
				nbPokemonLvl1++;
			}
		}
		return listePokeLvl1;
	}

	/**
	 * Retourne la taille réelle d'une liste de capacité, c-à-d le nombre d'éléments qu'elle possède
	 * @return la taille réelle de cette liste
	 */
	public static int tailleReelle(ICapacite[] caps) {
		int tailleReelle = 0;

		for(int i = 0; i < caps.length; i++) {
			if(caps[i] != null) {
				tailleReelle++;
			}
		}

		return tailleReelle;
	}

	/**
	 * Créer aléatoirement un ranch.
	 * 
	 * @return un ranch
	 */
    public IPokemon[] engendreRanch() {
		int ranchIndex = 0;
        IPokemon[] ranch = new IPokemon[6];
		Espece[] listelvl1 = this.getLvl1Espece(); 

		for(int i = 0; i < 6; i++){
			int randomIndex = ThreadLocalRandom.current().nextInt(0, 78 + 1);
			ranch[ranchIndex] = new Pokemon(listelvl1[randomIndex].getNom(), this);
			ranchIndex++;
		}
		return ranch;
    }
}
