package com.vempraarena.plataforma.utils;

public class DataMaskingUtils {

    /**
     * Oculta os 3 primeiros dígitos e os 2 últimos do CPF.
     * Exemplo com formatação: 123.456.789-00 -> ***.456.789-**
     * Exemplo sem formatação: 12345678900 -> ***456789**
     */
    public static String maskCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return cpf;
        }

        // Se o CPF estiver formatado (ex: 123.456.789-00)
        if (cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            return "***" + cpf.substring(3, 12) + "**";
        } 
        
        // Se o CPF for apenas números (ex: 12345678900)
        if (cpf.matches("\\d{11}")) {
            return "***" + cpf.substring(3, 9) + "**";
        }

        // Retorno padrão de ofuscação caso fuja da regra esperada
        return "***.***.***-**";
    }

    /**
     * Mostra apenas a primeira letra do nome do e-mail e mantém o domínio.
     * Exemplo: caua@gmail.com -> c***@gmail.com
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }

        String[] parts = email.split("@");
        String namePart = parts[0];
        String domainPart = parts[1];

        if (namePart.length() <= 1) {
            return namePart + "***@" + domainPart;
        }

        return namePart.charAt(0) + "***@" + domainPart;
    }
}
