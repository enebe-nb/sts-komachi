package komachi.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import komachi.KomachiMod;
import komachi.patches.KomachiEnum;
import komachi.powers.FocusRemovePower;

public class FaithCard extends AbstractCard {
    public static final String ID = "Komachi:Faith";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final int COST = 1;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public FaithCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        if (!AbstractDungeon.player.hasPower(FocusRemovePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new FocusPower(player, 1), 1));
        } AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new FocusRemovePower(player, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}


