package micro.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeFormattingHelper {

    public static String extractFirstTwoNumbersFromCode(String code) {
        Pattern pattern = Pattern.compile("(\\+\\d{1,2})");
        Matcher matcher = pattern.matcher(code);
        matcher.find();
        return matcher.group(1).replace("+", "");
    }

    public static String removeSpaces(String number) {
        return number.replaceAll(" ", "");
    }
}
