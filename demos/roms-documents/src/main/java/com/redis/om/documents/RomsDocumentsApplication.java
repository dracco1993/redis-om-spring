package com.redis.om.documents;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Point;

import com.redis.om.documents.domain.Company;
import com.redis.om.documents.domain.Person;
import com.redis.om.documents.repositories.CompanyRepository;
import com.redis.om.documents.repositories.PersonRepository;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;

@SpringBootApplication
@EnableRedisDocumentRepositories(basePackages = "com.redis.om.documents.*")
public class RomsDocumentsApplication {

  @Autowired
  CompanyRepository companyRepo;

  @Autowired
  PersonRepository personRepo;

  @Bean
  CommandLineRunner loadTestData() {
    return args -> {
      companyRepo.deleteAll();
      Company redis = Company.of("Redis", "https://redis.com", new Point(-122.066540, 37.377690), 526, 2011);
      redis.setTags(Set.of("fast", "scalable", "reliable"));

      Company microsoft = Company.of("Microsoft", "https://microsoft.com", new Point(-122.124500, 47.640160), 182268, 1975);
      microsoft.setTags(Set.of("innovative", "reliable"));

      companyRepo.save(redis);
      companyRepo.save(redis); // save again to test @LastModifiedDate
      companyRepo.save(microsoft);

      personRepo.deleteAll();
      personRepo.save(Person.of("Brian", "Sam-Bodden", "bsb@redis.com"));
      personRepo.save(Person.of("Guy", "Royse", "guy.royse@redis.com"));
      personRepo.save(Person.of("Guy", "Korland", "guy.korland@redis.com"));
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(RomsDocumentsApplication.class, args);
  }

}
