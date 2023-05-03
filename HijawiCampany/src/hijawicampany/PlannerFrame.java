/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hijawicampany;

import static hijawicampany.functions.DoesItExist;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */


public class PlannerFrame extends javax.swing.JFrame {
    /**
     * Creates new form PlannerFrame
     */
    functions f = new functions();
    long millis=System.currentTimeMillis();
    java.sql.Date Tdate = new java.sql.Date(millis);
    private final String username;
    private CardLayout cardLayout;
    int CNo;
    int PNo;
    int DNo;
    
    public PlannerFrame(String UserID) {
        initComponents();
        cardLayout = (CardLayout) PlannerCards.getLayout();
        setColor(this.AddEmp);
        cardLayout.show(PlannerCards, "card2");
        
        Image icon;
        icon = new ImageIcon(this.getClass().getResource("/Images/cc.png")).getImage();
        this.setIconImage(icon);
        
        CNo=f.NoOfTool("iclasheh");
        PNo=f.NoOfTool("iplate");
        DNo=f.NoOfTool("dicut");
        this.NoOfClasheh.setText(Integer.toString(CNo));
        this.NoOfDicut.setText(Integer.toString(PNo));
        this.NoOfPlate.setText(Integer.toString(DNo));
        f.ToolTables("iplate",PlateTable);
        f.ToolTables("iclasheh",ClashehTable);
        f.ToolTables("dicut",DicutTable);
        
        this.username=UserID;
        this.jLabel2.setText(username);    
        OrderDate.setDate(Tdate);
        Timer timer = new Timer(0, new ActionListener() {

       @Override
       public void actionPerformed(ActionEvent e) {
          tabelcontent();
       }
       });

      timer.setDelay(10000);
      timer.start();
    }
    
        public void tabelcontent(){
             String[] columnNames = {"رقم الطلبية", "تاريخ الطلبية", "تاريخ التسليم", "اسم الاداة","الملفات"};
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    DefaultTableModel model = new DefaultTableModel();
                     model.setColumnIdentifiers(columnNames);
                    jTable1.setModel(model);
                    jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    jTable1.setFillsViewportHeight(true);
                     jTable1.setRowHeight(40);
                    String id="";
                    Date orderdate;
                     String Finishdate;
                    String toolname;
                    String fileUrl;    
                   PreparedStatement ps1 = connection.prepareStatement("select * from orders ORDER BY orderDate Desc");
                    ResultSet rs1 = ps1.executeQuery();
                    while(rs1.next())
                    {
                        id=rs1.getString(1);
                      orderdate=rs1.getDate(2);
                      Finishdate=rs1.getString(3);
                      toolname=rs1.getString(4);
                      fileUrl=rs1.getString(5);
                       model.addRow(new Object[]{id, orderdate, Finishdate, toolname,fileUrl});
                    }        
        } catch (SQLException ex) {
            Logger.getLogger(PlannerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }
    
    void setColor(JPanel panel) {
       // panel.setBackground(new Color(20, 63, 111));
       panel.setBackground(new Color(0, 43, 91));
    }
    
    void resetColor(JPanel panel) {
       // panel.setBackground(new Color(0,43,91));
        panel.setBackground(new Color(20,63,111));
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
    
    boolean validate(String email){
        boolean x=false;
        //Regular Expression   
        String regex = "^(.+)@(.+)$";  
        //Compile regular expression to get the pattern  
        Pattern pattern = Pattern.compile(regex); 
        Matcher matcher = pattern.matcher(email); 
        x= matcher.matches();
        return x;
    }
    
    public  boolean checkDates(Date d1, String d2)    {
        SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd ");
    boolean b = false;
    try {
        if(d1.before(dfDate.parse(d2)))
        {
            b = true;//If start date is before end date
        }
        else if(d1.equals(dfDate.parse(d2)))
        {
            b = true;//If two dates are equal
        }
        else
        {
            b = false; //If start date is after the end date
        }
    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return b;
}
    
    boolean SearchForTool(String ToolType,String search){
        Vector data = new Vector();
        boolean x=false;
        String TypeOfTool1="";
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
        int area=0;
        String Path="";
        String Sectors="";
        search=this.searchKey1.getText();
       String s =ToolType;
        Connection connection;
        PreparedStatement ps,ps2;
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
                        if(JobOfTool1.equals("Restaurant")){Sectors="مطاعم";}
                        else if(JobOfTool1.equals("Medicine")){Sectors="الادوية";}
                        else if(JobOfTool1.equals("Others")){Sectors="اخرى";}
                        else if(JobOfTool1.equals("Education")){Sectors="تعليم";}
                        else if(JobOfTool1.equals("Banks")){Sectors="بنوك";}
                        size=rs.getInt(4);
                        area=rs.getInt(5);
                        status=rs.getInt(6);
                        Path=rs.getString(13);
                        
                                                
                        if(s.equals("dicut")){
                        supplier1=rs.getString(10);
                        isle=rs.getInt(11);
                        carierNo =rs.getInt(12);
                        }
                        
                        if(s.equals("iplate")|| s.equals("iclasheh")){
                        isle=rs.getInt(10);
                        carierNo =rs.getInt(11);
                        colornumber1=rs.getInt(12);
                        }
                        
                        if(s.equals("iplate")){
                         ps2 = connection.prepareStatement("select * from color where platename= ?");
                         ps2.setString(1,search );
                         ResultSet rs2 = ps2.executeQuery();
                         while(rs2.next()){data.addElement(rs2.getString(2));}
                         this.Colors1.setListData(data);
                        }
                        
                        if(status==1)status1="متوفر";
                        if(status==0) status1="غير متوفر";
                        if(status==2) status1="تالفة";
                        if(s.equals("dicut")|| s.equals("iclasheh")){
                        switch (size) {  
                            case 1:
                            size1="70*100";
                            sectorno=getSectorno(Sectors);
                            break;
                            case 2:
                            size1="50*30";
                            sectorno=getSectorno(Sectors);
                            break;
                            case 3:
                            size1="غير ذلك";
                            sectorno=getSectorno1(Sectors);
                            break;
                            default:
                            break;
                        }
                        }
                        else if (s.equals("iplate")){
                            switch (size) {  
                            case 1:
                            size1="70*100";
                            sectorno=getSectorno1(Sectors);
                            break;
                            case 2:
                            size1="50*30";
                            sectorno=getSectorno1(Sectors);
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
                    x=true;

                    }

                    else  {x=false;}
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                } 
        

        
        this.sectorno.setText(Integer.toString(sectorno));
        this.Tool_name1.setText(TypeOfTool1);
        this.Supplier1.setText(supplier1);
        this.Tool_size1.setText(size1);
        this.sector1.setText(Sectors);
        this.Status1.setText(status1);
        this.Area1.setText(Integer.toString(area));
        this.aisle1.setText(Integer.toString(isle));
        this.CarierMo1.setText(Integer.toString(carierNo));
        this.colorNo1.setText(Integer.toString(colornumber1));
    return x;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        SidePannel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        AddEmp = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tools = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LogOut = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        AddOrder = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        SearchAndDeleteOrder = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        OrderList = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        tools1 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        ToolsList = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        PlannerCards = new javax.swing.JPanel();
        Tools = new javax.swing.JPanel();
        toolsTap = new javax.swing.JTabbedPane();
        clasheh = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        NoOfClasheh = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ClashehTable = new javax.swing.JTable();
        plate = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        NoOfPlate = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        PlateTable = new javax.swing.JTable();
        dicut = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        NoOfDicut = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        DicutTable = new javax.swing.JTable();
        OrderListFrame = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        SearchAndDeleteFrame = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        SearchO = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        TName = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        DateOfOrder = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        DateOfFinish = new javax.swing.JTextField();
        State = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        SearchOrder = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        DeleteO = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        AddOrderFrame = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        FilePath = new javax.swing.JTextField();
        Attach = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        ToolName = new javax.swing.JTextField();
        ToolStatus = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        OrderNo = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        FinishDate = new com.toedter.calendar.JDateChooser();
        jPanel14 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        OrderDate = new com.toedter.calendar.JDateChooser();
        addTheOrder = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        SearchToolFrame = new javax.swing.JPanel();
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
        jLabel55 = new javax.swing.JLabel();
        AddEmpFrame = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        name2 = new javax.swing.JTextField();
        jPanel39 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        name1 = new javax.swing.JTextField();
        jPanel40 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        WorkerPassWord = new javax.swing.JTextField();
        jPanel41 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        workerID = new javax.swing.JTextField();
        jPanel42 = new javax.swing.JPanel();
        workertype = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        empNo2 = new javax.swing.JTextField();
        jPanel45 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        newPassword = new javax.swing.JTextField();
        EditPass = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        AddWorker = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Planning");
        setResizable(false);

        jPanel1.setLayout(null);

        SidePannel.setBackground(new java.awt.Color(20, 63, 111));

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("اسم المستخدم");
        jLabel2.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );

        AddEmp.setBackground(new java.awt.Color(20, 63, 111));
        AddEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddEmpMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("اضافة موظف");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("__________________________");

        javax.swing.GroupLayout AddEmpLayout = new javax.swing.GroupLayout(AddEmp);
        AddEmp.setLayout(AddEmpLayout);
        AddEmpLayout.setHorizontalGroup(
            AddEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddEmpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddEmpLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(21, 21, 21))
        );
        AddEmpLayout.setVerticalGroup(
            AddEmpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddEmpLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tools.setBackground(new java.awt.Color(20, 63, 111));
        tools.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toolsMouseClicked(evt);
            }
        });
        tools.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("البحث عن أداه");
        tools.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 110, 30));

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("__________________________");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("__________________________");

        LogOut.setPreferredSize(new java.awt.Dimension(183, 55));
        LogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogOutMouseClicked(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(204, 0, 0));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("تسجيل الخروج");

        javax.swing.GroupLayout LogOutLayout = new javax.swing.GroupLayout(LogOut);
        LogOut.setLayout(LogOutLayout);
        LogOutLayout.setHorizontalGroup(
            LogOutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LogOutLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addGap(57, 57, 57))
        );
        LogOutLayout.setVerticalGroup(
            LogOutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogOutLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(20, 63, 111));

        AddOrder.setBackground(new java.awt.Color(20, 63, 111));
        AddOrder.setPreferredSize(new java.awt.Dimension(190, 35));
        AddOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddOrderMouseClicked(evt);
            }
        });
        AddOrder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("اضافة طلبية");
        AddOrder.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 90, 35));

        SearchAndDeleteOrder.setBackground(new java.awt.Color(20, 63, 111));
        SearchAndDeleteOrder.setPreferredSize(new java.awt.Dimension(190, 35));
        SearchAndDeleteOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchAndDeleteOrderMouseClicked(evt);
            }
        });
        SearchAndDeleteOrder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("بحث و حذف");
        jLabel1.setPreferredSize(new java.awt.Dimension(70, 35));
        SearchAndDeleteOrder.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 74, 35));

        OrderList.setBackground(new java.awt.Color(20, 63, 111));
        OrderList.setPreferredSize(new java.awt.Dimension(190, 35));
        OrderList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OrderListMouseClicked(evt);
            }
        });
        OrderList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("لائحة الطلبيات");
        jLabel4.setPreferredSize(new java.awt.Dimension(82, 35));
        OrderList.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 0, 95, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("الطلبيات");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(OrderList, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(SearchAndDeleteOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(AddOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(SearchAndDeleteOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OrderList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("الأدوات");

        tools1.setBackground(new java.awt.Color(20, 63, 111));
        tools1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tools1MouseClicked(evt);
            }
        });
        tools1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("البحث عن أداه");
        tools1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 110, 30));

        ToolsList.setBackground(new java.awt.Color(20, 63, 111));
        ToolsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ToolsListMouseClicked(evt);
            }
        });
        ToolsList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("لائحة الأدوات");
        ToolsList.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 110, 30));

        javax.swing.GroupLayout SidePannelLayout = new javax.swing.GroupLayout(SidePannel);
        SidePannel.setLayout(SidePannelLayout);
        SidePannelLayout.setHorizontalGroup(
            SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SidePannelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LogOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(SidePannelLayout.createSequentialGroup()
                .addGroup(SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddEmp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(SidePannelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SidePannelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SidePannelLayout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))))
                    .addComponent(tools, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(SidePannelLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(ToolsList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SidePannelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tools1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        SidePannelLayout.setVerticalGroup(
            SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SidePannelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(AddEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tools, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ToolsList, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(LogOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
            .addGroup(SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SidePannelLayout.createSequentialGroup()
                    .addGap(303, 303, 303)
                    .addComponent(tools1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(412, Short.MAX_VALUE)))
        );

        jPanel1.add(SidePannel);
        SidePannel.setBounds(790, 0, 210, 750);

        PlannerCards.setLayout(new java.awt.CardLayout());

        Tools.setBackground(new java.awt.Color(255, 255, 255));

        clasheh.setBackground(new java.awt.Color(255, 255, 255));

        jPanel22.setBackground(new java.awt.Color(250, 250, 250));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 43, 91));
        jLabel41.setText("عدد الأدوات");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(NoOfClasheh, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(NoOfClasheh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        ClashehTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(ClashehTable);

        javax.swing.GroupLayout clashehLayout = new javax.swing.GroupLayout(clasheh);
        clasheh.setLayout(clashehLayout);
        clashehLayout.setHorizontalGroup(
            clashehLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clashehLayout.createSequentialGroup()
                .addContainerGap(430, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(clashehLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        clashehLayout.setVerticalGroup(
            clashehLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clashehLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );

        toolsTap.addTab("   Clasheh   ", clasheh);

        plate.setBackground(new java.awt.Color(255, 255, 255));

        jPanel24.setBackground(new java.awt.Color(250, 250, 250));

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 43, 91));
        jLabel43.setText("عدد الأدوات");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(NoOfPlate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(NoOfPlate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        PlateTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(PlateTable);

        javax.swing.GroupLayout plateLayout = new javax.swing.GroupLayout(plate);
        plate.setLayout(plateLayout);
        plateLayout.setHorizontalGroup(
            plateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plateLayout.createSequentialGroup()
                .addContainerGap(430, Short.MAX_VALUE)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(plateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        plateLayout.setVerticalGroup(
            plateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plateLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );

        toolsTap.addTab("   Plate   ", plate);

        dicut.setBackground(new java.awt.Color(255, 255, 255));

        jPanel25.setBackground(new java.awt.Color(250, 250, 250));

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 43, 91));
        jLabel60.setText("عدد الأدوات");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(NoOfDicut, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(NoOfDicut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        DicutTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(DicutTable);

        javax.swing.GroupLayout dicutLayout = new javax.swing.GroupLayout(dicut);
        dicut.setLayout(dicutLayout);
        dicutLayout.setHorizontalGroup(
            dicutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dicutLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(dicutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                .addContainerGap())
        );
        dicutLayout.setVerticalGroup(
            dicutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dicutLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );

        toolsTap.addTab("   Dicut   ", dicut);

        javax.swing.GroupLayout ToolsLayout = new javax.swing.GroupLayout(Tools);
        Tools.setLayout(ToolsLayout);
        ToolsLayout.setHorizontalGroup(
            ToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ToolsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(toolsTap, javax.swing.GroupLayout.PREFERRED_SIZE, 741, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        ToolsLayout.setVerticalGroup(
            ToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ToolsLayout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addComponent(toolsTap, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        PlannerCards.add(Tools, "card7");

        OrderListFrame.setBackground(new java.awt.Color(250, 250, 250));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "null", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setEnabled(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 43, 91));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("لائحة الطلبيات");

        javax.swing.GroupLayout OrderListFrameLayout = new javax.swing.GroupLayout(OrderListFrame);
        OrderListFrame.setLayout(OrderListFrameLayout);
        OrderListFrameLayout.setHorizontalGroup(
            OrderListFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OrderListFrameLayout.createSequentialGroup()
                .addContainerGap(582, Short.MAX_VALUE)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
            .addGroup(OrderListFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(OrderListFrameLayout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(68, Short.MAX_VALUE)))
        );
        OrderListFrameLayout.setVerticalGroup(
            OrderListFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrderListFrameLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(675, Short.MAX_VALUE))
            .addGroup(OrderListFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(OrderListFrameLayout.createSequentialGroup()
                    .addGap(103, 103, 103)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(29, Short.MAX_VALUE)))
        );

        PlannerCards.add(OrderListFrame, "card6");

        SearchAndDeleteFrame.setBackground(new java.awt.Color(255, 255, 255));
        SearchAndDeleteFrame.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(246, 42));

        jLabel25.setForeground(new java.awt.Color(0, 43, 91));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("رقم الطلبية");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SearchO, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(SearchO)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(41, 41, 41))
        );

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 43, 91));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("بحث و حذف طلبية");
        jLabel26.setPreferredSize(new java.awt.Dimension(48, 42));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(246, 42));

        jLabel28.setForeground(new java.awt.Color(0, 43, 91));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("الأداة");

        TName.setEditable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(TName)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(41, 41, 41))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(246, 42));

        jLabel29.setForeground(new java.awt.Color(0, 43, 91));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("تاريخ الطلبية");

        DateOfOrder.setEditable(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DateOfOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(DateOfOrder)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(41, 41, 41))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(246, 42));

        jLabel30.setForeground(new java.awt.Color(0, 43, 91));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("تاريخ التسليم");

        DateOfFinish.setEditable(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DateOfFinish, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(DateOfFinish)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(41, 41, 41))
        );

        State.setForeground(new java.awt.Color(204, 0, 0));
        State.setText("Status");

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setPreferredSize(new java.awt.Dimension(420, 365));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Untitled-6.png"))); // NOI18N

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        SearchOrder.setBackground(new java.awt.Color(0, 43, 91));
        SearchOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchOrderMouseClicked(evt);
            }
        });

        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("بحث");

        javax.swing.GroupLayout SearchOrderLayout = new javax.swing.GroupLayout(SearchOrder);
        SearchOrder.setLayout(SearchOrderLayout);
        SearchOrderLayout.setHorizontalGroup(
            SearchOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SearchOrderLayout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        SearchOrderLayout.setVerticalGroup(
            SearchOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        DeleteO.setBackground(new java.awt.Color(255, 255, 255));
        DeleteO.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 43, 91), 1, true));
        DeleteO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DeleteOMouseClicked(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 43, 91));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("حذف");

        javax.swing.GroupLayout DeleteOLayout = new javax.swing.GroupLayout(DeleteO);
        DeleteO.setLayout(DeleteOLayout);
        DeleteOLayout.setHorizontalGroup(
            DeleteOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeleteOLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DeleteOLayout.setVerticalGroup(
            DeleteOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setForeground(new java.awt.Color(0, 43, 91));
        jCheckBox1.setText("تم التسليم");
        jCheckBox1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel44Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(DeleteO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel44Layout.createSequentialGroup()
                                .addComponent(SearchOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                                        .addComponent(State, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(55, 55, 55))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(State, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jCheckBox1)
                        .addGap(18, 18, 18)
                        .addComponent(DeleteO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        SearchAndDeleteFrame.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 730));

        PlannerCards.add(SearchAndDeleteFrame, "card5");

        AddOrderFrame.setBackground(new java.awt.Color(255, 255, 255));
        AddOrderFrame.setPreferredSize(new java.awt.Dimension(790, 750));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(375, 375));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/im12.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        Attach.setBackground(new java.awt.Color(0, 43, 91));
        Attach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AttachMouseClicked(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("ارفاق ملف");

        javax.swing.GroupLayout AttachLayout = new javax.swing.GroupLayout(Attach);
        Attach.setLayout(AttachLayout);
        AttachLayout.setHorizontalGroup(
            AttachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AttachLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(30, 30, 30))
        );
        AttachLayout.setVerticalGroup(
            AttachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Attach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FilePath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Attach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setForeground(new java.awt.Color(0, 43, 91));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("اسم الأداة");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ToolName, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ToolName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        ToolStatus.setForeground(new java.awt.Color(204, 0, 0));
        ToolStatus.setText("status");

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setForeground(new java.awt.Color(0, 43, 91));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("رقم الطلبية");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OrderNo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OrderNo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(ToolStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(ToolStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setPreferredSize(new java.awt.Dimension(252, 42));

        jLabel13.setForeground(new java.awt.Color(0, 43, 91));
        jLabel13.setText("تاريخ التسليم");
        jLabel13.setPreferredSize(new java.awt.Dimension(68, 42));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FinishDate, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FinishDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setPreferredSize(new java.awt.Dimension(252, 42));

        jLabel16.setForeground(new java.awt.Color(0, 43, 91));
        jLabel16.setText("تاريخ الطلبية");
        jLabel16.setPreferredSize(new java.awt.Dimension(68, 42));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        addTheOrder.setBackground(new java.awt.Color(255, 255, 255));
        addTheOrder.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 43, 91), 1, true));
        addTheOrder.setPreferredSize(new java.awt.Dimension(228, 42));
        addTheOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTheOrderMouseClicked(evt);
            }
        });

        jLabel17.setForeground(new java.awt.Color(0, 43, 91));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("اضافة الطلبية");

        javax.swing.GroupLayout addTheOrderLayout = new javax.swing.GroupLayout(addTheOrder);
        addTheOrder.setLayout(addTheOrderLayout);
        addTheOrderLayout.setHorizontalGroup(
            addTheOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addTheOrderLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        addTheOrderLayout.setVerticalGroup(
            addTheOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 43, 91));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("اضافة طلبية جديدة");

        javax.swing.GroupLayout AddOrderFrameLayout = new javax.swing.GroupLayout(AddOrderFrame);
        AddOrderFrame.setLayout(AddOrderFrameLayout);
        AddOrderFrameLayout.setHorizontalGroup(
            AddOrderFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddOrderFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddOrderFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddOrderFrameLayout.createSequentialGroup()
                        .addGap(0, 53, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(AddOrderFrameLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(AddOrderFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddOrderFrameLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(AddOrderFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddOrderFrameLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addTheOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddOrderFrameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        AddOrderFrameLayout.setVerticalGroup(
            AddOrderFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddOrderFrameLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(AddOrderFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddOrderFrameLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddOrderFrameLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)
                        .addComponent(addTheOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        PlannerCards.add(AddOrderFrame, "card4");

        SearchToolFrame.setBackground(new java.awt.Color(255, 255, 255));

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

        sectorno.setEditable(false);
        sectorno.setBackground(new java.awt.Color(255, 255, 255));
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

        jScrollPane3.setViewportView(Colors1);

        jPanel37.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 190, 100));

        Status1.setForeground(new java.awt.Color(204, 0, 0));
        Status1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Status1.setText("Status");

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
                        .addContainerGap(50, Short.MAX_VALUE))))
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

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 43, 91));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("البحث عن أداه");

        javax.swing.GroupLayout SearchToolFrameLayout = new javax.swing.GroupLayout(SearchToolFrame);
        SearchToolFrame.setLayout(SearchToolFrameLayout);
        SearchToolFrameLayout.setHorizontalGroup(
            SearchToolFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SearchToolFrameLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(SearchToolFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SearchToolFrameLayout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addGap(49, 49, 49)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        SearchToolFrameLayout.setVerticalGroup(
            SearchToolFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SearchToolFrameLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        PlannerCards.add(SearchToolFrame, "card3");

        AddEmpFrame.setBackground(new java.awt.Color(255, 255, 255));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setForeground(new java.awt.Color(0, 43, 91));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("الاسم الثلاثي");

        name2.setBackground(new java.awt.Color(250, 250, 250));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(name2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setForeground(new java.awt.Color(0, 43, 91));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("البريد الالكتروني");

        name1.setBackground(new java.awt.Color(250, 250, 250));

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(name1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(name1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setForeground(new java.awt.Color(0, 43, 91));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("كلمة السر");

        WorkerPassWord.setEditable(false);
        WorkerPassWord.setBackground(new java.awt.Color(250, 250, 250));

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(WorkerPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(WorkerPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setForeground(new java.awt.Color(0, 43, 91));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("الرقم التعريفي");

        workerID.setEditable(false);
        workerID.setBackground(new java.awt.Color(250, 250, 250));

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(workerID, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(workerID, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));

        workertype.setBackground(new java.awt.Color(245, 245, 245));
        workertype.setEditable(true);
        workertype.setForeground(new java.awt.Color(0, 43, 91));
        workertype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "قسم التخطيط", "مسؤول المخازن", "مدير المبيعات" }));

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(workertype, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(workertype, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel24.setForeground(new java.awt.Color(0, 43, 91));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("الوظيفة");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24)))
                .addGap(57, 57, 57))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 43, 91));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("اضافة موظف");

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));
        jPanel43.setPreferredSize(new java.awt.Dimension(320, 320));

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 43, 91));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("تغيير كلمة السر");

        empNo2.setBackground(new java.awt.Color(250, 250, 250));

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));

        jLabel59.setForeground(new java.awt.Color(0, 43, 91));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("كلمة السر الجديدة");

        newPassword.setBackground(new java.awt.Color(250, 250, 250));

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addComponent(newPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        EditPass.setBackground(new java.awt.Color(255, 255, 255));
        EditPass.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 43, 91), 1, true));
        EditPass.setPreferredSize(new java.awt.Dimension(273, 42));
        EditPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EditPassMouseClicked(evt);
            }
        });

        jLabel35.setBackground(new java.awt.Color(255, 255, 255));
        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 43, 91));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("تعديل");

        javax.swing.GroupLayout EditPassLayout = new javax.swing.GroupLayout(EditPass);
        EditPass.setLayout(EditPassLayout);
        EditPassLayout.setHorizontalGroup(
            EditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPassLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );
        EditPassLayout.setVerticalGroup(
            EditPassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jLabel36.setForeground(new java.awt.Color(0, 43, 91));
        jLabel36.setText("الرقم التعريفي");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(EditPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel43Layout.createSequentialGroup()
                        .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel43Layout.createSequentialGroup()
                                .addComponent(empNo2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empNo2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(EditPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        AddWorker.setBackground(new java.awt.Color(255, 255, 255));
        AddWorker.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 43, 91)));
        AddWorker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddWorkerMouseClicked(evt);
            }
        });

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 43, 91));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("اضافة ");

        javax.swing.GroupLayout AddWorkerLayout = new javax.swing.GroupLayout(AddWorker);
        AddWorker.setLayout(AddWorkerLayout);
        AddWorkerLayout.setHorizontalGroup(
            AddWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddWorkerLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        AddWorkerLayout.setVerticalGroup(
            AddWorkerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Untitled-4.png"))); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(AddWorker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AddWorker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel57)
                        .addGap(79, 79, 79))))
        );

        javax.swing.GroupLayout AddEmpFrameLayout = new javax.swing.GroupLayout(AddEmpFrame);
        AddEmpFrame.setLayout(AddEmpFrameLayout);
        AddEmpFrameLayout.setHorizontalGroup(
            AddEmpFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddEmpFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AddEmpFrameLayout.setVerticalGroup(
            AddEmpFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddEmpFrameLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PlannerCards.add(AddEmpFrame, "card2");

        jPanel1.add(PlannerCards);
        PlannerCards.setBounds(0, 0, 790, 750);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void AddEmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEmpMouseClicked
        // TODO add your handling code here:
        resetColor(this.AddOrder);
        resetColor(this.tools);
        setColor(this.AddEmp);
        resetColor(this.SearchAndDeleteOrder);
        resetColor(this.OrderList);
        resetColor(this.ToolsList);
        cardLayout.show(PlannerCards, "card2");
    }//GEN-LAST:event_AddEmpMouseClicked

    private void toolsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toolsMouseClicked
        // TODO add your handling code here:
        resetColor(this.AddOrder);
        setColor(this.tools);
        resetColor(this.AddEmp);
        resetColor(this.SearchAndDeleteOrder);
        resetColor(this.OrderList);
        resetColor(this.ToolsList);
        cardLayout.show(PlannerCards, "card3");
    }//GEN-LAST:event_toolsMouseClicked

    private void AddOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddOrderMouseClicked
        // TODO add your handling code here:
        setColor(this.AddOrder);
        resetColor(this.tools);
        resetColor(this.AddEmp);
        resetColor(this.SearchAndDeleteOrder);
        resetColor(this.OrderList);
        resetColor(this.ToolsList);
        cardLayout.show(PlannerCards, "card4");
    }//GEN-LAST:event_AddOrderMouseClicked

    private void LogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOutMouseClicked
        // TODO add your handling code here:
        LogIn login=new LogIn ();
        this.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_LogOutMouseClicked

    private void SearchAndDeleteOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchAndDeleteOrderMouseClicked
        // TODO add your handling code here:
        resetColor(this.AddOrder);
        resetColor(this.tools);
        resetColor(this.AddEmp);
        setColor(this.SearchAndDeleteOrder);
        resetColor(this.OrderList);
        resetColor(this.ToolsList);
        cardLayout.show(PlannerCards, "card5");
    }//GEN-LAST:event_SearchAndDeleteOrderMouseClicked

    private void OrderListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OrderListMouseClicked
        // TODO add your handling code here:
        resetColor(this.AddOrder);
        resetColor(this.tools);
        resetColor(this.AddEmp);
        resetColor(this.SearchAndDeleteOrder);
        setColor(this.OrderList);
        resetColor(this.ToolsList);
        cardLayout.show(PlannerCards, "card6");
    }//GEN-LAST:event_OrderListMouseClicked

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
      /*  String TypeOfTool1="";
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
        boolean x,w;
        if(search.isEmpty()){JOptionPane.showMessageDialog(this,"Empty Search Field" );}
        else{

            char TypeOfTool=search.charAt(0);
            Connection connection;
            PreparedStatement ps,ps1,ps2;

            switch (TypeOfTool) {
                case 'D' : case  'd' :
                   x= SearchForTool("dicut", search);
                   if(!x){
                       w= DoesItExist("dicut",search,"Jname");
                       if(w){
                           Table T = new Table(search,"dicut");
                           T.setVisible(true);
                       }
                       else if(!w){
                          JOptionPane.showMessageDialog(this,"لم يتم العثور على الأداة" );
                       }
                   }
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
                        supplier1=rs.getString(9);
                        isle=rs.getInt(10);
                        carierNo =rs.getInt(11);
                        if(status==1)status1="متوفر";
                        if(status==0) status1="غير متوفر";
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
                case 'P': case  'p':
                    x= SearchForTool("iplate", search);
                   if(!x){
                       w= DoesItExist("iplate",search,"Jname");
                       if(w){
                           Table T = new Table(search,"iplate");
                           T.setVisible(true);
                       }
                       else if(!w){
                          JOptionPane.showMessageDialog(this,"لم يتم العثور على الأداة" );
                       }
                   }
               /* try{
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
                }   */    break;
                case 'C': case  'c':
                    x= SearchForTool("iclasheh", search);
                   if(!x){
                       w= DoesItExist("iclasheh",search,"Jname");
                       if(w){
                           Table T = new Table(search,"iclasheh");
                           T.setVisible(true);
                       }
                       else if(!w){
                          JOptionPane.showMessageDialog(this,"لم يتم العثور على الأداة" );
                       }
                   }
               /* try{
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

    private void AddWorkerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddWorkerMouseClicked
        // TODO add your handling code here:
        String email=this.name1.getText();
        String workername=this.name2.getText();
        if(workername.isEmpty() || email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Empty username or email" );
        }
        else{
            boolean x=validate(email);
            if(!x){JOptionPane.showMessageDialog(this,"ادخل بريد الكتروني صحيح" );}
            else{
            String type1=(String) this.workertype.getSelectedItem();
            String Type2="";
            if(type1.equals("الادارة")){Type2="Management";}
            else if(type1.equals("مسؤول المخازن")){Type2="Storage";}
            else if(type1.equals("مدير المبيعات")){Type2="Sales";}
            else if (type1.equals("قسم التخطيط")){Type2="Planning";}
            Connection connection;
            PreparedStatement ps,p;
            try {

                final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                SecureRandom random = new SecureRandom();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i <6; i++)
                {
                    int randomIndex = random.nextInt(chars.length());
                    sb.append(chars.charAt(randomIndex));
                }
                String password=sb.toString();
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");

                ps = connection.prepareStatement("INSERT INTO user (username,password,eamil,type)VALUES (?,?,?,?)");
                ps.setString(1,workername);
                ps.setString(2,password);
                ps.setString(3,email);
                ps.setString(4,Type2);
                boolean rs = ps.execute();
                if(!rs)
                {
                    p = connection.prepareStatement("select * from user where username = ?");
                    p.setString(1,workername);
                    ResultSet s = p.executeQuery();
                    if(s.next()){
                        int id=s.getInt(5);
                        workerID.setText(Integer.toString(id));
                        WorkerPassWord.setText(password);
                        JOptionPane.showMessageDialog(this, "تم الاضافة بنجاح\n id="+id+"\n password="+password);
                    }
                }
                else{ JOptionPane.showMessageDialog(this, "Erorr");}
            } catch (HeadlessException | SQLException ex ) {JOptionPane.showMessageDialog(this,"Wrong \n"+ex );}
        }
        }
    }//GEN-LAST:event_AddWorkerMouseClicked

    private void SearchOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchOrderMouseClicked
        // TODO add your handling code here:
        if(SearchO.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"what is the user id" );
        }
        else {
            String ONo=
                    SearchO.getText();
           
            Connection connection;
            PreparedStatement ps,p;
            try {
                String state = "";
                String StateText="";
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                 p = connection.prepareStatement("select * from orders where ordernumber = ?");
                 p.setString(1,ONo);
                 ResultSet s = p.executeQuery();
                 if(s.next()){
                     String ODate = s.getString(2);
                     DateOfOrder.setText(ODate);
                     String FDate = s.getString(3);
                     DateOfFinish.setText(FDate);
                     String ToolName = s.getString(4);
                     TName.setText(ToolName);
                     if(s.getInt(6)==1){this.jCheckBox1.setSelected(true);}
                     else if (s.getInt(6)==0){this.jCheckBox1.setSelected(false);}
                     //tool name
                     char schar=ToolName.charAt(0);
                     if(schar=='D'||schar=='d'){state=f.returnvalue("dicut", ToolName, "name");StateText=f.returnTextState(state);}
                     else if(schar=='P'||schar=='p'){state=f.returnvalue("iplate", ToolName, "name");StateText=f.returnTextState(state);}
                     else if(schar=='C'||schar=='c'){state=f.returnvalue("iclasheh", ToolName, "name");StateText=f.returnTextState(state);}
                     State.setText(StateText);
                     try{
                     String FolderPath = s.getString(5);                      
                        Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  FolderPath);
                     }
                     catch(Exception e){JOptionPane.showMessageDialog(this, "Erorr File");}  
                 }
                 else{ JOptionPane.showMessageDialog(this, "This Order Does Not Exist"); } 
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_SearchOrderMouseClicked

    private void AttachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AttachMouseClicked
        // TODO add your handling code here:
        JFileChooser Chooser =new JFileChooser();
        Chooser.showOpenDialog(null);
        File f= Chooser.getSelectedFile();
        String fileName=f.getAbsolutePath();
        FilePath.setText(fileName);
       /* JFileChooser chooser;
        String choosertitle="";
        chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
       chooser.setAcceptAllFileFilterUsed(false);
    //    
      if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      FilePath.setText(chooser.getCurrentDirectory().toString());
      }
      else {
      System.out.println("اختر ملف ");
      }*/
    }//GEN-LAST:event_AttachMouseClicked

    private void addTheOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTheOrderMouseClicked
        // TODO add your handling code here:
        String ToolNam= ToolName.getText();
        String Path = FilePath.getText();
        String Date2="NO DaTE WAS SET" ;
        SimpleDateFormat D = new SimpleDateFormat("yyyy-MM-dd ");
        Date2 = D.format(FinishDate.getDate());
         boolean check=  checkDates(Tdate,Date2);
        boolean xx=false;
        
        if( ToolName.getText().isEmpty()|| FilePath.getText().isEmpty() || OrderNo.getText().isEmpty()   ){
           //|| FinishDate.getDate().toString().isEmpty()
           boolean x =this.FinishDate.getDateFormatString().isEmpty();
           JOptionPane.showMessageDialog(this, "empty fields");   
       }
       else{
            xx = f.DoesItExist("orders",OrderNo.getText(),"ordernumber");
            if(xx){JOptionPane.showMessageDialog(this,"this ordernumber already exists" );}
            else {
                if(check){
                  //  if(f.isNumeric(OrderNo.getText())){
       try { 
        //java.util.Date FD = new java.util.Date("mm/dd/yyy");
        //FDate = (Date) D.parse(D.format(FinishDate.getDate()));
        //  ODate = (Date) D.parse(D.format(OrderDate.getDate()));
        //String Date1 = D.format(OrderDate.getDate());
        //java.sql.Date Date1 = new java.sql.Date(FDate.getTime());
        // java.sql.Date Date2 = new java.sql.Date(ODate.getTime());
        String ordernumber = OrderNo.getText();
        // this.FinishDate.getDateFormatString();
        // SimpleDateFormat D = new SimpleDateFormat();
        //Date2 = D.format(FinishDate.getDate());
        Connection connection;
        PreparedStatement ps = null,p;
        PreparedStatement addToolDate;
          ResultSet s1;
          String stext=ToolName.getText();
          char schar=stext.charAt(0);
          int e = 2;
          String ToolTableName="";          
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
            if(schar=='D'||schar=='d'){
             p = connection.prepareStatement("SELECT status FROM dicut WHERE  name=?");
             p.setString(1,ToolName.getText());
                s1 = p.executeQuery();
                ToolTableName="dicut";
                if(s1.next())e= s1.getInt(1);
                 else   JOptionPane.showMessageDialog(this, "tool not found");
                    }
            else if(schar=='P'||schar=='p'){
             p = connection.prepareStatement("SELECT status FROM iplate WHERE  name=?");
              p.setString(1,ToolName.getText());
                s1 = p.executeQuery();
                ToolTableName="iplate";
                if(s1.next())e= s1.getInt(1);
                 else   JOptionPane.showMessageDialog(this, "tool not found");
                    }
            else if(schar=='C'||schar=='c'){
             p = connection.prepareStatement("SELECT status FROM iclasheh WHERE  name=?");
              p.setString(1,ToolName.getText());
                s1 = p.executeQuery();
                ToolTableName="iclasheh";
                if(s1.next())e= s1.getInt(1);
                 else   JOptionPane.showMessageDialog(this, "tool not found");
                    }
            else   JOptionPane.showMessageDialog(this, "tool not found");;
            if(e==1){
                ToolStatus.setText("متوفرة");
                String sql = String.format("UPDATE %s SET orderDate=? ,DateOfFinishOrder=? WHERE name=? ",ToolTableName); 
                addToolDate=connection.prepareStatement(sql);
                addToolDate.setDate(1, Tdate);
                addToolDate.setString(2,Date2);
                addToolDate.setString(3, ToolName.getText());
                boolean rs1 = addToolDate.execute();

                ps = connection.prepareStatement("INSERT INTO orders (ordernumber,OrderDate,FinishDate,ToolUsedName,fileUrl,OrderStatus)VALUES (?,?,?,?,?,?)");
                ps.setString(1,ordernumber);
                ps.setDate(2,Tdate);
                ps.setString(3,Date2);
                ps.setString(4,ToolNam);
                ps.setString(5,Path);
                ps.setInt(6, 0);// means not ready
                boolean rs = ps.execute();
            if(!rs) {
                JOptionPane.showMessageDialog(this, "تم الاضافة بنجاح");
                ToolName.setText("");
                OrderNo.setText("");
                FilePath.setText("");
                //OrderDate.setDateFormatString("");
                //FinishDate.setDateFormatString("");
            
            }
            else   JOptionPane.showMessageDialog(this, "Erorr");

            }
            
             else  if(e==0){ 
                     
               //  String ODate = s.getString(2);
                    // DateOfOrder.setText(ODate);
                     
                  ResultSet ss1;               
                 ToolStatus.setText("غير متوفرة");
                 String sql1=String.format("SELECT OrderDate,DateOfFinishOrder FROM %s WHERE   name=?", ToolTableName);
                p = connection.prepareStatement(sql1);
                p.setString(1,ToolName.getText());
                ss1 = p.executeQuery();
                if(ss1.next()){
                    Date DDATE= ss1.getDate(1);
                    String dd= DDATE.toString();
                 DateValidate d=new DateValidate(dd,ss1.getString(2)) ; 
                 d.setVisible(true);
                }
                

             }
                            else  if(e==2){
                ToolStatus.setText("تالفة");
                 JOptionPane.showMessageDialog(this, "هذه الأداة تالفة");
                
                }
            connection.close();
        } catch (HeadlessException | SQLException ex ) {
           JOptionPane.showMessageDialog(this,"Wrong \n"+ex.getLocalizedMessage() );
       }
       }
      catch(Exception ex) {
           JOptionPane.showMessageDialog(this,"Wrong \n"+ex.toString() );   
       }
                    //}
                    // else{JOptionPane.showMessageDialog(this, "رقم الطلبية يحب انيكون رقما");}
       }
      else{
                JOptionPane.showMessageDialog(this, "التاريخ غير صحيح");
                }
            }
        }
        
    }//GEN-LAST:event_addTheOrderMouseClicked

    private void DeleteOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteOMouseClicked
        // TODO add your handling code here:
        if(SearchO.getText().isEmpty()){
             JOptionPane.showMessageDialog(this, "empty field");
        }
        else {
            String ONo=SearchO.getText();
            Connection connection;
            PreparedStatement ps,p;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                ps = connection.prepareStatement("select * from orders where ordernumber = ?");
                ps.setString(1,ONo);
                ResultSet sr = ps.executeQuery();
                if(sr.next()){
                      p = connection.prepareStatement("DELETE FROM orders where ordernumber = ?");
                      p.setString(1,ONo);
                      boolean s = p.execute();
                     if(!s){
                    // SearchO.setText("");
                     TName.setText("");
                     DateOfOrder.setText("");
                     DateOfFinish.setText("");
                     State.setText("");
                     JOptionPane.showMessageDialog(this, "تم الحذف بنجاح");
                     }
                     else{ JOptionPane.showMessageDialog(this, "Error"); }
                }
                else{JOptionPane.showMessageDialog(this, "No Such Order In DataBase");}
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_DeleteOMouseClicked

    private void EditPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditPassMouseClicked
        // TODO add your handling code here:
        if ( empNo2.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter employee ID");
        }
        else{
            int workerid=Integer.parseInt(this.empNo2.getText());
            if ( newPassword.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Enter newpassword ");
            }
            else{
                String newpass=this.newPassword.getText();
                Connection connection;
                PreparedStatement ps,p;
                try{
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    p = connection.prepareStatement("select * from user where id = ?");
                    p.setInt(1,workerid);
                    ResultSet s = p.executeQuery();
                    if(s.next()){
                        ps = connection.prepareStatement("UPDATE user SET password = ?  WHERE id = ?");
                        ps.setString(1,newpass);
                        ps.setInt(2,workerid);
                        ps.execute();
                        JOptionPane.showMessageDialog(this, "تمت العملية بنجاح");
                    }
                    else{ JOptionPane.showMessageDialog(this, "No Such User In DataBase");}
                }catch (HeadlessException | SQLException ex ) {JOptionPane.showMessageDialog(this,"Wrong \n"+ex );}
            }
        }
    }//GEN-LAST:event_EditPassMouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int row=jTable1.rowAtPoint(evt.getPoint());
        DefaultTableModel model= (DefaultTableModel)jTable1.getModel();
        String path = model.getValueAt(row, 4).toString();  
        try {
           Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  path);
        } catch (IOException ex) {
            Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void tools1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tools1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tools1MouseClicked

    private void ToolsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ToolsListMouseClicked
        // TODO add your handling code here:
        resetColor(this.AddOrder);
        resetColor(this.tools);
        resetColor(this.AddEmp);
        resetColor(this.SearchAndDeleteOrder);
        resetColor(this.OrderList);
        setColor(this.ToolsList);
        cardLayout.show(PlannerCards, "card7");
    }//GEN-LAST:event_ToolsListMouseClicked

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
            java.util.logging.Logger.getLogger(PlannerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlannerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlannerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlannerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PlannerFrame("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddEmp;
    private javax.swing.JPanel AddEmpFrame;
    private javax.swing.JPanel AddOrder;
    private javax.swing.JPanel AddOrderFrame;
    private javax.swing.JPanel AddWorker;
    private javax.swing.JTextField Area1;
    private javax.swing.JPanel Attach;
    private javax.swing.JTextField CarierMo1;
    private javax.swing.JTable ClashehTable;
    private javax.swing.JList<String> Colors1;
    private javax.swing.JTextField DateOfFinish;
    private javax.swing.JTextField DateOfOrder;
    private javax.swing.JPanel DeleteO;
    private javax.swing.JTable DicutTable;
    private javax.swing.JPanel EditPass;
    private javax.swing.JTextField FilePath;
    private com.toedter.calendar.JDateChooser FinishDate;
    private javax.swing.JPanel LogOut;
    private javax.swing.JLabel NoOfClasheh;
    private javax.swing.JLabel NoOfDicut;
    private javax.swing.JLabel NoOfPlate;
    private com.toedter.calendar.JDateChooser OrderDate;
    private javax.swing.JPanel OrderList;
    private javax.swing.JPanel OrderListFrame;
    private javax.swing.JTextField OrderNo;
    private javax.swing.JPanel PlannerCards;
    private javax.swing.JTable PlateTable;
    private javax.swing.JPanel SearchAndDeleteFrame;
    private javax.swing.JPanel SearchAndDeleteOrder;
    private javax.swing.JTextField SearchO;
    private javax.swing.JPanel SearchOrder;
    private javax.swing.JPanel SearchToolFrame;
    private javax.swing.JPanel SidePannel;
    private javax.swing.JLabel State;
    private javax.swing.JLabel Status1;
    private javax.swing.JTextField Supplier1;
    private javax.swing.JTextField TName;
    private javax.swing.JTextField ToolName;
    private javax.swing.JLabel ToolStatus;
    private javax.swing.JTextField Tool_name1;
    private javax.swing.JTextField Tool_size1;
    private javax.swing.JPanel Tools;
    private javax.swing.JPanel ToolsList;
    private javax.swing.JTextField WorkerPassWord;
    private javax.swing.JPanel addTheOrder;
    private javax.swing.JTextField aisle1;
    private javax.swing.JPanel clasheh;
    private javax.swing.JTextField colorNo1;
    private javax.swing.JPanel dicut;
    private javax.swing.JTextField empNo2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField name1;
    private javax.swing.JTextField name2;
    private javax.swing.JTextField newPassword;
    private javax.swing.JPanel plate;
    private javax.swing.JPanel search1;
    private javax.swing.JTextField searchKey1;
    private javax.swing.JTextField sector1;
    private javax.swing.JTextField sectorno;
    private javax.swing.JPanel tools;
    private javax.swing.JPanel tools1;
    private javax.swing.JTabbedPane toolsTap;
    private javax.swing.JTextField workerID;
    private javax.swing.JComboBox<String> workertype;
    // End of variables declaration//GEN-END:variables
}
