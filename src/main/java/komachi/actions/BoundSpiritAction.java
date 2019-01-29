package komachi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import komachi.orbs.SpiritOrb;
import komachi.relics.GhostlyCloakRelic;

public class BoundSpiritAction extends AbstractGameAction {
    private SpiritOrb spirit;

    public BoundSpiritAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.spirit = new SpiritOrb();
    }

    public BoundSpiritAction(SpiritOrb spirit) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.spirit = spirit;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.maxOrbs > 0) {
            AbstractDungeon.player.channelOrb(this.spirit);
            AbstractDungeon.actionManager.addToBottom(new ApplySpiritAction(this.spirit));
            AbstractRelic cloak = AbstractDungeon.player.getRelic(GhostlyCloakRelic.ID);
            if (cloak != null) cloak.onTrigger();
            tickDuration();
        }
        this.isDone = true;
    }
}