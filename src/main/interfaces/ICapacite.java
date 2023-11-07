/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * ICapacite.java
 */
package interfaces;

/**
 * @author Leo Donati
 *	Une capacité est un type d'attaque qu ele pokemon peut utilser
 */
public interface ICapacite extends IAttaque {
	String getNom();
	double getPrecision();
	int getPuissance();
	int getPP();				//Le nombre de fois où cette capacité peut être utilisée
	void resetPP();				//remet PP au maximum de la capacité
	ICategorie getCategorie();	//Catégorie de la capacité (Physique ou Special)
	IType getType();			//Type de la capacité (la liste des types est la même que pour le pokemon)
}
