package komachi.cards;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.LifeSwapAction;
import komachi.patches.KomachiEnum;

public class LifeSwapCard extends AbstractCard {
    public static final String ID = "Komachi:LifeSwap";
    public static final String NAME = "Life Swap";
    public static final String DESCRIPTION = "Exchange two random enemies life bars. NL Exhaust.";
    public static final String UPGRADE_DESCRIPTION = "Exchange enemy life bar with a random enemy. NL Exhaust.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final int COST = 1;

    public LifeSwapCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.exhaust = true;
    }

    public boolean cardPlayable(AbstractMonster target) {
        ArrayList<AbstractMonster> validMonsters = new ArrayList<AbstractMonster>();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDeadOrEscaped()) validMonsters.add(monster);
        }
        
        if (target != null && !validMonsters.contains(target)) {
            this.cantUseMessage = "Invalid target";
            return false;
        }

        if (validMonsters.size() < 2) {
            this.cantUseMessage = "Requires at least 2 targets";
            return false;
        }

        return true;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        ArrayList<AbstractMonster> validMonsters = new ArrayList<AbstractMonster>();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDeadOrEscaped()) validMonsters.add(monster);
        }

        if (validMonsters.size() >= 2) {
            if (target != null) {
                if (!validMonsters.contains(target)) return;
                validMonsters.remove(target);
            } else {
                target = validMonsters.get(MathUtils.random(0, validMonsters.size() - 1));
                validMonsters.remove(target);
            }

            AbstractMonster target2 = validMonsters.get(MathUtils.random(0, validMonsters.size() - 1));
            AbstractDungeon.actionManager.addToBottom(new LifeSwapAction(target, target2));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.target = CardTarget.ENEMY;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


