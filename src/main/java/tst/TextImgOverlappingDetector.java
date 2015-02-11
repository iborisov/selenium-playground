package tst;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class TextImgOverlappingDetector {
    private TextImgOverlappingDetector() {
        throw new RuntimeException("You don't need to construct this.");
    }

    private static final String JS_TEXT_AND_IMG_NODES_RECTS = readFileAsString("textAndImgNodesRects.js.txt");
    private static final NumberDblValComparator NUMBER_DBL_VAL_COMPARATOR = new NumberDblValComparator();
    public static final String TEXT_RECTS_KEY = "textRects";
    public static final String IMG_RECTS_KEY = "imgRects";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_LEFT = "left";
    public static final String KEY_BOTTOM = "bottom";
    public static final String KEY_RIGHT = "right";
    public static final String KEY_X = "x";
    public static final String KEY_Y = "y";
    public static final String KEY_WIDTH = "width";
    public static final String KEY_TOP = "top";

    public static Map<String, Number> createRect(final Number top, final Number left,
                                                 final Number bottom, final Number right) {
        return ImmutableMap.of(KEY_TOP, top, KEY_LEFT, left, KEY_BOTTOM, bottom, KEY_RIGHT, right);
    }

    public static int detect(final JavascriptExecutor jsExecutor) {
        int result = 0;

        @SuppressWarnings("unchecked")
        final Map<String, List<Map<String, Number>>> jsRes = (Map<String, List<Map<String, Number>>>) jsExecutor.executeScript(JS_TEXT_AND_IMG_NODES_RECTS);
        // TODO: implement.

        return result;
    }

    /**
     * @param filename file name.
     * @return content of file with given name, or empty string if IOException occurred.
     */
    private static String readFileAsString(final String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }

    public static boolean areRectsOverlap(final Map<String, Number> r1,
                                          final Map<String, Number> r2) {
        return !(greater(r2.get(KEY_LEFT), r1.get(KEY_RIGHT))
                || less(r2.get(KEY_RIGHT), r1.get(KEY_LEFT))
                || greater(r2.get(KEY_TOP), r1.get(KEY_BOTTOM))
                || less(r2.get(KEY_BOTTOM), r1.get(KEY_TOP)));
    }

    public static boolean less(final Number n1, final Number n2) {
        return NUMBER_DBL_VAL_COMPARATOR.compare(n1, n2) < 0;
    }

    public static boolean greater(final Number n1, final Number n2) {
        return NUMBER_DBL_VAL_COMPARATOR.compare(n1, n2) > 0;
    }

    public static boolean lessOrEqual(final Number n1, final Number n2) {
        return NUMBER_DBL_VAL_COMPARATOR.compare(n1, n2) <= 0;
    }

    public static class NumberDblValComparator implements Comparator<Number> {
        @Override
        public int compare(Number n1, Number n2) {
            final double n1v = n1.doubleValue(), n2v = n2.doubleValue();
            int res = 0;
            if (n1v > n2v) {
                res = 1;
            } else if (n1v < n2v) {
                res = -1;
            }
            return res;
        }
    }
}
