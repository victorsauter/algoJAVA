package operations;

import exceptions.GraphPatternException;

import java.util.*;

public class Graph {
    private Set<Node> nodes = new HashSet<>();
    public void addNode(Node n){
        nodes.add(n);
    }

    public void removeNodeFromName(String name){
        nodes.remove(this.getNodeFromName(name));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("operations.Graph \n");
        for (Node n : nodes) {
            stringBuilder.append(n.getName());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public int V(){
        return nodes.size();
    }

    public Map<Node, Integer>  adj(int v){
        for (Node n : nodes){
            if (n.getId()==v) return n.adjacentNodes;
        }
        return null;
    }

    public Map<Node, Integer> adj(String v){
        return adj(Integer.parseInt(v));
    }

    public Set<Node> getNodes(){
        return  nodes;
    }

    public Node getNodeFromId(int id){
        for (Node n: nodes){
            if (n.getId()==id) return n;
        }
        return null;
    }

    public Node getNodeFromName(String name){
        for (Node n: nodes){
            if (n.getName().equals(name))
                return n;
        }
        return null;
    }

    public List<Integer>[] graphToList(){
        List<Integer>[] g = new List[nodes.size()];
        for (Node n: nodes){
            g[n.getId()] = new ArrayList<Integer>();
            for (Map.Entry<Node, Integer> adj: n.getAdjacentNodes().entrySet()){
                g[n.getId()].add(adj.getKey().getId());
            }
        }
        return g;
    }

    public void createGraphFromString(String graphPattern, boolean nonOrienté) throws GraphPatternException {
        StringBuilder firstNode=new StringBuilder();
        StringBuilder secondNode= new StringBuilder();
        StringBuilder distance= new StringBuilder();

        graphPattern=graphPattern.replaceAll(" ","");

        if (graphPattern.length()==0 || graphPattern.charAt(0)!='[' || graphPattern.charAt(graphPattern.length()-1)!=']')
            throw new GraphPatternException();

        for (int i=0; i< graphPattern.length(); i++){
            if (graphPattern.charAt(i)=='('){
                int j=i+1;
                while(graphPattern.charAt(j)!=',' && graphPattern.charAt(j)!=')'){
                    firstNode.append(graphPattern.charAt(j));
                    j++;
                }
                if (graphPattern.charAt(j)==','){
                    j++;
                    while(graphPattern.charAt(j)!=',' && graphPattern.charAt(j)!=')'){
                        secondNode.append(graphPattern.charAt(j));
                        j++;
                    }
                }
                else throw new GraphPatternException();
                if (graphPattern.charAt(j)==','){
                    j++;
                    while(graphPattern.charAt(j)!=',' && graphPattern.charAt(j)!=')' && graphPattern.charAt(j)!=' '){
                        distance.append(graphPattern.charAt(j));
                        j++;
                    }
                    if (distance.toString().length()==0 || !isNumeric(distance.toString())){
                        throw new GraphPatternException();
                    }
                }
                else throw new GraphPatternException();
                if (graphPattern.charAt(j)==')'){
                    i=j;
                    boolean firstNodeAlreadyExists=false;
                    boolean secondNodeAlreadyExists=false;
                    Node newFirstNode = null;
                    Node newSecondNode= null;

                    for (Node n: getNodes()){
                        if (n.getName().equals(firstNode.toString())){
                            firstNodeAlreadyExists=true;
                            newFirstNode=n;
                        }
                        else if (n.getName().equals(secondNode.toString())){
                            secondNodeAlreadyExists=true;
                            newSecondNode=n;
                        }
                    }

                    if(!firstNodeAlreadyExists){
                        newFirstNode=new Node(firstNode.toString());
                        addNode(newFirstNode);
                    }
                    if (!secondNodeAlreadyExists){
                        newSecondNode=new Node(secondNode.toString());
                        addNode(newSecondNode);
                    }

                    newFirstNode.addDestination(newSecondNode, Integer.parseInt(distance.toString()));
                    if(nonOrienté){
                        newSecondNode.addDestination(newFirstNode, Integer.parseInt(distance.toString()));
                    }

                    firstNode=new StringBuilder();
                    secondNode=new StringBuilder();
                    distance=new StringBuilder();
                }
                else throw new GraphPatternException();
            }
        }
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
