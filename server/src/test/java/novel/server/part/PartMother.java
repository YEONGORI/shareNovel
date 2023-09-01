package novel.server.part;

import novel.server.part.dto.PartCreateReqDTO;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.nio.charset.StandardCharsets;

public class PartMother {
    public static PartCreateReqDTO createDto() {
        EasyRandomParameters randomParameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(1, 5000);

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(PartCreateReqDTO.class);
    }
}
