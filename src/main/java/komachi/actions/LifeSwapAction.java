package komachi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class LifeSwapAction extends AbstractGameAction {
    private AbstractCreature target2;
    public LifeSwapAction(AbstractCreature target1, AbstractCreature target2) {
        setValues(target1, AbstractDungeon.player);
        this.target2 = target2;
    }
  
    public void update() {
        int tmp = target.maxHealth;
        target.maxHealth = target2.maxHealth;
        target2.maxHealth = tmp;
        tmp = target.currentHealth;
        target.currentHealth = target2.currentHealth;
        target2.currentHealth = tmp;
        target.healthBarUpdatedEvent();
        target2.healthBarUpdatedEvent();

        AbstractDungeon.actionManager.addToBottom(new VFXAction(this.source, new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, false), Settings.FAST_MODE ? 0.1F : Settings.ACTION_DUR_FAST));
        this.isDone = true;
    }
}
