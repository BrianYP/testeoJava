package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class registrar {

    private WebDriver driver;

    // Datos del usuario
    private final String cedula = "1000567802";
    private final String nombre = "horacio";
    private final String primerA = "perez";
    private final String segundoA = "henao";
    private final String fechaN = "22/03/2001";
    private final String telefono = "3146678923";
    private final String correo = "prueba50@gmail.com";
    private final String contrasenia = "horacio1234";
    private final String confirmarContra = "horacio1234";

    public static void main(String[] args) {
        registrar registrar = new registrar();
        try {
            registrar.setUp();
            registrar.registrarEnPagina();
        } catch (Exception e) {
            System.err.println("Error durante la ejecución: " + e.getMessage());
        } finally {
            registrar.tearDown();
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

    public void registrarEnPagina() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navegar a la página de registro
        WebElement navbarRegisterLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='navbarNav']/ul/li[4]/a")));
        navbarRegisterLink.click();

        // Esperar y hacer clic en el enlace de registro en el desplegable
        WebElement dropdownRegisterLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='navbarNav']/ul/li[4]/ul/li[3]/a")));
        dropdownRegisterLink.click();

        // Completar el formulario de registro
        WebElement cedulaField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputCedula")));
        cedulaField.sendKeys(cedula);

        driver.findElement(By.id("inputNombre")).sendKeys(nombre);
        driver.findElement(By.id("inputApellido1")).sendKeys(primerA);
        driver.findElement(By.id("inputApellido2")).sendKeys(segundoA);
        driver.findElement(By.id("inputFechaNac")).sendKeys(fechaN);
        driver.findElement(By.id("inputTelefono")).sendKeys(telefono);
        driver.findElement(By.id("inputCorreo")).sendKeys(correo);
        driver.findElement(By.id("inputContraseña1")).sendKeys(contrasenia);
        driver.findElement(By.id("inputContraseña2")).sendKeys(confirmarContra);

        // Esperar un momento antes de enviar el formulario
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Esperar a que el botón de registro sea visible y luego hacer clic
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit' and text()='Registrar']")));
        registerButton.click();

        // Esperar un momento después de enviar el formulario
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Agregar un retraso antes de cerrar el navegador
        try {
            Thread.sleep(5000); // 5 segundos de retraso antes de cerrar el navegador
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
