public class IntNode2 {
    int val; 
    IntNode2 left, right;  
    int height;

    // 子供の木の高さの情報からこの節の高さを再設定
    public void reset_height() {
        this.height = Math.max(height(left), height(right)) + 1;
    }

    // 高さの情報を返す。nがnullの時は0を返す。
    public static int height(IntNode2 n) {
        if (n == null) return 0; else return n.height;
    }

    public IntNode2 (int val, IntNode2 left,  IntNode2 right) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.height = Math.max(height(left), height(right)) + 1;
    }
}
