package ru.netology.patient.service.medical;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;

class MedicalServiceImplTest {
    PatientInfoRepository patientInfoRepositoryMock;
    SendAlertService alertServiceMock;
    MedicalService medicalServiceMock;
    PatientInfo idIvanPetrov = new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
            new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));

    @BeforeEach
    public void init() {
        patientInfoRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        alertServiceMock = Mockito.mock(SendAlertService.class);
        medicalServiceMock = new MedicalServiceImpl(patientInfoRepositoryMock, alertServiceMock);
        Mockito.when(patientInfoRepositoryMock.getById(Mockito.any())).thenReturn(idIvanPetrov);
    }

    @Test
    void checkBloodPressure() {
        BloodPressure highPressure = new BloodPressure(150, 80);
        medicalServiceMock.checkBloodPressure(idIvanPetrov.toString(), highPressure);
        Mockito.verify(alertServiceMock, Mockito.times(1)).send(Mockito.anyString());
    }

    @Test
    void checkTemperature() {
        BigDecimal febrileTemperature = new BigDecimal("36.4");
        medicalServiceMock.checkTemperature(idIvanPetrov.toString(), febrileTemperature);
        Mockito.verify(alertServiceMock, Mockito.times(0)).send(Mockito.anyString());
    }
}