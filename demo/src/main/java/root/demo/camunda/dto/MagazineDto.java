package root.demo.camunda.dto;


import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MagazineDto {

    private Long id;

    private String naziv;

    private String issn;

    private Long cena;

    private String koPlaca;

    private String processInstanceId;

    private Boolean enabled;

    private String glavniUrednik;

    private LinkedHashMap<String, String> urednici = new LinkedHashMap<>();

    private LinkedHashMap<String, ArrayList<String>> recezenti = new LinkedHashMap<>();

}

