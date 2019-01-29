
package komachi.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;

import basemod.abstracts.CustomRelic;
import komachi.KomachiMod;
import komachi.actions.BoundSpiritAction;

public class FerryingBoatRelic extends CustomRelic {
    public static final String ID = "Komachi:Relic:FerryingBoat";

    public FerryingBoatRelic() {
        super(ID, new Texture(KomachiMod.getResourcePath("relics/ferryingboat.png")),
            new Texture(KomachiMod.getResourcePath("relics/ferryingboat_o.png")),
            RelicTier.STARTER, LandingSound.HEAVY);
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onMonsterDeath(AbstractMonster monster) {
        if (monster.currentHealth == 0 && !monster.hasPower(MinionPower.POWER_ID)) {
            this.flash();
            this.counter++;
        }
    }

    @Override
    public void atBattleStart() {
        this.flash();
        for (int i = 0; i < this.counter; ++i) {
            AbstractDungeon.actionManager.addToBottom(new BoundSpiritAction());
        } this.counter = 0;
    }
}