package edu.io.player;

import edu.io.token.Token;
import edu.io.token.Tool;

public class NoTool implements Tool {

    @Override
    public Tool useWith(Token token)
    {
        return this;
    }

    @Override
    public Tool ifWorking(Runnable r)
    {
        return this;
    }

    @Override
    public Tool ifBroken(Runnable r)
    {
        return this;
    }

    @Override
    public  Tool ifIdle(Runnable r)
    {
        return this;
    }

    @Override
    public boolean isBroken()
    {
        return true;
    }
}
