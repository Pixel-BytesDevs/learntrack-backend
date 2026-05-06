package org.tesis.modulodiagnostico.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.dtos.CuestionarioAlternativaResponse;
import org.tesis.modulodiagnostico.mappers.AlternativaMapper;
import org.tesis.modulodiagnostico.repositories.IAlternativaRepository;
import org.tesis.modulodiagnostico.services.IAlternativaService;

import java.util.List;

@Service
public class AlternativaServiceImpl implements IAlternativaService {
    private final IAlternativaRepository alternativaRepository;
    private final AlternativaMapper alternativaMapper;

    @Autowired
    public AlternativaServiceImpl(IAlternativaRepository alternativaRepository, AlternativaMapper alternativaMapper) {
        this.alternativaRepository = alternativaRepository;
        this.alternativaMapper = alternativaMapper;
    }

    @Override
    public List<CuestionarioAlternativaResponse> getAllAlternativas() {
        return alternativaMapper.toCuestionarioAlternativaResponseList(alternativaRepository.findAll());
    }
}
