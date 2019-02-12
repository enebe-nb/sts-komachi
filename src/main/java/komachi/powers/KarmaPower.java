package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;

public class KarmaPower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:Karma";
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    private AbstractCreature source;

    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public KarmaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.source = source;
        this.type = POWER_TYPE;
        this.amount = amount;
        this.img = new Texture(KomachiMod.getResourcePath("powers/karma.png"));
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        int value = MathUtils.ceil(this.amount / 2.0F);
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, value));
    }

    @Override
    public void onInitialApplication() {
        this.onSpecificTrigger();
    }

    @Override
    public void stackPower(int amount) {
        super.stackPower(amount);
        this.onSpecificTrigger();
    }

    @Override
    public void onSpecificTrigger() {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.source, this.amount, DamageInfo.DamageType.THORNS)));
    }
}
