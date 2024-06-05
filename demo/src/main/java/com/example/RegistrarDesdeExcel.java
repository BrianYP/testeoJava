package com.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

public class RegistrarDesdeExcel {

    private WebDriver driver;
    private final String excelFilePath = "C:\\Users\\Brahyan\\Desktop\\testeoJava\\robot\\pruebaExcel.xlsx";
    private static final String XPATH_REGISTRAR_BTN = "//button[@type='submit' and text()='Registrar']";
    private static final String XPATH_ACEPTAR_BTN = "//*[@id='registro']/div/div/div[2]/form/button";

    public static void main(String[] args) {
        RegistrarDesdeExcel registrarDesdeExcel = new RegistrarDesdeExcel();
        try {
            registrarDesdeExcel.setUp();
            registrarDesdeExcel.registrarUsuarios();
        } catch (Exception e) {
            System.err.println("Error durante la ejecución: " + e.getMessage());
        } finally {
            registrarDesdeExcel.tearDown();
        }
    }

    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Brahyan\\Desktop\\testeoJava\\demo\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://localhost:5173");
    }

    public void registrarUsuarios() {
        try (FileInputStream file = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheet("Sheet3");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                String cedula = getCellValueAsString(row.getCell(0));
                String correoUser = getCellValueAsString(row.getCell(1));
                String nombre = getCellValueAsString(row.getCell(2));
                String primerApellido = getCellValueAsString(row.getCell(3));
                String segundoApellido = getCellValueAsString(row.getCell(4));
                String contraseniaUser = getCellValueAsString(row.getCell(5));
                String fechaNacimiento = getCellValueAsString(row.getCell(6));
                String telefono = getCellValueAsString(row.getCell(7));
                registerUser(cedula, correoUser, nombre, primerApellido, segundoApellido, fechaNacimiento, telefono, contraseniaUser);
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
                    return new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public void registerUser(String cedula, String correoUser, String nombre, String primerApellido, String segundoApellido, String fechaNacimiento, String telefono, String contraseniaUser) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement navbarRegisterLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='navbarNav']/ul/li[4]/a")));
        navbarRegisterLink.click();
        WebElement dropdownRegisterLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='navbarNav']/ul/li[4]/ul/li[3]/a")));
        dropdownRegisterLink.click();
        WebElement cedulaField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputCedula")));
        cedulaField.sendKeys(cedula);
        driver.findElement(By.id("inputNombre")).sendKeys(nombre);
        driver.findElement(By.id("inputApellido1")).sendKeys(primerApellido);
        driver.findElement(By.id("inputApellido2")).sendKeys(segundoApellido);
        WebElement fechaNacField = driver.findElement(By.id("inputFechaNac"));
        fechaNacField.clear();
        fechaNacField.sendKeys(fechaNacimiento);
        WebElement telefonoField = driver.findElement(By.id("inputTelefono"));
        telefonoField.clear();
        telefonoField.sendKeys(telefono);
        driver.findElement(By.id("inputCorreo")).sendKeys(correoUser);
        driver.findElement(By.id("inputContraseña1")).sendKeys(contraseniaUser);
        driver.findElement(By.id("inputContraseña2")).sendKeys(contraseniaUser);
        WebElement registrarBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_REGISTRAR_BTN)));
        registrarBtn.click();
        try {
            Thread.sleep(5000);
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
