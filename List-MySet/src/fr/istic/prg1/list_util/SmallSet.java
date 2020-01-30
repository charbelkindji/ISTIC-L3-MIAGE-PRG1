package fr.istic.prg1.list_util;

/**
 * 
 * @author Gaspard KINDJI <chabkind@gmail.com>
 * @author D�borah GELIN <deborahgelin@hotmail.fr>
 * 
 * @version 1.0
 * @since 2019-10-14
 * 
 *        Classe g�rant des ensembles d'�l�ments
 */


public class SmallSet {

    /**
     * taille de l'ensemble
     */
    private static final int setSize = 256;

    /**
     * Ensemble est repr�sent� comme un tableau de bool�ens.
     */
    private boolean[] tab = new boolean[setSize];

    public SmallSet() {
	for (int i = 0; i < setSize; ++i) {
	    tab[i] = false;
	}
    }

    public SmallSet(boolean[] t) {
	for (int i = 0; i < setSize; ++i) {
	    tab[i] = t[i];
	}
    }

    /**
     * @return nombre de valeurs appartenant � l�ensemble
     */
    public int size() {
    	int size = 0;
    	for(int i = 0; i < setSize; ++i) {
    		if(tab[i]) { // == true
    			size++;
    		}
    	}
    	return size;
    }

    /**
     * @param x
     *            valeur � tester
     * @return true, si l�entier x appartient � l�ensemble, false sinon
     */
    public boolean contains(int x) {
    	return x < setSize && tab[x] ? true : false;
    }

    /**
     * @return true, si l�ensemble est vide, false sinon
     */
    public boolean isEmpty() {
    	return size() != 0 ? false : true;
    }

    /**
     * Ajoute x � l�ensemble (sans effet si x d�j� pr�sent)
     *
     * @param x
     *            valeur � ajouter
     * @pre 0 <= x <= 255
     */
    public void add(int x) {
    	// Si x n'est pas dans tab
    	if(!tab[x])
    	{
    		tab[x] = true;
    	}
    }

    /**
     * Retire x de l�ensemble (sans effet si x n�est pas pr�sent)
     *
     * @param x
     *            valeur � supprimer
     * @pre 0 <= x <= 255
     */
    public void remove(int x) {
    	// Si x est dans tab
    	if(tab[x])
    	{
    		tab[x] = false;
    	}
    }

    /**
     * Ajoute � l�ensemble les valeurs deb, deb+1, deb+2, ..., fin.
     *
     * @param deb
     *            d�but de l�intervalle
     * @param fin
     *            fin de l�intervalle
     * @pre 0 <= begin <= end <= 255
     */
    public void addInterval(int deb, int fin) {
    	for(int i = deb; i <= fin; ++i) {
    		add(i);
    	}
    }

    /**
     * Retire de l�ensemble les valeurs deb, deb+1, deb+2, ..., fin.
     *
     * @param begin
     *            d�but de l�intervalle
     * @param end
     *            fin de l�intervalle
     * @pre 0 <= begin <= end <= 255
     */
    public void removeInterval(int deb, int fin) {
    	for(int i = deb; i <= fin; ++i) {
    		remove(i);
    	}
    }

    /**
     * this devient l'union de this et set2
     * 
     * @param set2
     *            deuxi�me ensemble
     */
    public void union(SmallSet set2) {
    	for(int i = 0; i < setSize; ++i) {
    		tab[i] = set2.contains(i) || tab[i];
    	}
    }

    /**
     * this devient l'intersection de this et set2
     * 
     * @param set2
     *            deuxi�me ensemble
     */
    public void intersection(SmallSet set2) {
    	for(int i = 0; i < setSize; ++i) {
    		tab[i] = set2.contains(i) && tab[i];    		
    	}
    }

    /**
     * this devient la diff�rence de this et set2
     * 
     * @param set2
     *            deuxi�me ensemble
     */
    public void difference(SmallSet set2) {
    	for(int i = 0; i < setSize; ++i) {    		
    		tab[i] = !set2.contains(i) && tab[i];
    	}
    }

    /**
     * this devient la diff�rence sym�trique de this et set2
     * 
     * @param set2
     *            deuxi�me ensemble
     */
    public void symmetricDifference(SmallSet set2) {
    	for(int i = 0; i < setSize; ++i) {   
    		tab[i] = set2.contains(i) ^ tab[i];
    	}
    }

    /**
     * this devient le compl�ment de this.
     */
    public void complement() {
    	for(int i = 0; i < setSize; ++i) {   
    		tab[i] = !tab[i];
    	}
    }

    /**
     * this devient l'ensemble vide.
     */
    public void clear() {
    	for(int i = 0; i < setSize; ++i) {   
    		tab[i] = false;
    	}
    }

    /**
     * @param set2
     *            second ensemble
     * @return true, si this est inclus dans set2, false sinon
     */
    public boolean isIncludedIn(SmallSet set2) {
    	for(int i = 0; i < setSize; ++i) {  
    		if(tab[i] && !set2.contains(i))
    		{
    			return false;
    		}
    	}
    	return true;
    }

    @Override
    public boolean equals(Object obj) {
		if (this == obj) {
		    return true;
		}
		if (obj == null) {
		    return false;
		}
		if (!(obj instanceof SmallSet)) {
		    return false;
		}
		
    	for(int i = 0; i < setSize; ++i) { 
    		if(tab[i] != ((SmallSet)obj).contains(i)) {
    			return false;
    		}
    	}
		return true;
    }

    /**
     * @return copie de this
     */
    public SmallSet clone() {
    	return new SmallSet(tab);
    }

    @Override
    public String toString() {
		String s;
		s = "elements presents : ";
		for (int i = 0; i < setSize; ++i) {
		    if (tab[i]) {
		    	s = s + i + " ";
		    }
		}
		return s;
    }
}