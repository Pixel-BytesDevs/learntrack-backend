package org.tesis.gestorgrafo.dtos.base;

import lombok.Data;

@Data
public class ResponseAPI<T> {
    private String status;
    private T data;
}
