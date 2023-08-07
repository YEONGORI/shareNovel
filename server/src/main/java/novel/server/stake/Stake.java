package novel.server.stake;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double stakeValue;

//    @OneToOne
//    @JoinColumn(name = "participation_id")
//    private WriterParticipation participation;
}
