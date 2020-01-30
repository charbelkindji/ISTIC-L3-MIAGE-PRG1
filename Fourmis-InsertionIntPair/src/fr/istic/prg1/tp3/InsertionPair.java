package fr.istic.prg1.tp3;

import java.util.Scanner;

/**
 * 
 * @author Gaspard KINDJI <chabkind@gmail.com>
 * @author D�borah GELIN <deborahgelin@hotmail.fr>
 * 
 * @version 1.0
 * @since 2019-10-10
 * 
 *        Classe g�rant l'insertion de doublets
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
	 * D�termine si la paire p est contenu dans array
	 * 
	 * @param 	p
	 * 			paire à chercher
	 * @return	false si p n'appartient pas à array
	 * 			true si p appartient à array
	 */
	public boolean contains(Pair p) {
		for(int i = 0; i < size; i++) {
			// Si �galit�, compareTo renvoie 0
			if(p.compareTo(array[i]) == 0) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Insère une nouvelle paire d'entiers dans le tableau array, effectue un tri du tableau et met à jour array. 
	 * 
	 * @param 	p
	 * 			paire à ins�rer
	 * @pre		les valeurs de array[0..size-1] sont tri�es par ordre croissant
	 * @return 	false
	 * 			si p appartient à array[0..size-1] ou si array est complètement rempli
	 * 			true
	 * 			si p n'appartient pas à array[0..size-1]
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
			// L'�l�ment � ins�rer est plus grand que tous les autres
			if( i == size || p.compareTo(array[i]) == -1)
			{
				// on parcours en sens inverse du prochain emplacement vide (size-1) au dernier emplacement test� (i)
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
	 * Cr�er un tableau de paire d'entiers � partir des entr�es utilisateur
	 * 
	 * @param 	scanner
	 * 			Variable de type Scanner pour lire les entr�es
	 * 
	 * @return	
	 */
	public void createArray(Scanner scanner) {
		// Initialiser size utile pour la fonction toArray()
		size = 0;
		
		System.out.println("Veuillez entrer les elements du tableau et terminer avec le chiffre '-1' :");		
		
		int value = scanner.nextInt();
		
		// Deux entiers constituent une paire. Stocker un int (la valeur x) de fa�on temporaire en attendant de r�cuperer la seconde (y)
		int temp = 0;
		
		// Pour d�terminer le moment o� il faut cr�er la paire : Le moment o� on a r�cup�r� les deux valeurs (x et y)
		int nbEntiers = 1;
				
		while(value != -1) {
			System.out.println("value = " + value);	
			if(nbEntiers == 2) {
				// Cr�er objet avec valeur temporaire stock�e et nouvelle valeur
				Pair p = new Pair(temp, value);
				System.out.println("temp = " + temp + " value = " + value);
				insert(p);
				
				// R�initialiser pour la prochaine paire à r�cup�rer
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