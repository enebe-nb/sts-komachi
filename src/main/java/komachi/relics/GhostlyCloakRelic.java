
package komachi.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

import basemod.abstracts.CustomRelic;
import komachi.KomachiMod;

public class GhostlyCloakRelic extends CustomRelic {
    public static final String ID = "Komachi:Relic:GhostlyCloak";

    public GhostlyCloakRelic() {
        super(ID, new Texture(KomachiMod.getResourcePath("relics/ghostlycloak.png")),
            new Texture(KomachiMod.getResourcePath("relics/ghostlycloak_o.png")),
            RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onTrigger() {
        if (++this.counter >= 4) {
            this.flash();
            this.counter = 0;
            AbstractPlayer player = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new DexterityPower(player, 1), 1));
        }
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }
}