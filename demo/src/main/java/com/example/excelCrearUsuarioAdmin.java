package com.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

public class excelCrearUsuarioAdmin {

    private WebDriver driver;
    private final String excelFilePath = "C:\\Users\\Brahyan\\Desktop\\testeoJava\\robot\\pruebaExcel.xlsx";

    // Datos de inicio de sesión
    private final String correo = "brahyan@gmail.com";
    private final String contrasenia = "pepe12345";

    public static void main(String[] args) {
        excelCrearUsuarioAdmin app = new excelCrearUsuarioAdmin(); // Cambié de App a excel
        try {
            app.setUp();
            app.runTests();
        } catch (Exception e) {
            System.err.println("Error durante la ejecución: " + e.getMessage());
        } finally {
            app.tearDown();
        }
    }

    public void setUp() {
        // Configura el driver de Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Brahyan\\Desktop\\testeoJava\\demo\\chromedriver.exe");

        // Opciones de Chrome para evitar errores de WebSocket
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:5173");
    }

    public void runTests() {
        try (FileInputStream file = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet("Sheet2");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // skip header row
                }

                String cedula = getCellValueAsString(row.getCell(0));
                String correoUser = getCellValueAsString(row.getCell(1));
                String nombre = getCellValueAsString(row.getCell(2));
                String primerApellido = getCellValueAsString(row.getCell(3));
                String sapellido = getCellValueAsString(row.getCell(4));
                String contraseniaUser = getCellValueAsString(row.getCell(5));
                String fecha = getCellValueAsString(row.getCell(6));
                String telefono = getCellValueAsString(row.getCell(7));

                // Perform the user registration
                testUserRegistration(cedula, correoUser, nombre, primerApellido, sapellido, contraseniaUser, fecha, telefono);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public void testUserRegistration(String cedula, String correoUser, String nombre, String primerApellido, String sapellido, String contraseniaUser, String fecha, String telefono) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navegar e iniciar sesión
        WebElement inicioSesionLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='navbarNav']/ul/li[4]/a")));
        inicioSesionLink.click();

        WebElement inicioSesionDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='navbarNav']/ul/li[4]/ul/li[1]/a")));
        inicioSesionDropdown.click();

        WebElement correoField = driver.findElement(By.id("inputCorreoL"));
        WebElement passField = driver.findElement(By.id("inputPass"));

        correoField.sendKeys(correo);
        passField.sendKeys(contrasenia);
        passField.sendKeys(Keys.ENTER);

        // Esperar y manejar el modal de confirmación
        WebElement confirmationButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div/div[6]/button[1]")));
        confirmationButton.click();

        // Navegar a la página de registro de usuario
        WebElement userProfileLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='navbarNav']/ul/li[5]/a")));
        userProfileLink.click();

        WebElement newUserLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div/div/div[2]/a[1]/i")));
        newUserLink.click();

        // Seleccionar el valor de la lista desplegable
        WebElement selectRole = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@class='form-select']")));
        Select roleDropdown = new Select(selectRole);
        roleDropdown.selectByValue("2");

        // Completar el formulario de registro
        driver.findElement(By.name("inputCedula")).sendKeys(cedula);
        driver.findElement(By.name("inputCorreo")).sendKeys(correoUser);
        driver.findElement(By.name("inputNombre")).sendKeys(nombre);
        driver.findElement(By.name("inputApellido1")).sendKeys(primerApellido);
        driver.findElement(By.name("inputApellido2")).sendKeys(sapellido);
        driver.findElement(By.name("inputContraseña1")).sendKeys(contraseniaUser);
        driver.findElement(By.name("inputContraseña2")).sendKeys(contraseniaUser);

        // Ingresar fecha de nacimiento
        WebElement fechaNacField = driver.findElement(By.name("inputFechaNac"));
        fechaNacField.sendKeys(fecha);
        fechaNacField.sendKeys(Keys.ENTER);

        driver.findElement(By.name("inputTelefono")).sendKeys(telefono);

        // Enviar el formulario de registro
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Esperar y manejar el modal de confirmación
        WebElement finalConfirmationButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div[6]/button[1]")));
        finalConfirmationButton.sendKeys(Keys.ENTER);

        // Espera para ver el resultado final (esto puede ajustarse según las necesidades)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}