import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import capacite.Capacite;
import interfaces.ICapacite;
import interfaces.IPokemon;
import pokedex.Pokedex;
import pokedex.Type;
import pokemon.Espece;


public class TestPokedex {
	static Pokedex pokedex = new Pokedex();
    static Espece feunard = new Espece(38);

    // ======== Tests classe Capacite ========== //

    @Test
    public void testPokedex() {
        String expectedNomEspece = "Feunard"; //Nom d'espèce au hazard
        String expectedNomCapacite = "Tranche"; //Nom de capacite au hazard

        assertEquals(expectedNomEspece, pokedex.getListeEspece().get(38).getNom()); //Le pokedex possède bien cette espèce
        assertEquals(expectedNomCapacite, pokedex.getListeCapacite().get("Tranche").getNom()); //Le pokedex contient bien cette capacite
    }

    @Test
    public void testGetListeEspece() {
        assertNotNull(pokedex.getListeEspece());
    }

    @Test
    public void testGetListeCapacite() {
        assertNotNull(pokedex.getListeCapacite());
    }

    @Test
    public void testGetInfo() {
        String expectedNom = "Feunard";

        assertEquals(expectedNom, pokedex.getInfo("Feunard").getNom());
        assertEquals(expectedNom, pokedex.getInfo(38).getNom());
    }

	@Test
	public void testGetColonneType() {
            int expected = 7;
            assertEquals(expected, Pokedex.getColonneType("Insecte")); //On s'assure que le type insecte corresponde bien à la septième colonne du csv
	}   

    @Test
    public void testcalculEfficacite() {
        //On effectue des tests avec des attaques inéficaces, peu efficaces, efficaces et super efficaces sur le type Spectre pour couvrir toutes les possibilités
        double expectedNormal = 0.0, expectedPoison = 0.5, expectedSol = 1.0, expectedSpectre = 2.0;

        assertEquals(1, Pokedex.calculEfficacite(new Type("Vol"), new Type("Spectre")));
        assertEquals(expectedNormal, Pokedex.calculEfficacite(new Type("Normal"), new Type("Spectre")));
        assertEquals(expectedPoison, Pokedex.calculEfficacite(new Type("Poison"), new Type("Spectre")));
        assertEquals(expectedSol, Pokedex.calculEfficacite(new Type("Sol"), new Type("Spectre")));
        assertEquals(expectedSpectre, Pokedex.calculEfficacite(new Type("Spectre"), new Type("Spectre")));
    }

    @Test
    public void testcalculDoubleEfficacite() {
        //On effectue des tests avec des attaques inéficaces, peu efficaces, efficaces et super efficaces sur le double type Spectre/Poison (Ectoplasma) pour couvrir toutes les possibilités
        double expectedNormal = 0.0, expectedPoison = 0.25, expectedEau = 1.0, expectedSol = 2.0;
        Type[] spectrePoison = {new Type("Spectre"), new Type("Poison")};
        Type[] feuVol = {new Type("Feu"), new Type("Vol")};

        assertEquals(expectedNormal, Pokedex.calculDoubleEfficacite(new Type("Normal"), spectrePoison));
        assertEquals(expectedPoison, Pokedex.calculDoubleEfficacite(new Type("Poison"), spectrePoison));
        assertEquals(expectedEau, Pokedex.calculDoubleEfficacite(new Type("Eau"), spectrePoison));
        assertEquals(expectedSol, Pokedex.calculDoubleEfficacite(new Type("Sol"), spectrePoison));
        assertEquals(2.0, Pokedex.calculDoubleEfficacite(new Type("Electrik"), feuVol)); //Vol étant un type à problème (dernière colonne du csv) j'ai rajouter un test pour lui
    }

    @Test
    public void testGetCapacite() {
        int expectedPuissance = 70;

        assertEquals(expectedPuissance, pokedex.getCapacite("Tranche").getPuissance());
        assertEquals(expectedPuissance, pokedex.getCapacite(109).getPuissance());
    }

    @Test
    public void testGetLvl1Espece() {
        int i = 0;

        //Comme la méthode getLvl1Espece est censé renvoyer une liste d'especes de pokemon au niveau 1 on s'assure qu'elle ne continne pas de coquilles
        while(pokedex.getLvl1Espece()[i] != null) {
            if(pokedex.getLvl1Espece()[i].getNiveauDepart() != 1) {
                Assert.fail();
            }
            i++;
        }
    }

    @Test
    public void testTailleReelle() {
        ICapacite[] caps = new Capacite[4]; //On simule l'attribut capaciteApprise d'un pokemon
        caps[0] = new Capacite("Tranche"); //On le rempli avec 3 capacités et non 4
        caps[1] = new Capacite("Charge");
        caps[2] = new Capacite("Ultralaser");
        int expectedTailleReelle = 3;

        assertEquals(4, caps.length); //La taille d'un set de capacite d'un pokemon est de 4
        assertEquals(expectedTailleReelle, Pokedex.tailleReelle(caps)); //La taille réelle est son nombre de capacités qu'il possède
    }

    @Test
    public void testEngendreRanch() {
        IPokemon[] ranch = pokedex.engendreRanch();

        for(int a = 0; a < 6; a++){
            if(ranch[a].getEspece().getNiveauDepart() != 1) {
                Assert.fail();
            }
        }
    }
}

