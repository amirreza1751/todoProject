package com.example.demo.bootstrap;
import com.example.demo.model.Users;
import com.example.demo.repositories.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UsersLoader implements CommandLineRunner {
    public final UsersRepository usersRepository;

    public UsersLoader(UsersRepository usersRepository) {

        this.usersRepository = usersRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
    }

    private void loadUsers() {
        if (usersRepository.count() == 0) {
            usersRepository.save(
                    Users.builder()
                            .name("User1")
                            .build()
            );
            usersRepository.save(
                    Users.builder()
                            .name("User2")
                            .build()
            );
            usersRepository.save(
                    Users.builder()
                            .name("User3")
                            .build()
            );
            System.out.println("Sample Users Loaded");
        }
    }
}