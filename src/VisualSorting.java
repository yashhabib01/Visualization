import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.IllegalFormatCodePointException;
import java.util.Random;

import static java.lang.Integer.SIZE;

public class VisualSorting {

    //Frames
    private JFrame jFrame;
    // general variable
    private int len = 50;
    private int off = 0;
    private int  currentAlog = 0;
    private final int Height  = 600;
    private int spd= 15;
    private int compare  = 0;
    private int acc= 0;
    //Graph variable
    private final  int Size  = 800;

    private int current = -1;
    private int check = -1;
    // is for the array rect
    private float width = Size/len;
    private int heightOfArr = Height/len;
    // exit
    public  int type = 0;


    // Arrays
    public int [] list;
    private String types[]= {"Bar Graph  " };
    private String [] algorithms = {"Selection Sort " , "Bubble Sort", "Insertion Sort","Quick Sort","Merge Sort"};

//Booleans
    private boolean  sorting = false;
    private boolean shuffled = true;
    //Util Objects
    // Sorting
     Algorithm algorithm = new Algorithm();
    Random  r = new Random();
    //Panels

    JPanel tools = new JPanel();
     GraphCanvas canvas;
    //Lables
    JLabel delayL  = new JLabel("Delay:");
    JLabel msL = new JLabel(spd+" ms");
    JLabel sizeL = new JLabel("Size :");
    JLabel lenL = new JLabel(len+"");
    JLabel compareL = new JLabel("Comparisons : " + compare);
    JLabel accessL = new JLabel("Array Accesses : " + acc);
    JLabel algorithmL = new JLabel("Algorithms");
    JLabel typeL = new JLabel("Graph Types");

    //Drop down box
    JComboBox alg  = new JComboBox(algorithms);
    JComboBox graph  = new JComboBox(types);


    //BUTTONS
    JButton sort = new JButton("Sort");
    JButton shuffle = new JButton("Shuffle");
    JButton credit = new JButton("Credit");
    //SLIDERS
    JSlider size = new JSlider(JSlider.HORIZONTAL,1,3,1);
    JSlider speed = new JSlider(JSlider.HORIZONTAL,0,100,spd);
    //BORDER STYLE
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    public VisualSorting(){
        shuffleList();
        initialize();
    }



    public void createList(){
        list = new int[len];
        for(int  i = 0 ; i< len ; i++){
            list[i] = i+1;
        }
    }
    private void shuffleList(){
        createList();
        for(int a = 0; a < 500; a++) {	//SHUFFLE RUNS 500 TIMES
            for(int i = 0; i < len; i++) {	//ACCESS EACH ELEMENT OF THE LIST
                int rand = r.nextInt(len);	//PICK A RANDOM NUM FROM 0-LEN
                int temp = list[i];			//SETS TEMP INT TO CURRENT ELEMENT
                list[i] = list[rand];		//SWAPS THE CURRENT INDEX WITH RANDOM INDEX
                list[rand] = temp;			//SETS THE RANDOM INDEX TO THE TEMP
            }
        }
        sorting = false;
        shuffled = true;
    }

    public void initialize(){

        //Setup frame

        jFrame = new JFrame();
        jFrame.setSize(820,740);
        jFrame.setTitle("Visual Sort");
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.getContentPane().setLayout(null);

        //SET UP TOOLBAR
        tools.setLayout(null);
       tools.setBounds(5,605,810,90);
        tools.setBorder(BorderFactory.createTitledBorder(loweredetched,"Customize"));

        //SET UP ALGORITHM LABEL
        algorithmL.setHorizontalAlignment(JLabel.CENTER);
        algorithmL.setBounds(10,20,100,25);
        tools.add(algorithmL);

        //SET UP DROP DOWN
        alg.setBounds(10,50,120,25);
        tools.add(alg);

        //SET UP GRAPH TYPE LABEL
        typeL.setHorizontalAlignment(JLabel.CENTER);
        typeL.setBounds(140,20,100,25);
        tools.add(typeL);

        //SET UP GRAPH TYPE DROP DOWN
        graph.setBounds(140,50,120,25);
        tools.add(graph);

        //SET UP SORT BUTTON
        sort.setBounds(270,20,100,25);
        tools.add(sort);

        //SET UP SHUFFLE BUTTON
        shuffle.setBounds(270,50,100,25);
        tools.add(shuffle);

        //SET UP DELAY LABEL
        delayL.setHorizontalAlignment(JLabel.LEFT);
        delayL.setBounds(380,20,50,25);
        tools.add(delayL);

        //SET UP MS LABEL
        msL.setHorizontalAlignment(JLabel.LEFT);
        msL.setBounds(416,20,50,25);
        tools.add(msL);

        //SET UP SPEED SLIDER
        speed.setMajorTickSpacing(5);
        speed.setBounds(380,50,75,25);
        speed.setPaintTicks(false);
        tools.add(speed);

        //SET UP SIZE LABEL
        sizeL.setHorizontalAlignment(JLabel.LEFT);
        sizeL.setBounds(460,20,50,25);
        tools.add(sizeL);

        //SET UP LEN LABEL
        lenL.setHorizontalAlignment(JLabel.LEFT);
        lenL.setBounds(486,20,50,25);
        tools.add(lenL);

        //SET UP SIZE SLIDER
        size.setMajorTickSpacing(50);
        size.setBounds(460,50,75,25);
        size.setPaintTicks(false);
        tools.add(size);

        //SET UP COMPARISONS LABEL
        compareL.setHorizontalAlignment(JLabel.LEFT);
        compareL.setBounds(550,20,200,25);
        tools.add(compareL);

        //SET UP ARRAY ACCESS LABEL
        accessL.setHorizontalAlignment(JLabel.LEFT);
        accessL.setBounds(550,50,200,25);
        tools.add(accessL);


        //SET UP CREDIT BUTTON
        credit.setBounds(680,20,100,25);
        tools.add(credit);

        // setUp canvas

        canvas = new GraphCanvas();
        canvas.setBounds(10,0,Size,600);
        canvas.setBorder(BorderFactory.createLineBorder(Color.black));
        jFrame.getContentPane().add(tools);
        jFrame.getContentPane().add(canvas);


        //Add action event Listener

        alg.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                currentAlog = alg.getSelectedIndex();

            }
        });

       graph.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               // canvas.setType(graph.getSelectedIndex());
                type = graph.getSelectedIndex();
                shuffleList();
                reset();
                Update();
            }
        });




        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(shuffled) {
                    sorting = true;
                    compare = 0;
                    acc = 0;
                }

            }
        });

		shuffle.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            shuffleList();
             reset();
        }
    });

        speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                spd = (int)speed.getValue();
                msL.setText(spd+" ms");
            }
        });


        size.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {





                if(size.getValue() == 1){
                len = 50;
                }else if(size.getValue() == 2){
                    len  = 100;
                }else if(size.getValue() == 3){
                    len = 200;
                }

                   lenL.setText(len + "");




                if(list.length != len)
                    shuffleList();
                reset();
            }

        });


        credit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jFrame, "	                         Visual Sort\n"
                        + "             Copyright (c) 2020-2021\n"
                        + "                         Yash Habib\n"
                        + "           Build Date:  December 15 , 2020   ", "Credit", JOptionPane.PLAIN_MESSAGE, new ImageIcon(""));
            }
        });
        sorting();
    }
    public void sorting(){
        if (sorting) {
            try {
                switch (currentAlog) {

                    case 0:
                        algorithm.selectionSort();
                        break;
                    case 2:
                        algorithm.insertionSort(0, len - 1);
                        break;
                    case 3:
                        algorithm.quickSort(0, len - 1);
                        break;
                    case 4: algorithm.mergeSort(0,len-1);
                    break;
                    default:
                        algorithm.bubbleSort();
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        reset();
        pause();

    }
    public void reset() {
        sorting = false;
        current = -1;
        check = -1;
        off = 0;
        Update();
    }

    public void pause() {
        int i = 0;
        while(!sorting) {	//LOOP RUNS UNTIL SORTING STARTS
            i++;
            if(i > 100)
                i = 0;
            try {
                Thread.sleep(1);
            } catch(Exception e) {}
        }
        sorting();	//EXIT THE LOOP AND START SORTING
    }



    public void Update() {
        width = Size/len;


            heightOfArr = Height / len;


        canvas.repaint();
        compareL.setText("Comparisons : " + compare);
        accessL.setText("Array Accesses : " + acc);
    }
    public void delay() {
        try {
            Thread.sleep(spd);
        } catch(Exception e) {}
    }

    public static void main(String args []){
        new VisualSorting();
    }

    class GraphCanvas extends JPanel {

        public GraphCanvas() {
            setBackground(Color.black);
        }

        //PAINTS THE GRAPH

        public void paintComponent(Graphics g) {
            super.paintComponent(g);




            for (int i = 0; i < len; i++) {    //RUNS TROUGH EACH ELEMENT OF THE LIST
                int HEIGHT;

                     HEIGHT = list[i] * heightOfArr;






                //SETS THE HEIGHT OF THE ELEMENT ON THE GRAPH
                if (type == 0) {        //BAR GRAPH TYPE
                    g.setColor(Color.cyan);    //DEFAULT COLOR
                    if (current > -1 && i == current) {
                        g.setColor(Color.green);    //COLOR OF CURRENT
                    }
                    if (check > -1 && i == check) {
                        g.setColor(Color.red);    //COLOR OF CHECKING
                    }

                    g.fillRect(Math.round(i * width), Height- HEIGHT, Math.round(width), HEIGHT);
                    g.setColor(Color.black);
                    g.drawRect(Math.round(i * width), Height - HEIGHT, Math.round(width), HEIGHT);

                }
            }


        }
    }



    public class Algorithm{
        public void insertionSort(int start, int end) {
            for(int i = start+1; i <= end; i++) {
                current = i;
                int j = i;
                while(list[j] < list[j-1] && sorting) {
                    swap(j,j-1);
                    check = j;
                    compare++;	acc+=2;
                    Update();
                    delay();
                    if(j > start+1)
                        j--;
                }
            }
        }


        public void selectionSort() {
            int c = 0;
            while(c < len && sorting) {
                int sm = c;
                current = c;
                for(int i = c+1; i < len; i++) {
                    if(!sorting)
                        break;
                    if(list[i] < list[sm]) {
                        sm = i;
                    }
                    check = i;
                    compare++;	acc+=2;
                    Update();
                    delay();
                }
                if(c != sm)
                    swap(c,sm);
                c++;
            }
        }

        public void bubbleSort() {
            int c = 1;
            boolean noswaps = false;
            while(!noswaps && sorting) {
                current = len-c;
                noswaps = true;
                for(int i = 0; i < len-c; i++) {
                    if(!sorting)
                        break;
                    if(list[i+1] < list[i]) {
                        noswaps = false;
                        swap(i,i+1);
                    }
                    check = i+1;
                    compare++;	acc+=2;
                    Update();
                    delay();
                }
                c++;
            }
        }

        public void quickSort(int lo, int hi) {
            if(!sorting)
                return;
            current = hi;
            if(lo < hi) {
                int p = partition(lo,hi);
                quickSort(lo,p-1);
                quickSort(p+1, hi);
            }
        }

        public int partition(int lo, int hi) {
            int pivot = list[hi];	acc++;
            int i = lo - 1;
            for(int j = lo; j < hi; j++) {
                check = j;
                if(!sorting)
                    break;
                if(list[j] < pivot) {
                    i++;
                    swap(i,j);
                }
                compare++;	acc++;
                Update();
                delay();
            }
            swap(i+1,hi);
            Update();
            delay();
            return i+1;
        }

        void merge(int l, int m, int r)
        {
            int n1 = m - l + 1;
            int n2 = r - m;

            int L[] = new int [n1];
            int R[] = new int [n2];

            for (int i=0; i<n1; i++) {
                L[i] = list[l + i];	acc++;
            }
            for (int j=0; j<n2; j++) {
                R[j] = list[m + 1+ j];	acc++;
            }
            int i = 0, j = 0;

            int k = l;
            while (i < n1 && j < n2 && sorting) {
                check = k;
                if (L[i] <= R[j]) {
                    list[k] = L[i];	acc++;
                    i++;
                } else {
                    list[k] = R[j];	acc++;
                    j++;
                }
                compare++;
                Update();
                delay();
                k++;
            }

            while (i < n1 && sorting) {
                list[k] = L[i];	acc++;
                i++;
                k++;
                Update();
                delay();
            }

            while (j < n2 && sorting) {
                list[k] = R[j];	acc++;
                j++;
                k++;
                Update();
                delay();
            }
        }

        public void mergeSort(int l, int r) {
            if (l < r) {
                int m = (l+r)/2;
                current = r;
                mergeSort(l, m);
                mergeSort(m+1, r);

                merge(l, m, r);
            }
        }

        public void swap(int i1, int i2) {
            int temp = list[i1];	acc++;
            list[i1] = list[i2];	acc+=2;
            list[i2] = temp;	acc++;
        }
    }
}
