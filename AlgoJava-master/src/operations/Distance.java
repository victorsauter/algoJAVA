package operations;
import java.util.*;

public class Distance {
    // caractère Unicode représentant l'infini
    public final static char infinity = 0x221E;
    /**
     * Calcul de la distance d'un poinr S par rapport aux autres points du graphe g
     * @param g graphe du point S
     * @param S
     * @param V nombre de point à afficher
     */
    static int [] calculDistance(Graph g, int S, int V) {

        S=S-1;
        //Initialisation des distances
        int[] d = new int[V + 1];
        //queue ou on ajoute le sommet de départ
        Queue<Integer> q = new LinkedList<>();
        boolean[] inQueue = new boolean[V + 1];
        //Initialisation des distances a Integer.MAX_VALUE
        for (int i = 0; i <= V; i++) {
            d[i] = Integer.MAX_VALUE;
        }
        d[S] = 0;

        q.add(S);
        inQueue[S] = true;

        while (!q.isEmpty()) {

            // On dépile
            int u = q.peek();
            q.remove();
            inQueue[u] = false;
            //On parcours les Nodes adjacent au Node que nous traitons
            for (Map.Entry<Node, Integer> entry : g.getNodeFromName(String.valueOf(u+1)).adjacentNodes.entrySet()) {
                Node cle = entry.getKey();
                int v = Integer.parseInt(cle.getName())-1;
                Integer valeur = entry.getValue();
                int weight = valeur.intValue();
                // traitements de la distance
                if (d[v] > d[u] + weight) {
                    d[v] = d[u] + weight;
                    //Ajout d'une Node a traité dans le Queue
                    if (!inQueue[v]) {
                        q.add(v);
                        inQueue[v] = true;
                    }
                }
            }
        }
        return d;
    }


    /**
     * Methode retournant la matrice des distances d'un Graph g
     * @param g
     * @return
     */
    public static int[][] matriceDistance(Graph g){
        int [][] matrice = new int[g.V()+1][g.V()+1];
        for (int i =1;i<=g.V();i++){
            matrice[i]=calculDistance(g,i, g.V());
        }
        return matrice;
    }

    /**
     * Affichage console de la matrice des distances
     * @param ma
     */
    public static void affiche_MatriceDistance(int [][]ma){
        for (int i=1;i<ma.length;i++){
            //for sur les valeurs des distances
            for (int j=0;j<ma[i].length;j++){

                if((ma[i][j]!= Integer.MAX_VALUE)){
                    System.out.print(ma[i][j]+"\t");
                }else{
                    System.out.print(infinity +"\t");
                }

            }
            System.out.println();
        }
    }

}