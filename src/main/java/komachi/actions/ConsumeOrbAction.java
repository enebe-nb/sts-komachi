package komachi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;
import komachi.powers.SpiritBladePower;
import komachi.powers.WanderingSpiritsPower;

public class ConsumeOrbAction extends AbstractGameAction {
    private AbstractGameAction chain;
  
    public ConsumeOrbAction(int amount) {
        if (Settings.FAST_MODE) this.startDuration = Settings.ACTION_DUR_XFAST;
        else this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.amount = amount;
    }

    public ConsumeOrbAction(int amount, AbstractGameAction chain) {
        this.amount = amount;
        this.chain = chain;
    }
  
    public void update() {
        if (this.duration == this.startDuration) {
            AbstractPower wanderingSpirits = AbstractDungeon.player.getPower(WanderingSpiritsPower.POWER_ID);
            AbstractPower spiritBlade = AbstractDungeon.player.getPower(SpiritBladePower.POWER_ID);
            AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(amount));
            for (int i = 0; i < this.amount && AbstractDungeon.player.hasOrb(); i++) {
                KomachiMod.consumedInThisCombat++;
                AbstractDungeon.player.triggerEvokeAnimation(0);
                AbstractDungeon.player.evokeOrb();
                if (wanderingSpirits != null) wanderingSpirits.onSpecificTrigger();
                if (spiritBlade != null) spiritBlade.onSpecificTrigger();
                if (this.chain != null) AbstractDungeon.actionManager.addToBottom(this.chain);
            }
        }

        tickDuration();
    }
}
