import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import capacite.Attaque;
import capacite.Capacite;
import pokedex.Pokedex;
import pokemon.Pokemon;

public class TestCapacite {
	Pokedex pokedex = new Pokedex();
    static Capacite capacite = new Capacite("Tranche"); //On crée une nouvelle capacite correspondant à Tranche
	static Attaque attaque = new Attaque(capacite); //On crée une nouvelle attaque correspondant à l'action d'un pokemon en combat utilisant Tranche

	// ======== Tests classe Capacite ========== //

	@Test
	public void testGetNom(){
		String expected = "Tranche";
		assertEquals(expected, capacite.getNom());
	}

	@Test
	public void testGetNumCapacite(){
		int expected = 109;
		assertEquals(expected, capacite.getNumCapacite());
	}

	@Test
	public void testGetPrecision(){
		double expected = 1.0;
		assertEquals(expected, capacite.getPrecision());
	}

	@Test
	public void testGetPuissance(){
		int expected = 70;
		assertEquals(expected, capacite.getPuissance());
	}

	@Test
	public void testGetPP(){
		int expected = 20;
		assertEquals(expected, capacite.getPP());
	}

	@Test
	public void testSetPP(){
		int expected = 19;
		capacite.setPP(19);
		assertEquals(expected, capacite.getPP());
	}

	@Test
	public void testGetCategorie(){
		String expected = "Physique";
		assertEquals(expected, capacite.getCategorie().getNom());
	}

	@Test
	public void testGetType(){
		String expected = "Normal";
		assertEquals(expected, capacite.getType().getNom());
	}

	@Test
	public void testResetPP(){
		int expected = 20;
		capacite.setPP(19); //On enlève un pp à la capacité 
		capacite.resetPP(); //On remet ces pp à la normale
		assertEquals(expected, capacite.getPP()); //On s'attend à ce qu'il ait les même pp qu'avant le setPP()
	}


	// ======== Tests classe Attaque ========== //

	@Test
	public void testAttaque(){
        Capacite expected = capacite;
		assertEquals(expected, attaque.getCapaciteUtilise()); //On s'assure que l'attaque correspond bien à la capacite Tranche
	}

	@Test
	public void testReussite(){
		Attaque claquoir = new Attaque(new Capacite("Claquoir")); //La capacite claquoir possède une précision de 75%
		int expected1 = 0;
		int expected2 = 0;

		for(int i = 0; i < 1000; i++){ //Pour vérifier que notre méthode fonctionne on vas faire 1000 tests sur la précision de claquoir et prouver l'effiacité de notre méthode par la loi des grands nombres
			if(claquoir.getReussite() == true){
				expected2++;
			}
		}

		if(expected2 < 650 || expected2 > 850) { //On laisse une marge d'erreur de 10%, si claquoi a touché moins de 650 fois ou plus de 850 fois on peut considérer notre méthode comme défaillante
			Assert.fail();
		}

		System.out.println(expected2); 
		assertEquals(expected1, 0); //On s'attend à ce que l'attaque Tranche ne loupe jamais car elle possède 100% de précision
	}

	@Test
	public void testStab(){
		Pokemon dracaufeu = new Pokemon("Dracaufeu", pokedex); //Dracaufeu est un pokemon de type Feu/Vol, ses attaques Feu et Vol auront donc 50% de dégâts supplémentaires
		Attaque attaque1 = new Attaque(new Capacite("Vol")); //On crée la capacite Vol de type Vol (donc STAB)
		Attaque attaque2 = new Attaque(new Capacite("Déflagration")); //On crée la capacite Déflagration de type Feu (donc STAB)
		Attaque attaque3 = new Attaque(new Capacite("Griffe")); //On crée la capacite griffe de type normal (donc non STAB)

		double expectedVol = 1.5;
		double expectedDeflagration = 1.5;
		double expectedGriffe = 1.0;

		assertEquals(expectedVol, attaque1.stab(dracaufeu));
		assertEquals(expectedDeflagration, attaque2.stab(dracaufeu));
		assertEquals(expectedGriffe, attaque3.stab(dracaufeu));
	}

	@Test
	public void testGetAtt(){
		Pokemon pikachu = new Pokemon("Pikachu", pokedex);
		Attaque attaque1 = new Attaque(new Capacite("Tonnerre")); //On crée la capacite Tonnerre de catégorie Special
		Attaque attaque2 = new Attaque(new Capacite("Vive-Attaque")); //On crée la capacite Griffe de categorie Physique

		int expected1 = pikachu.getStat().getSpecial();
		int expected2 = pikachu.getStat().getForce();

		assertEquals(expected1, attaque1.getAtt(pikachu)); //On s'assure que la statistique renvoyé par getAtt() correspond bien à la statistique de Special de pikachu car Tonnerre est Special
		assertEquals(expected2, attaque2.getAtt(pikachu)); //On s'assure que la statistique renvoyé par getAtt() correspond bien à la statistique de Force de pikachu car Vive-Attaque est physique
	}

	@Test
	public void testGetDef(){
		Pokemon pikachu = new Pokemon("Pikachu", pokedex);
		Attaque attaque1 = new Attaque(new Capacite("Tonnerre")); //On crée la capacite Tonnerre de catégorie Special
		Attaque attaque2 = new Attaque(new Capacite("Vive-Attaque")); //On crée la capacite Griffe de categorie Physique

		int expected1 = pikachu.getStat().getSpecial();
		int expected2 = pikachu.getStat().getDefense();

		assertEquals(expected1, attaque1.getDef(pikachu)); //On s'assure que la statistique renvoyé par getDef() correspond bien à la statistique de Special de pikachu car Tonnerre est Special
		assertEquals(expected2, attaque2.getDef(pikachu)); //On s'assure que la statistique renvoyé par getDef() correspond bien à la statistique de Defense de pikachu car Vive-Attaque est physique
	}

	@Test
	public void testCasParticuliers(){
		Pokemon arcanin = new Pokemon("Arcanin", pokedex); //Création du pokemon lanceur
		Pokemon feunard = new Pokemon("Feunard", pokedex); //Création du pokemon receveur

		Attaque crocFatal = new Attaque(new Capacite("Croc Fatal")); //Création des cas particuliers à tester
		Attaque vaguePsy = new Attaque(new Capacite("Vague Psy")); 
		Attaque ombreNocturne = new Attaque(new Capacite("Ombre Nocturne")); 
		Attaque dracoRage = new Attaque(new Capacite("Draco-Rage")); 
		Attaque frappeAtlas = new Attaque(new Capacite("Frappe Atlas")); 
		Attaque sonicBoom = new Attaque(new Capacite("Sonic Boom")); 
		Attaque guillotine = new Attaque(new Capacite("Guillotine")); 
		Attaque abime = new Attaque(new Capacite("Abîme")); 

		//Test Croc Fatal
		int expected1 = 0;
		if(feunard.getStat().getPV() % 2 == 0){
			expected1 = feunard.getStat().getPV() / 2;
		}
		else{
			expected1 = feunard.getStat().getPV() / 2 + 1;
		}
		crocFatal.casParticuliers(arcanin, feunard);
		assertEquals(expected1, crocFatal.getDommage()); //On vérifie que Croc Fatal enlève bien 50% des PV actuels de la cible

		//Test Vague Psy
		for(int i = 0; i < 100; i++){ //On fait 100 tests pour s'assurer que Vague Psy ne dépasse jamais les dégâts qu'elle doit faire à savoir 33 ici (1.5 * 22 (niveau du lanceur)) et ne soient jamais en dessous de 11 (0.5 * 22 (niveau du lanceur))
			vaguePsy.casParticuliers(arcanin, feunard);
			if(vaguePsy.getDommage() < 11 || vaguePsy.getDommage() > 33){
				Assert.fail();
			}
		}

		//Test Ombre Nocturne
		int expected2 = 22;
		ombreNocturne.casParticuliers(arcanin, feunard);
		assertEquals(expected2, ombreNocturne.getDommage());

		//Test Draco-Rage
		int expected = 40;
		dracoRage.casParticuliers(arcanin, feunard);
		assertEquals(expected, dracoRage.getDommage());
		
		//Test Frappe Atlas
		int expected3 = 22;
		frappeAtlas.casParticuliers(arcanin, feunard);
		assertEquals(expected3, frappeAtlas.getDommage());

		//Test Sonic Boom
		int expected4 = 20;
		sonicBoom.casParticuliers(arcanin, feunard);
		assertEquals(expected4, sonicBoom.getDommage());

		//Test Guillautine et Abîme
		int expected5 = feunard.getStat().getPV();
		guillotine.casParticuliers(arcanin, feunard);
		abime.casParticuliers(arcanin, feunard);
		assertEquals(expected5, guillotine.getDommage());
		assertEquals(expected5, abime.getDommage());		
	}

	@Test
	public void testCalculDamage(){	
		Pokemon dracaufeu = new Pokemon("Dracaufeu", pokedex);
		Pokemon florizarre = new Pokemon("Florizarre", pokedex);
		Attaque lanceFlammes = new Attaque(new Capacite("Lance-Flammes"));

		//Pour établir des tests de dégâts il faut au préalable définir les dégâts minimums et les dégâts maximum d'une attaque en fonctions des ivs offencifs du lanceur et des ivs defensifs du receveur 
		//Pour cela on calcul à la main les dégâts minimums que ferrait une attaque lance flammes d'un dracaufeu avec 0 ivs offensifs sur un florizarre avec 15 ivs defensifs tout deux niveau 32 ( = niveau départ donc pas d'evs)
		//En calculant la statistique Spéciale mimimale de dracaufeu on obtient 59,4 (arrondi à 59) et pour la statistique Spéciale maximale de florizarre on obtient 78,6 (arrondi à 79), en applicant la formule de dégât avec comme CM 3 (Super efficace + STAB) on obtient comme valeur de dégâts minimale : 69
		//On effectue la même chose pour les dégâts maximaux avec une capacité offensive maximale et une capacité défensive minimale et on obtient comme valeur de dégâts maximale : 90,36
		//Nos dégâts devront donc être situés entre 69 et 91

		if(lanceFlammes.calculeDommage(dracaufeu, florizarre) < 69 || lanceFlammes.calculeDommage(dracaufeu, florizarre) > 91){
			Assert.fail();
		}

		//Ce test comprend la méchanique de STAB, de super efficacité, d'Ivs et d'Evs
	}

	@Test
	public void testUtilise(){
		int expected = 19; //On s'attend à ce que la capacité n'ait plus que 19 pp après utilisation (car Tranche en a 20 de base)
		Attaque tranche = new Attaque(new Capacite("Tranche"));
		tranche.utilise();
		assertEquals(expected, tranche.getCapaciteUtilise().getPP());
	}
}
