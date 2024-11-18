package ru.hse.db.hw7.orm.configuration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hse.db.hw7.orm.seeder.DatabaseSeeder;

@Configuration
public class DataSeederConfig {

    @Bean
    public ApplicationRunner runSeeder(DatabaseSeeder databaseSeeder) {
        return args -> {
            databaseSeeder.seedDatabase(100, 1000, 50, 5000, 10000);
        };
    }
}
