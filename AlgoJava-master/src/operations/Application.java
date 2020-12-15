package operations;

import exceptions.DisplayModeException;
import exceptions.GraphPatternException;

import java.util.Scanner;

public class Application {
    private String modeAffichage;
    private Graph G;
    private Scanner sc;
    private String grapheTemporaire;

    public Application(String mode){
        sc=new Scanner(System.in);
        modeAffichage=mode;
        System.out.println("Bienvenue dans cette application.");
        System.out.println("_________________________________");
        System.out.println();
        try{
            buildGraph();
        }catch (GraphPatternException e) {
            System.out.println(e);
        }
    }

    private void buildGraph() throws GraphPatternException {
        if (modeAffichage.equals("console")){
            System.out.print("Quel graphe désirez-vous ? (ex: [(source, destination, distance),(source, destination, distance)] ) \n");
            grapheTemporaire=sc.nextLine();
            System.out.println("__________________________________________________________________________________________________");
            System.out.println();
            G=new Graph();
            G.createGraphFromString(grapheTemporaire, false);
        }
    }

    public void run()  {
        if (modeAffichage.equals("console")){
            String commandsInstructions="Que voulez-vous faire ? (ex : tarjan/dijkstra/rang/distances/prufer/ordonnancement ou help)";
            System.out.println(commandsInstructions);
            //System.out.println("_____________________________________________________");
            String command="";
            while (true){
                command=sc.nextLine();
                switch (command){
                    case "tarjan":
                        Tarjan tarj=new Tarjan();
                        tarj.printComposantesFortementConnexes(G);
                        break;

                    case "dijkstra":
                        System.out.println("A partir de quel point d'entrée ?");
                        String firstPoint = sc.nextLine();
                        Node n = G.getNodeFromName(firstPoint);
                        if (n != null){
                            Dijkstra.getDistanceFromEachPoint(Dijkstra.calculateShortestPath(G, n));
                        }else
                            System.out.println("Le point choisi n'existe pas !");
                        break;

                    case "distance":
                        int [][] distances = Distance.matriceDistance(G);
                        Distance.affiche_MatriceDistance(distances);
                        break;

                    case "rang":
                        int []rang=Rang.rang(G);
                        Rang.afficheRang(rang);
                        break;
                    case "ordonnancement":
                        Ordonnancement t = new Ordonnancement();
                        t.ordonnacementConsole(sc);
                        break;
                    case "prufer":
                        System.out.println("Graph vers codage de Prufer (yes/no) :");
                        String mode = sc.nextLine();
                        int[] pruferCode;
                        boolean choixMode = true;
                        while(choixMode){
                            switch (mode){
                                case "yes":
                                    Graph g2 = new Graph();
                                    try {
                                        g2.createGraphFromString(grapheTemporaire, true);
                                        pruferCode = PruferCode.pruferCode_from(g2);
                                        System.out.print(PruferCode.toString(pruferCode));
                                    } catch (Exception e) {
                                        System.out.println("Votre graphe n'a pas de feuille");
                                    }
                                    choixMode = false;
                                    break;
                                case "no":
                                    System.out.println("Rentrer le codage de prufer     ex : 5,3,5,2 ");
                                    String pruferString = sc.nextLine();
                                    pruferCode = PruferCode.getPruferCodeFrom(pruferString);
                                    Graph graph = PruferCode.graph_from(pruferCode);
                                    System.out.println(graph.toString());
                                    choixMode = false;
                                    break;
                                default:
                                    System.out.println("Veuillez saisir yes or no : ");
                                    mode = sc.nextLine();
                            }
                        }
                        break;

                    case "help":
                        printHelp();
                        break;

                    case "exit":
                        return;

                    default:
                        System.out.println("Utilisez la commande \"help\" pour visualiser la liste des commandes ");
                        break;
                }
                System.out.println();
                System.out.println(commandsInstructions);
                System.out.println("_____________________________________________________");
            }
        }
    }

    public String getModeAffichage() {
        return modeAffichage;
    }

    private void printHelp(){
        StringBuilder stringCommandHelp=new StringBuilder();
        stringCommandHelp.append("Liste des commandes :\n");
        stringCommandHelp.append("  tarjan\n");
        stringCommandHelp.append("  dijkstra\n");
        stringCommandHelp.append("  prufer\n");
        stringCommandHelp.append("  distance\n");
        stringCommandHelp.append("  rang\n");
        stringCommandHelp.append("  ordonnancement\n");
        stringCommandHelp.append("  exit\n");
        System.out.println(stringCommandHelp.toString());
    }

    public void setModeAffichage(String modeAffichage) throws DisplayModeException {
        if (modeAffichage.equals("console") || modeAffichage.equals("écran") || modeAffichage.equals("ecran")){
            this.modeAffichage = modeAffichage;
        }
        else throw new DisplayModeException();
    }

    public Graph getGraph() {
        return G;
    }

    public void setGraph(Graph g) {
        G = g;
    }

    public Scanner getScanner() {
        return sc;
    }

    public void setScanner(Scanner sc) {
        this.sc = sc;
    }
}
