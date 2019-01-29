
package komachi.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

import basemod.abstracts.CustomRelic;
import komachi.KomachiMod;
import komachi.actions.ConsumeOrbAction;

public class OtherSidePierRelic extends CustomRelic {
    public static final String ID = "Komachi:Relic:OtherSidePier";

    public OtherSidePierRelic() {
        super(ID, new Texture(KomachiMod.getResourcePath("relics/othersidepier.png")),
            new Texture(KomachiMod.getResourcePath("relics/othersidepier_o.png")),
            RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }
    
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    @Override
    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(1));
    }
}