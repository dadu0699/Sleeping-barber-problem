package main.java.org.didierdominguez.sleepingbarber;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Barbershop extends Thread {
    protected JPanel[] seats;
    protected LinkedList<Integer> numberOfSeats;

    protected JLabel lbBarberAwake;
    protected JLabel lbSleepingBarber;
    protected Boolean barberCuttingHair;
    protected Boolean sleepingBarber;

    protected JLabel lbArrived;
    protected JLabel lbWithoutHaircut;
    protected JLabel lbCompletedHaircut;
    protected JLabel lbWaitingRoom;

    protected Integer arrived;
    protected Integer withoutHaircut;
    protected Integer completedHaircut;
    protected Integer waitingRoom;

    protected Integer arrivalFrequency;
    protected Integer haircutTime;

    protected Integer repainted;

    public Barbershop(JPanel[] seats, JLabel lbBarberAwake, JLabel lbSleepingBarber, JLabel lbArrived,
            JLabel lbWithoutHaircut, JLabel lbCompletedHaircut, JLabel lbWaitingRoom) {
        this.seats = seats;
        this.lbBarberAwake = lbBarberAwake;
        this.lbSleepingBarber = lbSleepingBarber;
        this.lbArrived = lbArrived;
        this.lbWithoutHaircut = lbWithoutHaircut;
        this.lbCompletedHaircut = lbCompletedHaircut;
        this.lbWaitingRoom = lbWaitingRoom;

        this.getNumberOfSeats();
        this.arrivalFrequency = 3000;
        this.haircutTime = 2000;
        this.barberCuttingHair = false;
        this.sleepingBarber = true;

        this.arrived = 0;
        this.withoutHaircut = 0;
        this.completedHaircut = 0;
        this.waitingRoom = 0;

        this.repainted = 0;
    }

    public void getNumberOfSeats() {
        this.numberOfSeats = new LinkedList<>();
        for (int i = 0; i < this.seats.length; i++) {
            this.numberOfSeats.addLast(i);
        }
    }

    public synchronized void arrival(int seat) {
        while (this.barberCuttingHair) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(this.repainted);
            this.seats[seat].setBackground(Color.decode("#00BC7E"));
            this.numberOfSeats.addLast(seat);
            System.out.println("Customer vacating the seat");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.barberCuttingHair = true;

        if (this.sleepingBarber) {
            notifyAll();
        }

        System.out.println("Customer getting haircut");
        while (this.barberCuttingHair) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.barberCuttingHair = false;
        notifyAll();
        System.out.println("Customer leaving");
    }

    public synchronized void toSleep() {
        this.sleepingBarber = true;
        while (!this.barberCuttingHair) {
            this.lbBarberAwake.setVisible(false);
            this.lbSleepingBarber.setVisible(true);
            System.out.println("Sleeping barber");

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.sleepingBarber = false;
        this.lbBarberAwake.setVisible(true);
        this.lbSleepingBarber.setVisible(false);
        System.out.println("Barber cutting hair");
    }

    public synchronized void cutHair() {
        try {
            System.out.println("To cut hair");

            Thread.sleep(this.haircutTime);

            this.barberCuttingHair = false;
            this.completedHaircut++;
            this.lbCompletedHaircut.setText("Completed haircut: " + this.completedHaircut);
            System.out.println("Finished haircut");

            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void settings(Integer arrivalFrequency, Integer haircutTime, JLabel lbApplyingChanges) {
        lbApplyingChanges.setVisible(true);
        try {
            this.suspend();
            this.arrivalFrequency = arrivalFrequency;
            this.haircutTime = haircutTime;
            Thread.sleep(2000);
            this.resume();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lbApplyingChanges.setVisible(false);
        }
    }

    @Override
    public void run() {
        Barber barber = new Barber(this);
        barber.start();
        int i = 0;

        while (true) {
            try {
                Thread.sleep(this.arrivalFrequency);

                this.arrived++;
                this.lbArrived.setText("Arrived: " + this.arrived);
                System.out.println(arrived);

                if (this.numberOfSeats.isEmpty()) {
                    this.withoutHaircut++;
                    this.lbWithoutHaircut.setText("Without a haircut: " + this.withoutHaircut);
                    continue;
                }

                i = this.numberOfSeats.getFirst();
                this.numberOfSeats.removeFirst();

                this.waitingRoom = this.seats.length - this.numberOfSeats.size();
                this.lbWaitingRoom.setText("Waiting room: " + this.waitingRoom);

                Customer customer = new Customer(this, i);
                customer.start();
                this.seats[i].setBackground(Color.decode("#FD397A"));
                System.out.println("Customer sitting in his seat");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
