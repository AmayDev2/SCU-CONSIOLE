package com.amay.scu.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.JavaBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class SLELocationListObject  {


    public static Map<String,SLELocation> list =new HashMap<>();


    @Data
    @NoArgsConstructor
    public static class SLELocation {
        private double xAxis;
        private double yAxis;
        private String name;
        private String status;
        private String type;
        private String id;

    }
}
