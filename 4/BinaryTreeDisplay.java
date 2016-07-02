import java.util.*;

public class BinaryTreeDisplay {
    // 行きがけ順のなぞり
    public static void preorder (Node n) {
        if (n == null)
            return;

        System.out.print(n.val);
        __preorder(n.left);
        __preorder(n.right);
        System.out.println();
    }

    private static void __preorder (Node n) {
        if (n == null)
            return;

        System.out.print(" " + n.val);
        __preorder(n.left);
        __preorder(n.right);
    }

    // 通りがけ順のなぞり
    public static void inorder (Node n) {
        if (n == null)
            return;

        __inorder(n.left, false);
        System.out.print(n.val);
        __inorder(n.right, true);
        System.out.println();
    }

    private static void __inorder (Node n, boolean front) {
        if (n == null)
            return;

        __inorder(n.left, front);
        if (front)
            System.out.print(" ");
        System.out.print(n.val);
        if (!front)
            System.out.print(" ");
        __inorder(n.right, front);
    }

    // 帰りがけ順のなぞり
    public static void postorder (Node n) {
        if (n == null)
            return;

        __postorder(n.left);
        __postorder(n.right);
        System.out.println(n.val);
    }

    private static void __postorder (Node n) {
        if (n == null)
            return;

        __postorder(n.left);
        __postorder(n.right);
        System.out.print(n.val + " ");
    }

    // 木構造の表示
    public static void display (Node n) {
        if (n == null) {
            System.out.println();
        } else {
            ArrayList<String> result = __display(n);
            for (int i = 0; i < result.size(); i++)
                System.out.println(result.get(i));
        }
    }

    private static String __space(int n) {                                  // nを受け取って" " * nを返す
        String result = "";
        for (int i = 0; i < n; i++)
            result += " ";
        return result;
    }

    private static ArrayList<String> __display (Node n) {                   // 1行ごとにArrayListに入れて返す
        int diff;
        ArrayList<String> left, right, result = new ArrayList<String>();

        String val = n.val;
        if (val.length() % 2 == 0)                                          // ラベルが偶数文字の時はスペースを追加
            val = " " + val;

        if (n.left == null && n.right == null) {                            // 末端ノードのとき
            result.add(val);
            return result;
        }

        if (n.left == null)
            left = new ArrayList<String>();
        else
            left = __display(n.left);

        if (n.right == null)
            right = new ArrayList<String>();
        else
            right = __display(n.right);

        if (right.size() != 0) {                                            // 左側のデータの幅と高さを右側に合わせる
            if (left.size() != 0) {
                diff = Math.max(0, (right.get(0).length() - left.get(0).length()) / 2);
                for (int i = 0; i < left.size(); i++)
                    left.set(i, __space(diff) + left.get(i) + __space(diff));
            }
            while (left.size() < right.size())
                left.add(__space(right.get(0).length()));
        }

        if (left.size() != 0) {                                             // 右側のデータの幅と高さを左側に合わせる
            if (right.size() != 0) {
                diff = Math.max(0, (left.get(0).length() - right.get(0).length()) / 2);
                for (int i = 0; i < right.size(); i++)
                    right.set(i, __space(diff) + right.get(i) + __space(diff));
            }
            while (right.size() < left.size())
                right.add(__space(left.get(0).length()));
        }

        result.add(__space(left.get(0).length() + 1) + val + __space(right.get(0).length() + 1));
        for (int i = 0; i < left.get(0).length() / 2 + 1; i++) {            // 子ノードまでの辺を書く
            String line = __space(left.get(0).length() - i);
            if (n.left == null)
                line += " ";
            else
                line += "/";
            line += __space(i * 2 + val.length());
            if (n.right == null)
                line += " ";
            else
                line += "\\";
            line += __space(right.get(0).length() - i);
            result.add(line);
        }
        for (int i = 0; i < left.size(); i++)                               // 左右の子ノードをコピー
            result.add(left.get(i) + __space(val.length() + 2) + right.get(i));

        return result;
    }

    // 幅優先探索
    public static void breadth_first_search (Node n) {
        if (n == null)
            return;

        QueueArray queue = new QueueArray<Node>(100);

        System.out.print(n.val);
        if (n.left != null)
            queue.enqueue(n.left);
        if (n.right != null)
            queue.enqueue(n.right);

        while ((n = (Node)queue.dequeue()) != null) {
            System.out.print(" " + n.val);
            if (n.left != null)
                queue.enqueue(n.left);
            if (n.right != null)
                queue.enqueue(n.right);
        }

        System.out.println();
    }

    // 木の高さ：ただし、教科書の定義の高さ + 1とする。nullの高さが0, 根のみの木が高さ1
    public static int height (Node n) {
        if (n == null)
            return 0;
        return Math.max(height(n.left), height(n.right)) + 1;
    }

    public static void main (String[] args) {
        // 木構造の構築
        Node i = new Node("I", null, null);
        Node h = new Node("H", null, null);
        Node g = new Node("G", null, null);
        Node f = new Node("F", h, g);
        Node e = new Node("E", null, i);
        Node d = new Node("D", null, null);
        Node c = new Node("C", d, e);
        Node b = new Node("B", f, null);
        Node a = new Node("A", c, b);

        // 各メソッドのテスト
        preorder(a);
        postorder(a);
        inorder(a); 
        breadth_first_search(a);  
        display(a);
        System.out.println(height(a));
    }
}

