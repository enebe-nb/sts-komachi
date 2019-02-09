package komachi.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;

public class MementoCard extends AbstractCard {
    public static final String ID = "Komachi:Memento";
    public static final String NAME = "Memento";
    public static final String DESCRIPTION = "Put a card from discard pile into your hand. NL Consume: Gain !B! block.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final int COST = 0;

    public MementoCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseBlock = 5;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        if (AbstractDungeon.player.discardPile.size() > 0) {
            AbstractDungeon.actionManager.addToBottom(new DiscardPileToHandAction(1));
        } if (AbstractDungeon.player.hasOrb()) {
            AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(5);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


