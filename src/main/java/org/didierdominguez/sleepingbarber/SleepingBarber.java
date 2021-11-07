package main.java.org.didierdominguez.sleepingbarber;

import javax.swing.*;

public class SleepingBarber extends JFrame {
    private JPanel mainPanel;

    private JTextField arrivalFrequencyField;
    private JTextField haircutTimeField;
    private JButton applyChanges;
    private JLabel lbApplyingChanges;

    private JLabel lbArrived;
    private JLabel lbWithoutHaircut;
    private JLabel lbCompletedHaircut;
    private JLabel lbWaitingRoom;

    private JPanel seat1;
    private JPanel seat2;
    private JPanel seat3;
    private JPanel seat4;
    private JPanel seat5;
    private JPanel seat6;
    private JPanel seat7;
    private JPanel seat8;
    private JPanel seat9;
    private JPanel seat10;
    private JPanel seat11;
    private JPanel seat12;
    private JPanel seat13;
    private JPanel seat14;
    private JPanel seat15;
    private JPanel seat16;
    private JPanel seat17;
    private JPanel seat18;
    private JPanel seat19;
    private JPanel seat20;

    private JLabel lbImage1;
    private JLabel lblImage2;
    private JButton btnPlay;
    private JButton btnPause;
    private JButton btnResume;

    public SleepingBarber() {
        super();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setTitle("Sleeping Barber Problem");
        this.setResizable(false);

        JPanel[] seats = new JPanel[] { seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10, seat11,
                seat12, seat13, seat14, seat15, seat16, seat17, seat18, seat19, seat20 };

        Barbershop barbershop = new Barbershop(seats, lbImage1, lblImage2, lbArrived, lbWithoutHaircut,
                lbCompletedHaircut, lbWaitingRoom);

        applyChanges.addActionListener(e -> {
            Integer arrivalFrequency = Integer.parseInt(arrivalFrequencyField.getText());
            Integer haircutTime = Integer.parseInt(haircutTimeField.getText());
            barbershop.settings(arrivalFrequency, haircutTime, lbApplyingChanges);
        });

        btnPlay.addActionListener(e -> {
            barbershop.start();
            btnPause.setVisible(true);
            btnPlay.setVisible(false);
        });

        btnPause.addActionListener(e -> {
            barbershop.suspend();
            btnResume.setVisible(true);
            btnPause.setVisible(false);
        });

        btnResume.addActionListener(e -> {
            barbershop.resume();
            btnPause.setVisible(true);
            btnResume.setVisible(false);
        });
    }

    public static void main(String[] arg) {
        java.awt.EventQueue.invokeLater(() -> new SleepingBarber().setVisible(true));
    }
}
