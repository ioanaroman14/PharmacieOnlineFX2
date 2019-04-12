package Tests;

import Domain.Medicine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicineTest {

   // @org.junit.jupiter.api.Test
   // void setIdShouldSetTheGivenId() {
     //   Medicine medicine = new Medicine("1", "test", "terapia", 10, true);
     //   String setId = "7";
     //   medicine.setId(setId);
     //   assertNotEquals(setId, medicine.getId(), String.format("Returned=%s, Expected=%s", medicine.getId(), setId));
  //  }

    @org.junit.jupiter.api.Test
    void constructorShouldSetAllFieldsCorrectly() {
        Medicine medicine = new Medicine("1", "test", "terapia", 10, true);
        assertEquals("1", medicine.getId());
        assertEquals("test", medicine.getName());
        assertEquals("terapia", medicine.getProducer());
        assertEquals(10, medicine.getPrice());
        assertTrue(medicine.isRecipe());
    }

    @org.junit.jupiter.api.Test
    void settersShouldSetFieldsCorrectly() {
        Medicine medicine = new Medicine("2", "test", "terapia", 10,  true);

       // medicine.setId("2");
        medicine.setName("test2");
        medicine.setProducer("terapia");
        medicine.setPrice(200);
        medicine.setRecipe(false);

        assertEquals("2", medicine.getId());
        assertEquals("test2", medicine.getName());
        assertEquals(200, medicine.getPrice());
        assertFalse(medicine.isRecipe());
    }

    @org.junit.jupiter.api.Test
    void equalityShouldWorkCorrectly() {
        Medicine medicine1 = new Medicine("10", "test", "terapia", 100, true);
        Medicine medicine2 = new Medicine("10", "test", "terapia", 100, true);
        Medicine medicine3 = new Medicine("11", "test", "terapia", 100, true);

        assertNotEquals(medicine1, medicine3);
        assertNotEquals(medicine3, medicine1);
        assertNotEquals(medicine3, medicine2);
        assertNotEquals(medicine2, medicine3);
        assertEquals(medicine1, medicine2);
        assertEquals(medicine2, medicine1);

    }


    @org.junit.jupiter.api.Test
    void toStringShouldReturnALongEnoughString() {
        Medicine medicine1 = new Medicine("1", "test", "terapia", 100,  true);

        assertTrue(medicine1.toString().length() > 10);
    }
}