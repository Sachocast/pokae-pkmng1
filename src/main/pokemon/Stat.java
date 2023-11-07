package pokemon;
import java.util.concurrent.ThreadLocalRandom;

import interfaces.IStat;
/**
 * 
 * @author Royer Baptiste et Castillejos Sacha.
 * 
 * La classe des Stats.
 * Cette classe impléme,te IStat.
 */
public class Stat implements IStat{
	/**
	 * La stat PV.
	 */
    private int pv;
    /**
     * La stat force.
     */
    private int force;
    /**
     * La stat défense.
     */
    private int defense;
    /**
     * La stat special.
     */
    private int special;
    /**
     * La stat vitesse.
     */
    private int vitesse;

    /**
     * Constructeur vide de Stat.
     */
    public Stat(){}

    /**
     * Constructeur de Stat
     * 
     * @param pv sont les PVs du pokemon.
     * @param force est la force du pokemon.
     * @param defense est la defense du pokemon.
     * @param special est la stat special du pokemon.
     * @param vitesse est la vitesse du pokemon.
     */
    public Stat(int pv, int force, int defense, int special, int vitesse){
        this.pv = pv;
        this.force = force;
        this.defense = defense;
        this.special = special;
        this.vitesse = vitesse;
    }

    /**
     * Retourne les PVs du pokemon.
     * 
     * @return les PVs du pokemon.
     */
    public int getPV(){
        return this.pv;
    }

    /**
     * Retourne la force du pokemon.
     * 
     * @return la force du pokemon.
     */
	public int getForce(){
        return this.force;
    }

    /**
     * Retourne la défense du pokemon.
     * 
     * @return la défense du pokemon.
     */
	public int getDefense(){
        return this.defense;
    }

    /**
     * Retourne la stat special du pokemon.
     * 
     * @return la stat special du pokemon.
     */
	public int getSpecial(){
        return this.special;
    }

    /**
     * Retourne la vitesse du pokemon.
     * 
     * @return la vitesse du pokemon.
     */
	public int getVitesse(){
        return this.vitesse;
    }
    
	/**
	 * Modifie les PVs.
	 * 
	 * @param i est la nouvelle valeur de PV.
	 */
	public void setPV(int i){
        this.pv = i;
    }

	/**
	 * Modifie la force.
	 * 
	 * @param i est la nouvelle valeur de force.
	 */
	public void setForce(int i){
        this.force = i;
    }

	/**
	 * Modifie la défense.
	 * 
	 * @param i est la nouvelle valeur de défense.
	 */
	public void setDefense(int i){
        this.defense = i;
    }

	/**
	 * Modifie la vitesse.
	 * 
	 * @param i est la nouvelle valeur de vitesse.
	 */
	public void setVitesse(int i){
        this.vitesse = i;
    }

	/**
	 * Modifie la stat special.
	 * 
	 * @param i est la nouvelle valeur de la stat special.
	 */
	public void setSpecial(int i){
        this.special = i;
    }

	/**
	 * Methode qui initalise les IVs du pokemon.
	 */
    public void initIvs(){
        String ivHp = "";
        Integer[] ivs = new Integer[5];

        for(int i = 0; i<5; i++){
            ivs[i] = ThreadLocalRandom.current().nextInt(0, 15 + 1);
        }
        
        for(int b = 1; b<5; b++){
            if(ivs[b]%2 == 0){
                ivHp += 0;
            }
            else{
                ivHp += 1;
            }
        }
        this.pv = Integer.parseInt(ivHp, 2);
        this.force = ivs[1];
        this.defense = ivs[2];
        this.special = ivs[3];
        this.vitesse = ivs[4];
    }

    
}
