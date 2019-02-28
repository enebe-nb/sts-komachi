package komachi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayACopyAction extends AbstractGameAction {
    private AbstractCard card;

    public PlayACopyAction(AbstractCard card, AbstractCreature target) {
        this.actionType = ActionType.WAIT;
        this.card = card;
        this.target = target;
    }

    public void update() {
        AbstractCard copy = this.card.makeStatEquivalentCopy();
        AbstractDungeon.player.limbo.group.add(copy);
        copy.current_x = card.current_x;
        copy.current_y = card.current_y;
        copy.target_x = (Settings.WIDTH / 2.0F + 200.0F * Settings.scale);
        copy.target_y = (Settings.HEIGHT / 2.0F);
        copy.targetAngle = 0.0F;
        copy.lighten(false);
        copy.drawScale = 0.12F;
        copy.targetDrawScale = 0.75F;
        copy.freeToPlayOnce = true;
        copy.exhaustOnFire = true;
        if (copy instanceof komachi.cards.AbstractCard) ((komachi.cards.AbstractCard)copy).isJustCopied = true;
        if (copy.canUse(AbstractDungeon.player, (AbstractMonster) target)) {
            copy.applyPowers();
            AbstractDungeon.actionManager.addToBottom(new QueueCardAction(copy, this.target));
            AbstractDungeon.actionManager.addToTop(new UnlimboAction(copy));
        } else {
            AbstractDungeon.actionManager.addToTop(new UnlimboAction(copy));
            AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(copy, AbstractDungeon.player.limbo));
        } AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.FAST_MODE ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FAST));

        this.isDone = true;
    }
}
