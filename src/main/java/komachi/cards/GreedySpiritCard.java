package komachi.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.patches.KomachiEnum;
import komachi.powers.SoulCallPower;

public class GreedySpiritCard extends AbstractCard {
    public static final String ID = "Komachi:GreedySpirit";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.POWER;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final int COST = 1;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public GreedySpiritCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/greedyspirit.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 25;
        this.tags.add(KomachiEnum.TAG_BOUND);
    }

    public boolean cardPlayable(AbstractMonster target) {
        return super.cardPlayable(target) && this.magicNumber <= AbstractDungeon.player.gold;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.player.loseGold(this.magicNumber);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new SoulCallPower(player, 1), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-10);
        }
    }
}
