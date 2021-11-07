package main.java.org.didierdominguez.sleepingbarber;

public class Barber extends Thread {
    protected Barbershop barbershop;

    public Barber(Barbershop barbershop) {
        this.barbershop = barbershop;
    }

    @Override
    public void run() {
        while (true) {
            this.barbershop.toSleep();
            this.barbershop.cutHair();
        }
    }
}
