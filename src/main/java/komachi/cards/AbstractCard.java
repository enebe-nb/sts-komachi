package komachi.cards;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;

public abstract class AbstractCard extends CustomCard {
    public int baseAltDamage = -1;
    public int altDamage = -1;
    public int[] multiAltDamage;
    public boolean upgradeAltDamage = false;
    public boolean isAltDamageModified = false;

    public AbstractCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (this.baseAltDamage < 0) return;

        AbstractPlayer player = AbstractDungeon.player;
        this.isAltDamageModified = false;
        if (!this.isMultiDamage) {
            float tmp = this.baseAltDamage;
            if ((AbstractDungeon.player.hasRelic("WristBlade")) && ((this.costForTurn == 0) || (this.freeToPlayOnce))) {
                tmp += 3.0F;
            }
            for (AbstractPower p : player.powers) tmp = p.atDamageGive(tmp, this.damageTypeForTurn);
            for (AbstractPower p : player.powers) tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn);
            if (tmp < 0.0F) tmp = 0.0F;
            if (this.baseAltDamage != (int)tmp) this.isAltDamageModified = true;
            this.altDamage = MathUtils.floor(tmp);
        } else {
            ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            float[] tmp = new float[m.size()];
            for (int i = 0; i < tmp.length; i++) tmp[i] = this.baseAltDamage;
            for (int i = 0; i < tmp.length; i++) {
                if ((AbstractDungeon.player.hasRelic("WristBlade")) && ((this.costForTurn == 0) || (this.freeToPlayOnce))) {
                    tmp[i] += 3.0F;
                }
                for (AbstractPower p : player.powers) tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn);
            }
            for (int i = 0; i < tmp.length; i++) {
                for (AbstractPower p : player.powers) tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn);
            }
            for (int i = 0; i < tmp.length; i++) if (tmp[i] < 0.0F) tmp[i] = 0.0F;
            this.multiAltDamage = new int[tmp.length];
            for (int i = 0; i < tmp.length; i++) {
                if (this.baseAltDamage != (int)tmp[i]) this.isAltDamageModified = true;
                this.multiAltDamage[i] = MathUtils.floor(tmp[i]);
            }
            this.altDamage = this.multiAltDamage[0];
        }        
    }

    @Override
    public void calculateCardDamage(AbstractMonster monster) {
        super.calculateCardDamage(monster);
        if (this.baseAltDamage < 0) return;

        AbstractPlayer player = AbstractDungeon.player;
        this.isAltDamageModified = false;
        if ((!this.isMultiDamage) && (monster != null)) {
            float tmp = this.baseAltDamage;
            if ((AbstractDungeon.player.hasRelic("WristBlade")) && ((this.costForTurn == 0) || (this.freeToPlayOnce))) {
                tmp += 3.0F;
            }
            for (AbstractPower p : player.powers) tmp = p.atDamageGive(tmp, this.damageTypeForTurn);
            for (AbstractPower p : monster.powers) tmp = p.atDamageReceive(tmp, this.damageTypeForTurn);
            for (AbstractPower p : player.powers) tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn);
            for (AbstractPower p : monster.powers) tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn);
            if (tmp < 0.0F) tmp = 0.0F;
            if (this.baseAltDamage!= (int)tmp) this.isAltDamageModified = true;
            this.altDamage = MathUtils.floor(tmp);
        } else {
            ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            float[] tmp = new float[m.size()];
            for (int i = 0; i < tmp.length; i++) tmp[i] = this.baseAltDamage;
            for (int i = 0; i < tmp.length; i++) {
                if ((AbstractDungeon.player.hasRelic("WristBlade")) && ((this.costForTurn == 0) || (this.freeToPlayOnce))) {
                    tmp[i] += 3.0F;
                }
                for (AbstractPower p : player.powers) tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn);
            }
            for (int i = 0; i < tmp.length; i++) {
                for (AbstractPower p : ((AbstractMonster)m.get(i)).powers) {
                    if ((!((AbstractMonster)m.get(i)).isDying) && (!((AbstractMonster)m.get(i)).isEscaping)) {
                        tmp[i] = p.atDamageReceive(tmp[i], this.damageTypeForTurn);
                    }
                }
            }
            for (int i = 0; i < tmp.length; i++) {
                for (AbstractPower p : player.powers) tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn);
            }
            for (int i = 0; i < tmp.length; i++) {
                for (AbstractPower p : ((AbstractMonster)m.get(i)).powers) {
                    if ((!((AbstractMonster)m.get(i)).isDying) && (!((AbstractMonster)m.get(i)).isEscaping)) {
                        tmp[i] = p.atDamageFinalReceive(tmp[i], this.damageTypeForTurn);
                    }
                }
            }
            for (int i = 0; i < tmp.length; i++) if (tmp[i] < 0.0F) tmp[i] = 0.0F;
            this.multiAltDamage = new int[tmp.length];
            for (int i = 0; i < tmp.length; i++) {
                if (this.baseAltDamage != (int)tmp[i]) this.isAltDamageModified = true;
                this.multiAltDamage[i] = MathUtils.floor(tmp[i]);
            }
            this.altDamage = this.multiAltDamage[0];
        }
    }

    public void upgradeAltDamage(int amount) {
        this.altDamage = this.baseAltDamage += amount;
        if (this.altDamage > this.baseAltDamage || amount > 0) this.isAltDamageModified = true;
    }

    public void upgradeDescription(String newDescription) {
        this.rawDescription = newDescription;
        initializeDescription();
    }
}