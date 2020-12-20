package l3new_tg_e3_code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;

import l3new_tg_e3_code.L3NEW_TG_E3_Partie1.Arc;

/*
 * classe Partie2 permettant de traiter les fonctions de la partie 2 du cahier des charges du projet des graphes (ordonnancement)
 */
public class L3NEW_TG_E3_Partie2 {
    private L3NEW_TG_E3_Partie1 p; //instance de la partie1 permettant notamment de récupérer des données
    int[][] matAdj; //matrice d'adjacence
    String[][] matVal; //matrice de valeur
    int n; //nombre de sommets
    private Map<Integer, LinkedList<Integer>> adj; //map de matrice d'adjacence
    private Map<Integer, Integer> val;// utilisation LinkedList<Integer> a Integer parce que pour un sommet dans graphe d'ordonnancement toutes ses valeurs sont identiques
    								  //map de matrice de valeurs

    //constructeur
    public L3NEW_TG_E3_Partie2() {
        p = new L3NEW_TG_E3_Partie1();
    }

    /** LireGraphe() permet de créer les matrices d'adjacence et de valeurs et de récupérer le nombre de sommets
	 * @param fileName: Le numéro du graphe
	 */
    public void LireGraphe(String fileName) throws Exception {
        p.LireGraphe(fileName);
        n = p.getN();
        this.matAdj = p.getMaAdj();
        this.matVal = p.getMaValeur();
    }
    
    /** MatriceVersList() permet de transformer les matrices d'adjacence et de valeurs vers une liste d'adjacence et liste de valeurs
	 */
    private void MatriceVersList() {
        adj = new TreeMap<>();
        val = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            LinkedList<Integer> arr1 = new LinkedList<>();
            for (int j = 0; j < n; j++) {
                if (matAdj[i][j] == 1) {
                    arr1.add(j);
                    adj.put(i, arr1);
                    val.put(i, Integer.valueOf(matVal[i][j]));
                }
            }
        }
    }

    /** Verification() permet d'effectuer l'ensemble des verifications  pour effectuer l'ordonnancement
	 * @return boolean: permet de savoir s'il est possible de passer aux fonctions d'ordonnancement
	 */
    public boolean Verification() {
        if (VerificatonES()) {
            if (DecCircuit()) {
                if (VerifValeurIdentiqueEtNonNegatif()) {
                    if (VerifiValeurEntreeNulle()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** VerificatonES() permet d'effectuer la  verification que la graphe ait un seul point d'entrée et un seul point de sortie
	 * @return boolean: permet de savoir si cette vérification est validée
	 */
    private boolean VerificatonES() {
        int sommeE = 0;
        int sommeS = 0;

        for (int i = 0; i < p.getN(); i++) {
            int entree = 0;
            int sortie = 0;
            for (int j = 0; j < p.getN(); j++) {
                if (p.getMaAdj()[i][j] == 1) sortie++;
                if (p.getMaAdj()[j][i] == 1) entree++;
            }
            if (entree == 0) sommeE++;
            if (sortie == 0) sommeS++;
        }

        if (sommeE == 1 && sommeS == 1) {
            return true;
        }
        return false;
    }

    /** DecCircuit() permet d'effectuer la  verification que la graphe ne possède pas de circuit
	 * @return boolean: permet de savoir si cette vérification est validée
	 */
    private boolean DecCircuit() {
        int[][] fer = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matAdj[i], 0, fer[i], 0, n);
        }
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (fer[x][y] == 1)
                    for (int i = 0; i < n; i++) {
                        fer[x][i] = fer[x][i] | fer[y][i];
                    }
            }
        }

        for (int i = 0; i < n; i++) {
            if (fer[i][i] == 1) {
                return false;
            }
        }
        return true;
    }

    /** VerifValeurIdentiqueEtNonNegatif() permet de savoir s'il y a des valeurs identiques pour tous les arcs incidents vers l'exté�?rieur d'un sommet et qu'il n'y ait pas d'arcs de valeur negative.
	 * @return boolean: permet de savoir si cette vérification est validée
	 */
    private boolean VerifValeurIdentiqueEtNonNegatif() {
        int n = p.getN();
        String[][] mat = p.getMaValeur();
        for (int i = 0; i < n; i++) {
            Set<Integer> set = new TreeSet<>();
            for (int j = 0; j < n; j++) {
                if (mat[i][j] != "*") {
                    if (Integer.valueOf(mat[i][j]) < 0)
                        return false;
                    set.add(Integer.valueOf(mat[i][j]));
                }
            }
            if (set.size() > 1) return false;
        }
        return true;
    }

    //- 
    /** VerifiValeurEntreeNulle() verifie les arcs incidents vers l'exterieur au point d'entre�?e de valeur nulle
	 * @return boolean: permet de savoir si cette vérification est validée
	 */
    private boolean VerifiValeurEntreeNulle() {
        int n = p.getN();
        String[][] mat = p.getMaValeur();
        for (int i = 0; i < n; i++) {
            int temp = 0;
            for (int j = 0; j < n; j++) {
                if (mat[j][i] == "*") temp++;
            }
            if (temp == n) {
                for (int k = 0; k < n; k++) {
                    if (mat[i][k] != "*") {
                        if (Integer.valueOf(mat[i][k]) == 0)
                            return true;
                        else return false;
                    }
                }
            }
        }
        return false;
    }

    /** TopologiqueDfs() effectue le tri topologique du graphe
	 * @return stack: pile topologique créée
	 */
    public Stack<Integer> TopologiqueDfs() {
        MatriceVersList();
        Stack<Integer> stack = new Stack<>();
        int n = p.getN();
        Boolean[] dv = new Boolean[n];// marque deja vu
        for (int i = 0; i < n; i++) {
            dv[i] = false;
        }

        for (int i = 0; i < n; i++) {
            if (dv[i] == false) {
                Visite(i, dv, stack);
            }
        }

        return stack;
    }

    /** Visite() permet d'effectuer le tri topologique du graphe et remplir la pile
	 */
    private void Visite(int i, Boolean[] dv, Stack stack) {

        dv[i] = true;
        Integer k;
        if (adj.containsKey(i)) {
            Iterator<Integer> it = adj.get(i).iterator();

            while (it.hasNext()) {
                k = it.next();
                if (!dv[k]) {
                    Visite(k, dv, stack);
                }
            }
        }
        stack.push(i);
    }

    /** Plustot() permet de calculer les dates au plus tôt de chaque sommet grâce au tri topologique
	 * @return dist: tableau d'entiers des dates au plus tôt de chaque sommet
	 */
    public int[] Plustot() {
        if (Verification()) {
            int[] dist = new int[n];
            Stack<Integer> stack = TopologiqueDfs();
            for (int i = 0; i < n; i++) {
                dist[i] = Integer.MIN_VALUE;
            }

            dist[stack.peek()] = 0;

            while (!stack.isEmpty()) {
                int k = stack.pop();

                if (adj.containsKey(k)) {
                    Iterator<Integer> it = adj.get(k).iterator();
                    if (dist[k] != Integer.MIN_VALUE) {
                        while (it.hasNext()) {
                            int next = it.next();
                            if (dist[next] < dist[k] + val.get(k)) {
                                dist[next] = dist[k] + val.get(k);
                            }
                        }
                    }
                }

            }
            return dist;
        }
        return null;
    }

    /** Plustard() permet de calculer les dates au plus tard de chaque sommet grâce au tri topologique
	 * @return dist: tableau d'entiers des dates au plus tard de chaque sommet
	 */
    public int[] Plustard() {
        int[] dist = Plustot();
        Stack<Integer> stack = reverseStack(TopologiqueDfs());

        while (!stack.isEmpty()) {
            int k = stack.pop();

            if (adj.containsKey(k)) {
                Iterator<Integer> it = adj.get(k).iterator();
                boolean x = false;
                PriorityQueue<Integer> pq = new PriorityQueue<>();
                while (it.hasNext()) {
                    int next = it.next();
                    if (dist[k] <= dist[next] - val.get(k)) {
                        pq.add(dist[next] - val.get(k));
                        x = true;
                    }
                }
                if (x) {
                    dist[k] = pq.poll();
                }
            }
        }
        return dist;

    }

    /** margeTotal() permet de calculer les marges totales de chaque sommet
	 * @return marge: tableau d'entiers des marges totales
	 */
    public int[] margeTotal() {
        int[] marge = new int[n];
        int[] plustot = Plustot();
        int[] plustard = Plustard();
        for (int i = 0; i < n; i++) {
            marge[i] = plustard[i] - plustot[i];
        }
        return marge;
    }

    /** margeLibre() permet de calculer les marges libres de chaque sommet grâce au tri topologique
	 * @return dist: tableau d'entiers des marges libres
	 */
    public int[] margeLibre() {
        int[] dist = Plustot();
        Stack<Integer> stack = TopologiqueDfs();

        while (!stack.isEmpty()) {
            int k = stack.pop();
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            if (adj.containsKey(k)) {
                Iterator<Integer> it = adj.get(k).iterator();
                while (it.hasNext()) {
                    int next = it.next();
                    pq.add(dist[next]);
                }
                dist[k] = pq.poll() - dist[k] - val.get(k);
            }else
                dist[k] = 0;//vu que sommet n'a pas de succsseur (le dernier sommet)
            //selon moi,il y a pas de tâches postérieur a faire donc son marge libre est toujours 0.
        }
        return dist;
    }
    
    /** margesLibres() permet de calculer les marges libres de chaque sommet en fonction de leurs successeurs
	 * @return tabMargesLibres: tableau d'entiers des marges libres
	 */
	public int[] margesLibres() {
    	int nbSommets = p.getN();
    	int[] tabMargesLibres = new int[nbSommets];
    	//initialisation du tabMargesLibres à 0
    	for (int i = 0; i < n; i++) {
    		tabMargesLibres[i] = 0;
        }
    	int margeLibre = 0;
        int[] datesPlusTot = Plustot();
        int valeur = 0;
        ArrayList<Arc> listArcs = new ArrayList<Arc>();
        listArcs = p.getList();
        ArrayList<Integer> listDateTotSuccesseurs = new ArrayList<Integer>();
        //parcours de chacune des tâches
        for(int i=0; i<nbSommets; i++) {
        	for(Arc arc : listArcs) {
        		//si la tâche courante a au moins 1 successeur
        		if(arc.in == i) {
        			//stockage des dates au plus tot des successeurs de la tache n°i
        			listDateTotSuccesseurs.add(datesPlusTot[arc.out]);
        			valeur = arc.valeur;
        		}
        	}
        	// si la tâche n'a pas de successeurs
    		if(listDateTotSuccesseurs.size()==0) {
    			tabMargesLibres[i]=-1;
    		}
    		else {
    			//calcul de la marge libre = min des dates au plus tôt des successeurs - date au plus tôt de la tache courante - durée de la tache
    			margeLibre = Collections.min(listDateTotSuccesseurs)-datesPlusTot[i]-valeur ;
    			//stockage de la marge libre dans un tableau à l'indice souhaité
    			tabMargesLibres[i] = margeLibre;
    		}
    		listDateTotSuccesseurs.clear();
    		valeur=0;
        }
        return tabMargesLibres;
    }
	
    /** AffichageMargesLibres() permet d'afficher les marges libres de chaque sommet 
	 * @param tabMarges: tableau d'entiers des marges libres
	 */
	public void AffichageMargesLibres(int[] tabMarges) {
		System.out.print("\tn° Tâche:    ");
		for(int i=0; i<tabMarges.length; i++) {
			if(i<10) {System.out.print("  "+i);}
			else { System.out.print(" "+i);}
		}
		System.out.print("\n\tMarge libre: ");
		int cpt=0;
		for(int marge : tabMarges){
			// si la tâche n' pas de successeur
			if (marge==-1) {
				if(cpt<10) {System.out.print("  *");}
				else {System.out.print("  *");}
			}
			else {
				if(cpt<10) {
					if (marge>=10) {System.out.print(" "+marge);}
					else {System.out.print("  "+marge);}
				}
				else {
					if (marge>=10) {System.out.print(" "+marge);}
					else {System.out.print("  "+marge);}
				}
			} 
			cpt++;
        }
	}
	
    /** AffichageMargesTotales() permet d'afficher les marges libres de chaque sommet 
	 * @param tabMarges: tableau d'entiers des marges totales
	 */
	public void AffichageMargesTotales(int[] tabMarges) {
		System.out.print("\tn° Tâche:    ");
		for(int i=0; i<tabMarges.length; i++) {
			if(i<10) {System.out.print("  "+i);}
			else { System.out.print(" "+i);}
		}
		System.out.print("\n\tMarge totale:");
		int cpt=0;
		for(int marge : tabMarges){
			// si la tâche n' pas de successeur
			if (marge==-1) {
				if(cpt<10) {System.out.print("  *");}
				else {System.out.print("  *");}
			}
			else {
				if(cpt<10) {
					if (marge>=10) {System.out.print(" "+marge);}
					else {System.out.print("  "+marge);}
				}
				else {
					if (marge>=10) {System.out.print(" "+marge);}
					else {System.out.print("  "+marge);}
				}
			} 
			cpt++;
        }
	}
	
    /** AffichageCalendrier() permet d'afficher les dates(au plus tot ou plus tard en fonction du tableau en parametre) de chaque sommet 
	 * @param tabDate: tableau d'entiers des dates de chaque sommet(au plus tard ou au plus tard en fonction du tabDate)
	 */
	public void AffichageCalendrier(int[] tabDate) {
		System.out.print("n° Tâche:");
		for(int i=0; i<tabDate.length; i++) {
			if(i<10) {System.out.print("  "+i);}
			else { System.out.print(" "+i);}
		}
		System.out.print("\nDate:    ");
		int cpt=0;
		for(int date : tabDate){
			if(cpt<10) {
				if (date>=10) {System.out.print(" "+date);}
				else {System.out.print("  "+date);}
			}
			else {
				if (date>=10) {System.out.print(" "+date);}
				else {System.out.print("  "+date);}
			}
		} 
		cpt++;
	}

    private Stack<Integer> reverseStack(Stack<Integer> stack) {
        Stack<Integer> stack1 = new Stack<>();
        while (!stack.isEmpty()) {
            stack1.push(stack.pop());
        }
        return stack1;
    }
}