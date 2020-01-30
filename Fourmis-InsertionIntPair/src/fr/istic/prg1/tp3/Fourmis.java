package fr.istic.prg1.tp3;
import java.io.IOException;

/**
 * 
 * @author Gaspard KINDJI <chabkind@gmail.com>
 * @author Déborah GELIN <deborahgelin@hotmail.fr>
 * 
 * @version 1.0
 * @since 2019-10-10
 * 
 *        Suite de Conway <https://fr.wikipedia.org/wiki/Suite_de_Conway>
 */

public class Fourmis
{
	
	 public static void main(String[] args) throws IOException 
	 {
		 String ui = "1";
	    
		 System.out.println("Les 10 premiers termes de la suite des fourmis : \n");
	        
	     System.out.println("u0 = "+ui);
	        
	     for(int i=0; i<10;i++)
	     {
	    	 //Appel de la fonction permettant de "calculer" le terme suivant et stockage dans la variable
	         ui = next(ui);
	         System.out.println("u"+String.valueOf(i+1)+" = "+ ui);
	     }
	 }
	 
	 /**
	 * @param s
	 * 			un terme de la suite des fourmis
	 * @pre		s.length() > 0
	 * @return  le terme suivant de la suite des fourmis
	 * 
	 *
	 */
	 
	 public static String next(String ui)
	 {
		 char terme= ' '; 
	     String Uiplus1="";
	     int compteur=1;
	        
	     for(int i = 0 ; i<ui.length(); i++)
	     {
	    	 if(terme == ui.charAt(i))
	         {
	    		 compteur++;
	             if(i==ui.length()-1)
	             {
	            	 Uiplus1 = Uiplus1 + compteur +ui.charAt(i);
	             }
	         }
	         else
	         {
	             if( i > 0)
	             {
	                 Uiplus1 = Uiplus1 + compteur +ui.charAt(i-1);
	                 compteur = 1;
	             }
	                
	             if(i==ui.length()-1)
	             {
	                 Uiplus1 = Uiplus1 + compteur +ui.charAt(i);   
	             }
	             else
	             {
	                    terme = ui.charAt(i); 
	                    compteur=1;
	             }
	          }

	      }
	     
	     return Uiplus1;
	 }

}