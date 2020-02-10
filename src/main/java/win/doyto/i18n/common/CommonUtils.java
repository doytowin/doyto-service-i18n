package win.doyto.i18n.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CommonUtils
 *
 * @author f0rb on 2020-02-10
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {
    private static final Pattern PTN_$EX = Pattern.compile("\\$\\{(\\w+)}");

    public static String replaceHolderEx(String input, Map<String, String> params) {
        Matcher matcher = PTN_$EX.matcher(input);
        if (!matcher.find()) {
            return input;
        }

        StringBuffer sb = new StringBuffer();
        do {
            String fieldName = matcher.group(1);
            String replacement = String.valueOf(params.getOrDefault(fieldName, ""));
            replacement = StringUtils.remove(replacement, ' ');
            matcher.appendReplacement(sb, replacement);
        } while (matcher.find());
        return matcher.appendTail(sb).toString();
    }
}
