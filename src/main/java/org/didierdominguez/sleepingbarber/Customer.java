package main.java.org.didierdominguez.sleepingbarber;

public class Customer extends Thread {
    protected Barbershop barbershop;
    protected int seat;

    public Customer(Barbershop barbershop, int seat) {
        this.barbershop = barbershop;
        this.seat = seat;
    }

    @Override
    public void run() {
        this.barbershop.arrival(this.seat);
    }
}
