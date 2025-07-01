package com.itheima;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class MainFrame extends JFrame implements KeyListener {//KeyListener是自带的，在这里实现接口
    int[][] datas={
            {0,2,2,4},
            {2,2,4,4},
            {0,8,2,4},
            {0,32,0,64}
    };
    public MainFrame(){
        initFrame();
        printView();
        setVisible(true);//可视化
    }
    public void initFrame(){
        setSize(514,538);//调用成员方法，设置窗体可见
        setLocationRelativeTo(null);  // 窗口居中显示
        setAlwaysOnTop(true);//置顶
        setDefaultCloseOperation(3);//窗体关闭终止java程序
        setTitle("SWim");
        setLayout(null);//取消默认布局、

        this.addKeyListener(this);//为窗体添加键盘监听
    }
    public void printView(){
        //移除界面内容
        getContentPane().removeAll();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JLabel image =new JLabel(new ImageIcon("D:\\SWim\\"+datas[i][j]+".jpg"));
                image.setBounds(50+100*j,50+100*i,100,100);
                super.getContentPane().add(image);//把JLabel对象添加到面板
            }
        }
        //添加背景
        JLabel image =new JLabel(new ImageIcon("D:\\SWim\\background.jpg"));
        image.setBounds(40,40,420,420);
        super.getContentPane().add(image);//把JLabel对象添加到面板
        //调用父类的方法，并且若子类未改写，不用写super就可

        //刷新界面
        getContentPane().repaint();
    }

    public void printArray(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(datas[i][j]+" ");
            }
            System.out.println();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {//键盘按下
        int code =e.getKeyCode();
//        System.out.println(code);//左上右下37 38 39 40
        if (code==37){
            leftMove();
            System.out.println("左");
            printArray();
        }
        else if (code==38){
            topMove();
            System.out.println("上");
            printArray();
        }
        else if(code==39){
            rightMove();
            System.out.println("右");
            printArray();
        }
        else if(code==40){
            downMove();
            System.out.println("下");
            printArray();
        }
        printView();//重新绘制
    }

    @Override
    public void keyReleased(KeyEvent e) {//键盘松开

    }

    public void leftMove(){//左移要先把非0挪到前面再进行一次合并
        for (int i = 0; i < datas.length; i++) {//datas.length数组长度，即几行
            int newArr[] = new int[4];//自动初始化为0
            int index = 0;
            for (int x = 0; x < datas[i].length; x++) {//取出一维数组
                if (datas[i][x]!= 0) {//前置非0数据
                    newArr[index] = datas[i][x];
                    index++;
                }
            }
            //datas[i] = newArr;//转换之后的数组变成原本的一行
            System.arraycopy(newArr, 0, datas[i], 0, newArr.length);

            //合并元素=相同相加+后续元素前移
            for (int j = 0; j <3; j++) {
                if (datas[i][j]==datas[i][j+1]&&datas[i][j]!=0) {
                    datas[i][j] *= 2;
                    //后续前移，末尾补零
                    for (int k = j+1; k <3; k++) {
                        datas[i][k]=datas[i][k+1];
                    }
                    datas[i][3]=0;
                }
            }
        }
    }

    //右移本质上也可以切换成左移
    public void rightMove(){
        //1.二维数组反转
        reverse();
        //2.左移动
        leftMove();
        //3.反转数组
        reverse();
    }
    private void reverse() {
        for (int i = 0; i < datas.length; i++) {
            //调用reverseArrays
            reverseArray(datas[i]);
        }
    }
    public void reverseArray(int[] arr){//一维数组反转
        for (int start = 0,end=arr.length-1; start<end;start++,end--) {
            int temp=arr[start];
            arr[start]=arr[end];
            arr[end]=temp;
        }

    }

    //上移，先逆时针旋转再左移再旋转回来
    private void topMove() {

        rotateMatrixLeft();
        leftMove();
        rotateMatrixRight();
    }

    private void rotateMatrixLeft() {//逆时针
        //创建一个新数组，再把新数组地址赋值给datas
        int[][] temp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[3-j][i]=datas[i][j];
            }
        }
        datas=temp;
    }

    private void rotateMatrixRight() {
        int[][] temp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[j][3-i]=datas[i][j];
            }
        }
        datas=temp;
    }

    //下移，先顺时针旋转再左移再旋转回来
    private void downMove() {
        rotateMatrixRight();
        leftMove();
        rotateMatrixLeft();
    }

    
}
