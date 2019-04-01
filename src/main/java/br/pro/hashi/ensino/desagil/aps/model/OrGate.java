package br.pro.hashi.ensino.desagil.aps.model;

public class OrGate extends Gate{

    private final NandGate nand1;
    private final NandGate nand2;
    private final NandGate nand3;


    public OrGate() {
        super(4);
        nand1 = new NandGate();
        nand2 = new NandGate();
        nand3 = new NandGate();
    }

    @Override
    public boolean read() {
        return nand3.read();
    }

    @Override
    public void connect(int inputPin, SignalEmitter signal) {

        if (inputPin < 0 || inputPin > 1) {
            throw new IndexOutOfBoundsException(inputPin);
        }

        if (inputPin == 0) {
            nand1.connect(0, signal);
            nand1.connect(1, signal);
        } else {
            nand2.connect(0, signal);
            nand2.connect(1, signal);
        }

        nand3.connect(0, nand1);
        nand3.connect(1, nand2);
    }
}



