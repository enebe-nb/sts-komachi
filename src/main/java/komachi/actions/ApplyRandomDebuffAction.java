package komachi.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import komachi.powers.CracklingSoulPower;
import komachi.powers.KarmaPower;
import komachi.powers.SinRecollectionPower;

public class ApplyRandomDebuffAction extends AbstractGameAction {
    public ApplyRandomDebuffAction(AbstractCreature target, AbstractCreature source, int amount) {
        setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.amount > 0) switch(this.source.hasPower(SinRecollectionPower.POWER_ID) ? 0 : MathUtils.random(4)) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new KarmaPower(this.target, this.source, this.amount), this.amount));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new VulnerablePower(this.target, this.amount, false), this.amount));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new WeakPower(this.target, this.amount, false), this.amount));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new CracklingSoulPower(this.target, this.source, this.amount), this.amount));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new StrengthPower(this.target, -this.amount), -this.amount));
                if (!this.target.hasPower("Artifact")) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, this.source, new GainStrengthPower(this.target, this.amount), this.amount));
                }
                break;
        };
        this.isDone = true;
    }
}