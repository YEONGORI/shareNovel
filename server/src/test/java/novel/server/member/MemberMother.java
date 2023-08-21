package novel.server.member;

import novel.server.member.dto.MemberDefaultLoginDTO;
import novel.server.member.dto.MemberDefaultRegisterDTO;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.nio.charset.StandardCharsets;

import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

public class MemberMother {
    public static MemberDefaultRegisterDTO registerDto() {
        EasyRandomParameters randomParameters = getRandomParameters();

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(MemberDefaultRegisterDTO.class);
    }

    public static MemberDefaultLoginDTO loginDto() {
        EasyRandomParameters randomParameters = getRandomParameters();

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(MemberDefaultLoginDTO.class);
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
