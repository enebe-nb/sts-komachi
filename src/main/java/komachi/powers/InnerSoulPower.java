package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;
import komachi.patches.KomachiEnum;

public class InnerSoulPower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:InnerSoul";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public InnerSoulPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = POWER_TYPE;
        this.img = new Texture(KomachiMod.getResourcePath("powers/inner-soul.png"));
        updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(KomachiEnum.TAG_CONSUME)) card.exhaust = true;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onSpecificTrigger() {
        this.flash();
    }
}