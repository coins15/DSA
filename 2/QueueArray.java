public class QueueArray {
    int length, front, count;
    int[] queue;

    // 指定された長さの配列を生成するコンストラクタ
    QueueArray(int len) {
        length = len;
        front = 0;
        count = 0;
        queue = new int[len];
    }

    // データのエンキュー
    int enqueue(int val) {
        if (count == length)                    // 要素数が配列の長さと同じときは挿入しない
            return -1;

        queue[(front + count) % length] = val;
        count += 1;
        return count;
    }

    // データのデキュー
    int dequeue() {
        if (count == 0)                         // 残っている要素がないとき
            return Integer.MIN_VALUE;

        int result = queue[front];
        front = (front + 1) % length;
        count -= 1;
        return result;
    }

    // キューの要素の表示
    void display() {
        for (int i = 0; i < count; i++) {
            if (i != 0)                         // 先頭の要素は頭にスペースをつけない
                System.out.print(" ");
            System.out.print(queue[(i + front) % length]);
        }
        System.out.println();
    }

    // main メソッド
    public static void main(String[] args) {
        QueueArray queue = new QueueArray(3);

        // キューに整数を1つ格納し、それが取り出せること。
        queue.enqueue(1);
        queue.display();
        System.out.println("dequeue: " + queue.dequeue());
        System.out.println();

        // キューに整数を複数連続して格納し、それが格納した順番で取り出せること。
        queue.enqueue(1);
        queue.enqueue(2);
        queue.display();
        System.out.println("dequeue: " + queue.dequeue());
        System.out.println("dequeue: " + queue.dequeue());
        System.out.println();

        // キューに格納するデータが配列の末尾と先頭にまたがる場合で、上の 1, 2 が行えること。
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        queue.enqueue(3);
        queue.enqueue(4);
        System.out.println("front = " + queue.front + ", count = " + queue.count);
        queue.display();
        System.out.println("dequeue: " + queue.dequeue());
        System.out.println("dequeue: " + queue.dequeue());
        System.out.println("dequeue: " + queue.dequeue());
        System.out.println();

        // キューに格納するデータが配列の末尾と先頭にまたがる場合と、そうでない場合の両方の状態について、1) 要素を取り出し、キューが空になった後にさらに要素取り出そうとした時、2) キューが一杯の時にさらに格納しようとした時、その旨をメッセージとして出力すること。
        System.out.println("dequeue: " + queue.dequeue());
        System.out.println("enqueue: " + queue.enqueue(1));
        System.out.println("enqueue: " + queue.enqueue(2));
        System.out.println("enqueue: " + queue.enqueue(3));
        queue.display();
        System.out.println("enqueue: " + queue.enqueue(4));
        queue.display();
    }
}
