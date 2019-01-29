package komachi.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import komachi.powers.CracklingSoulPower;

@SpirePatch(
    clz = AbstractCreature.class,
    method = "renderHealth",
    paramtypez = {SpriteBatch.class}
)
public class CracklingSoulHPPatch {
    private static final float HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
    private static final float HEALTH_BAR_OFFSET_Y = -28.0F * Settings.scale;

    @SpireInsertPatch(rloc=18, localvars={"x", "y", "this.targetHealthBarWidth", "this.redHbBarColor", "this.blueHbBarColor"})
    public static void renderCracklingSoulBar(AbstractCreature __instance, SpriteBatch sb, float x, float y, float targetWidth, Color redColor, Color blueColor) {
        if (targetWidth <= 0.0F) return;
        
        CracklingSoulPower soulPower = (CracklingSoulPower) __instance.getPower(CracklingSoulPower.POWER_ID);
        if (soulPower == null || soulPower.isDone) return;
        
        AbstractPower poisonPower = __instance.getPower(PoisonPower.POWER_ID);
        int poisonAmount = poisonPower == null ? 0 :
            __instance.hasPower(IntangiblePower.POWER_ID) ? 1 : poisonPower.amount;
        int soulAmount = 0;
        if (__instance.hasPower(IntangiblePower.POWER_ID)) soulAmount = 1;
        else for (AbstractPower power : __instance.powers) {
            if (power.type == PowerType.DEBUFF && power.ID != GainStrengthPower.POWER_ID) soulAmount += soulPower.amount;
        }

        sb.setColor(0.4F, 0.4F, 0.4F, 1.0F);
        if (__instance.currentHealth > poisonAmount) {
            float e = targetWidth * (__instance.currentHealth - poisonAmount) / __instance.currentHealth;
            float s = targetWidth * (__instance.currentHealth - poisonAmount - soulAmount) / __instance.currentHealth;
            if (s < 0.0F) s = 0.0F;
            sb.draw(ImageMaster.HEALTH_BAR_B, x + s, y + HEALTH_BAR_OFFSET_Y, e - s, HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + e, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            if (__instance.currentHealth > soulAmount + poisonAmount) {
                if (__instance.currentBlock > 0) sb.setColor(blueColor);
                else sb.setColor(redColor);
                sb.draw(ImageMaster.HEALTH_BAR_R, x + s, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            } else {
                sb.draw(ImageMaster.HEALTH_BAR_L, x + s - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
            }
        }
    }
}