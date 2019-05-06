package br.pro.hashi.ensino.desagil.aps.model;

public abstract class Gate implements SignalEmitter, SignalReceiver {
    private final String name;
    private final int inputSize;
    private final int outputSize;

    protected Gate(String name, int inputSize, int outputSize) {
        this.name = name;
        this.inputSize = inputSize;
        this.outputSize = outputSize;
    }

    public String toString() {
        return name;
    }

    public int getInputSize() {
        return inputSize;
    }

    public int getOutputSize() {
        return outputSize;
    }
}
