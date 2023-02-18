import java.util.Scanner;

public class Graph {
    public int n;  // dimensiune
    public int[][] matrix;  // matricea de adiacenta

    public Graph(int n) {
        this.n = n;
        this.matrix = new int[n][n];
    }

    public void readGraph(Scanner s) {
        int i = 0;
        while(s.hasNextLine()) {
            String temp = s.nextLine();
            if(!temp.isEmpty()) {
                String[] t = temp.split(" ");
                for(int j = 0; j < t.length; j++) {
                    int node = Integer.parseInt(t[j]) - 1;
                    matrix[i][node] = 1;
                    matrix[node][i] = 1;
                }
            }
            i++;
        }
    }
}
