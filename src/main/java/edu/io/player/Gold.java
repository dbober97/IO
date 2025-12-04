package edu.io.player;

public class Gold {

    private double amount;

    public Gold(double amount)
    {
        if(amount < 0) throw new IllegalArgumentException("Gold amount cannot be negative!");
        this.amount = amount;
    }

    public Gold()
    {
        this(0);
    }

    public double amount()
    {
        return amount;
    }

    public void gain(double g)
    {
        if(g < 0) throw new IllegalArgumentException("Positive value is required!");
        amount += g;
    }

    public void lose(double g)
    {
        if(g < 0) throw new IllegalArgumentException("Positive value is required!");
        amount -= g;
        if(amount < 0) {
            amount = 0;
            throw new IllegalArgumentException("Positive value is required!");
        }
    }
}
