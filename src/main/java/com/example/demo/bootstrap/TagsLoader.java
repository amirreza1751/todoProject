package com.example.demo.bootstrap;
import com.example.demo.model.Tag;
import com.example.demo.model.Users;
import com.example.demo.repositories.TagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TagsLoader implements CommandLineRunner {
    public final TagRepository tagRepository;

    public TagsLoader(TagRepository tagRepository) {

        this.tagRepository = tagRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadTags();
    }

    private void loadTags() {
        if (tagRepository.count() == 0) {
            tagRepository.save(
                    Tag.builder()
                            .name("Tag1")
                            .build()
            );
            tagRepository.save(
                    Tag.builder()
                            .name("Tag2")
                            .build()
            );
            tagRepository.save(
                    Tag.builder()
                            .name("Tag3")
                            .build()
            );
            tagRepository.save(
                    Tag.builder()
                            .name("Tag4")
                            .build()
            );
            System.out.println("Sample Tags Loaded");
        }
    }
}