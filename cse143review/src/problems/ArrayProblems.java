package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {

        StringBuilder buildArray = new StringBuilder();

        buildArray.append("[");
        for (int i = 0; i < array.length; i++) {
           if (i < array.length - 1) {
               buildArray.append(array[i] + ", ");
           } else {
               buildArray.append(array[i]);
           }
        }
        buildArray.append("]");
        return buildArray.toString();

    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {

        int[] reversedArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            reversedArray[array.length - 1 - i] = array[i];
        }
        return reversedArray;

    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {

        if (array.length == 0) {
            return;
        }

        int lastElement = array[0];
        int currElement = 0;
        for (int i = 1; i < array.length; i++) {
            currElement = array[i];
            array[i] = lastElement;
            lastElement = currElement;
        }

        array[0] = lastElement;

    }
}
