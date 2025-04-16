
package poo.banco;

public class ContaInvestimento extends Conta {

    public ContaInvestimento(String numero, Cliente titular) {
        super(numero, titular);
    }

    @Override
    public void sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque realizado com sucesso");
        }
    }

    @Override
    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }

    public void aplicarRendimento() {
        saldo += saldo * 0.005; // 0.5% de rendimento
    }

    @Override
    public String toString() {
        return "Conta Investimento " + getNumero() + " - " + getTitular().getNome();
    }
}
