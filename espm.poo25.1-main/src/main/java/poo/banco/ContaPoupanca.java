
package poo.banco;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(String numero, Cliente titular) {
        super(numero, titular);
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo && valor > 0) {
            saldo -= valor;
        }
    }
}