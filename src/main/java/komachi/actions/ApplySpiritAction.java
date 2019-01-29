package komachi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import komachi.orbs.SpiritOrb;

public class ApplySpiritAction extends AbstractGameAction {
    private SpiritOrb spirit;

    public ApplySpiritAction(SpiritOrb spirit) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.spirit = spirit;
    }

    @Override
    public void update() {
        AbstractCreature monster = AbstractDungeon.getMonsters().getRandomMonster(true);
        AbstractCreature player = AbstractDungeon.player;
        if (monster != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this.spirit, OrbFlareEffect.OrbFlareColor.LIGHTNING), Settings.FAST_MODE ? 0.0F : 0.1F));
            AbstractDungeon.actionManager.addToBottom(new ApplyRandomDebuffAction(monster, player, spirit.passiveAmount));
        }

        this.isDone = true;
    }
}