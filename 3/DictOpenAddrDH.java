import java.util.*;

public class DictOpenAddrDH {
    DictData[] H;           // 辞書の配列
    int B;                  // 配列の大きさ
    boolean hash1, hash2;   // hash1 = ハッシュ関数, hash2 = 線形走査法か2重ハッシュ法か

    final int DH = 7;       // 2重ハッシュ法のmod

    // コンストラクタ
    DictOpenAddrDH(int len, boolean h1, boolean h2) {
        B = len;
        H = new DictData[len];

        for (int i = 0; i < B; i++)
            H[i] = new DictData();

        hash1 = h1; hash2 = h2;
        if (hash2)                                  // 2重ハッシュ法の時はDHと要素数が互いに素でなければならない
            for (int i = 2; i <= DH; i++)
                if (DH % i == 0 && B % i == 0)
                    System.err.println("配列の大きさが" + i + "で割り切れます");
    }

    // ハッシュ関数
    int h1(int d) {
        if (!hash1)
            return d % B;

        double dA = d * 0.5 * (Math.sqrt(5) -1);         // Mutiplication Method
        return (int)Math.floor(B * (dA - Math.floor(dA)));
    }

    // ハッシュの増分を求める　
    int h2(int init, int d, int count) {
        if (!hash2)                                 // 線形走査法
            return (init + count) % B;
        return (init + count * (d % DH + 2)) % B;   // 2重ハッシュ法
    }

    // データ d を辞書に挿入
    void insert_hash(int d) {
        int count = 0;
        int init = h1(d), hash = init;              // h1は1度のみ計算

        while (true) {
            if (H[hash].state != State.OCCUPIED) {  // 配列が空いている場合は挿入
                H[hash].name = d;
                H[hash].state = State.OCCUPIED;
                break;
            }

            hash = h2(init, d, ++count);            // 空いていない場合は次のハッシュを得る
            if (count == B) {
                System.err.println("要素数が辞書の大きさを超えました");
                break;
            }
        }
    }

    // データ d が辞書内に含まれるかを探索（戻り値はboolean型でも可）
    int search_hash(int d) {
        int count = 0;
        int init = h1(d), hash = init;                                  // h1は1度のみ計算

        while (true) {
            if (H[hash].state == State.OCCUPIED && H[hash].name == d)   // データが存在
                return hash;
            if (H[hash].state == State.EMPTY || ++count == B)           // 空の要素に当たった場合は存在しない
                break;
            hash = h2(init, d, count);                                  // 次のハッシュを得る
        }

        return -1;
    }

    // データ d を辞書から削除
    void delete_hash(int d) {
        int hash;
        while ((hash = search_hash(d)) != -1) {                         // 該当するデータをすべて削除
            H[hash].state = State.DELETED;
        }
    }

    // 配列要素の表示
    void display() {
        for (int i = 0; i < B; i++) {
            if (H[i].state == State.OCCUPIED)
                System.out.println(i + ": " + H[i].name);
            else if (H[i].state == State.EMPTY)
                System.out.println(i + ": EMPTY");
            else if (H[i].state == State.DELETED)
                System.out.println(i + ": DELETED");
        }
    }

    // mainメソッド
    public static void main(String[] args) {
        final int D = 10;       // 密度について10段階で比較
        final int M = 131311;   // 辞書の大きさ
        final int T = 10;       // 試行回数
        final int P = 10;       // 検索するデータの割合 (10分の1)

        // 挿入と検索にかかった時間
        long insertionTime[][] = new long[4][D], searchTime[][] = new long[4][D];
        Random rnd = new Random();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < D; j++) {
                insertionTime[i][j] = 0;
                searchTime[i][j] = 0;
            }
        }

        for (int i = 0; i < T; i++) {
            System.out.println(i);
            DictOpenAddrDH dict[] = {new DictOpenAddrDH(M, false, false), new DictOpenAddrDH(M, false, true),
                                     new DictOpenAddrDH(M, true, false), new DictOpenAddrDH(M, true, true)};

            int num[] = new int[M], search[] = new int[M / P + 1];  // 辞書に入れるデータと検索するデータ

            for (int j = 0; j < D; j++) {
                int start = M * j / D, end = M * (j + 1) / D;

                for (int k = start; k < end; k++) {
                    num[k] = rnd.nextInt(M * 100);                  // 辞書に入れるデータ
                    if (k % P == 0)
                        search[k / P] = rnd.nextInt(M * 100);       // 検索するデータ
                }

                for (int k = 0; k < 4; k++) {
                    long t1 = System.nanoTime();
                    for (int l = start; l < end; l++)               // 新しいデータを挿入
                        dict[k].insert_hash(num[l]);
                    long t2 = System.nanoTime();

                    insertionTime[k][j] += t2 - t1;
                }

                for (int k = 0; k < 4; k++) {
                    long t1 = System.nanoTime();
                    for (int l = 0; l < end / P; l++)               // 検索
                        dict[k].search_hash(search[l]);
                    long t2 = System.nanoTime();

                    searchTime[k][j] += t2 - t1;
                }
            }
        }

        // 挿入時間は累積和を表示
        System.out.println("Insertion:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < D; j++) {
                if (j != 0) {
                    insertionTime[i][j] += insertionTime[i][j - 1];
                    System.out.print("\t");
                }
                // 平均を計算
                System.out.print(insertionTime[i][j] / (double)T);
            }
            System.out.println();
        }

        System.out.println("Search:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < D; j++) {
                if (j != 0)
                    System.out.print("\t");
                // 平均を計算
                System.out.print(searchTime[i][j] / (double)T);
            }
            System.out.println();
        }
    }
}
