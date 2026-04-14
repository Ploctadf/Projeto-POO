package pt.domuscontrol.persistence;

import pt.domuscontrol.exception.PersistenciaException;

import java.io.*;

public class RepositorioBinario {

    private static final String FICHEIRO_PADRAO = "domuscontrol.dat";

    private RepositorioBinario() {}

    public static void guardar(BaseDados bd) throws PersistenciaException {
        guardar(bd, FICHEIRO_PADRAO);
    }

    public static void guardar(BaseDados bd, String caminho) throws PersistenciaException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(bd);
        } catch (IOException e) {
            throw new PersistenciaException("Erro ao guardar ficheiro '" + caminho + "': " + e.getMessage());
        }
    }

    public static BaseDados carregar() throws PersistenciaException {
        return carregar(FICHEIRO_PADRAO);
    }

    public static BaseDados carregar(String caminho) throws PersistenciaException {
        File f = new File(caminho);
        if (!f.exists()) throw new PersistenciaException("Ficheiro não encontrado: " + caminho);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            return (BaseDados) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Erro ao carregar ficheiro '" + caminho + "': " + e.getMessage());
        }
    }

    public static boolean existeFicheiro() {
        return new File(FICHEIRO_PADRAO).exists();
    }
}
