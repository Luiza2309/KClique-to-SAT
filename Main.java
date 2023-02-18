import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    int n, k;

    public static void main(String[] args) {
        Main m = new Main();

        try {
            FileWriter out = new FileWriter(args[1]);
            File arg = new File(args[0]);
            Scanner s = new Scanner(arg);
            String[] t = (s.nextLine()).split(" ");
            m.n = Integer.parseInt(t[0]);
            m.k = Integer.parseInt(t[1]);
            Graph g = new Graph(m.n);
            g.readGraph(s);

            String sat = m.checkIfVertexExists(m.k, m.n) + " & " + m.checkIfVertexIsUnique(m.k, m.n) + " & " + m.checkIfAllEdgesExist(m.k, m.n, g);

            String[] temp1 = sat.split(" & ");  // clauses
            StringBuilder ret = new StringBuilder("p cnf " + m.k * m.n + " " + temp1.length + "\n");
            for(String str1 : temp1) {
                str1 = str1.replace("(", "");
                str1 = str1.replace(")", "");
                String[] temp2 = str1.split(" V ");  // literals
                for(String str2 : temp2) {
                    String[] str3 = str2.split(",");  // indexes
                    if (str3[0].charAt(0) == '-') {
                        str3[0] = str3[0].replace("-x", "");
                        int reti = Integer.parseInt(str3[0]) * m.n + Integer.parseInt(str3[1]);
                        ret.append("-").append(reti).append(" ");
                    } else {
                        str3[0] = str3[0].replace("x", "");
                        int reti = Integer.parseInt(str3[0]) * m.n + Integer.parseInt(str3[1]);
                        ret.append(reti).append(" ");
                    }
                }
                ret.append("0\n");
            }

            out.write(ret.toString());
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String checkIfVertexExists(int k, int n) {
        StringBuilder clause = new StringBuilder();
        for(int i = 0; i < k; i++) {
            clause.append("(");
            for(int v = 0; v < n; v++) {
                clause.append("x").append(i).append(",").append(v + 1).append(" V ");
            }

            clause = new StringBuilder(clause.toString().substring(0, clause.length() - 3));  // fara " V "
            clause.append(") & ");
        }
        return clause.toString().substring(0, clause.length() - 3);
    }

    public String checkIfVertexIsUnique(int k, int n) {
        StringBuilder clause = new StringBuilder();
        for(int v = 0; v < n; v++) {
            for(int i = 0; i < k - 1; i ++) {
                for(int j = i + 1; j < k; j++) {
                    clause.append("(-x").append(i).append(",").append(v + 1).append(" V ").append("-x").append(j).append(",").append(v + 1).append(") & ");
                }
            }
        }
        return clause.toString().substring(0, clause.length() - 3); 
    }

    public String checkIfAllEdgesExist(int k, int n, Graph g) {
        StringBuilder clause = new StringBuilder();
        for(int u = 0; u < n - 1; u++) {
            for (int v = u + 1; v < n; v++) {
                if (g.matrix[u][v] == 0) {
                    for (int i = 0; i < k - 1; i++) {
                        for (int j = i + 1; j < k; j++) {
                            clause.append("(-x").append(i).append(",").append(u + 1).append(" V ").append("-x").append(j).append(",").append(v + 1).append(") & ");
                            clause.append("(-x").append(j).append(",").append(u + 1).append(" V ").append("-x").append(i).append(",").append(v + 1).append(") & ");
                        }
                    }
                }
            }
        }

        return clause.toString().substring(0, clause.length() - 3);  // fara " & "
    }
}