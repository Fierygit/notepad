package basic.util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;

/**
 * @author yangxf
 */
public final class JvmUtil {
    private JvmUtil() {
        throw new InstantiationError("JvmUtil can't be instantiated");
    }

    public static int getThreadCount() {
        return THREAD.getThreadCount();
    }

    public static int getPeakThreadCount() {
        return THREAD.getPeakThreadCount();
    }

    public static long getTotalStartedThreadCount() {
        return THREAD.getTotalStartedThreadCount();
    }

    public static int getDaemonThreadCount() {
        return THREAD.getDaemonThreadCount();
    }

    public static long getHeapUsed() {
        return MEMORY.getHeapMemoryUsage().getUsed();
    }

    public static long getHeapCommitted() {
        return MEMORY.getHeapMemoryUsage().getCommitted();
    }

    public static long getHeapMax() {
        return MEMORY.getHeapMemoryUsage().getMax();
    }

    public static long getNonHeapUsed() {
        return MEMORY.getNonHeapMemoryUsage().getUsed();
    }

    public static long getNonHeapCommitted() {
        return MEMORY.getNonHeapMemoryUsage().getCommitted();
    }

    public static long getNonHeapMax() {
        return MEMORY.getNonHeapMemoryUsage().getMax();
    }

    private static final ThreadMXBean THREAD = ManagementFactory.getThreadMXBean();
    private static final MemoryMXBean MEMORY = ManagementFactory.getMemoryMXBean();

    public static void main(String[] args) {
        System.out.println("getThreadCount: " + getThreadCount());
        System.out.println("getPeakThreadCount: " + getPeakThreadCount());
        System.out.println("getTotalStartedThreadCount: " + getTotalStartedThreadCount());
        System.out.println("getDaemonThreadCount: " + getDaemonThreadCount());
        System.out.println("getHeapUsed: " + getHeapUsed());
        System.out.println("getHeapCommitted: " + getHeapCommitted());
        System.out.println("getHeapMax: " + getHeapMax());
        System.out.println("getNonHeapUsed: " + getNonHeapUsed());
        System.out.println("getNonHeapCommitted: " + getNonHeapCommitted());
        System.out.println("getNonHeapMax: " + getNonHeapMax());

    }
}
