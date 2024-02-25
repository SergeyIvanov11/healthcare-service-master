package ru.netology.patient.service.alert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

class SendAlertServiceImplTest {
    PatientInfoRepository patientInfoRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
    SendAlertService alertServiceMock = Mockito.mock(SendAlertService.class);
    MedicalService medicalServiceMock = new MedicalServiceImpl(patientInfoRepositoryMock, alertServiceMock);
    ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
    PatientInfo idIvanPetrov = Mockito.mock(PatientInfo.class);
    HealthInfo healthInfoMock = Mockito.mock(HealthInfo.class);

    @Test
    void send() {
        Mockito.when(patientInfoRepositoryMock.getById(Mockito.any())).thenReturn(idIvanPetrov);
        Mockito.when(idIvanPetrov.getHealthInfo()).thenReturn(healthInfoMock);
        Mockito.when(idIvanPetrov.getId()).thenReturn("IvanPetrov");
        Mockito.when(healthInfoMock.getBloodPressure()).thenReturn(new BloodPressure(120, 70));
        BloodPressure highPressure = new BloodPressure(150, 80);
        medicalServiceMock.checkBloodPressure(idIvanPetrov.toString(), highPressure);
        Mockito.verify(alertServiceMock).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: IvanPetrov, need help", argumentCaptor.getValue());
    }
}