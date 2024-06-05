package com.example;

public class Main {
    public static void main(String[] args) {
        // Crear una instancia de RegistrarDesdeExcel
        RegistrarDesdeExcel registrarDesdeExcel = new RegistrarDesdeExcel();

        try {
            // Inicia la grabación en formato MP4
            ScreenRecorderUtil.startRecord("registro_usuarios");

            // Configurar el entorno
            registrarDesdeExcel.setUp();

            // Llamar al método para registrar usuarios
            registrarDesdeExcel.registrarUsuarios();
        } catch (Exception e) {
            System.err.println("Error durante la ejecución: " + e.getMessage());
        } finally {
            // Realizar limpieza
            registrarDesdeExcel.tearDown();

            // Detener la grabación
            try {
                ScreenRecorderUtil.stopRecord();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
