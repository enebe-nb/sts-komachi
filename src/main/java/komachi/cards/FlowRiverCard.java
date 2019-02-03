package komachi.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.DiscardToAction;
import komachi.patches.KomachiEnum;

public class FlowRiverCard extends AbstractCard {
    public static final String ID = "Komachi:FlowRiver";
    public static final String NAME = "Flow of the River";
    public static final String DESCRIPTION = "Gain !B! block. NL You may discard a card to draw !M! cards";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final int COST = 0;

    public FlowRiverCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseBlock = 2;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        if (!AbstractDungeon.player.hand.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new DiscardToAction(1, new DrawCardAction(player, this.magicNumber)));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


