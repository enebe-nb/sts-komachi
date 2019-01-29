package komachi.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ShortenLifeAction extends AbstractGameAction {
    private boolean reduceCurrentHealth;
    private float factor;
    public ShortenLifeAction(AbstractCreature target, float factor, boolean reduceCurrentHealth) {
        setValues(target, AbstractDungeon.player);
        this.reduceCurrentHealth = reduceCurrentHealth;
        this.factor = factor;
    }
  
    public void update() {
        AbstractPower invincible = target.getPower(InvinciblePower.POWER_ID);
        int limit = invincible != null ? invincible.amount : 999;

        int damage = MathUtils.floor(target.maxHealth / factor);
        if (damage > limit) damage = limit;
        target.maxHealth -= damage;

        damage = 0;
        if (reduceCurrentHealth) {
            damage = MathUtils.floor(target.currentHealth / factor);
            if (damage > limit) damage = limit;
            target.currentHealth -= damage;
        }

        if (target.currentHealth > target.maxHealth) {
            damage += target.currentHealth - target.maxHealth;
            target.currentHealth = target.maxHealth;
        }

        if (invincible != null) invincible.amount -= damage;
        target.healthBarUpdatedEvent();

        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, false));
        this.isDone = true;
    }
}
