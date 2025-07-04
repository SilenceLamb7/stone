package com.itheima;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Random;

public class MainFrame extends JFrame implements KeyListener {//KeyListener是自带的，在这里实现接口
    int[][] datas=new int[4][4];
    int loseFlag=0;
    int score=0;
    public void initData(){
        generateNum();
        generateNum();
    }
    public MainFrame(){
        initFrame();
        initData();
        printView();
        setVisible(true);//可视化
    }
    public void initFrame(){//初始化窗体
        //setSize(514,538);//调用成员方法，设置窗体可见
        setSize(540,600);
        setLocationRelativeTo(null);  // 窗口居中显示
        setAlwaysOnTop(true);//置顶
        setDefaultCloseOperation(3);//窗体关闭终止java程序
        setTitle("SWim");
        setLayout(null);//取消默认布局、

        this.addKeyListener(this);//为窗体添加键盘监听
    }
    public void printView(){//绘制游戏界面
        //移除界面内容
        getContentPane().removeAll();

        if (loseFlag==1){
            JLabel loseLabel =new JLabel(new ImageIcon("D:\\SWim\\lose.jpg"));
            loseLabel.setBounds(40,40,420,420);
            getContentPane().add(loseLabel);//把JLabel对象添加到面板
        }
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
        getContentPane().add(image);//把JLabel对象添加到面板
        //调用父类的方法，并且若子类未改写，不用写super就可


        JLabel back =new JLabel("得分:"+score);
        back.setBounds(50,10,500,30);
        back.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        getContentPane().add(back);//把JLabel对象添加到面板
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
        //System.out.println(code);//左上右下37 38 39 40
        if (code==37){
            leftMove(1);
            System.out.println("左");
            printArray();
            generateNum();
        }
        else if (code==38){
            topMove(1);
            System.out.println("上");
            printArray();
            generateNum();
        }
        else if(code==39){
            rightMove(1);
            System.out.println("右");
            printArray();
            generateNum();
        }
        else if(code==40){
            downMove(1);
            System.out.println("下");
            printArray();
            generateNum();
        }
        check();
        printView();//重新绘制
    }

    @Override
    public void keyReleased(KeyEvent e) {//键盘松开

    }

    public void leftMove(int flag){//左移要先把非0挪到前面再进行一次合并

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
                    if(flag==1){
                        score+=datas[i][j];
                    }

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
    public void rightMove(int flag){
        //1.二维数组反转
        reverse();
        //2.左移动
        leftMove(flag);
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
    private void topMove(int flag) {

        rotateMatrixLeft();
        leftMove(flag);
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
    public void downMove(int flag) {
        rotateMatrixRight();
        leftMove(flag);
        rotateMatrixLeft();
    }

    public boolean checkLeft(){
        int[][] newArr=new int[4][4];
        copyArray(datas,newArr);
        leftMove(2);
        boolean flag=false;
        lo:
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                if (datas[i][j]!=newArr[i][j]){
                    flag=true;
                    break lo;
                }
            }
        }
        copyArray(newArr,datas);
        return flag;
    }

    public void check(){
        if (checkLeft()==false&&checkRight()==false&&checkTop()==false&&checkDown()==false){
            System.out.println("游戏失败了");
            loseFlag=1;
        }
    }

    public boolean checkDown() {
        int[][] newArr=new int[4][4];
        copyArray(datas,newArr);
        downMove(2);
        boolean flag = false;
        lo:
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                if (datas[i][j]!=newArr[i][j]){
                    flag=true;
                    break lo;
                }
            }
        }
        copyArray(newArr,datas);
        return flag;
    }

    public boolean checkTop() {
        int[][] newArr=new int[4][4];
        copyArray(datas,newArr);
        topMove(2);
        boolean flag=false;
        lo:
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                if (datas[i][j]!=newArr[i][j]){
                    flag=true;
                    break lo;
                }
            }
        }
        copyArray(newArr,datas);
        return flag;
    }

    public boolean checkRight() {
        int[][] newArr=new int[4][4];
        copyArray(datas,newArr);
        rightMove(2);
        boolean flag=false;
        lo:
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                if (datas[i][j]!=newArr[i][j]){
                    flag=true;
                    break lo;
                }
            }
        }
        copyArray(newArr,datas);
        return flag;
    }

    public void copyArray(int[][]src,int[][]newArr){
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[i].length; j++) {
                newArr[i][j]=src[i][j];
            }
        }
    }

    public void generateNum(){
        int[] arrayI={-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        int[] arrayJ={-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        int w=0;
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                if (datas[i][j]==0){
                    arrayI[w]=i;
                    arrayJ[w]=j;
                    w++;
                    //w 从 0 开始计数，但 写入位置是 arrayI[w] 和 arrayJ[w] 之后才 w++，因此有效索引是 [0, w-1]。
                }
            }
        }
        if(w!=0){
            Random r = new Random();
            int index =r.nextInt(w);
            int x=arrayI[index];
            int y=arrayJ[index];
            datas[x][y]=2;
        }

    }
}
