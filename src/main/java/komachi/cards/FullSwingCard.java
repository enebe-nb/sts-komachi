package komachi.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import komachi.KomachiMod;
import komachi.actions.ChainAction;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;

public class FullSwingCard extends AbstractCard {
    public static final String ID = "Komachi:FullSwing";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = -1;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public FullSwingCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = 4;
        this.baseAltDamage = 5;
        this.isMultiDamage = true;

        this.tags.add(KomachiEnum.TAG_CONSUME);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        int effect = this.energyOnUse;
        if (player.hasRelic(ChemicalX.ID)) {
            effect += ChemicalX.BOOST;
            player.getRelic(ChemicalX.ID).flash();
        }
        if (effect > 0) {
            ArrayList<AbstractGameAction> chainConsume = new ArrayList<>();
            ArrayList<AbstractGameAction> chainNormal = new ArrayList<>();
            for (int i = 0; i < effect; ++i) {
                chainConsume.add(new DamageAllEnemiesAction(player, this.multiAltDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                chainNormal.add(new DamageAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
            }

            AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(
                new ChainAction(chainConsume),
                new ChainAction(chainNormal)
            ));
        }
        if (!this.freeToPlayOnce && effect > 0) player.energy.use(EnergyPanel.totalCount);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeAltDamage(3);
        }
    }
}


