package novel.server.member;

import novel.server.member.dto.MemberDefaultLoginDto;
import novel.server.member.dto.MemberDefaultRegisterDto;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.nio.charset.StandardCharsets;

import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

public class MemberMother {
    public static MemberDefaultRegisterDto registerDto() {
        EasyRandomParameters randomParameters = getRandomParameters();

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(MemberDefaultRegisterDto.class);
    }

    public static MemberDefaultLoginDto loginDto() {
        EasyRandomParameters randomParameters = getRandomParameters();

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(MemberDefaultLoginDto.class);
    }

    public static Member generateMember() {
        EasyRandomParameters randomParameters = getRandomParameters();

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(Member.class);
    }

    private static EasyRandomParameters getRandomParameters() {
        return new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .randomize(named("penName").and(ofType(String.class)), new StringRandomizer(30))
                .randomize(named("password").and(ofType(String.class)), new StringRandomizer(100));
    }
}
