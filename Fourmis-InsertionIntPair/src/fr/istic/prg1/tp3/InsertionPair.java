package fr.istic.prg1.tp3;

import java.util.Scanner;

/**
 * 
 * @author Gaspard KINDJI <chabkind@gmail.com>
 * @author Déborah GELIN <deborahgelin@hotmail.fr>
 * 
 * @version 1.0
 * @since 2019-10-10
 * 
 *        Classe gérant l'insertion de doublets
 */

public class InsertionPair{
	private static final int SIZE_MAX = 10;
	private int size;
	private Pair[] array = new Pair[SIZE_MAX];
	
	public InsertionPair() {
		
	}
	
	/**
	 * @return copie de la partie remplie du tableau
	 */
	public Pair[] toArray() {
		Pair[] tab = new Pair[size];
		for(int i = 0; i<size; i++) {
			tab[i] = array[i];
		}
		return tab;
	}
	
	/**
	 * Détermine si la paire p est contenu dans array
	 * 
	 * @param 	p
	 * 			paire Ã  chercher
	 * @return	false si p n'appartient pas Ã  array
	 * 			true si p appartient Ã  array
	 */
	public boolean contains(Pair p) {
		for(int i = 0; i < size; i++) {
			// Si égalité, compareTo renvoie 0
			if(p.compareTo(array[i]) == 0) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * InsÃ¨re une nouvelle paire d'entiers dans le tableau array, effectue un tri du tableau et met Ã  jour array. 
	 * 
	 * @param 	p
	 * 			paire Ã  insérer
	 * @pre		les valeurs de array[0..size-1] sont triées par ordre croissant
	 * @return 	false
	 * 			si p appartient Ã  array[0..size-1] ou si array est complÃ¨tement rempli
	 * 			true
	 * 			si p n'appartient pas Ã  array[0..size-1]
	 */
	public boolean insert(Pair p) {
		boolean added = false;
		// Taille du tableau est atteinte ou value est deja dans le tableau
		if(size == SIZE_MAX || contains(p)) {
			return added;
		}
		
		// Insertion
		// on cherche l'emplacement de la paire				
		for(int i=0; i<=size && !added;i++)
		{
			// On trouve l'emplacement, ou on parcours tout le tableau et 
			// L'élément à insérer est plus grand que tous les autres
			if( i == size || p.compareTo(array[i]) == -1)
			{
				// on parcours en sens inverse du prochain emplacement vide (size-1) au dernier emplacement testé (i)
				for (int j=size-1;j>=i;j--)
				{		
					array[j+1]=array[j];
				}
				array[i]=p;
				size ++;
				added = true; // rupture normale de la boucle
			}
		}
		
		return added;
	}
	
	/**
	 * Créer un tableau de paire d'entiers à  partir des entrées utilisateur
	 * 
	 * @param 	scanner
	 * 			Variable de type Scanner pour lire les entrées
	 * 
	 * @return	
	 */
	public void createArray(Scanner scanner) {
		// Initialiser size utile pour la fonction toArray()
		size = 0;
		
		System.out.println("Veuillez entrer les elements du tableau et terminer avec le chiffre '-1' :");		
		
		int value = scanner.nextInt();
		
		// Deux entiers constituent une paire. Stocker un int (la valeur x) de façon temporaire en attendant de récuperer la seconde (y)
		int temp = 0;
		
		// Pour déterminer le moment où il faut créer la paire : Le moment où on a récupéré les deux valeurs (x et y)
		int nbEntiers = 1;
				
		while(value != -1) {
			System.out.println("value = " + value);	
			if(nbEntiers == 2) {
				// Créer objet avec valeur temporaire stockée et nouvelle valeur
				Pair p = new Pair(temp, value);
				System.out.println("temp = " + temp + " value = " + value);
				insert(p);
				
				// Réinitialiser pour la prochaine paire Ã  récupérer
				nbEntiers = 0;
			}			
			temp = value;			
			value = scanner.nextInt();			
			nbEntiers++;
		}
	}
	
	@Override
	public String toString() {
		String chaine = "";
		for (int i = 0; i < size; i++) {
			chaine += array[i] + "";
		}
		return chaine;
	}
	
}