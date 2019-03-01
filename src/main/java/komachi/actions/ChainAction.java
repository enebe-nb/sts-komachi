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
        for (int i = this.chain.size(); i-- > 0; ) {
            AbstractDungeon.actionManager.addToTop(this.chain.get(i));
        }
        this.isDone = true;
    }
}
