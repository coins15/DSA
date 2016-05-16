public class ListDLmain {
    public static void main(String[] args) {
        ListDL head = new ListDL();         // ダミーセルの生成
        ListDL elem;

        head.insertNext(new ListDL(2));     // セルの先頭への追加
        head.insertNext(new ListDL(1));
        head.display();                     // リストの表示
        System.out.println();

        head.insertPrev(new ListDL(5));     // セルの末尾への追加
        head.display();                     // リストの表示
        System.out.println();

        elem = head.search(2);              // セルを探す
        elem.insertNext(new ListDL(3));     // 探したセルの直後にセルを追加
        head.display();
        System.out.println();

        elem = head.search(2);              // セルを探す
        elem.delete();                      // 探したセルを削除
        head.display();
        System.out.println();

        head.delete();                      // ダミーセルを削除
        elem = head.search(1);              // 先頭のセルを探す
        elem.delete();                      // 探したセルを削除
        head.display();
        elem = head.search(5);              // 末尾のセルを探す
        elem.delete();                      // 探したセルを削除
        head.display();
        System.out.println();

    }
}
