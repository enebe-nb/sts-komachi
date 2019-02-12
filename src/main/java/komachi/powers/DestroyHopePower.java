package komachi.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;

import komachi.KomachiMod;

public class DestroyHopePower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:DestroyHope";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    private boolean justApplied = true;

    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DestroyHopePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = POWER_TYPE;
        this.img = new Texture(KomachiMod.getResourcePath("powers/destroy-hope.png"));
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == PowerType.DEBUFF
            && power.ID != GainStrengthPower.POWER_ID
            && !target.isPlayer
            && !target.hasPower(ArtifactPower.POWER_ID)
            && (power.ID != CracklingSoulPower.POWER_ID || !((CracklingSoulPower)power).fromDestroyHope)) {

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, new CracklingSoulPower(target, source, 1, true), 1));
        }
    }

    @Override
    public void atEndOfRound() {
        if (this.justApplied) this.justApplied = false;
        else AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
    }
}