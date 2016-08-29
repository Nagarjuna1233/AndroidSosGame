package com.sos.nag.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

/**
 * Created by nagarjuna on 29/8/16.
 */
public class WordTesting {
    public TreeSet<String> dic = new TreeSet();
    public TreeSet<String> points_words = new TreeSet();
    String file = null;
    public String meaning_word = "";

    public WordTesting(String string) throws IOException, FileNotFoundException {
//        String string2;
//        this.file = string;
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(string)));
//        while ((string2 = bufferedReader.readLine()) != null) {
//            this.dic.add(string2);
//        }
        dic.add("in");
        dic.add("a");
        dic.add("an");
        dic.add("and");
        dic.add("to");
        dic.add("put");
        dic.add("key");
    }

    public boolean forwardTesting(String string) {
        boolean bl = false;
        String string2 = "";
        block0 : for (int i = 0; i < string.length() - 1 && !bl; ++i) {
            char c = string.charAt(i);
            string2 = String.valueOf(c);
            for (int j = i + 1; j < string.length(); ++j) {
                char c2 = string.charAt(j);
                if (this.points_words.contains(string2 = string2 + String.valueOf(c2)) || !this.dic.contains(string2)) continue;
                this.meaning_word = string2;
                this.points_words.add(string2);
                bl = true;
                continue block0;
            }
        }
        return bl;
    }
}
