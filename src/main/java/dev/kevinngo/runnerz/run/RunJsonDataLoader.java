package dev.kevinngo.runnerz.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);
    private final ObjectMapper objectMapper;
    private final JdbcClientRunRepository runRepository;

    // Inject the JSON file from classpath
    @Value("classpath:data/runs.json")
    private Resource runsJson;

    public RunJsonDataLoader(ObjectMapper objectMapper, JdbcClientRunRepository runRepository) {
        this.objectMapper = objectMapper;
        this.runRepository = runRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(runRepository.count() == 0) {
            try (var inputStream = runsJson.getInputStream()) {
                Runs allRuns = objectMapper.readValue(inputStream, Runs.class);
                log.info("Reading {} runs from JSON data and saving to in-memory collection.", allRuns.runs().size());
                runRepository.saveAll(allRuns.runs());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        } else {
            log.info("Not loading Runs from JSON data because the collection contains data.");
        }
    }

}