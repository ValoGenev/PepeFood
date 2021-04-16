package com.gegessen.repository;

import com.gegessen.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<UserEntity,String> {

    Optional<UserEntity> findByEmail(String email);


    @Query("select case when count(u)> 0 then true else false end from UserEntity as u where lower(u.email) like lower(:email)")
    boolean checkIfEmailExists(@Param("email") String email);

    Optional<UserEntity> findByGoogleClientId(String googleClientId);

    @Query("select u from UserEntity as u where u.facebookClientId = :facebookClientId")
    Optional<UserEntity> findByFacebookClientId(String facebookClientId);

    Optional<UserEntity> findByEmailVerificationToken(String emailVerificationToken);




}
