package graphic;
import exceptions.DisplayModeException;
import exceptions.GraphPatternException;
import operations.Application;
import operations.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Launcher extends JFrame {

    public static String textAreaGrapheString;

    public Launcher(String title, Dimension d){
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(d);
        setLayout(new GridLayout(2,1));

        JButton screenModeButton=new JButton("Mode écran");
        screenModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                InitialisationGraphe initialisationGraphe = new InitialisationGraphe(title, d);
                initialisationGraphe.setVisible(true);
                setVisible(false);
                dispose();
            }
        });

        JButton consoleModeButton = new JButton("Mode console");
        consoleModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                dispose();
                runApplication("console");
            }
        });

        JButton fileModeButton = new JButton("Mode UploadFichier");
        fileModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser dialogue = new JFileChooser(new File("."));
                File fichier;

                if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    fichier = dialogue.getSelectedFile();

                    Scanner myReader = null;
                    try {
                        myReader = new Scanner(fichier);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String graphFromFileString = myReader.nextLine();
                    JOptionPane message = new JOptionPane();
                    try {
                        textAreaGrapheString = graphFromFileString;
                        Graph g = buildGraph(graphFromFileString);
                        JOptionPane.showMessageDialog(message, "Votre graphe est valide !", "Bien joué", JOptionPane.INFORMATION_MESSAGE);
                        ChooseOperation chooseOperationFrame = new ChooseOperation(title, d, g);
                        chooseOperationFrame.setVisible(true);
                        setVisible(false);
                    } catch (GraphPatternException e){
                        JOptionPane.showMessageDialog(message, e.getMessage() + "Vous avez mal saisie le graphe dans le fichier", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        myReader.close();
                    }

                }
            }
        });

        JPanel textContainer=new JPanel();
        textContainer.add(new JLabel("Choisir un mode d'affichage"));

        JPanel buttonsContainer=new JPanel();
        buttonsContainer.add(consoleModeButton);
        buttonsContainer.add(screenModeButton);
        buttonsContainer.add(fileModeButton);

        getContentPane().add(textContainer);
        getContentPane().add(buttonsContainer);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void runApplication(String mode){
        Application app=new Application(mode);
        if (app.getGraph()!=null){
            app.run();
        }
    }

    private Graph buildGraph(String text) throws GraphPatternException {
        String grapheTemporaire = text;
        Graph g=new Graph();
        g.createGraphFromString(grapheTemporaire, false);
        return g;
    }
}
