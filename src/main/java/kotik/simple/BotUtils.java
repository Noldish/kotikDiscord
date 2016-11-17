package kotik.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Roman_Kuznetcov on 17.11.2016.
 */
public class BotUtils {

    public static List<String> getCommandParams(String message) {
        String result = message;
        if (result.indexOf(' ') != -1) {
            result = result.substring(result.indexOf(' ')+1, result.length());
        }
        return new ArrayList<String>(Arrays.asList(result.split(";")));
    }

    public static String getCommandKey(String message) {
        String result = message;
        if (result.indexOf(' ') != -1) {
            result = result.substring(0, result.indexOf(' '));
        }
        return result;
    }
}