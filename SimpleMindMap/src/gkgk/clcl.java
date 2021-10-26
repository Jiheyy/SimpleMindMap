package gkgk;

import java.awt.*;
import javax.swing.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.lang.Object;
import org.json.*;
import java.util.LinkedList;

import java.util.List;
//import gk.Frame.Component.Node;

class Frame extends JFrame {
			
	private Container contentPane; //��üȭ��
	private JPanel textEditor; //�ؽ�Ʈ����������
	private JPanel mindMap;	//���ε�� ����
	private JPanel attribute; //�Ӽ� ����
	private JPanel mindMap_;
	JTextArea te;
	Component component;
	private TextField tf;
	
	private Node rootNode;
	private Node[] child1Node=new Node[4];
	private Node[] child2Node=new Node[12];
	private LabelMove lb;
	
	private int preR;
	private int preG;
	private int preB;
	
	private Node save;
	
	/*������*/
	public Frame(){
		setTitle("�� ����.json");
		setSize(1000,1000); //ȭ�������
		contentPane = getContentPane();
		MenuBar();
		makeToolBar();
		dividePanels();
		mindMapPane();
		textEditorPane();
		tf=new TextField();
		tf.resetTextField();
		mindMapPane();
		setVisible(true);
	}
	
	/*textEditorPane, MindMapPane,AttributePane*/
	public void dividePanels() {
		textEditor = new JPanel();
		textEditor.setLayout(new BorderLayout());
        mindMap = new JPanel();
        mindMap.setLayout(new BorderLayout());
        attribute = new JPanel();
        attribute.setLayout(new BorderLayout());
      
		JSplitPane sp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textEditor, mindMap); //�ؽ�Ʈ����, ���ε������
	    JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, attribute); //�ؽ�Ʈ����, ���ε������, �Ӽ�����   
	    sp1.setResizeWeight(0.33);
	    sp2.setResizeWeight(0.8);
        contentPane.add(sp2);
	 }
	
	/*�ؽ�Ʈ����*/
	public void textEditorPane() {
		textEditor.add(new JLabel("Text Editor Pane"), BorderLayout.NORTH);
		te = new JTextArea();
		JScrollPane sp = new JScrollPane(te);
		textEditor.add(sp, BorderLayout.CENTER);
		JButton bt = new JButton("����");
		
		textEditor.add(bt, BorderLayout.SOUTH);
		component =new Component();
		bt.addActionListener(component);
	}

	/*���ε������*/
	public void mindMapPane() {
		mindMap.add(new JLabel("Mind Map Pane"), BorderLayout.NORTH);
		mindMap_ = new JPanel();
		mindMap.add(new JScrollPane(mindMap_), BorderLayout.CENTER);
		mindMap_.setLayout(null);
	}
		
	/*�Ӽ�����*/
	class TextField {
		private JTextField tfText;
		private JTextField tfX;
		private JTextField tfY;
		private JTextField tfW;
		private JTextField tfH;
		private JTextField tfColor;
	
		public TextField() {
			attribute.add(new JLabel("AttriBute Pane"), BorderLayout.NORTH);
			JPanel newPanel = new JPanel();
			GridLayout grid = new GridLayout(6,2);
			newPanel.setLayout(grid);
			attribute.add(newPanel, BorderLayout.CENTER);
			JButton bt = new JButton("����");
			attribute.add(bt, BorderLayout.SOUTH);
			bt.addActionListener(new ChangeActionListener());
			
			grid.setVgap(30);

			newPanel.add(new JLabel(" TEXT:"));
			tfText=new JTextField("");
			newPanel.add(tfText);
			newPanel.add(new JLabel(" X:"));
			tfX = new JTextField("");
			newPanel.add(tfX);
			newPanel.add(new JLabel(" Y:"));
			tfY = new JTextField("");
			newPanel.add(tfY);
			newPanel.add(new JLabel(" W:"));
			tfW = new JTextField("");
			newPanel.add(tfW);
			newPanel.add(new JLabel(" H:"));
			tfH = new JTextField("");
			newPanel.add(tfH);
			newPanel.add(new JLabel(" Color:"));
			tfColor = new JTextField("");
			newPanel.add(tfColor);
			
			tfColor.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					
					Color color=JColorChooser.showDialog(null, "Color", Color.YELLOW);
					if(color != null)
					{
						save.setColor(color.getRed(), color.getGreen(), color.getBlue());
						tf.setColorTextField(String.format("%02x%02x%02x",  color.getRed(),  color.getGreen(),color.getBlue()));
					}
				}
			});
		}
		public void resetTextField() {
			this.tfText.setText("");
			this.tfX.setText("");
			this.tfY.setText("");
			this.tfW.setText("");
			this.tfH.setText("");
			tfColor.setText("");
		}
		public void setTextField(String text, String x, String y, String w, String h, String color) {
			this.tfText.setText(String.valueOf(text));
			this.tfText.setEditable(false);
			this.tfX.setText(x);
			this.tfY.setText(y);
			this.tfW.setText(w);
			this.tfH.setText(h);
			this.tfColor.setText(color);
		}
		public void setColorTextField(String color) {
			this.tfColor.setText(color);
		}
		public void setXY(String x, String y) {
			this.tfX.setText(x);
			this.tfY.setText(y);
		}
		public String getText() { return tfText.getText(); }
		public String getX() { return tfX.getText(); }
		public String getY() { return tfY.getText(); }
		public String getW() { return tfW.getText(); }
		public String getH() { return tfH.getText(); }
		public String getColor() { return tfColor.getText(); }
	}
	
	class ChangeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {	
			preR=save.getRed();
			preG=save.getGreen();
			preB=save.getBlue();
			
			LabelMove la =new LabelMove(save);	
			la.change(tf.getX(), tf.getY(), tf.getW(), tf.getH());
			save.la.setBackground(new Color(save.getRed(),save.getGreen(), save.getBlue()));
			
		}
	}
	
	/*�޴���*/
	public void MenuBar() {
		JMenuBar mb = new JMenuBar();
		
		mb.setBackground(Color.LIGHT_GRAY); //������
		createMenu(mb);
	}
	
	/*Screen �޴� ����*/
	public void createMenu(JMenuBar mb) {
		JMenuItem[] menuItem = new JMenuItem[7];
		String[] itemTitle = {"���θ����", "����", "����", "�ٸ� �̸����� ����", "�ݱ�", "����", "����"};
		JMenu screenMenu = new JMenu("File");
		
		//4���� �޴��������� Screen �޴��� �����Ѵ�.
		MenuActionListener listener = new MenuActionListener();//Action ������ ���� ->�޴��������� �̺�Ʈ ó��
		for(int i =0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			if(i==6) {
				menuItem[i].addActionListener(new ChangeActionListener());
				screenMenu.add(menuItem[i]);
				break;
			}
			menuItem[i].addActionListener(listener);
			screenMenu.add(menuItem[i]);
		}
		mb.add(screenMenu);
		setJMenuBar(mb);
	}
	class MenuActionListener implements ActionListener  {
		private JFileChooser fileChooser = new JFileChooser();                                                            
		private JFrame frm = new JFrame();
		
		private boolean fileSaved = false;//������ ����Ǿ��ִ��� ����
		private File file;	
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			switch(cmd) {
				case "���θ����" : {
					te.setText(null);
					mindMap_.removeAll();
					tf.resetTextField();
					repaint();
					
							
					break;
				}
				case "����" : {
					JSONParser parser = new JSONParser();	//�ؽ�Ʈ������ string�� JsonŸ������ �ٲ��ִ� ��ü
				       int returnVal = fileChooser.showOpenDialog(frm);
						
			            if( returnVal == JFileChooser.APPROVE_OPTION)
			            {
			                //���� ��ư�� ������
			            	file = fileChooser.getSelectedFile();
			               this.fileSaved = true;
			                te.setText(null);
			                mindMap_.removeAll();
			                tf.resetTextField();
			                repaint();
			                setTitle(file.toString());//�ҷ��� �����̸� ���� 
							
								Object obj;
								try {
									obj = parser.parse(new FileReader(file.toString()));
									component.getComponent_byParser(obj);
								} catch (IOException | ParseException e1) {
									e1.printStackTrace();
								}
							
						}
								
					break;
				}			
				case "����" : {
					if(this.fileSaved == false) {		//������ ����� ���°� �ƴҶ� ���� ����
						try	{
							FileWriter fw = new FileWriter("�� ����.json");//�׳� �� ����.json��������
				            	
				            	fw.write(component.saveJson());
				            	fw.flush();				//���Ͽ� ���� ���� ����
				            	fw.close();
				            	
				            }
				            catch(IOException e1){e1.printStackTrace();}
						System.out.println(component.saveJson());
				           
					}
						
					if(this.fileSaved == true){	//������ ����Ǿ��ִ� ���¿��� ���̾�α׸� ������ʰ� �ٷ�����
						FileWriter fw;
						try	{
							file.delete();
							fw = new FileWriter(file.toString());
			            	
			            	fw.write(component.saveJson());
			            	fw.flush();				//���Ͽ� ���� ���� ����
			            	fw.close();
			            	
			            }
			            catch(IOException e1){e1.printStackTrace();}
			            System.out.println(component.saveJson());
					}this.fileSaved = true;
				break;
				}		
				case "�ٸ� �̸����� ����" : {
					int returnVal = fileChooser.showSaveDialog(frm);
			        if( returnVal == JFileChooser.APPROVE_OPTION)
			        {
			        //�����ư�� ������
			        	file = fileChooser.getSelectedFile();
			               
			        	FileWriter fw;
						try {
							file.delete();
							fw = new FileWriter(file.toString());
							fw.write(component.saveJson());
							fw.flush();
							fw.close();
						} catch (IOException e1) {e1.printStackTrace();}
					}
			                 
			        System.out.println(component.saveJson());
			        setTitle(file.toString());//�����̸� ����
				this.fileSaved = true;					
			        break;
				}
				case "�ݱ�" : {
					System.exit(0);
					break;
				}
				case "����" : {
					component.actionPerformed(e);
					break;
				}
			}
		}
	}
	
	/*���� ����*/
	public void makeToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(Color.BLUE); //������
		String[] itemTitle = {"���θ����", "����", "����", "�ٸ� �̸����� ����","�ݱ�","����","����"};		
		
		MenuActionListener listener = new MenuActionListener();
		for(int i=0; i<7; i++) {
			JButton bt = new JButton(itemTitle[i]);
			toolBar.add(bt);
			if(i==6) {
				bt.addActionListener(new ChangeActionListener());
				break;
			}
			bt.addActionListener(listener);
			toolBar.addSeparator(); //�и����� ����
		}	
		contentPane.add(toolBar, BorderLayout.NORTH); //�����̳��� NORTH�� ���� ����
	}
	
	/*��� ����,���ε�ʿ��ø���,�̵�*/
	class Component implements ActionListener { 
		/*���ϰ� �о����->component�� �ֱ�*/
		public void getComponent_byParser(Object obj) {
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject getRoot = (JSONObject) jsonObject.get("root");
			String text_ = (String) getRoot.get("text");
			int x_ = (int)(long) getRoot.get("x");
			int y_ = (int)(long) getRoot.get("y");
			int w_ = (int)(long) getRoot.get("w");
			int h_ = (int)(long) getRoot.get("h");
			int r_ = (int)(long) getRoot.get("r");
			int g_ = (int)(long) getRoot.get("g");
			int b_ = (int)(long) getRoot.get("b");
			String temp = text_;
			int k=0;
			
			System.out.println(text_);	
			rootNode = new Node(text_,x_,y_,w_,h_);
			rootNode.setColor(r_,g_,b_);
			rootNode.la= new JLabel(rootNode.getText());
		    rootNode.la.setOpaque(true);
		    lb = new LabelMove(rootNode);
		    rootNode.la.add(lb);
		    rootNode.la.addMouseListener(lb);
		    rootNode.la.addMouseMotionListener(lb);
		    rootNode.la.setBackground(new Color(rootNode.getRed(),rootNode.getGreen(),rootNode.getBlue()));
		    mindMap_.add(rootNode.la);
		    rootNode.la.setBounds(rootNode.getX(),rootNode.getY(),rootNode.getW(),rootNode.getH());
			
			for(int i=0;(JSONObject)jsonObject.get("child"+i)!=null;i++) {
				JSONObject getChild1 = (JSONObject) jsonObject.get("child"+i);
			
				String text = (String) getChild1.get("text");
				int x = (int)(long) getChild1.get("x");
				int y = (int)(long) getChild1.get("y");
				int w = (int)(long) getChild1.get("w");
				int h = (int)(long) getChild1.get("h");
				int r = (int)(long) getChild1.get("r");
				int g = (int)(long) getChild1.get("g");
				int b = (int)(long) getChild1.get("b");
				temp=temp+"\n\t";
				temp=temp+text;
				
				child1Node[i] = new Node(text,x,y,w,h);
				child1Node[i].setColor(r,g,b);
				child1Node[i].la= new JLabel(child1Node[i].getText());
				child1Node[i].la.setOpaque(true);
			    lb = new LabelMove(child1Node[i]);
			    child1Node[i].la.add(lb);
			    child1Node[i].la.addMouseListener(lb);
			    child1Node[i].la.addMouseMotionListener(lb);
			    child1Node[i].la.setBackground(new Color(child1Node[i].getRed(),child1Node[i].getGreen(),child1Node[i].getBlue()));
			    rootNode.addChild(child1Node[i]);
			    mindMap_.add(child1Node[i].la);
			    child1Node[i].la.setBounds(child1Node[i].getX(),child1Node[i].getY(),child1Node[i].getW(),child1Node[i].getH());
				
			    	for(int j=0;(JSONObject) jsonObject.get(i+"-"+j)!=null;j++) {
					JSONObject getChild2 = (JSONObject) jsonObject.get(i+"-"+j);
					String text_2 = (String) getChild2.get("text");
					int x_2 = (int)(long) getChild2.get("x");
					int y_2 = (int)(long) getChild2.get("y");
					int w_2 = (int)(long)getChild2.get("w");
					int h_2 = (int)(long) getChild2.get("h");
					int r_2= (int)(long) getChild2.get("r");
					int g_2= (int)(long) getChild2.get("g");
					int b_2= (int)(long) getChild2.get("b");
					
					temp=temp+"\n\t\t";
					temp=temp+text_2;
					
					child2Node[k] = new Node(text_2,x_2,y_2,w_2,h_2);
					child2Node[k].setColor(r_2,g_2,b_2);
					child1Node[i].addChild(child2Node[k]);
					child2Node[k].la= new JLabel(child2Node[k].getText());
					child2Node[k].la.setOpaque(true);
				    lb = new LabelMove(child2Node[k]);
				    child2Node[k].la.add(lb);
				    child2Node[k].la.addMouseListener(lb);
				    child2Node[k].la.addMouseMotionListener(lb);
				    child2Node[k].la.setBackground(new Color(child2Node[k].getRed(),child2Node[k].getGreen(),child2Node[k].getBlue()));
				    mindMap_.add(child2Node[k].la);
				    child2Node[k].la.setBounds(child2Node[k].getX(),child2Node[k].getY(),child2Node[k].getW(),child2Node[k].getH());
					k++;
				}
			}
			te.setText(temp);
		}
		
		/*Json �������� �ٲٱ�*/
		public String saveJson() {
			JSONObject obj = new JSONObject();
			JSONObject root = new JSONObject();
				
			root.put("text",rootNode.getText());
			root.put("x", rootNode.getX());
			root.put("y", rootNode.getY());
			root.put("w", rootNode.getW());
			root.put("h", rootNode.getH());
			root.put("r", rootNode.getRed());
			root.put("g", rootNode.getGreen());
			root.put("b", rootNode.getBlue());
				
				
			for(int i = 0; i<rootNode.child.size() ;i++) {
				
				JSONObject obj_1 = new JSONObject();
				obj_1.put("text", rootNode.child.get(i).getText());
				obj_1.put("x", rootNode.child.get(i).getX());
				obj_1.put("y", rootNode.child.get(i).getY());
				obj_1.put("w", rootNode.child.get(i).getW());
				obj_1.put("h", rootNode.child.get(i).getH());
				obj_1.put("r", rootNode.child.get(i).getRed());
				obj_1.put("g", rootNode.child.get(i).getGreen());
				obj_1.put("b", rootNode.child.get(i).getBlue());
				obj.put("child"+i, obj_1);			
			}
			for(int i=0; i<rootNode.child.size();i++) {//rootNode�� child�� ����
			for(int j = 0; j<rootNode.child.get(i).child.size();j++) {//child[i]�� child�� ����
				JSONObject obj_2 = new JSONObject();
				System.out.println(rootNode.child.get(i).child.get(j).getText());
				obj_2.put("text",rootNode.child.get(i).child.get(j).getText());
				obj_2.put("x", rootNode.child.get(i).child.get(j).getX());
				obj_2.put("y", rootNode.child.get(i).child.get(j).getY());
				obj_2.put("w", rootNode.child.get(i).child.get(j).getW());
				obj_2.put("h", rootNode.child.get(i).child.get(j).getH());
				obj_2.put("r", rootNode.child.get(i).child.get(j).getRed());
				obj_2.put("g", rootNode.child.get(i).child.get(j).getGreen());
				obj_2.put("b", rootNode.child.get(i).child.get(j).getBlue());
				obj.put(i+"-"+j, obj_2);
			}
			}
			obj.put("root",root);
		
			return obj.toJSONString();
		}
		
		public void actionPerformed(ActionEvent e) {

			LabelMove lb;
			   
			int count1=0;
			int count2=0;
			
			int count=0;
			int count_=0;
			int count_2=0;
			int count_3=0;
			  
			String quey = te.getText();
			StringTokenizer st = new StringTokenizer(quey, "\r\n");
			   
			if(st.hasMoreTokens()) {
			String text = st.nextToken();
			    
			rootNode = new Node(text,280,280,60,30);//��Ʈ��� ����
			rootNode.setColor(Color.GREEN.getRed(),Color.GREEN.getGreen(), Color.GREEN.getBlue());
			
			rootNode.la= new JLabel(rootNode.getText());
			rootNode.la.setOpaque(true);
			lb = new LabelMove(rootNode);
			rootNode.la.add(lb);
			rootNode.la.addMouseListener(lb);
			rootNode.la.addMouseMotionListener(lb);
			rootNode.la.setBackground(Color.GREEN);
			mindMap_.add(rootNode.la);
			rootNode.la.setBounds(rootNode.getX(),rootNode.getY(),rootNode.getW(),rootNode.getH());
			
				if(st.hasMoreTokens()) {
					text =st.nextToken();
					text=text.trim();
					
					child1Node[count1]=new Node(text,190,190,60,30);
					
					count1++;
				
					while(st.hasMoreTokens()) {
						text = st.nextToken();
					     
						if(text.contains("\t\t") && count==0) {
							
							text = text.trim();
							child2Node[count2]=new Node(text,80,190,60,30);
							 
							count2++;
							count++;
						}
						else if(text.contains("\t\t") && count==1) {
							
							text=text.trim();
							child2Node[count2]=new Node(text,180,100,60,30);
					      
							count2++;
							count++;
						}
						else if(text.contains("\t\t") && count==2) {
							
							text=text.trim();
							child2Node[count2]=new Node(text,140,260,60,30);
					      
							count2++;
							count++;
						}
						else {
							
							text=text.trim();
							child1Node[count1]=new Node(text,380,190,60,30);
					      
							count1++; break;
						}
					}

					while(st.hasMoreTokens()) {
						text = st.nextToken();
					     
						if(text.contains("\t\t") && count_==0) {
							
							text = text.trim();
							child2Node[count2]=new Node(text,340,100,60,30);
					      
							count2++;
							count_++;
						}
						else if(text.contains("\t\t") && count_==1) {
							
						    text=text.trim();
						    child2Node[count2]=new Node(text,440,100,60,30);
					      
						    count2++;
						    count_++;
						}
						else if(text.contains("\t\t") && count_==2) {
							
							text=text.trim();
							child2Node[count2]=new Node(text,500,190,60,30);
					      
							count2++;
							count_++;
						}
						else {
							
							text=text.trim();
							child1Node[count1]=new Node(text,200,370,60,30);
						      
							count1++; break;
						}
					}
					   
					while(st.hasMoreTokens()) {
						text = st.nextToken();
					     
						if(text.contains("\t\t") && count_2==0) {
							
							text = text.trim();
							child2Node[count2]=new Node(text,80,370,60,30);
					      
							count2++;
							count_2++;
						}
						else if(text.contains("\t\t") && count_2==1) {
							
							text=text.trim();
							child2Node[count2]=new Node(text,140,450,60,30);
					      
							count2++;
							count_2++;
						}
						else if(text.contains("\t\t") && count_2==2) {
							
							text=text.trim();
							child2Node[count2]=new Node(text,270,450,60,30);
					      
							count2++;
							count_2++;
					  	  }
						else {
							
							text=text.trim();
							child1Node[count1]=new Node(text,380,370,60,30);
					      
							count1++; break;
						}
					}
				
					while(st.hasMoreTokens()) {
						text = st.nextToken();
					     
						if(text.contains("\t\t") && count_3==0) {
							
						    text = text.trim();
						    child2Node[count2]=new Node(text,420,300,60,30);
						      
						    count2++;
						    count_3++;
						}
						else if(text.contains("\t\t") && count_3==1) {
							text=text.trim();
						    child2Node[count2]=new Node(text,490,370,60,30);
						      
						    count2++;
						    count_3++;
						}
						else {
						    text=text.trim();
						    child2Node[count2]=new Node(text,400,450,60,30);
						      
						    count2++;
						    count_3++;
						}
		
					}
				}

				for(int i=0; i<count1; i++) {
					child1Node[i].setColor(Color.ORANGE.getRed(),Color.ORANGE.getGreen(), Color.ORANGE.getBlue());
					child1Node[i]=rootNode.addChild(child1Node[i]);
					
					child1Node[i].la = new JLabel(child1Node[i].getText());
					child1Node[i].la.setOpaque(true);
					child1Node[i].la.setBackground(Color.ORANGE);
					lb = new LabelMove(child1Node[i]);
					child1Node[i].la.add(lb);
					child1Node[i].la.addMouseListener(lb);
					child1Node[i].la.addMouseMotionListener(lb);
					mindMap_.add(child1Node[i].la);
					child1Node[i].la.setBounds(child1Node[i].getX(),child1Node[i].getY(),child1Node[i].getW(),child1Node[i].getH());
				
				

					if(i==0) {
						for(int k=0; k<count; k++) {
							child2Node[k]=child1Node[0].addChild(child2Node[k]);
							//System.out.println(child2Node[k].getText()+"  1");
							System.out.print(rootNode.child.get(0).child.get(k).getText()+"  ");
						}
					}
					if(i==1) {
						for(int k=0; k<count_; k++) {
							child2Node[k+count]=child1Node[i].addChild(child2Node[k+count]);
							System.out.println(child2Node[k+count].getText()+"  2");
						//	System.out.print(rootNode.child.get(1).child.get(k+count).getText()+"  ");
							
						}
					}
					if(i==2) {
						for(int k=0; k<count_2; k++) {
							child2Node[k+count+count_]=child1Node[i].addChild(child2Node[k+count+count_]);
							System.out.println(child2Node[k+count+count_].getText()+"  3");
						}
					}
					if(i==3){
						for(int k=0; k<count_3; k++) {
							child2Node[k+count+count_+count_2]=child1Node[i].addChild(child2Node[k+count+count_+count_2]);
							System.out.println(child2Node[k+count+count_+count_2].getText()+"  4");
						//	System.out.println("4");
						}
					}
				}

				for(int j=0; j<count2; j++) { 
					child2Node[j].setColor(Color.YELLOW.getRed(),Color.YELLOW.getGreen(), Color.YELLOW.getBlue());
					
					child2Node[j].la = new JLabel(child2Node[j].getText());
					child2Node[j].la.setOpaque(true);
					child2Node[j].la.setBackground(Color.YELLOW);
					lb = new LabelMove(child2Node[j]);
					child2Node[j].la.add(lb);
					child2Node[j].la.addMouseListener(lb);
					child2Node[j].la.addMouseMotionListener(lb);
					mindMap_.add(child2Node[j].la);
					child2Node[j].la.setBounds(child2Node[j].getX(),child2Node[j].getY(),child2Node[j].getW(),child2Node[j].getH());
				}
			}
			//for(int i=0; i< rootNode.child.size();i++)
			//	for(int j=0; j<rootNode.child.get(i).child.size();j++) 
			//		System.out.println(rootNode.child.get(i).child.get(j).getText());
		}
	}
	
	/*�� �巡�׽� �̵��ϴ� Ŭ����*/
	class LabelMove extends JComponent implements MouseListener, MouseMotionListener{
		private Node node_p; //�̵��� ��带 ����Ŵ
		private boolean isDragged = false;
		private String preText="";
		int count0=0;
		public LabelMove(Node node_p) {this.node_p = node_p;}

		public void change(String getX, String getY, String getW, String getH) {
			
			int x=Integer.parseInt(getX);
			int y=Integer.parseInt(getY);
			int w=Integer.parseInt(getW);
			int h=Integer.parseInt(getH);
			
			node_p.la.setBounds(x,y,w,h);
			node_p.setXYWH(x,y,w,h);
		}

		public void mousePressed(MouseEvent e) {//#1���콺������, label�� ���콺 Ŭ�������ǥ���ϱ�
			isDragged = true;//�巡�׽���
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
		public void mouseClicked(MouseEvent e) {
			
			if(preText==node_p.getText()) {
				node_p.la.setBackground(new Color(preR, preG, preB));
				tf.resetTextField();
				preText="";
				count0=0;
			}
			else {
				count0=1;
			preText=node_p.getText();
			preR=node_p.getRed();
			preG=node_p.getGreen();
			preB=node_p.getBlue();
			
			node_p.la.setOpaque(true);
			node_p.la.setBackground(Color.RED);
			String text=node_p.getText();
			String x=Integer.toString(node_p.la.getX());
			String y=Integer.toString(node_p.la.getY());
			String w=Integer.toString(node_p.getW());
			String h=Integer.toString(node_p.getH());
			tf.setTextField(text,x,y,w,h,node_p.getColor());
			save=node_p;
			}
		}
		public void mouseReleased(MouseEvent e) {//#3���콺������
			isDragged = false;//���콺��ư ������Ǹ� �巡�׸�� ����
			node_p.x = e.getX()+node_p.la.getX();//e.getX();//�̵��� ��ġ ����� ���ο� x,y������ ����
			node_p.y = e.getY()+node_p.la.getY();//e.getY();
		}
		public void mouseDragged(MouseEvent e) {//#2���콺�巡��
			if(isDragged) { //�巡�׸���� ��쿡�� �� �̵���Ŵ
					
				node_p.la.setLocation(e.getX()+node_p.la.getX(),e.getY()+node_p.la.getY());
				node_p.setXY(e.getX()+node_p.la.getX(),e.getY()+node_p.la.getY());
				if(count0==1) {
				tf.setXY(Integer.toString(e.getX()+node_p.la.getX()), Integer.toString(e.getY()+node_p.la.getY()));
				}
			}
		}
		public void mouseMoved(MouseEvent e) {}
	}
	
	class Node implements Iterable<Node>{
		private String text;
		private int x,y,w,h;
		private int red,green,blue;
		
		private Node parent;
		private List<Node> child;
		
		JLabel la; //��帶�� �̸� �ޱ�
		
		public Node() {}
		
		public Node(String text, int x,int y, int w, int h){ //int color�� �ٲٱ�
			this.text = text;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			
			this.child=new LinkedList<Node>();
		}
		@Override
		public Iterator<Node> iterator() {
			return null;
		}
		
		public Node addChild(Node child) {
			Node childNode = new Node();
			childNode=child;
			childNode.parent=this;
			this.child.add(childNode);
			return childNode;
		}
		
		public Node(int x,int y) {
			this.x = x;
			this.y = y;
		}
		public void setXY(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public void setXYWH(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
		public void setColor(int r, int g, int b) {
			this.red =r;
			this.green=g;
			this.blue=b;
		}

		public String getText() {return text;} //Json���� �ѱ涧 �ʿ��� �Լ���
		public int getX() {return x;}
		public int getY() {return y;}
		public int getW() {return w;}
		public int getH() {return h;}
		public int getRed() {return red;}
		public int getGreen() {return green;}
		public int getBlue() {return blue; }
		public String getColor() {return String.format("%02x%02x%02x",  red, green, blue);}
	}
}


public class clcl{
	public static void main(String[] args) {
		Frame frame = new Frame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
