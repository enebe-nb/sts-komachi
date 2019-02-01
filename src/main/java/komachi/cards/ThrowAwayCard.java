package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.patches.KomachiEnum;

public class ThrowAwayCard extends AbstractCard {
    public static final String ID = "Komachi:ThrowAway";
    public static final String NAME = "Throw Away";
    public static final String DESCRIPTION = "Lose up to !M! gold. Deal twice that much damage.";
    public static final String EX_DESCRIPTION = " NL (current !D! damage)";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 1;

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
        this.rawDescription = DESCRIPTION + EX_DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster monster) {
        int spendGold = AbstractDungeon.player.gold > this.magicNumber ? this.magicNumber : AbstractDungeon.player.gold;
        this.baseDamage = 2 * spendGold;
        super.calculateCardDamage(monster);
        this.rawDescription = DESCRIPTION + EX_DESCRIPTION;
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


