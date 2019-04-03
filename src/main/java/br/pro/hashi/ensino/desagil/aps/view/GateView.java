package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.*;
import sun.misc.Signal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GateView extends JPanel implements ActionListener{

    private final Gate gate;

    private final JCheckBox aField;
    private final JCheckBox bField;
    private final JCheckBox resultField;

    public GateView(Gate gate){

        this.gate = gate;

        aField = new JCheckBox();
        bField = new JCheckBox();
        resultField = new JCheckBox();


        JLabel Label = new JLabel("Entrada:");
        JLabel resultLabel = new JLabel("Saida:");

        if (gate.getInputSize() == 2){


            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            add(Label);
            add(aField);
            add(bField);
            add(resultLabel);
            add(resultField);

            aField.addActionListener(this);
            bField.addActionListener(this);
            resultField.setEnabled(false);

            update();
        }
        else {

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            add(Label);
            add(aField);
            add(resultLabel);
            add(resultField);


            aField.addActionListener(this);
            resultField.setEnabled(false);

            update();
        }

    }

    private void update() {
        boolean a;
        boolean b;

        Switch aSwitch;
        Switch bSwitch;

        if (gate.getInputSize() == 2){

            a = aField.isSelected();
            b = bField.isSelected();

            aSwitch = new Switch();
            bSwitch = new Switch();

            // define o switch de acordo com o check em a
            if (a){
                aSwitch.turnOn();
            }
            else{
                aSwitch.turnOff();
            }

            // define o switch de acordo com o check em b
            if (b){
                bSwitch.turnOn();
            }
            else{
                bSwitch.turnOff();
            }

            gate.connect(0, aSwitch);
            gate.connect(1, bSwitch);


            boolean result = gate.read();

            // da check na ultima box caso resultado seja true, uncheck caso seja false
            resultField.setSelected(result);

        }

        else{

            a = aField.isSelected();

            aSwitch = new Switch();


            if (a){
                aSwitch.turnOn();
            }
            else{
                aSwitch.turnOff();
            }
            gate.connect(0, aSwitch);



            boolean result = gate.read();

            resultField.setSelected(result);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }




}
