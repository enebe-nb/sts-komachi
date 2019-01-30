package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.patches.KomachiEnum;

public class BeheadCard extends AbstractCard {
    public static final String ID = "Komachi:Behead";
    public static final String NAME = "Behead";
    public static final String DESCRIPTION = "Deal !D! damage. NL Receive !M! additional damage for each consumed spirit in this combat.";
    public static final String EX_DESCRIPTION = " NL (total !ALTDMG! damage)";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 2;

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
            upgradeAltDamage(2);
            upgradeMagicNumber(1);
        }
    }

    public void applyPowers() {
        this.baseAltDamage = this.baseDamage + this.magicNumber * KomachiMod.consumedInThisCombat;
        super.applyPowers();
        this.rawDescription = DESCRIPTION + EX_DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster monster) {
        this.baseAltDamage = this.baseDamage + this.magicNumber * KomachiMod.consumedInThisCombat;
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


