package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import komachi.KomachiMod;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;

public class FullSwingCard extends AbstractCard {
    public static final String ID = "Komachi:FullSwing";
    public static final String NAME = "Full Swing";
    public static final String DESCRIPTION = "Deal !D! damage X times. Consume: Deal !ALTDMG! damage to ALL enemies X times instead.";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final int COST = -1;

    public FullSwingCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = 4;
        this.baseAltDamage = 5;
        this.isMultiDamage = true;
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
        if (!AbstractDungeon.player.hasOrb()) {
            for (int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(1));
            for (int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, this.multiAltDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeAltDamage(3);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


