package ru.netology.patient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

class MainTest {
    PatientInfoRepository patientInfoRepositoryMock;
    SendAlertService alertServiceMock;
    MedicalService medicalServiceMock;
    ArgumentCaptor<String> argumentCaptor;

    @BeforeEach
    public void init() {
        patientInfoRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        alertServiceMock = Mockito.mock(SendAlertService.class);
        medicalServiceMock = new MedicalServiceImpl(patientInfoRepositoryMock, alertServiceMock);
        argumentCaptor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    void checkBloodPressureTest() {
        PatientInfo idIvanPetrov = new PatientInfo("Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        patientInfoRepositoryMock.add(idIvanPetrov);

        medicalServiceMock.checkBloodPressure(idIvanPetrov.toString(), idIvanPetrov.getHealthInfo().getBloodPressure());
        Mockito.verify(alertServiceMock).send(argumentCaptor.capture());
        Assertions.assertEquals("user1", argumentCaptor.getValue());
    }

    @Test
    void checkTemperatureTest() {

    }

    @Test
    void normalMessageTest() {

    }
}


/*
Что нужно сделать
Написать тесты для проверки класса MedicalServiceImpl, сделав заглушку для класса
PatientInfoFileRepository, который он использует
Проверить вывод сообщения во время проверки давления checkBloodPressure
Проверить вывод сообщения во время проверки температуры checkTemperature
Проверить, что сообщения не выводятся, когда показатели в норме.

Создайте тесты в соответствии с задачей (для сервиса MedicalServiceImpl нужно обязательно
 создать заглушки (mock) в виде PatientInfoFileRepository и SendAlertService) - сделать минимум 3 unit-теста;

Для заглушки класса SendAlertService нужно проверить вызов метода send (можно воспользоваться
ArgumentCaptor и verify из библиотеки mockito)

 */