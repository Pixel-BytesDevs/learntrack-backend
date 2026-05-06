package org.tesis.gestorgrafo.services;

import org.tesis.gestorgrafo.models.Competencia;

public interface IGrafoService {
    Competencia generateGraph(String url);
}
