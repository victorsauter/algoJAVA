package operations;

import java.util.*;

public class Ordonnancement {

    public static int maxCost;
    private ArrayList<Node> nodes;
    private Node start,end;
    private Map<String,Integer[]> associationNameSuccesseur;

    public  void ordonnacementConsole(Scanner s){

        nodes=new ArrayList<>();
        associationNameSuccesseur=new HashMap<>();

        boolean again = true;
        start = new Node("1");
        start.setData("Start");
        start.setDistance(0);
        while (again){
            System.out.println("__________________________________________________________________________________________________");
            System.out.println("Choississer la commande que vous souhaiter add/g pour monntrer le cout du chemin critique/ q to quit");
            String commande = s.nextLine();
            commande=commande.trim();
            switch (commande){
                case "add":
                    String name=String.valueOf(nodes.size()+2);
                    Node newNode= new Node(name);
                    System.out.println("Donnez le nom de la tâche que vous voulez ajouter");
                    String data=s.nextLine();
                    newNode.setData(data);
                    System.out.println("Quel est la durée de cette tache ?");
                    String distance = s.nextLine();
                    newNode.setDistance(Integer.parseInt(distance));
                    System.out.println("Donnez le numéro des taches succédent la tache que vous venez de rentrer (sachant que les taches commencent a 2)");
                    String tachesSuivante = s.nextLine();
                    Integer[] ant= calculTab_String(tachesSuivante);
                    associationNameSuccesseur.put(name,ant);
                    nodes.add(newNode);
                    break;
                case "g":
                    Graph g = this.creationDeGraph();
                    Node[] result=null;
                    try{
                         result = criticalPath(g.getNodes());
                         print(result);
                    }catch (Exception e){
                        System.out.println("Erreur exécution algorithme");
                    }
                    break;
                case "q":
                    again=false;
                    break;
                default:
                    System.out.println("Essayez la commande add pour ajouter une tache ou bien q pour quitter\n");
                    break;
            }
        }

    }

    private Graph creationDeGraph() {
        end = new Node(String.valueOf(nodes.size()+2));
        end.setDistance(0);
        end.setData("End");
        String nameLastNode=new String();
        boolean notFirstNodes[]= new boolean[nodes.size()+2];
        for(Map.Entry<String, Integer[]> entry : associationNameSuccesseur.entrySet()){
            addDestinationNode(getNodeWithName(entry.getKey()),entry.getValue());
            for (int i = 0; i <entry.getValue().length ; i++) {
                try {
                    notFirstNodes[entry.getValue()[i]]=true;
                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Node inexistant");
                }
            }
            if (entry.getValue().length==0){
                nameLastNode=entry.getKey();
            }

        }
        for (int i = 2; i <notFirstNodes.length ; i++) {
            if (notFirstNodes[i]==false){
                start.addDestination(getNodeWithName(String.valueOf(i)),start.getDistance());
            }
        }
        getNodeWithName(nameLastNode).addDestination(end, getNodeWithName(nameLastNode).getDistance());
        Graph g = new Graph();
        g.addNode(start);
        for (int i = 0; i <nodes.size() ; i++) {
            g.addNode(nodes.get(i));
        }
        g.addNode(end);
        return g;
    }

    private void addDestinationNode(Node newNode, Integer[] ant) {
        for (int i = 0; i <ant.length ; i++) {
            String nameDestinationNode = String.valueOf(ant[i]);
            Node destination= getNodeWithName(nameDestinationNode);
            newNode.addDestination(destination,newNode.getDistance());
        }

    }

  private Node getNodeWithName(String nameDestinationNode) {
        for (int i = 0; i <nodes.size() ; i++) {
            if (nodes.get(i).getName().equals(nameDestinationNode))return nodes.get(i);
        }
        return null;
    }

    private  Integer[] calculTab_String(String tachesAntérieur) {
        String[] tempo = tachesAntérieur.split(" ");
        Integer []val= new Integer[tempo.length];

        int compteur = 0;
        for (int i = 0; i <tempo.length ; i++) {
            try {
                val[i]=Integer.parseInt(tempo[i]);
                if (val[i]!=null){
                    compteur++;
                }
            }catch (NumberFormatException e){
            }

        }
        Integer []value= new Integer[compteur];
        compteur=0;

        for (int i = 0; i <val.length ; i++) {
            if (val[i]!=null){
                value[compteur]=val[i];
                compteur++;
            }
        }
        return value;
    }

    public static Node[] criticalPath(Set<Node> nodes) {

        // Nodes ayant les distances critiques deja calculé
        HashSet<Node> completed = new HashSet<Node>();
        // Nodes ayant les distances critiques pas encore calculé
        HashSet<Node> remaining = new HashSet<Node>(nodes);

        // tant qu'il reste des nodes a parcourir
        while (!remaining.isEmpty()) {
            boolean progress = false;

            for (Iterator<Node> it = remaining.iterator(); it.hasNext();) {
                Node task = it.next();
                if (completed.containsAll(task.getAdjacentNodes().keySet())) {
                    int critical = 0;
                    for (Map.Entry<Node, Integer> t : task.getAdjacentNodes().entrySet()) {
                        if (t.getKey().criticalCost > critical) {
                            critical = t.getKey().criticalCost;
                        }
                    }
                    task.criticalCost = critical + task.getDistance();

                    completed.add(task);
                    it.remove();
                    progress = true;
                }
            }
            //if no progress throw Exception
            if (!progress)
                throw new RuntimeException("Cyclic dependency, algorithm stopped!");
        }

        maxCost(nodes);
        HashSet<Node> initialNodes = initials(nodes);
        calculateEarly(initialNodes);

        Node[] ret = completed.toArray(new Node[0]);
        Arrays.sort(ret, new Comparator<Node>() {

            @Override
            public int compare(Node o1, Node o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return ret;
    }

    public static void calculateEarly(HashSet<Node> initials) {
        for (Node initial : initials) {
            initial.earlyStart = 0;
            initial.earlyFinish = initial.getDistance();
            setEarly(initial);
        }
    }

    public static void setEarly(Node initial) {
        int completionTime = initial.earlyFinish;
        for (Map.Entry<Node, Integer> t : initial.getAdjacentNodes().entrySet()) {
            if (completionTime >= t.getKey().earlyStart) {
                t.getKey().earlyStart = completionTime;
                t.getKey().earlyFinish = completionTime + t.getKey().getDistance();
            }
            setEarly(t.getKey());
        }
    }

    public static HashSet<Node> initials(Set<Node> tasks) {
        HashSet<Node> remaining = new HashSet<Node>(tasks);
        for (Node t : tasks) {
            for (Map.Entry<Node, Integer> td : t.getAdjacentNodes().entrySet()) {
                remaining.remove(td);
            }
        }

        System.out.print("Initial nodes: ");
        for (Node t : remaining)
            System.out.print(t.getName() + " ");
        System.out.print("\n\n");
        return remaining;
    }

    public static void maxCost(Set<Node> tasks) {
        int max = -1;
        for (Node t : tasks) {
            if (t.criticalCost > max)
                max = t.criticalCost;
        }
        maxCost = max;
        System.out.println("Chemin critique longueur : " + maxCost);
        for (Node t : tasks) {
            t.setLatest();
        }
    }

    public static void print(Node[] tasks) {
        System.out.println("Name\tData\tDistance\t Point Critique ?");
        for (Node t : tasks){
            String criticalCond = t.earlyStart == t.latestStart ? "Yes" : "No";
            System.out.println(t.getName()+"\t"+t.getData()+"\t\t"+t.getDistance()+"\t"+criticalCond);
        }

    }
}