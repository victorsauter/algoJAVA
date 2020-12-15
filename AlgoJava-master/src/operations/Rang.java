package operations;

import java.util.ArrayList;
import java.util.Map;

public class Rang {

    /**
     * Méthode retournant le rang de chaque point
     * @param g
     * @return
     */
    public static int[] rang(Graph g){
        //nombre de sommets
        int n = g.getNodes().size();
        int rangCourant=0,pas=-1,e=0,d=n+1,tempo,s;
        int[] rang = new int[n+1];
        int[] ddi= new int[n+1];
        int []fs = calculFs(g);
        int[]aps=initaps(fs, n);
        int[]pile = new int[n+1];
        rang[0]=n;
        for (int i=1;i<=n;i++){
            rang[i]=0;
            ddi[0]=0;
        }


        for (int i = 1; i <fs[0] ; i++) {
            if (fs[i]!=0){
                ddi[fs[i]]++;
            }
        }
        for (int i = 1; i <=n ; i++) {
            if (ddi[i]==0){
                pile[++e]=i;
            }
        }
        while ((e!=0)&&(e!=n+1)){
            tempo=e;
            e=d;
            d=tempo;
                while ((d!=0)&&(d!=n+1)){
                    s=pile[d];
                    d+=pas;
                    rang[s]=rangCourant;
                    for (int i = aps[s]; fs[i]>0 ; i++) {
                        ddi[fs[i]]--;
                        if (ddi[fs[i]]==0){
                            e+=pas;
                            pile[e]=fs[i];
                        }
                    }
                }
                rangCourant++;
                pas=-pas;
        }
        return rang;
    }

    /**
     * Méthode calculant aps à partir de fs et du nombre de points
     * @param fs
     * @param n
     * @return
     */
    private static int[] initaps(int[] fs,int n) {
       int[] aps=new int[n+1];
        aps[0]=n;
        aps[1]=1;
        int compt=2;
        for (int i = 2; i <fs[0] ; i++) {

            if (fs[i]==0){
                aps[compt]=i+1;
                compt++;
            }
        }
        return aps;
    }

    /**
     * Méethode permettant la calcule de Fs d'un grapge g orienté
     * @param g
     * @return
     */
    public static int[] calculFs(Graph g) {

        int nbArcs=0;
        for (Node node : g.getNodes()) {
            nbArcs+=node.getNumberOfAdjacentNodes();
        }
        int n = g.getNodes().size();
        int []fs= new int[n+nbArcs+1];
        ArrayList<Integer> q = new ArrayList<>();

        fs[0]=n+nbArcs;

        for (int u=0;u<n;u++){
            for (Map.Entry<Node, Integer> entry : g.getNodeFromName(String.valueOf(u+1)).adjacentNodes.entrySet()) {
               q.add(Integer.parseInt(entry.getKey().getName()));
            }
           q.add(0);
        }
        for (int i=1;i<q.size();i++){
            fs[i]=q.get(i-1);
        }

       return fs;
    }

    /**
     * Affichage console de la matrice contenant les rangs
     * @param rang
     */
    public static void afficheRang(int []rang){
        for (int i = 1; i <rang.length ; i++) {
            System.out.print(i+"\t");
        }
        System.out.println();
        for (int i = 1; i <rang.length ; i++) {
            System.out.print(rang[i]+"\t");
        }
    }
}
