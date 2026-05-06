package org.tesis.modulodiagnostico.config.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

// Migrar a flyway en un futuro
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            this.executeSchemaUpdates();
            this.executeInsertPreguntas();
            this.executeInsertEstilos();
            this.executeInsertAlternativas();
        } catch (Exception e) {
            log.error("ERROR CRÍTICO EN LA INICIALIZACIÓN DE DATOS: {}", e.getMessage());
            throw e; // Esto detendrá la app si falla la DB
        }
    }

    // MÉTODO AUXILIAR CLAVE: Lee el recurso como un Stream (compatible con JAR)
    private String loadSql(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    private void executeSchemaUpdates() throws IOException {
        log.info("APLICANDO ACTUALIZACIONES DE ESQUEMA ...");

        jdbcTemplate.execute(loadSql("database/04_ALTER_QUESTIONS_TESTS.sql"));
        jdbcTemplate.execute(loadSql("database/05_NORMALIZE_DOMAIN_LEVEL.sql"));
        jdbcTemplate.execute(loadSql("database/06_ADD_TOPIC_FLAGS.sql"));
        jdbcTemplate.execute(loadSql("database/07_ADD_CONTINUOUS_RECOMMENDATION_ASSIGNMENTS.sql"));
    }

    private void executeInsertPreguntas() throws IOException {
        Long count = jdbcTemplate.queryForObject("select count(*) from preguntas", Long.class);
        if (count == 0) {
            log.info("TABLA PREGUNTAS VACÍA - EJECUTANDO SCRIPT ...");
            jdbcTemplate.execute(loadSql("database/01_INSERT_PREGUNTAS.sql"));
        }
    }

    private void executeInsertAlternativas() throws IOException {
        Long count = jdbcTemplate.queryForObject("select count(*) from alternativas", Long.class);
        if (count == 0) {
            log.info("TABLA ALTERNATIVAS VACÍA - EJECUTANDO SCRIPT ...");
            jdbcTemplate.execute(loadSql("database/02_INSERT_RESPUESTAS.sql"));
        }
    }

    private void executeInsertEstilos() throws IOException {
        Long count = jdbcTemplate.queryForObject("select count(*) from estilos_vark", Long.class);
        if (count == 0) {
            log.info("TABLA ESTILOS VARK VACÍA - EJECUTANDO SCRIPT ...");
            jdbcTemplate.execute(loadSql("database/03_INSERT_ESTILOS.sql"));
        }
    }
}
