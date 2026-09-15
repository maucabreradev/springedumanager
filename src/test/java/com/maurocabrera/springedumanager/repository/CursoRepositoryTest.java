package com.maurocabrera.springedumanager.repository;

import com.maurocabrera.springedumanager.entity.Curso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @BeforeEach
    void cleanup() {
        cursoRepository.deleteAll();
    }

    @Test
    void whenFindByActivoTrue_thenReturnOnlyActiveCourses() {
        Curso active = new Curso();
        active.setNombre("Active Course");
        active.setActivo(true);
        Curso inactive = new Curso();
        inactive.setNombre("Inactive Course");
        inactive.setActivo(false);
        cursoRepository.save(active);
        cursoRepository.save(inactive);

        List<Curso> result = cursoRepository.findByActivoTrue();

        assertEquals(1, result.size());
        assertTrue(result.get(0).getActivo());
        assertEquals("Active Course", result.get(0).getNombre());
    }

    @Test
    void whenFindByCoordinador_thenReturnMatchingCourses() {
        Curso c1 = new Curso();
        c1.setNombre("Course 1");
        c1.setCoordinador("Juan");
        Curso c2 = new Curso();
        c2.setNombre("Course 2");
        c2.setCoordinador("Pedro");
        cursoRepository.save(c1);
        cursoRepository.save(c2);

        List<Curso> result = cursoRepository.findByCoordinador("Juan");

        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getCoordinador());
    }
}
