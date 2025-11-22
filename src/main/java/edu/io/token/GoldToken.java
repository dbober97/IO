package edu.io.token;

public class GoldToken extends Token {

    private final double amount;

    public double amount()
    {
        return amount;
    }

    public GoldToken() {
        this(1);
    }

    public GoldToken(double a)
    {
        super(Label.GOLD_TOKEN_LABEL);
        if(a < 0) throw new IllegalArgumentException("Positive value is required!");
        this.amount = a;
    }
}
