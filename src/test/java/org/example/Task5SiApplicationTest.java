package org.example;


import io.restassured.http.ContentType;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.example.settlement.DTO.request.RequestAccountDTO;
import org.example.settlement.dbentity.TppRefAccountType;
import org.example.settlement.repository.TppProductRegisterRepo;
import org.example.settlement.repository.TppRefAccountTypeRepo;
import org.json.JSONArray;
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @ParameterizedTest
    @DisplayName("Создание нового экземпляра продукта. Удачно")
    @MethodSource("org.example.RequestInstanceDataSource#newInstOK")
    void createInstanceOK(Object json) throws FileNotFoundException, ParseException {
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
    @ParameterizedTest
    @DisplayName("Создание нового экземпляра продукта. Не указано обязательное поле")
    @MethodSource("org.example.RequestInstanceDataSource#newInstNoRequiredField")
    void createInstanceNoRequiredField(Object json) throws FileNotFoundException, ParseException {
        given()
                .contentType(ContentType.JSON)
                //.log().all()
                .when()
                .body(json)
                .post(requestInsUrl)
                .then()
                .log().body()
                .statusCode(400)
                .body(containsString("Обязательный параметр <productType> не заполнен."));
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
        Field field = accountDTO.getClass().getDeclaredField("currencyCode");
        field.setAccessible(true);
        field.set(accountDTO, "501");
        return List.of(accountDTO);
    }
}
class RequestInstanceDataSource {
        public static List<Object> newInstOK() throws FileNotFoundException, ParseException {
            JSONParser parser = new JSONParser(new FileReader("files/instance.json"));
            var json = parser.parse();
            return List.of(json);
        }

    public static List<Object> newInstNoRequiredField() throws FileNotFoundException, ParseException {
        JSONParser parser = new JSONParser(new FileReader("files/instance.json"));
        Map json = (LinkedHashMap) parser.parse();
        json.remove("productType");
        return List.of(json);
    }
}

