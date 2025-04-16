package poo.banco;

public class Rendimento extends Conta {

    public Rendimento(String numero, Cliente titular) {
        super(numero, titular);
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo && valor > 0) {
            saldo -= valor;
        }
    }
}

