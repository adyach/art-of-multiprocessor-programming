package chapter_1_1;

/**
 * Stick
 *
 * @author Andrey Dyachkov on 12/06/16 11:45
 */
public class Stick {

    private volatile boolean taken;

    public synchronized boolean take() {
        if (taken) {
            return false;
        }
        this.taken = true;
        return true;
    }

    public synchronized void put() {
        this.taken = false;
    }

    public boolean isTaken() {
        return taken;
    }

}
