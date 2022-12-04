package cz.cvut.fit.tjv.art_commissions.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiMain {
    public static void main(String[] args) {
        SpringApplication.run(ApiMain.class, args);
    }
}

// TODO Ask about docker - there seems to be a problem with the database connection
// TODO Ask about the relation in Artist - how can I make it transitive? Can it even be done?
// TODO Review the api - should the sorting and filtering be done differently?
