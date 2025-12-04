package edu.io.token;

public class SluiceboxToken extends Token implements Tool{

    private double gainFactor;
    private int durability;
    private enum State { WORKING, BROKEN, IDLE }
    private State lastState = State.IDLE;

    public SluiceboxToken()
    {
        super(Label.SLUICEBOX_TOKEN_LABEL);
        gainFactor = 1.2;
        durability = 5;
    }

    public double gainFactor()
    {
        return gainFactor;
    }

    public int durability()
    {
        return durability;
    }

    public void use()
    {
        durability -= 1;
        switch(durability){
            case 4: {gainFactor = Double.parseDouble("1.16"); break;}
            case 3: {gainFactor = Double.parseDouble("1.12"); break; }
            case 2: {gainFactor = Double.parseDouble("1.08"); break; }
            case 1: {gainFactor = Double.parseDouble("1.04"); break; }
        };
    }

    @Override
    public Tool useWith(Token token)
    {
        if(token instanceof GoldToken) {
            if(isBroken()) lastState = State.BROKEN;
            else {
                lastState = State.WORKING;
                durability -= 1;
                switch(durability){
                    case 4: {gainFactor = Double.parseDouble("1.16"); break;}
                    case 3: {gainFactor = Double.parseDouble("1.12"); break; }
                    case 2: {gainFactor = Double.parseDouble("1.08"); break; }
                    case 1: {gainFactor = Double.parseDouble("1.04"); break; }
                };
            }
        }
        else lastState = State.IDLE;
        return this;
    }

    public Tool ifWorking(Runnable r)
    {
        if(lastState == State.WORKING) r.run();
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

    public boolean isBroken()
    {
        return durability <= 0 || gainFactor < 1.04;
    }

}
