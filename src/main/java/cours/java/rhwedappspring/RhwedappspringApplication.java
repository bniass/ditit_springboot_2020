package cours.java.rhwedappspring;

import cours.java.rhwedappspring.dao.SpecialiteRepository;
import cours.java.rhwedappspring.model.Specialite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RhwedappspringApplication implements CommandLineRunner {
    @Autowired
    private SpecialiteRepository specialiteRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(RhwedappspringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(encoder.encode("passer"));
    }
}
