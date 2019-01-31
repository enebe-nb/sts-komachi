package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.RitualDaggerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import komachi.KomachiMod;
import komachi.patches.KomachiEnum;
import komachi.powers.KarmaPower;

public class RepentantSoulCard extends AbstractCard {
    public static final String ID = "Komachi:RepentantSoul";
    public static final String NAME = "Repentant Soul";
    public static final String DESCRIPTION = "Deal !D! damage. NL If this card kills an enemy with karma, permanently increase this card's damage by that much. NL Exhaust.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 1;

    public RepentantSoulCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = this.misc = 9;
        this.exhaust = true;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractPower power = target.getPower(KarmaPower.POWER_ID);
        if (power != null && power.amount > 0) {
            AbstractDungeon.actionManager.addToBottom(new RitualDaggerAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), power.amount, this.uuid));
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


