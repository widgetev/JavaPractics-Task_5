package org.example;


import io.restassured.http.ContentType;
import org.example.settlement.DTO.request.RequestAccountDTO;
import org.example.settlement.dbentity.TppRefAccountType;
import org.example.settlement.repository.TppProductRegisterRepo;
import org.example.settlement.repository.TppRefAccountTypeRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.lang.reflect.Field;
import java.util.List;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(/*classes = {Task5SiApplication.class},*/ webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Task5SiApplicationTest {
    @LocalServerPort
    private Integer port;
    private String requestAccUrl;
    private String requestInsUrl;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    TppRefAccountTypeRepo tppRefAccountTypeRepo;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test_db")
            .withUsername("postgres")
            .withPassword("example")
            .withCopyFileToContainer(MountableFile.forHostPath("schema.sql"), "/docker-entrypoint-initdb.d/schema.sql")
            .withCopyFileToContainer(MountableFile.forHostPath("data.sql"), "/docker-entrypoint-initdb.d/data.sql");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.name", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll(ApplicationContext context) {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void voidTestOk() {
        Assertions.assertEquals(1, 1);
    }

    @BeforeEach
    void setRequestUrl() {
        requestAccUrl = "http://localhost:" + port + "/corporate-settlement-account/create";
        requestInsUrl = "http://localhost:" + port + "/corporate-settlement-instance/create";
    }

    @AfterEach
    void removeAll(@Autowired TppProductRegisterRepo tppProductRegisterRepo){
            tppProductRegisterRepo.deleteAll();
    }

    @Test
    @DisplayName("БД инициализировалась вроде верно")
    void testInitTables() {
        List<TppRefAccountType> tppRefAccountTypeList = tppRefAccountTypeRepo.findAll();
        Assertions.assertEquals(2, tppRefAccountTypeList.size());
        for (TppRefAccountType tppRefAccountType : tppRefAccountTypeList) {
            Assertions.assertEquals(true, "Внутрибанковский Клиентский".contains(tppRefAccountType.getValue()));
        }
    }

    @ParameterizedTest(name = "Создание нового продукта. Счет не найден")
    @MethodSource("org.example.RequestAccountDTODataSource#newAccErr")
    void addNewAccountNotFound(RequestAccountDTO x) throws InterruptedException {

        //Повторый запрос вернет ошибку "Уже существует"
        given()
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .body(x)
                .post(requestAccUrl)
                .then()
                .log().body()
                .statusCode(404)
                .body(containsString("Не найден номер счета для branchCode = 0021 currencyCode = 501 mdmCode = 13 priorityCode + 00 registryTypeCode = 02.001.005_45343_CoDowFF"));
    }

    @ParameterizedTest(name = "Создание нового продукта. Создался")
    @MethodSource("org.example.RequestAccountDTODataSource#newAccOK")
    void addNewAccountOK(RequestAccountDTO x) throws InterruptedException {
        given()
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .body(x)
                .post(requestAccUrl)
                .then()
                .log().body()
                .statusCode(200)
                //.body("data", notNullValue())
                .body("data.accountId", matchesPattern("\\d{1,3}")); //equalTo("1") не факт. Записи уже могли быть, порядок тестов не установлен
    }


    @ParameterizedTest(name = "Создание нового продукта. Уже существует")
    @MethodSource("org.example.RequestAccountDTODataSource#newAccOK")
    void addNewAccount400(RequestAccountDTO x) throws InterruptedException {
        //Повторый запрос вернет ошибку "Уже существует"
        given()
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .body(x)
                .post(requestAccUrl)
                .then()
                .log().body()
                .statusCode(200)
                //.body("data", notNullValue())
                .body("data.accountId",matchesPattern("\\d{1,3}"));

        given()
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .body(x)
                .post(requestAccUrl)
                .then()
                .log().body()
                .statusCode(400)
                .body(containsString("Параметр registryTypeCode тип регистра 02.001.005_45343_CoDowFF уже существует для ЭП с ИД  2."));
    }

    @Test
    void createInstance(){

        String json = "{\n" +
                "\"instanceId\":\"\",\n" +
                "\"productType\":\"СМО\",\n" +
                "\"productCode\":\"03.012.002\",\n" +
                "\"registerType\":\"03.012.002_47533_ComSoLd\",\n" +
                "\"mdmCode\":\"15\",\n" +
                "\"contractNumber\":\"64314/ee\",\n" +
                "\"contractDate\":\"2023-05-17\",\n" +
                "\"priority\":\"6\",\n" +
                "\"interestRatePenalty\":\"1000\",\n" +
                "\"minimalBalance\":\"80\",\n" +
                "\"thresholdAmount \":\"50\",\n" +
                "\"accountingDetails\":\"645348165345\",\n" +
                "\"rateType\":\"1\",\n" +
                "\"taxPercentageRate \":\"5\",\n" +
                "\"technicalOverdraftLimitAmount\":\"800000\",\n" +
                "\"contractId\":\"0\",\n" +
                "\"branchCode\":\"98743\",\n" +
                "\"isoCurrencyCode\":\"810\",\n" +
                "\"urgencyCode\":\"00\",\n" +
                "\"referenceCode\":\"77777\",\n" +
                "\"additionalPropertiesVip\":{\n" +
                "\t\"data\":[\n" +
                "\t\t{ \n" +
                "\t\"key\": \" RailwayRegionOwn\", \n" +
                "\t\"value\": \"ABC\", \n" +
                "\t\"name\": \"Регион принадлежности железной дороги\" \n" +
                "            }, \n" +
                "\t{ \n" +
                "\t\"key\": \"counter\",\n" +
                "             \"value\": \"123\", \n" +
                "\t\"name\": \"Счетчик\" \n" +
                "            }         \n" +
                "\n" +
                "\t]\n" +
                "},\n" +
                "\"instanceArrangement\":[\n" +
                "\t\t{\n" +
                "\t\t\"generalAgreementId\":\"44\",\n" +
                "\t\t\"supplementaryAgreementId\":\"333\",\n" +
                "\t\t\"arrangementType\":\"ЕЖО\",\n" +
                "\t\t\"shedulerJobId\":\"44\",\n" +
                "\t\t\"number\":\"55\",\n" +
                "\t\t\"openingDate\":\"2023-08-15\",\n" +
                "\t\t\"closingDate\":\"\",\n" +
                "\t\t\"CancelDate\":\"\",\n" +
                "\t\t\"validityDuration\":\"45\",\n" +
                "\t\t\"cancellationReason\":\"\",\n" +
                "\t\t\"Status\":\"OPEN\",\n" +
                "\t\t\"interestCalculationDate\":\"\",\n" +
                "\t\t\"interestRate\":\"34\",\n" +
                "\t\t\"coefficient\":\"8\",\n" +
                "\t\t\"coefficientAction\":\"+\",\n" +
                "\t\t\"minimumInterestRate\":\"\",\n" +
                "\t\t\"minimumInterestRateCoefficient\":\"\",\n" +
                "\t\t\"minimumInterestRateCoefficientAction\":\"\",\n" +
                "\t\t\"maximalnterestRate\":\"\",\n" +
                "\t\t\"maximalnterestRateCoefficient\":\"\",\n" +
                "\t\t\"maximalnterestRateCoefficientAction\":\"\"\n" +
                "\t\t}\t\n" +
                "\t]\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .body(json)
                .post(requestInsUrl)
                .then()
                .log().body()
                .statusCode(200)
                .body("data.instanceId",matchesPattern("\\d{1,3}"))
                .body("data.registerId",hasItems("1"))
                .body("data.supplementaryAgreementId",hasItems("03.012.002_47533_ComSoLd"));

    }

}
class RequestAccountDTODataSource {
        static private RequestAccountDTO accountDTO = new RequestAccountDTO(
                2,
                "02.001.005_45343_CoDowFF",
                "Клиентский",
                "500",
                "0021",
                "00",
                "13",
                "РЖД",
                "РЖД",
                "28",
                "4444444"
        );
        public static List<RequestAccountDTO> newAccOK() {
            return List.of(accountDTO);
        }
            public static List<RequestAccountDTO> newAccErr() throws NoSuchFieldException, IllegalAccessException {
                Field field= accountDTO.getClass().getDeclaredField("currencyCode");
                field.setAccessible(true);
                field.set(accountDTO,"501");
                return List.of(accountDTO);
        }
}

