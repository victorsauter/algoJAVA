package graphic;

import exceptions.GraphPatternException;
import operations.Graph;
import operations.Node;
import operations.PruferCode;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class InitialisationGraphe extends JFrame {
    private JTextArea textAreaGraph;
    private Graph G;
    private Scanner sc;
    private JButton buttonSubmit;
    private JButton buttonPrevious;
    private JComboBox choiceList;
    private final String choices[] = {"Graphe", "Code Prufer"};
    public static String textAreaGrapheString;


    public InitialisationGraphe(String title, Dimension d){
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(d);
        setLayout(new GridLayout(4,1));
        sc=new Scanner(System.in);

        choiceList = new JComboBox(choices);
        choiceList.setSize(new Dimension(50,50));

        JPanel choiceListContainer = new JPanel();
        choiceListContainer.add(choiceList);

        buttonPrevious = new JButton("Retour");
        buttonPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Launcher launcher = new Launcher(title, d);
                launcher.setVisible(true);
                Node.cmptNodes = 0;
                setVisible(false);
            }
        });

        buttonSubmit = new JButton("Vérification & validation");
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane message = new JOptionPane();
                if (choiceList.getSelectedItem().toString().equals("Graphe")) {
                    try {
                        buildGraph();
                        int answer = JOptionPane.showConfirmDialog(message, "Votre graphe est valide! Voulez vous le sauvegarder dans " +
                                "un fichier ?", "Bien joué", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (answer != 2) {
                            if (answer == 0){
                                //print dans le fichier
                                saveGraphToFile(textAreaGraph.getText());
                            }
                            textAreaGrapheString = textAreaGraph.getText();
                            ChooseOperation chooseOperationFrame = new ChooseOperation(title, d, G);
                            chooseOperationFrame.setVisible(true);
                            setVisible(false);
                        }
                    } catch (GraphPatternException e){
                        //traitement des erreurs de saisie dans le texte area !
                        JOptionPane.showMessageDialog(message, e.getMessage(), "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    String pruferString = textAreaGraph.getText();
                    int []pruferCode = PruferCode.getPruferCodeFrom(pruferString);
                    Graph g = PruferCode.graph_from(pruferCode);

                    //Création de la fenetre affichage
                    JFrame w = new JFrame();
                    w.setTitle("Graphe");
                    Dimension d = new Dimension(600, 600);
                    w.setPreferredSize(d);
                    w.add(new GraphDrawer(g, d));
                    w.pack();
                    w.setVisible(true);
                    setVisible(false);
                }
            }

            private void saveGraphToFile(String text) {
                JOptionPane message = new JOptionPane();
                try {
                    File file = new File("SavedGraphe.txt");
                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getName());
                        JOptionPane.showMessageDialog(message, "Le fichier n'existe pas encore alors " +
                                "il sera crée à la racine du projet et contiendra le graphe rentré précédament.",
                                "Création de fichier", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        System.out.println("File already exists.");
                        JOptionPane.showMessageDialog(message, "Un fichier contenant un graphe existe déjà. " +
                                "Il sera alors écrasé et remplacé par le nouveau graphe", "Ecraser le fichier", JOptionPane.INFORMATION_MESSAGE);
                    }
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(text);
                    JOptionPane.showMessageDialog(message, "Sauvegarde réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        });

        textAreaGraph = new JTextArea("[" +
                "(1,2,1),(1,3,2),(2,4,1)," +
                "(3,5,4),(3,8,3),(4,2,1)," +
                "(4,6,5),(5,3,3),(5,7,2)," +
                "(6,1,1),(6,8,3),(7,8,4)," +
                "(8,7,7)]"
        );
        textAreaGraph.setLineWrap(true);
        textAreaGraph.setWrapStyleWord(true);
        textAreaGraph.setBorder(new LineBorder(Color.BLACK));
        JScrollPane areaScrollPane = new JScrollPane(textAreaGraph);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 250));
        areaScrollPane.getViewport().setBackground(Color.BLUE);
        areaScrollPane.getViewport().add(textAreaGraph);


        JPanel textContainer=new JPanel();
        textContainer.add(new JLabel("<html>Quel graphe désirez-vous ? <br> ex: [(source,destination, distance),(source,destination,distance)]</html>", SwingConstants.CENTER));

        Box buttonsContainer = Box.createHorizontalBox();
        buttonsContainer.add(buttonPrevious);
        buttonsContainer.add(buttonSubmit);

        getContentPane().add(textContainer);
        getContentPane().add(choiceListContainer);
        getContentPane().add(areaScrollPane);
        getContentPane().add(buttonsContainer);

        pack();
        setLocationRelativeTo(null);
        setVisible(false);
    }

    private void buildGraph() throws GraphPatternException {
        String grapheTemporaire = textAreaGraph.getText();
        G=new Graph();
        G.createGraphFromString(grapheTemporaire, false);
    }
}
