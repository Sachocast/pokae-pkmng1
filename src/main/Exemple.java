import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import combat.Combats;
import combat.ConfigLog;
import combat.Dresseur;
import combat.Prudente;
import combat.Aleatoire;
import pokedex.Pokedex;

public class Exemple {
	private final static Logger LOGGER = Logger.getLogger("");

    public static void main(String[] args) {

    	
    	ConfigLog.setup();
        LOGGER.setLevel(Level.INFO);
        Pokedex pokedex = new Pokedex();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS"); //Permet d'afficher les mili secondes dans le temps d'exécution d'une méthode à tester
        Date date1 = new Date();

        Dresseur iaA = new Aleatoire("alea", pokedex);
        Dresseur iaP = new Prudente("prud", pokedex);
        Combats combat = new Combats(iaA, iaP);
        combat.commence(); //Combat entre une IA Aléatoire et une IA Prudente
        
        Date date2 = new Date();
        System.out.println(dateFormat.format(date1) + " / " + dateFormat.format(date2)); //Permet de connaître le temps d'éxecution de chaque méthode que l'on veut test

        // System.out.println("\n====================== COMBAT IA ALEATOIRE VS IA ALEATOIRE ======================\n");

        // Strategy ia1 = new Strategy("ia1", pokedex);
        // Strategy ia2 = new Strategy("ia2", pokedex);
        // Combats combat = new Combats(ia1, ia2);
        // combat.commence();

        // System.out.println("\n====================== COMBAT JOUEUR VS IA ======================\n");

        // Dresseur j3 = new Dresseur("Joueur3",pokedex);
        // Dresseur i3 = new Aleatoire("IA3",pokedex);

        // Combats combat2 = new Combats(j3, i3); //Combat entre 2 joueurs
        // combat2.commence();

        // System.out.println("\n====================== COMBAT ENTRE JOUEURS ======================\n");

        // Dresseur j1 = new Dresseur("Joueur1");
        // Dresseur j2 = new Dresseur("Joueur2");

        // Combats combat3 = new Combats(j1, j2); //Combat entre 2 joueurs
        // combat3.commence();

    
        
        /** ========================================= Comment on a créé especeCapa.csv ========================================= //

        Pokedex pokedex = new Pokedex();
        File newFile = new File("docs/especeCapa.csv");
        PrintWriter out = new PrintWriter(newFile);

        out.println("Numéro;Nom;PV-base;Force-base;Défense-base;Spécial-base;Vitesse-base;Exp base;EV-PV;EV-Force;EV-defense;EV-special;EV-vitesse;Type 1;Type 2;Niveau de base;Niv mutation;Espece mutation;Capacites");
        for(int i = 1; i < 152; i++) {
            Espece espece = (Espece) pokedex.getInfo(pokedex.getListeEspece().get(i).getNom());
            System.out.println(espece.getNom()); //Pour vérifier l'avancement car c'est très long (~ 5min)
            if(espece.getEvolution(0) != null) { 
                out.print(espece.getNoPokedex() + ";" + espece.getNom() + ";" + espece.getBaseStat().getPV() + ";" + espece.getBaseStat().getForce() + ";" + espece.getBaseStat().getDefense() + ";" + espece.getBaseStat().getSpecial() + ";" + espece.getBaseStat().getVitesse() + ";" + espece.getBaseExp() + ";" + espece.getGainsStat().getPV() + ";" + espece.getGainsStat().getForce() + ";" + espece.getGainsStat().getDefense() + ";" + espece.getGainsStat().getSpecial() + ";" + espece.getGainsStat().getVitesse() + ";" + espece.getTypes()[0].getNom() + ";" + espece.getTypes()[1].getNom() + ";" + espece.getNiveauDepart() + ";" + espece.getEvolution(0).getNiveauDepart() + ";" + espece.getEvolution(0).getNom() + ";");
            }     
            else{
                out.print(espece.getNoPokedex() + ";" + espece.getNom() + ";" + espece.getBaseStat().getPV() + ";" + espece.getBaseStat().getForce() + ";" + espece.getBaseStat().getDefense() + ";" + espece.getBaseStat().getSpecial() + ";" + espece.getBaseStat().getVitesse() + ";" + espece.getBaseExp() + ";" + espece.getGainsStat().getPV() + ";" + espece.getGainsStat().getForce() + ";" + espece.getGainsStat().getDefense() + ";" + espece.getGainsStat().getSpecial() + ";" + espece.getGainsStat().getVitesse() + ";" + espece.getTypes()[0].getNom() + ";" + espece.getTypes()[1].getNom() + ";" + espece.getNiveauDepart() + ";" + 0 + ";;");
            }
            int i2 = 0;
            while(espece.getCapSet()[i2] != null) {
                Capacite capa = (Capacite) espece.getCapSet()[i2];
                out.print(capa.getNom() + ";" + espece.getLvlLearnedCapSet().get(capa) + ";");
                i2++;
            }
            out.println(";;;;");
        }
        out.close();

        ========================================= Exemple du rendu précédent ========================================= //


        Pokedex pokedex = new Pokedex(); //Création du pokedex qui contient les données de toutes les capacitées et de toutes les especes de pokemon.
        Pokemon carapuce = (Pokemon) new Pokemon("Carapuce"); //Création d'un pokemon
        Pokemon leveinard = (Pokemon) new Pokemon("Leveinard"); //Création d'un pokemon
        carapuce.apprendCapacites(carapuce.getCapacitesApprises()); //Le joueur choisi les capacité à mettre sur le pokemon.
        System.out.println("Exp de carapuce avant le combat " + carapuce.getExperience());
        System.out.println("Niveau de carapuce avant le combat " + carapuce.getNiveau());
        carapuce.gagneExperienceDe(leveinard);
        carapuce.aChangeNiveau();
        System.out.println("Exp de carapuce après le combat " + carapuce.getExperience());
        System.out.println("Niveau de carapuce après le combat " + carapuce.getNiveau());

        for(int i = 0; i<100; i++){ //Carapuce combat 100 fois leveinard
            carapuce.gagneExperienceDe(leveinard);
        }
        System.out.println("Exp de carapuce après les combats " + carapuce.getExperience());
        System.out.println("Niveau de carapuce après les combats " + carapuce.getNiveau());
        carapuce.vaMuterEn(carapuce.getEspece().getEvolution(16)); //Carapuce évolue (Le int entré en paramètre est inutile)
        System.out.println("Nouvelle espece du pokemon : " + carapuce.getEspece().getNom());
        */
    }
}
