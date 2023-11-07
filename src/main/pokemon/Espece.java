package pokemon;
import interfaces.IEspece;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import capacite.Capacite;
import interfaces.ICapacite;
import interfaces.IType;
import pokedex.Type;
import interfaces.IStat;

/**
 * @author Royer Baptiste et Castillejos Sacha
 * 
 * Cette classe contient les informations sur une espece précise de pokémon.
 * Cette classe implémente l'interface IEspece.
 * 
 */

public class Espece implements IEspece{
	/**
	 * Numéro de pokedex de l'espece.
	 */
    private int noPokedex;
    /**
     * Nom de l'espece.
     */
    private String nom;
    /**
     * Stats de base de l'espece.
     */
    private IStat baseStat = new Stat();
    /**
     * Les stats que fait gagner le pokémon lorsqu'il perd.
     */
    private IStat gainStat = new Stat();
    /**
     * l'experience de base de l'espece.
     */
    private int baseExp;
    /**
     * Le niveau de départ de l'espece.
     */
    private int niveauDepart;
    /**
     * Le ou les types de l'espece. 
     */
    private IType[] types = new IType[2];
    /**
     * L'évolution de l'espece.
     */
    private IEspece evolution;
    /**
     * Les capacité que peut apprendre l'espece.
     */
    private ICapacite[] capSet = new ICapacite[111];
    /**
     * Hashmap triant le niveau auquel l'espece peut apprendre une capacité.
     */
    private HashMap<ICapacite, Integer> lvlLearnedCapSet = new HashMap<ICapacite, Integer>();

    /**
     * Constructeur de Espece.
     * 
     * @param noPokedex est le numéro de l'espece
     */
    public Espece(int noPokedex){
        File fichier = new File("docs/especeCapa.csv");
		
		try {
			Scanner scan = new Scanner(fichier).useDelimiter(";");

			scan.nextLine();
			while(scan.hasNextLine() == true) {
                if(noPokedex == Integer.parseInt(scan.next())){
                    this.noPokedex = noPokedex;
                    this.nom = scan.next();
                    this.baseStat.setPV(Integer.parseInt(scan.next()));
                    this.baseStat.setForce(Integer.parseInt(scan.next()));
                    this.baseStat.setDefense(Integer.parseInt(scan.next()));
                    this.baseStat.setSpecial(Integer.parseInt(scan.next()));
                    this.baseStat.setVitesse(Integer.parseInt(scan.next()));
                    this.baseExp = Integer.parseInt(scan.next());
                    this.gainStat.setPV(Integer.parseInt(scan.next()));
                    this.gainStat.setForce(Integer.parseInt(scan.next()));
                    this.gainStat.setDefense(Integer.parseInt(scan.next()));
                    this.gainStat.setSpecial(Integer.parseInt(scan.next()));
                    this.gainStat.setVitesse(Integer.parseInt(scan.next()));
                    this.types[0] =  new  Type(scan.next());
                    this.types[1] =  new  Type(scan.next());
                    this.niveauDepart = Integer.parseInt(scan.next());

                    int peutEvoluer = Integer.parseInt(scan.next());
                    if(peutEvoluer != 0){
                        this.evolution = new Espece(noPokedex + 1);
                    }

                    int i = 0, stop = 0;
                    scan.next();
                    String suivant = scan.next();

                    while(stop == 0) {
                        if(suivant.equals("") == false){
                            this.capSet[i] = new Capacite(suivant);
                            this.lvlLearnedCapSet.put(this.capSet[i], Integer.parseInt(scan.next()));
                            suivant = scan.next();
                            i++;
                        }
                        else{
                            stop = 1;
                        }
                    }
                }
				scan.nextLine();
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
    }

    /**
     * Retourne le numéro de l'espece.
     * 
     * @return le numéro de l'espece
     */
    public int getNoPokedex(){
        return this.noPokedex;
    }

    /**
     * Retourne les stats de base de l'espece.
     * 
     * @return les stats de base
     */
    public IStat getBaseStat(){
        return this.baseStat;
    }

    /**
     * Retourne le nom de l'espece.
     * 
     * @return le nom
     */
	public String getNom(){
        return this.nom;
    }

	/**
	 * Retourne le niveau de depart de l'espece.
	 * 
	 * @return le niveau de depart
	 */
	public int getNiveauDepart(){
        return this.niveauDepart;
    }

	/**
	 * Retourne l'experience de base d'une espece.
	 * 
	 * @return BaseExp
	 */
	public int getBaseExp(){
        return this.baseExp;
    }

	/**
	 * Retourne le gain de stat que l'espece procure.
	 * 
	 * @return gainStat
	 */
	public IStat getGainsStat(){
        return this.gainStat;
    }

	/**
	 * Retourne les capacité que l'espece peut apprendre.
	 * 
	 * @return capSet
	 */
	public ICapacite[] getCapSet(){
        return this.capSet;
    }

    public ICapacite[] getCapSetAt(int niveau) {
        ICapacite[] apprenables = new Capacite[75];

        for(int i = 0; i < 75; i++) {
            if(capSet[i] != null) {
                if(lvlLearnedCapSet.get(capSet[i]) <= niveau) {
                    apprenables[i] = capSet[i];
                }
            }
        }
        return apprenables;
    }

	/**
	 * Retourne l'évolution de l'espece.
	 * 
	 * @return l'évolution
	 */
	public IEspece getEvolution(int niveau) {
        return this.evolution;
    }  

	/**
	 * Retourne les types du pokemon.
	 * 
	 * @return types
	 */
	public IType[] getTypes(){
        return this.types;
    }

	/**
	 * Retourne une HashMap qui correspond à toutes les capacités que l'Espece de pokemon peut apprendre en clé 
	 * et en valeur le niveau auquel elle l'apprend.
	 * 
	 * @return lvl1LearnedCapSet
	 */
    public HashMap<ICapacite, Integer> getLvlLearnedCapSet(){
        return this.lvlLearnedCapSet;
    }

    /**
     * Lit l'url entré en paramètre et renvoie les informations obtenues à partir de se dernier en s'y connectant
     * 
     * @param url 
     * 
     * @return les informations obtenues
     */
    static public String getAPI(URL url) {

        HttpURLConnection urlConnect;
        try {
            urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.connect();
            BufferedReader urlReader = new BufferedReader(new InputStreamReader(urlConnect.getInputStream()));
            StringBuilder responseStrBuilder = new StringBuilder();
    
            String inputStr ="";
            while ((inputStr = urlReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            inputStr = responseStrBuilder.toString();
            urlReader.close();
            return inputStr;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Méthode qui nous a permit de constituer le CSV contenant toutes les capacités que chaque pokemon peut apprendre à un certain niveau.
     * 
     * @param listeCapacite
     */
    public void initCapSet(HashMap<String, Capacite> listeCapacite) {

        JSONObject indexJson;
        try {
            indexJson = (JSONObject) new JSONParser().parse(new FileReader("docs/listePokemon.json"));
            
            JSONArray listePokeJson = (JSONArray) indexJson.get("results");
            JSONObject infoEspece = (JSONObject) listePokeJson.get(this.noPokedex - 1);
            String urlPoke = (String) infoEspece.get("url");
            URL url = (URL) new URL(urlPoke);
            JSONObject infoPokemon = (JSONObject) new JSONParser().parse(getAPI(url));     
            JSONArray moves = (JSONArray) infoPokemon.get("moves");
                    
            int index = 0;
            for(int i = 0; i < moves.size(); i++){
                JSONObject move = (JSONObject) moves.get(i);
                JSONObject capacite = (JSONObject) move.get("move");
                String urlCapa = (String) capacite.get("url");
                JSONArray detailCapacite = (JSONArray) move.get("version_group_details");
                JSONObject versionDetail = (JSONObject) detailCapacite.get(0);
                Long levelLearned = (Long) versionDetail.get("level_learned_at");
    
                JSONObject infoCapacite = (JSONObject) new JSONParser().parse(getAPI((URL) new URL(urlCapa))); 
                JSONArray names = (JSONArray) infoCapacite.get("names");
                JSONObject language = (JSONObject) names.get(3);
                String nomAttaque = (String) language.get("name");
    
                for(Capacite capa : listeCapacite.values()){
                    if(capa.getNom().equals(nomAttaque)){
                        this.capSet[index] = capa;
                        this.lvlLearnedCapSet.put((ICapacite) capa, Integer.parseInt(levelLearned.toString()));
                        index ++;
                    }
                }        
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}