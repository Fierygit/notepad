package basic.util;

/**
 * @author yangxf
 */
@FunctionalInterface
public interface SegmentCounterFactory {
    SegmentCounter newCounter();
}
