package komachi.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.patches.KomachiEnum;

public class OfferingsCard extends AbstractCard {
    public static final String ID = "Komachi:Offerings";
    public static final String NAME = "Offerings";
    public static final String DESCRIPTION = "Exhaust a card. NL Lose !M! gold. NL Gain an orb slot. NL Exhaust";
    public static final String UPGRADE_DESCRIPTION = "Exhaust a card. NL Lose !M! gold. NL Gain an orb slot.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final int COST = 1;

    public OfferingsCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 9;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        int spendGold = AbstractDungeon.player.gold > this.magicNumber ? this.magicNumber : AbstractDungeon.player.gold;
        if (spendGold > 0) AbstractDungeon.player.loseGold(spendGold);
        AbstractDungeon.actionManager.addToBottom(new ExhaustAction(player, player, 1, false));
        AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-3);
            this.exhaust = false;
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


