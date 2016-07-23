package chapter_1_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Buffet
 *
 * @author Andrey Dyachkov on 16/05/16 23:25
 */
public class Buffet {

    private static final int PHILOSOPHERS_NUMBER = 5;
    private static final long WORKING_TIME = TimeUnit.SECONDS.toMillis(3);

    private final List<Philosopher> philosophers = new ArrayList(PHILOSOPHERS_NUMBER);

    public static void main(String[] args) throws InterruptedException {
        Buffet buffet = new Buffet();
        List<Stick> sticks = initSticks();
        startPhilosophers(buffet, sticks);
        Thread.sleep(WORKING_TIME);
        stopPhilosophers(buffet.philosophers);
    }

    private static List<Stick> initSticks() {
        List<Stick> sticks = new ArrayList(PHILOSOPHERS_NUMBER);
        for (int i = 0; i < PHILOSOPHERS_NUMBER; i++) {
            sticks.add(new Stick());
        }
        return sticks;
    }

    private static void startPhilosophers(Buffet buffet, List<Stick> sticks) {
        for (int i = 0; i < PHILOSOPHERS_NUMBER; i++) {
            int index = i + 1;
            if (index >= PHILOSOPHERS_NUMBER) {
                index = 0;
            }
            Philosopher philosopher = new Philosopher(i, sticks.get(i), sticks.get(index));
            buffet.philosophers.add(philosopher);
            Philosopher.start(philosopher);
        }
    }

    private static void stopPhilosophers(List<Philosopher> philosophers) {
        for (Philosopher philosopher : philosophers) {
            philosopher.setIsRunning(false);
        }
    }

}
