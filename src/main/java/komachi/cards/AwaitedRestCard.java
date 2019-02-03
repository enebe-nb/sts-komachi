package komachi.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.patches.KomachiEnum;
import komachi.powers.RestPower;

public class AwaitedRestCard extends AbstractCard {
    public static final String ID = "Komachi:AwaitedRest";
    public static final String NAME = "Awaited Rest";
    public static final String DESCRIPTION = "Autoplay. NL At end of combat heals !M!, reduce this value each turn.";
    public static final String CANTUSE_DESCRIPTION = "I can't play this card.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final int COST = -2;

    public AwaitedRestCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 5;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new RestPower(player, this.magicNumber), this.magicNumber));
    }

    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new QueueCardAction(this, null));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


