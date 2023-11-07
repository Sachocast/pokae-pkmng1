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

    }
}
