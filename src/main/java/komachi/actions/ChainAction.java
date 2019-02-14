package komachi.actions;

import java.util.List;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ChainAction extends AbstractGameAction {
    private List<AbstractGameAction> chain;
  
    public ChainAction(List<AbstractGameAction> chain) {
        this.chain = chain;
    }

    public void update() {
        for (AbstractGameAction action : this.chain) {
            AbstractDungeon.actionManager.addToBottom(action);
        }
        this.isDone = true;
    }
}
