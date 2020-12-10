package basic.Collection;

import java.time.LocalDate;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/5/25 17:47
 */

public class TestPriorityQueue {


    public static void main(String[] args) {
        Queue<LocalDate> pq = new PriorityQueue<>(2,
                (a, b) -> {
                    if (a.getDayOfMonth() == b.getDayOfMonth()) {
                        return 0;
                    }
                    return a.getDayOfMonth() > b.getDayOfMonth() ? -1 : 1;

                });
        pq.add(LocalDate.of(1906, 12, 9)); // C. Hopper
        pq.add(LocalDate.of(1815, 12, 10)); // A. Lovelace
        pq.add(LocalDate.of(1903, 12, 3)); // ]. von Neumann
        pq.add(LocalDate.of(1910, 6, 2)); // K. Zuse
        System.out.println("Iterating over elements...");
        for (LocalDate date : pq) {
            System.out.println(date);
        }

        System.out.println("Removing elements");
        while (!pq.isEmpty()) {
            System.out.println(pq.remove());
        }
    }
}
