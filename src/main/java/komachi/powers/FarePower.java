package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;

public class FarePower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:Fare";
    public static final String NAME = "Fare";
    public static final int MAX_GOLD = 12;
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS = new String[]{
        "This enemy gives you ", " additional gold reward. (max ", " stacks)"
    };

    public FarePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = POWER_TYPE;
        this.amount = amount > MAX_GOLD ? MAX_GOLD : amount;
        this.img = new Texture(KomachiMod.getResourcePath("powers/fare.png"));
        updateDescription();
    }

    public void stackPower(int amount) {
        super.stackPower(amount);
        if (this.amount > MAX_GOLD) this.amount = MAX_GOLD;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + MAX_GOLD + DESCRIPTIONS[2];
    }

    public void onDeath() {
        AbstractDungeon.getCurrRoom().addGoldToRewards(this.amount);
    }
}