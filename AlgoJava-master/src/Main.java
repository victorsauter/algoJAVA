    import exceptions.GraphPatternException;
import graphic.GraphDrawer;
import graphic.Launcher;
import operations.Application;
import operations.Graph;
import operations.Node;
import operations.PruferCode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.*;
import java.util.List;

public class Main {
    public final static String EXEMPLE_COURS="[(1,2,1),(1,3,2),(2,4,1),(3,5,4),(3,8,3),(4,2,1),(4,6,5),(5,3,3),(5,7,2),(6,1,1),(6,8,3),(7,8,4),(8,7,7)]";

    public static void main(String[] args) throws GraphPatternException {
        Launcher launcher=new Launcher("Application Graphe", new Dimension(800,600));


        // --------------------------------  TEST PRÜFER CODE --------------------------------------------

        /* DECODAGE
        int[] pruferCode = {1,1,3,5,5};
        Graph graph = PruferCode.pruferCode_to_Graph(pruferCode);
        System.out.println("\n"+graph.toString());
        */
/*
        Graph graph = new Graph();
        Node n1 = new Node("1");
        Node n2 = new Node("2");
        Node n3 = new Node("3");
        Node n4 = new Node("4");
        Node n5 = new Node("5");
        Node n6 = new Node("6");
        Node n7 = new Node("7");

        n1.addDestination(n2, 1);
        n1.addDestination(n4, 1);
        n1.addDestination(n3, 1);

        n2.addDestination(n1, 1);

        n4.addDestination(n1, 1);

        n3.addDestination(n1, 1);
        n3.addDestination(n5, 1);

        n5.addDestination(n3, 1);
        n5.addDestination(n6, 1);
        n5.addDestination(n7, 1);

        n7.addDestination(n5, 1);

        n6.addDestination(n5, 1);

        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.addNode(n5);
        graph.addNode(n6);
        graph.addNode(n7);

        int[] pruferCode = PruferCode.graph_to_pruferCode(graph);
        System.out.print("[");
        for(int i : pruferCode){
            System.out.print(i+",");
        }
        System.out.print("]");
*/
        //------------------------------------ FIN TEST -------------------------------------------

//        operations.Graph g=l.getGraph();
//        if (g!=null){
//            for (operations.Node n: g.getNodes()){
//                System.out.println(n.getName());
//                for (Map.Entry<operations.Node, Integer> adjacencyPair:
//                        n.getAdjacentNodes().entrySet()){
//                    System.out.println("-----> "+adjacencyPair.getKey().getName()+" | "+ (int) adjacencyPair.getValue());
//                }
//            }
//        }
//        operations.Node nodeA = new operations.Node("1");
//        operations.Node nodeB = new operations.Node("2");
//        operations.Node nodeC = new operations.Node("3");
//        operations.Node nodeD = new operations.Node("4");
//        operations.Node nodeE = new operations.Node("5");
//        operations.Node nodeF = new operations.Node("6");
//        operations.Node nodeG = new operations.Node("7");
//        operations.Node nodeH = new operations.Node("8");
//          [(1,2,1),(1,3,2),(2,4,1),(3,5,4),(3,8,3),(4,2,1),(4,6,5),(5,3,3),(5,7,2),(6,1,1),(6,8,3),(7,8,4),(8,7,7)]
//        nodeA.addDestination(nodeB, 2);
//        nodeA.addDestination(nodeC, 2);
//
//        nodeB.addDestination(nodeD, 2);
//
//        nodeC.addDestination(nodeE, 2);
//        nodeC.addDestination(nodeH, 2);
//
//        nodeD.addDestination(nodeF, 2);
//        nodeD.addDestination(nodeB, 2);
//
//        nodeE.addDestination(nodeG, 2);
//        nodeE.addDestination(nodeC, 2);
//
//        nodeF.addDestination(nodeA, 2);
//        nodeF.addDestination(nodeH, 2);
//
//        nodeG.addDestination(nodeH, 2);
//
//        nodeH.addDestination(nodeG, 2);
//        operations.Node nodeB = new operations.Node("B");
//        operations.Node nodeC = new operations.Node("C");
//        operations.Node nodeD = new operations.Node("D");
//        operations.Node nodeE = new operations.Node("E");
//        operations.Node nodeF = new operations.Node("F");
//
//        nodeA.addDestination(nodeB, 10);
//        nodeA.addDestination(nodeC, 15);
//
//        nodeB.addDestination(nodeD, 12);
//        nodeB.addDestination(nodeF, 15);
//
//        nodeC.addDestination(nodeE, 10);
//
//        nodeD.addDestination(nodeE, 2);
//        nodeD.addDestination(nodeF, 1);
//
//        nodeF.addDestination(nodeE, 5);
//
//        operations.Graph graph = new operations.Graph();
//        graph.addNode(nodeA);
//        graph.addNode(nodeB);
//        graph.addNode(nodeC);
//        graph.addNode(nodeD);
//        graph.addNode(nodeE);
//        graph.addNode(nodeF);
//        graph.addNode(nodeG);
//        graph.addNode(nodeH);
////        System.out.println(graph);
//
//        operations.Dijkstra.getDistanceFromEachPoint(operations.Dijkstra.calculateShortestPath(graph, nodeA));
//
////        System.out.println(graph);
//        operations.Tarjan g=new operations.Tarjan();
//        g.printComposantesFortementConnexes(graph);
//        g.printComposantesFortementConnexes(graph);
    }
}
