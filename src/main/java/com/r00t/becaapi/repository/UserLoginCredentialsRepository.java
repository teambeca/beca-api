package com.r00t.becaapi.repository;

import com.r00t.becaapi.models.UserLoginCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLoginCredentialsRepository extends MongoRepository<UserLoginCredentials, String> {

    Optional<Integer> countAllByScoreGreaterThanAndActive(long score, boolean active);

    Optional<UserLoginCredentials> findByUsername(String username);

    Optional<List<UserLoginCredentials>> findTop5ByActiveOrderByScoreDesc(boolean active);

    Optional<List<UserLoginCredentials>> findAllByUsername(String username);

    Optional<List<UserLoginCredentials>> findAllByRole(String role);

    Optional<List<UserLoginCredentials>> findAllByScoreGreaterThan(long score);

    Optional<List<UserLoginCredentials>> findAllByScoreLessThan(long score);

    Optional<List<UserLoginCredentials>> findAllByScoreBetween(long start, long end);

    Optional<List<UserLoginCredentials>> findAllByCreationDateGreaterThan(long creationDate);

    Optional<List<UserLoginCredentials>> findAllByCreationDateLessThan(long creationDate);

    Optional<List<UserLoginCredentials>> findAllByCreationDateBetween(long start, long end);
}
