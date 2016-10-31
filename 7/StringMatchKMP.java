import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

class StringMatchKMP {
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
        for (int i = 0; i < text.length - pat.length; i++) {
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

    /**
     * KMP法のためのまくらパターン算出．
     * @param pat 検索パターン
     * @return まくらパターンの配列．
     */
    public static int[] compnext(char[] pat) {
        int i = 0, j = 0;
        int[] result = new int[pat.length];

        for (i = 1; i < pat.length; i++) {
            for (j = 0; i + j < pat.length; j++) {
                if (!cmp(pat[i + j], pat[j])) {
                    result[i + j] = Math.max(result[i + j], j + 1);
                    break;
                }
            }
        }

        if (isVerbose) {
            System.out.print("Next:");
            for (i = 0; i < pat.length; i++)
                System.out.print(" " + result[i]);
            System.out.println();
        }

        return result;
    }

    /**
     * KMP法による文字列検出
     * @param text テキスト
     * @param pat 検索パターン
     * @return 照合に成功した位置．失敗した場合は -1．
     */
    public static int kmp(char[] text, char[] pat) {
        int i = 0, m = 0;
        int next[] = compnext(pat);

        while (i + m < text.length) {
            if (cmp(text[i + m], pat[i])) {
                i += 1;
                if (i == pat.length)
                    return m;
            } else {
                m += i - next[i] + 1;
                i = next[i];
            }
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

        System.out.println("Pattern found at " + kmp(text, pat) + ".");
        if (isVerbose)
            System.out.println("# of comparisons: " + Ncmp + ".");
    }
}
