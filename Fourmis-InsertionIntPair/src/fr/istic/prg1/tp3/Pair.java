package fr.istic.prg1.tp3;

/**
 * 
 * @author Gaspard KINDJI <chabkind@gmail.com>
 * @author D�borah GELIN <deborahgelin@hotmail.fr>
 * 
 * @version 1.0
 * @since 2019-10-10
 * 
 *        Classe repr�sentant des doublets non modifiables
 */

public class Pair implements Comparable<Pair> {

	private int x;
	private int y;
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY() 
	{
		return this.y;
	}
	
	public Pair(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}	
	
	@Override
	/**
	 * Compare this (objet courant) � d
	 * 
	 * @param	d
	 * 			Paire � comparer � this
	 * 
	 * @return	0
	 * 			si this == d
	 * 			-1
	 * 			si this < d
	 * 			1
	 * 			si this > d
	 */
	public int compareTo(Pair d)
	{
		// Inf�riorirt�
		if((this.getX() < d.getX()) || ((this.getX() == d.getX()) && (this.getY() < d.getY())))
		{
			return -1;
		}
		
		// Egalit�
		if(equals(d))
		{
			return 0;
		}
		
		// Sup�riorit�
		return 1;		
	}

	@Override
	public Pair clone() {
	    return null;
	}

	@Override
	public String toString() {
	    return "x = " + x + " y = " + y;
	}
	
	/**
	 * Compare deux paires this (objet courant) et obj pour d�terminer si this < obj
	 * 
	 * @param   obj
	 * 			paire � comparer � this
	 * @return	true
	 * 			si this < obj
	 * 			False
	 * 			si this > obj
	 */
	public boolean less(Object obj) {
		if((this.getX() < ((Pair)obj).getX()) || ((this.getX() == ((Pair)obj).getX()) && (this.getY() < ((Pair)obj).getY()))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Pair)) {
			return false;
		}
		if((this.getX() == ((Pair)obj).getX()) && (this.getY() == ((Pair)obj).getY()))
		{
			return true;
		}
		return false;
	}
}