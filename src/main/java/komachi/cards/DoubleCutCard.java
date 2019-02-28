package komachi.cards;

import java.util.Arrays;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ChainAction;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;

public class DoubleCutCard extends AbstractCard {
    public static final String ID = "Komachi:DoubleCut";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 1;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public DoubleCutCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = 4;
        this.baseAltDamage = 6;

        this.tags.add(KomachiEnum.TAG_CONSUME);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(
            new ChainAction(Arrays.asList(
                new DamageAction(target, new DamageInfo(player, this.altDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL),
                new DamageAction(target, new DamageInfo(player, this.altDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL)
            )), new ChainAction(Arrays.asList(
                new DamageAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL),
                new DamageAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL)
            ))
        ));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
            upgradeAltDamage(2);
        }
    }
}


