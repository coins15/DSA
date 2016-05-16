public class ListDLGenerics<T extends Comparable<T>> {          // searchのためにcompareToを持つクラスに限定
    private ListDLGenerics prev, next;  // 前と次のセルを指す
    private boolean dummy;              // ダミーセルかどうかを記録
    T val;                              // T型の値を格納

    // コンストラクタ
    ListDLGenerics() {
        this.dummy = true;
        initLinks();
    }
    ListDLGenerics(T val) {
        this.dummy = false;
        this.val = val;
        initLinks();
    }

    // セルを挿入、削除する操作を共通化するメソッド
    private static void __Insert(ListDLGenerics cell, ListDLGenerics prev, ListDLGenerics next) {   // prevとnextの間にcellを挿入
        prev.next = cell;
        cell.prev = prev;
        cell.next = next;
        next.prev = cell;
    }
    private static void __Delete(ListDLGenerics prev, ListDLGenerics next) {                        // prevとnextの間のセルを削除
        prev.next = next;
        next.prev = prev;
    }

    // 前後のセルへのリンクの初期化
    private void initLinks() {
        this.prev = this;
        this.next = this;
    }

    // このセル (this) の次に cell を挿入
    void insertNext(ListDLGenerics cell) {
        __Insert(cell, this, this.next);
    }

    // このセル (this) の前に cell を挿入
    void insertPrev(ListDLGenerics cell) {
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
    ListDLGenerics search(T i) {
        if (!this.dummy && this.val.compareTo(i) == 0)
            return this;
        if (this.next.dummy)            // 末尾まで検索したとき
            return null;
        return this.next.search(i);
    }

    // リストの要素を順に出力
    void display() {
        if (!this.dummy)
            System.out.print(this.val);

        if (this.next.dummy) {          // 末尾まで表示したとき
            System.out.println();
        } else {
            if (!this.dummy)            // 先頭の場合は頭にスペースをつけない
                System.out.print(" ");
            this.next.display();
        }
    }
}
