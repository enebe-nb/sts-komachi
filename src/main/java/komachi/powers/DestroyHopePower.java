package komachi.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;

import komachi.KomachiMod;

public class DestroyHopePower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:DestroyHope";
    public static final String NAME = "Destroy the Hope";
    public static String DESCRIPTION = "Until your next turn, if you apply a debuff also apply 1 Crackling_Soul.";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    private boolean justApplied = true;

    public DestroyHopePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = POWER_TYPE;
        this.img = new Texture(KomachiMod.getResourcePath("powers/destroy-hope.png"));
        this.description = DESCRIPTION;
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == PowerType.DEBUFF
            && power.ID != GainStrengthPower.POWER_ID
            && !target.isPlayer
            && !target.hasPower(ArtifactPower.POWER_ID)
            && (power.ID != CracklingSoulPower.POWER_ID || !((CracklingSoulPower)power).fromDestroyHope)) {

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, new CracklingSoulPower(target, source, 1, true), 1));
        }
    }

    public void atEndOfRound() {
        if (this.justApplied) this.justApplied = false;
        else AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
    }
}