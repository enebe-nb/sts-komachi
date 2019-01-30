package komachi;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
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
import komachi.cards.ChildSpiritCard;
import komachi.cards.CollectTipCard;
import komachi.cards.ConfinesAvichiCard;
import komachi.cards.DefendCard;
import komachi.cards.DoubleCutCard;
import komachi.cards.DrowningCard;
import komachi.cards.EarthSpiritCard;
import komachi.cards.EaseRestCard;
import komachi.cards.EndlessWayCard;
import komachi.cards.EtherealCutCard;
import komachi.cards.ExorcismCard;
import komachi.cards.FaithCard;
import komachi.cards.FareCard;
import komachi.cards.FastBreakCard;
import komachi.cards.FellowTravelerCard;
import komachi.cards.FinalJudgmentCard;
import komachi.cards.FloatingSpiritCard;
import komachi.cards.FlowRiverCard;
import komachi.cards.GhostlyBarrierCard;
import komachi.cards.GhostlyChainCard;
import komachi.cards.GhostlySliceCard;
import komachi.cards.GreedySpiritCard;
import komachi.cards.HumanSpiritCard;
import komachi.cards.HungrySpiritCard;
import komachi.cards.LifeSwapCard;
import komachi.cards.LonelySpiritCard;
import komachi.cards.MementoCard;
import komachi.cards.ModestyCard;
import komachi.cards.OfferingsCard;
import komachi.cards.PhantomPainCard;
import komachi.cards.PowerSharingCard;
import komachi.cards.ReaperScytheCard;
import komachi.cards.RepentantSoulCard;
import komachi.cards.ShortcutCard;
import komachi.cards.ShortenLifeCard;
import komachi.cards.SinnerSpiritCard;
import komachi.cards.SliceThroughCard;
import komachi.cards.SpiritBladeCard;
import komachi.cards.StrikeCard;
import komachi.cards.TasteDeathCard;
import komachi.cards.TiringJourneyCard;
import komachi.cards.TrueMeditationCard;
import komachi.cards.UnseenSpiritCard;
import komachi.cards.WanderingSpiritsCard;
import komachi.cards.WideSwingCard;
import komachi.cards.YesterdayMoneyCard;
import komachi.characters.KomachiCharacter;
import komachi.helpers.AltDamageVariable;
import komachi.patches.KomachiEnum;
import komachi.relics.FerryingBoatRelic;
import komachi.relics.GhostlyCloakRelic;
import komachi.relics.OtherSidePierRelic;

@com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
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

        BaseMod.addCard(new DefendCard());
        BaseMod.addCard(new StrikeCard());
        BaseMod.addCard(new BoundCard());
        BaseMod.addCard(new WideSwingCard());

        BaseMod.addCard(new AwaitedRestCard());
        BaseMod.addCard(new BeheadCard());
        BaseMod.addCard(new BoatSanzuCard());
        BaseMod.addCard(new ChildSpiritCard());
        BaseMod.addCard(new CollectTipCard());
        BaseMod.addCard(new ConfinesAvichiCard());
        BaseMod.addCard(new DoubleCutCard());
        BaseMod.addCard(new DrowningCard());
        BaseMod.addCard(new EarthSpiritCard());
        BaseMod.addCard(new EaseRestCard());
        BaseMod.addCard(new EndlessWayCard());
        BaseMod.addCard(new EtherealCutCard());
        BaseMod.addCard(new ExorcismCard());
        BaseMod.addCard(new FaithCard());
        BaseMod.addCard(new FareCard());
        BaseMod.addCard(new FastBreakCard());
        BaseMod.addCard(new FellowTravelerCard());
        BaseMod.addCard(new FinalJudgmentCard());
        BaseMod.addCard(new FloatingSpiritCard());
        BaseMod.addCard(new FlowRiverCard());
        BaseMod.addCard(new GhostlyBarrierCard());
        BaseMod.addCard(new GhostlyChainCard());
        BaseMod.addCard(new GhostlySliceCard());
        BaseMod.addCard(new GreedySpiritCard());
        BaseMod.addCard(new HumanSpiritCard());
        BaseMod.addCard(new HungrySpiritCard());
        BaseMod.addCard(new LifeSwapCard());
        BaseMod.addCard(new LonelySpiritCard());
        BaseMod.addCard(new MementoCard());
        BaseMod.addCard(new ModestyCard());
        BaseMod.addCard(new OfferingsCard());
        BaseMod.addCard(new PhantomPainCard());
        BaseMod.addCard(new PowerSharingCard());
        BaseMod.addCard(new ReaperScytheCard());
        BaseMod.addCard(new RepentantSoulCard());
        BaseMod.addCard(new ShortcutCard());
        BaseMod.addCard(new ShortenLifeCard());
        BaseMod.addCard(new SinnerSpiritCard());
        BaseMod.addCard(new SliceThroughCard());
        BaseMod.addCard(new SpiritBladeCard());
        BaseMod.addCard(new TasteDeathCard());
        BaseMod.addCard(new TiringJourneyCard());
        BaseMod.addCard(new TrueMeditationCard());
        BaseMod.addCard(new UnseenSpiritCard());
        BaseMod.addCard(new WanderingSpiritsCard());
        BaseMod.addCard(new YesterdayMoneyCard());
    }

    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new FerryingBoatRelic(), KomachiEnum.KOMACHI_COLOR);
        BaseMod.addRelicToCustomPool(new GhostlyCloakRelic(), KomachiEnum.KOMACHI_COLOR);
        BaseMod.addRelicToCustomPool(new OtherSidePierRelic(), KomachiEnum.KOMACHI_COLOR);
    }

    public void receiveEditKeywords() {
        BaseMod.addKeyword("Spirit", new String[]{"spirit", "spirits"}, "When bound and at start of your turn, apply a random #yDebuff");
        BaseMod.addKeyword("Consume", new String[]{"consume"}, "Remove a spirit to apply the card effect.");
        BaseMod.addKeyword("Crackling Soul", new String[]{"crackling soul", "crackling_soul"}, "At next turn the enemy loses HP for each debuff.");
        BaseMod.addKeyword("Rest", new String[]{"rest"}, "At end of combat heals, reduce the value each turn.");
    }

    public void receiveEditStrings() {
        String language = "eng";

        String relicStrings = Gdx.files.internal("localization/" + language + "/komachi-relics.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
    }

    public void receiveOnBattleStart(AbstractRoom room) {
        consumedInThisCombat = 0;
    }
}



