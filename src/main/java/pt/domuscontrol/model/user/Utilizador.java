package pt.domuscontrol.model.user;

import java.io.Serializable;

public class Utilizador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String email;
    private String password;

    public Utilizador(String id, String nome, String email, String password) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID não pode ser vazio");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email não pode ser vazio");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password não pode ser vazia");
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    public boolean autenticar(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    // Getters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    // Setters com validação
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio");
        this.nome = nome;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email não pode ser vazio");
        this.email = email;
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password não pode ser vazia");
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s <%s>", id, nome, email);
    }
}
