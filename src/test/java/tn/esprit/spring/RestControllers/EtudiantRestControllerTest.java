package tn.esprit.spring.RestControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.Services.Etudiant.IEtudiantService;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EtudiantRestController.class)
class EtudiantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiantService etudiantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddOrUpdate() throws Exception {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");

        Mockito.when(etudiantService.addOrUpdate(Mockito.any(Etudiant.class))).thenReturn(etudiant);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/etudiant/addOrUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idEtudiant").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomEt").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prenomEt").value("John"));
    }

    @Test
    void testFindAll() throws Exception {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");

        List<Etudiant> etudiants = Collections.singletonList(etudiant);
        Mockito.when(etudiantService.findAll()).thenReturn(etudiants);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idEtudiant").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomEt").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].prenomEt").value("John"));
    }

    @Test
    void testFindById() throws Exception {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");

        Mockito.when(etudiantService.findById(1L)).thenReturn(etudiant);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/findById")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idEtudiant").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomEt").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.prenomEt").value("John"));
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");

        Mockito.doNothing().when(etudiantService).delete(Mockito.any(Etudiant.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/etudiant/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteById() throws Exception {
        // Arrange
        Mockito.doNothing().when(etudiantService).deleteById(1L);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/etudiant/deleteById")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSelectJPQL() throws Exception {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");

        List<Etudiant> etudiants = Collections.singletonList(etudiant);
        Mockito.when(etudiantService.selectJPQL("Doe")).thenReturn(etudiants);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/etudiant/selectJPQL")
                        .param("nom", "Doe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].idEtudiant").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomEt").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].prenomEt").value("John"));
    }
}