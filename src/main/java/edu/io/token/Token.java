package edu.io.token;

abstract public class Token {

    private final String label;

    public Token(String label)
    {
        this.label=label;
    }

    public String label() {
        return label;
    }
}

