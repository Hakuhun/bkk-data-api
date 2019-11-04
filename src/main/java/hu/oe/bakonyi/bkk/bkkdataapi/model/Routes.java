package hu.oe.bakonyi.bkk.bkkdataapi.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Routes implements Serializable {

    private Long id;

    private String title;

    private String routeCode;

    private String routeType;
}
