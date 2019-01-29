package komachi.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.BoundSpiritAction;
import komachi.patches.KomachiEnum;

public class ChildSpiritCard extends AbstractCard {
    public static final String ID = "Komachi:ChildSpirit";
    public static final String NAME = "Bound Child Spirit";
    public static final String DESCRIPTION = "Bind a Spirit. NL Heal !M! for each bounded Spirit. NL Exhaust.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final int COST = 1;

    public ChildSpiritCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;
        this.tags.add(KomachiEnum.TAG_BOUND);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        int orbs = player.filledOrbCount();
        if (player.orbs.size() > orbs) ++orbs;
        AbstractDungeon.actionManager.addToBottom(new BoundSpiritAction());
        AbstractDungeon.actionManager.addToBottom(new HealAction(player, player, this.magicNumber * orbs));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
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
