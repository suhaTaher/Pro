/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hijawicampany;

import java.awt.HeadlessException;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class SearchForSales extends javax.swing.JFrame {

    /**
     * Creates new form SearchForSales
     */
    functions f = new functions();
    public SearchForSales(String UserID) {
        initComponents();
        Image icon;
        icon = new ImageIcon(this.getClass().getResource("/Images/cc.png")).getImage();
        this.setIconImage(icon);
    }
    
    int getSectorno(String Sector){//for dicut and clasheh standered sizes
        int sectorno=0;
         if(Sector.equals("مطاعم")){sectorno=1;}
         else if(Sector.equals("الادوية")){sectorno=2;}
         else if(Sector.equals("بنوك")){sectorno=3;}
         else if(Sector.equals("تعليم")){sectorno=4;}
         else{sectorno=5;}
        return sectorno;
    }
    
    int getSectorno1(String Sector){//for plates standered sizes and dicut clicheh other sizes
        int sectorno=0;
         if(Sector.equals("مطاعم")){sectorno=1;}
         else if(Sector.equals("الادوية")){sectorno=2;}
         else if(Sector.equals("بنوك")){sectorno=1;}
         else if(Sector.equals("تعليم")){sectorno=2;}
         else{sectorno=3;}
        return sectorno;
    }
    
    void SearchForTool(String ToolType,String search){
        Vector data = new Vector();
        this.Colors1.setListData(data);
        String TypeOfTool1="";
        String size1="";
        String JobOfTool1="";
        String supplier1="";
        String status1="";
        String Path="";
        int status;
        int sectorno=0;
        int size=0;
        int flag=0;
        int isle=0;
        int carierNo =0;
        int ordernumber1=0;
        int colornumber1=0;
        int area=0;
        search=this.searchKey1.getText();
        String s =ToolType;
        Connection connection;
        PreparedStatement ps;
        try {
           String sql= String.format("Select * FROM %s WHERE  name=?",ToolType); 
                     
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps = connection.prepareStatement(sql);
                    ps.setString(1,search );
                    ResultSet rs = ps.executeQuery();
                   
                    if(rs.next())
                    {
                        TypeOfTool1=rs.getString(2);
                        JobOfTool1=rs.getString(3);
                        size=rs.getInt(4);
                        area=rs.getInt(5);
                        status=rs.getInt(6);
                        supplier1=rs.getString(10);
                        isle=rs.getInt(11);
                        carierNo =rs.getInt(12);
                        Path=rs.getString(13);
                        if(status==1)status1="متوفر";
                        if(status==0) status1="غير متوفر";
                        if(status==2) status1="تالفة";
                        if(s.equals("dicut")|| s.equals("iclasheh")){
                        switch (size) {  
                            case 1:
                            size1="70×100";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 2:
                            size1="50×30";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 3:
                            size1="غير ذلك";
                            sectorno=getSectorno1(JobOfTool1);
                            break;
                            default:
                            break;
                        }
                        }
                        else if (s.equals("iplate")){
                            switch (size) {  
                            case 1:
                            size1="70×100";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 2:
                            size1="50×30";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                         default:
                            break;
                        
                        }
                        }
                                             try{
                                           
                        Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  Path);
                     }
                     catch(Exception e){JOptionPane.showMessageDialog(this, "Erorr image");} 
                    this.Tool_name1.setText((TypeOfTool1));

                    }

                    else  JOptionPane.showMessageDialog(this,"Not Found5" );
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                } 
        

        
        this.sectorno.setText(Integer.toString(sectorno));
        this.Tool_name1.setText(TypeOfTool1);
        this.Supplier1.setText(supplier1);
        this.Tool_size1.setText(size1);
        this.sector1.setText(JobOfTool1);
        this.Status1.setText(status1);
        this.Area1.setText(Integer.toString(area));
        this.aisle1.setText(Integer.toString(isle));
        this.CarierMo1.setText(Integer.toString(carierNo));
        this.colorNo1.setText(Integer.toString(colornumber1));
 
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        searchKey1 = new javax.swing.JTextField();
        search1 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        Tool_name1 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        Supplier1 = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        Tool_size1 = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        Area1 = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        aisle1 = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        CarierMo1 = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        sector1 = new javax.swing.JTextField();
        sectorno = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        colorNo1 = new javax.swing.JTextField();
        jPanel37 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Colors1 = new javax.swing.JList<>();
        Status1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sales");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setPreferredSize(new java.awt.Dimension(320, 320));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Untitled-5.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(20, 63, 111));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("اسم المستخدم");

        jPanel4.setBackground(new java.awt.Color(20, 63, 111));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("تسجيل الخروج");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 654, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(250, 250, 250));

        jLabel27.setForeground(new java.awt.Color(0, 43, 91));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("اسم الأداة");

        search1.setBackground(new java.awt.Color(0, 43, 91));
        search1.setForeground(new java.awt.Color(51, 51, 51));
        search1.setPreferredSize(new java.awt.Dimension(147, 42));
        search1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search1MouseClicked(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("بحث");

        javax.swing.GroupLayout search1Layout = new javax.swing.GroupLayout(search1);
        search1.setLayout(search1Layout);
        search1Layout.setHorizontalGroup(
            search1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(search1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        search1Layout.setVerticalGroup(
            search1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchKey1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel16Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchKey1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setForeground(new java.awt.Color(0, 43, 91));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("نوع الأداة");
        jPanel17.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Tool_name1.setEditable(false);
        Tool_name1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel17.add(Tool_name1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel45.setForeground(new java.awt.Color(0, 43, 91));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("المورد ");
        jPanel18.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Supplier1.setEditable(false);
        Supplier1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel18.add(Supplier1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setForeground(new java.awt.Color(0, 43, 91));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("الحجم");
        jPanel23.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Tool_size1.setEditable(false);
        Tool_size1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel23.add(Tool_size1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel31.setBackground(new java.awt.Color(250, 250, 250));

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 43, 91));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("موقع التخرين");

        jPanel32.setBackground(new java.awt.Color(250, 250, 250));
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setForeground(new java.awt.Color(0, 43, 91));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("المنطقة");
        jPanel32.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Area1.setEditable(false);
        Area1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.add(Area1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel33.setBackground(new java.awt.Color(250, 250, 250));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel49.setForeground(new java.awt.Color(0, 43, 91));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("الممر");
        jPanel33.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        aisle1.setEditable(false);
        aisle1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.add(aisle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel34.setBackground(new java.awt.Color(250, 250, 250));
        jPanel34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setBackground(new java.awt.Color(250, 250, 250));
        jLabel50.setForeground(new java.awt.Color(0, 43, 91));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("الحاملة");
        jPanel34.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        CarierMo1.setEditable(false);
        CarierMo1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.add(CarierMo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel35.setBackground(new java.awt.Color(250, 250, 250));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setForeground(new java.awt.Color(0, 43, 91));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("القطاع");
        jPanel35.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        sector1.setEditable(false);
        sector1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.add(sector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 42));
        jPanel35.add(sectorno, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 40, 42));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 43, 91));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("الألوان");

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel53.setForeground(new java.awt.Color(0, 43, 91));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("عدد الألوان");
        jPanel36.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 60, 40));

        colorNo1.setEditable(false);
        colorNo1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel36.add(colorNo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel54.setForeground(new java.awt.Color(0, 43, 91));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("الألوان");
        jPanel37.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Colors1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(Colors1);

        jPanel37.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 190, 100));

        Status1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Status1.setForeground(new java.awt.Color(204, 0, 0));
        Status1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Status1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(85, 85, 85))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                    .addGap(17, 17, 17)
                                    .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28))
                                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(Status1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(350, 350, 350)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void search1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search1MouseClicked
        // TODO add your handling code here:
        Vector data = new Vector();
        this.Tool_name1.setText("");
        this.Supplier1.setText("");
        this.Tool_size1.setText("");
        this.sector1.setText("");
        this.Area1.setText("");
        this.aisle1.setText("");
        this.CarierMo1.setText("");
        this.colorNo1.setText("");
        this.Colors1.setListData(data);
       /* String TypeOfTool1="";
        String size1="";
        String JobOfTool1="";
        String supplier1="";
        String status1="";
        int status;
        int sectorno=0;
        int size=0;
        int flag=0;
        int isle=0;
        int carierNo =0;
        int ordernumber1=0;
        int colornumber1=0;
        int area=0;*/
        String search=this.searchKey1.getText();
        if(search.isEmpty()){JOptionPane.showMessageDialog(this,"Empty Search Field" );}
        else{
            char TypeOfTool=search.charAt(0);
            Connection connection;
            PreparedStatement ps,ps1,ps2;

            switch (TypeOfTool) {
                case 'D': case 'd':
                    SearchForTool("dicut", search);
                /*try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps = connection.prepareStatement("select * from dicut where name= ?");
                    ps.setString(1,search );
                    ResultSet rs = ps.executeQuery();
                    if(rs.next())
                    {
                        TypeOfTool1=rs.getString(2);
                        JobOfTool1=rs.getString(3);
                        size=rs.getInt(4);
                        area=rs.getInt(5);
                        status=rs.getInt(6);
                        supplier1=rs.getString(10);
                        isle=rs.getInt(11);
                        carierNo =rs.getInt(12);
                        if(status==1)status1="متوفر";
                        if(status==0) status1="غير متوفر";
                        if(status==2) status1="تالفة";
                        switch (size) {
                            case 1:
                            size1="70×100";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 2:
                            size1="50×30";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 3:
                            size1="غير ذلك";
                            sectorno=getSectorno1(JobOfTool1);
                            break;
                            default:
                            break;
                        }

                    }

                    else  JOptionPane.showMessageDialog(this,"Not Found" );
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                }  */     break;
                case 'P': case 'p':
                    SearchForTool("iplate", search);
                /*try{
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps1 = connection.prepareStatement("select * from iplate where name= ?");
                    ps1.setString(1,search );
                    ResultSet rs1 = ps1.executeQuery();
                    if(rs1.next())
                    {
                        TypeOfTool1=rs1.getString(2);
                        JobOfTool1=rs1.getString(3);
                        size=rs1.getInt(4);
                        area=rs1.getInt(5);
                        status=rs1.getInt(6);
                        isle=rs1.getInt(9);
                        carierNo =rs1.getInt(10);
                        colornumber1=rs1.getInt(11);
                        ps2 = connection.prepareStatement("select * from color where platename= ?");
                        ps2.setString(1,search );
                        ResultSet rs2 = ps2.executeQuery();
                        while(rs2.next()){data.addElement(rs2.getString(2));}
                        this.Colors1.setListData(data);
                        if(status==1)status1="متوفر";
                        if(status==0) status1="غير متوفر";
                        if(status==2) status1="تالفة";
                        switch (size) {
                            case 1:
                            size1="70×100";
                            sectorno=getSectorno1(JobOfTool1);
                            break;
                            case 2:
                            size1="50×30";
                            sectorno=getSectorno1(JobOfTool1);
                            break;
                            default:
                            break;
                        }
                    }
                    else  JOptionPane.showMessageDialog(this,"Not Found" );
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                }  */     break;
                case 'C': case 'c':
                    SearchForTool("iclasheh", search);
                /*try{
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps1 = connection.prepareStatement("select * from iclasheh where name= ?");
                    ps1.setString(1,search );
                    ResultSet rs1 = ps1.executeQuery();
                    if(rs1.next())
                    {
                        TypeOfTool1=rs1.getString(2);
                        JobOfTool1=rs1.getString(3);
                        size=rs1.getInt(4);
                        status=rs1.getInt(6);
                        area=rs1.getInt(5);
                        isle=rs1.getInt(9);
                        carierNo=rs1.getInt(10);
                        colornumber1=rs1.getInt(11);
                        if(status==1)status1="متوفر";
                        if(status==0) status1="غير متوفر";
                        if(status==2) status1="تالفة";
                        switch (size) {
                            case 1:
                            size1="70×100";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 2:
                            size1="50×30";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 3:
                            size1="غير ذلك";
                            sectorno=getSectorno1(JobOfTool1);
                            break;
                            default:
                            break;
                        }

                    }
                    else  JOptionPane.showMessageDialog(this,"Not Found" );
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                } */      break;
                default:
                JOptionPane.showMessageDialog(this,"Not Found" );
                break;
            }
        }

       /* this.sectorno.setText(Integer.toString(sectorno));
        this.Tool_name1.setText(TypeOfTool1);
        this.Supplier1.setText(supplier1);
        this.Tool_size1.setText(size1);
        this.sector1.setText(JobOfTool1);
        this.Status1.setText(status1);
        this.Area1.setText(Integer.toString(area));
        this.aisle1.setText(Integer.toString(isle));
        this.CarierMo1.setText(Integer.toString(carierNo));
        this.colorNo1.setText(Integer.toString(colornumber1));*/
    }//GEN-LAST:event_search1MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
        LogIn login=new LogIn ();
        this.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_jPanel4MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SearchForSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchForSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchForSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchForSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchForSales("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Area1;
    private javax.swing.JTextField CarierMo1;
    private javax.swing.JList<String> Colors1;
    private javax.swing.JLabel Status1;
    private javax.swing.JTextField Supplier1;
    private javax.swing.JTextField Tool_name1;
    private javax.swing.JTextField Tool_size1;
    private javax.swing.JTextField aisle1;
    private javax.swing.JTextField colorNo1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel search1;
    private javax.swing.JTextField searchKey1;
    private javax.swing.JTextField sector1;
    private javax.swing.JTextField sectorno;
    // End of variables declaration//GEN-END:variables
}
