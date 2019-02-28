package komachi.helpers;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AltDamageVariable extends DynamicVariable
{
    @Override
    public String key() {
        return "ALTDMG";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof komachi.cards.AbstractCard) return ((komachi.cards.AbstractCard)card).isAltDamageModified;
        return false;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof komachi.cards.AbstractCard) ((komachi.cards.AbstractCard)card).isAltDamageModified = v;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof komachi.cards.AbstractCard) return ((komachi.cards.AbstractCard)card).altDamage;
        return card.damage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof komachi.cards.AbstractCard) return ((komachi.cards.AbstractCard)card).baseAltDamage;
        return card.baseDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof komachi.cards.AbstractCard) return ((komachi.cards.AbstractCard)card).upgradedAltDamage;
        return false;
    }
}