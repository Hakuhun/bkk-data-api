package hu.oe.bakonyi.bkk.bkkdataapi.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-09-02T19:31:53.527Z[GMT]")
public class Model200 {
  @JsonProperty("coord")
  private Coord coord = null;

  @JsonProperty("weather")
  @Valid
  private List<Weather> weather = null;

  @JsonProperty("base")
  private String base = null;

  @JsonProperty("main")
  private Main main = null;

  @JsonProperty("visibility")
  private Integer visibility = null;

  @JsonProperty("wind")
  private Wind wind = null;

  @JsonProperty("clouds")
  private Clouds clouds = null;

  @JsonProperty("rain")
  private Rain rain = null;

  @JsonProperty("snow")
  private Snow snow = null;

  @JsonProperty("dt")
  private Integer dt = null;

  @JsonProperty("sys")
  private Sys sys = null;

  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("cod")
  private Integer cod = null;
}
