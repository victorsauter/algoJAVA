package graphic;

import operations.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonGraphShow extends JButton {

    public ButtonGraphShow(Graph graph) {
        super("Visualisez le graphe");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame w = new JFrame();
                w.setTitle("Graphe");
                Dimension d = new Dimension(600, 600);
                w.setPreferredSize(d);
                w.add(new GraphDrawer(graph, d));
                w.pack();
                w.setVisible(true);
            }
        });
    }
}
