package graphic;

import exceptions.GraphPatternException;
import operations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ShowResult extends JFrame {
    private final Graph graph;
    private String algoChoosen;
    private List<String> options;
    private JPanel resultContainer;
    private JButton buttonPrevious;

    public ShowResult(String title, Dimension d, Graph graph, String algo, List<String> options) throws GraphPatternException {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(d);
        setLayout(new GridLayout(3, 1));
        algoChoosen = algo;
        this.options = options;
        this.graph = graph;

        buttonPrevious = new JButton("Précédent");
        buttonPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ChooseOperation chooseOperation = new ChooseOperation(title, d, graph);
                setVisible(false);
                chooseOperation.setVisible(true);
            }
        });

        ButtonGraphShow buttonGraphShow = new ButtonGraphShow(graph);

        Box buttonsContainer = Box.createHorizontalBox();
        buttonsContainer.add(buttonPrevious);
        buttonsContainer.add(buttonGraphShow);

        JPanel textContainer = new JPanel();
        textContainer.add(createLabel());
        getContentPane().add(textContainer);

        resultContainer = bindResultContainer();
        getContentPane().add(resultContainer);
        getContentPane().add(buttonsContainer);

        pack();
        setLocationRelativeTo(null);
        setVisible(false);
    }

    private JPanel bindResultContainer() throws GraphPatternException {
        JPanel panel = new JPanel();
        if (algoChoosen.equals("Dijkstra")) {
            Dijkstra.calculateShortestPath(graph, graph.getNodeFromName(options.get(0)));
            createResultDijsktra(panel);
        } else if (algoChoosen.equals("Tarjan")) {
            Tarjan tarj=new Tarjan();
            List<List<Integer>> composantes = tarj.getComposantesFortementConnexes(graph.graphToList());
            createResultTarjan(panel, composantes);
        } else if (algoChoosen.equals("Rang")) {
            int []rang= Rang.rang(graph);
            createResultRang(panel, rang);
        } else if (algoChoosen.equals("Distance")) {
            int [][] distances = Distance.matriceDistance(graph);
            createResultDistance(panel, distances);
        } else if (algoChoosen.equals("Prufer")) {
            try{
                Graph g2 = new Graph();
                g2.createGraphFromString(InitialisationGraphe.textAreaGrapheString != null ? InitialisationGraphe.textAreaGrapheString : Launcher.textAreaGrapheString , true);
                int[] pruferCode = PruferCode.pruferCode_from(g2);
                createResultPrufer(panel, pruferCode);
            } catch (Exception e) {
                JOptionPane mess = new JOptionPane();
                JOptionPane.showMessageDialog(mess, "Votre graphe n'a pas de feuilles ", "Erreur saisie graphe", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (algoChoosen.equals("Ordonnancement")){
            try {
                Node[] nodes = Ordonnancement.criticalPath(graph.getNodes());
                createResultOrd(panel, nodes);
            } catch (Exception e ) {
                JOptionPane mess = new JOptionPane();
                JOptionPane.showMessageDialog(mess, "Votre graphe est cyclique impossible de faire ordonnancement",
                        "Erreur saisie graphe", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        return panel;
    }

    private void createResultOrd(JPanel panel, Node[] nodes) {
        JLabel label = new JLabel();
        label.setText("Max Cost: " + Ordonnancement.maxCost);
        JLabel label1 = new JLabel();
        StringBuilder str = new StringBuilder();
        for(Node n: nodes) {
            str.append(n.getName()+", ");
        }
        label.setText(str.toString());
        panel.add(label, label1);
    }

    private void createResultPrufer(JPanel panel, int[] pruferCode) {
        JLabel label = new JLabel();
        label.setText(PruferCode.toString(pruferCode));
        panel.add(label);
    }

    private void createResultDistance(JPanel panel, int[][] distances) {
        Vector<String> titres = new Vector<String>();
        for (int i = 1; i<distances.length; i++){
            titres.add(""+i);
        }

        Vector<Vector<String>> data = new Vector<Vector<String>>();
        for (int i = 1; i <distances.length ; i++) {
            Vector<String> row = new Vector<String>();
            for (int j=0;j<distances[i].length;j++){
                if((distances[i][j]!= Integer.MAX_VALUE)){
                    row.add(distances[i][j]+"");
                }else{
                    row.add(Distance.infinity+"");
                }
            }
            data.add(row);
        }
        JTable tableDistance = new JTable(data, titres);
        panel.add(new JScrollPane(tableDistance));
    }

    private void createResultRang(JPanel panel, int[] rang) {
        Vector<String> titres = new Vector<String>();
        titres.add("Nom");
        titres.add("Rang");

        Vector<Vector<String>> data = new Vector<Vector<String>>();
        for (int i = 1; i <rang.length ; i++) {
            Vector<String> row = new Vector<String>();
            row.add(""+i);
            row.add(""+rang[i]);
            data.add(row);
        }
        JTable tableRang = new JTable(data, titres);
        panel.add(new JScrollPane(tableRang));
    }

    private void createResultTarjan(JPanel panel, List<List<Integer>> composantes) {
        Vector<String> titres = new Vector<String>();
        titres.add("Nom");
        titres.add("Points");

        Vector<Vector<String>> data = new Vector<Vector<String>>();
        for (int i = 0; i < composantes.size(); i++) {
            Vector<String> row = new Vector<String>();
            row.add("Composantes num " + (i+1));
            StringBuilder res = new StringBuilder();
            for (int j = 0; j < composantes.get(i).size(); j++) {
                if (j<composantes.get(i).size()-1){
                    res.append(graph.getNodeFromId(composantes.get(i).get(j)).getName());
                    res.append(",");
                }
                else res.append(graph.getNodeFromId(composantes.get(i).get(j)).getName());
            }
            row.add(res.toString());
            data.add(row);

            JTable tableTarjan = new JTable(data, titres);
            JScrollPane tableScrollPane = new JScrollPane(tableTarjan);
            panel.add(tableScrollPane);
        }
    }

    private void createResultDijsktra(JPanel container) {
        //Les données du tableau
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        for (Iterator<Node> it = graph.getNodes().iterator(); it.hasNext(); ) {
            Vector<String> row = new Vector<String>();
            Node n = it.next();
            row.add(n.getName());
            row.add(""+n.getDistance());
            row.add(createPathString(n));
            data.add(row);
        }
        //Les titres des colonnes
        Vector<String> titres = new Vector<String>();
        titres.add("Nom");
        titres.add("Distance");
        titres.add("Chemin");


        JTable tableDijkstra = new JTable(data, titres);
        container.add(new JScrollPane(tableDijkstra));
    }

    private String createPathString(Node n) {
        StringBuilder str = new StringBuilder();
        for (Node node : n.getShortestPath()){
            str.append(node.getName() + "->");
        }
        str.append(n.getName());
        return str.toString();
    }

    private JLabel createLabel() {
        StringBuilder str = new StringBuilder();
        str.append("<html>Résultat de l'algo: <br>" + algoChoosen + " avec les options : <br>");
        for (String s : options) {
            str.append(s);
        }
        str.append("</html>");
        return new JLabel(str.toString(), SwingConstants.CENTER);
    }
}
