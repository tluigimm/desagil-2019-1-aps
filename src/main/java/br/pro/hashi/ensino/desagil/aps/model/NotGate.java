package br.pro.hashi.ensino.desagil.aps.model;

public class NotGate extends Gate {
    private final NandGate nand;


    public NotGate() {
        super("NOT", 1, 1);

        nand = new NandGate();
    }


    @Override
    public boolean read(int outputPin) {
        if (outputPin != 0) {
            throw new IndexOutOfBoundsException();
        }
        return nand.read();
    }


    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        if (inputPin != 0) {
            throw new IndexOutOfBoundsException();
        }
        nand.connect(0, emitter);
        nand.connect(1, emitter);
    }
}
