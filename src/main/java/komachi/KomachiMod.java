package komachi;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import komachi.cards.AwaitedRestCard;
import komachi.cards.BeheadCard;
import komachi.cards.BoatSanzuCard;
import komachi.cards.BoundCard;
import komachi.cards.ChangeRouteCard;
import komachi.cards.ChildSpiritCard;
import komachi.cards.CollectTipCard;
import komachi.cards.ConfinesAvichiCard;
import komachi.cards.DeathVisionCard;
import komachi.cards.DefendCard;
import komachi.cards.DestroyHopeCard;
import komachi.cards.DoubleCutCard;
import komachi.cards.DrowningCard;
import komachi.cards.EarthSpiritCard;
import komachi.cards.EaseRestCard;
import komachi.cards.EndlessWayCard;
import komachi.cards.EtherealCutCard;
import komachi.cards.EtherealSwipeCard;
import komachi.cards.ExorcismCard;
import komachi.cards.FaithCard;
import komachi.cards.FareCard;
import komachi.cards.FastBreakCard;
import komachi.cards.FearAuraCard;
import komachi.cards.FellowTravelerCard;
import komachi.cards.FinalJudgmentCard;
import komachi.cards.FloatingSpiritCard;
import komachi.cards.FlowRiverCard;
import komachi.cards.FullSwingCard;
import komachi.cards.GhostlyBarrierCard;
import komachi.cards.GhostlyChainCard;
import komachi.cards.GhostlySliceCard;
import komachi.cards.GreedySpiritCard;
import komachi.cards.HellVisionCard;
import komachi.cards.HiganRetourCard;
import komachi.cards.HumanSpiritCard;
import komachi.cards.HungrySpiritCard;
import komachi.cards.InnerSoulCard;
import komachi.cards.LifeSwapCard;
import komachi.cards.LonelySpiritCard;
import komachi.cards.MementoCard;
import komachi.cards.ModestyCard;
import komachi.cards.OfferingsCard;
import komachi.cards.PowerSharingCard;
import komachi.cards.PremonitiveStrikeCard;
import komachi.cards.ReaperScytheCard;
import komachi.cards.RemorseCard;
import komachi.cards.RepentantSoulCard;
import komachi.cards.RiverMistCard;
import komachi.cards.ShortcutCard;
import komachi.cards.ShortenLifeCard;
import komachi.cards.SinRecollectionCard;
import komachi.cards.SinnerSpiritCard;
import komachi.cards.SpiritBladeCard;
import komachi.cards.SpiritualEnergyCard;
import komachi.cards.StrikeCard;
import komachi.cards.TasteDeathCard;
import komachi.cards.ThrowAwayCard;
import komachi.cards.TrueMeditationCard;
import komachi.cards.UnseenSpiritCard;
import komachi.cards.VengefulSpiritCard;
import komachi.cards.WanderingSpiritsCard;
import komachi.cards.WideSwingCard;
import komachi.cards.YesterdayMoneyCard;
import komachi.characters.KomachiCharacter;
import komachi.helpers.AltDamageVariable;
import komachi.helpers.AltMagicNumberVariable;
import komachi.helpers.KeywordData;
import komachi.patches.KomachiEnum;
import komachi.relics.FerryingBoatRelic;
import komachi.relics.GhostlyCloakRelic;
import komachi.relics.OtherSidePierRelic;

@SpireInitializer
public class KomachiMod implements EditCardsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, OnStartBattleSubscriber {
    private static final String ASSETS_FOLDER = "komachiassets/";

    private static final String ATTACK_CARD = "512/bg_attack.png";
    private static final String SKILL_CARD = "512/bg_skill.png";
    private static final String POWER_CARD = "512/bg_power.png";
    private static final String ENERGY_ORB = "512/card_orb.png";
    private static final String ENERGY_ORB_SMALL = "characters/energy_small.png";

    private static final String ATTACK_CARD_PORTRAIT = "1024/bg_attack.png";
    private static final String SKILL_CARD_PORTRAIT = "1024/bg_skill.png";
    private static final String POWER_CARD_PORTRAIT = "1024/bg_power.png";
    private static final String ENERGY_ORB_PORTRAIT = "1024/card_orb.png";

    private static final String CHAR_BUTTON = "characters/button.png";
    private static final String CHAR_PORTRAIT = "characters/portrait.png";

    public static KomachiCharacter character;
    public static int consumedInThisCombat;

    public static final String getResourcePath(String resource) {
        return ASSETS_FOLDER + resource;
    }


    public KomachiMod() {
        BaseMod.subscribe(this);
        
        BaseMod.addColor(KomachiEnum.KOMACHI_COLOR, KomachiEnum.BG_COLOR,
                getResourcePath(ATTACK_CARD), getResourcePath(SKILL_CARD),
                getResourcePath(POWER_CARD), getResourcePath(ENERGY_ORB),
                getResourcePath(ATTACK_CARD_PORTRAIT), getResourcePath(SKILL_CARD_PORTRAIT),
                getResourcePath(POWER_CARD_PORTRAIT), getResourcePath(ENERGY_ORB_PORTRAIT), getResourcePath(ENERGY_ORB_SMALL));
    }

    public static void initialize() {
        new KomachiMod();
    }

    public void receiveEditCharacters() {
        character = new KomachiCharacter();
        BaseMod.addCharacter(character, getResourcePath(CHAR_BUTTON), getResourcePath(CHAR_PORTRAIT), KomachiEnum.KOMACHI_CLASS);
    }

    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new AltDamageVariable());
        BaseMod.addDynamicVariable(new AltMagicNumberVariable());

        BaseMod.addCard(new DefendCard());
        BaseMod.addCard(new StrikeCard());
        BaseMod.addCard(new BoundCard());
        BaseMod.addCard(new WideSwingCard());

        BaseMod.addCard(new AwaitedRestCard());
        BaseMod.addCard(new BeheadCard());
        BaseMod.addCard(new BoatSanzuCard());
        BaseMod.addCard(new ChangeRouteCard());
        BaseMod.addCard(new ChildSpiritCard());
        BaseMod.addCard(new CollectTipCard());
        BaseMod.addCard(new ConfinesAvichiCard());
        BaseMod.addCard(new DeathVisionCard());
        BaseMod.addCard(new DestroyHopeCard());
        BaseMod.addCard(new DoubleCutCard());
        BaseMod.addCard(new DrowningCard());
        BaseMod.addCard(new EarthSpiritCard());
        BaseMod.addCard(new EaseRestCard());
        BaseMod.addCard(new EndlessWayCard());
        BaseMod.addCard(new EtherealCutCard());
        BaseMod.addCard(new EtherealSwipeCard());
        BaseMod.addCard(new ExorcismCard());
        BaseMod.addCard(new FaithCard());
        BaseMod.addCard(new FareCard());
        BaseMod.addCard(new FastBreakCard());
        BaseMod.addCard(new FearAuraCard());
        BaseMod.addCard(new FellowTravelerCard());
        BaseMod.addCard(new FinalJudgmentCard());
        BaseMod.addCard(new FloatingSpiritCard());
        BaseMod.addCard(new FlowRiverCard());
        BaseMod.addCard(new FullSwingCard());
        BaseMod.addCard(new GhostlyBarrierCard());
        BaseMod.addCard(new GhostlyChainCard());
        BaseMod.addCard(new GhostlySliceCard());
        BaseMod.addCard(new GreedySpiritCard());
        BaseMod.addCard(new HellVisionCard());
        BaseMod.addCard(new HiganRetourCard());
        BaseMod.addCard(new HumanSpiritCard());
        BaseMod.addCard(new HungrySpiritCard());
        BaseMod.addCard(new InnerSoulCard());
        BaseMod.addCard(new LifeSwapCard());
        BaseMod.addCard(new LonelySpiritCard());
        BaseMod.addCard(new MementoCard());
        BaseMod.addCard(new ModestyCard());
        BaseMod.addCard(new OfferingsCard());
        BaseMod.addCard(new PowerSharingCard());
        BaseMod.addCard(new PremonitiveStrikeCard());
        BaseMod.addCard(new ReaperScytheCard());
        BaseMod.addCard(new RemorseCard());
        BaseMod.addCard(new RepentantSoulCard());
        BaseMod.addCard(new RiverMistCard());
        BaseMod.addCard(new ShortcutCard());
        BaseMod.addCard(new ShortenLifeCard());
        BaseMod.addCard(new SinnerSpiritCard());
        BaseMod.addCard(new SinRecollectionCard());
        BaseMod.addCard(new SpiritBladeCard());
        BaseMod.addCard(new SpiritualEnergyCard());
        BaseMod.addCard(new TasteDeathCard());
        BaseMod.addCard(new ThrowAwayCard());
        BaseMod.addCard(new TrueMeditationCard());
        BaseMod.addCard(new UnseenSpiritCard());
        BaseMod.addCard(new VengefulSpiritCard());
        BaseMod.addCard(new WanderingSpiritsCard());
        BaseMod.addCard(new YesterdayMoneyCard());
    }

    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new FerryingBoatRelic(), KomachiEnum.KOMACHI_COLOR);
        BaseMod.addRelicToCustomPool(new GhostlyCloakRelic(), KomachiEnum.KOMACHI_COLOR);
        BaseMod.addRelicToCustomPool(new OtherSidePierRelic(), KomachiEnum.KOMACHI_COLOR);
    }

    private String getLanguageAbbr() {
        return "eng";
    }

    public void receiveEditKeywords() {
        final Gson gson = new Gson();
        String language = getLanguageAbbr();

        final String json = Gdx.files.internal("localization/" + language + "/komachi-keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordData[] keywords = gson.fromJson(json, KeywordData[].class);
        for (KeywordData keyword : keywords) {
            BaseMod.addKeyword(keyword.PROPER, keyword.NAMES, keyword.DESCRIPTION);
        }
    }

    public void receiveEditStrings() {
        String language = getLanguageAbbr();

        BaseMod.loadCustomStringsFile(CardStrings.class, "localization/" + language + "/komachi-cards.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "localization/" + language + "/komachi-characters.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, "localization/" + language + "/komachi-orbs.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/" + language + "/komachi-powers.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/" + language + "/komachi-relics.json");
    }

    public void receiveOnBattleStart(AbstractRoom room) {
        consumedInThisCombat = 0;
    }
}



