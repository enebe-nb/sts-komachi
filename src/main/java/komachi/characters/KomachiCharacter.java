package komachi.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
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
import java.util.List;

public class KomachiCharacter extends CustomPlayer {
    public static final String NAME = "OnozukaKomachi";
    private static CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(NAME);

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

    @Override
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

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> relics = new ArrayList<>();
        relics.add(FerryingBoatRelic.ID);
        UnlockTracker.markRelicAsSeen(FerryingBoatRelic.ID);
        return relics;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0], characterStrings.TEXT[0], STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, HAND_SIZE, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
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

    @Override
    public AbstractPlayer newInstance() { return new KomachiCharacter(); }
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("BUFF_1", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override public int getAscensionMaxHPLoss() { return 5; }
    @Override public CharacterStrings getCharacterString() { return characterStrings; }
    @Override public String getCustomModeCharacterButtonSoundKey() { return "BUFF_1"; }
    @Override public BitmapFont getEnergyNumFont() { return FontHelper.energyNumFontRed; }
    @Override public String getLocalizedCharacterName() { return characterStrings.NAMES[0]; }
    @Override public String getSpireHeartText() { return characterStrings.TEXT[1]; }
    @Override public AbstractCard getStartCardForEvent() { return new BoundCard(); }
    @Override public String getTitle(PlayerClass playerClass) { return characterStrings.NAMES[0]; }
    @Override public String getVampireText() { return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[1]; }

    @Override public AbstractCard.CardColor getCardColor() { return KomachiEnum.KOMACHI_COLOR; }
    @Override public Color getCardRenderColor() { return Color.SCARLET; }
    @Override public Color getCardTrailColor() { return Color.SCARLET.cpy(); }
    @Override public Color getSlashAttackColor() { return Color.BLACK; }

    @Override
    public Texture getCutsceneBg() {
        return super.getCutsceneBg();
        //return ImageMaster.loadImage("images/scenes/redBg.jpg");
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        return super.getCutscenePanels();
        //List<CutscenePanel> panels = new ArrayList<>();
        //panels.add(new CutscenePanel("images/scenes/ironclad1.png", "ATTACK_HEAVY"));
        //panels.add(new CutscenePanel("images/scenes/ironclad2.png"));
        //panels.add(new CutscenePanel("images/scenes/ironclad3.png"));
        //return panels;
    }
}


