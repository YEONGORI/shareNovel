package novel.server.stake;

import org.springframework.stereotype.Service;


@Service
public interface StakeService {
    Stake adjustStake();
}
