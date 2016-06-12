package chapter_1_1;

import java.util.Random;

/**
 * Philosopher
 *
 * @author Andrey Dyachkov on 16/05/16 23:25
 */
class Philosopher implements Runnable {

    private static final Random RANDOM = new Random();
    private static final int MAX_SLEEP = 200;
    private final Stick leftStick;
    private final Stick rightStick;
    private int id;
    private int feastingCount;
    private volatile boolean isRunning;

    public Philosopher(int id, Stick leftStick, Stick rightStick) {
        this.id = id;
        this.leftStick = leftStick;
        this.rightStick = rightStick;
        this.isRunning = true;
    }

    public static void start(Philosopher philosopher) {
        new Thread(philosopher).start();
    }

    @Override
    public void run() {
        while (isRunning) {
            wait(RANDOM.nextInt(MAX_SLEEP));
            if (hasBothSticks()) {
                feastingCount++;
                print("is eating ...");
                getRightStick().put();
                getLeftStick().put();
            } else {
                print("is thinking ...");
                if (!getRightStick().take()) {
                    getLeftStick().put();
                }

                if (!getLeftStick().take()) {
                    getRightStick().put();
                }
            }
        }

        print(String.format("had %s feastings", feastingCount));
    }

    private boolean hasBothSticks() {
        return getLeftStick().isTaken() && getRightStick().isTaken();
    }

    private Stick getLeftStick() {
        return leftStick;
    }

    private Stick getRightStick() {
        return rightStick;
    }

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print(String s) {
        System.out.print(String.format("Philosopher #%s %s \n", id, s));
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

}
