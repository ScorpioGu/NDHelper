package com.cc4j.gui;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.Iterator;
import java.util.Map;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import com.cc4j.serial.NdpSerial;
import com.cc4j.serial.SerialInteractor;
import com.cc4j.pojo.protocol.Bmp;
import com.cc4j.pojo.protocol.Disco;
import com.cc4j.pojo.protocol.Hello;
import com.cc4j.pojo.protocol.Hello_S;
import com.cc4j.pojo.protocol.Hello_SR;
import com.cc4j.pojo.Mote2;
import com.cc4j.pojo.protocol.Searchlight_S;
import com.cc4j.pojo.protocol.Searchlight_Stripe_S;
import com.cc4j.pojo.protocol.UConnect;

public class NeigDiscoUI extends JFrame {

	private JPanel contentPane;
	private JTextField channel;
	private JTextField power;
	private JTextField textField_3_1_1;
	private JTextField textField_3_1_2;
	private JTextField textField_3_1_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField discoSlotLength_A;
	private JTextField discoPrime1_A;
	private JTextField discoPrime2_A;
	private JTextField discoSlotLength_B;
	private JTextField discoPrime1_B;
	private JTextField discoPrime2_B;
	
	JRadioButton slotHeaderAndTail = null;
	JRadioButton slotStart = null;
	JRadioButton slotMiddle = null;
	JRadioButton slotEnd = null;
	
	JComboBox comboBox = null;
	JComboBox CCA = null;

	private JComboBox selectProtocol;
	JPanel panel_3_1 = null;							
	JPanel panel_3_2 = null;							
	
	JPanel panel_3_1_1 = null;							//放A节点参数的面板
	JPanel panel_3_2_1 = null;							//放B节点参数的面板
	JLabel lblA = null;
	private JTextField UConnectSlotLength_A;
	private JTextField UConnectPrime_A;
	private JTextField UConnectSlotLength_B;
	private JTextField UConnectPrime_B;
	private JTextField repeatNumber;
	private JTextField bootTimeA;
	private JTextField bootTimeB;
	private JTextField Searchlight_S_SlotLength_A;
	private JTextField Searchlight_S_t_A;
	private JTextField Searchlight_S_SlotLength_B;
	private JTextField Searchlight_S_t_B;
	private JTextField helloSlotLength_A;
	private JTextField hello_N_A;
	private JTextField hello_C_A;
	private JTextField helloSlotLength_B;
	private JTextField hello_C_B;
	private JTextField hello_N_B;
	private JTextField SearchlightStripe_S_SlotLength_A;
	private JTextField SearchlightStripe_S_SlotExtendLength_A;
	private JTextField SearchlightStripe_S_T_A;
	private JTextField SearchlightStripe_S_SlotLength_B;
	private JTextField SearchlightStripe_S_SlotExtendLength_B;
	private JTextField SearchlightStripe_S_T_B;
	private JTextField hello_S_SlotExtendLen_A;
	private JTextField hello_S_SlotLen_A;
	private JTextField hello_S_C_A;
	private JTextField hello_S_N_A;
	private JTextField hello_S_SlotExtendLen_B;
	private JTextField hello_S_SlotLen_B;
	private JTextField hello_S_C_B;
	private JTextField hello_S_N_B;
	private JTextField bmpPeriodLen_A;
	private JTextField bmpListenTimeLen_A;
	private JTextField bmpPeriodLen_B;
	private JTextField bmpListenTimeLen_B;
	
	private JTextField countNumber;
	private JTextField fileLine;
	
	
	private Map<Integer, Mote2> moteMap; 		//存放连接到串口的节点
	
	private int moteA_ID;				//存放节点ID，默认两个节点
	private int moteB_ID;

	
	/** 
	 * Launch the application.
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NeigDiscoUI frame = new NeigDiscoUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	private void updateToDisco() {												//选择disoc协议时，独立参数设置界面
		
		JLabel lblNewLabel_2 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		lblNewLabel_2.setBounds(10, 22, 54, 15);
		panel_3_1_1.add(lblNewLabel_2);
		
		discoSlotLength_A = new JTextField("320");							//disco  协议参数
		discoSlotLength_A.setBounds(100, 19, 66, 21);
		panel_3_1_1.add(discoSlotLength_A);
		discoSlotLength_A.setColumns(10);
		
		JLabel label_3 = new JLabel("\u7D20\u65701");
		label_3.setBounds(10, 50, 54, 15);
		panel_3_1_1.add(label_3);
		
		discoPrime1_A = new JTextField("23");
		discoPrime1_A.setColumns(10);
		discoPrime1_A.setBounds(100, 47, 66, 21);
		panel_3_1_1.add(discoPrime1_A);
		
		JLabel label_4 = new JLabel("\u7D20\u65702\r\n");
		label_4.setBounds(10, 78, 54, 15);
		panel_3_1_1.add(label_4);
		
		discoPrime2_A = new JTextField("151");
		discoPrime2_A.setColumns(10);
		discoPrime2_A.setBounds(100, 75, 66, 21);
		panel_3_1_1.add(discoPrime2_A);
		
		
		
		JLabel label_5 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_5.setBounds(27, 23, 54, 15);
		panel_3_2_1.add(label_5);
		
		JLabel label_6 = new JLabel("\u7D20\u65701");
		label_6.setBounds(27, 51, 54, 15);
		panel_3_2_1.add(label_6);
		
		JLabel label_7 = new JLabel("\u7D20\u65702\r\n");
		label_7.setBounds(27, 79, 54, 15);
		panel_3_2_1.add(label_7);
		
		discoSlotLength_B = new JTextField("320");
		discoSlotLength_B.setColumns(10);
		discoSlotLength_B.setBounds(117, 20, 66, 21);
		
		panel_3_2_1.add(discoSlotLength_B);
		
		discoPrime1_B = new JTextField("37");
		discoPrime1_B.setColumns(10);
		discoPrime1_B.setBounds(117, 48, 66, 21);
		panel_3_2_1.add(discoPrime1_B);
		
		discoPrime2_B = new JTextField("43");
		discoPrime2_B.setColumns(10);
		discoPrime2_B.setBounds(117, 76, 66, 21);
		panel_3_2_1.add(discoPrime2_B);
		
		
	}
	private void updateToUConnect(){
		
		JLabel lblNewLabel_3 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		lblNewLabel_3.setBounds(38, 31, 54, 15);
		panel_3_1_1.add(lblNewLabel_3);
		
		UConnectSlotLength_A = new JTextField("320");
		UConnectSlotLength_A.setBounds(114, 28, 66, 21);
		panel_3_1_1.add(UConnectSlotLength_A);
		UConnectSlotLength_A.setColumns(10);
		
		UConnectPrime_A = new JTextField();
		UConnectPrime_A.setColumns(10);
		UConnectPrime_A.setBounds(114, 70, 66, 21);
		panel_3_1_1.add(UConnectPrime_A);
		
		JLabel label_3 = new JLabel("\u7D20\u6570");
		label_3.setBounds(38, 73, 54, 15);
		panel_3_1_1.add(label_3);
		
		
		JLabel label_4 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_4.setBounds(32, 30, 54, 15);
		panel_3_2_1.add(label_4);
		
		JLabel label_5 = new JLabel("\u7D20\u6570");
		label_5.setBounds(32, 72, 54, 15);
		panel_3_2_1.add(label_5);
		
		UConnectSlotLength_B = new JTextField("320");
		UConnectSlotLength_B.setColumns(10);
		UConnectSlotLength_B.setBounds(108, 27, 66, 21);
		panel_3_2_1.add(UConnectSlotLength_B);
		
		UConnectPrime_B = new JTextField();
		UConnectPrime_B.setColumns(10);
		UConnectPrime_B.setBounds(108, 69, 66, 21);
		panel_3_2_1.add(UConnectPrime_B);

	}											
	private void updateToSearchLight_S(){
		
		JLabel label_3 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(34, 28, 54, 15);
		panel_3_1_1.add(label_3);
		
		Searchlight_S_SlotLength_A = new JTextField();
		Searchlight_S_SlotLength_A.setBounds(104, 25, 66, 21);
		panel_3_1_1.add(Searchlight_S_SlotLength_A);
		Searchlight_S_SlotLength_A.setColumns(10);
		
		JLabel lblT = new JLabel("t");
		lblT.setHorizontalAlignment(SwingConstants.CENTER);
		lblT.setBounds(34, 63, 54, 15);
		panel_3_1_1.add(lblT);
		
		Searchlight_S_t_A = new JTextField();
		Searchlight_S_t_A.setColumns(10);
		Searchlight_S_t_A.setBounds(104, 60, 66, 21);
		panel_3_1_1.add(Searchlight_S_t_A);
		
		JLabel label_4 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(31, 26, 54, 15);
		panel_3_2_1.add(label_4);
		
		Searchlight_S_SlotLength_B = new JTextField();
		Searchlight_S_SlotLength_B.setColumns(10);
		Searchlight_S_SlotLength_B.setBounds(101, 23, 66, 21);
		panel_3_2_1.add(Searchlight_S_SlotLength_B);
		
		Searchlight_S_t_B = new JTextField();
		Searchlight_S_t_B.setColumns(10);
		Searchlight_S_t_B.setBounds(101, 58, 66, 21);
		panel_3_2_1.add(Searchlight_S_t_B);
		
		JLabel label_5 = new JLabel("t");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(31, 61, 54, 15);
		panel_3_2_1.add(label_5);
	}
	private void updateSearchlight_Stripe_S(){
		JLabel label_3 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(20, 17, 72, 15);
		panel_3_1_1.add(label_3);
		
		JLabel label_4 = new JLabel("\u65F6\u9699\u6269\u5C55\u957F\u5EA6");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(10, 52, 95, 15);
		panel_3_1_1.add(label_4);
		
		JLabel lblT_1 = new JLabel("t");
		lblT_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblT_1.setBounds(10, 87, 72, 15);
		panel_3_1_1.add(lblT_1);
		
		SearchlightStripe_S_SlotLength_A = new JTextField();
		SearchlightStripe_S_SlotLength_A.setBounds(107, 14, 66, 21);
		panel_3_1_1.add(SearchlightStripe_S_SlotLength_A);
		SearchlightStripe_S_SlotLength_A.setColumns(10);
		
		SearchlightStripe_S_SlotExtendLength_A = new JTextField();
		SearchlightStripe_S_SlotExtendLength_A.setColumns(10);
		SearchlightStripe_S_SlotExtendLength_A.setBounds(107, 49, 66, 21);
		panel_3_1_1.add(SearchlightStripe_S_SlotExtendLength_A);
		
		SearchlightStripe_S_T_A = new JTextField();
		SearchlightStripe_S_T_A.setColumns(10);
		SearchlightStripe_S_T_A.setBounds(107, 84, 66, 21);
		panel_3_1_1.add(SearchlightStripe_S_T_A);
		

		
		JLabel label_5 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(20, 13, 72, 15);
		panel_3_2_1.add(label_5);
		
		JLabel label_6 = new JLabel("\u65F6\u9699\u6269\u5C55\u957F\u5EA6");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(10, 48, 94, 15);
		panel_3_2_1.add(label_6);
		
		JLabel label_7 = new JLabel("t");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(10, 83, 72, 15);
		panel_3_2_1.add(label_7);
		
		SearchlightStripe_S_SlotLength_B = new JTextField();
		SearchlightStripe_S_SlotLength_B.setColumns(10);
		SearchlightStripe_S_SlotLength_B.setBounds(107, 10, 66, 21);
		panel_3_2_1.add(SearchlightStripe_S_SlotLength_B);
		
		SearchlightStripe_S_SlotExtendLength_B = new JTextField();
		SearchlightStripe_S_SlotExtendLength_B.setColumns(10);
		SearchlightStripe_S_SlotExtendLength_B.setBounds(107, 45, 66, 21);
		panel_3_2_1.add(SearchlightStripe_S_SlotExtendLength_B);
		
		SearchlightStripe_S_T_B = new JTextField();
		SearchlightStripe_S_T_B.setColumns(10);
		SearchlightStripe_S_T_B.setBounds(107, 80, 66, 21);
		panel_3_2_1.add(SearchlightStripe_S_T_B);
	}
	private void updateToHello(){
		JLabel label_3 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(28, 10, 54, 15);
		panel_3_1_1.add(label_3);
		
		JLabel lblC = new JLabel("c");
		lblC.setHorizontalAlignment(SwingConstants.CENTER);
		lblC.setBounds(28, 35, 54, 15);
		panel_3_1_1.add(lblC);
		
		JLabel lblN = new JLabel("n");
		lblN.setHorizontalAlignment(SwingConstants.CENTER);
		lblN.setBounds(28, 70, 54, 15);
		panel_3_1_1.add(lblN);
		
		helloSlotLength_A = new JTextField("320");
		helloSlotLength_A.setHorizontalAlignment(SwingConstants.LEFT);
		helloSlotLength_A.setBounds(105, 7, 66, 21);
		panel_3_1_1.add(helloSlotLength_A);
		helloSlotLength_A.setColumns(10);
		
		hello_N_A = new JTextField();
		hello_N_A.setHorizontalAlignment(SwingConstants.LEFT);
		hello_N_A.setColumns(10);
		hello_N_A.setBounds(105, 67, 66, 21);
		panel_3_1_1.add(hello_N_A);
		
		hello_C_A = new JTextField();
		hello_C_A.setHorizontalAlignment(SwingConstants.LEFT);
		hello_C_A.setColumns(10);
		hello_C_A.setBounds(105, 36, 66, 21);
		panel_3_1_1.add(hello_C_A);
		
		JLabel label_4 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(30, 13, 54, 15);
		panel_3_2_1.add(label_4);
		
		JLabel label_5 = new JLabel("c");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(30, 38, 54, 15);
		panel_3_2_1.add(label_5);
		
		JLabel label_6 = new JLabel("n");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(30, 73, 54, 15);
		panel_3_2_1.add(label_6);
		
		helloSlotLength_B = new JTextField("320");
		helloSlotLength_B.setHorizontalAlignment(SwingConstants.LEFT);
		helloSlotLength_B.setColumns(10);
		helloSlotLength_B.setBounds(107, 10, 66, 21);
		panel_3_2_1.add(helloSlotLength_B);
		
		hello_C_B = new JTextField();
		hello_C_B.setHorizontalAlignment(SwingConstants.LEFT);
		hello_C_B.setColumns(10);
		hello_C_B.setBounds(107, 39, 66, 21);
		panel_3_2_1.add(hello_C_B);
		
		hello_N_B = new JTextField();
		hello_N_B.setHorizontalAlignment(SwingConstants.LEFT);
		hello_N_B.setColumns(10);
		hello_N_B.setBounds(107, 70, 66, 21);
		panel_3_2_1.add(hello_N_B);
		
	}
	private void updateToHello_S(){
		
		JLabel label_4 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(19, 38, 54, 15);
		panel_3_1_1.add(label_4);
		
		JLabel lblC_1 = new JLabel("c\r\n");
		lblC_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblC_1.setBounds(19, 63, 54, 15);
		panel_3_1_1.add(lblC_1);
		
		JLabel lblN_1 = new JLabel("n");
		lblN_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblN_1.setBounds(19, 88, 54, 15);
		panel_3_1_1.add(lblN_1);
		
		JLabel label_3 = new JLabel("\u65F6\u9699\u6269\u5C55\u957F\u5EA6");
		label_3.setBounds(10, 13, 89, 15);
		panel_3_1_1.add(label_3);
		
		hello_S_SlotExtendLen_A = new JTextField();
		hello_S_SlotExtendLen_A.setBounds(109, 10, 66, 21);
		panel_3_1_1.add(hello_S_SlotExtendLen_A);
		hello_S_SlotExtendLen_A.setColumns(10);
		
		hello_S_SlotLen_A = new JTextField();
		hello_S_SlotLen_A.setColumns(10);
		hello_S_SlotLen_A.setBounds(109, 35, 66, 21);
		panel_3_1_1.add(hello_S_SlotLen_A);
		
		hello_S_C_A = new JTextField();
		hello_S_C_A.setColumns(10);
		hello_S_C_A.setBounds(109, 60, 66, 21);
		panel_3_1_1.add(hello_S_C_A);
		
		hello_S_N_A = new JTextField();
		hello_S_N_A.setColumns(10);
		hello_S_N_A.setBounds(109, 85, 66, 21);
		panel_3_1_1.add(hello_S_N_A);
		
		

		
		JLabel label_5 = new JLabel("\u65F6\u9699\u6269\u5C55\u957F\u5EA6");
		label_5.setBounds(20, 13, 89, 15);
		panel_3_2_1.add(label_5);
		
		JLabel label_6 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(29, 38, 54, 15);
		panel_3_2_1.add(label_6);
		
		JLabel label_7 = new JLabel("c\r\n");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(29, 63, 54, 15);
		panel_3_2_1.add(label_7);
		
		JLabel label_8 = new JLabel("n");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setBounds(29, 88, 54, 15);
		panel_3_2_1.add(label_8);
		
		hello_S_SlotExtendLen_B = new JTextField();
		hello_S_SlotExtendLen_B.setColumns(10);
		hello_S_SlotExtendLen_B.setBounds(119, 10, 66, 21);
		panel_3_2_1.add(hello_S_SlotExtendLen_B);
		
		hello_S_SlotLen_B = new JTextField();
		hello_S_SlotLen_B.setColumns(10);
		hello_S_SlotLen_B.setBounds(119, 35, 66, 21);
		panel_3_2_1.add(hello_S_SlotLen_B);
		
		hello_S_C_B = new JTextField();
		hello_S_C_B.setColumns(10);
		hello_S_C_B.setBounds(119, 60, 66, 21);
		panel_3_2_1.add(hello_S_C_B);
		
		hello_S_N_B = new JTextField();
		hello_S_N_B.setColumns(10);
		hello_S_N_B.setBounds(119, 85, 66, 21);
		panel_3_2_1.add(hello_S_N_B);	
	}
	private void updateToHello_SR(){
		
		JLabel label_4 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(19, 38, 54, 15);
		panel_3_1_1.add(label_4);
		
		JLabel lblC_1 = new JLabel("c\r\n");
		lblC_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblC_1.setBounds(19, 63, 54, 15);
		panel_3_1_1.add(lblC_1);
		
		JLabel lblN_1 = new JLabel("n");
		lblN_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblN_1.setBounds(19, 88, 54, 15);
		panel_3_1_1.add(lblN_1);
		
		JLabel label_3 = new JLabel("\u65F6\u9699\u6269\u5C55\u957F\u5EA6");
		label_3.setBounds(10, 13, 89, 15);
		panel_3_1_1.add(label_3);
		
		hello_S_SlotExtendLen_A = new JTextField();
		hello_S_SlotExtendLen_A.setBounds(109, 10, 66, 21);
		panel_3_1_1.add(hello_S_SlotExtendLen_A);
		hello_S_SlotExtendLen_A.setColumns(10);
		
		hello_S_SlotLen_A = new JTextField();
		hello_S_SlotLen_A.setColumns(10);
		hello_S_SlotLen_A.setBounds(109, 35, 66, 21);
		panel_3_1_1.add(hello_S_SlotLen_A);
		
		hello_S_C_A = new JTextField();
		hello_S_C_A.setColumns(10);
		hello_S_C_A.setBounds(109, 60, 66, 21);
		panel_3_1_1.add(hello_S_C_A);
		
		hello_S_N_A = new JTextField();
		hello_S_N_A.setColumns(10);
		hello_S_N_A.setBounds(109, 85, 66, 21);
		panel_3_1_1.add(hello_S_N_A);
		
		

		
		JLabel label_5 = new JLabel("\u65F6\u9699\u6269\u5C55\u957F\u5EA6");
		label_5.setBounds(20, 13, 89, 15);
		panel_3_2_1.add(label_5);
		
		JLabel label_6 = new JLabel("\u65F6\u9699\u957F\u5EA6");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(29, 38, 54, 15);
		panel_3_2_1.add(label_6);
		
		JLabel label_7 = new JLabel("c\r\n");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(29, 63, 54, 15);
		panel_3_2_1.add(label_7);
		
		JLabel label_8 = new JLabel("n");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setBounds(29, 88, 54, 15);
		panel_3_2_1.add(label_8);
		
		hello_S_SlotExtendLen_B = new JTextField();
		hello_S_SlotExtendLen_B.setColumns(10);
		hello_S_SlotExtendLen_B.setBounds(119, 10, 66, 21);
		panel_3_2_1.add(hello_S_SlotExtendLen_B);
		
		hello_S_SlotLen_B = new JTextField();
		hello_S_SlotLen_B.setColumns(10);
		hello_S_SlotLen_B.setBounds(119, 35, 66, 21);
		panel_3_2_1.add(hello_S_SlotLen_B);
		
		hello_S_C_B = new JTextField();
		hello_S_C_B.setColumns(10);
		hello_S_C_B.setBounds(119, 60, 66, 21);
		panel_3_2_1.add(hello_S_C_B);
		
		hello_S_N_B = new JTextField();
		hello_S_N_B.setColumns(10);
		hello_S_N_B.setBounds(119, 85, 66, 21);
		panel_3_2_1.add(hello_S_N_B);	
	}
	private void updateToBmp(){
		JLabel label_3 = new JLabel("\u5468\u671F\u957F\u5EA6");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(23, 24, 54, 15);
		panel_3_1_1.add(label_3);
		
		JLabel label_4 = new JLabel("\u4FA6\u542C\u65F6\u957F");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(23, 70, 54, 15);
		panel_3_1_1.add(label_4);
		
		bmpPeriodLen_A = new JTextField();
		bmpPeriodLen_A.setBounds(111, 21, 66, 21);
		panel_3_1_1.add(bmpPeriodLen_A);
		bmpPeriodLen_A.setColumns(10);
		
		bmpListenTimeLen_A = new JTextField();
		bmpListenTimeLen_A.setColumns(10);
		bmpListenTimeLen_A.setBounds(111, 67, 66, 21);
		panel_3_1_1.add(bmpListenTimeLen_A);
		

		
		JLabel label_5 = new JLabel("\u4FA6\u542C\u65F6\u957F");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(22, 69, 54, 15);
		panel_3_2_1.add(label_5);
		
		JLabel label_6 = new JLabel("\u5468\u671F\u957F\u5EA6");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(22, 23, 54, 15);
		panel_3_2_1.add(label_6);
		
		bmpPeriodLen_B = new JTextField();
		bmpPeriodLen_B.setColumns(10);
		bmpPeriodLen_B.setBounds(110, 20, 66, 21);
		panel_3_2_1.add(bmpPeriodLen_B);
		
		bmpListenTimeLen_B = new JTextField();
		bmpListenTimeLen_B.setColumns(10);
		bmpListenTimeLen_B.setBounds(110, 66, 66, 21);
		panel_3_2_1.add(bmpListenTimeLen_B);
		
	}
	
	public NeigDiscoUI(Map<Integer, Mote2> moteMap) throws HeadlessException {
		super();
		this.moteMap = moteMap;
		Iterator<Integer> it = moteMap.keySet().iterator();
		moteA_ID = it.next();														//记录节点的ID号,默认两个节点
		moteB_ID = it.next();
	}

	/**/
	public NeigDiscoUI(){
		super();
		FrameInit();
	}
	
	
	/**
	 * Create the frame.
	 */
	public void FrameInit() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 10, 414, 34);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u9009\u62E9\u534F\u8BAE");
		lblNewLabel.setBounds(46, 8, 109, 15);
		panel_1.add(lblNewLabel);
		
		selectProtocol = new JComboBox();
		
		selectProtocol.addItemListener(new ItemListener() {									//选择协议下拉列表响应函数
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					int selectIndex = selectProtocol.getSelectedIndex();
					panel_3_1_1.removeAll();
					panel_3_2_1.removeAll();
					
					switch(selectIndex){
						case 0:
							updateToDisco();

							break;
						case 1:
							updateToUConnect();
							break;
						case 2:
							updateToSearchLight_S();
							break;
						case 3:
							updateSearchlight_Stripe_S();
							break;
						case 4:
							updateToHello();
							break;
						case 5:
							updateToHello_S();
							break;
						case 6:
							updateToHello_SR();
							break;
						case 7:
							updateToBmp();
							break;
						default:
							break;
							
					}
					panel_3_1_1.updateUI();
					panel_3_2_1.updateUI();
						
				}
			}


		});

		selectProtocol.setBounds(160, 5, 146, 21);
		selectProtocol.setModel(new DefaultComboBoxModel(new String[] {"Disco", "U-Connect", "Searchlight-S", "Searchlight-Stripe-S", "Hello", "Hello-S", "Hello-SR", "Bmp"}));
		selectProtocol.setMaximumRowCount(18);
		panel_1.add(selectProtocol);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u516C\u5171\u53C2\u6570", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 54, 414, 249);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
	
		
		/**	信道标签 */
		JLabel lblNewLabel_1 = new JLabel("\u4FE1\u9053");						
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(41, 38, 54, 15);
		panel_2.add(lblNewLabel_1);
		
		/**	信道JTextField */
		channel = new JTextField("25");
		channel.setBounds(116, 35, 66, 21);
		panel_2.add(channel);
		channel.setColumns(10);
		
		/** 功率标签*/
		JLabel label_1 = new JLabel("\u529F\u7387");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(41, 66, 54, 15);
		panel_2.add(label_1);
		
		/**	功率JTextField */
		power = new JTextField("31");
		power.setColumns(10);
		power.setBounds(116, 63, 66, 21);
		panel_2.add(power);
		
		/** 重复次数标签 */
		JLabel label = new JLabel("\u91CD\u590D\u6B21\u6570");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(41, 94, 54, 15);
		panel_2.add(label);
		
		/** 重复次数JTextField */
		repeatNumber = new JTextField("22");
		repeatNumber.setColumns(10);
		repeatNumber.setBounds(116, 91, 66, 21);
		panel_2.add(repeatNumber);
		
		/** 节点号标签 */
		JLabel label_2 = new JLabel("\u8282\u70B9\u53F7");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(242, 18, 54, 15);
		panel_2.add(label_2);
		
		JLabel moteALabel = new JLabel(moteA_ID+"");									//显示节点号，这里写的很一般，默认两个节点
		moteALabel.setHorizontalAlignment(SwingConstants.CENTER);
		moteALabel.setBounds(242, 45, 54, 15);
		panel_2.add(moteALabel);
		
		JLabel moteBLabel = new JLabel(moteB_ID+"");
		moteBLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moteBLabel.setBounds(242, 75, 54, 15);
		panel_2.add(moteBLabel);
		
		/**统计次数标签*/
		JLabel label_3 = new JLabel("统计次数");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(242, 105, 54, 15);
		panel_2.add(label_3);
		
		/**文件行数标签*/
		JLabel label_4 = new JLabel("文件行数");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(242, 135, 54, 15);
		panel_2.add(label_4);
		
		
		/**启动时间标签*/
		JLabel lbll = new JLabel("启动时间");
		lbll.setHorizontalAlignment(SwingConstants.CENTER);
		lbll.setBounds(317, 18, 66, 15);
		panel_2.add(lbll);
		
		bootTimeA = new JTextField("22");												//bootTimeA是和moteALabel对应的
		bootTimeA.setColumns(10);
		bootTimeA.setBounds(317, 42, 66, 21);
		panel_2.add(bootTimeA);
		
		bootTimeB = new JTextField("220");												//bootTimeB是和moteBLabel对应的
		bootTimeB.setColumns(10);
		bootTimeB.setBounds(317, 71, 66, 21);
		panel_2.add(bootTimeB);

		
		/**统计次数JTextField*/
		countNumber = new JTextField("22");
		countNumber.setColumns(10);
		countNumber.setBounds(317, 102, 66, 21);
		panel_2.add(countNumber);
		
		
		/**文件行号JTextField*/
		fileLine = new JTextField("2");
		fileLine.setColumns(10);
		fileLine.setBounds(317, 132, 66, 21);
		panel_2.add(fileLine);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Beacon\u53D1\u9001\u6A21\u5F0F", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_2_1.setBounds(10, 158, 384, 81);
		panel_2.add(panel_2_1);
		panel_2_1.setLayout(null);
		
		slotHeaderAndTail = new JRadioButton("\u65F6\u9699\u9996\u3001\u5C3E\u53D1");
		slotHeaderAndTail.setBounds(73, 17, 121, 23);
		slotHeaderAndTail.setSelected(true);   							
		panel_2_1.add(slotHeaderAndTail);
		
		slotStart= new JRadioButton("\u65F6\u9699\u5F00\u59CB\u53D1");
		slotStart.setBounds(73, 52, 121, 23);
		panel_2_1.add(slotStart);
		
		slotMiddle = new JRadioButton("\u65F6\u9699\u4E2D\u95F4\u53D1");
		slotMiddle.setBounds(214, 17, 121, 23);
		panel_2_1.add(slotMiddle);
		
		slotEnd = new JRadioButton("\u65F6\u9699\u7ED3\u5C3E\u53D1");
		slotEnd.setBounds(214, 52, 121, 23);
		panel_2_1.add(slotEnd);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(slotHeaderAndTail);
		bg.add(slotStart);
		bg.add(slotMiddle);
		bg.add(slotEnd);
		
		CCA = new JComboBox();
		CCA.setModel(new DefaultComboBoxModel(new String[] {"yes", "no"}));
		CCA.setToolTipText("");
		CCA.setBounds(116, 122, 66, 21);
		panel_2.add(CCA);
		
		JLabel lblcca = new JLabel("使用CCA");
		lblcca.setHorizontalAlignment(SwingConstants.CENTER);
		lblcca.setBounds(41, 125, 65, 15);
		panel_2.add(lblcca);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 307, 414, 148);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		panel_3_1 = new JPanel();						
		panel_3_1.setBounds(0, 0, 206, 148);
		panel_3.add(panel_3_1);
		panel_3_1.setLayout(null);
		
		panel_3_2 = new JPanel();
		panel_3_2.setLayout(null);
		panel_3_2.setBounds(208, 0, 206, 148);
		panel_3.add(panel_3_2);

		
		lblA = new JLabel(moteA_ID+"");							
		lblA.setBounds(71, 10, 54, 15);
		panel_3_1.add(lblA);
		
		
		JLabel lblB = new JLabel(moteB_ID+"");
		lblB.setBounds(71, 10, 54, 15);
		panel_3_2.add(lblB);

				
		panel_3_1_1 = new JPanel();						//这个面板中的组件用来设置moteA的参数				
		panel_3_1_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3_1_1.setBounds(0, 28, 206, 120);
		panel_3_1.add(panel_3_1_1);
		panel_3_1_1.setLayout(null);
		
		panel_3_2_1 = new JPanel();						//这个面板中的组件用来设置moteB的参数
		panel_3_2_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3_2_1.setBounds(0, 27, 206, 121);
		panel_3_2.add(panel_3_2_1);
		panel_3_2_1.setLayout(null);
		
			
		updateToDisco();								//默认显示Disco
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(10, 465, 414, 34);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		JButton btnNewButton = new JButton("\u53D6\u6D88");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(59, 10, 93, 23);
		panel_4.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u786E\u5B9A\r\n");
		
		
		btnNewButton_1.addActionListener(new ActionListener() {							//"确定"按钮响应函数
			public void actionPerformed(ActionEvent e) {

				
				int ndpType = selectProtocol.getSelectedIndex();					//所选协议的索引,从0开始
				
				String channelStr = channel.getText();														
				String powerStr = power.getText();	
				if((channelStr == null) || (Integer.parseInt(channelStr)<11) || (Integer.parseInt(channelStr)>26)){
					JOptionPane.showMessageDialog(null, "信道信息有误", "信道信息有误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if((powerStr == null) || (Integer.parseInt(powerStr)<3) || (Integer.parseInt(powerStr)>31)){
					JOptionPane.showMessageDialog(null, "功率信息有误", "功率信息有误", JOptionPane.ERROR_MESSAGE);
					return;
				}	
				
				
				String repeatNumberStr = repeatNumber.getText();		//重复次数					
				
				String bootTime_A = bootTimeA.getText();				//两个节点的启动时间				
				String bootTime_B = bootTimeB.getText();
				
				String countNum = countNumber.getText();				//节点统计次数
				
				String fileLineNum = fileLine.getText();				//文件行数
				
				byte sendMode = 4;										//标记发送模式   0:时隙首尾，1：时隙开始，2：时隙中间,3：时隙结尾										
				if(slotHeaderAndTail.isSelected()){
					sendMode = 0;
				}else if(slotStart.isSelected()){
					sendMode = 1;
				}else if(slotMiddle.isSelected()){
					sendMode = 2;
				}else if(slotEnd.isSelected()){
					sendMode = 3;
				}
				int cca = 1-CCA.getSelectedIndex();					//所选协议的索引,从0开始

				Mote2 moteA = moteMap.get(moteA_ID);						//分别得到A,B节点
				Mote2 moteB = moteMap.get(moteB_ID);
				
				//填充节点公共参数
				if((channelStr!=null) && (channelStr!="")){					//如果你设了值就设置成你的值，否则就是0
					moteA.setChannel(Integer.parseInt(channelStr));
					moteB.setChannel(Integer.parseInt(channelStr));
				}
				if((powerStr!=null) && (powerStr!="")){
					moteA.setpower(Integer.parseInt(powerStr));
					moteB.setpower(Integer.parseInt(powerStr));
				}
				
				if((bootTime_A!=null) && (bootTime_A!="")){
					moteA.setBootTime(Integer.parseInt(bootTime_A));
					
				}
				if((bootTime_B!=null) && (bootTime_B!="")){
					moteB.setBootTime(Integer.parseInt(bootTime_B));
				}
				if((repeatNumberStr!=null) && (repeatNumberStr!="")){
					Mote2.setRepeatCount(Integer.parseInt(repeatNumberStr));	//设置重复次数			
				}
				if((countNum!=null) && (countNum!="")){
					moteA.setCountNum(Integer.parseInt(countNum));	//设置重复次数,默认两个节点一致	
					moteB.setCountNum(Integer.parseInt(countNum));	
				}
				
				//已经已经已经已经已经System.out.println("biaozhi:"+fileLineNum);
				if((fileLineNum!=null) && (fileLineNum!="")){
					moteA.setFileLineNum(Long.parseLong(fileLineNum));
					moteB.setFileLineNum(Long.parseLong(fileLineNum));	
				}
				
				moteA.setRestart(false);
				moteB.setRestart(false);
				
				//发送给A,B节点的信息
				String msgStrToA = ndpType+"," +channelStr+"," + powerStr + "," + repeatNumberStr + "," + cca + "," +  sendMode + "," + countNum + "," + bootTime_A;	
				String msgStrToB = ndpType+"," +channelStr+"," + powerStr + "," + repeatNumberStr + "," + cca + "," +  sendMode + "," + countNum + "," + bootTime_B;

				switch(ndpType){
					case 0:				//说明是Disco协议
						int slotLen_A = Integer.parseInt(discoSlotLength_A.getText());
						int prime1_A = Integer.parseInt(discoPrime1_A.getText());
						int prime2_A = Integer.parseInt(discoPrime2_A.getText());
						int slotLen_B = Integer.parseInt(discoSlotLength_B.getText());
						int prime1_B = Integer.parseInt(discoPrime1_B.getText());
						int prime2_B = Integer.parseInt(discoPrime2_B.getText());
						moteA.setNdpProtocol(new Disco(Byte.parseByte(0+""), sendMode, slotLen_A, prime1_A, prime2_A));
						moteB.setNdpProtocol(new Disco(Byte.parseByte(0+""), sendMode, slotLen_B, prime1_B, prime2_B));
						msgStrToA = msgStrToA+ ","+slotLen_A +","+prime1_A +","+prime2_A;
						msgStrToB = msgStrToB+ ","+slotLen_B +","+prime1_B +","+prime2_B;
						break;
					case 1:
						slotLen_A = Integer.parseInt(UConnectSlotLength_A.getText());
						int prime_A = Integer.parseInt(UConnectPrime_A.getText());	
						slotLen_B = Integer.parseInt(UConnectSlotLength_B.getText());
						int prime_B = Integer.parseInt(UConnectPrime_B.getText());
						moteA.setNdpProtocol(new UConnect(Byte.parseByte("1"), sendMode, slotLen_A, prime_A));
						moteB.setNdpProtocol(new UConnect(Byte.parseByte("1"), sendMode, slotLen_B, prime_B));
						
						msgStrToA = msgStrToA+ ","+slotLen_A +","+prime_A ;
						msgStrToB = msgStrToB+ ","+slotLen_B +","+prime_B ;
						break;
					case 2:
						slotLen_A = Integer.parseInt(Searchlight_S_SlotLength_A.getText());
						int t_A = Integer.parseInt(Searchlight_S_t_A.getText());			
						slotLen_B = Integer.parseInt(Searchlight_S_SlotLength_B.getText());
						int t_B = Integer.parseInt(Searchlight_S_t_B.getText());	
						moteA.setNdpProtocol(new Searchlight_S(Byte.parseByte("2"), sendMode, slotLen_A, t_A));
						moteB.setNdpProtocol(new Searchlight_S(Byte.parseByte("2"), sendMode, slotLen_B, t_B));
						msgStrToA = msgStrToA+ ","+slotLen_A +","+t_A ;
						msgStrToB = msgStrToB+ ","+slotLen_B +","+t_B ;
						
						break;
					case 3:
						int slotExtensionLen_A = Integer.parseInt(SearchlightStripe_S_SlotExtendLength_A.getText());
						slotLen_A = Integer.parseInt(SearchlightStripe_S_SlotLength_A.getText());
						t_A = Integer.parseInt(SearchlightStripe_S_T_A.getText());
						int slotExtensionLen_B = Integer.parseInt(SearchlightStripe_S_SlotExtendLength_B.getText());
						slotLen_B = Integer.parseInt(SearchlightStripe_S_SlotLength_B.getText());
						t_B = Integer.parseInt(SearchlightStripe_S_T_B.getText());
						moteA.setNdpProtocol(new Searchlight_Stripe_S(Byte.parseByte("3"), sendMode, slotLen_A, slotExtensionLen_A, t_A));
						moteB.setNdpProtocol(new Searchlight_Stripe_S(Byte.parseByte("3"), sendMode, slotLen_B, slotExtensionLen_B, t_B));
						
						msgStrToA = msgStrToA+ ","+slotLen_A +","+slotExtensionLen_A +","+t_A ;
						msgStrToB = msgStrToB+ ","+slotLen_B +","+slotExtensionLen_B +","+t_B ;
						
						break;
					case 4:
						slotLen_A = Integer.parseInt(helloSlotLength_A.getText());
						int c_A =  Integer.parseInt(hello_C_A.getText());
						int n_A = Integer.parseInt(hello_N_A.getText());
						slotLen_B = Integer.parseInt(helloSlotLength_B.getText());
						int c_B =  Integer.parseInt(hello_C_B.getText());
						int n_B = Integer.parseInt(hello_N_B.getText());						
						moteA.setNdpProtocol(new Hello(Byte.parseByte("4"), sendMode, slotLen_A, c_A, n_A));
						moteB.setNdpProtocol(new Hello(Byte.parseByte("4"), sendMode, slotLen_B, c_B, n_B));
						
						msgStrToA = msgStrToA+ ","+slotLen_A +","+c_A +","+n_A ;
						msgStrToB = msgStrToB+ ","+slotLen_B +","+c_B +","+n_B ;
						
						break;
					case 5:
						slotExtensionLen_A = Integer.parseInt(hello_S_SlotExtendLen_A.getText());
						slotLen_A = Integer.parseInt(hello_S_SlotLen_A.getText());
						c_A =  Integer.parseInt(hello_S_C_A.getText());
						n_A = Integer.parseInt(hello_S_N_A.getText());
						slotExtensionLen_B = Integer.parseInt(hello_S_SlotExtendLen_B.getText());
						slotLen_B = Integer.parseInt(hello_S_SlotLen_B.getText());
						c_B =  Integer.parseInt(hello_S_C_B.getText());
						n_B = Integer.parseInt(hello_S_N_B.getText());
						moteA.setNdpProtocol(new Hello_S(Byte.parseByte("5"), sendMode, slotLen_A,slotExtensionLen_A,c_A,n_A));
						moteB.setNdpProtocol(new Hello_S(Byte.parseByte("5"), sendMode, slotLen_B,slotExtensionLen_B,c_B,n_B));
						msgStrToA = msgStrToA+ ","+slotLen_A  +","+slotExtensionLen_A +","+c_A +","+n_A ;
						msgStrToB = msgStrToB+ ","+slotLen_B  +","+slotExtensionLen_B +","+c_B +","+n_B ;
						
						break;
					case 6:
						slotExtensionLen_A = Integer.parseInt(hello_S_SlotExtendLen_A.getText());
						slotLen_A = Integer.parseInt(hello_S_SlotLen_A.getText());
						c_A =  Integer.parseInt(hello_S_C_A.getText());
						n_A = Integer.parseInt(hello_S_N_A.getText());
						slotExtensionLen_B = Integer.parseInt(hello_S_SlotExtendLen_B.getText());
						slotLen_B = Integer.parseInt(hello_S_SlotLen_B.getText());
						c_B =  Integer.parseInt(hello_S_C_B.getText());
						n_B = Integer.parseInt(hello_S_N_B.getText());
						moteA.setNdpProtocol(new Hello_SR(Byte.parseByte("6"), sendMode, slotLen_A,slotExtensionLen_A,c_A,n_A));
						moteB.setNdpProtocol(new Hello_SR(Byte.parseByte("6"), sendMode, slotLen_B,slotExtensionLen_B,c_B,n_B));
						msgStrToA = msgStrToA+ ","+slotLen_A  +","+slotExtensionLen_A +","+c_A +","+n_A ;
						msgStrToB = msgStrToB+ ","+slotLen_B  +","+slotExtensionLen_B +","+c_B +","+n_B ;
						
						break;
					case 7:
						int periodLen_A = Integer.parseInt(bmpPeriodLen_A.getText());
						int listenTimeLen_A = Integer.parseInt(bmpListenTimeLen_A.getText());
						int periodLen_B = Integer.parseInt(bmpPeriodLen_B.getText());
						int listenTimeLen_B = Integer.parseInt(bmpListenTimeLen_B.getText());
						moteA.setNdpProtocol(new Bmp(Byte.parseByte("7"), sendMode, periodLen_A, listenTimeLen_A));
						moteB.setNdpProtocol(new Bmp(Byte.parseByte("7"), sendMode, periodLen_B, listenTimeLen_B));
						msgStrToA = msgStrToA+ ","+periodLen_A  +","+listenTimeLen_A ;
						msgStrToB = msgStrToB+ ","+periodLen_B  +","+listenTimeLen_B ;
						
						break;
				}
				
				NdpSerial payload = new NdpSerial();
				payload.set_type((short)10);
				
				//给A节点发送初始化参数
				payload.setString_msg(msgStrToA);
				
				SerialInteractor interactorA = moteA.getInteractor();
				interactorA.sendPackets(payload);

				//给B节点发送初始化参数
				payload.setString_msg(msgStrToB);
				SerialInteractor interactorB = moteB.getInteractor();
				interactorB.sendPackets(payload);
			}
		});
		btnNewButton_1.setBounds(237, 10, 93, 23);
		panel_4.add(btnNewButton_1);
		
		this.setVisible(true);
		
	}
}
