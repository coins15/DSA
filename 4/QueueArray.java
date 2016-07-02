public class QueueArray<T> {
	int length, front, count;
	Object[] queue;

	// 指定された長さの配列を生成するコンストラクタ
	QueueArray(int len) {
		length = len;
		front = 0; count = 0;
		queue = new Object[len];
	}

	// データのエンキュー
	int enqueue(T val) {
		if (count == length)
			return -1;

		queue[(front + count) % length] = val;
		count += 1;
		return count;
	}

	// データのデキュー
	T dequeue() {
		if (count == 0)
            return null;

		T result = (T)queue[front];
		front = (front + 1) % length;
		count -= 1;
		return result;
	}

	// キューの要素の表示
	void display() {
		for (int i = 0; i < count; i++) {
			if (i != 0)
				System.out.print(" ");
			System.out.print(queue[(i + front) % length]);
		}
		System.out.println();
	}

	// main メソッド
	public static void main(String[] args) {
		QueueArray queue = new QueueArray<Integer>(3);

		queue.enqueue(new Integer(1));
		queue.enqueue(new Integer(2));
		queue.display();

		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());
	}
}
