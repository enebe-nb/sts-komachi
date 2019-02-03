package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.BoundSpiritAction;
import komachi.patches.KomachiEnum;

public class VengefulSpiritCard extends AbstractCard {
    public static final String ID = "Komachi:VengefulSpirit";
    public static final String NAME = "Bound Vengeful Spirit";
    public static final String DESCRIPTION = "Bind a Spirit. NL Deal !D! damage.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF_AND_ENEMY;
    private static final int COST = 1;

    public VengefulSpiritCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = 6;
        this.tags.add(KomachiEnum.TAG_BOUND);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new BoundSpiritAction());
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


