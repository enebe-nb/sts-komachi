package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;

import komachi.KomachiMod;

public class CracklingSoulPower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:CracklingSoul";
    public static final String NAME = "Crackling Soul";
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public AbstractCreature source;
    public boolean isDone = false;

    public static String[] DESCRIPTIONS = new String[] {
        "At start of its next turn it loses ", " HP for each debuff."
    }; 

    public CracklingSoulPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.source = source;
        this.type = POWER_TYPE;
        this.amount = amount;
        this.img = new Texture(KomachiMod.getResourcePath("powers/crackling-soul.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atStartOfTurn() {
        int damage = 0;
        for (AbstractPower power : this.owner.powers) {
            if (power.type == PowerType.DEBUFF && power.ID != GainStrengthPower.POWER_ID) damage += this.amount;
        } if (damage > 0) {
            flash();
            this.isDone = true;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.source, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}
