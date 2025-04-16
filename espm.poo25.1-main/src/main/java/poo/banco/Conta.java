package poo.banco;

public abstract class Conta {
    private String numero;
    private Cliente titular;
    protected double saldo;

    public Conta(String numero, Cliente titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0.0;
    }

    public void depositar(double valor) {
        if (valor > 0) saldo += valor;
    }

    public abstract void sacar(double valor);

    public Cliente getTitular() {
        return titular;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + numero + " - " + titular.getNome() +
               " | Saldo: R$ " + String.format("%.2f", saldo);
    }
}
