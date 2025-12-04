package edu.io.token;

public interface Tool {

    public Tool useWith(Token token);
    public Tool ifWorking(Runnable r);
    public Tool ifBroken(Runnable r);
    public Tool ifIdle(Runnable r);

    public boolean isBroken();

}
