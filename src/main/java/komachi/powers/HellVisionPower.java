package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;

public class HellVisionPower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:HellVision";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HellVisionPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = POWER_TYPE;
        this.amount = amount;
        this.img = new Texture(KomachiMod.getResourcePath("powers/hell-vision.png"));
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature source, AbstractCreature target) {
        if (power instanceof KarmaPower) power.amount += this.amount;
    }
}