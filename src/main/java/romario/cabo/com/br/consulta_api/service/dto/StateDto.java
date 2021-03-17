package romario.cabo.com.br.consulta_api.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StateDto implements Serializable {
    private static final long serialVersionUID = 5938989189971824038L;

    private Long id;
    private String name;
    private String acronym;
    private String urlImage;
    private String capital;
    private String gentle;
    private int territorialArea;
    private int totalCounties;
    private int totalPopulation;
    private double demographicDensity;
    private double idh;
    private String borderingTerritory;
    private double pib;
    private String naturalAspects;
    private String economicActivities;
    private String curiosity;
    private String region;
}
