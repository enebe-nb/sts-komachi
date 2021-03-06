package komachi.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import komachi.KomachiMod;
import komachi.actions.BoundSpiritAction;
import komachi.patches.KomachiEnum;

public class FloatingSpiritCard extends AbstractCard {
    public static final String ID = "Komachi:FloatingSpirit";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final int COST = -1;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public FloatingSpiritCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/floatingspirit.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.tags.add(KomachiEnum.TAG_BOUND);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        int effect = this.energyOnUse;
        if (this.upgraded) effect++;
        if (player.hasRelic(ChemicalX.ID)) {
            effect += ChemicalX.BOOST;
            player.getRelic(ChemicalX.ID).flash();
        }
        for (int i = 0; i < effect; ++i) {
            AbstractDungeon.actionManager.addToBottom(new BoundSpiritAction());
        }
        if (!this.freeToPlayOnce && effect > 0) player.energy.use(EnergyPanel.totalCount);
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDescription(UPGRADE_DESCRIPTION);
        }
    }
}
