package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;

public class SinRecollectionPower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:SinRecollection";
    public static final String NAME = "Sin Recollection";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS = new String[] {
        "This turn, if you deal unblocked damage, apply ", " karma."
    }; 

    public SinRecollectionPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = POWER_TYPE;
        this.amount = amount;
        this.img = new Texture(KomachiMod.getResourcePath("powers/sin-recollection.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if ((damageAmount > 0) && (target != this.owner) && (info.type == DamageInfo.DamageType.NORMAL)) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, this.owner, new KarmaPower(target, this.owner, this.amount), this.amount, true));
        }
    }

    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}
