package operations;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Dijkstra {

    public static Graph calculateShortestPath(Graph graph, Node source){
        source.setDistance(0);
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unSettledNodes = new HashSet<>();

        unSettledNodes.add(source);

        while (unSettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unSettledNodes);
            unSettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair:
            currentNode.getAdjacentNodes().entrySet()){
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)){
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unSettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }
    private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
    private static void calculateMinimumDistance(Node evaluationNode,
                                                 Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    //afficher la distance de chaque point à partir du sommet d'entrée
    public static void getDistanceFromEachPoint(Graph g){
        for (Node n :
                g.getNodes()) {
            System.out.println(n.getName() + " : " + n.getDistance());
            System.out.println("---->");
            for (Node nodes:n.getShortestPath()){
                System.out.println(nodes.getName());
                System.out.println("----");
            }
            System.out.println("!!!!!");
        }
    }
}
