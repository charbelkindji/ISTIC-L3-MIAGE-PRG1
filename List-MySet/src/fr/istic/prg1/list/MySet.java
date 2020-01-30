// D�borah GELIN et Gaspard KINDJI

package fr.istic.prg1.list;

import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.lang.Character.Subset;
import java.util.Scanner;

import fr.istic.prg1.list_util.Comparison;
import fr.istic.prg1.list_util.Iterator;
import fr.istic.prg1.list_util.List;
import fr.istic.prg1.list_util.SmallSet;

/**
 * @author Gaspard KINDJI <chabkind@gmail.com>
 * @author D�borah GELIN <deborahgelin@hotmail.fr>
 * 
 * @version 1.0
 * @since 2019-10-28
 * 
 *        Classe repr�sentant une liste d'ensembles
 */

public class MySet extends List<SubSet> {

	/**
	 * Borne sup�rieure pour les rangs des sous-ensembles.
	 */
	private static final int MAX_RANG = 128;
	/**
	 * Sous-ensemble de rang maximal � mettre dans le drapeau de la liste.
	 */
	private static final SubSet FLAG_VALUE = new SubSet(MAX_RANG,
			new SmallSet());
	/**
	 * Entr�e standard.
	 */
	private static final Scanner standardInput = new Scanner(System.in);

	public MySet() {
		super();
		setFlag(FLAG_VALUE);
		
		
	}

	/**
	 * Fermer tout (actuellement juste l'entr�e standard).
	 */
	public static void closeAll() {
		standardInput.close();
	}

	private static Comparison compare(int a, int b) {
		if (a < b) {
			return Comparison.INF;
		} else if (a == b) {
			return Comparison.EGAL;
		} else {
			return Comparison.SUP;
		}
	}

	/**
	 * Afficher � l'�cran les entiers appartenant � this, dix entiers par ligne
	 * d'�cran.
	 */
	public void print() {
		System.out.println(" [version corrigee de contenu]");
		this.print(System.out);
	}

	// //////////////////////////////////////////////////////////////////////////////
	// //////////// Appartenance, Ajout, Suppression, Cardinal
	// ////////////////////
	// //////////////////////////////////////////////////////////////////////////////

	/**
	 * Ajouter � this toutes les valeurs saisies par l'utilisateur et afficher
	 * le nouveau contenu (arr�t par lecture de -1).
	 */
	public void add() {
		System.out.println(" valeurs a ajouter (-1 pour finir) : ");
		this.add(System.in);
		System.out.println(" nouveau contenu :");
		this.printNewState();
	}

	/**
	 * Ajouter � this toutes les valeurs prises dans is.
	 * C'est une fonction auxiliaire pour add() et restore().
	 * 
	 * @param is
	 *            flux d'entr�e.
	 */
	public void add(InputStream is) {
		Scanner scanner = new Scanner(is);		
		int value = readValue(scanner, -1);
		
		// Tant qu'on n'est pas � la fin de la saisie
		while(value != -1)
		{			
			addNumber(value);
			value = readValue(scanner, -1);
		}	
	}

	/**
	 * Ajouter value � this.
	 * 
	 * @param value
	 *            valuer � ajouter.
	 */
	public void addNumber(int value) {
		Iterator<SubSet> it = this.iterator();
		SubSet current = it.getValue(); 	
		int rank = value/256;
		
		boolean added = false;
		
		// Cond. d'arr�t pour ajouter l'�l�ment quand on a parcouru toute la liste et qu'on est revenu sur le flag
		while(current.rank <= MAX_RANG && added == false) { 
			
			switch(compare(current.rank, rank))
			{
				case INF:
					current = it.nextValue();
					break; // break switch
				case EGAL:
					// Ajout de l'�l�ment dans le SubSet
					current.set.add(value%256);
					added = true;
					break;
				case SUP:
					// Cr�ation d'un nouveau SmallSet
					SmallSet small = new SmallSet();
					
					// On ajoute l'�l�ment dans le SubSet
					small.add(value%256);
					
					// Ajout du SubSet dans this
					it.addLeft(new SubSet(rank, small));
					added = true;
					break;
			}
			
		}	
	}

	/**
	 * Supprimer de this toutes les valeurs saisies par l'utilisateur et
	 * afficher le nouveau contenu (arr�t par lecture de -1).
	 */
	public void remove() {
		System.out.println("  valeurs a supprimer (-1 pour finir) : ");
		this.remove(System.in);
		System.out.println(" nouveau contenu :");
		this.printNewState();
	}

	/**
	 * Supprimer de this toutes les valeurs prises dans is.
	 * 
	 * @param is
	 *            flux d'entr�e
	 */
	public void remove(InputStream is) {
		Scanner scanner = new Scanner(is);		
		int value = readValue(scanner, -1);
		
		// Tant qu'on n'est pas � la fin de la saisie
		while(value != -1)
		{
			removeNumber(value);
			value = readValue(scanner, -1);
		}
	}

	/**
	 * Supprimer value de this.
	 * 
	 * @param value
	 *            valeur � supprimer
	 */
	public void removeNumber(int value) {
		Iterator<SubSet> it = this.iterator();
		SubSet current = it.getValue();
		
		int rank = value/256;
		while(!it.isOnFlag())
		{
			switch(compare(current.rank, rank))
			{
				case INF:
					current = it.nextValue();
					break; // break switch and continue searching
				case EGAL:
					// Suppression de l'�l�ment du SubSet
					current.set.remove(value%256);	
					
					// ensemble d�sormais vide, le supprimer de this
					if(current.set.isEmpty())
						it.remove();
				case SUP:
					// Positionner it sur le flag pour arr�t de la boucle
					it.restart();
					it.goBackward();					
					break;
			}
		}		
	}

	/**
	 * @return taille de l'ensemble this
	 */
	public int size() {
		Iterator<SubSet> it = this.iterator();
		SubSet current = it.getValue();
		int compteur = 0;
		while(!it.isOnFlag())
		{
			compteur += current.set.size();
			current = it.nextValue();
		}
		return compteur;
	}


	/**
	 * @return true si le nombre saisi par l'utilisateur appartient � this,
	 *         false sinon
	 */
	public boolean contains() {
		int value = readValue(standardInput, 0);
		return this.contains(value);
	}

	/**
	 * @param value
	 *            valeur � tester
	 * @return true si valeur appartient � l'ensemble, false sinon
	 */

	public boolean contains(int value) {
		// It�rateur pour le parcours de la liste
		Iterator<SubSet> it = this.iterator();
		
		// R�cup�rer la valeur du premier �l�ment
		SubSet current = it.getValue();
		
		int rank = value/256;
			
		// Faire le tour de la liste tant qu'on n'est pas revenus sur le flag
		while(!it.isOnFlag() || current.rank <= rank)
		{
			if(current.rank == rank)
			{
				return current.set.contains(value%256);
			}
			current = it.nextValue();
		}
		return false;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// /////// Difference, DifferenceSymetrique, Intersection, Union ///////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * This devient la diff�rence de this et set2.
	 * 
	 * @param set2
	 *            deuxi�me ensemble
	 */
	public void difference(MySet set2) {
		// It�rateurs sur this (it1) et sur set2 (it2)
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();
		
		// Elements courants de this (current1) et set2 (current2)
		SubSet current1 = it1.getValue(); 
		SubSet current2 = it2.getValue();
	
		// Si les deux ensembles sont �gaux
		if(this == set2)
		{
			this.clear();
			return;
		}
	
		// Parcours des deux listes jusqu'� la fin de l'une
		while(!it1.isOnFlag() || !it2.isOnFlag())
		{			
			// S'assurer d'�tre sur des SubSet de m�me rangs avant de faire l'op�ration
			// On s'appuie sur le fait que les listes soient rang�es dans l'ordre croissant des rangs
			switch(compare(current1.rank, current2.rank))
			{
				case INF:
					current1 = it1.nextValue();					
					break;
				case EGAL:
					// Diff�rence des deux SubSet et avancer les deux it
					current1.set.difference(current2.set); // R�sultat dans current1 (this)

					// Ensemble d�sormais vide ? Le supprimer.
					if(current1.set.isEmpty())
					{
						it1.remove();
						
						// Prochains SubSets
						current1 = it1.getValue();
						current2 = it2.nextValue();	
					}else {
						// Prochains SubSets
						current1 = it1.nextValue();
						current2 = it2.nextValue();
					}
					
					break;
				case SUP:					
					current2 = it2.nextValue();
					break;
			}
		}	
		
				
	}

	/**
	 * This devient la diff�rence sym�trique de this et set2.
	 * 
	 * @param set2
	 *            deuxi�me ensemble
	 */
	public void symmetricDifference(MySet set2) {	
		// It�rateurs sur this (it1) et sur set2 (it2)
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();
		
		// Elements courants de this (current1) et set2 (current2)
		SubSet current1 = it1.getValue(); 
		SubSet current2 = it2.getValue();
	
		// Si les deux ensembles sont �gaux
		if(this == set2)
		{
			this.clear();
			return;
		}
	
		// Parcours des deux listes jusqu'� la fin de l'une
		while(!it2.isOnFlag())
		{			
			// S'assurer d'�tre sur des SubSet de m�me rangs avant de faire l'op�ration
			// On s'appuie sur le fait que les listes soient rang�es dans l'ordre croissant des rangs
			switch(compare(current1.rank, current2.rank))
			{
				case INF:
					// El�ment appartient � this ; donc on le garde et on avance juste l'it�rateur.
					current1 = it1.nextValue();					
					break;
				case EGAL:
					// Diff�rence sym�trique des deux SubSet et avancer les deux it
					current1.set.symmetricDifference(current2.set); // R�sultat dans current1 (this)
					if(current1.set.isEmpty())
					{
						it1.remove();
						// Prochains SubSets
						current1 = it1.getValue();
						current2 = it2.nextValue();	
					}else {
						// Prochains SubSets
						current1 = it1.nextValue();
						current2 = it2.nextValue();
					}
					break;
				case SUP:
					// Elt de set2 non contenu dans this. On garde cet �l�ment dans le r�sultat
					it1.addLeft(current2.clone()); // autrement, les modif sur set2 affectent this.
					
					// Avancer l'it1 sur l'�l�ment pr�c�demment point�. current1 ne change pas
					it1.goForward();
					
					current2 = it2.nextValue();
					break;
			}
		}
	}

	/**
	 * This devient l'intersection de this et set2.
	 * 
	 * @param set2
	 *            deuxi�me ensemble
	 */
	public void intersection(MySet set2) {
		// It�rateurs sur this (it1) et sur set2 (it2)
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();
		
		// Elements courants de this (current1) et set2 (current2)
		SubSet current1 = it1.getValue(); 
		SubSet current2 = it2.getValue();
		
		// Si set2 est vide, on vide this
		if(set2.size() == 0)
		{
			this.clear();
			return;
		}
		// Parcours des deux listes jusqu'� la fin de l'une
		while(!it1.isOnFlag())
		{
			// S'assurer d'�tre sur des SubSet de m�me rangs avant de faire l'op�ration
			// On s'appuie sur le fait que les listes soient rang�es dans l'ordre croissant des rangs
			switch(compare(current1.rank, current2.rank))
			{
				case INF:
					// Supprimer le SubSet
					it1.remove();
					current1 = it1.getValue();
					break;
				case EGAL:
					// Intersection des deux SubSet et avancer les deux it
					current1.set.intersection(current2.set); // R�sultat dans current1 (this)

					// Ensemble d�sormais vide ? On le supprime.
					if(current1.set.isEmpty())
					{
						it1.remove();
						// Prochains SubSets
						current1 = it1.getValue();
						current2 = it2.nextValue();							
					}else {
						// Prochains SubSets
						current1 = it1.nextValue();
						current2 = it2.nextValue();
					}
					break;
				case SUP:					
					current2 = it2.nextValue();
					break;
			}
		}			
	}

	/**
	 * This devient l'union de this et set2.
	 * 
	 * @param set2
	 *            deuxi�me ensemble
	 */
	public void union(MySet set2) {
		// It�rateurs sur this (it1) et sur set2 (it2)
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();
		
		// Elements courants de this (current1) et set2 (current2)
		SubSet current1 = it1.getValue(); 
		SubSet current2 = it2.getValue();
				
		// Parcours des listes
		while(!it2.isOnFlag())
		{
			switch(compare(current1.rank, current2.rank))
			{
				case INF:
					current1 = it1.nextValue();
					break;
				case EGAL:
					// Union des deux SmallSets et avancer les deux it
					current1.set.union(current2.set);
					
					// Avancer les it�rateurs
					current1 = it1.nextValue();
					current2 = it2.nextValue();
					break;
				case SUP:
					it1.addLeft(current2.clone());
					// Avancer l'it1 sur l'�l�ment pr�c�demment point�. current1 ne change pas
					it1.goForward();
					
					current2 = it2.nextValue();
					break;
			}
		}
	}

	// /////////////////////////////////////////////////////////////////////////////
	// /////////////////// Egalit�, Inclusion ////////////////////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * @param o
	 *            deuxi�me ensemble
	 * 
	 * @return true si les ensembles this et o sont �gaux, false sinon
	 */
	@Override
	public boolean equals(Object o) {
		boolean b = true;
		if (this == o) {
			b = true;
		} else if (o == null) {
			b = false;
		} else if (!(o instanceof MySet)) {
			b = false;
		} else {
			// It�rateurs sur this (it1) et sur set2 (it2)
			Iterator<SubSet> it1 = this.iterator();
			Iterator<SubSet> it2 = ((MySet)o).iterator();
			
			// Elements courants de this (current1) et set2 (current2)
			SubSet current1 = it1.getValue(); 
			SubSet current2 = it2.getValue();
			
			
			if(((MySet)o).size() != this.size())
			{
				b = false;
				return b;
			}
			
			// Parcours des listes
			while(!it1.isOnFlag() && b == true)
			{
				switch(compare(current1.rank, current2.rank))
				{
					case INF:
					case SUP:
						b = false;
						break;
					case EGAL:
						// Test d'�galit� des deux SmallSets et avancer les deux it
						if(!current1.set.equals(current2.set))
							b = false;
						
						// Avancer les it�rateurs
						current1 = it1.nextValue();
						current2 = it2.nextValue();
						break;
				}				
			}
				
		}
		return b;
	}

	/**
	 * @param set2
	 *            deuxi�me ensemble
	 * @return true si this est inclus dans set2, false sinon
	 */
	public boolean isIncludedIn(MySet set2) {	
		// Si this est plus grand que set2
		if(this.size() > set2.size())
			return false;
		
		// It�rateurs sur this (it1) et sur set2 (it2)
		Iterator<SubSet> it1 = this.iterator();
		Iterator<SubSet> it2 = set2.iterator();
		
		// Elements courants de this (current1) et set2 (current2)
		SubSet current1 = it1.getValue(); 
		SubSet current2 = it2.getValue();
		
		while(!it1.isOnFlag())
		{
			switch(compare(current1.rank, current2.rank))
			{
				case INF:
					// Un elt de this n'appartient pas � set2
					return false;
				case EGAL:
					// Test sur les deux SmallSets et avancer les deux it
					if(!current1.set.isIncludedIn(current2.set))
						return false;	
					
					// Avancer les it�rateurs
					current1 = it1.nextValue();
					current2 = it2.nextValue();
					break;
				case SUP:
					// Avancer l'it�rateur de set2 jusqu'� trouver son �l�ment de m�me rang que this
					current2 = it2.nextValue();
					break;
			}				
		}
		
		return true;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// //////// Rangs, Restauration, Sauvegarde, Affichage //////////////
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * Afficher les rangs pr�sents dans this.
	 */
	public void printRanks() {
		System.out.println(" [version corrigee de rangs]");
		this.printRanksAux();
	}

	private void printRanksAux() {
		int count = 0;
		System.out.println(" Rangs presents :");
		Iterator<SubSet> it = this.iterator();
		while (!it.isOnFlag()) {
			System.out.print("" + it.getValue().rank + "  ");
			count = count + 1;
			if (count == 10) {
				System.out.println();
				count = 0;
			}
			it.goForward();
		}
		if (count > 0) {
			System.out.println();
		}
	}

	/**
	 * Cr�er this � partir d'un fichier choisi par l'utilisateur contenant une
	 * s�quence d'entiers positifs termin�e par -1 (cf f0.ens, f1.ens, f2.ens,
	 * f3.ens et f4.ens).
	 */
	public void restore() {
		String fileName = readFileName();
		InputStream inFile;
		try {
			inFile = new FileInputStream(fileName);
			System.out.println(" [version corrigee de restauration]");
			this.clear();
			this.add(inFile);
			inFile.close();
			System.out.println(" nouveau contenu :");
			this.printNewState();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("fichier " + fileName + " inexistant");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("probleme de fermeture du fichier " + fileName);
		}
	}

	/**
	 * Sauvegarder this dans un fichier d'entiers positifs termin� par -1.
	 */
	public void save() {
		System.out.println(" [version corrigee de sauvegarde]");
		OutputStream outFile;
		try {
			outFile = new FileOutputStream(readFileName());
			this.print(outFile);
			outFile.write("-1\n".getBytes());
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("pb ouverture fichier lors de la sauvegarde");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("probleme de fermeture du fichier");
		}
	}

	/**
	 * @return l'ensemble this sous forme de cha�ne de caract�res.
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int count = 0;
		SubSet subSet;
		int startValue;
		Iterator<SubSet> it = this.iterator();
		while (!it.isOnFlag()) {
			subSet = it.getValue();
			startValue = subSet.rank * 256;
			for (int i = 0; i < 256; ++i) {
				if (subSet.set.contains(i)) {
					String number = String.valueOf(startValue + i);
					int numberLength = number.length();
					for (int j = 6; j > numberLength; --j) {
						number += " ";
					}
					result.append(number);
					++count;
					if (count == 10) {
						result.append("\n");
						count = 0;
					}
				}
			}
			it.goForward();
		}
		if (count > 0) {
			result.append("\n");
		}
		return result.toString();
	}

	/**
	 * Imprimer this dans outFile.
	 * 
	 * @param outFile
	 *            flux de sortie
	 */
	private void print(OutputStream outFile) {
		try {
			String string = this.toString();
			outFile.write(string.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Afficher l'ensemble avec sa taille et les rangs pr�sents.
	 */
	private void printNewState() {
		this.print(System.out);
		System.out.println(" Nombre d'elements : " + this.size());
		this.printRanksAux();
	}

	/**
	 * @param scanner
	 * @param min
	 *            valeur minimale possible
	 * @return l'entier lu au clavier (doit être entre min et 32767)
	 */
	private static int readValue(Scanner scanner, int min) {
		int value = scanner.nextInt();
		System.out.println("read value = " + value);
		while (value < min || value > 32767) {
			System.out.println("valeur incorrecte");
			value = scanner.nextInt();
		}
		return value;
	}

	/**
	 * @return nom de fichier saisi psar l'utilisateur
	 */
	private static String readFileName() {
		System.out.print(" nom du fichier : ");
		String fileName = standardInput.next();
		return fileName;
	}
}
