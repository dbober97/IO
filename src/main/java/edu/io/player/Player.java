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

    private void usePickaxeOnGold(PickaxeToken p, GoldToken g)
    {
        if (p.isBroken()) {
            shed.dropTool();
            gold.gain(g.amount());
        } else {
            gold.gain(g.amount() * p.gainFactor());
            p.use();
        }
    }

    private void useSluiceboxOnGold(SluiceboxToken s, GoldToken g)
    {
        if (s.isBroken()) {
            shed.dropTool();
            gold.gain(g.amount());
        } else {
            gold.gain(g.amount() * s.gainFactor());
            s.use();
        }
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
                System.out.println("GOLD! (value " + g.amount() + ")");
                Tool tl = shed.getTool();
                if (tl instanceof PickaxeToken p) usePickaxeOnGold(p, g);
                else if (tl instanceof SluiceboxToken s) useSluiceboxOnGold(s, g);
                else this.gold.gain(g.amount());
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
