import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

class StringMatch {
    static boolean isVerbose = false;	// 比較回数表示スイッチ
    static long Ncmp = 0;		// 比較回数のカウンタ

    static char[] text;	// テキスト
    static char[] pat;	// 検索パターン

    /**
     * ファイルを読み込み，内容を文字列として返す．
     * @param path 読み込むファイルのパス
     * @return ファイルの内容の文字列
     */
    public static String readFile(String path) {
        StringBuffer sb = new StringBuffer();
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null)
                sb.append(str);
            br.close();
        } catch(Exception e){
            System.out.println(e);
        }
        return new String(sb);
    }

    /**
     * 文字比較関数．比較回数をカウントする．
     * @param a 比較文字
     * @param b 比較文字
     * @return a と b が等しければ true, 等しくなければ false.
     */
    public static boolean cmp(char a, char b) {
        if (isVerbose)
            System.out.println("cmp(" + a + ", " + b + ")");
        Ncmp++;
        if (a == b)
            return true;
        else
            return false;
    }

    /**
     * 単純照合法による文字列照合．
     * @param text テキスト
     * @param pat 検索パターン
     * @return 照合に成功した位置．失敗した場合は -1．
     */
    public static int naive(char[] text, char[] pat) {
        for (int i = 0; i <= text.length - pat.length; i++) {
            boolean flag = true;

            for (int j = 0; j < pat.length; j++) {
                if (!cmp(text[i + j], pat[j])) {
                    flag = false;
                    break;
                }
            }

            if (flag)
                return i;
        }

        return -1;
    }

    public static void main(String[] args) {
        // 引数処理
        if (args.length < 2) {
            System.err.println("Usage: java StringMatch [-v] <text file> <pattern file>");
            System.exit(1);
        }
        int i = 0;
        if (args[i].equals("-v")) {
            isVerbose = true;
            i++;
        }

        // ファイルを読み込んで，char型の配列に格納．
        text = readFile(args[i++]).toCharArray();
        pat = readFile(args[i]).toCharArray();

        if (isVerbose) {
            System.out.println("text size: " + text.length);
            System.out.println("pattern size: " + pat.length);
        }

        System.out.println("Pattern found at " + naive(text, pat) + ".");
        if (isVerbose)
            System.out.println("# of comparisons: " + Ncmp + ".");
    }
}
