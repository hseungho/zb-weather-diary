package com.zerobase.hseungho.weatherdiary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "memo")
public class Memo extends BaseEntity {

    private String text;

    public Memo(int id, String text) {
        this.id = id;
        this.text = text;
    }
}
