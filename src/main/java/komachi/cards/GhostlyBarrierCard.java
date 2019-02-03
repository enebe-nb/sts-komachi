package komachi.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;

public class GhostlyBarrierCard extends AbstractCard {
    public static final String ID = "Komachi:GhostlyBarrier";
    public static final String NAME = "Ghostly Barrier";
    public static final String DESCRIPTION = "Gain !B! block. NL Consume: Play again. NL Ethereal";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final int COST = 1;

    public GhostlyBarrierCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseBlock = 6;
        this.isEthereal = true;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        if (AbstractDungeon.player.hasOrb()) {
            AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(1));

            com.megacrit.cardcrawl.cards.AbstractCard tmp = this.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = this.current_x;
            tmp.current_y = this.current_y;
            tmp.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
            tmp.target_y = (Settings.HEIGHT / 2.0F);
            tmp.freeToPlayOnce = true;
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, target, this.energyOnUse));
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


