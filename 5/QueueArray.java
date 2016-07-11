import java.*;

public class QueueArray {
    int length, front, rear;
    int[] queue;

    // 指定された長さの配列を生成するコンストラクタ
    QueueArray(int len) {
        queue = new int[len];
        front=rear=length=0;
    }

    // データのエンキュー
    void enqueue(int val) {
        if(++length > queue.length){
            System.err.println("Queue is full.");
            //System.exit(1);
            length--;
            return;
        }
        queue[rear++]=val;
        rear %=queue.length;
    }

    // データのデキュー
    int dequeue() {
        if(--length < 0){
            System.err.println("Queue is empty.");
            //System.exit(1);
            return -1;
        }
        int ret=queue[front++];
        front %= (queue.length);
        return ret;
    }

    // キューの要素の表示
    void display() {
        for(int i=0;i<length;i++){
            System.out.print(queue[(front+i)%(queue.length)]+" ");
        }
        System.out.println("");
    }

    // main メソッド
    public static void main(String[] args) {
        QueueArray queue = new QueueArray(3);
        queue.enqueue(0);
        System.out.println(queue.dequeue());

        queue.enqueue(1);
        queue.display();
        queue.enqueue(2);
        queue.display();
        queue.enqueue(3);
        queue.display();
        
        queue.enqueue(4);
        queue.display();

        System.out.println(queue.dequeue());
        queue.display();
        
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        queue.display();
        System.out.println(queue.dequeue());
    }
}
