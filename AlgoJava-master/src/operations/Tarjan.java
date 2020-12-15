package operations;

import java.util.*;

public class Tarjan
{
    /** nombre de sommets **/
    private int V;

    /** pré-comptage des sommets **/
    private int precomptage;

    /** plus petit sommet **/
    private int[] low;

    /** tableau des sommets visités **/
    private boolean[] visites;

    private List<Integer>[] graph;

    private List<List<Integer>> composantesFortementConnexes;

    /** la pile **/
    private Stack<Integer> pile;

    /**
     * Retourne le tableau des composantes fortement connexes {@code composantesFortementConnexes}
     * @param graph le graphe
     * @return composantesFortementConnexes
     */
    public List<List<Integer>> getComposantesFortementConnexes(List<Integer>[] graph)
    {
        V = graph.length;
        this.graph = graph;
        low = new int[V];
        visites = new boolean[V];
        pile = new Stack<Integer>();
        composantesFortementConnexes = new ArrayList<>();
        for (int v = 0; v < V; v++)
            if (!visites[v])
                rechercheEnProfondeur(v);
        return composantesFortementConnexes;
    }

    /**
     * Permet de parcourir le graphe, de visiter tous ses sommets et de calculer les composantes fortement connexes
     * @param v le sommet à traiter
     */
    private void rechercheEnProfondeur(int v)
    {
        low[v] = precomptage++;
        visites[v] = true;
        pile.push(v);
        int min = low[v];

        for (int w : graph[v])
        {
            if (!visites[w])
                rechercheEnProfondeur(w);
            if (low[w] < min)
                min = low[w];
        }

        if (min < low[v])
        {
            low[v] = min;
            return;
        }
        List<Integer> composante = new ArrayList<Integer>();
        int w;
        do
        {
            w = pile.pop();
            composante.add(w);
            low[w] = V;
        } while (w != v);
        composantesFortementConnexes.add(composante);
    }

    /**
     * Affiche les composantes fortement connexes sous la forme : [(p1,p2),(p3,p4,p5)]
     * @param G le graphe
     */
    public String printComposantesFortementConnexes(Graph G){
        List<List<Integer>> liste=getComposantesFortementConnexes(G.graphToList());
        StringBuilder resultat=new StringBuilder();
        resultat.append("[");
        for (int i=0; i< liste.size(); i++){
            resultat.append("(");
            for(int j=0; j<liste.get(i).size(); j++){
                if (j<liste.get(i).size()-1){
                    resultat.append(G.getNodeFromId(liste.get(i).get(j)).getName());
                    resultat.append(",");
                }
                else resultat.append(G.getNodeFromId(liste.get(i).get(j)).getName());
            }
            if (i<liste.size()-1){
                resultat.append("),");
            }
            else resultat.append(")");
        }
        resultat.append("]");
        System.out.print("Composantes fortement connexes : ");
        System.out.println(resultat.toString());
        return resultat.toString();
    }
}