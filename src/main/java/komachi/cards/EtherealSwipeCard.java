package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.patches.KomachiEnum;
import komachi.powers.CracklingSoulPower;

public class EtherealSwipeCard extends AbstractCard {
    public static final String ID = "Komachi:EtherealSwipe";
    public static final String NAME = "Ethereal Swipe";
    public static final String DESCRIPTION = "ALL enemies loses !M! HP. NL Apply 2 Crackling_Soul to ALL enemies.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final int COST = 2;

    public EtherealSwipeCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 13;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.magicNumber, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new CracklingSoulPower(monster, player, 2), 2));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(7);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


