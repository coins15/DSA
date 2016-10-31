public class DPKnapsack2 {
    static int[] v = {0, 250, 380, 420, 520};
    static int[] w = {0, 1, 2, 4, 3};

    public static boolean[] knapsack (int[] v, int[] w, int n, int C) {
        int i, j;
        int[][] G  = new int[n+1][C+1];
        boolean[][] S  = new boolean[n+1][C+1]; 
        boolean[] SS  = new boolean[n+1]; 

        // 荷物がないときは0を代入
        for (i = 0; i <= C; i++)
            G[n][i] = 0;

        // SSにすべてfalseを初期化
        for (i = 0; i <= n; i++)
            for (j = 0; j <= C; j++)
                S[i][j] = false;

        // 漸化式を更新
        for (i = n - 1; i >= 0; i--) {
            for (j = 0; j <= C; j++) {
                G[i][j] = G[i + 1][j];
                if (j >= w[i + 1]) {
                    int k = G[i + 1][j - w[i + 1]] + v[i + 1];
                    if (k > G[i][j]) {
                        G[i][j] = k;
                        S[i][j] = true; // 荷物を使った
                    }
                }
            }
        }

        // Sを逆順に辿っていく
        for (i = 0; i < n; i++) {
            if (S[i][C]) {
                C -= w[i + 1];
                SS[i + 1] = true;
            } else {
                SS[i + 1] = false;
            }
        }

        return SS;
    }

    public static void main(String [] args) {
        if (args.length == 2) {
            int k = Integer.parseInt(args[0]);
            int i = Integer.parseInt(args[1]);
            boolean [] S = knapsack(v, w, k, i);  
            int total = 0;
            for (k = 1; k< S.length; k++) 
                if (S[k]) {
                    total = total + v[k];
                    System.out.println("重さ "+w[k]+" 価値 "+v[k]);
                }
            System.out.println("合計価値 "+total);
        } else 
            System.out.println("１〜２個の引数を与えてください");
    }

}
