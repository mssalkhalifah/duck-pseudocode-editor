package utils;

import java.util.Iterator;
import java.util.Map;

public class PrettyPrintingMap {
    private PrettyPrintingMap() {}

    public static <K, V> String getMapString(Map<K, V> map) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();
            stringBuilder.append(entry.getKey());
            stringBuilder.append('=').append('"');
            stringBuilder.append(entry.getValue());
            stringBuilder.append('"');
            if (iterator.hasNext()) {
                stringBuilder.append(',').append('\n');
            }
        }

        return stringBuilder.toString();
    }
}
