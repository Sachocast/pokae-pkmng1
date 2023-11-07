import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import capacite.Attaque;
import capacite.Capacite;
import pokedex.Pokedex;
import pokemon.Espece;
import pokemon.Pokemon;
import pokemon.Stat;

public class TestPokemon{
    static Pokedex pokedex = new Pokedex(); //Nous servira à initialiser les sets de capacités
    static Stat stat = new Stat();
    static Espece especeGoupix = new Espece(37); //Correspond à Goupix

    // ======== Tests classe Stat ========== //

    @Test
    public void testStat() {
        Stat stat2 = new Stat(20, 19, 18, 17, 16);
        int expectedPv = 20, expectedForce = 19, expectedDefense = 18, expectedSpecial = 17, expectedVitesse = 16;

        //On vérifie l'initialisation à l'aide du constructeur Stat(int, int, int, int, int)
        assertEquals(expectedPv, stat2.getPV());
        assertEquals(expectedForce, stat2.getForce());
        assertEquals(expectedDefense, stat2.getDefense());
        assertEquals(expectedSpecial, stat2.getSpecial());
        assertEquals(expectedVitesse, stat2.getVitesse());

        //On vérifie l'initialisation à l'aide de setters
        stat.setPV(20);
        stat.setForce(19);
        stat.setDefense(18);
        stat.setSpecial(17);
        stat.setVitesse(16);

        assertEquals(expectedPv, stat.getPV());
        assertEquals(expectedForce, stat.getForce());
        assertEquals(expectedDefense, stat.getDefense());
        assertEquals(expectedSpecial, stat.getSpecial());
        assertEquals(expectedVitesse, stat.getVitesse());
    }

    @Test
    public void testinitIvs() {
        stat.initIvs();

        //On s'assure que les valeurs des ivs sont bien supérieures ou égales à 0 et inférieures ou égales à 15
        assertTrue(stat.getPV() >= 0 && stat.getPV() <= 15);
        assertTrue(stat.getForce() >= 0 && stat.getForce() <= 15);
        assertTrue(stat.getDefense() >= 0 && stat.getDefense() <= 15);
        assertTrue(stat.getSpecial() >= 0 && stat.getSpecial() <= 15);
        assertTrue(stat.getVitesse() >= 0 && stat.getVitesse() <= 15);
    }


    // ======== Tests classe Espece ========== //

    @Test
    public void testEspece() {
        int expectedNoPokedex = 37, expectedBasePv = 38, expectedGainVit = 1, expectedBaseExp = 63, expectedNiveauDepart = 1;
        String expectedNom = "Goupix", expectedNomEvolution = "Feunard"; //Correspond à Feunard (son évolution);
        String expectedTypeNom = "Feu";
        //La vérification du set de capacité et de la HashMap contenant le niveau ou elle les apprends se fera dans un autre test

       assertEquals(expectedNoPokedex, especeGoupix.getNoPokedex());
       assertEquals(expectedNom, especeGoupix.getNom());
       assertEquals(expectedBaseExp, especeGoupix.getBaseExp());
       assertEquals(expectedNiveauDepart, especeGoupix.getNiveauDepart());
       assertEquals(expectedBasePv, especeGoupix.getBaseStat().getPV());
       assertEquals(expectedGainVit, especeGoupix.getGainsStat().getVitesse());
       assertEquals(expectedTypeNom, especeGoupix.getTypes()[0].getNom());
       assertEquals(expectedNomEvolution, especeGoupix.getEvolution(0).getNom());
    }

    @Test
    public void testGetAPI() throws MalformedURLException {
        URL url = new URL("https://pokeapi.co/api/v2/move/595/");
        assertNotNull(Espece.getAPI(url)); //Méthode difficila à tester car renvoie une API
    }

    @Test
    public void testInitCapSet() {
        Espece especeFeunard = new Espece(38); //La méthode initCapSet() est appelée par la méthode getInfo() de la classe Pokedex
        int expectedNbCapacites = 16, compteur = 0;
        //D'après l'API et les capacités disponibles dans le csv, feunard doit pouvoir apprendre : Charge, Plaquage, Bélier, Damoclès, Flammèche, Lance-Flammes, Ultralaser, Lance-Soleil, 
        //Danse Flammes, Tunnel, Vive-Attaque, Frénésie, Patience, Déflagration, Météore et Dévorêve soit 16 capacités

        while(especeFeunard.getCapSet()[compteur] != null){
            compteur++;
        }
        
        assertEquals(expectedNbCapacites, compteur);

        //Toujours d'après l'API, Feunard devrait apprendre Danse Flammes au niveau 43
        int expectedNiveauDF = 43;
        Capacite danseFlamme = (Capacite) especeFeunard.getCapSet()[8]; //Danse Flammes se situe en huitième position dans le tableau des capacités apprises par feunard
        assertEquals(expectedNiveauDF, ((Espece) especeFeunard).getLvlLearnedCapSet().get(danseFlamme));
    }


    // ======== Tests classe Pokemon ========== //

    @Test
    public void testPokemon() {
        Pokemon goupix = new Pokemon("Goupix", pokedex);

        String expectedNomEspece = "Goupix";
        int expectedEvPv = 0;
        double expectedPoucentagePv = 100.0;
        int expectedNiveau = goupix.getEspece().getNiveauDepart();
        //Les ivs pouvant être de 0 à 15 il est difficile de les tester, les capacitées aprisent également car c'est le joueur qui décide de les apprendre

        assertEquals(expectedNomEspece, goupix.getEspece().getNom());
        assertEquals(expectedEvPv, goupix.getEvs().getPV());
        assertEquals(expectedPoucentagePv, goupix.getPourcentagePV());
        assertEquals(expectedNiveau, goupix.getNiveau());

    }

    @Test
    public void testCalculStat() {
        //Il est difficile de tester si le calcul des statistiques est correct car les ivs du pokemon changent à chaque initialisation
        Pokemon feunard = new Pokemon("Feunard", pokedex);

        //On va déterminer les statistiques minimales et maximales qu'un pokemon peut avoir en fonction de ses ivs
        int expectedMinPV = 64, expectedMaxPV = 71; //Obtenu en faisant la formule du calcul des PV avec ivs à 0 puis ivs à 15
        int expectedMinForce = 38, expectedMaxForce = 45; //Obtenu en faisant la formule du calcul des autres statistiques avec ivs à 0 puis ivs à 15

        if(feunard.getStat().getPV() < expectedMinPV || feunard.getStat().getPV() > expectedMaxPV){
            Assert.fail();
        }

        if(feunard.getStat().getForce() < expectedMinForce || feunard.getStat().getForce() > expectedMaxForce){
            Assert.fail();
        }
    }

    @Test
    public void testGainEV() {
        Pokemon feunard = new Pokemon("Feunard", pokedex);
        Pokemon arcanin = new Pokemon("Arcanin", pokedex);

        assertEquals(0, feunard.getEvs().getForce());
        feunard.gainEV(arcanin); //D'après Poképédia arcanin est cencé donner 2 evs force
        assertEquals(2, feunard.getEvs().getForce()); //On vérifie que c'est bien le cas
        assertEquals(0, feunard.getEvs().getPV()); //On s'assure également qu'il n'a pas donné d'autres evs par erreur
    }

    @Test
    public void testGagneExperienceDe() {
        Pokemon goupix = new Pokemon("Goupix", pokedex);
        Pokemon leveinard = new Pokemon("Leveinard", pokedex);
        //Leveinard de niveau 1 donne 255 expérience auquels on applique la formule : (1.5 x 255) / 7 = 54.64
        //Goupix possède de base 0.8 exp car de niveau 1 on obtient donc 55.44
        //Pour calculer les niveaux gagner on inverse la formule de l'expérience et on obtient Niv = racine cubique de (Exp / 0.8) soit 4 ici
        double expectedExp = 55.44, epsilon = 0.01;
        int expectedNiveau = 4;
        goupix.gagneExperienceDe(leveinard);

        if(Math.abs(expectedExp - goupix.getExperience()) > epsilon){ //Si la différence d'exp entre celle attendue et la réelle est supérieur à 0.01 (marge de calcul) alors la méthode a un problème 
            Assert.fail();
        }
        assertEquals(expectedNiveau, goupix.getNiveau());
    }

    @Test
    public void testAChangerNiveau() {
        Pokemon goupix = new Pokemon("Goupix", pokedex);
    
        //Comme Goupix vient d'être crée il ne peut pas monter de niveau
        assertFalse(goupix.aChangeNiveau());
    }

    @Test
    public void testPeutMuter() {
        Pokemon goupix = new Pokemon("Goupix", pokedex); //Goupix évolue en fenard au niveau 22
        Pokemon dracaufeu = new Pokemon("Dracaufeu", pokedex);
        
        //Comme goupix est niveau 1 il ne peut pas évoluer
        assertFalse(goupix.peutMuter());

        //On fait gagner à Goupix l'équivalent de 10 dracaufeu pour le faire passer nivneau 26
        for(int i = 0; i <  10; i++){
            goupix.gagneExperienceDe(dracaufeu);
        }
        System.out.println(goupix.getNiveau());
        assertTrue(goupix.peutMuter()); //On s'assure qu'il peut désormais évoluer
    }

    @Test
    public void testVaMuterEn() {
        Pokemon goupix = new Pokemon("Goupix", pokedex); //Goupix évolue en fenard au niveau 22
        Pokemon dracaufeu = new Pokemon("Dracaufeu", pokedex);
        String expectedNouvelleEspece = "Feunard";

        //On fait gagner à Goupix l'équivalent de 10 dracaufeu pour le faire passer nivneau 26
        for(int i = 0; i <  10; i++){
            goupix.gagneExperienceDe(dracaufeu);
        }

        goupix.vaMuterEn(goupix.getEspece().getEvolution(goupix.getEspece().getNiveauDepart())); 
        assertEquals(expectedNouvelleEspece, goupix.getEspece().getNom()); //On s'assure que le nom de la nouvelle espèce de Goupix est bien Feunard car on ne peut pas comparer deux objets avec des asserts
    }

    @Test
    public void testRemplaceCapacite() throws Exception {
        Pokemon goupix = new Pokemon("Goupix", pokedex);
        Capacite lanceFlammes = new Capacite("Lance-Flammes");

        goupix.remplaceCapacite(0, lanceFlammes); //On apprend Lance Flammes à Goupix
        assertEquals(lanceFlammes, goupix.getCapacitesApprises()[0]); //On s'assure qu'il l'a bien apprit
    }

    @Test
    public void testApprendCapacites() {
        //Comme la méthode apprendCapacite est une méthode intéractive qui demande l'action de l'utilisateur dans la commande il va être impossible de la tester ici
    }

    @Test
    public void testSubitAttaqueDe() {
        Pokemon goupix = new Pokemon("Goupix", pokedex);
        Pokemon leveinard = new Pokemon("Leveinard", pokedex);
        Attaque lanceFlammes = new Attaque (new Capacite("Lance-Flammes"));

        assertTrue(leveinard.getPourcentagePV() == 100.0); //On s'assure que le leveinard a tous ces points de vie avant l'attaque de Goupix
        leveinard.subitAttaqueDe(goupix, lanceFlammes);
        assertFalse(leveinard.getPourcentagePV() == 100.0); //Puis on s'assure que le leveinard a bien subit des dégâts et qu'il ne possède plus tous ces points de vie
    }

    @Test
    public void testSubitDegatsRecul() {
        //Cette méthode n'est utilisé que lors de l'attaque Lutte qui enlève des points de vie à son lanceur
        Pokemon goupix = new Pokemon("Goupix", pokedex);
        Pokemon leveinard = new Pokemon("Leveinard", pokedex);
        Attaque lutte = new Attaque (new Capacite("Lutte"));

        assertTrue(leveinard.getPourcentagePV() == 100.0); //On s'assure que Goupix a tous ces points de vie avant de lancer son attaque
        leveinard.subitAttaqueDe(goupix, lutte);
        assertFalse(goupix.getPourcentagePV() == 100.0); //Puis on s'assure que lGoupix a bien subit les dégâts de recul suite à son attaque
    }

    @Test
    public void testEstEvanoui() {
        Pokemon goupix = new Pokemon("Goupix", pokedex);
        Pokemon tortank = new Pokemon("Tortank", pokedex);
        Attaque surf = new Attaque (new Capacite("Surf"));

        assertFalse(goupix.estEvanoui()); //On s'assure que le Goupix n'est pas évanoui avant l'attaque de Tortank
        goupix.subitAttaqueDe(tortank, surf); //L'attaque Surf d'un Tortank de niveau 36 sur un Goupix de niveau 1 suffit largement à l'évanouir
        assertTrue(goupix.estEvanoui()); //Puis on s'assure que cette dernière l'a bien évanoui
    }

    @Test
    public void testSoigne() {
        Pokemon goupix = new Pokemon("Goupix", pokedex);
        Pokemon tortank = new Pokemon("Tortank", pokedex);
        Attaque surf = new Attaque (new Capacite("Surf"));

        goupix.subitAttaqueDe(tortank, surf); //On enlève des points de vie à Goupix
        assertFalse(goupix.getPourcentagePV() == 100.0); //On s'assure qu'il les a bien perdu
        goupix.soigne(); //On le soigne
        assertTrue(goupix.getPourcentagePV() == 100.0); //Puis on s'assure qu'il a bien récupéré tous ces points de vie
    }

    @Test
    public void testGetClone() {
        Pokemon goupix = new Pokemon("Goupix", pokedex); //On crée un premier pokemon
        try {
            goupix.remplaceCapacite(0, new Capacite("Tranche")); //On lui donne une capacité
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pokemon cloneGoupix = goupix.getClone(); //On crée son clone

        //On vérifie que tous les attributs du clone correspondent à ceux du "vrai" pokemon
        assertEquals(cloneGoupix.getEspece().getNom(), goupix.getEspece().getNom());
        assertEquals(cloneGoupix.getCapacitesApprises()[0].getNom(), goupix.getCapacitesApprises()[0].getNom());
        assertEquals(cloneGoupix.getExperience(), goupix.getExperience());
        assertEquals(cloneGoupix.getNiveau(), goupix.getNiveau());
        assertEquals(cloneGoupix.getPourcentagePV(), goupix.getPourcentagePV());
        assertEquals(cloneGoupix.getStat().getPV(), goupix.getStat().getPV());
        assertEquals(cloneGoupix.getEvs().getForce(), goupix.getEvs().getForce());
        assertEquals(cloneGoupix.getIvs().getDefense(), goupix.getIvs().getDefense());
    }
}

