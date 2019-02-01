package komachi.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ApplyRandomDebuffAction;
import komachi.patches.KomachiEnum;

public class RiverMistCard extends AbstractCard {
    public static final String ID = "Komachi:RiverMist";
    public static final String NAME = "River's Mist";
    public static final String DESCRIPTION = "Gain !B! block. Apply to ALL enemies a random debuff with !M! power.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL;
    private static final int COST = 2;

    public RiverMistCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);
        
        this.baseBlock = 12;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyRandomDebuffAction(monster, player, this.magicNumber));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(6);
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


