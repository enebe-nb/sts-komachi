package komachi.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;

public class SinRecollectionPower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:SinRecollection";
    public static final String NAME = "Sin Recollection";
    public static final String DESCRIPTION = "Until your next turn, if you would apply a random debuff, instead you apply that much karma.";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    private boolean justApplied = true;


    public SinRecollectionPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = POWER_TYPE;
        this.img = new Texture(KomachiMod.getResourcePath("powers/sin-recollection.png"));
        this.description = DESCRIPTION;
    }

    public void atEndOfRound() {
        if (this.justApplied) this.justApplied = false;
        else AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}
