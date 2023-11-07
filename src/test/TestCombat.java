import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

import capacite.Attaque;
import capacite.Capacite;
import capacite.Echange;
import combat.Dresseur;
import combat.Aleatoire;
import combat.Tour;
import interfaces.IPokemon;
import pokedex.Pokedex;
import pokemon.Pokemon;

public class TestCombat {
    static Pokedex pokedex = new Pokedex();
    Dresseur ia = new Aleatoire("ia", pokedex);
    static Pokemon feunard = new Pokemon("Feunard", pokedex);
    static Pokemon arcanin = new Pokemon("Arcanin", pokedex);
    static Attaque lanceFlamme = new Attaque(new Capacite("Lance-Flammes"));
    static Attaque echange = new Echange(feunard, arcanin);
    static Tour tour = new Tour(arcanin, echange, feunard, echange);

    // ======== Tests classe Dresseur ========== //

    @Test
    public void testDresseur() {
        String expectedNom = "ia";

        assertEquals(expectedNom, ia.getNom()); 
        assertNotNull(ia.getRanch()); //On vérifie que le dresseur possède bien un ranch
        assertEquals(6, ia.getNiveau()); //Un dresseur commencera forcément au niveau 6 car il ne peut pas avoir plus ou moins de 6 pokemon
    }

    @Test
    public void testSoigneRanch() {
        for(int i = 0; i < 6; i++) {
            ia.getRanch()[i].subitAttaqueDe(feunard, lanceFlamme);
            assertNotEquals(100, ia.getRanch()[i].getPourcentagePV()); //On s'assure que chacun des pokemon a subit des dégâts
        }

        ia.soigneRanch();
        for(int i = 0; i < 6; i++) {
            assertEquals(100, ia.getRanch()[i].getPourcentagePV()); //Puis on s'assure qu'ils ont bien récupéré leurs PVs
        }
    }

    @Test
    public void testChoisitCombattant() {
        //ChoisirCombattant() étant une méthode intéractive dans la console on ne peut pas la tester ici, un exemple d'utilisation se trouve dans le main
    }

    @Test
    public void testChoisitCombattantContre() {
        //ChoisirCombattantContre() étant une méthode intéractive dans la console on ne peut pas la tester ici, un exemple d'utilisation se trouve dans le main
    }

    @Test
    public void testGetCopyRanch() {
        IPokemon[] copyRanch = ia.getRanchCopy();
        
        //Comme on a déjà testé la méthode Pokemon.getClone() dans TestPokemon et qu'on sait qu'elle marche on va juste s'assurer ici que les 6 pokemon sont créés
        for(int i = 0; i < 6; i++) {
            assertEquals(copyRanch[i].getNom(), ia.getPokemon(i).getNom());
        }
    }

    // ======== Tests classe Tour ========== //

    @Test 
    public void testCommence() {
        System.out.println("\n================ TEST commence() ================\n");

        Pokemon mew = new Pokemon("Mew", pokedex);
        Pokemon mewtwo = new Pokemon("Mewtwo", pokedex);
        Attaque echange1 = new Echange(feunard, mew);
        Attaque echange2 = new Echange(arcanin, mewtwo);
        Tour tour1 = new Tour(feunard, lanceFlamme, arcanin, lanceFlamme); //Cas n°1 : Attaque / Attaque
        Tour tour2 = new Tour(feunard, echange1, arcanin, echange2); //Cas n°2 : Echange / Echange
        Tour tour3 = new Tour(feunard, lanceFlamme, arcanin, echange2); //Cas n°3 : Attaque / Echange
        Tour tour4 = new Tour(feunard, echange1, arcanin, lanceFlamme); //Cas n°4 : Echange / Attaque

        System.out.println("\n---------------- Attaque / Attaque ----------------\n");

        tour1.commence();
        arcanin.soigne();
        feunard.soigne();

        System.out.println("\n---------------- Echange / Echange ----------------\n");

        tour2.commence();
        arcanin.soigne();
        feunard.soigne();
        System.out.println("\n---------------- Attaque / Echange ----------------\n");

        tour3.commence();
        arcanin.soigne();
        feunard.soigne();
        mewtwo.soigne();
        mew.soigne();

        System.out.println("\n---------------- Echange / Attaque ----------------\n");

        tour4.commence();
        arcanin.soigne();
        feunard.soigne();
        mewtwo.soigne();
        mew.soigne();
    }

    @Test 
    public void testCalculPriorite() {
        Tour tour2 = new Tour(new Pokemon("Carapuce", pokedex), echange, feunard, lanceFlamme);
        
        assertEquals(2, tour2.calculPriorite()); //Feunard étant de niveau 22 il a beaucoup plus de vitesse que le Carapuce de niveau 1 et jouera alors en premier (La méhtode renvoie 2 car le pok1 correspond à Carapuce et pok2 à Feunard);
    }

    @Test
    public void testLancementAttaque() {
        System.out.println("\n================ TEST lancementAttaque()================\n");

        tour.affichePV();
        tour.lancementAttaque(lanceFlamme, feunard, arcanin);
        tour.affichePV();
        tour.lancementAttaque(lanceFlamme, feunard, arcanin);
        tour.affichePV();

        arcanin.soigne();
        feunard.soigne();

        //On ne peut pas vérifier avec des asserts cette méthode mais en regardant dans la console tout fonctionne correctement
    }

    @Test
    public void testAffichePV() {
        System.out.println("\n================ TEST affichePV()================\n");
    
        tour.affichePV();
        tour.lancementAttaque(lanceFlamme, feunard, arcanin);
        tour.affichePV();
        tour.lancementAttaque(lanceFlamme, feunard, arcanin);
        tour.affichePV();

        arcanin.soigne();
        feunard.soigne();

        //On ne peut pas vérifier avec des asserts cette méthode mais en regardant dans la console tout fonctionne correctement
    }
}



