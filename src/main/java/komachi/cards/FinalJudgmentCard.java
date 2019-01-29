package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import komachi.KomachiMod;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;

public class FinalJudgmentCard extends AbstractCard {
    public static final String ID = "Komachi:FinalJudgment";
    public static final String NAME = "Final Judgement";
    public static final String DESCRIPTION = "Deal !D! damage. Consume: Deal !ALTDMG! damage instead.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 3;

    public FinalJudgmentCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = 22;
        this.baseAltDamage = 33;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        if (target != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(target.hb.cX, target.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
        }
        
        if (!AbstractDungeon.player.hasOrb()) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.altDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(8);
            upgradeAltDamage(12);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


