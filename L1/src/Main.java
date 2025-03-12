import org.w3c.dom.ls.LSOutput;

import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

class Compulsory {
    public static void main(String[] args) {
        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1000000);

        n *= 3;
        n += Integer.parseInt("10101", 2);
        n += Integer.parseInt("FF", 16);
        n *= 6;

        int result = computeResult(n);
        System.out.println("Willy-nilly, this semester I will learn " + languages[result]);
    }

    public Compulsory() {
    }

    public static int computeResult(int res) {
        while (res > 9) {
            int sum = 0;
            while (res > 0) {
                sum += res % 10;
                res /= 10;
            }
            res = sum;
        }

        return res;
    }
}

class Homework {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();


        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        if (k > n / 2) {
            System.out.println("Error: k cannot be greater than n/2.");
            return;
        }

        int[][] G = createMatrix(n, k);


        int nEdges = countEdges(G, n);
        System.out.println("\nNumber of edges: " + nEdges);

        printDegrees(G, n);

        verifySum(G, n, nEdges);

        long endTime = System.currentTimeMillis();
        System.out.println("\n Running time without printing the matrix: " + (endTime - startTime) + " ms\n");

//        printMatrix(G,n);
//        System.out.println();
//        prettyPrintMatrix(G,n);
    }

    // clique of size k = subset that forms a complete graph
    // stable set of size k = subset where no 2 vertices are adjacent
    // to have both, we combine a k-complete graph with k independent vertices
    public static int[][] createMatrix(int n, int k) {
        int[][] matrix = new int[n][n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i != j) {
                    matrix[i][j] = random.nextInt(2);
                    matrix[j][i] = matrix[i][j];
                }
            }
        }
//        printMatrix(matrix,n);
//        System.out.println();

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    matrix[i][j] = 1;
                }
            }
        }

        for (int i = k; i < k * 2; i++) {
            for (int j = k; j < k * 2; j++) {
                matrix[i][j] = 0;
            }
        }

        return matrix;
    }

    public static void printMatrix(int[][] matrix, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void prettyPrintMatrix(int[][] matrix, int n) {
        String[][] prettyMatrix = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    prettyMatrix[i][j] = "●";
                } else {
                    prettyMatrix[i][j] = "○";
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(prettyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int countEdges(int[][] matrix, int n) {
        int edges = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (matrix[i][j] == 1) {
                    edges++;
                }
            }
        }

        return edges;
    }

    public static void printDegrees(int[][] matrix, int n) {
        int minDegree = n - 1;
        int maxDegree = 0;

        for (int i = 0; i < n; i++) {
            int degree = 0;

            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    degree++;
                }
            }

            maxDegree = Math.max(maxDegree, degree);
            minDegree = Math.min(minDegree, degree);
        }

        System.out.println("Maximum Degree (Δ(G)): " + maxDegree);
        System.out.println("Minimum Degree (δ(G)): " + minDegree);
    }

    public static void verifySum(int[][] matrix, int n, int nEdges) {
        int degreeSum = 0;

        for (int i = 0; i < n; i++) {
            int degree = 0;

            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    degree++;
                }
            }

            degreeSum += degree;
        }

        if (degreeSum == nEdges * 2) {
            System.out.println("Sum is equal to " + nEdges * 2);
        } else {
            System.out.println("Sum is not equal to " + nEdges * 2);
        }
    }
}

class Bonus {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        int[][] G = createMatrix(n);
        printMatrix(G, n);

        int[][] C = complementMatrix(G, n);

        System.out.println("It is " + hasClique(G, n, k) + " that the graph has at least one clique of size " + k + ".");
        System.out.println("It is " + hasClique(C, n, k) + " that the graph has at least one stable set of size " + k + ".");
    }

    public static int[][] createMatrix(int n) {
        int[][] matrix = new int[n][n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i != j) {
                    matrix[i][j] = random.nextInt(2);
                    matrix[j][i] = matrix[i][j];
                }
            }
        }
        return matrix;
    }

    public static void printMatrix(int[][] matrix, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Algorithm logic:
    // First we find if the graph has at least a clique of size k, then we build the complement matrix to apply
    // the same algorithm, but for stable sets instead. The main part of the algorithm for finding the clique
    // is recursively building subsets of size k and check if they form a clique, by tracking how many vertices
    // we add so far (index) and by picking a (start) vertex to avoid duplicates.

    public static boolean isClique(int[][] matrix, int[] subset, int k) {
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                if (matrix[subset[i]][subset[j]] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean findClique(int[][] matrix, int[] subset, int n, int k, int index, int start) {
        if (index == k) {
            return isClique(matrix, subset, k);
        }

        for (int i = start; i < n; i++) {
            subset[index] = i;
            if (findClique(matrix, subset, n, k, index + 1, i + 1)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasClique(int[][] matrix, int n, int k) {
        int[] subset = new int[k];
        return findClique(matrix, subset, n, k, 0, 0);
    }

    public static int[][] complementMatrix(int[][] matrix, int n) {
        int[][] complement = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) complement[i][j] = 1 - matrix[i][j];
            }
        }
        return complement;
    }
}

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        switch (args[0]) {
            case "C" -> Compulsory.main(args);
            case "H" -> Homework.main(new String[]{args[1], args[2]});
            case "B" -> Bonus.main(new String[]{args[1], args[2]});
        }
    }
}