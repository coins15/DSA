import java.util.Random;

public class DPKnapsack {
    /* 教科書：表 6.1の例
       v[1]〜v[4]：価格
       w[1]〜w[4]：重さ */
    static int[] v = {0, 250, 380, 420, 520};
    static int[] w = {0, 1, 2, 4, 3};

    /**
     * ナップサック問題の最適解を探索（動的計画法を用いる）
     * v: 価格の配列
     * w: 重さの配列
     * k: 対象とする荷物の数
     * i: ナップサックの容量
     */
    public static int knapsack(int[] v, int[] w, int k, int i) {
        int[][] m = new int[k + 1][i + 1];
        // 荷物がないときは0を代入
        for (int j = 0; j < i + 1; j++)
            m[k][j] = 0;

        // 漸化式を更新
        for (int j = k - 1; j >= 0; j--) {
            for (int l = 0; l <= i; l++) {
                m[j][l] = m[j + 1][l];
                if (l >= w[j + 1])
                    m[j][l] = Math.max(m[j][l], m[j + 1][l - w[j + 1]] + v[j + 1]);
            }
        }

        return m[0][i];
    }

    public static void main(String [] args) {
        if (args.length == 2) {
            int k = Integer.parseInt(args[0]);
            int i = Integer.parseInt(args[1]);
            System.out.println(knapsack(v, w, k, i));  
        } else if (args.length == 1) {
            int n = Integer.parseInt(args[0]);
            int[] v = new int[n+1]; 
            int[] w = new int[n+1]; 
            Random rnd = new Random();
            for (int i = 1; i <= n; i++) { 
                v[i] = rnd.nextInt(100);
                w[i] = rnd.nextInt(10)+1;
            }
            long t1 = System.nanoTime(); 
            int i = knapsack(v, w, n, n*5);
            long t2 = System.nanoTime(); 
            System.out.println(i);   
            System.out.println();
            System.out.println(((t2-t1)/1000000)+"ミリ秒");
        } else 
            System.out.println("１〜２個の引数を与えてください");
    }

}
