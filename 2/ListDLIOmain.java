import java.io.*;

public class ListDLIOmain {
    public static void main(String[] args) throws IOException {
        ListDLIO head = new ListDLIO();         // ダミーセルの生成
        ListDLIO elem;

        head.insertNext(new ListDLIO(2));       // セルの先頭への追加
        head.insertNext(new ListDLIO(1));
        head.display();                         // リストの表示

        int num[] = {6, 7, 8, 9};               // 配列を初期化
        head.readFromArray(num);                // ファイルの内容をセルの先頭へ追加
        head.display();

        int arr[] = head.writeToArray();        // 配列への書き出し
        for (int i = 0; i < arr.length; i++) {
            if (i != 0)
                System.out.print(" ");
            System.out.print(arr[i]);           // 配列の内容を表示
        }
        System.out.println();

        File input = new File("input.txt");     // ファイルを開く
        head.readFromFile(input);               // ファイルの内容をセルの先頭へ追加
        head.display(); 

        File output = new File("output.txt");   // ファイルを開く
        head.writeToFile(output);               // ファイルへの書き出し
    }
}
