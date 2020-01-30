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
 *        Classe gérant l'insertion d'entiers
 */

public class InsertionInteger{
	
	private static final int SIZE_MAX = 10;
	
	/**
	 * Nombre d'entiers présents dans t, 0 <= size <= SIZE_MAX 
	 */
	private int size;
	private int[] array = new int[SIZE_MAX];
	
	public InsertionInteger() 
	{
		
	}
	
	/**
	 * @return copie de la partie remplie du tableau
	 */
	public int[] toArray() 
	{
		int[] tab = new int[size];
		for(int i = 0; i<size; i++)
		{
			tab[i] = array[i];
		}
		return tab;
	}
	
	/**
	 * Détermine si x est contenu dans array
	 * 
	 * @param 	value
	 * 			valeur à  chercher
	 * @return	false si x n'appartient pas à  array
	 * 			true si x appartient à  array
	 */
	public boolean contains(int value) {
		for(int i = 0; i < array.length; i++) {
			if(value == array[i]) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Insère un nouvel entier dans le tableau array, effectue un tri du tableau et met à  jour array. 
	 * 
	 * @param 	value
	 * 			valeur à  insérer
	 * @pre		les valeurs de array[0..size-1] sont triées par ordre croissant
	 * @return 	false
	 * 			si value appartient à  array[0..size-1] ou si array est complètement rempli
	 * 			true
	 * 			si value n'appartient pas à  array[0..size-1]
	 */
	public boolean insert(int value) 
	{
		boolean added = false;
		// Taille du tableau est atteinte ou value est déjÃ  dans le tableau
		if(size == SIZE_MAX || contains(value) || value < 0) 
		{
			return added;
		}
		
		// Insertion
		// on cherche l'emplacement de value
		for(int i=0; i<=size && !added;i++)
		{
			
			if(i == size || value<array[i])
			{
				// on parcours en sens inverse du prochain emplacement vide (size-1) au dernier emplacement testé (i)
				for (int j=size-1;j>=i;j--)
				{
					
					array[j+1]=array[j];
				}
				array[i]=value;
				size ++;
				added = true;
			}
		}
		
	
		return added;		
	}
	
	
	public void createArray(Scanner scanner) {
		// Initialiser size utile pour la fonction toArray()
		size = 0;
		
		System.out.println("Veuillez entrer les elements du tableau et terminer avec le chiffre '-1' :");
		int value = scanner.nextInt();
		while(value != -1) {
			System.out.println("value = " + value);			
			insert(value);	
			value = scanner.nextInt();
		}
	}
	

	/**
	 * @Override
	 */
	public String toString() {
		String resultat = "Tableau contenant " + size + " elements non nuls distincts : ";
		
		for(int i = 0; i<array.length; i++) {
			resultat += array[i] + " ";
		}
		
		return resultat;
	}
	
	
}