package operations;

import java.util.*;

import static operations.Ordonnancement.maxCost;

public class Node {

    public static int cmptNodes=0;
    private int id; //id unique identifiant chaque noeuds afin de pouvoir faire un marquage des noeuds par passage

    private String name;
    //Dijkstra
    private List<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    //Tarjan
    Map <Node, Integer> adjacentNodes = new HashMap<>();
    //Prufer
    private List<Node> adjacentnodesList = new ArrayList<>();


    public Node(String name){
        this.name = name;
        id=cmptNodes;
        cmptNodes++;
        this.earlyFinish = -1;

    }

    @Override
    public String toString() {
        return "operations.Node{" +
                "name='" + name + '\'' +
//                ", shortestPath=" + shortestPath +
  //              ", distance=" + distance +
//                ", adjacentNodes=" + adjacentNodes  +
                '}';
    }

    public void addDestination (Node destination, int distance){
        adjacentNodes.put(destination, distance);
        adjacentnodesList.add(destination);
    }

    public void removeDestination(Node destination){
        adjacentnodesList.remove(destination);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public List<Node> getAdjacentnodesList() {
        return this.adjacentnodesList;
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public int getId(){
        return id;
    }

    public int getNumberOfAdjacentNodes(){return adjacentNodes.size();}

    //Ordonnancement
    //Donnee représentant le nom de la tache
    private String data;
    //La distance du Node durant le chemin critique
    public int criticalCost;

    public int earlyStart;

    public int earlyFinish;

    public int latestStart;

    public int latestFinish;

    public void setLatest() {
        latestStart = maxCost - criticalCost;
        latestFinish = latestStart + distance;
    }
    public String getData() {return data;}

    public void setData(String data) {this.data = data;}


}
