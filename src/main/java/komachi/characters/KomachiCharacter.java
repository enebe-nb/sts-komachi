package komachi.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import komachi.KomachiMod;
import komachi.cards.AbstractCard;
import komachi.cards.BoundCard;
import komachi.cards.DefendCard;
import komachi.cards.StrikeCard;
import komachi.cards.WideSwingCard;
import komachi.patches.KomachiEnum;
import komachi.relics.FerryingBoatRelic;

import java.util.ArrayList;

public class KomachiCharacter extends CustomPlayer {
    public static final String NAME = "Onozuka Komachi";
    public static final String DESCRIPTION = "Widely known for being a slacker shinigami and a slow worker. NL Her main job is to carry the souls through the Sanzu River. NL But sometimes this can wait.";
    public static final String HEART_TEXT = " ... ";

    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int ORB_SLOTS = 4;
    public static final int STARTING_GOLD = 99;
    public static final int HAND_SIZE = 5;

    public static Color cardColor = new Color(0.0F, 0.1F, 0.0F, 1.0F);
    private static final String[] energyOrb = {
        KomachiMod.getResourcePath("characters/L3A.png"),
        KomachiMod.getResourcePath("characters/L2A.png"),
        KomachiMod.getResourcePath("characters/L1A.png"),
        KomachiMod.getResourcePath("characters/L0.png"),
        KomachiMod.getResourcePath("characters/L3B.png"),
        KomachiMod.getResourcePath("characters/L2B.png"),
        KomachiMod.getResourcePath("characters/L1B.png"),
    };

    public KomachiCharacter() {
        super(NAME, KomachiEnum.KOMACHI_CLASS,
            new CustomEnergyOrb(energyOrb, KomachiMod.getResourcePath("characters/energy_small.png"), new float[]{0.0F, 0.0F, 0.0F, -40.0F, 20.0F}),
            new SpineAnimation(KomachiMod.getResourcePath("characters/Koma.atlas"), KomachiMod.getResourcePath("characters/Koma.json"), 2.0F));

        this.initializeClass(null, KomachiMod.getResourcePath("characters/shoulder2.png"), KomachiMod.getResourcePath("characters/shoulder.png"), KomachiMod.getResourcePath("characters/corpse.png"), this.getLoadout(), 0.0F, 0.0F, 300.0F, 180.0F, new EnergyManager(3));
        this.state.setAnimation(0, "Idle", true);

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 220.0F * Settings.scale);
    }

    public ArrayList<String> getStartingDeck() {
		ArrayList<String> deck = new ArrayList<>();
		deck.add(StrikeCard.ID);
		deck.add(StrikeCard.ID);
		deck.add(StrikeCard.ID);
		deck.add(StrikeCard.ID);
		deck.add(DefendCard.ID);
		deck.add(DefendCard.ID);
		deck.add(DefendCard.ID);
		deck.add(DefendCard.ID);
		deck.add(BoundCard.ID);
		deck.add(WideSwingCard.ID);
		return deck;
	}
	
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> relics = new ArrayList<>();
		relics.add(FerryingBoatRelic.ID);
		UnlockTracker.markRelicAsSeen(FerryingBoatRelic.ID);
		return relics;
	}

	public CharSelectInfo getLoadout() {
		return new CharSelectInfo(NAME, DESCRIPTION, STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, HAND_SIZE, this, getStartingRelics(), getStartingDeck(), false);
	}

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
            AbstractGameAction.AttackEffect.SLASH_VERTICAL,
            AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
            AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
            AbstractGameAction.AttackEffect.SLASH_HEAVY
        };
    }

    public AbstractPlayer newInstance() { return new KomachiCharacter(); }
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("BUFF_1", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    public int getAscensionMaxHPLoss() { return 10; }
    public AbstractCard.CardColor getCardColor() { return KomachiEnum.KOMACHI_COLOR; }
    public Color getCardRenderColor() { return cardColor; }
    public Color getCardTrailColor() { return cardColor.cpy(); }
    public String getCustomModeCharacterButtonSoundKey() { return "BUFF_1"; }
    public BitmapFont getEnergyNumFont() { return FontHelper.energyNumFontRed; }
    public String getLocalizedCharacterName() { return NAME; }
    public Color getSlashAttackColor() { return Color.BLACK; }
    public String getSpireHeartText() { return HEART_TEXT; }
    public AbstractCard getStartCardForEvent() { return new StrikeCard(); }
    public String getTitle(PlayerClass playerClass) { return NAME; }
    public String getVampireText() { return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[1]; }
}


