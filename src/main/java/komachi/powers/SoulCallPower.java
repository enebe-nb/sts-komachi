package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;
import komachi.actions.BoundSpiritAction;

public class SoulCallPower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:SoulCall";
    public static final String NAME = "Soul Call";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS = new String[]{
        "At end of your turn bind ", " Spirits."
    };

    public SoulCallPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = POWER_TYPE;
        this.amount = amount;
        this.img = new Texture(KomachiMod.getResourcePath("powers/soul-call.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) for (int i = 0; i < this.amount; ++i) {
            AbstractDungeon.actionManager.addToBottom(new BoundSpiritAction());
        }
    }
}