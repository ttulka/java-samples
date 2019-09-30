import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

class StringPermutations {

    public static Set<String> permutations(String str) {
        if (str.length() < 2) {
            return Collections.singleton(str);
        }
        final Set<String> res = new TreeSet<>(); // TreeSet used to show the progress of permuting

        for (int i = 0; i < str.length(); i++) {
            char prefix = str.charAt(i);
            for (String s : permutations(str.substring(0, i) + str.substring(i + 1))) {
                res.add(prefix + s);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(permutations(""));
        System.out.println(permutations("a"));
        System.out.println(permutations("ab"));
        System.out.println(permutations("abc"));
        System.out.println(permutations("abcd"));
    }
}
