package com.jeanbarcellos.project101.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleTests {

    Role roleAdmin;
    Role roleEditor;
    Role roleAuthor;
    Role roleViewer;

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

        // Criando a hierarquia de roles
        roleAuthor.addChild(roleViewer);
        roleEditor.addChild(roleViewer);
        roleAdmin.addChild(roleEditor);
    }

    // #region Constructores

    @Test
    void construct_entryWithNameAndDescription_shouldReturnNewRole() {

        // Arrange
        String name = "admin";
        String description = "Descrição do perfil";

        // Act
        Role role = new Role(name, description);

        // Assert
        assertNotNull(role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    @Test
    void construct_entryWithIdAndNameAndDescription_shouldReturnNewRole() {

        // Arrange
        UUID id = UUID.randomUUID();
        String name = "admin";
        String description = "Descrição do perfil";

        // Act
        Role role = new Role(id, name, description);

        // Assert
        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
    }

    // #endregion

    // #region Equals

    @Test
    void equals_equalsNames_shouldReturnTrue() {

        // Arrange
        Role role1 = new Role("admin", "Description");
        Role role2 = new Role("admin", "Description");

        // Act
        boolean actual = role1.equals(role2);

        // Assert
        assertTrue(actual);
    }

    @Test
    void equals_differentNames_shouldReturnFalse() {

        // Arrange
        Role role1 = new Role("admin", "Description");
        Role role2 = new Role("triador", "Description");

        // Act
        boolean actual = role1.equals(role2);

        // Assert
        assertFalse(actual);
    }

    // #endregion

    // #region Implments GrantedAuthority, RoleHierarchy

    @Test
    void getAuthority_roleWithName_shouldReturnValueEqualsName() {

        // Arrange
        String name = "admin";
        String description = "Descrição do perfil";

        // Act
        Role role = new Role(name, description);

        // Assert
        assertEquals(name, role.getAuthority());
    }

    // #endregion

    // #region Mehtods handler Child and Parent roles

    @Test
    void addChildRole_additionOfTwoDifferentChildRoles_shouldContainTwoChildren() {
        // Arrange
        Role role1 = new Role("pai", "Pai");
        Role role2 = new Role("filho01", "Filho 01");
        Role role3 = new Role("filho02", "Filho 02");

        // Act
        role1.addChild(role2);
        role1.addChild(role3);

        // Assert
        assertEquals(2, role1.getChildRoles().size());
        assertTrue(role1.hasChild(role2));
        assertTrue(role1.hasChild(role3));
    }

    @Test
    void addChildRole_additionOfTwoEqualChildRoles_shouldContainOneChildren() {
        // Arrange
        Role role1 = new Role("pai", "Pai");
        Role role2 = new Role("filho01", "Filho 01");
        Role role3 = new Role("filho01", "Filho 01");

        // Act
        role1.addChild(role2);
        role1.addChild(role3);

        // Assert
        assertEquals(1, role1.getChildRoles().size());
    }

    @Test
    void addChildRole_addThreeRolesInHierarchy_shouldReturnRolesInHierarchy() {
        // Arrange
        Role role1 = new Role("pai", "Pai");
        Role role2 = new Role("filho", "Filho");
        Role role3 = new Role("neto", "Neto");

        // Act
        role2.addChild(role3);
        role1.addChild(role2);

        // Assert
        assertEquals(1, role1.getChildRoles().size());
        assertEquals(1, role2.getChildRoles().size());

        assertTrue(role1.hasChild(role2));
        assertTrue(role2.hasChild(role3));
    }

    @Test
    void addChildRole_addChildDirectly_showuldThrowException() {
        // Arrange
        Role role1 = new Role("pai", "Pai");
        Role role2 = new Role("filho", "Filho");

        // Act && Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            role1.getChildRoles().add(role2);
        });
    }

    @Test
    void addParentRole_additionOfTwoDifferentParentRoles_shouldContainTwoParents() {
        // Arrange
        Role role1 = new Role("filho01", "Filho 01");
        Role role2 = new Role("pai01", "Pai 01");
        Role role3 = new Role("pai02", "Pai 02");

        // Act
        role1.addParent(role2);
        role1.addParent(role3);

        // Assert
        assertEquals(2, role1.getParentRoles().size());
        assertTrue(role1.hasParent(role2));
        assertTrue(role1.hasParent(role3));
    }

    @Test
    void addParentRole_additionOfTwoEqualParentRoles_shouldContainOneParents() {
        // Arrange
        Role role1 = new Role("filho", "Filho");
        Role role2 = new Role("pai01", "Pai 01");
        Role role3 = new Role("pai01", "Pai 01");

        // Act
        role1.addParent(role2);
        role1.addParent(role3);

        // Assert
        assertEquals(1, role1.getParentRoles().size());
    }

    @Test
    void addParentRole_addThreeRolesInHierarchy_shouldReturnRolesInHierarchy() {
        // Arrange
        Role role1 = new Role("neto", "Neto");
        Role role2 = new Role("filho", "Filho");
        Role role3 = new Role("pai", "Pai");

        // Act
        role1.addParent(role2);
        role2.addParent(role3);

        // Assert
        assertEquals(1, role1.getParentRoles().size());
        assertEquals(1, role2.getParentRoles().size());

        assertTrue(role1.hasParent(role2));
        assertTrue(role2.hasParent(role3));
    }

    @Test
    void addParentRole_addParentDirectly_shouldThrowException() {
        // Arrange
        Role role1 = new Role("filho", "Filho");
        Role role2 = new Role("pai", "Pai");

        // Act && Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            role1.getParentRoles().add(role2);
        });
    }

    @Test
    void getReachableRoles_addFourRolesInHierarchy_shouldReturnListWithThreeRoles() {
        // Arrange
        Role role1 = new Role("nivel.01", "Nível 1");
        Role role2 = new Role("nivel.02", "Nível 2");
        Role role3 = new Role("nivel.03", "Nível 3");
        Role role4 = new Role("movel.04", "Nível 4");

        role1.addChild(role2);
        role2.addChild(role3);
        role3.addChild(role4);

        // Act
        var result = role1.getReachableRoles();

        // Assert
        assertEquals(3, result.size());
    }

    // #endregion

}
