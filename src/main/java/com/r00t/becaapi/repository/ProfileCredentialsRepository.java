package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.ProfileCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileCredentialsRepository extends MongoRepository<ProfileCredentials, String> {

    Optional<ProfileCredentials> findByUserId(String userId);

    Optional<ProfileCredentials> findByEmail(String email);

    Optional<List<ProfileCredentials>> findAllByFullName(String fullName);

    Optional<List<ProfileCredentials>> findAllByJob(String job);

    Optional<List<ProfileCredentials>> findAllByCityId(String cityId);

    Optional<List<ProfileCredentials>> findAllByGender(int gender);

    Optional<List<ProfileCredentials>> findAllByAge(int age);

    Optional<List<ProfileCredentials>> findAllByAgeGreaterThan(int age);

    Optional<List<ProfileCredentials>> findAllByAgeLessThan(int age);

    Optional<List<ProfileCredentials>> findAllByAgeBetween(int start, int end);

    Optional<List<ProfileCredentials>> findAllByCreationDateGreaterThan(long creationDate);

    Optional<List<ProfileCredentials>> findAllByCreationDateLessThan(long creationDate);

    Optional<List<ProfileCredentials>> findAllByCreationDateBetween(long start, long end);

    void deleteByUserId(String userId);
}
