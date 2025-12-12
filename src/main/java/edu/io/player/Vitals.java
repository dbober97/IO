package edu.io.player;

import java.util.Objects;

public class Vitals {
    private int hydration;
    private Runnable onDeathCallback;

    public Vitals()
    {
       hydration = 100;
        onDeathCallback = () -> {};
    }

    public int hydration()
    {
        return hydration;
    }

    public void hydrate(int amount)
    {
        if(amount >= 0)
        {
            if(amount + hydration >= 100) hydration = 100;
            else hydration += amount;
        }
        else throw new IllegalArgumentException("Amount cannot be negative!");
    }

    public void dehydrate(int amount)
    {
        if(amount >= 0)
        {
            if(hydration - amount <= 0) hydration = 0;
            else hydration -= amount;
        }
        else throw new IllegalArgumentException("Amount cannot be negative!");
        if(!isAlive()) onDeathCallback.run();
    }

    public boolean isAlive()
    {
        return hydration > 0;
    }


    public void setOnDeathHandler(Runnable callback)
    {
        onDeathCallback = Objects.requireNonNull(callback,
                "callback cannot be null");
    }




}
