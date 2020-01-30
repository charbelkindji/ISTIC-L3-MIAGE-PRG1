package fr.istic.prg1.tree;

import java.util.Scanner;

import fr.istic.prg1.tree.AbstractImage;
import fr.istic.prg1.tree_util.Iterator;
import fr.istic.prg1.tree_util.Node;
import fr.istic.prg1.tree_util.NodeType;

/**
 * @author Gaspard KINDJI <chabkind@gmail.com>
 * @author Déborah GELIN <deborahgelin@hotmail.fr>
 * 
 * @version 1.0
 * @since 2019-11-20
 * 
 *        Classe décrivant les images en noir et blanc de 256 sur 256 pixels
 *        sous forme d'arbres binaires.
 */
public class Image extends AbstractImage {
	private static final Scanner standardInput = new Scanner(System.in);

	public Image() {
		super();
	}

	public static void closeAll() {
		standardInput.close();
	}

	/**
	 * @param x
	 *            abscisse du point
	 * @param y
	 *            ordonnée du point
	 * @pre !this.isEmpty()
	 * @return true, si le point (x, y) est allumé dans this, false sinon
	 */
	@Override
	public boolean isPixelOn(int x, int y) 
	{
		//initialisation
		int level=1;
		int BminX=0;
		int BmaxX=255;
		int BminY=0;
		int BmaxY=255;
		int median=0;
		Iterator<Node> it=this.iterator();
		
		while(it.nodeType()!=NodeType.LEAF)
		{
			if(level%2==0) //on traite x
			{
				median=(BmaxX+BminX)/2;

				if(x<=median)
				{
					BmaxX=median;
					it.goLeft();
				}
				else
				{
					BminX=median;
					it.goRight();
				}
				level ++;
			}
			else // on traite y
			{
				median=(BmaxY+BminY)/2;

				if(y<=median)
				{
					BmaxY=median;
					it.goLeft();
				}
				else
				{
					BminY=median;
					it.goRight();
				}
				level ++;
			}
		}
		
		// c'est une feuille
		return it.getValue().state==1; // state == 1. On doit renvoyer true si c'est le cas. 
	}

	/**
	 * this devient identique à image2.
	 *
	 * @param image2
	 *            image à copier
	 *
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void affect(AbstractImage image2) 
	{
		//initialisation
		Iterator <Node> it1=this.iterator();
		Iterator <Node> it2= image2.iterator();
		//on vide this
		it1.clear();
		affectAux(it1,it2);
	}
	
	
	/**
	 * Méthode auxiliaire pour le parcours dans l'exécution de la méthode affect
	 *  
	 * @param it1
	 * 			itérateur sur this
	 * @param it2
	 * 			itérateur sur la seconde image paramètre de la méthode affect
	 */
	private static void affectAux(Iterator<Node>it1, Iterator<Node>it2)
	{
		if(!it2.isEmpty())
		{
			//Racine
			Node current=it2.getValue();
			it1.addValue(current);
			
			//Sous arbre gauche
			it1.goLeft();
			it2.goLeft();
			affectAux(it1,it2);
			it1.goUp();
			it2.goUp();
			
			//Sous arbre droit
			it1.goRight();
			it2.goRight();
			affectAux(it1,it2);
			it1.goUp();
			it2.goUp();
		}
	}

	/**
	 * this devient rotation de image2 à 180 degrés.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate180(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		
		// Vider this
		it.clear();
		
		rotate180Aux(it, it2);		
	}

	
	/**
	 * Méthode auxiliaire pour le parcours dans l'exécution de la méthode rotate180
	 *  
	 * @param it
	 * 			itérateur sur this
	 * @param it2
	 * 			itérateur sur la seconde image paramètre de la méthode rotate180
	 */
	private static void rotate180Aux(Iterator<Node> it, Iterator<Node> it2)
	{
		// Récupérer valeur actuelle
		Node current = it2.getValue();

		// Racine
		it.addValue(current);
		
		// Inverser les sous arbres gauche et droit seulement quand on est sur un noeud double
		if(it2.nodeType() == NodeType.DOUBLE)
		{
			// Sous arbre droit de image2 devient sous arbre gauche de this
			it.goLeft();
			it2.goRight();
			rotate180Aux(it, it2);
			it.goUp();
			it2.goUp();
			
			// Sous arbre gauche de image2 devient sous arbre droit de this
			it.goRight();
			it2.goLeft();
			rotate180Aux(it, it2);
			it.goUp();
			it2.goUp();
			
		}
		
		// Si la condition ne se vérifie pas, on est sur une feuille ==> plus rien à faire.
	}
	
	/**
	 * this devient rotation de image2 à 90 degrés dans le sens des aiguilles
	 * d'une montre.
	 *
	 * @param image2
	 *            image pour rotation
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void rotate90(AbstractImage image2) {  
	}

	/**
	 * this devient inverse vidéo de this, pixel par pixel.
	 *
	 * @pre !image.isEmpty()
	 */
	@Override
	public void videoInverse() {
		Iterator<Node> it = this.iterator();
		
		videoInverseAux(it);
	}
	
	/**
	 * Méthode auxiliaire pour le parcours dans l'exécution de la méthode videoInverse
	 *  
	 * @param it
	 * 			itérateur sur this
	 */
	private static void videoInverseAux(Iterator<Node> it)
	{
		Node current = it.getValue();
		if(current.state == 0)
		{
			it.setValue(Node.valueOf(1));
		}else if(current.state == 1)
		{
			it.setValue(Node.valueOf(0));
		}else // state == 2
		{
			// Sous arbre gauche
			it.goLeft();
			videoInverseAux(it);
			it.goUp();
			
			// Sous arbre droit
			it.goRight();
			videoInverseAux(it);
			it.goUp();
		}
	}
	
	/**
	 * this devient image miroir verticale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorV(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		
		// Vider this
		it.clear();
		
		mirrorAux(it, it2, 0, true); // niveau 0 au départ. La racine et isMirrorV est true
	}


	
	/**
	 * this devient image miroir horizontale de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void mirrorH(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
		
		// Vider this
		it.clear();
		
		mirrorAux(it, it2, 0, false); // niveau 0 au départ. La racine	
	}
	
	/**
	 * Méthode auxiliaire pour les méthodes principales mirroV et mirrorH
	 * 
	 * @param it
	 * 		  itérateur sur this
	 * @param it2
	 * 		  itérateur sur image2 
	 * @param currentLevel
	 * 		  profondeur actuelle dans l'arbre
	 * @param isMirrorV
	 * 		  booleen spécifiant s'il s'agit d'un mirrorV ou d'un mirrorH
	 */
	private static void mirrorAux(Iterator<Node> it, Iterator<Node> it2, int currentLevel, boolean isMirrorV)
	{
		// Récupérer valeur actuelle
		Node current = it2.getValue();

		// Racine
		it.addValue(current);
		
		// Inverser les sous arbres gauche et droit seulement quand on est sur un noeud double et au niveau impair (axe y)
		if(it2.nodeType() == NodeType.DOUBLE)
		{
			currentLevel++;
			
			// Si on est sur un niveau pair (axe des x), inverser ses fils gauche et droit
			/** Ce bloc if s'exécute
			 * 1- Si c'est un mirrorV ET que le prochain niveau est le niveau des y (à inverser)
			 * 2- Si c'est un mirrorH ET que le prochain niveau est le niveau des x (à inverser)
			 */
			it2.goRight();
			if((isMirrorV && currentLevel % 2 != 0) || (!isMirrorV && currentLevel % 2 == 0))
			{
				// Sous arbre droit de image2 devient sous arbre gauche de this
				it.goLeft();
			}else
			{
				// Pas d'inversion
				it.goRight();
			}
			
			mirrorAux(it, it2, currentLevel, isMirrorV);
			it.goUp();
			it2.goUp();
				
			
			it2.goLeft();	
			if((isMirrorV && currentLevel % 2 != 0) || (!isMirrorV && currentLevel % 2 == 0))
			{
				// Sous arbre gauche de image2 devient sous arbre droit de this
				it.goRight();
			}else
			{
				// Pas d'inversion
				it.goLeft();
			}
			mirrorAux(it, it2, currentLevel, isMirrorV);
			it.goUp();
			it2.goUp();
		}
				
		// Si la condition ne se vérifie pas, on est sur une feuille ==> plus rien à faire.
	}
	
	/**
	 * this devient quart supérieur gauche de image2.
	 *
	 * @param image2
	 *            image à agrandir
	 * 
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomIn(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
	
		// Vider this
		it.clear();
		
		// Positionnement du pointeur. On est peut être sur un arbre contenant un seul noeud (la racine) ou un seul 
		// noeud à gauche (on ne peut pas aller deux fois à gauche)
		for (int i=0; i<2;i++)
		{
			if(it2.nodeType() == NodeType.DOUBLE)
			{
				it2.goLeft();
			}
		}
		affectAux(it, it2);
	}

	/**
	 * Le quart supérieur gauche de this devient image2, le reste de this
	 * devient éteint.
	 * 
	 * @param image2
	 *            image à réduire
	 * @pre !image2.isEmpty()
	 */
	@Override
	public void zoomOut(AbstractImage image2)  {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();	
		
		// Vider this
		it.clear();
				
		// Créer les noeuds initiaux de l'arbre résultat
		/**
		 * 		2
		 * 	   / \
		 * 	  2   0
		 *   / \
		 *  .   0
		 */

		for(int i = 0; i < 2; i++) {
			it.addValue(Node.valueOf(2));
			it.goRight();
			it.addValue(Node.valueOf(0));
			it.goUp();
			it.goLeft();			
		}		

		zoomOutAux(it, it2, 0);
		
		/**
		 * Au final, on a peut être obtenu ceci :
		 * 
		 * 		2
		 * 	   / \
		 * 	  2   0
		 *   / \
		 *  0   0
		 *  
		 *  Il faut donc corriger l'arbre
		 */

		// Si on a un noeud 0 à gauche
		if(it.getValue().state == 0)
		{
			it.goRoot();
			it.clear();
			it.addValue(Node.valueOf(0));
		}
	}

	/**
	 * Compte le nombre de noeud allumés dans le sous arbre pointé par it
	 * 
	 * @param it
	 * 		  itérateur à zoomer
	 * @return
	 */
	private static int zoomOutCountNodeOn(Iterator<Node>it)
	{
		if(it.nodeType() == NodeType.LEAF && it.getValue().state == 1)
		{
			return 1;
		}
					
		if(it.nodeType() == NodeType.DOUBLE)
		{
			
			// compter le nombre dans le sous arbre gauche 
			it.goLeft();
			int nbOn = zoomOutCountNodeOn(it);
			it.goUp();
			
			// compter le nombre dans le sous arbre droit
			it.goRight();
			nbOn += zoomOutCountNodeOn(it);
			it.goUp();
			
			// résultat
			return nbOn;
		}
		return 0; // pas allumé 
	}

	/**
	 * Indique si le sous arbre pointé par it est de hauteur 1
	 * @param it
	 * 	      itérateur sur l'arbre
	 * @return
	 */
	private static boolean isHeightOne(Iterator<Node>it)
	{
		if(it.nodeType() == NodeType.LEAF || it.isEmpty())
		{
			return false;
		}
		
		it.goLeft();
		if(it.nodeType() == NodeType.LEAF) // feuille à gauche...
		{
			it.goUp();
			it.goRight();
			if(it.nodeType() == NodeType.LEAF) // ... et feuille à droite
			{
				it.goUp();
				return true;
			}else
			{
				it.goUp();
				return false;
			}
		}else
		{
			it.goUp();
			// Allé à gauche sans tomber sur une feuille alors il y a plus d'éléments en bas
			return false;
		}
	}
	
	/**
	 * Renvoie le state du noeud à ajouter à this en faisant le calcul de la majorité 
	 * de noeuds dans le sous arbre pointé par it
	 * 
	 * @param it
	 * 			itérateur sur image2
	 * @return
	 */
	private static int zoomOutNodeToPut(Iterator<Node>it)
	{
		if(it.nodeType() == NodeType.LEAF)
		{
			return it.getValue().state;
		}else if(isHeightOne(it))
		{
			return 1;
		}
		
		// Compter nombre de noeuds allumés
		int nbOn = zoomOutCountNodeOn(it);
		
		// Si blanc majoritaire, ajouter blanc. Sinon ajouter noir.
		return nbOn >= 2 ? 1 : 0;

	}
	
	/**
	 * Méthode auxiliaire pour le parcours dans l'exécution de la méthode zoomOut
	 *  
	 * @param it1
	 * 			itérateur sur this
	 * @param it2
	 * 			itérateur sur la seconde image paramètre de zoomOut
	 * @param level
	 * 			niveau de profondeur actuel dans l'arbre
	 */
	private static void zoomOutAux(Iterator<Node>it1, Iterator<Node>it2, int level)
	{
		if(!it2.isEmpty())
		{
			if(level == 14) // Simplification
			{
				// Récupérer le noeud à ajouter
				it1.addValue(Node.valueOf(zoomOutNodeToPut(it2)));
			}else
			{
				Node current=it2.getValue();
				it1.addValue(current);

				//Sous arbre gauche
				it1.goLeft();
				it2.goLeft();
				zoomOutAux(it1, it2, level+1);
				Node leftChild = it1.getValue(); // récup. fils ajouté
				it1.goUp();
				it2.goUp();
				
				//Sous arbre droit
				it1.goRight();
				it2.goRight();
				zoomOutAux(it1,it2, level+1);

				Node rightChild = it1.getValue();
				it1.goUp();
				it2.goUp();

				if(it1.nodeType() == NodeType.DOUBLE)
				{
					if(leftChild.state != 2 && rightChild.state != 2 && leftChild.state == rightChild.state)
					{
						it1.setValue(leftChild);
						// Supprimer les fils
						it1.goLeft();
						it1.clear();
						it1.goUp();
						
						it1.goRight();
						it1.clear();
						it1.goUp();
					}
				}
			}
		}
	}
	

	/**
	 * this devient l'intersection de image1 et image2 au sens des pixels
	 * allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void intersection(AbstractImage image1, AbstractImage image2) {
		//initialisation
		Iterator <Node> it=this.iterator();
		Iterator <Node> it1=image1.iterator();
		Iterator <Node> it2=image2.iterator();
		//on vide this
		it.clear();
		intersectionAux(it,it1,it2);
	}
	
	
	/**
	 * Méthode auxiliaire pour le parcours dans l'exécution de la méthode intersection
	 *  
	 * @param it
	 * 			itérateur sur this
	 * @param it1
	 * 			itérateur sur la première image paramètre de intersection
	 * @param it2
	 * 			itérateur sur la deuxième image paramètre de intersectiont
	 */
	private static void intersectionAux(Iterator<Node> it, Iterator<Node> it1, Iterator<Node> it2)
	{
		//initialisation
		Node current1=it1.getValue();
		Node current2=it2.getValue();
		
		//racine
		if(current1.state==1 && current2.state==1)
		{
			it.addValue(Node.valueOf(1));
		}
		else if (current1.state==2 && current2.state==2)
		{
			it.addValue(Node.valueOf(2));
			
			// Sous arbre gauche
			it.goLeft();
			it1.goLeft();
			it2.goLeft();
			intersectionAux(it, it1, it2);
			// Récupérer le fils gauche ajouté
			Node leftChild = it.getValue();
			
			// Repositionner les pointeurs
			it.goUp();
			it1.goUp();
			it2.goUp();
			
			// Sous arbre droit
			it.goRight();
			it1.goRight();
			it2.goRight();
			intersectionAux(it, it1, it2);
			// Récupérer le fils droit ajouté
			Node rightChild = it.getValue();
			
			// Repositionner les pointeurs
			it.goUp();
			it1.goUp();
			it2.goUp();		

			if(leftChild.state != 2 && rightChild.state != 2)
			{
				if(leftChild.state == rightChild.state)
				{
					// it est actuellement sur la racine
					int state = leftChild.state;
					it.clear();
					it.addValue(Node.valueOf(state));
				}
			}
		}
		else if (current1.state==2 && current2.state==1)
		{
			affectAux(it,it1);
		}
		else if (current1.state==1 && current2.state==2)
		{
			affectAux(it,it2);
		}
		else
		{
			// Pour tous les autres cas
			it.addValue(Node.valueOf(0));
		}
	}
	/**
	 * this devient l'union de image1 et image2 au sens des pixels allumés.
	 * 
	 * @pre !image1.isEmpty() && !image2.isEmpty()
	 * 
	 * @param image1 premiere image
	 * @param image2 seconde image
	 */
	@Override
	public void union(AbstractImage image1, AbstractImage image2) 
	{
		//initialisation
		Iterator <Node> it=this.iterator();
		Iterator <Node> it1=image1.iterator();
		Iterator <Node> it2=image2.iterator();
		//on vide this
		it.clear();
		unionAux(it,it1,it2);
	}

	/**
	 * Méthode auxiliaire pour le parcours dans l'exécution de la méthode union
	 *  
	 * @param it
	 * 			itérateur sur this
	 * @param it1
	 * 			itérateur sur la première image paramètre de union
	 * @param it2
	 * 			itérateur sur la deuxième image paramètre de union
	 */
	private void unionAux(Iterator <Node>it,Iterator <Node>it1,Iterator <Node>it2 )
	{
		//initialisation
		Node current1=it1.getValue();
		Node current2=it2.getValue();
		
		//racine
		if(current1.state==1 || current2.state==1)
		{
			it.addValue(Node.valueOf(1));
		}
		else if (current1.state==2 && current2.state==2)
		{
			it.addValue(Node.valueOf(2));
			
			// Sous arbre gauche
			it.goLeft();
			it1.goLeft();
			it2.goLeft();
			unionAux(it, it1, it2);
			// Récupérer le fils gauche ajouté
			Node leftChild = it.getValue();
			
			// Repositionner les pointeurs
			it.goUp();
			it1.goUp();
			it2.goUp();
			
			// Sous arbre droit
			it.goRight();
			it1.goRight();
			it2.goRight();
			unionAux(it, it1, it2);
			// Récupérer le fils droit ajouté
			Node rightChild = it.getValue();
			
			// Repositionner les pointeurs
			it.goUp();
			it1.goUp();
			it2.goUp();		
			
			if(leftChild.state != 2 && rightChild.state != 2)
			{
				if(leftChild.state == rightChild.state)
				{
					// it est actuellement sur la racine
					it.setValue(leftChild);
					
					// Supprimer les fils
					it.goLeft();
					it.remove();
					it.goUp();
					
					it.goRight();
					it.remove();
					it.goUp();
				}
			}
		}
		else if (current1.state==2 && current2.state==0)
		{
			affectAux(it,it1);
		}
		else if (current1.state==0 && current2.state==2)
		{
			affectAux(it,it2);
		}
		else
		{
			it.addValue(Node.valueOf(0));
		}
	}
	
	/**
	 * Attention : cette fonction ne doit pas utiliser la commande isPixelOn
	 * 
	 * @return true si tous les points de la forme (x, x) (avec 0 <= x <= 255)
	 *         sont allumés dans this, false sinon
	 */
	@Override
	public boolean testDiagonal() {
		Iterator<Node> it = this.iterator();
		
		return testDiagonalAux(it);
	}
	
	/**
	 * Méthode auxiliaire pour le parcours dans l'exécution de la méthode testDiagonal
	 * 
	 * @param it
	 * 			itérateur sur this
	 */
	private static boolean testDiagonalAux(Iterator<Node> it)
	{
	    Node current = it.getValue();

	    if(current.state == 0)
	    {
	      return false;
	    }else if(current.state == 1)
	    {
	      return true;
	    }else // current.state == 2
	    {
	    	// Pour stocker le résultat
	    	boolean isDiagonalOn = true;
	    	
	    	// Explorer le quart supérieur gauche (sous arbre gauche)
	    	// On doit descendre deux fois à gauche pour retomber sur le 1/4 supérieur gauche correspondant (sur l'axe des x) 
	    	it.goLeft(); // ... on descend une première fois
	    	if(it.nodeType() == NodeType.LEAF)
	    	{
	    		// On ne peut plus descendre. On appelle la fonction à nouveau sur ce noeud. L'état du noeud qu'on cherche est "inclus" dans celui ci.
	    		isDiagonalOn = testDiagonalAux(it);
				// Go up pour repositionner l'itérateur
	    		it.goUp();
	    	}else 
	    	{
	    		// On peut descendre une fois de plus pour atteindre le noeud à tester
	    		it.goLeft();
	    		isDiagonalOn = testDiagonalAux(it);
				// Go up pour repositionner l'itérateur
	    		it.goUp();
	    		it.goUp();
	    	}
	    	
	    	if(isDiagonalOn)
	    	{
	    		// Explorer le quart inférieur droit (sous arbre droit)
				it.goRight();
				
				if(it.nodeType() == NodeType.LEAF)
				{
					isDiagonalOn = testDiagonalAux(it);
					it.goUp();
				}else // On peut descendre encore une fois comme souhaité
				{
					it.goRight();
					isDiagonalOn = testDiagonalAux(it);
					// Go up pour repositionner l'itérateur
					it.goUp();
					it.goUp();
				}
	    	}      

	    	return isDiagonalOn;
	    }
	    
	  }

	/**
	 * @param x1
	 *            abscisse du premier point
	 * @param y1
	 *            ordonnée du premier point
	 * @param x2
	 *            abscisse du deuxième point
	 * @param y2
	 *            ordonnée du deuxième point
	 * @pre !this.isEmpty()
	 * @return true si les deux points (x1, y1) et (x2, y2) sont représentés par
	 *         la même feuille de this, false sinon
	 */
	@Override
	public boolean sameLeaf(int x1, int y1, int x2, int y2) {

		//initialisation
		int level=1;
		int BminX=0;
		int BmaxX=255;
		int BminY=0;
		int BmaxY=255;
		int median=0;
		Iterator <Node> it=this.iterator();
		
		// Les deux sont sur la même branche à chaque fois jusqu'à la dernière feuille sinon return false
		while(it.nodeType()!=NodeType.LEAF)
		{
			if(level%2==0) //on traite x
			{
				median=(BmaxX+BminX)/2;
				if(x1 <= median && x2 <= median)
				{
					BmaxX=median;
					it.goLeft();
				}
				else if(x1 > median && x2 > median)
				{
					BminX=median;
					it.goRight();
				}else
				{
					// not same leaf
					return false;
				}
				level ++;
			}
			else 
			{
				median=(BmaxY+BminY)/2;
				if(y1 <= median && y2 <= median)
				{
					BmaxY=median;
					it.goLeft();
				}
				else if(y1 > median && y2 > median)
				{
					BminY=median;
					it.goRight();
				}else
				{
					// not same leaf
					return false;
				}
				level ++;
			}
		}
		return true;  
	}

	/**
	 * @param image2
	 *            autre image
	 * @pre !this.isEmpty() && !image2.isEmpty()
	 * @return true si this est incluse dans image2 au sens des pixels allumés
	 *         false sinon
	 */
	@Override
	public boolean isIncludedIn(AbstractImage image2) {
		Iterator<Node> it = this.iterator();
		Iterator<Node> it2 = image2.iterator();
	
	    return isIncludedInAux(it, it2);
	}
	
	/**
	 * Méthode auxiliaire pour le parcours dans l'exécution de la méthode isIncludedIn
	 * 
	 * @param it1
	 * 			itérateur sur this
	 * @param it2
	 * 			itérateur sur la seconde image paramètre de la méthode isIncludedIn
	 */
	private static boolean isIncludedInAux(Iterator<Node> it, Iterator<Node> it2)
	{
		Node current=it.getValue(); // Noeud actuel this
		Node current2=it2.getValue(); // Noeud actuel image2
		
		if(current.state == 2 && current2.state == 2)
		{			
			// On continue à fouiller les arbres. Sous arbre gauche puis sous arbre droit
			it.goLeft();
			it2.goLeft();
			boolean isIncludedPartial = isIncludedInAux(it, it2);
			it.goUp();
			it2.goUp();
			
			if(isIncludedPartial)
			{
				it.goRight();
				it2.goRight();
				isIncludedPartial = isIncludedInAux(it, it2);
				it.goUp();
				it2.goUp();
			}

			return isIncludedPartial; 

		}else if((current.state == 1 && (current2.state == 0 || current2.state == 2)) || 
				 (current.state == 2 && current2.state == 0))
		{
			return false;
		}else
		{
			// Dans les autres cas, tout va bien. Jusqu'à ce niveau de l'exploration des sous arbres, tout va bien
			return true;
		}
	}
}
