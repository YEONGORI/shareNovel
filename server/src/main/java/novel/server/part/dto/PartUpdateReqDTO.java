package novel.server.part.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@ToString
public class PartUpdateReqDTO {
    @Min(0)
    @Max(1000)
    private Integer partNum;


    @NotBlank
    @Length(max = 5000)
    private String content;
}
