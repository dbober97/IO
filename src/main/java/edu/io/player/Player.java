package edu.io.player;

import edu.io.token.*;

public class Player {
    private PlayerToken pToken = null;
    public final Gold gold = new Gold();
    public final Shed shed = new Shed();
    public final Vitals vitals = new Vitals();

    public void assignToken(PlayerToken pToken)
    {
        if(pToken == null) throw new NullPointerException("Token cannot be null!");
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
        if(!vitals.isAlive()) throw new IllegalStateException("You are dead.");
        switch (token)
        {
            case Tool tl-> {
                shed.add(tl);
            }
            case GoldToken g-> {
                vitals.dehydrate(VitalsValues.DEHYDRATION_GOLD);
                System.out.println("GOLD! (value " + g.amount() + ")");
                Tool tl = shed.getTool();
                if(tl instanceof PickaxeToken pt) usePickaxeOnGold(pt, g);
                else if (tl instanceof SluiceboxToken st) useSluiceboxOnGold(st, g);
                else gold.gain(g.amount());
            }
            case AnvilToken a-> {
                vitals.dehydrate(VitalsValues.DEHYDRATION_ANVIL);
                if(shed.getTool() instanceof Repairable tool) tool.repair();//próbuje naprawić tylko ostatnie narzędzie, nie ważne czy się da
            }
            case WaterToken w->{
                vitals.hydrate(w.amount());
            }
            default -> {vitals.dehydrate(VitalsValues.DEHYDRATION_MOVE);}
        }
    }
}
