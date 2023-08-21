package novel.server.novel;

import novel.server.novel.dto.NovelRegisterDTO;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.nio.charset.StandardCharsets;

import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

public class NovelMother {
    public static NovelRegisterDTO registerDto() {
        EasyRandomParameters randomParameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .randomize(named("title").and(ofType(String.class)), new StringRandomizer(100))
                .randomize(named("plot").and(ofType(String.class)), new StringRandomizer(500))
                .randomize(named("theme").and(ofType(String.class)), new StringRandomizer(400))
                .randomize(named("characters").and(ofType(String.class)), new StringRandomizer(100))
                .randomize(named("background").and(ofType(String.class)), new StringRandomizer(100))
                .randomize(named("event").and(ofType(String.class)), new StringRandomizer(100))
                .randomize(named("penName").and(ofType(String.class)), new StringRandomizer(30));

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(NovelRegisterDTO.class);


    }

    public static Novel generateNovel() {
        EasyRandomParameters randomParameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .randomize(named("title").and(ofType(String.class)), new StringRandomizer(100))
                .randomize(named("plot").and(ofType(String.class)), new StringRandomizer(1000))
                .randomize(named("theme").and(ofType(String.class)), new StringRandomizer(500))
                .randomize(named("characters").and(ofType(String.class)), new StringRandomizer(100))
                .randomize(named("background").and(ofType(String.class)), new StringRandomizer(100))
                .randomize(named("event").and(ofType(String.class)), new StringRandomizer(100));

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(Novel.class);
    }
}
