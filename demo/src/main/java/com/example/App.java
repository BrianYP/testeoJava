package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class App {

    private WebDriver driver;

    // Datos de inicio de sesión
    private final String correo = "brahyan@gmail.com";
    private final String contrasenia = "pepe123456";
    // Datos del usuario
    private final String cedula = "12333322212";
    private final String correoUser = "carlas@gmail.com";
    private final String nombre = "Carlos";
    private final String primerApellido = "Meneses";
    private final String segundoApellido = "Ozuna";
    private final String contraseniaUser = "pepe123443";
    private final String fecha = "2003-03-12";
    private final String telefono = "3198277161";

    public static void main(String[] args) {
        App app = new App();
        try {
            app.setUp();
            app.testUserRegistration();
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

    public void testUserRegistration() {
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
        selectRole.sendKeys("2");

        // Completar el formulario de registro
        driver.findElement(By.name("inputCedula")).sendKeys(cedula);
        driver.findElement(By.name("inputCorreo")).sendKeys(correoUser);
        driver.findElement(By.name("inputNombre")).sendKeys(nombre);
        driver.findElement(By.name("inputApellido1")).sendKeys(primerApellido);
        driver.findElement(By.name("inputApellido2")).sendKeys(segundoApellido);
        driver.findElement(By.name("inputContraseña1")).sendKeys(contraseniaUser);
        driver.findElement(By.name("inputContraseña2")).sendKeys(contraseniaUser);
        driver.findElement(By.name("inputFechaNac")).sendKeys(fecha);
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
