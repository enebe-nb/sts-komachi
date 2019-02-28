package komachi.helpers;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AltMagicNumberVariable extends DynamicVariable
{
    @Override
    public String key() {
        return "ALTMN";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof komachi.cards.AbstractCard) return ((komachi.cards.AbstractCard)card).isAltMagicNumberModified;
        return false;
    }

    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof komachi.cards.AbstractCard) ((komachi.cards.AbstractCard)card).isAltMagicNumberModified = v;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof komachi.cards.AbstractCard) return ((komachi.cards.AbstractCard)card).altMagicNumber;
        return card.magicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof komachi.cards.AbstractCard) return ((komachi.cards.AbstractCard)card).baseAltMagicNumber;
        return card.baseMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof komachi.cards.AbstractCard) return ((komachi.cards.AbstractCard)card).upgradedAltMagicNumber;
        return false;
    }
}