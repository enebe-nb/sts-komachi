package komachi.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ApplyRandomDebuffAction;
import komachi.actions.ChainAction;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;
import komachi.powers.CracklingSoulPower;

public class TasteDeathCard extends AbstractCard {
    public static final String ID = "Komachi:TasteDeath";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final int COST = 2;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public TasteDeathCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 4;
        this.altMagicNumber = this.baseAltMagicNumber = 2;
        this.exhaust = true;

        this.tags.add(KomachiEnum.TAG_CONSUME);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        for (AbstractCreature monster : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new CracklingSoulPower(monster, player, this.magicNumber), this.magicNumber));
        }
        ArrayList<AbstractGameAction> chainConsume = new ArrayList<>();
        for (AbstractCreature monster : AbstractDungeon.getMonsters().monsters) {
            for (int i = 0; i < this.altMagicNumber; ++i) {
                chainConsume.add(new ApplyRandomDebuffAction(monster, player, 1));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(new ChainAction(chainConsume)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeAltMagicNumber(1);
        }
    }
}


