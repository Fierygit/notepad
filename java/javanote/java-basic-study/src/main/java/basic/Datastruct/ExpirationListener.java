package basic.Datastruct;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/5/6 0:16
 */

public interface ExpirationListener<E> {

    /**
     * Invoking when a expired event occurs.
     *
     * @param expiredObject
     */
    void expired(E expiredObject);

}
