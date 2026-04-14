package pt.domuscontrol.ui;

import java.util.Scanner;

public class ConsoleUtils {

    private static final Scanner scanner = new Scanner(System.in);

    private ConsoleUtils() {}

    public static int lerOpcao(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("  Opção inválida. Introduza um número.");
            }
        }
    }

    public static int lerInteiro(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(linha);
                if (valor >= min && valor <= max) return valor;
                System.out.printf("  Valor deve estar entre %d e %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  Valor inválido. Introduza um número inteiro.");
            }
        }
    }

    public static double lerDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim();
            try {
                double valor = Double.parseDouble(linha.replace(',', '.'));
                if (valor >= min && valor <= max) return valor;
                System.out.printf("  Valor deve estar entre %.1f e %.1f.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  Valor inválido. Introduza um número.");
            }
        }
    }

    public static String lerTexto(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim();
            if (!linha.isEmpty()) return linha;
            System.out.println("  Campo obrigatório.");
        }
    }

    public static String lerTextoOpcional(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static boolean lerSimNao(String prompt) {
        while (true) {
            System.out.print(prompt + " (s/n): ");
            String r = scanner.nextLine().trim().toLowerCase();
            if (r.equals("s") || r.equals("sim")) return true;
            if (r.equals("n") || r.equals("nao") || r.equals("não")) return false;
            System.out.println("  Responda 's' ou 'n'.");
        }
    }

    public static void pausar() {
        System.out.print("\n  Prima Enter para continuar...");
        scanner.nextLine();
    }

    public static void imprimirSeparador() {
        System.out.println("─".repeat(60));
    }

    public static void imprimirTitulo(String titulo) {
        imprimirSeparador();
        System.out.println("  " + titulo);
        imprimirSeparador();
    }

    public static void imprimirErro(String mensagem) {
        System.out.println("  [ERRO] " + mensagem);
    }

    public static void imprimirSucesso(String mensagem) {
        System.out.println("  [OK] " + mensagem);
    }
}
