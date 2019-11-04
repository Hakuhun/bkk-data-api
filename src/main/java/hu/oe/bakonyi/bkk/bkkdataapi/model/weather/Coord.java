package hu.oe.bakonyi.bkk.bkkdataapi.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Builder
@AllArgsConstructor
@NoArgsConstructor
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-09-02T19:31:53.527Z[GMT]")
public class Coord   {
  @JsonProperty("lon")
  private Double lon = null;

  @JsonProperty("lat")
  private Double lat = null;

}
