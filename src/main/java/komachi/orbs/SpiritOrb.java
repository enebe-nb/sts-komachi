package komachi.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;

import komachi.KomachiMod;
import komachi.actions.ApplySpiritAction;

public class SpiritOrb extends AbstractOrb {
    private float vfxTimer = 0;

    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString("Dark");
    public static final String NAME = orbStrings.NAME;
    public static final String[] DESCRIPTIONS = orbStrings.DESCRIPTION;

    public SpiritOrb() {
        this.ID = "Komachi:Orb:Spirit";
        this.name = "Spirit";
        this.description = "When bound and at start of yout turn, apply a random #yDebuff";
        this.img = ImageMaster.loadImage(KomachiMod.getResourcePath("orbs/spirit.png"));
        this.bobEffect.speed = 1.5F;
        this.bobEffect.dist = 7.0F * Settings.scale;
        this.basePassiveAmount = 1;
    }

    @Override
    public void updateDescription() {
        this.applyFocus();
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();

        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(this.cX, this.cY));
            this.vfxTimer = 0.25F;
        }
    }
    
    @Override
    //Taken from frost orb and modified a bit. Works to draw the basic orb image.
    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    @Override
    public void onEvoke() {
        //AbstractDungeon.actionManager.addToBottom(new ApplySpiritAction());
    }

    @Override
    public void onStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ApplySpiritAction(this));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new SpiritOrb();
    }

    @Override
    public void triggerEvokeAnimation() {
        AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_PLASMA_CHANNEL", 0.1F);
    }
}