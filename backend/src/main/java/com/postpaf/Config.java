package com.postpaf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
@Component
@PropertySource("classpath:application.properties")
public class Config implements CommandLineRunner {
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void run(String... args) {
        try {
            // Obtenez le nom exact de la séquence pour la table users
            String userSeq = jdbcTemplate.queryForObject(
                "SELECT pg_get_serial_sequence('users', 'id')", String.class);
            if (userSeq != null) {
                Integer maxUserId = jdbcTemplate.queryForObject("SELECT COALESCE(MAX(id), 0) FROM users", Integer.class);
                jdbcTemplate.execute("SELECT setval('" + userSeq + "', " + maxUserId + ", true)");
                System.out.println("La séquence " + userSeq + " a été réinitialisée à " + maxUserId);
            }
            // Faites de même pour la table post
            String postSeq = jdbcTemplate.queryForObject(
                "SELECT pg_get_serial_sequence('post', 'id')", String.class);
            if (postSeq != null) {
                Integer maxPostId = jdbcTemplate.queryForObject("SELECT COALESCE(MAX(id), 0) FROM post", Integer.class);
                jdbcTemplate.execute("SELECT setval('" + postSeq + "', " + maxPostId + ", true)");
                System.out.println("La séquence " + postSeq + " a été réinitialisée à " + maxPostId);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la réinitialisation des séquences: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
