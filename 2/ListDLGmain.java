public class ListDLGmain {
    public static void main(String[] args) {
        testInteger();
        testString();
    }

    // Integer型のListDLGenericsについてテスト
    public static void testInteger() {
        ListDLGenerics<Integer> head = new ListDLGenerics<Integer>();
        ListDLGenerics<Integer> elem;

        head.insertNext(new ListDLGenerics<Integer>(new Integer(2)));
        head.insertNext(new ListDLGenerics<Integer>(new Integer(1)));
        head.insertPrev(new ListDLGenerics<Integer>(new Integer(5)));
        head.display();

        elem = head.search(new Integer(2));
        elem.insertNext(new ListDLGenerics<Integer>(new Integer(3)));
        head.display();

        elem = head.search(new Integer(5));
        elem.delete();
        head.display();
    }

    // String型のListDLGenericsについてテスト
    public static void testString() {
        ListDLGenerics<String> head = new ListDLGenerics<String>();
        ListDLGenerics<String> elem;

        head.insertNext(new ListDLGenerics<String>("abc"));
        head.insertNext(new ListDLGenerics<String>("def"));
        head.insertPrev(new ListDLGenerics<String>("ghi"));
        head.display();

        elem = head.search("abc");
        elem.insertNext(new ListDLGenerics<String>("jkl"));
        head.display();

        elem = head.search("ghi");
        elem.delete();
        head.display();
    }
}
