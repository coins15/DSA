import java.io.*;
import java.util.*;

public class ShortestPathWarshallFloyd {

    static final int M = Integer.MAX_VALUE/2;	// 到達不能（無限大）
    static final int N = 6;			// グラフの頂点数

    static int w[][] = {			// グラフの隣接行列
        { 0, M,  M, 8, 15, M},
        {10, 0, 24, M,  8, M},
        { M, M,  0, M,  M, 6},
        { M, M,  M, 0,  5, M},
        { M, M, 12, M,  0, 7},
        { M, M,  3, M,  M, 0}};
    static int d[][] = new int[N][];      // 重みの累積値を表す配列

    static int parent[][] = new int[N][]; // i,j: iからjの最短経路におけるjの直前の頂点
    /**
     * 頂点 u から頂点 x に接続する辺が存在すれば true, それ以外は
     * false を返す.
     * @param u 頂点
     * @param x 頂点
     * @return 辺が存在すれば true, それ以外は false
     */
    static boolean member(int u, int x) {
        return w[u][x] != M;
    }
    /**
     * ダイクストラ法で，頂点 p から各頂点への最短路の重みを計算する．
     * @param p 開始点
     * @return なし
     */
    static void WarshallFloyd() {
        for(int k=0;k<N;k++){
            System.out.print(".");
            for(int i=0;i<N;i++){
                for(int j=0;j<N;j++){
                    if(d[i][j] > d[i][k]+d[k][j]){
                        d[i][j] = d[i][k]+d[k][j];
                        parent[i][j] = parent[k][j];
                    }
                }
            }
        }
    }

    /**
     * 配列の中身を標準出力に表示．結果出力およびデバッグ用．
     * @param name ラベル（変数名など）
     * @param ary 配列
     * @return なし
     */
    static void display(String name, int[] ary) {
        System.out.print(name + ":");
        for (int i = 0; i < ary.length; i++) {
            if (ary[i] == M)
                System.out.print(" M");
            else 
                System.out.print(" " + ary[i]);
        }
        System.out.println();
    }

    public static void printPath(int i,int j){
        if(i==j)return;
        System.out.print(j + " -["+w[parent[i][j]][j] +"]> ");
        printPath(i,parent[i][j]);
    }
    /**
     * メイン関数．
     * @param args コマンドライン引数
     * @return なし
     */
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        
        //初期化
        for(int i=0;i<N;i++){
            d[i] = new int[N];
            parent[i] = new int[N];
            for(int j=0;j<N;j++){
                d[i][j] = w[i][j];
                parent[i][j] = i;
            }
        }
        
        System.out.print("Calculating");
        WarshallFloyd();			// ワーシャル・フロイド法による最短路の計算
        
        while(true){
            System.out.flush();
            // String s = DIS.readLine();      // 文字列の入力
            // int p = Integer.parseInt(s);  // 整数に変換
            int p=0;
            do{
                try{
                    System.out.print("表示したい開始頂点を入力 (0~" + (N-1)+"): ");
                    line = reader.readLine();
                    p = Integer.parseInt(line);
                } catch (IOException e){
                }
            }while(!(0<=p && p<N));
            display("Result", d[p]);		// 結果表示
            
            for(int i=0;i<N;i++){
                if(d[p][i]==M){
                    System.out.println(i+" impossible");
                    continue;
                }
                printPath(p,i);
                System.out.println(p + " (" + d[p][i] +")");
            }
        }
    }
}
