package komachi.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;

public class SpiritBladePower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:SpiritBlade";
    public static final String NAME = "Spirit Blade";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS = new String[] {
        "When you consume a spirit, deal ", " damage to a random enemy."
    }; 

    public SpiritBladePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = POWER_TYPE;
        this.amount = amount;
        this.img = new Texture(KomachiMod.getResourcePath("powers/spirit-blade.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onConsumeOrb() {
        AbstractCreature target = AbstractDungeon.getRandomMonster();
        if (target != null) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS)));
        }
    }
}