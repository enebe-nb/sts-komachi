package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;

public class FarePower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:Fare";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    private static PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

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

    @Override
    public void stackPower(int amount) {
        super.stackPower(amount);
        int max = getMaxStack();
        if (this.amount > max) this.amount = max;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + getMaxStack() + DESCRIPTIONS[2];
    }

    @Override
    public void onDeath() {
        AbstractDungeon.getCurrRoom().addGoldToRewards(this.amount);
    }

    private int getMaxStack() {
        AbstractMonster owner = (AbstractMonster)this.owner;
        return owner.type == AbstractMonster.EnemyType.BOSS ? 24
            : owner.type == AbstractMonster.EnemyType.ELITE ? 18
            : 12;
    }
}