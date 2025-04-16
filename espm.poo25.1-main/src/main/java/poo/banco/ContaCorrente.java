package poo.banco;

public class ContaCorrente extends Conta {
    private double limite;

    public ContaCorrente(String numero, Cliente titular) {
        super(numero, titular);
        this.limite = 0;
    }

    public ContaCorrente(String numero, Cliente titular, double limite) {
        super(numero, titular);
        this.limite = limite;
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo + limite && valor > 0) {
            saldo -= valor;
        }
    }
}

