public class SubsetSum {
    /* 
     * 配列setが集合{3,7,11,15} を表現
     */
    static int[] set = {3, 7, 11, 15};

    /*
     * set[] : 正の整数の集合
     * n :     対象とする要素数
     * sum :   部分和
     */
    public static boolean[] subsetSum (int[] set, int n, int sum) {
        boolean[] SS = new boolean[n];
        // G[i][j] = i番目までの数字で部分和jを作れるか
        boolean[][] G = new boolean[n + 1][sum + 1];
        boolean[][] S = new boolean[n + 1][sum + 1];

        // Gの初期化
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= sum; j++) {
                G[i][j] = false;
                S[i][j] = false;
            }
        }

        // 0はいつでも作れる
        G[0][0] = true;

        // Gを更新
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= sum; j++) {
                G[i][j] = G[i - 1][j];
                if (j >= set[i - 1] && G[i - 1][j - set[i - 1]])
                    G[i][j] = S[i][j] = true;
            }
        }

        // 部分和が作れない時
        if (!G[n][sum])
            return null;

        // 使った数字を辿っていく
        for (int i = n; i > 0; i--) {
            if (S[i][sum]) {
                SS[i - 1] = true;
                sum -= set[i - 1];
            } else {
                SS[i - 1] = false;
            }
        }

        return SS;
    }

    public static void main(String [] args) {
        if (args.length == 2) {
            int n = Integer.parseInt(args[0]);
            int k = Integer.parseInt(args[1]);
            boolean [] S = subsetSum(set, n, k);
            if (S != null) {
                System.out.println("部分集合");
                for (int j = 0; j < S.length; j++)
                    if (S[j])  System.out.println(set[j]);
            } else
                System.out.println("条件を満たす部分集合はない．");
        } else 
            System.out.println("２個の引数を与えてください");
    }
}
