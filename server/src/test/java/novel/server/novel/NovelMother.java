package novel.server.novel;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.FixtureMonkeyBuilder;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import novel.server.novel.dto.NovelRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NovelMother {
    @Autowired
    FixtureMonkeyBuilder fixtureMonkeyBuilder;

    private static Novel generateNovel() {
        FixtureMonkey fixtureMonkey = fixtureMonkeyBuilder.objectIntrospector()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        fixtureMonkeyBuilder.

    }

    private static NovelRegisterDto generateNovelDto() {

    }
}
