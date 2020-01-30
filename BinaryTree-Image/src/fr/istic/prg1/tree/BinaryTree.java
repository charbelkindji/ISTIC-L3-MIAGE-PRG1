package fr.istic.prg1.tree;

import java.util.Stack;

import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Gaspard KINDJI <chabkind@gmail.com>
 * @author D�borah GELIN <deborahgelin@hotmail.fr>
 * 
 * @version 1.0
 * @since 2019-11-20
 * 
 * @param <T>
 *            type formel d'objet pour la classe
 *
 *            Les arbres binaires sont construits par cha�nage par r�f�rences
 *            pour les fils et une pile de p�res.
 */
public class BinaryTree<T> {

	/**
	 * Type repr�sentant les noeuds.
	 */
	private class Element {
		public T value;
		public Element left, right;

		public Element() {
			value = null;
			left = null;
			right = null;
		}

		public boolean isEmpty() {
			return left == null && right == null;
		}
	}

	private Element root;

	public BinaryTree() {
		root = new Element();
	}

	/**
	 * @return Un nouvel iterateur sur l'arbre this. Le noeud courant de
	 *         l�it�rateur est positionn� sur la racine de l�arbre.
	 */
	public TreeIterator iterator() {
	    return new TreeIterator();
	}

	/**
	 * @return true si l'arbre this est vide, false sinon
	 */
	public boolean isEmpty() {
		// Arbre vide si racine est un butoir
	    return root.isEmpty();
	}

	/**
	 * Classe repr�sentant les it�rateurs sur les arbres binaires.
	 */
	public class TreeIterator implements Iterator<T> {
		private Element currentNode;
		private Stack<Element> stack;

		private TreeIterator() {
			stack = new Stack<Element>();
			currentNode = root;
		}

		/**
		 * L'it�rateur se positionnne sur le fils gauche du noeud courant.
		 * 
		 * @pre Le noeud courant n�est pas un butoir.
		 */
		@Override
		public void goLeft() {
			assert !this.isEmpty() : "le butoir n'a pas de fils";
			stack.push(currentNode);
			currentNode = currentNode.left;
		}

		/**
		 * L'it�rateur se positionnne sur le fils droit du noeud courant.
		 * 
		 * @pre Le noeud courant n�est pas un butoir.
		 */
		@Override
		public void goRight() {
			assert !this.isEmpty() : "le butoir n'a pas de fils";
			stack.push(currentNode);
			currentNode = currentNode.right;
		}

		/**
		 * L'it�rateur se positionnne sur le p�re du noeud courant.
		 * 
		 * @pre Le noeud courant n�est pas la racine.
		 */
		@Override
		public void goUp() {
			assert !stack.empty() : " la racine n'a pas de pere";
			currentNode = stack.pop();
		}

		/**
		 * L'it�rateur se positionne sur la racine de l'arbre.
		 */
		@Override
		public void goRoot() {
			stack.clear();
			currentNode = root;
		}

		/**
		 * @return true si l'iterateur est sur un sous-arbre vide, false sinon
		 */
		@Override
		public boolean isEmpty() {
			return currentNode.isEmpty();
		}

		/**
		 * @return Le genre du noeud courant.
		 */
		@Override
		public NodeType nodeType() {

			if(this.isEmpty()) // Butoir
			{
				
				return NodeType.SENTINEL;
			}
			
			// R�cup�rer fils gauche et droit pour faire les tests
			Element leftChild = currentNode.left;
			Element rightChild = currentNode.right;
		
			if(leftChild.isEmpty() && rightChild.isEmpty()) // boutoir � gauche et � droite
			{
				return NodeType.LEAF;
			}else if(leftChild.isEmpty() && !rightChild.isEmpty()) // simple � droite - butoir � fauche
			{
				return NodeType.SIMPLE_RIGHT;
			}else if(!leftChild.isEmpty() && rightChild.isEmpty()) // simple � gauche - butoir � droite
			{
				return NodeType.SIMPLE_LEFT;
			}else
			{
				return NodeType.DOUBLE;	
			}
		}
		

		/**
		 * Supprimer le noeud courant de l'arbre.
		 * 
		 * @pre Le noeud courant n'est pas un noeud double.
		 */
		@Override
		public void remove() {
			assert nodeType() != NodeType.DOUBLE : "retirer : retrait d'un noeud double non permis";
			Element father = stack.peek();
			Element temp = currentNode;
				
			switch(this.nodeType())
			{
				// Simple � gauche ou � droite : rattacher le fils au p�re du noeud � supprimer
				case SIMPLE_LEFT:
					currentNode = currentNode.left;
					break;
				case SIMPLE_RIGHT:
					currentNode = currentNode.right;
					break;
				case LEAF:
					// currentNode devient butoir
					currentNode = currentNode.left; // ou .right
					break;
				case SENTINEL:
					return;
				
			}
				
			// MAJ du cha�nage. Noeud courant �tait fils fauche ou droit de son p�re ? 
			if(father.left == temp)
			{
				father.left = currentNode;
			}else 
			{
				father.right = currentNode;
			}
		}

		/**
		 * Vider le sous–arbre r�f�renc� par le noeud courant, qui devient
		 * butoir.
		 */
		@Override
		public void clear() {
			// Si on �tait positionn� sur la racine ...
			if(currentNode == root)
			{
				root = new Element(); // devient butoir
				currentNode = root; 
				return;
			}
			// ... sinon, ne pas changer la racine et red�finir le chainage
			Element father = stack.peek();
			Element temp = currentNode;
			currentNode = new Element();
			
			// MAJ du cha�nage. Noeud courant �tait fils fauche ou droit de son p�re ? 
			if(father.left == temp)
			{
				father.left = currentNode;
			}else 
			{
				father.right = currentNode;
			}
		}

		/**
		 * @return La valeur du noeud courant.
		 */
		@Override
		public T getValue() {
			return currentNode.value;
		}

		/**
		 * Cr�er un nouveau noeud de valeur v �  cet endroit.
		 * 
		 * @pre Le noeud courant est un butoir.
		 * 
		 * @param v
		 *            Valeur �  ajouter.
		 */

		@Override
		public void addValue(T v) {
			assert isEmpty() : "Ajouter : on n'est pas sur un butoir";
			currentNode.value = v;
			// Ajouter butoirs
			currentNode.left = new Element();
			currentNode.right = new Element();
		}

		/**
		 * Affecter la valeur v au noeud courant.
		 * 
		 * @param v
		 *            La nouvelle valeur du noeud courant.
		 */
		@Override
		public void setValue(T v) {
			currentNode.value = v;
		}

		private void ancestor(int i, int j) {
			assert !stack.empty() : "switchValue : argument trop grand";
			Element x = stack.pop();
			if (j < i) {
				ancestor(i, j + 1);
			} else {
				T v = x.value;
				x.value = currentNode.value;
				currentNode.value = v;
			}
			stack.push(x);
		}

		/**
		 * Echanger les valeurs associ�es au noeud courant et � son p�re d'ordre
		 * i (le noeud courant reste inchang�).
		 * 
		 * @pre i>= 0 et racine est p�re du noeud courant d'ordre >= i.
		 * 
		 * @param i
		 *            ordre du p�re
		 */
		@Override
		public void switchValue(int i) {
			assert i >= 0 : "switchValue : argument negatif";
			if (i > 0) {
				ancestor(i, 1);
			}
		}
	}
}
