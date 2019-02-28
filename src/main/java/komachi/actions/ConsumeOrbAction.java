package komachi.actions;

import java.util.Arrays;
import java.util.List;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;
import komachi.powers.InnerSoulPower;
import komachi.powers.SpiritBladePower;
import komachi.powers.WanderingSpiritsPower;

public class ConsumeOrbAction extends AbstractGameAction {
    private AbstractGameAction onSuccess;
    private AbstractGameAction onFailure;
    private List<String> triggeredPowers = Arrays.asList(
        InnerSoulPower.POWER_ID,
        SpiritBladePower.POWER_ID,
        WanderingSpiritsPower.POWER_ID
    );

    public ConsumeOrbAction() {
        this(null, null);
    }

    public ConsumeOrbAction(AbstractGameAction onSuccess) {
        this(onSuccess, null);
    }

    public ConsumeOrbAction(AbstractGameAction onSuccess, AbstractGameAction onFailure) {
        if (Settings.FAST_MODE) this.startDuration = Settings.ACTION_DUR_XFAST;
        else this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            boolean hasInnerSoul = AbstractDungeon.player.hasPower(InnerSoulPower.POWER_ID);
            if (AbstractDungeon.player.hasOrb() || hasInnerSoul) {
                KomachiMod.consumedInThisCombat++;
                if (!hasInnerSoul) {
                    AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(1));
                    AbstractDungeon.player.triggerEvokeAnimation(0);
                    AbstractDungeon.player.evokeOrb();
                }

                for (AbstractPower power : AbstractDungeon.player.powers) {
                    if (triggeredPowers.contains(power.ID)) power.onSpecificTrigger();
                }
                if (this.onSuccess != null) AbstractDungeon.actionManager.addToBottom(this.onSuccess);
            } else if (this.onFailure != null) AbstractDungeon.actionManager.addToBottom(this.onFailure);
        }

        tickDuration();
    }
}
