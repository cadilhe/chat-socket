package chat;

import java.util.ArrayList;

/**
 *
 * @author Lincoln
 */
public class ListaDeClientes {
    
    private ArrayList<GerenciadorClienteServidor> clientes = new ArrayList<>();
    
    

    public ArrayList<GerenciadorClienteServidor> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<GerenciadorClienteServidor> clientes) {
        this.clientes = clientes;
    }
    
    
}
