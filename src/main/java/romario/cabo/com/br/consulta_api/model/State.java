package romario.cabo.com.br.consulta_api.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "estado",
        catalog = "consulta_bd",
        uniqueConstraints = @UniqueConstraint(columnNames = {"nome", "sigla"})
)
@Data
public class State implements Serializable {

    private static final long serialVersionUID = 3867135242328320754L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado", unique = true, nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 45, unique = true)
    private String name;

    @Column(name = "sigla", nullable = false, length = 2, unique = true)
    private String acronym;

    @Column(name = "imagem")
    private String image;

    @Column(name = "capital", nullable = false, length = 100)
    private String capital;

    @Column(name = "gentilico", nullable = false)
    private String gentle;

    @Column(name = "area_territorial", nullable = false)
    private int territorialArea;

    @Column(name = "total_municipios", nullable = false)
    private int totalCounties;

    @Column(name = "total_populacao", nullable = false)
    private int totalPopulation;

    @Column(name = "densidade_demografica", nullable = false, precision = 12)
    private double demographicDensity;

    @Column(name = "idh", nullable = false, precision = 13, scale = 3)
    private double idh;

    @Lob
    @Column(name = "territorio_limitrofes", nullable = false)
    private String borderingTerritory;

    @Column(name = "pib", nullable = false, precision = 22)
    private double pib;

    @Lob
    @Column(name = "aspectos_naturais", nullable = false)
    private String naturalAspects;

    @Lob
    @Column(name = "atividades_economicas", nullable = false)
    private String economicActivities;

    @Lob
    @Column(name = "curiosidade")
    private String curiosity;

    @Column(name = "regiao", nullable = false, length = 45)
    private String region;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities = new ArrayList<>();
}
