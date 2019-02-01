package komachi.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import komachi.KomachiMod;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;

public class GhostlyChainCard extends AbstractCard {
    public static final String ID = "Komachi:GhostlyChain";
    public static final String NAME = "Ghostly Chains";
    public static final String DESCRIPTION = "ALL enemies loses !M! Strength. NL Consume: Play again. NL Ethereal";
    public static final String UPGRADE_DESCRIPTION = "ALL enemies loses !M! Strength. NL Consume: Play again. NL Ethereal, Innate.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final int COST = 0;

    public GhostlyChainCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 1;
        this.isEthereal = true;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new StrengthPower(monster, -this.magicNumber), -this.magicNumber));
        } if (AbstractDungeon.player.hasOrb()) {
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

    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
    }
  
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


