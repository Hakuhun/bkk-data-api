package hu.oe.bakonyi.bkk.bkkdataapi.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-09-02T19:31:53.527Z[GMT]")
public class Sys   {
  @JsonProperty("type")
  private Integer type = null;

  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("message")
  private BigDecimal message = null;

  @JsonProperty("country")
  private String country = null;

  @JsonProperty("sunrise")
  private Integer sunrise = null;

  @JsonProperty("sunset")
  private Integer sunset = null;
}
