package edu.io;

import edu.io.token.GoldToken;
import edu.io.token.PlayerToken;
import edu.io.token.Token;

public class Player {
    private PlayerToken pToken = null;
    private double gold = 0;

    public void assignToken(PlayerToken pToken)
    {
        this.pToken = pToken;
    }

    public PlayerToken token()
    {
        return pToken;
    }

    public double gold()
    {
        return this.gold;
    }

    public void gainGold(double g)
    {
        if(g < 0) throw new IllegalArgumentException("Positive value is required!");
        this.gold += g;
    }

    public void loseGold(double g)
    {
        if(g < 0) throw new IllegalArgumentException("Positive value is required!");
        this.gold -= g;
        if(this.gold < 0) {
            this.gold = 0;
            throw new IllegalArgumentException("Positive value is required!");
        }
    }

    public void interactWithToken(Token token)
    {
        if(token instanceof GoldToken g)
        {
            System.out.println("GOLD!");
            this.gainGold(g.amount());
        }
    }
}
