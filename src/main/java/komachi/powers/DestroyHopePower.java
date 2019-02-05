package komachi.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;

import komachi.KomachiMod;

public class DestroyHopePower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:DestroyHope";
    public static final String NAME = "Destroy the Hope";
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    private boolean justApplied = true;

    public static String[] DESCRIPTIONS = new String[] {
        "Until your next turn, if you apply a debuff also apply ", " Crackling_Soul."
    };

    public DestroyHopePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = POWER_TYPE;
        this.img = new Texture(KomachiMod.getResourcePath("powers/destroy-hope.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == PowerType.DEBUFF
            && power.ID != GainStrengthPower.POWER_ID
            && !target.isPlayer
            && !target.hasPower(ArtifactPower.POWER_ID)
            && (power.ID != CracklingSoulPower.POWER_ID || !((CracklingSoulPower)power).fromDestroyHope)) {

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, new CracklingSoulPower(target, source, this.amount, true), this.amount));
        }
    }

    public void atEndOfRound() {
        if (this.justApplied) this.justApplied = false;
        else AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}