package win.doyto.i18n.util;

import org.apache.commons.lang3.StringUtils;

/**
 * CommonUtil
 *
 * @author f0rb on 2017-03-30.
 */
public class CommonUtil {
    public static String concatWithUnderline(String... args) {
        return StringUtils.join(args, '_');
    }
}
