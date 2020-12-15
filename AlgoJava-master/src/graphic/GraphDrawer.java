package graphic;

import operations.Graph;
import operations.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class GraphDrawer extends Canvas {
    private Graph G;
    private Dimension screenDimension;
    private double[][] listCoordonnees;
    private int ovalWidth;
    private int ovalHeight;
    private  int pasX;
    private int pasY;

    public GraphDrawer(Graph graph, Dimension screenDim){
        G=graph;
        screenDimension=screenDim;
        pasX=screenDimension.width/4;
        pasY=screenDimension.height/4;
        ovalWidth=screenDimension.width/20;
        ovalHeight=screenDimension.height/20;
        listCoordonnees=new double[G.getNodes().size()][];
    }

    public void paint(Graphics g){
        for(Node n: G.getNodes()){
            int xc=getWidth()/2;
            int yc=getHeight()/2;
            int rayon=Math.min(xc,yc)*80/100;

            double angle=n.getId()*(Math.PI/(((double) G.getNodes().size())/2.0));
            double centerX=xc+rayon*Math.cos(angle);
            double centerY=yc+rayon*Math.sin(angle);
            double [] coord={centerX,centerY};
            listCoordonnees[n.getId()]=coord;

        }
        paintEdges(g);
        paintVertex(g);
    }

    private void paintVertex(Graphics g){
        for (Node n: G.getNodes()){
            String text = n.getName();
            double centerX=listCoordonnees[n.getId()][0];
            double centerY=listCoordonnees[n.getId()][1];
            g.setColor(Color.BLUE);
            g.fillOval((int) centerX-ovalWidth/2, (int) centerY-ovalHeight/2,
                    ovalWidth, ovalHeight);

            // Draw centered text
            FontMetrics fm = g.getFontMetrics();
            double textWidth = fm.getStringBounds(text, g).getWidth();
            g.setColor(Color.WHITE);
            g.drawString(text, (int) (centerX - textWidth/2),
                    (int) (centerY + fm.getMaxAscent() / 2));
        }
    }

    private void paintEdges(Graphics g){
        ArrayList<String> alreadyPrinted=new ArrayList<>();
        g.setColor(Color.black);
        for (Node n: G.getNodes()){
            for (Map.Entry<Node, Integer> adjacencyPair:
                    n.getAdjacentNodes().entrySet()){
                double centerX=(listCoordonnees[n.getId()][0]+listCoordonnees[adjacencyPair.getKey().getId()][0])/2.;
                double centerY=(listCoordonnees[n.getId()][1]+listCoordonnees[adjacencyPair.getKey().getId()][1])/2.;

                double distance=adjacencyPair.getValue();
                FontMetrics fm = g.getFontMetrics();
                double textWidth = fm.getStringBounds(String.valueOf(distance), g).getWidth();

                if (checkBidirectionalEdges(n, adjacencyPair.getKey()) && !isAlreadyPrinted(n, adjacencyPair.getKey(), alreadyPrinted)){ //si l'arête est bidirectionnelle (A->B) et (B->A) et que la distance n'a pas encore été affichée
                    g.setColor(Color.red);// on affiche l'arête bidirect en rouge
                    g.drawLine((int) listCoordonnees[n.getId()][0], (int) listCoordonnees[n.getId()][1], (int) listCoordonnees[adjacencyPair.getKey().getId()][0], (int) listCoordonnees[adjacencyPair.getKey().getId()][1]);
                    if (!isAlreadyPrinted(n,adjacencyPair.getKey(),alreadyPrinted)){
                        alreadyPrinted.add(n.getId()+","+adjacencyPair.getKey().getId());
                        alreadyPrinted.add(adjacencyPair.getKey().getId()+","+n.getId());

                        g.drawString(String.valueOf(distance)+" ("+n.getName()+"->"+adjacencyPair.getKey().getName()+")", (int) ((centerX+10) - textWidth/2),
                                (int) ((centerY+10) + fm.getMaxAscent() / 2));

                        for (Map.Entry<Node, Integer> adjacencyPair2: //on cherche la valeur de la distance de l'arête dite "opposée"
                                adjacencyPair.getKey().getAdjacentNodes().entrySet()){
                            if (adjacencyPair2.getKey().getId()==n.getId()){
                                double distance2=adjacencyPair2.getValue();
                                g.drawString(String.valueOf(distance2)+" ("+adjacencyPair.getKey().getName()+"->"+n.getName()+")", (int) ((centerX-10) - textWidth/2), // on affiche la seconde distance en décalée par rapport à la première pour qu'elle ne se superpose pas
                                        (int) ((centerY-10) + fm.getMaxAscent() / 2));
                                break;
                            }
                        }
                    }
                    g.setColor(Color.black);
                }
                else if (!isAlreadyPrinted(n, adjacencyPair.getKey(), alreadyPrinted)){//affichage des arêtes normales
                    g.drawLine((int) listCoordonnees[n.getId()][0], (int) listCoordonnees[n.getId()][1], (int) listCoordonnees[adjacencyPair.getKey().getId()][0], (int) listCoordonnees[adjacencyPair.getKey().getId()][1]);

                    alreadyPrinted.add(n.getId()+","+adjacencyPair.getKey().getId());
                    g.drawString(String.valueOf(distance)+" ("+n.getName()+"->"+adjacencyPair.getKey().getName()+")", (int) ((centerX-10) - textWidth/2),
                            (int) ((centerY-10) + fm.getMaxAscent() / 2));
                }
            }
        }
    }

    private boolean checkBidirectionalEdges(Node n1, Node n2){
        for (Map.Entry<Node, Integer> adjacencyPair:
                n2.getAdjacentNodes().entrySet()){
            if (adjacencyPair.getKey().getId()==n1.getId()){
                return true;
            }
        }
        return false;
    }

    private boolean isAlreadyPrinted(Node n1, Node n2, ArrayList<String> alreadyPrinted){
        return alreadyPrinted.contains(n1.getId()+","+n2.getId());
    }
}
