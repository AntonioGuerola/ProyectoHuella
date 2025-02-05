package org.example.model.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum Tipo implements Serializable {
    DIARIA("diaria"),
    SEMANAL("semanal"),
    MENSUAL("mensual"),
    ANUAL("anual");

    private final String dbField;

    Tipo(String url){
        this.dbField = url;
    }

    @XmlElement
    public String getUrl() {
        return dbField;
    }
}