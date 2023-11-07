/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IStat.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IStat {
	public int getPV();
	public int getForce();
	public int getDefense();
	public int getSpecial();
	public int getVitesse();
	void setPV(int i);
	void setForce(int i);
	void setDefense(int i);
	void setVitesse(int i);
	void setSpecial(int i);
}
