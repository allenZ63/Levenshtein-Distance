package levenshteindistance;

import java.io.*;
import java.util.*;

public class LevenshteinDistance {

    static Map<String, HashSet<String>> myMap = new HashMap();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("dictionary words.txt"));
        ArrayList<String> list = new ArrayList();
        String text = "";

        while (text != null) {
            text = br.readLine();
            if (text == null) {
                break;
            }
            list.add(text);
        }

        String start = "aa";
        String end = "aahs";

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (isEditDistanceOne(list.get(i), list.get(j)) == true) {
                    if (myMap.containsKey(list.get(i)) == true) {
                        myMap.get(list.get(i)).add(list.get(j));
                    } else {
                        myMap.put(list.get(i), new HashSet<String>());
                        myMap.get(list.get(i)).add(list.get(j));
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

    public static boolean isEditDistanceOne(String a, String b) {
        boolean test = false;
        if (Math.abs(a.length() - b.length()) >= 2) {//length distance 2 or larger
            return false;
        }
        if (Math.abs(a.length() - b.length()) == 1) {//length distance 1
            String big = "";
            String small = "";
            String temp = "";
            if (a.length() > b.length()) {
                big = a;
                small = b;
                temp = big;
            }
            if (a.length() < b.length()) {
                big = b;
                small = a;
                temp = big;
            }
            for (int i = 0; i < big.length(); i++) {
                StringBuffer sb = new StringBuffer(big);
                sb.deleteCharAt(i);
                if (sb.toString().equals(small)) {
                    return true;
                }
                big = temp;
            }
        }
        if (a.length() == b.length()) {//same length
            int count = 0;
            for (int i = 1; i < a.length(); i++) {
                if (a.charAt(i) != b.charAt(i)) {
                    count++;
                }
            }
            if (count >= 2) {
                return false;
            } else {
                return true;
            }
        }
        return false;
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
            for (String s : myMap.get(wordstep.word)) {
                if (!seen.contains(s)) {
                    ll.addLast(new WordStep(wordstep.step + 1, s));
                    seen.add(s);
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



