package komachi.cards;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.LifeSwapAction;
import komachi.patches.KomachiEnum;

public class LifeSwapCard extends AbstractCard {
    public static final String ID = "Komachi:LifeSwap";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final int COST = 1;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

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
            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        }

        if (validMonsters.size() < 2) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[1];
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
            upgradeDescription(UPGRADE_DESCRIPTION);
        }
    }
}


