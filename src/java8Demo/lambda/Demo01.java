package java8Demo.lambda;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 初识lambda表达式
public class Demo01 {

    public static void main(String[] args) {
        Test01();
    }

    public static void Test01() {
        JFrame jFrame = new JFrame("my jframe");
        JButton jButton = new JButton("my jbutton");

        // 使用jdk1.8以前的写法
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("我点击了");
            }
        });

        // 使用jdk1.8的写法
        jButton.addActionListener((ActionEvent event) ->  // 参数列表
        {
            // 方法体：
            System.out.println("123");
            System.out.println("456");
        });

        jFrame.add(jButton);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
