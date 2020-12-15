package graphic;

import exceptions.GraphPatternException;
import operations.Graph;
import operations.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChooseOperation extends JFrame {
    private Scanner sc;
    private final String choices[] = {"Tarjan", "Dijkstra", "Rang", "Distance", "Prufer", "Ordonnancement"};
    private JComboBox choiceList;
    private JButton buttonSubmit, buttonPrevious;
    private Graph graph;

    public ChooseOperation(String title, Dimension d, Graph graph) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(d);
        setLayout(new GridLayout(3, 1));
        sc = new Scanner(System.in);
        this.graph =graph;

        choiceList = new JComboBox(choices);

        JPanel textContainer = new JPanel();
        textContainer.add(new JLabel("<html>Quelle opération réaliser ?</html>", SwingConstants.CENTER));

        buttonSubmit = new JButton("Valider ");
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<String> options = new ArrayList<String>();
                if (choiceList.getSelectedItem().toString().equals("Dijkstra")){
                    String name = JOptionPane.showInputDialog(new JOptionPane(), "Quel est le noeud de départ ?","1");
                    if (name == null || name.equals(""))
                        return;
                    options.add(name);
                }
                ShowResult showResultFrame = null;
                try {
                    showResultFrame = new ShowResult(title, d, graph ,choiceList.getSelectedItem().toString(), options );
                } catch (GraphPatternException e) {
                    e.printStackTrace();
                }
                showResultFrame.setVisible(true);
                setVisible(false);

            }
        });

        buttonPrevious = new JButton("Précédent");
        buttonPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                InitialisationGraphe initialisationGraphe = new InitialisationGraphe(title, d);
                initialisationGraphe.setVisible(true);
                Node.cmptNodes = 0;
                setVisible(false);
            }
        });

        ButtonGraphShow buttonGraphShow = new ButtonGraphShow(graph);

        Box buttonsContainer = Box.createHorizontalBox();
        buttonsContainer.add(buttonPrevious);
        buttonsContainer.add(buttonGraphShow);
        buttonsContainer.add(buttonSubmit);


        getContentPane().add(textContainer);
        getContentPane().add(choiceList);
        getContentPane().add(buttonsContainer);

        pack();
        setLocationRelativeTo(null);
        setVisible(false);
    }
}
