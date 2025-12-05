package edu.io.token;

public class PickaxeToken extends Token implements Tool, Repairable{

    private final double gainFactor;
    private int durability;
    private final int initialDurability;//zmienna przechowuje początkowe durability, które można przywrócić w razie naprawy

    private enum State { WORKING, BROKEN, IDLE }
    private State lastState;

    public PickaxeToken()
    {
        this(1.5, 3);
    }

    public PickaxeToken(double gainFactor)
    {
        this(gainFactor, 3);
    }

    public PickaxeToken(double gainFactor, int durability) {
        super(Label.PICKAXE_TOKEN_LABEL);
        if (durability <= 0) throw new IllegalArgumentException("Durability cannot be negative!");
        else if(gainFactor <= 0) throw new IllegalArgumentException("GainFactor cannot be negative!");
        else {

            this.gainFactor = gainFactor;
            this.durability = durability;
            this.initialDurability = durability;
            this.lastState = State.IDLE;
        }
    }

    public double gainFactor()
    {
        return gainFactor;
    }

    public double durability()
    {
        return durability;
    }

    public double initialDurability()
    {
        return initialDurability;
    }

    public void use()
    {
        durability -= 1;
    }

    @Override
    public boolean isBroken()
    {
        return durability <= 0;
    }

    @Override
    public Tool useWith(Token token)
    {
        if(token instanceof GoldToken) {
            if(isBroken()) lastState = State.BROKEN;
            else {
                lastState = State.WORKING;
            }
        }
        else lastState = State.IDLE;
        return this;
    }

    @Override
    public Tool ifWorking(Runnable r)
    {
        if(lastState == State.WORKING) {
            r.run();
            use();
        }
        return this;
    }

    @Override
    public Tool ifBroken(Runnable r)
    {
        if(lastState == State.BROKEN) r.run();
        return this;
    }

    @Override
    public Tool ifIdle(Runnable r)
    {
        if(lastState == State.IDLE) r.run();
        return this;
    }

    public void repair()
    {
        durability = initialDurability;
    }
}
