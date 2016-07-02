// 2分木のノードを実現するクラス
public class Node {
    String val;
    Node left, right;  

    public Node (String val, Node left,  Node right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
