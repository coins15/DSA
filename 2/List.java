public class List {
    static List head;       // 先頭セルを格納する変数
    List next;              // セルのポインタ部
    int data;               // セルのデータ部

    // 新しいセルの挿入（挿入位置はセル p の直後）
    static void insert_cell(List p, int d) {
        List new_cell = new List();
        new_cell.data = d;
        new_cell.next = p.next;
        p.next = new_cell;                          // p.nextを置換する
    }

    // 新しいセルの挿入（挿入位置はリストの先頭）
    static void insert_cell_top(int d) {
        List new_cell = new List();
        new_cell.data = d;
        new_cell.next = head;
        head = new_cell;                            // 先頭セルを置換する
    }

    // セルの削除（削除されるのはセル p の直後のセル）
    static void delete_cell(List p) {
        if (p.next == null)                         // 直後のセルが存在しない場合
            return;
        p.next = p.next.next;
    }

    // セルの削除（削除されるのはリストの先頭セル）
    static void delete_cell_top() {
        head = head.next;
    }

    // リストの要素の表示
    static void display() {
        List pos = head;
        while (true) {
            System.out.print(pos.data);
            if (pos.next == null)
                break;
            pos = pos.next;
            System.out.print(" ");
        }
        System.out.println();
    }

    // main メソッド
    public static void main(String[] args) {
        // セルを、リストの先頭に挿入できること。
        insert_cell_top(1);
        display();
        System.out.println();

        // セルを、指定したセルの直後に挿入できること。
        insert_cell(head, 3);
        insert_cell(head, 2);
        display();
        System.out.println();

        // 先頭セルを削除できること。
        delete_cell_top();
        display();
        System.out.println();

        // 指定したセルの直後のセルを削除できること。
        delete_cell(head);
        display();
        System.out.println();

    }
}
