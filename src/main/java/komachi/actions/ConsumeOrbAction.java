package komachi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

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
        AbstractPower wanderingSpirits = AbstractDungeon.player.getPower(WanderingSpiritsPower.POWER_ID);
        AbstractPower spiritBlade = AbstractDungeon.player.getPower(SpiritBladePower.POWER_ID);
        AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(amount));
        for (int i = 0; i < this.amount && AbstractDungeon.player.hasOrb(); i++) {
            KomachiMod.consumedInThisCombat++;
            AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
            if (wanderingSpirits != null) wanderingSpirits.onSpecificTrigger();
            if (spiritBlade != null) spiritBlade.onSpecificTrigger();
            if (this.chain != null) AbstractDungeon.actionManager.addToBottom(this.chain);
        }
        
        this.isDone = true;
    }
}
