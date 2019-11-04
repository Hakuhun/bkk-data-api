package hu.oe.bakonyi.bkk.bkkdataapi.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * Rain
 */
@Validated
@Data
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-09-02T19:31:53.527Z[GMT]")
public class Rain   {
  @JsonProperty("3h")
  private Double _3h = 0.0;
}
