package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.BaseModCardTags;
import komachi.KomachiMod;
import komachi.patches.KomachiEnum;

public class StrikeCard extends AbstractCard {
    public static final String ID = "Komachi:Strike";
    public static final String NAME = "Strike";
    public static final String DESCRIPTION = "Deal !D! damage.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 1;

    public StrikeCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/strike.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = 6;
        this.tags.add(BaseModCardTags.BASIC_STRIKE);
        this.tags.add(AbstractCard.CardTags.STRIKE);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


