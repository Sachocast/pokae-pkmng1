/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * ICategory.java
 */
package interfaces;

/**
 * @author Leo Donati
 * Il s'agit de la catégorie d'une capacité :
 *  - soit Physique
 *  - soit Special
 */
public interface ICategorie {
	boolean isSpecial();
	String getNom();
}
