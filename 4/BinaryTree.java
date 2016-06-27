public class BinaryTree {
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
        __display(n);
        System.out.println();
    }

    private static void __display (Node n) {
        if (n == null) {
            System.out.print("null");
            return;
        }

        System.out.print(n.val + "(");
        __display(n.left);
        System.out.print(", ");
        __display(n.right);
        System.out.print(")");
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

