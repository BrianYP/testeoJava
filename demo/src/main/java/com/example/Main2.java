package com.example;

public class Main2 {
    public static void main(String[] args) {
        excelCrearUsuarioAdmin app = new excelCrearUsuarioAdmin(); // Cambié de App a excel
        try {
            // Iniciar la grabación en formato MP4
            ScreenRecorderUtil.startRecord("registro_usuariosAdmin");

            app.setUp();
            app.runTests();
        } catch (Exception e) {
            System.err.println("Error durante la ejecución: " + e.getMessage());
        } finally {
            app.tearDown();

            // Detener la grabación
            try {
                ScreenRecorderUtil.stopRecord();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
