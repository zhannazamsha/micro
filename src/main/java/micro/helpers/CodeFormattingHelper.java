package micro.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeFormattingHelper {

    public static int[] extractFirstTwoNumbersFromCode(String code) {
        Pattern pattern = Pattern.compile("(\\+\\d{1,2})");
        Matcher matcher = pattern.matcher(code);
        matcher.find();
        String extractedFirstTwoDigits = matcher.group(1).replace("+", "");

        int first = Integer.parseInt(String.valueOf(extractedFirstTwoDigits.charAt(0)));
        int second = 0;
        if (extractedFirstTwoDigits.length() > 1) {
            second = Integer.parseInt(String.valueOf(extractedFirstTwoDigits.charAt(1)));
        }
        return new int[]{first, second};
    }

    public static String removeSpaces(String number) {
        return number.replaceAll(" ", "");
    }
}
