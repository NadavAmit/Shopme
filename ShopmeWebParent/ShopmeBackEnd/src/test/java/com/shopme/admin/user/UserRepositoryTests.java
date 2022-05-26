package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userAmitNadav = new User("amit@codejava.net","amit2020","Amit","Nadav");
        userAmitNadav.addRole(roleAdmin);

        User savedUser = repo.save(userAmitNadav);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateNewUserWithTwoRoles(){
        User userYanivNadav = new User("Yaniv@codejava.net","yaniv2020","Yaniv","Nadav");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        userYanivNadav.addRole(roleEditor);
        userYanivNadav.addRole(roleAssistant);

        User savedUser = repo.save(userYanivNadav);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));

    }

    @Test
    public void testGetUserById(){
       User userAmit = repo.findById(1).get();
       System.out.println(userAmit);
       assertThat(userAmit).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User userAmit = repo.findById(1).get();
        userAmit.setEnabled(true);
        userAmit.setEmail("amitjavaprogrammer@gmail.com");

        repo.save(userAmit);
    }

    @Test
    public void testUpdateUserRoles(){
        User userYaniv = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);
        userYaniv.getRoles().remove(roleEditor);
        userYaniv.addRole(roleSalesperson);

        repo.save(userYaniv);
    }

    @Test
    public void testDeleteUser(){
        Integer userId = 2;
        repo.deleteById(userId);
    }
}
