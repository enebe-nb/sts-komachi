package komachi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import komachi.KomachiMod;
import komachi.powers.SpiritBladePower;
import komachi.powers.WanderingSpiritsPower;

public class ConsumeOrbAction extends AbstractGameAction {
    private AbstractGameAction chain;
  
    public ConsumeOrbAction(int amount) {
        this.amount = amount;
    }

    public ConsumeOrbAction(int amount, AbstractGameAction chain) {
        this.amount = amount;
        this.chain = chain;
    }
  
    public void update() {
        WanderingSpiritsPower wanderingSpirits = (WanderingSpiritsPower) AbstractDungeon.player.getPower(WanderingSpiritsPower.POWER_ID);
        SpiritBladePower spiritBlade = (SpiritBladePower) AbstractDungeon.player.getPower(SpiritBladePower.POWER_ID);
        AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(amount));
        for (int i = 0; i < this.amount && AbstractDungeon.player.hasOrb(); i++) {
            KomachiMod.consumedInThisCombat++;
            AbstractDungeon.player.evokeOrb();
            if (wanderingSpirits != null) wanderingSpirits.onConsumeOrb();
            if (spiritBlade != null) spiritBlade.onConsumeOrb();
            if (this.chain != null) AbstractDungeon.actionManager.addToBottom(this.chain);
        }
        
        this.isDone = true;
    }
}
