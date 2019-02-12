package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.patches.KomachiEnum;

public class ThrowAwayCard extends AbstractCard {
    public static final String ID = "Komachi:ThrowAway";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 1;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String TOTAL_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];

    public ThrowAwayCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 6;
        this.baseDamage = 2 * this.magicNumber;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        this.calculateCardDamage(target);
        int spendGold = AbstractDungeon.player.gold > this.magicNumber ? this.magicNumber : AbstractDungeon.player.gold;
        if (spendGold > 0) {
            AbstractDungeon.player.loseGold(spendGold);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }

    public void applyPowers() {
        int spendGold = AbstractDungeon.player.gold > this.magicNumber ? this.magicNumber : AbstractDungeon.player.gold;
        this.baseDamage = 2 * spendGold;
        super.applyPowers();
        this.rawDescription = DESCRIPTION + TOTAL_DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster monster) {
        int spendGold = AbstractDungeon.player.gold > this.magicNumber ? this.magicNumber : AbstractDungeon.player.gold;
        this.baseDamage = 2 * spendGold;
        super.calculateCardDamage(monster);
        this.rawDescription = DESCRIPTION + TOTAL_DESCRIPTION;
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }
}


