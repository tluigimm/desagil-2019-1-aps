package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener, MouseListener {
    private final Switch[] switches;
    private final Gate gate;

    private final JCheckBox[] inputBoxes;
    private final JCheckBox outputBox;

    private final Image image;
    private Light light;
    private Color color;

    public GateView(Gate gate) {
        super(245, 346);
        this.gate = gate;
        light = new Light();
        int inputSize = gate.getInputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();
            gate.connect(i, switches[i]);
        }

        outputBox = new JCheckBox();

        JLabel inputLabel = new JLabel("Input");
        JLabel outputLabel = new JLabel("Output");



        //add(inputLabel, 10, 10, 75, 25);
        //add(outputLabel, 30, 10, 75, 25);

        //add(inputLabel);
        //System.out.println(inputSize);
        if (inputSize > 1) {
            add(inputBoxes[0],30, 132, 150, 25);
            add(inputBoxes[1],30, 222, 150, 25);
        }
        else{
            add(inputBoxes[0],30, 177, 150, 25);
        }


        //add(outputLabel,10, 311, 75, 25);
        add(outputBox,85, 311, 120, 25);

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }

        outputBox.setEnabled(false);
        String name = gate.toString() + ".png";
        //System.out.print(gate.toString());

        URL url = getClass().getClassLoader().getResource(name);
        //System.out.print(url);
        image = getToolkit().getImage(url);

        light.setR(255);
        light.setG(0);
        light.setB(0);
        addMouseListener(this);

        update();
        System.out.println(gate.read());
    }

    private void update() {
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        boolean result = gate.read();

        outputBox.setSelected(result);
        int r = light.getR();
        int g = light.getG();
        int b = light.getB();

        color = new Color(r, g, b);

        this.light.connect(0,gate);

        repaint();

    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        // Se o clique foi dentro do quadrado colorido...
        if (Math.pow((x-(180+10.5)),2) + Math.pow((y-(177+10.5)),2) < Math.pow(25/2,2)){

            // ...então abrimos a janela seletora de cor...
            color = JColorChooser.showDialog(this, null, color);
            if (color != null){

                light.setR(color.getRed());
                light.setG(color.getGreen());
                light.setB(color.getBlue());
            }
            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }


    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 10, 80, 221, 221, this);

        // Desenha um quadrado cheio.

        g.setColor(color);
        g.fillOval(180, 177, 25, 25);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}
