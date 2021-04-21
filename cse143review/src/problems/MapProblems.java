package problems;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        // word, occurances
        Map<String, Integer> occurances = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {

            String curr = list.get(i);
            if (occurances.containsKey(curr)) {
                occurances.put(curr, occurances.get(curr) + 1);
                if (occurances.get(curr) == 3) {
                    return true;
                }
            }  else {
                occurances.put(curr, 1);
            }

        }

        return false;

    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {

        Map<String, Integer> intersection = new HashMap<>();
        for (String key : m1.keySet()) {

            if (m2.containsKey(key) && m2.get(key) == m1.get(key)) {
                intersection.put(key, m1.get(key));
            }

        }

        return intersection;
    }
}
