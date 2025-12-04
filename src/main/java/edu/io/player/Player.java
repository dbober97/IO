package edu.io.player;

import edu.io.token.*;

public class Player {
    private PlayerToken pToken = null;
    public final Gold gold = new Gold();
    public final Shed shed = new Shed();

    public void assignToken(PlayerToken pToken)
    {
        this.pToken = pToken;
    }

    public PlayerToken token()
    {
        return pToken;
    }

    public void interactWithToken(Token token)
    {

        switch (token)
        {
            case Tool tl-> {
                shed.add(tl);
            }
            case GoldToken g-> {
                System.out.println("GOLD!");
                Tool tl = shed.getTool();
                if (tl instanceof PickaxeToken p) {
                    if (p.isBroken()) {
                        shed.dropTool();
                        gold.gain(g.amount());
                    } else {
                        gold.gain(g.amount() * p.gainFactor());
                        p.use();
                    }
                } else this.gold.gain(g.amount());
            }
            case AnvilToken a-> {
                if(shed.getTool() instanceof Repairable tool)
                {
                    tool.repair();
                }
            }
            default -> {}
        }
    }
}
