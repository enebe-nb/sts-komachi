package komachi.cards;

import java.util.Arrays;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ApplyRandomDebuffAction;
import komachi.actions.ChainAction;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;

public class ReaperScytheCard extends AbstractCard {
    public static final String ID = "Komachi:ReaperScythe";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = 1;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public ReaperScytheCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/reaperscythe.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = 7;
        this.baseAltDamage = 9;

        this.tags.add(KomachiEnum.TAG_CONSUME);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(
            new ChainAction(Arrays.asList(
                new DamageAction(target, new DamageInfo(player, this.altDamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL),
                new ApplyRandomDebuffAction(target, player, 1)
            )), new DamageAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL)
        ));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeAltDamage(4);
        }
    }
}


