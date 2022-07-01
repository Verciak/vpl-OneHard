package pl.vertty.nomenhc.helpers;

import java.util.ArrayList;
import org.json.JSONArray;

public class JSONConverter {
    public static ArrayList<Integer> getInt(JSONArray o) {
        ArrayList<Integer> array = new ArrayList<>();
        for (Object ad : o)
            array.add((Integer)ad);
        return array;
    }

    public static ArrayList<String> getString(JSONArray o) {
        ArrayList<String> array = new ArrayList<>();
        for (Object ad : o)
            array.add((String)ad);
        return array;
    }

    public static ArrayList<String> getStringSchowek(JSONArray o, int amount) {
        ArrayList<String> array = new ArrayList<>();
        for (Object ad : o)
            array.add(((String)ad).replace("{AMOUNT}", String.valueOf(amount)));
        return array;
    }

    public static String buildString(JSONArray strings) {
        StringBuilder s = new StringBuilder();
        for (Object s1 : strings)
            s.append((String)s1).append("\n");
        return s.toString();
    }
}

