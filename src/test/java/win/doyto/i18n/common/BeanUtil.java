package win.doyto.i18n.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * BeanUtil
 *
 * @author f0rb
 */
public class BeanUtil {
    private BeanUtil() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T loadJsonData(String path, TypeReference<T> typeReference) throws IOException {
       return loadJsonData(typeReference.getClass().getResourceAsStream(path), typeReference);
    }
    public static <T> T loadJsonData(InputStream resourceAsStream, TypeReference<T> typeReference) throws IOException {
       return objectMapper.readValue(resourceAsStream, typeReference);
    }
}
