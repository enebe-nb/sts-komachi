package komachi.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import komachi.KomachiMod;
import komachi.actions.ConsumeOrbAction;
import komachi.actions.PlayACopyAction;
import komachi.patches.KomachiEnum;

public class GhostlyChainCard extends AbstractCard {
    public static final String ID = "Komachi:GhostlyChain";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final int COST = -1;

    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public GhostlyChainCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = 1;
        this.isEthereal = true;

        this.tags.add(KomachiEnum.TAG_CONSUME);
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        int effect = this.energyOnUse;
        effect += this.magicNumber;
        if (player.hasRelic(ChemicalX.ID)) {
            effect += ChemicalX.BOOST;
            player.getRelic(ChemicalX.ID).flash();
        }
        if (effect > 0) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new StrengthPower(monster, -effect), -effect));
                if (!monster.hasPower("Artifact")) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, effect), effect));
                }
            }
        }
        if (!this.isJustCopied) AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(new PlayACopyAction(this, target, this.energyOnUse)));
        if (!this.freeToPlayOnce && effect > 0) player.energy.use(EnergyPanel.totalCount);
        this.isJustCopied = false;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
