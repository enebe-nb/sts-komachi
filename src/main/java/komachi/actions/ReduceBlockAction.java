package komachi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ReduceBlockAction extends AbstractGameAction {
    public ReduceBlockAction(AbstractCreature target, AbstractCreature source, int amount) {
        setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.BLOCK;
        this.duration = 0.25F;
    }
  
    public void update() {
        if ((!this.target.isDying) && (!this.target.isDead) && (this.duration == 0.25F) && (this.target.currentBlock > 0)) {
            this.target.loseBlock(this.amount);
        } tickDuration();
    }
}
