package komachi.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.BoundSpiritAction;
import komachi.patches.KomachiEnum;

public class HumanSpiritCard extends AbstractCard {
    public static final String ID = "Komachi:HumanSpirit";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final int COST = -2;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public HumanSpiritCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/humanspirit.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseBlock = 2;
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
            upgradeDescription(UPGRADE_DESCRIPTION);
        }
    }
}


