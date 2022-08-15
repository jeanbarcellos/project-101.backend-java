package com.jeanbarcellos.demo.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import com.jeanbarcellos.demo.domain.enums.UserStatus;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTests {

    String userName = "Jean Barcellos";
    String userEmail = "jeanbarcellos@hotmail.com";
    String userPassword = "teste@123";

    Role roleAdmin;
    Role roleEditor;
    Role roleAuthor;
    Role roleViewer;
    User userAdmin;
    User userEditor;
    User userAuthor;
    User userViewer;
    User userEditorAndAuthor;

    @BeforeAll
    void setUp() {

        roleViewer = new Role("viewer",
                "Pode ler qualquer postagem e não pode fazer mais nada.");
        roleAuthor = new Role("author",
                "Pode ver as postagens, além de criar uma postagem, editá-la e finalmente publicá-la.");
        roleEditor = new Role("editor",
                "Pode ver as postagens, além de editar e publicar qualquer postagem.");
        roleAdmin = new Role("administrator",
                "Pode fazer qualquer coisa que um Visualizador e Editor podem fazer, além de excluir postagens.");

        userAdmin = new User("Admin", "admin@test.com", "test@123", UserStatus.ACTIVE);
        userEditor = new User("Editor", "editor@test.com", "test@123", UserStatus.ACTIVE);
        userAuthor = new User("Author", "author@test.com", "test@123", UserStatus.ACTIVE);
        userViewer = new User("Viewer", "viewer@test.com", "test@123", UserStatus.ACTIVE);
        userEditorAndAuthor = new User("Viewer and Author", "viewer.author@test.com", "test@123", UserStatus.ACTIVE);

        // Criando a hierarquia de roles
        roleAuthor.addChild(roleViewer);
        roleEditor.addChild(roleViewer);
        roleAdmin.addChild(roleEditor);

        // Definindo as roles dos uusários
        userAdmin.addRole(roleAdmin);
        userAuthor.addRole(roleAuthor);
        userEditor.addRole(roleEditor);
        userViewer.addRole(roleViewer);
        userEditorAndAuthor.addRole(roleEditor);
        userEditorAndAuthor.addRole(roleAuthor);
    }

    // #region Contructors

    @Test
    void givenBaeldungNameAndEmailAndPassword_whenToBuild_returnCreatedUser() {
        // Arrange && Act
        User user = new User(userName, userEmail, userPassword);

        // Assert
        assertEquals(userName, user.getName());
        assertEquals(userEmail, user.getEmail());
        assertEquals(userPassword, user.getPassword());
    }

    @Test
    void givenUserActive_whenInactivate_thenReturnInactiveUser() {
        // Arrange
        User user = new User(userName, userEmail, userPassword, UserStatus.ACTIVE);

        // Act
        user.inactivate();

        // Assert
        assertFalse(user.isActive());
        assertEquals(UserStatus.INACTIVE, user.getStatus());
    }

    @Test
    void givenUserInactive_whenActivate_thenReturnActiveUser() {
        // Arrange
        User user = new User(userName, userEmail, userPassword, UserStatus.INACTIVE);

        // Act
        user.activate();

        // Assert
        assertTrue(user.isActive());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
    }

    // #endregion

    // #region Handler Roles

    @Test
    void getRoles_additionOfOneRole_shouldContainOneRole() {
        // Arrange && Act
        var result = userAdmin.getRoles();

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(roleAdmin));
    }

    @Test
    void getRoleNames_additionOfOneRole_shouldReturnListWithOneRole() {
        // Arrange && Act
        var result = userAdmin.getRoleNames();

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(roleAdmin.getName()));
    }

    @Test
    void getReachableRoles_additionOfOneRole_shouldReturnSetWithFourRoles() {
        // Arrange && Act
        var result = userAdmin.getReachableRoles();

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.contains(roleAdmin));
        assertTrue(result.contains(roleEditor));
        assertTrue(result.contains(roleViewer));
    }

    @Test
    void getReachableRoleNames_additionOfOneRole_shouldReturnListWithFourRoles() {
        // Arrange && Act
        var result = userAdmin.getReachableRoleNames();

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.contains(roleAdmin.getName()));
        assertTrue(result.contains(roleEditor.getName()));
        assertTrue(result.contains(roleViewer.getName()));
    }

    // #endregion

    // #region implements UserDetails

    @Test
    void getAuthorities() {
        // Arrange && Act
        var result = userAdmin.getAuthorities();

        // Assert
        assertEquals(3, result.size());
    }

    // #endregion

}
