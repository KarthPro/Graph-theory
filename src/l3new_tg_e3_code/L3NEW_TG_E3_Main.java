package l3new_tg_e3_code;

import java.util.Scanner;
public class L3NEW_TG_E3_Main {

	public static void main(String[] args) {
		//Partie1 p1= new Partie1();
		//Partie2 p2= new Partie2();
		Scanner sc = new Scanner(System.in);
		String testez_quittez;
		String numGraph;

		System.out.println(" # # # # # # # # # # # # # # # # # # # \n");
		System.out.println("~ Bienvenue");

		do {
			do {

				System.out.println("\n\n~ Que souhaitez-vous faire ?");
				System.out.println("               T : Testez un graphe");
				System.out.println("               Q : Quittez l'application");
				testez_quittez= sc.nextLine();

			}while(!testez_quittez.toUpperCase().equals("T") && !testez_quittez.toUpperCase().equals("Q"));

			if(testez_quittez.toUpperCase().equals("T")) {

				System.out.println("Quelle graphe souhaitez vous testez ?");
				numGraph= sc.nextLine();
				L3NEW_TG_E3_Partie1 p1= new L3NEW_TG_E3_Partie1();
				try {
					p1.LireGraphe(numGraph);

					//PARTIE 1

					// 1
					System.out.println("\n* Lecture du graphe sur fichier");
					System.out.println(p1.toString());	

					//2
					System.out.println("* Représentation du graphe sous forme matricielle");
					System.out.println("  Matrice d’adjacence");
					System.out.println(p1.AffichageMatriceAdj());
					System.out.println("  Matrice des valeurs");
					System.out.println(p1.AffichageMatriceValeur());

					//3
					System.out.println("* Détection de circuit");
					System.out.println(p1.DecCircut());

					//4
					System.out.println("* Calcul des rangs");
					System.out.println(p1.Rang());


					//PARTIE 2
					
					//5
					L3NEW_TG_E3_Partie2 p2= new L3NEW_TG_E3_Partie2();
					p2.LireGraphe(numGraph);
					//si le graphe est un graphe d'ordonnancement, on execute les fonctions liés à l'ordonnancement
					if(p2.Verification()) {
						System.out.println("\n*C'est un graphe d'ordonnancement");

						//6.1
						System.out.println("\n* Calcul du calendrier au plus tôt");
						// Affichage du calendrier des dates au plus tard
						p2.AffichageCalendrier(p2.Plustot());

						//6.2
						System.out.println("\n\n* Calcul du calendrier au plus tard");
						// Affichage du calendrier des dates au plus tard
						p2.AffichageCalendrier(p2.Plustard());

						//6.3
						System.out.println("\n\n* Calcul des marges");
						System.out.println("  Marges totales:");
						// Affichage des marges totales
						p2.AffichageMargesTotales(p2.margeTotal());
						// Affichage des marges libres
						System.out.println("\n  Marges libres:");
						p2.AffichageMargesLibres(p2.margesLibres());
					}
					else {
						System.out.println("\n *Ce n'est pas un graphe d'ordonnancement ");
					} 
				} catch(Exception e) {
					System.out.println("Le graphe "+numGraph+" n'existe pas");
				}
			}

		}while(!testez_quittez.toUpperCase().equals("Q"));

		System.out.println("~ Merci et à bientôt");
		System.out.println(" # # # # # # # # # # # # # # # # # # # \n");
	}

}
