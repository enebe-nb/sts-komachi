package komachi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.powers.KarmaPower;

@SpirePatch(
    clz = ApplyPowerAction.class,
    method = "update"
)
public class PowerAmountPatch {
    @SpireInsertPatch(rloc=18, localvars={"this.powerToApply"})
    public static void renderCracklingSoulBar(ApplyPowerAction __instance, AbstractPower power) {
        if (power instanceof KarmaPower) __instance.amount = power.amount;
    }
}