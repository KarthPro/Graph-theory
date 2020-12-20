package l3new_tg_e3_code;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * classe Partie1 permettant de traiter les fonctions de la partie 1 du cahier des charges du projet des graphes
 */
public class L3NEW_TG_E3_Partie1 {

	/*
	 * classe interne Arc
	 * correspond aux arcs du graphe traité
	 */
    public class Arc {
        int in, out, valeur;

        //constructor
        public Arc(int in, int out, int valeur) {
            this.in = in;			//n° de Tâche de départ
            this.out = out;			//n° de Tâche d'arrivée
            this.valeur = valeur;	//valeur de l'arc
        }
        
        public String toString() {
        	return in+" -> "+out+" = "+valeur;
        }
    }
   
    private int n; //nombre de sommets
    private int m; //nombre d'arcs
    private int[][] maAdj; //matrice d'adjacence
    private String[][] maValeur; //matrice de valeur
    private ArrayList<Arc> list; //liste d'objets Arc
    Boolean j = true; //boolean vérifiant si le numéro du sommet courant est suppérieur au nb de sommets du graphe

    //constructor
    public L3NEW_TG_E3_Partie1() {
        this.n = 0;
        this.m = 0;
        this.list = new ArrayList<>();
    }

	/** LireGraphe() permet de créer les matrices d'adjacence et de valeurs
	 * @param fileName: Le numéro du graphe
	 */
    public void LireGraphe(String fileName) throws Exception {
    	try {
    		n = readFile("src/l3new_tg_e3_code/L3NEW-TG-E3-g"+fileName+".txt", list);
            m = list.size();
            maAdj = new int[n][n];
            maValeur = new String[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    maValeur[i][j] = "*";
                }
            }
            try {
                RemplirMatrice();
            } catch (Exception e) {
                System.out.println("le numero de sommet doit inferieur de nombre de sommet");
                j = false;
            }
    	} catch (Exception e) {
    		throw new Exception();
    	}      
    }

	/** readFile() permet de récupérer le nombre de sommets du graphe et de créer des objets Arc
	 * @param filename: Le numéro du graphe
	 * @param arcs: ArrayList d'objets Arc (vide)
	 * @return n: Le nombre de sommets
	 */
    private int readFile(String filename, ArrayList<Arc> arcs) throws Exception {
        int n = 0;//nombre de sommet
        Scanner scanner;
        
        try {
            FileInputStream fis = new FileInputStream(new File(filename));
            scanner = new Scanner(new BufferedInputStream(fis));
           
        } catch (FileNotFoundException e) {
            throw new Exception();
        }

        int index = 0;
        while (scanner.hasNextLine()) {
            if (index == 0) {
                n = scanner.nextInt();
                index = 1;
            } else {
                arcs.add(new Arc(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
            }
        }
        return n;
    }

    /** RemplirMatrice() permet de remplir les matrices d'ajacence et de valeurs
	 */
    private void RemplirMatrice() throws Exception {
        //ArrayList<Integer>[] s = new ArrayList[n];
        for (int i = 0; i < m; i++) {
            maAdj[list.get(i).in][list.get(i).out] = 1;
            maValeur[list.get(i).in][list.get(i).out] = String.valueOf(list.get(i).valeur);
        }
    }
    
    /** AffichageMatriceAdj() permet d'afficher la matrice d'adjacence 
	 * @return AffichageMatriceAdj(maAdj): Appel d'une fonction permettant d'afficher la matrice d'adjacence
	 * 		   s'il n'y a pas de problèmes avec le nombre de sommets
	 * @return Message d'erreur
			   s'il y a pas de problèmes avec le nombre de sommets
	 */
    public String AffichageMatriceAdj() {
        if (j) return AffichageMatriceAdj(maAdj);
        else return "Erreur rencontrée lors de l'affichage de la matrice de valeurs";
    }

    /** AffichageMatriceAdj(maAdj) permet d'afficher la matrice d'adjacence
	 * @param maAdj: matrice d'adjacence
	 * @return String: permettant l'affichage de la matrice d'adjacence
	 */
    private String AffichageMatriceAdj(int[][] maAdj) {
        StringBuilder resA = new StringBuilder();
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                //1ère ligne
            	if (i == 0) {
                    if (j == 0) {
                        resA.append("  ");
                        continue;
                    }
                    if( j>10 ) {
                    	resA.append(" " + (j - 1));
                    }
                    else {
                    	resA.append("  " + (j - 1));
                    }
                    //fin de la ligne
                    if (j == n)
                        resA.append("\n");
                } 
            	//1ère colonne
            	else if (j == 0) {
            		 if( i<=10 ) {
                     	resA.append((i - 1)+ " ");
                     }
                     else {
                     	resA.append(i - 1);
                     }
                } else {
                	resA.append("  " + maAdj[i - 1][j - 1]);
                    if (j == n)
                        resA.append("\n");
                }
            }
        }
        return resA.toString();
    }

    /** AffichageMatriceValeur() permet d'afficher la matrice d'adjacence 
	 * @return AffichageMatriceValeur(maValeur): Appel d'une fonction permettant d'afficher la matrice d'adjacence
	 * 		   s'il n'y a pas de problèmes avec le nombre de sommets
	 * @return Message d'erreur
			   s'il y a pas de problèmes avec le nombre de sommets
	 */
    public String AffichageMatriceValeur() {
        if (j) return AffichageMatriceValeur(maValeur);
        else return "Erreur rencontrée lors de l'affichage de la matrice de valeurs";
    }

    /** AffichageMatriceValeur(maValeur) permet d'afficher la matrice de valeurs
	 * @return String: permettant l'affichage de la matrice de valeurs
	 */
    private String AffichageMatriceValeur(String[][] maValeur) {
        StringBuilder resA = new StringBuilder();
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                //1ère ligne
            	if (i == 0) {
                    if (j == 0) {
                        resA.append("  ");
                        continue;
                    }
                    if( j>10 ) {
                    	resA.append(" " + (j - 1));
                    }
                    else {
                    	resA.append("  " + (j - 1));
                    }
                    //fin de la ligne
                    if (j == n)
                        resA.append("\n");
                } 
            	//1ère colonne
            	else if (j == 0) {
            		 if( i<=10 ) {
                     	resA.append((i - 1)+ " ");
                     }
                     else {
                     	resA.append(i - 1);
                     }
                } else {
                	if( maValeur[i - 1][j - 1].length() == 2 ) {
                		resA.append(" " + maValeur[i - 1][j - 1]);
                	}
                	else {
                		resA.append("  " + maValeur[i - 1][j - 1]);
                	}
                    if (j == n)
                        resA.append("\n");
                }
            }
        }
        return resA.toString();
    }

    public String Rang() {
        StringBuilder res = new StringBuilder();
        res.append("Calcul des rangs\n");
        res.append("Méthode d’élimination des points d’entrée\n");

        //Etant donné qu'on a besoin de stocker les sommets et leur demi-degres correspondants, choix d'une collection à 2 dimensions
        //dimension 1:  le rang ; dimension 2: la liste des sommets ayant le rang associé
        //utilisation d'une map
        
        //s permet de stocker en clé le n° de rang et en valeur la liste des n° de sommets associés
        HashMap<Integer, ArrayList<Integer>> s = new HashMap<>();
        //d tableau associant au n° de sommet, le nombres d'arcs sortants
        int d[] = new int[n];
        int k = 0;
        //a Liste des numéros de sommets en cours de traitement
        ArrayList<Integer> a = new ArrayList<>();

       //pour gagner demi-degres //calculer le nb d'arcs sortants
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                d[i] += maAdj[j][i];
            }

            //trouver et intialiser la racine
            if (d[i] == 0) {
                a.add(i);
                s.put(0, a);
            }
        }

        while (k < n) {
            ArrayList<Integer> arr = new ArrayList<>();
            res.append("Rang Courant = " + k + "\n");
            res.append("Point d'entree : \n");
            try {
                int somme = 0;
                //si il y a un circuit, on rencontre forcement s.get(k) = vide avant que k arrive au maximal
                //Donc "for (int sommet : s.get(k))" engendre surment une NullPointerException.
                
                //Pour chaque sommet de rang k:
                for (int sommet : s.get(k)) {
                	//ajout de l'affichage du sommet
                    res.append(sommet + " ");
                    for (int j = 0; j < n; j++) {
                    	//suppression des arcs sortants du sommet
                        if (maAdj[sommet][j] == 1) {
                            d[j] -= 1;
                            if (d[j] == 0) {
                            	//ajout des sommets à la hash map s où l'on a supprimé les arcs sortants
                                arr.add(j);
                                s.put(k + 1, arr);
                            }
                            somme++;
                        }
                    }

                    // dans le cas où il n'y a plus de sommet
                    if (somme == 0) {
                    	res.append("\n");
                        res.append("Graphe vide\n");
                        res.append("Rangs calculés : \n");
                        for (int i = 0; i < s.size(); i++) {
                            res.append("Rang " + i + " : ");
                            for (int g : s.get(i))
                                res.append(g + " ");
                            res.append("\n");
                        }
                        return res.toString();
                    }
                }
                res.append("\n");
                k++;
            } catch (NullPointerException e) {
                return  new String("il existe au moins un circuit dans le graphe\n");
            }
        }

        return res.toString();
    }
    //On utilise l'algorithme de Warshall pour trouver la fermeture transitive de la matice puis on detecte les circuits sur la diagonale;
    /** DecCircut() permet de detecter les circuits
   	 * @return res: affichage permettant de savoir si un circuit a été detecté
   	 */
    public String DecCircut() {
        StringBuilder res = new StringBuilder();
        int[][] fer = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(maAdj[i], 0, fer[i], 0, n);
        }
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (fer[x][y] == 1)
                    for (int i = 0; i < n; i++) {
                        fer[x][i] = fer[x][i] | fer[y][i];
                    }
            }
        }
        
        res.append("Methode : par observation de la matrice de la fermeture transitive \n");
        res.append(AffichageMatriceAdj(fer));

        for (int i = 0; i < n; i++) {
            if (fer[i][i] == 1) {
                res.append("on detecte sur la diagonale de la matrice que ["+i+"] ["+i+"] = 1, \ndonc le graphe contient au moins un circuit\n");
                return res.toString();
            }
        }

        res.append("Le graphe ne contient pas de circuit\n");
        return res.toString();
    }

    //Getters
    public int getN() {
        return n;
    }

    public String[][] getMaValeur() {
        return maValeur;
    }

    public int getM() {
        return m;
    }

    public int[][] getMaAdj() {
        return maAdj;
    }

    public ArrayList<Arc> getList() {
		return list;
	}

	@Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("nombre sommets : " + n + "\n");
        res.append("nombre d'arcs : " + m + "\n");
        for (Arc arc : list) {
            res.append(arc.in + " -> " + arc.out + " = " + arc.valeur + "\n");
        }
        return res.toString();
    }
}