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

public class BeheadCard extends AbstractCard {
    public static final String ID = "Komachi:Behead";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 2;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String TOTAL_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];

    public BeheadCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseAltDamage = this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        this.calculateCardDamage(target);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.altDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeAltDamage(3);
            upgradeMagicNumber(1);
        }
    }

    public void applyPowers() {
        this.baseAltDamage = this.baseDamage + this.magicNumber * KomachiMod.consumedInThisCombat;
        super.applyPowers();
        this.rawDescription = DESCRIPTION + TOTAL_DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster monster) {
        this.baseAltDamage = this.baseDamage + this.magicNumber * KomachiMod.consumedInThisCombat;
        super.calculateCardDamage(monster);
        this.rawDescription = DESCRIPTION + TOTAL_DESCRIPTION;
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }
}


