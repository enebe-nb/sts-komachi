package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;

public class FarePower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:Fare";
    public static final String NAME = "Fare";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS = new String[]{
        "This enemy gives you ", " additional gold reward. (max ", " stacks)"
    };

    public FarePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = POWER_TYPE;
        int max = getMaxStack();
        this.amount = amount > max ? max : amount;
        this.img = new Texture(KomachiMod.getResourcePath("powers/fare.png"));
        updateDescription();
    }

    public void stackPower(int amount) {
        super.stackPower(amount);
        int max = getMaxStack();
        if (this.amount > max) this.amount = max;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + getMaxStack() + DESCRIPTIONS[2];
    }

    public void onDeath() {
        AbstractDungeon.getCurrRoom().addGoldToRewards(this.amount);
    }

    public int getMaxStack() {
        AbstractMonster owner = (AbstractMonster)this.owner;
        return owner.type == AbstractMonster.EnemyType.BOSS ? 24
            : owner.type == AbstractMonster.EnemyType.ELITE ? 18
            : 12;
    }
}