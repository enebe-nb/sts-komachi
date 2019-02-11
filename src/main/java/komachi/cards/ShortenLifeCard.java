package komachi.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ShortenLifeAction;
import komachi.patches.KomachiEnum;

public class ShortenLifeCard extends AbstractCard {
    public static final String ID = "Komachi:ShortenLife";
    public static final String NAME = "Shorten life";
    public static final String DESCRIPTION = "Reduce enemy MaxHP by a fourth. NL Cost 1 less each turn.";
    public static final String UPGRADE_DESCRIPTION = "Reduce enemy MaxHP and HP by a fourth. NL Cost 1 less each turn.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 6;

    public ShortenLifeCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/shortenlife.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new ShortenLifeAction(target, 4.0F, this.upgraded));
    }

    public void atTurnStart() {
        this.updateCost(-1);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


