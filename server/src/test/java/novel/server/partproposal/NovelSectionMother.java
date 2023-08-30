package novel.server.partproposal;

import novel.server.partproposal.dto.PartProposalPostReqDTO;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;

import java.nio.charset.StandardCharsets;

import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

public class NovelSectionMother {
    public static PartProposalPostReqDTO createDto() {
        EasyRandomParameters randomParameters = new EasyRandomParameters()
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(1, 5000)
                .randomize(named("part").and(ofType(Integer.class)), new IntegerRangeRandomizer(0, 5000));

        EasyRandom easyRandom = new EasyRandom(randomParameters);
        return easyRandom.nextObject(PartProposalPostReqDTO.class);
    }
}
