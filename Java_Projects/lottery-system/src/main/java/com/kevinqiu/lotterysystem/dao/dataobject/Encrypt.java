package com.kevinqiu.lotterysystem.dao.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Encrypt implements Serializable {
    private String value;
}
