package levenshteindistance;

import java.io.*;
import java.util.*;

public class LevenshteinDistance {

    static Map<String, HashSet<String>> myMap = new HashMap();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("dictionary words.txt"));
        HashSet<String> hs = new HashSet();
        String text = "";

        while (text != null) {
            text = br.readLine();
            if (text == null) {
                break;
            }
            hs.add(text);
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Starting Word: ");
        String start = sc.nextLine();
        System.out.println("Ending Word: ");
        String end = sc.nextLine();

        for (String a : hs) {
            ArrayList<String> list = deletion(a);
            list.addAll(insertion(a));
            list.addAll(substitution(a));
            for (String b : list) {
                if (hs.contains(b)) {
                    if (myMap.containsKey(a) == true) {
                        myMap.get(a).add(b);
                    } else {
                        myMap.put(a, new HashSet<String>());
                        myMap.get(a).add(b);
                    }
                }
            }

        }
        if (getSteps(start, end) != -1) {
            System.out.println(getSteps(start, end));
        }
        if (getSteps(start, end) == -1) {
            System.out.println("No Path Found");
        }
    }

    public static ArrayList<String> deletion(String a) {
        String temp = a;
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < a.length(); i++) {//deletion
            StringBuffer sb = new StringBuffer(a);
            sb.deleteCharAt(i);
            list.add(sb.toString());
            a = temp;
        }
        return list;
    }

    public static ArrayList<String> insertion(String a) {
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < a.length() + 1; i++) {//insertion
            for (int j = 97; j < 123; j++) {
                StringBuilder str = new StringBuilder(a);
                str.insert(i, Character.toString((char) j));
                list.add(str.toString());
            }
        }
        return list;
    }

    public static ArrayList<String> substitution(String a) {
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < a.length(); i++) {
            for (int j = 97; j < 123; j++) {
                StringBuilder str = new StringBuilder(a);
                str.setCharAt(i, (char) j);
                if (!str.toString().equals(a)) {
                    list.add(str.toString());
                }
            }
        }
        return list;
    }

    public static int getSteps(String start, String end) {

        LinkedList<WordStep> ll = new LinkedList();
        ll.addFirst(new WordStep(0, start));
        Set<String> seen = new HashSet<>();
        while (ll.size() != 0) {
            WordStep wordstep = ll.getFirst();
            ll.removeFirst();
            if (wordstep.word.equals(end)) {
                return wordstep.step;
            }
            HashSet<String> hset = myMap.get(wordstep.word);
            if (hset != null) {
                for (String s : hset) {
                    if (!seen.contains(s)) {
                        ll.addLast(new WordStep(wordstep.step + 1, s));
                        seen.add(s);
                    }
                }
            }
        }
        return -1;
    }
}

class WordStep {

    int step;
    String word;

    public WordStep(int step, String word) {
        this.step = step;
        this.word = word;
    }
}



