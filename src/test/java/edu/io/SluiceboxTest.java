import edu.io.player.Player;
import edu.io.token.EmptyToken;
import edu.io.token.GoldToken;
import edu.io.token.PickaxeToken;
import edu.io.token.SluiceboxToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SluiceboxTokenTest {

    @Test
    void default_values_should_be_correct() {
        SluiceboxToken token = new SluiceboxToken();

        assertEquals(1.2, token.gainFactor());
        assertEquals(5, token.durability());
        assertFalse(token.isBroken());
    }

    @Test
    void useWith_gold_should_decrease_durability_and_update_gain_factor() {
        SluiceboxToken token = new SluiceboxToken();

        token.useWith(new GoldToken())
                .ifWorking(() -> {});// 5 → 4
        assertEquals(4, token.durability());
        assertEquals(1.16, token.gainFactor());

        token.useWith(new GoldToken())
                .ifWorking(() -> {});// 4 → 3
        assertEquals(3, token.durability());
        assertEquals(1.12, token.gainFactor());

        token.useWith(new GoldToken())
                .ifWorking(() -> {});// 3 → 2
        assertEquals(2, token.durability());
        assertEquals(1.08, token.gainFactor());

        token.useWith(new GoldToken())
                .ifWorking(() -> {});// 2 → 1
        assertEquals(1, token.durability());
        assertEquals(1.04, token.gainFactor());
    }

    @Test
    void useWith_gold_should_eventually_break_token() {
        SluiceboxToken token = new SluiceboxToken();

        for (int i = 0; i < 5; i++) {
            token.useWith(new GoldToken())
                    .ifWorking(() -> {});
        }

        assertEquals(0, token.durability());
        assertTrue(token.isBroken());
    }

    @Test
    void use_with_gold_should_set_working_state() {
        SluiceboxToken token = new SluiceboxToken();
        GoldToken gold = new GoldToken();

        final boolean[] invoked = {false};

        token.useWith(gold)
                .ifWorking(() -> invoked[0] = true);

        assertTrue(invoked[0], "ifWorking should be triggered");
    }

    @Test
    void use_with_non_gold_should_set_idle_state() {
        SluiceboxToken token = new SluiceboxToken();
        EmptyToken empty = new EmptyToken();

        final boolean[] invoked = {false};

        token.useWith(empty)
                .ifIdle(() -> invoked[0] = true);

        assertTrue(invoked[0], "ifIdle should be triggered");
    }

    @Test
    void use_with_gold_on_broken_token_should_set_broken_state() {
        SluiceboxToken token = new SluiceboxToken();

        for (int i = 0; i < 5; i++) {
            token.useWith(new GoldToken())
                    .ifWorking(() -> {});
        }
        assertTrue(token.isBroken());

        final boolean[] invoked = {false};

        token.useWith(new GoldToken())
                .ifBroken(() -> invoked[0] = true);

        assertTrue(invoked[0], "ifBroken should be triggered");
    }

    @Test
    void chained_state_callbacks_should_trigger_only_correct_one() {
        SluiceboxToken token = new SluiceboxToken();

        final boolean[] working = {false};
        final boolean[] broken = {false};
        final boolean[] idle = {false};

        token.useWith(new GoldToken())
                .ifWorking(() -> working[0] = true)
                .ifBroken(() -> broken[0] = true)
                .ifIdle(() -> idle[0] = true);

        assertTrue(working[0]);
        assertFalse(broken[0]);
        assertFalse(idle[0]);
    }

    @Test
    void is_broken_should_return_true_when_gain_factor_too_low() {
        SluiceboxToken token = new SluiceboxToken();

        for (int i = 0; i < 4; i++) {
            token.useWith(new GoldToken())
                    .ifWorking(() -> {});
        }

        assertFalse(token.isBroken());

        // ostatni raz
        token.useWith(new GoldToken())
                .ifWorking(() -> {});
        assertTrue(token.isBroken());
    }

    @Test
    void sluicebox_gain_when_player_interacts_with_gold() {
        var player = new Player();
        player.interactWithToken(new SluiceboxToken());
        player.interactWithToken(new GoldToken(2.0));

        assertEquals(2.0 * 1.2, player.gold.amount());
    }

    @Test
    void last_use_before_broken_should_apply_1_04_gain() {
        var token = new SluiceboxToken();

        token.useWith(new GoldToken())
                .ifWorking(() -> {});// 4
        token.useWith(new GoldToken())
                .ifWorking(() -> {});// 3
        token.useWith(new GoldToken())
                .ifWorking(() -> {});// 2
        token.useWith(new GoldToken())
                .ifWorking(() -> {});// 1

        assertEquals(1.04, token.gainFactor());
        assertEquals(1, token.durability());

        token.useWith(new GoldToken())
                .ifWorking(() -> {});// 0 (broken)
        assertTrue(token.isBroken());
    }

    @Test
    void broken_sluicebox_is_unusable() {
        var player = new Player();
        var gold = new GoldToken(2.0);
        var sluice = new SluiceboxToken();

        player.interactWithToken(sluice);

        // rozbijamy
        for (int i = 0; i < 5; i++) {
            sluice.useWith(new GoldToken())
                    .ifWorking(() -> {});
        }

        assertTrue(sluice.isBroken());

        // próba zebrania
        player.interactWithToken(gold);

        assertEquals(2.0, player.gold.amount());
    }

    @Test
    void gainFactor_does_not_change_after_broken() {
        var sluice = new SluiceboxToken();

        for (int i = 0; i < 5; i++) {
            sluice.useWith(new GoldToken())
                    .ifWorking(() -> {});
        }

        assertTrue(sluice.isBroken());
        double oldGain = sluice.gainFactor();

        sluice.useWith(new GoldToken())
                .ifBroken(() -> assertTrue(true))
                .ifWorking(() -> fail("Should not work"))
                .ifIdle(() -> fail("Should not be idle"));

        assertEquals(oldGain, sluice.gainFactor(), 0.0001);
        assertEquals(0, sluice.durability());
    }

    @Test
    void useWith_empty_token_does_nothing() {
        var sluice = new SluiceboxToken();
        var beforeDurability = sluice.durability();
        var beforeGain = sluice.gainFactor();

        sluice.useWith(new EmptyToken())
                .ifIdle(() -> assertTrue(true))
                .ifWorking(() -> fail("working? impossible"))
                .ifBroken(() -> fail("broken? impossible"));

        assertEquals(beforeDurability, sluice.durability());
        assertEquals(beforeGain, sluice.gainFactor());
    }
}
