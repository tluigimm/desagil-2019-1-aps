// FONTE DAS IMAGENS: https://en.wikipedia.org/wiki/Logic_gate (domínio público)

package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.HalfAdder;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener {
    private static final int BORDER = 10;
    private static final int SWITCH_SIZE = 18;
    private static final int GATE_WIDTH = 90;
    private static final int GATE_HEIGHT = 60;

    private final Switch[] switches;
    private final Gate gate;
    private final JCheckBox[] inputBoxes;
    private final JCheckBox[] outputBox;
    private final Image image;

    public GateView(Gate gate) {
        super(BORDER + SWITCH_SIZE + GATE_WIDTH + SWITCH_SIZE + BORDER, GATE_HEIGHT);

        this.gate = gate;

        int inputSize = gate.getInputSize();
        int outputSize = gate.getOutputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];
        outputBox = new JCheckBox[outputSize];


        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }

        for (int i=0; i < outputSize; i++){
            outputBox[i] = new JCheckBox();
        }

        int x, y, step;

        x = BORDER;
        y = -(SWITCH_SIZE / 2);
        step = (GATE_HEIGHT / (inputSize + 1));

        for (JCheckBox inputBox : inputBoxes) {
            y += step;
            add(inputBox, x, y, SWITCH_SIZE, SWITCH_SIZE);
        }

        if (outputSize == 1){
            add(outputBox[0], BORDER + SWITCH_SIZE + GATE_WIDTH , (GATE_HEIGHT - SWITCH_SIZE) / 2, SWITCH_SIZE, SWITCH_SIZE);
        }

        else {
            add(outputBox[0], BORDER + SWITCH_SIZE + GATE_WIDTH , y - SWITCH_SIZE - 3, SWITCH_SIZE, SWITCH_SIZE);
            add(outputBox[1], BORDER + SWITCH_SIZE + GATE_WIDTH , y + step - SWITCH_SIZE - 3, SWITCH_SIZE, SWITCH_SIZE);
        }
//        }

        if (gate.toString() != "HalfAdder") {
            String name = gate.toString() + ".png";
            URL url = getClass().getClassLoader().getResource(name);
            image = getToolkit().getImage(url);
        }
        else{
            String name = "Half-Adder.png";
            URL url = getClass().getClassLoader().getResource(name);
            image = getToolkit().getImage(url);
        }


        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }

        for (JCheckBox outputBox : outputBox) {
            outputBox.addItemListener(this);
            outputBox.setEnabled(false);
        }

        update();
    }

    private void update() {
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        if (gate.getOutputSize() == 1){
            outputBox[0].setSelected(gate.read(0));
        }
        else {
            outputBox[0].setSelected(gate.read(0));
            outputBox[1].setSelected(gate.read(1));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, BORDER + SWITCH_SIZE, 0, GATE_WIDTH, GATE_HEIGHT, this);

        getToolkit().sync();
    }
}
