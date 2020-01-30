package fr.istic.prg1.list_util;

/**
 * @author Gaspard KINDJI <chabkind@gmail.com>
 * @author Déborah GELIN <deborahgelin@hotmail.fr>
 * 
 * @version 1.0
 * @since 2019-10-28
 * 
 *        Classe représentant une liste en double chainage par references
 */
public class List<T extends SuperT> {

	private class Element {
		// element de List<Item> : (Item, Element, Element)
		public T value;
		public Element left, right;

		public Element() {
			value = null;
			left = null;
			right = null;
		}
	}

	public class ListIterator implements Iterator<T> {
		private Element current;

		private ListIterator() 
		{ 
			current = flag.right;
		}

		@Override
		public void goForward() 
		{
			current = current.right;	
		}

		@Override
		public void goBackward() 
		{ 
			current = current.left;
		}

		@Override
		public void restart() 
		{
			// Aller sur le flag...
			while(!this.isOnFlag())
			{
				this.goForward();
			}
			
			// ... puis avancer une fois pour se positionner sur la tete
			this.goForward();
		}

		@Override
	    public boolean isOnFlag() 
		{ 
			if(current.equals(flag))
				return true;
			
			return false; 
		}

		@Override
		public void remove() {
			try 
			{
				assert current != flag : "\n\n\nimpossible de retirer le drapeau\n\n\n";
				Element leftNeighbour = current.left;
				Element rightNeighbour = current.right;

				// Redefinir le chainage pour les voisins gauche et droit de l'element a supprimer
				leftNeighbour.right = rightNeighbour;
				rightNeighbour.left = leftNeighbour;
				current = rightNeighbour;
			} catch (AssertionError e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		@Override		 
		public T getValue() { return current.value; }

		@Override
        public T nextValue() 
		{ 
			this.goForward();
			return this.getValue(); // ... ou return current.value;
		}

		@Override
		public void addLeft(T v) 
		{
			// Nouvel element de liste
			Element newElement = new Element();
			newElement.value = v;
			
			// Nouveau chainage
			Element leftNeighbour = current.left;
			newElement.left = leftNeighbour;
			newElement.right = current;
			
			current.left = newElement;
			leftNeighbour.right = newElement;
			
			// Positionner l'element courant sur le nouvel element
			current = newElement;
		}

		@Override
		public void addRight(T v) 
		{
			// Nouvel element de liste
			Element newElement = new Element();
			newElement.value = v;
			
			// Nouveau chainage
			Element rightNeighbour = current.right;
			newElement.left = current;
			newElement.right = rightNeighbour;
			
			current.right = newElement;
			rightNeighbour.left = newElement;
			
			// Positionner l'element courant sur le nouvel element
			current = newElement;
		}

		@Override
		public void setValue(T v) 
		{
			current.value = v;
		}

		@Override
		public String toString() 
		{
			return "parcours de liste : pas d'affichage possible \n";
		}

	} // class IterateurListe

	private Element flag;

	public List() 
	{
		flag = new Element();
		flag.left = flag;
		flag.right = flag;
	}

	public ListIterator iterator() { return new ListIterator(); }

	public boolean isEmpty() 
	{ 
		// S'il n'y a que le flag
		if(flag.left == flag && flag.right == flag)
			return true;
		return false; 
	}

	public void clear() 
	{
		flag.left = flag;
		flag.right = flag;
	}

	public void setFlag(T v) 
	{
		flag.value = v;
	}

	public void addHead(T v) 
	{
		// Nouvel element de liste
		Element newElement = new Element();
		newElement.value = v;
		
		// Nouveau chainage
		Element rightNeighbour = flag.right;
		newElement.left = flag;
		newElement.right = rightNeighbour;
		
		flag.right = newElement;
		rightNeighbour.left = newElement;
	}

	public void addTail(T v) 
	{
		// Nouvel element de liste
		Element newElement = new Element();
		newElement.value = v;
		
		// Nouveau chainage
		Element leftNeighbour = flag.left;
		newElement.left = leftNeighbour;
		newElement.right = flag;
		
		flag.left = newElement;
		leftNeighbour.right = newElement;
	}

	@SuppressWarnings("unchecked")
	public List<T> clone() {
		List<T> nouvListe = new List<T>();
		ListIterator p = iterator();
		while (!p.isOnFlag()) {
			nouvListe.addTail((T) p.getValue().clone());
			p.goForward();
		}
		return nouvListe;
	}

	@Override
	public String toString() {
		String s = "contenu de la liste : \n";
		ListIterator p = iterator();
		while (!p.isOnFlag()) {
			s = s + p.getValue().toString() + " ";
			p.goForward();
		}
		return s;
	}
}
