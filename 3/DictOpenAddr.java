public class DictOpenAddr {
    DictData[] H;       // 辞書の配列
    int B;              // 配列の大きさ

    // コンストラクタ
    DictOpenAddr(int len) {
        B = len;
        H = new DictData[len];

        for (int i = 0; i < B; i++)
            H[i] = new DictData();
    }

    // ハッシュ関数
    int h(int d, int count) {
        return (d + count) % B;
    }

    // データ d を辞書に挿入
    void insert_hash(int d) {
        int count = 0;
        int hash = h(d, count);

        while (true) {
            if (H[hash].state != State.OCCUPIED) {      // 配列が空いている場合は挿入
                H[hash].name = d;
                H[hash].state = State.OCCUPIED;
                break;
            }

            hash = h(d, ++count);                       // 空いていない場合は次のハッシュを得る
            if (count == B) {
                System.err.println("要素数が辞書の大きさを超えました");
                break;
            }
        }
    }

    // データ d が辞書内に含まれるかを探索（戻り値はboolean型でも可）
    int search_hash(int d) {
        int count = 0;
        int hash = h(d, count);

        while (true) {
            if (H[hash].state == State.OCCUPIED && H[hash].name == d)   // データが存在
                return hash;
            if (H[hash].state == State.EMPTY || ++count == B)           // 空の要素に当たった場合は存在しない
                break;
            hash = h(d, count);                                         // 次のハッシュを得る
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
        DictOpenAddr dict = new DictOpenAddr(6);

        // 挿入
        dict.insert_hash(1);
        dict.insert_hash(2);
        dict.insert_hash(3);
        dict.insert_hash(11);
        dict.insert_hash(12);
        dict.insert_hash(21);
        dict.display();
        dict.insert_hash(31);

        // 検索
        System.out.println("Search 1 ...\t" + dict.search_hash(1));
        System.out.println("Search 3 ...\t" + dict.search_hash(3));
        System.out.println("Search 21 ...\t" + dict.search_hash(21));
        System.out.println("Search 5 ...\t" + dict.search_hash(5));

        // 削除
        dict.delete_hash(3);
        dict.delete_hash(11);
        dict.display();

        // 削除した要素の検索
        System.out.println("Search 1 ...\t" + dict.search_hash(1));
        System.out.println("Search 11 ...\t" + dict.search_hash(11));
    }
}
