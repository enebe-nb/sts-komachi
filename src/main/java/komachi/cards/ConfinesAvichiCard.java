package komachi.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ChainAction;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;
import komachi.powers.KarmaPower;

public class ConfinesAvichiCard extends AbstractCard {
    public static final String ID = "Komachi:ConfinesAvichi";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final int COST = 2;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public ConfinesAvichiCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = 8;
        this.baseAltDamage = 10;
        this.magicNumber = this.baseMagicNumber = 3;
        this.isMultiDamage = true;

        this.tags.add(KomachiEnum.TAG_CONSUME);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        ArrayList<AbstractGameAction> chainConsume = new ArrayList<>();
        ArrayList<AbstractGameAction> chainNormal = new ArrayList<>();
        chainConsume.add(new DamageAllEnemiesAction(player, this.multiAltDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        chainNormal.add(new DamageAllEnemiesAction(player, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        for (AbstractCreature monster : AbstractDungeon.getMonsters().monsters) {
            AbstractGameAction action = new ApplyPowerAction(monster, player, new KarmaPower(monster, player, this.magicNumber), this.magicNumber);
            chainConsume.add(action);
            chainNormal.add(action);
        }

        AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(
            new ChainAction(chainConsume),
            new ChainAction(chainNormal)
        ));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeAltDamage(5);
            upgradeMagicNumber(1);
        }
    }
}


