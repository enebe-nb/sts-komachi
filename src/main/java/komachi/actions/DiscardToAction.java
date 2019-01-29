package komachi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardToAction extends AbstractGameAction {
    private AbstractGameAction action;
  
    public DiscardToAction(int amount, AbstractGameAction action) {
        setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.action = action;
    }
  
    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.handCardSelectScreen.open("Discard", this.amount, false, true);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                AbstractDungeon.actionManager.addToTop(this.action);
                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    AbstractDungeon.player.hand.moveToDiscardPile(card);
                    GameActionManager.incrementDiscard(false);
                    card.triggerOnManualDiscard();
                }                
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }
}
