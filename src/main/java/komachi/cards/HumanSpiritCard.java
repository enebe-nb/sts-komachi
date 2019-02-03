package komachi.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.BoundSpiritAction;
import komachi.patches.KomachiEnum;

public class HumanSpiritCard extends AbstractCard {
    public static final String ID = "Komachi:HumanSpirit";
    public static final String NAME = "Human Spirit Passing By";
    public static final String DESCRIPTION = "Unplayable. NL When you draw this card bound a Spirit and gain !B! block. NL Ethereal.";
    public static final String UPGRADE_DESCRIPTION = "Unplayable. NL When you draw this card bound a Spirit and gain !B! block.";
    public static final String CANTUSE_DESCRIPTION = "I can't play this card.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final int COST = -2;

    public HumanSpiritCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseBlock = 3;
        this.isEthereal = true;
    }

    public boolean cardPlayable(AbstractMonster target) { return false; }
    public void use(AbstractPlayer player, AbstractMonster target) { }

    public void triggerWhenDrawn() {
        this.applyPowers();
        AbstractDungeon.actionManager.addToBottom(new BoundSpiritAction());
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
            this.isEthereal = false;
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


