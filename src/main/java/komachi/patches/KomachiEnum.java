package komachi.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;

public class KomachiEnum {
    @SpireEnum public static AbstractCard.CardColor KOMACHI_COLOR;
    @SpireEnum public static AbstractCard.CardTags TAG_BOUND;
    @SpireEnum public static AbstractPlayer.PlayerClass KOMACHI_CLASS;

    public static final Color BG_COLOR = CardHelper.getColor(50.0F, 26.0F, 26.0F);
}