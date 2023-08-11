package novel.server.writer;

import novel.server.writer.dto.WriterDefaultLoginDto;
import novel.server.writer.dto.WriterDefaultRegisterDto;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.nio.charset.StandardCharsets;

import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

public class WriterMother {
    public static WriterDefaultRegisterDto registerDto() {
        EasyRandomParameters randomParameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .randomize(named("penName").and(ofType(String.class)), new StringRandomizer(30))
                .randomize(named("password").and(ofType(String.class)), new StringRandomizer(100));

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(WriterDefaultRegisterDto.class);
    }

    public static WriterDefaultLoginDto loginDto() {
        EasyRandomParameters randomParameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .randomize(named("penName").and(ofType(String.class)), new StringRandomizer(30))
                .randomize(named("password").and(ofType(String.class)), new StringRandomizer(100));

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(WriterDefaultLoginDto.class);
    }
}
