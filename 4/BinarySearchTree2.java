import java.util.*;

// 削除の実装で使用するデータ型
enum Lr {
    L, R
};

public class BinarySearchTree2 {
    static IntNode2 root;         // 2分探索木の根（nullで初期化されている）

    // 木構造の表示：2分木の実装のメソッド displayを再利用
    public static void display () {
        display(root);
    }

    public static void display (IntNode2 n) {
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

    private static ArrayList<String> __display (IntNode2 n) {                // 1行ごとにArrayListに入れて返す
        int diff;
        ArrayList<String> left, right, result = new ArrayList<String>();

        String val = Integer.toString(n.val) + "#" + Integer.toString(n.height);
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

    // 最小値の探索
    public static int min_bst() {
        IntNode2 n = min_bst(root);
        if (n == null)
            return Integer.MAX_VALUE;
        if (n.left == null)
            return n.val;
        return n.left.val;
    }

    private static IntNode2 min_bst(IntNode2 n) {                         // 削除で使うために対象ノードの1つ上を返す
        if (n == null)
            return null;

        IntNode2 prev = n;
        while (n.left != null) {
            prev = n;
            n = n.left;
        }
        return prev;
    }

    // 最大値の探索
    public static int max_bst() {
        IntNode2 n = max_bst(root);
        if (n == null)
            return Integer.MIN_VALUE;;
        if (n.right == null)
            return n.val;
        return n.right.val;
    }

    private static IntNode2 max_bst(IntNode2 n) {                         // 削除で使うために対象ノードの1つ上を返す
        if (n == null)
            return null;

        IntNode2 prev = n;
        while (n.right != null) {
            prev = n;
            n = n.right;
        }
        return prev;
    }

    // 値の探索
    public static boolean search_bst(int d) {
        IntNode2 n = search_bst(root, d);
        if (n == null)
            return false;
        return true;
    }

    private static IntNode2 search_bst(IntNode2 n, int d) {               // 削除で使うために対象ノードの1つ上を返す
        IntNode2 prev = n;
        while (n != null) {
            if (n.val == d)
                return prev;
            prev = n;
            if (n.val > d)
                n = n.left;
            else if (n.val < d)
                n = n.right;
        }
        return null;
    }

    // 値の挿入
    public static void insert_bst(int d) {
        IntNode2 n = root, newNode = new IntNode2(d, null, null);

        if (root == null) {                                             // rootノードが初期化されていない時はrootに入れる
            root = newNode;
        } else {
            while (true) {                                              // 葉まで探索する
                if (n.val == d)
                    break;
    
                if (n.val > d) {
                    if (n.left == null) {
                        n.left = newNode;
                        break;
                    }
                    n = n.left;
                }
    
                if (n.val < d) {
                    if (n.right == null) {
                        n.right = newNode;
                        break;
                    }
                    n = n.right;
                }
            }
        }

        update_height(root, d);
    }

    // 値の削除
    public static void delete_bst(int d) {
        IntNode2 n = search_bst(root, d);
        if (n == null)
            return;

        int search = n.val;

        if (n.left != null && n.left.val == d) {
            if (n.left.left == null && n.left.right == null) {
                n.left = null;
            } else if (n.left.left == null) {
                n.left = n.left.right;
            } else if (n.left.right == null) {
                n.left = n.left.left;
            } else {
                IntNode2 max = max_bst(n.left);
                n.left.val = max.right.val;
                max.right = null;
                search = max.val;
            }
        } else if (n.right != null && n.right.val == d) {           // 削除ノードが上のノードの右側にあるとき
            if (n.right.left == null && n.right.right == null) {    // 子ノードはなし
                n.right = null;
            } else if (n.right.left == null) {                      // 子ノードは右側のみ
                n.right = n.right.right;
            } else if (n.right.right == null) {                     // 子ノードは左側のみ
                n.right = n.right.left;
            } else {                                                // 右側の最小値と入れ替える
                IntNode2 min = min_bst(n.right);
                n.right.val = min.left.val;
                min.left = null;
                search = min.val;
            }
        } else {                                                    // rootノードの削除のとき
            if (n.left != null) {                                   // 左側にノードがあればその最大値と入れ替える
                IntNode2 max = max_bst(n.left);
                if (max.right == null) {
                    n.val = max.val;
                    n.left = null;
                } else {
                    n.val = max.right.val;
                    max.right = null;
                    search = max.val;
                }
            } else if (n.right != null) {                           // 右側にノードがあればその最小値と入れ替える
                IntNode2 min = min_bst(n.right);
                if (min.left == null) {
                    n.val = min.val;
                    n.right = null;
                } else {
                    n.val = min.left.val;
                    min.left = null;
                    search = min.val;
                }
            } else {                                                // rootノードしか残っていないとき
                root = null;
            }
        }

        update_height(root, search);
    }

    private static void update_height(IntNode2 n, int d) {
        if (n == null)
            return;
        
        if (n.val > d)
            update_height(n.left, d);
        else if (n.val < d)
            update_height(n.right, d);

        n.reset_height();
    }

    public static void main (String[] args) {
        int[] nodes = {10, 15, 18, 6, 12, 20, 9};
        for (int i = 0; i < nodes.length; i++)
            insert_bst(nodes[i]);
        display();

        delete_bst(15);
        display();

        delete_bst(19);
        display();
    }
}
