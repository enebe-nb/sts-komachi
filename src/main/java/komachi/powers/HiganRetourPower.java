package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import komachi.KomachiMod;
import komachi.orbs.SpiritOrb;

public class HiganRetourPower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:HiganRetour";
    public static final String NAME = "Higan Retour";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS = new String[] {
        "Whenever you bind a spirit, Gain ", " Strength for one turn."
    }; 

    public HiganRetourPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = POWER_TYPE;
        this.amount = amount;
        this.img = new Texture(KomachiMod.getResourcePath("powers/higan-retour.png"));
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onChannel(AbstractOrb orb) {
        if (orb instanceof SpiritOrb) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new LoseStrengthPower(this.owner, this.amount), this.amount));
        }
    }
}