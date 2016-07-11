import java.util.Random;
import java.util.Scanner;          // テキスト入力を簡単に扱うためのライブラリ

public class Sort {
    static long compare_count = 0; // 比較回数を計測するためのクラス変数

    // 比較回数をリセット
    static void reset () {
        compare_count = 0;
    }

    // i と j を比較
    static int compare (int i, int j) {
        if(visial)System.err.println("Comp "+i+"  "+j);
        compare_count++;
        if (i < j) return -1;
        else if (i == j) return 0;
        else return 1;
    }

    // 配列の要素の交換
    static void swap (int[] a, int i, int j) {
        if(visial)System.err.println("Swap ["+i+"]"+a[i]+"  ["+j+"]"+a[j]);
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }


    // 配列の要素を表示
    static void display (int [] a, int n) {
        for (int i = 0; i < n; i++) 
            System.out.print(a[i] + " ");
        System.out.println();
    }

    // 選択ソート
    static void selection_sort(int[] a, int n) {
        for (int i = 0; i < n-1; i++) {
            int min = i;
            for (int j = i+1; j < n; j++) {
                if (compare(a[j],a[min]) == -1) min = j;
            }
            swap(a, i, min);
        }
    }

    // 挿入ソート
    static void insertion_sort(int[] a, int n) {
        for(int i=1;i<n;i++){
            int tmp=a[i];
            int j=i-1;
            for(; j>=0 && compare(a[j],tmp)==1; j--){
                a[j+1] = a[j];
            }
            a[j+1] = tmp;
        }
    }

    // ヒープソート
    static void sift_down(int[] a, int i, int n) {
        int bigIdx = i;
        if(i*2+2<n){        //左右の子がいる
            bigIdx = (compare(a[i*2+1],a[i*2+2])>=0) ? i*2+1 : i*2+2;
        }else if(i*2+2==n){ //左の子だけいる
            bigIdx =  i*2+1;
        }
        if(bigIdx != i && compare(a[bigIdx],a[i])>0){
            swap(a,i,bigIdx);
            sift_down(a,bigIdx,n);
        }
    }

    static void build_heap (int[] a, int n) {
        for(int x=n/2-1; x>=0; x--){
            sift_down(a,x,n);
        }
    }

    static void heap_sort (int[] a, int n) {
        build_heap(a,n);
        for(int m=n-1; m>0; m--){
            swap(a,0,m);
            sift_down(a,0,m);
        }
    }

    // クイックソート
    // partition におけるピボットは, 最後の要素(a[right])とする. 
    static int partition(int[] a, int pivot, int left, int right) {
        if(right != pivot)swap(a,right,pivot);
        int l = left;
        int r = right - 1;
        while(true){
            while(compare(a[l],a[right]) <0) l++;
            while(l <= r && compare(a[r],a[right])>=0) r--;
            if(l<r) swap(a,l,r);
            else break;
        }
        if(l!=right)swap(a,l,right);
        return l;
    }

    static void quick_sort(int[] a, int left, int right) {
        if(left >= right) return;
        int pivot = right;
        int p = partition(a,pivot,left,right);
        quick_sort(a,left,p-1);
        quick_sort(a,p+1,right);
    }

    static void qsort(int[] a, int n) {
        quick_sort(a, 0, n-1);
    }


    // 待ち行列の配列を表示
    static void display_QueueArray(QueueArray[] b, int n) {
        for(int i=0;i<n;i++){
            System.out.print(i+":");
            b[i].display();
        }
        System.out.println();
    }

    // 基数ソート
    static void radix_sort(int[] a, int n, int k) {
        QueueArray[] b = new QueueArray[10];   // 課題2で実装した待ち行列QueueArrayを用いる
        for (int i = 0; i < 10; i++){
            b[i] = new QueueArray(n+1);  // 大きさnの配列で実装したQueueArrayは, n-1個しか要素を格納できない(わけじゃない)
        }
        for(int i=0;i<n;i++){
            b[a[i]%10].enqueue(a[i]);
        }
        for(int K=1,d=10;K<k;K++,d*=10){
            if(visial)display_QueueArray(b,10);
            for(int i=0;i<10;i++) b[i].enqueue(-1);
            for(int i=0;i<10;i++){
                while(true){
                    int v=b[i].dequeue();
                    if(v==-1)break;
                    b[v%(d*10)/d].enqueue(v);
                }
            }
        }
        int j=0;
        for(int i=0;i<10;i++){
            while(b[i].length>0){
                a[j++]=b[i].dequeue();
            }
        }
    }

    static boolean visial = false;
    public static void main (String[] args) {
        int numOfData=0;
        boolean SS=false,IS=false,HS=false,QS=false,RS=false,printArray=false;
        for(int i=0;i<args.length;i++){
            if(args[i].equals("-v")) visial = true;
            else if(args[i].equals("s")) SS=true;
            else if(args[i].equals("i")) IS=true;
            else if(args[i].equals("h")) HS=true;
            else if(args[i].equals("q")) QS=true;
            else if(args[i].equals("r")) RS=true;
            else if(args[i].equals("-p")) printArray=true;
            else{
                Scanner scan = new Scanner(System.in);
                numOfData = Integer.parseInt(args[i]);
            }
        }
        if (numOfData > 0) {
            int n = numOfData;
            int[] a = new int[n];
            Random rnd = new Random();
            for (int i = 0; i < n; i++){
                a[i] = rnd.nextInt(n*10); // 0〜配列の大きさ*10 -1 のランダムな整数を要素とする
                if(printArray)System.out.print(a[i]+" ");
            }
            if(printArray)System.out.println();
            long t1 = System.nanoTime();
            if(SS)selection_sort(a,n);
            if(IS)insertion_sort(a,n);
            if(HS)heap_sort(a,n);
            if(QS)qsort(a,n);
            if(RS)radix_sort(a,n,8);
            //heap_sort(a, n);
            ////heap_sort(a, n);
            long t2 = System.nanoTime();
            for(int i=1;i<n;i++){
                if(a[i-1]>a[i])System.out.println("並び替え失敗!!");
            }
            if(printArray){
                for(int i=0;i<n;i++)System.out.print(a[i] + " ");
                System.out.println();
            }
            System.out.println(((t2-t1)/1000000) +"ms comp:"+ compare_count);  // 比較回数を表示
            reset();                                // 比較回数をリセット
        }else{
            for (int j = 1; j <= 10; j++) {  // 大きさn (n=1000, 2000, ..., 10000) の配列に対してテスト
                int n = 1000 * j;
                System.err.println(n);
                int[] a = new int[n];
                Random rnd = new Random();
                for (int i = 0; i < n; i++)
                    a[i] = rnd.nextInt(n*10); // 0〜配列の大きさ*10 -1 のランダムな整数を要素とする
                for(int i=0;i < n;i++) a[i] = i;
                
                //heap_sort(a, n);
                //qsort(a, n);
                if(SS)selection_sort(a,n);
                if(IS)insertion_sort(a,n);
                if(HS)heap_sort(a,n);
                if(QS)qsort(a,n);
                if(RS)radix_sort(a,n,8);
                for(int i=1;i<n;i++){
                    if(a[i-1]>a[i])System.err.println("並び替え失敗!!");
                }
                System.out.print(n);
                System.out.println(" "+compare_count);  // 比較回数を表示
                reset();                                // 比較回数をリセット
            }
        }
    }
}
