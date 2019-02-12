package komachi.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import komachi.KomachiMod;
import komachi.actions.EndTurnAction;
import komachi.patches.KomachiEnum;

public class FastBreakCard extends AbstractCard {
    public static final String ID = "Komachi:FastBreak";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final int COST = 1;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public FastBreakCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/fastbreak.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        int energy = AbstractDungeon.player.energy.energy - (this.costForTurn > 0 && !this.freeToPlayOnce ? this.costForTurn : 0);
        if (energy > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new EnergizedPower(player, energy), energy));
        }
        AbstractDungeon.actionManager.addToBottom(new EndTurnAction());
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}


