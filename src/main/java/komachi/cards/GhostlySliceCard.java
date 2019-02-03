package komachi.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import komachi.KomachiMod;
import komachi.actions.ConsumeOrbAction;
import komachi.patches.KomachiEnum;

public class GhostlySliceCard extends AbstractCard {
    public static final String ID = "Komachi:GhostlySlice";
    public static final String NAME = "Ghostly Slice";
    public static final String DESCRIPTION = "Deal !D! damage to a random enemy. NL Consume: Play again. NL Ethereal";
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ALL_ENEMY;
    private static final int COST = 2;

    public GhostlySliceCard() {
        super(ID, NAME, KomachiMod.getResourcePath("cards/beta.png"), COST, DESCRIPTION, TYPE, KomachiEnum.KOMACHI_COLOR, RARITY, TARGET);

        this.baseDamage = 9;
        this.isEthereal = true;
    }

    public void use(AbstractPlayer player, AbstractMonster target) {
        target = AbstractDungeon.getRandomMonster();
        if (target == null) return;

        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (AbstractDungeon.player.hasOrb()) {
            AbstractDungeon.actionManager.addToBottom(new ConsumeOrbAction(1));

            com.megacrit.cardcrawl.cards.AbstractCard tmp = this.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = this.current_x;
            tmp.current_y = this.current_y;
            tmp.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
            tmp.target_y = (Settings.HEIGHT / 2.0F);
            tmp.freeToPlayOnce = true;
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, target, this.energyOnUse));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    //static {
        //cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        //NAME = cardStrings.NAME;
        //DESCRIPTION = cardStrings.DESCRIPTION;
        //UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //}
}


