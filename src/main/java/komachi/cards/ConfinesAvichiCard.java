package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;
import komachi.powers.KarmaPower;

public class ConfinesAvichiCard extends AbstractCard {
    public static final String ID = "Komachi:ConfinesAvichi";
    public static final String NAME = "Confines of Avichi";
    public static final String DESCRIPTION = "Deal !D! damage and apply !M! karma to ALL enemies. NL Consume: Deal !ALTDMG! damage instead.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final int COST = 2;

    public ConfinesAvichiCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = 9;
        this.baseAltDamage = 13;
        this.magicNumber = this.baseMagicNumber = 2;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        if (!AbstractDungeon.player.hasOrb()) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, this.multiAltDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        for (AbstractCreature monster : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new KarmaPower(monster, player, this.magicNumber), this.magicNumber));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
            upgradeAltDamage(6);
            upgradeMagicNumber(1);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


