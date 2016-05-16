import java.io.*;

public class ListDLIO {
    private ListDLIO prev, next;    // 前と次のセルを指す
    private boolean dummy;          // ダミーセルかどうかを記録
    int val;                        // セルには整数値を格納

    // コンストラクタ
    ListDLIO() {
        this.dummy = true;
        initLinks();
    }
    ListDLIO(int val) {
        this.dummy = false;
        this.val = val;
        initLinks();
    }

    // セルを挿入、削除する操作を共通化するメソッド
    private static void __Insert(ListDLIO cell, ListDLIO prev, ListDLIO next) { // prevとnextの間にcellを挿入
        prev.next = cell;
        cell.prev = prev;
        cell.next = next;
        next.prev = cell;
    }
    private static void __Delete(ListDLIO prev, ListDLIO next) {                // prevとnextの間のセルを削除
        prev.next = next;
        next.prev = prev;
    }

    // 前後のセルへのリンクの初期化
    private void initLinks() {
        this.prev = this;
        this.next = this;
    }

    // このセル (this) の次に cell を挿入
    void insertNext(ListDLIO cell) {
        __Insert(cell, this, this.next);
    }

    // このセル (this) の前に cell を挿入
    void insertPrev(ListDLIO cell) {
        __Insert(cell, this.prev, this);
    }

    // このセル (this) をリストから削除
    void delete() {
        if (this.prev == this && this.next == this) {
            System.err.println("このセルはリストに接続されていません");
        } else if (this.dummy) {
            System.err.println("ダミーセルは削除できません");
        } else {
            __Delete(this.prev, this.next);
            initLinks();
        }
    }

    // 与えられた整数 i を保持しているセルを探し、そのセルを返す．
    // 見つからなければ null を返す．
    ListDLIO search(int i) {
        if (!this.dummy && this.val == i)
            return this;
        if (this.next.dummy)                // 末尾まで検索したとき
            return null;
        return this.next.search(i);
    }

    // リストの要素を順に出力
    void display() {
        if (!this.dummy)
            System.out.print(this.val);

        if (this.next.dummy) {              // 末尾まで表示したとき
            System.out.println();
        } else {
            if (!this.dummy)
                System.out.print(" ");      // 先頭の場合は頭にスペースをつけない
            this.next.display();
        }
    }

    // このセルの次から配列の値を挿入
    void readFromArray(int[] array) {
        ListDLIO pos = this;
        for (int i = 0; i < array.length; i++) {
            pos.insertNext(new ListDLIO(array[i]));
            pos = pos.next;
        }
    }

    // このセルから末尾までを配列に書き出す
    int[] writeToArray() {
        int count = 0;
        ListDLIO pos = this;

        // 配列を確保するために要素数を数える
        while (true) {
            if (!pos.dummy)
                count++;
            if (pos.next.dummy)             // 末尾まで検索
                break;
            pos = pos.next;
        }

        int[] res = new int[count];
        count = 0;
        pos = this;

        while (true) {
            if (!pos.dummy)
                res[count++] = pos.val;
            if (pos.next.dummy)             // 末尾までコピー
                break;
            pos = pos.next;

        }

        return res;
    }

    // このセルの次からファイルの値を挿入
    void readFromFile(File input) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(input));

        String line;
        ListDLIO pos = this;

        while((line = br.readLine()) != null) {
            pos.insertNext(new ListDLIO(Integer.parseInt(line)));
            pos = pos.next;
        }
    }

    // このセルから末尾までをファイルに書き出す
    void writeToFile(File output) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(output)));
        ListDLIO pos = this;

        while (true) {
            if (!pos.dummy)
                pw.println(pos.val);
            if (pos.next.dummy)             // 末尾まで書き出し
                break;
            pos = pos.next;
        }

        pw.close();
    }
}
