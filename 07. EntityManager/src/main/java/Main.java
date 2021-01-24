import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/itis.BD");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("glotai)900");
        hikariConfig.setMaximumPoolSize(20);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        User user = new User(7L, "Anastasia", "Semenova", true);

        EntityManager entityManager = new EntityManager(dataSource);
        //entityManager.save("user", user);

        User user1 = entityManager.findById("user", User.class, Long.class, 5L);
        System.out.println(user1.toString());

        //entityManager.createTable("tableName", User.class);
    }
}
