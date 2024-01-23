import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class LineChart {
    public static void DrawChart(ArrayList<Double> values){
        JFrame frame = new JFrame("Sin Function Output to Generation");
        frame.setSize(1152,864);
        frame.setContentPane(new myPanel(values));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
}
class myPanel extends JPanel{
    private final int no_Gen;

    private final ArrayList<Double> gen_Best;
    myPanel(ArrayList<Double> gen_Best){
        setBackground(Color.white);
        no_Gen= gen_Best.size();
        this.gen_Best = new ArrayList<>(gen_Best);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setColor(Color.BLACK);
        //Vertical line
        gd.drawLine(50,30,50,750);
        //horizontal line
        //metric being used for graph is 50 - 750
        gd.drawLine(50,750,770,750);
        double max= Math.ceil(gen_Best.stream().max(Double::compare).isPresent()? gen_Best.stream().max(Double::compare).get():0.0);
        double min= gen_Best.stream().min(Double::compare).isPresent()? gen_Best.stream().min(Double::compare).get():0.0;

        double y = min;
        int x = no_Gen / 10;
        // ticks
        for(int i=0; i<700; i+=70)
        {
            gd.drawLine(45,680-i,55,680-i);
            //y tick measurement
            gd.drawString(String.format("%.2f",y),20,680-i);
            gd.drawLine(120+i,745,120+i,755);
            //x tick measurement
            gd.drawString(String.valueOf(x),120+i,777);
            x += no_Gen / 10;
            y += (max - min) /10;
        }
    }

}