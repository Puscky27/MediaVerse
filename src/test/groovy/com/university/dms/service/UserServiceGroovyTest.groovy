package com.university.dms.service

import com.university.dms.repository.user.RoleRepository
import com.university.dms.repository.user.UserRepository
import com.university.dms.model.user.User
import com.university.dms.service.user.UserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class UserServiceGroovyTest extends Specification {

    UserRepository userRepository = Mock()
    RoleRepository roleRepository = Mock()
    BCryptPasswordEncoder bCryptPasswordEncoder = Mock()

    UserService userService = new UserService(userRepository, roleRepository, bCryptPasswordEncoder)

    def "Find User By Email"() {
        setup: "Setting response from the user repository"
        userRepository.findByEmail("test@test.com") >> User.builder()
                .id(1)
                .email("test@test.com")
                .build()

        when: "Request a user by email"
        def result = userService.findUserByEmail("test@test.com")

        then: "No error is thrown"
        noExceptionThrown()

        and: "The result is as expected"
        result != null
        result.id == 1
        result.email == "test@test.com"
    }
}
