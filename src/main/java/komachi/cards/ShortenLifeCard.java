package komachi.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ShortenLifeAction;
import komachi.patches.KomachiEnum;

public class ShortenLifeCard extends AbstractCard {
    public static final String ID = "Komachi:ShortenLife";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 4;
    private static final int UPG_COST = 3;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public ShortenLifeCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/shortenlife.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new ShortenLifeAction(target, 4.0F, this.upgraded));
        this.cost = this.upgraded ? UPG_COST : COST;
    }

    @Override
    public void triggerOnCardPlayed(com.megacrit.cardcrawl.cards.AbstractCard cardPlayed) {
        if (cardPlayed.hasTag(KomachiEnum.TAG_CONSUME)) this.updateCost(-1);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPG_COST);
            upgradeDescription(UPGRADE_DESCRIPTION);
        }
    }
}


