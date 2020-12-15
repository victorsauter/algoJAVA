package operations;

import exceptions.GraphPatternException;

import java.util.ArrayList;

public class PruferCode {

    /**
     * Renvoie le graph correspondant au prufer code renseigné en paramètre
     *
     * @param pruferCode
     * @return Graph
     */
    public static Graph graph_from(int[] pruferCode){

        Graph graph = new Graph();
        int tab_noeuds[] = new int[pruferCode.length + 2];

        // Initialise le tableau de noeuds
        for (int i = 0; i < pruferCode.length + 2; i++)
            tab_noeuds[i] = 0;

        // Nombre d'occurence dans le pruferCode
        for (int i = 0; i < pruferCode.length; i++){
            tab_noeuds[pruferCode[i] - 1] += 1;
        }


        // Cherche la plus petite valeur non comprise dans le pruferCode
        int j = 0;
        for (int i = 0; i < pruferCode.length; i++) {
            for (j = 0; j < pruferCode.length + 2; j++) {
                // Si j+1 n'est pas présent dans le prufer code
                if (tab_noeuds[j] == 0) {
                    tab_noeuds[j] = -1;
                    
                    //Création node 
                    if(graph.getNodeFromName(j+1+"") == null) {
                        Node n = new Node((j + 1) + "");
                        graph.addNode(n);
                    }
                    if(graph.getNodeFromName(pruferCode[i]+"") == null){
                        Node n = new Node(pruferCode[i] + "");
                        graph.addNode(n);
                    }
                    graph.getNodeFromName(j+1+"").addDestination(graph.getNodeFromName(pruferCode[i]+""),1);
                    graph.getNodeFromName(pruferCode[i]+"").addDestination(graph.getNodeFromName(j+1+""),1);
                    
                    tab_noeuds[pruferCode[i] - 1]--;

                    break;
                }
            }
        }

        j = 0;
        int index = -1;
        // Pour les deux derniers noeuds
        for (int i = 0; i < pruferCode.length + 2; i++) {
            if (tab_noeuds[i] == 0 && j == 0) {
                index = i+1;
                if(graph.getNodeFromName(index+"") == null) {
                    Node n = new Node((index) + "");
                    graph.addNode(n);
                }
                j++;
            }
            else if (tab_noeuds[i] == 0 && j == 1) {
                //Création node
                if(graph.getNodeFromName(i + 1+"") == null){
                    Node n = new Node((i + 1) + "");
                    graph.addNode(n);
                }
                graph.getNodeFromName(index+"").addDestination(graph.getNodeFromName((i + 1)+""),1);
                graph.getNodeFromName((i + 1)+"").addDestination(graph.getNodeFromName(index+""),1);
            }
        }
        return graph;
    }

    /**
     * Renvoie le nom de la feuille aillant la plus petite valeur dans le graph donné en paramètre
     * Utilisation : méthode "graph_to_pruferCode"
     *
     * @param graph
     * @return nom feuille minimum
     */
    private static String feuilleMinimumDans(Graph graph) throws GraphPatternException{
        ArrayList<Node> feuilles = new ArrayList<>();
        for(Node node : graph.getNodes()){
            if(node.getAdjacentnodesList().size() <= 1){
                feuilles.add(node);
            }
        }
        int min = -1;
        try {
            min = Integer.parseInt(feuilles.get(0).getName());
            for(int i=1; i<feuilles.size(); i++){
                if(min > Integer.parseInt(feuilles.get(i).getName())){
                    min = Integer.parseInt(feuilles.get(i).getName());
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new GraphPatternException();
        }

        return String.valueOf(min);
    }

    /**
     * Renvoie le Prüfer code correspondant au graph renseigné en paramètre
     *
     * @param graph
     * @return prüferCode
     */
    public static int[] pruferCode_from(Graph graph) throws GraphPatternException {
        ArrayList<Integer> pruferCode = new ArrayList<>();

        //Algo
        while(graph.getNodes().size() > 2){
            String nodeMinimum = feuilleMinimumDans(graph);
            Node adjacentNode = graph.getNodeFromName(nodeMinimum+"").getAdjacentnodesList().get(0);
            pruferCode.add(Integer.parseInt(adjacentNode.getName()));
            graph.getNodeFromName(adjacentNode.getName()).removeDestination(graph.getNodeFromName(nodeMinimum));
            graph.removeNodeFromName(nodeMinimum);
        }

        //pruferCode ArrayList vers int[]
        int[] prufer = new int[pruferCode.size()];
        int i=0;
        for(int code : pruferCode){
            prufer[i++] = code;
        }

        return prufer;
    }

    /**
     * Renvoie le string associé au codage de prufer, pour permettre son affichage correctement
     *
     * @param pruferCode
     * @return pruferString
     */
    public static String toString(int[] pruferCode){
        StringBuilder sb = new StringBuilder();
        sb.append("\nCodage de Prufer = {");
        for(int i=0; i<pruferCode.length-1; i++){
            sb.append(pruferCode[i]+",");
        }
        sb.append(pruferCode[pruferCode.length-1]);
        sb.append("}");

        return sb.toString();
    }

    /**
     * Renvoie le tableau du codage de prufer renseigné en paramètre sous forme de String
     *
     * Exemple pruferString : "5,7,3,7,3"
     * Chaque valeur doit être séparé d'une virgule
     *
     * @param pruferString
     * @return int[] pruferCode
     */
    public static int[] getPruferCodeFrom(String pruferString){
        String[] pruferTabString = pruferString.split(",");
        int[] pruferCode = new int[pruferTabString.length];
        for(int i=0; i<pruferTabString.length; i++){
            pruferCode[i] = Integer.parseInt(pruferTabString[i]);
        }

        return pruferCode;
    }

}
