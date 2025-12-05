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

    private void useToolOnGold(Tool tl, GoldToken g)
    {
        tl.useWith(g)
                .ifWorking(() -> {
                    if(tl instanceof PickaxeToken pt) {
                        gold.gain(g.amount() * pt.gainFactor());
                    }
                    if(tl instanceof SluiceboxToken st) {
                        gold.gain(g.amount() * st.gainFactor());
                    }
                })
                .ifBroken(() -> {
                    shed.dropTool();
                    gold.gain(g.amount());
                })
                .ifIdle(() -> this.gold.gain(g.amount()));
    }

    private void usePickaxeOnGold(PickaxeToken p, GoldToken g)
    {
        useToolOnGold(p, g);
    }

    private void useSluiceboxOnGold(SluiceboxToken s, GoldToken g)
    {
        useToolOnGold(s, g);
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
                if(tl instanceof PickaxeToken pt) usePickaxeOnGold(pt, g);
                else if (tl instanceof SluiceboxToken st) useSluiceboxOnGold(st, g);
                else gold.gain(g.amount());
            }
            case AnvilToken a-> {
                if(shed.getTool() instanceof Repairable tool) tool.repair();//próbuje naprawić tylko ostatnie narzędzie, nie ważne czy się da
            }
            default -> {}
        }
    }
}
