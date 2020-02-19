package test.taylor.com.taylorcode.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
    /**
     * cast Object to List<T>
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
        }
        return result;
    }
}
