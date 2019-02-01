package komachi.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;

public class ScaredPower extends AbstractPower {
    public static final String POWER_ID = "Komachi:Power:Scared";
    public static final String NAME = "Scared";
    public static final String DESCRIPTION = "It is scared.";
    public static PowerType POWER_TYPE = PowerType.DEBUFF;

    public ScaredPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = POWER_TYPE;
        this.img = new Texture(KomachiMod.getResourcePath("powers/scared.png"));
        this.description = DESCRIPTION;
    }
}