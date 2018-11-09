package sample;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Controller {

    public TextField txtStudentList;
    public Button btnStudentOpen;
    public Button btnRoomOpen;
    public TextField txtRoomList;
    public TextField txtExportLocation;
    public TextArea txtLogs;
    public CheckBox chkMiddleTermTest;
    public Button btnSave;
    public Button btnStart;
    public DatePicker dtpicker;
    public CheckBox chkSkipSunday;
    public ComboBox<String> cbViewType;
    public ComboBox<String> cbViewAs;
    public TableView<ArrayList> tbView;
    public ProgressBar progressBar;
    private ArrayList<ArrayList<String>> roomList;
    private ArrayList<ArrayList<String>> registeredList;
    Scheduling scheduling;
    public void initialize()
    {

        txtLogs.setEditable(false);
        cbViewAs.setEditable(false);
        cbViewType.setEditable(false);
        txtExportLocation.setText(System.getProperty("user.dir"));
        File f1 = new File("KetQuaDangKyMonHoc.xlsx");
        if(f1.exists() && !f1.isDirectory()) {

            txtStudentList.setText(f1.getAbsolutePath());
            txtStudentList.setEditable(false);
            writeLog(f1.getName()+ " added");
        }
        File f2 = new File("DanhSachPhongThi.xlsx");
        if(f2.exists() && !f2.isDirectory()) {
            txtRoomList.setText(f2.getAbsolutePath());
            txtRoomList.setEditable(false);
            writeLog(f2.getName()+" added");

        }

        dtpicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.FRIDAY|| date.getDayOfWeek() == DayOfWeek.SATURDAY||
                        date.getDayOfWeek() == DayOfWeek.SUNDAY|| date.getDayOfWeek() == DayOfWeek.THURSDAY||
                        date.getDayOfWeek() == DayOfWeek.TUESDAY|| date.getDayOfWeek() == DayOfWeek.WEDNESDAY);
            }
        });
        dtpicker.setEditable(false);
    }

    private void writeLog(String logMessage)
    {
        String tempString;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        tempString = (txtLogs.getText()) + (dateFormat.format(date))+ (": ")+ (logMessage)+("\n");
        txtLogs.setText(tempString);
        txtLogs.setScrollTop(txtLogs.getText().length());
    }

    public void HandleDatePickerPicked()
    {
        writeLog("Set "+ dtpicker.getValue()+" as test start date");
    }

    public void HandleChkSkipChanged()
    {
        if(chkSkipSunday.isSelected())
        {
            writeLog("Test Scheduler will skip Sunday");
        }
        else
            writeLog("Test Scheduler will include Sunday");
    }
    public void HandleChkCheckedChanged()
    {
        if(chkMiddleTermTest.isSelected())
        {
            writeLog("Middle test scheduling mode is on");
        }
        else
            writeLog("Middle test scheduling mode is off");
    }
    public void HandleTxtStudentListClick() {

    }

    private String OpenFileDialog(String filename)
    {
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser,filename);
        File result = fileChooser.showOpenDialog(btnRoomOpen.getScene().getWindow());
        if(result!=null)
        {
            writeLog(result.getName() + " added");
            return result.getAbsolutePath();
        }
        return "";
    }
    private static void configureFileChooser(
            final FileChooser fileChooser, String filename) {
        fileChooser.setTitle("Choose File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel File Format:", filename),
                new FileChooser.ExtensionFilter("Excel File Format:", "xlsx")
        );
    }




    public void HandleBtnSaveClick()
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File Selected = directoryChooser.showDialog(new Stage());
        if(Selected!=null)
        {
            txtExportLocation.setText(Selected.getAbsolutePath());
            writeLog("Export Location selected");
        }
    }


    public void HandleBtnRoomClick()
    {
        String txt = OpenFileDialog("DanhSachPhongThi.xlsx");
        if(!txt.equals(""))
        {
            txtRoomList.setText(txt);
        }
    }
    public void HandleBtnClearClick()
    {
        txtLogs.setText("");
    }
    public void HandleBtnStudentOpen()
    {
        String txt = OpenFileDialog("KetQuaDangKyMonHoc.xlsx");
        if(!txt.equals(""))
        {
            txtStudentList.setText(txt);
        }
    }
    public void HandleBtnStartClick()
    {
        if(!checkValid())
            return;
        writeLog("Accessing files");
        try {
            Thread t = new Thread(() -> {
               getContentFormFiles();
               scheduling = new Scheduling(registeredList,roomList,chkMiddleTermTest.isSelected(),
                       dtpicker.getValue().toString(), txtExportLocation.getText());
               ////
                writeLog(scheduling.insertData());
                progressBar.setProgress(0.25);
                writeLog(scheduling.buildGraph());
                progressBar.setProgress(0.50);
                writeLog(scheduling.assignRoom());
                progressBar.setProgress(0.75);
                writeLog(setView());
                progressBar.setProgress(1.00);
            });
            t.start();
        }
        catch(Exception e)
        {
            //
            e.printStackTrace();
        }
    }

    private String setView() {
        //cbViewType.setEditable(true);
        cbViewType.getItems().add(0, "Môn học");
        cbViewType.getItems().add(1, "Sinh viên");
        cbViewType.getItems().add(2, "Phòng thi");
//        cbViewType.getItems().add(3, "Giờ thi");
        cbViewType.getItems().add(3, "Ngày thi");
        return "Assign successfully, you can view the result in pane below now";
    }

 public void handleViewTypeChosen()
 {
     cbViewAs.setEditable(true);

     if(cbViewType.getValue().equals("Môn học"))
     {
         cbViewAs.getItems().clear();
        for(String str: scheduling.getSubjectsUnion())
        {
            cbViewAs.getItems().add(str);
        }
     }
     if(cbViewType.getValue().equals("Sinh viên"))
     {
         cbViewAs.getItems().clear();
        for(String sv:scheduling.getStudentsUnion())
            cbViewAs.getItems().add(sv);
     }
     if(cbViewType.getValue().equals("Phòng thi"))
     {
         cbViewAs.getItems().clear();
         for(ClassRooms pt:scheduling.getListRooms())
             cbViewAs.getItems().add(pt.getName());
     }

     if(cbViewType.getValue().equals("Ngày thi")) {
         cbViewAs.getItems().clear();
         for(TestNode ng: scheduling.getFinalResult()){
             if(!cbViewAs.getItems().contains(ng.getDate() + " " + ng.getTime()))
             {
                 cbViewAs.getItems().add(ng.getDate() + " " + ng.getTime());
             }
         }
     }
 }

   public void handleViewAsChosen()
   {
       if(cbViewType.getValue().equals("Môn học"))
       {
           tbView.getColumns().clear();
          TableColumn<ArrayList,String> mssv = new TableColumn<>("Student Code");
           mssv.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(0).toString()));
          TableColumn<ArrayList,String> lastName = new TableColumn<>("Last Name");
           lastName.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(1).toString()));
          TableColumn<ArrayList,String> firstName = new TableColumn<>("First Name");
           firstName.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(2).toString()));
          TableColumn<ArrayList,String> ngayThi = new TableColumn<>("Date");
           ngayThi.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(3).toString()));
          TableColumn<ArrayList,String> gioThi = new TableColumn<>("Time");
           gioThi.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(4).toString()));
          TableColumn<ArrayList,String> phongThi = new TableColumn<>("Room");
           phongThi.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(5).toString()));
            tbView.getColumns().addAll(mssv,lastName,firstName,ngayThi,gioThi,phongThi);
           mssv.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           lastName.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           firstName.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           ngayThi.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           gioThi.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           phongThi.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           ObservableList<ArrayList> data=  FXCollections.observableArrayList();
          for(TestNode tn: scheduling.getFinalResult())
          {
              if(tn.getSubjectCode().equals(cbViewAs.getValue()))
              {
                  for(Student stu : tn.getStudents())
                  {
                      data.add(new ArrayList<>(Arrays.asList(new String[]{stu.getStudentCode(),stu.getLastName(),stu.getFirstName()
                      ,tn.getDate(),tn.getTime(),tn.getRoom()})));
                  }
              }
          }
           tbView.setItems(data);
       }
       if(cbViewType.getValue().equals("Sinh viên"))
       {
           tbView.getColumns().clear();
            TableColumn<ArrayList,String> subjectCode = new TableColumn<>("Subject Code");
           subjectCode.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(0).toString()));
            TableColumn<ArrayList,String> subjectName = new TableColumn<>("Subject Name");
           subjectName.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(1).toString()));
            TableColumn<ArrayList,String> date = new TableColumn<>("Date");
           date.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(2).toString()));
            TableColumn<ArrayList,String> time = new TableColumn<>("Time");
           time.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(3).toString()));
            TableColumn<ArrayList,String> room = new TableColumn<>("Room");
           room.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(4).toString()));
            tbView.getColumns().addAll(subjectCode,subjectName,date,time,room);
           subjectCode.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           subjectName.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           date.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           time.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           room.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           ObservableList<ArrayList> data =  FXCollections.observableArrayList();
           for(TestNode tn: scheduling.getFinalResult())
           {
               for(Student stu: tn.getStudents())
               {
                   if(stu.getStudentCode().equals(cbViewAs.getValue()))
                   {
                       data.add(new ArrayList<>(Arrays.asList(new String[]{tn.getSubjectCode(),
                               tn.getSubjectName(), tn.getDate(), tn.getTime(),tn.getRoom() })));
                       break;
                   }
               }
           }
           tbView.setItems(data);

       }
       if(cbViewType.getValue().equals("Phòng thi"))
       {
           tbView.getColumns().clear();
           TableColumn<ArrayList,String> subjectCode = new TableColumn<>("Subject Code");
           subjectCode.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(0).toString()));
           TableColumn<ArrayList,String> subjectName = new TableColumn<>("Subject Name");
           subjectName.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(1).toString()));
           TableColumn<ArrayList,String> date = new TableColumn<>("Date");
           date.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(2).toString()));
           TableColumn<ArrayList,String> time = new TableColumn<>("Time");
           time.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(3).toString()));
           tbView.getColumns().addAll(subjectCode,subjectName,date,time);
           subjectCode.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           subjectName.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           date.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           time.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           ObservableList<ArrayList> data = FXCollections.observableArrayList();
           for(TestNode tn: scheduling.getFinalResult())
           {
               if(tn.getRoom().equals(cbViewAs.getValue()))
               {
                   data.add(new ArrayList<>(Arrays.asList(new String[]{tn.getSubjectCode(),tn.getSubjectName(),tn.getDate(),tn.getTime()})));
               }
           }
           tbView.setItems(data);
       }

       if(cbViewType.getValue().equals("Ngày thi")) {
           tbView.getColumns().clear();
           TableColumn<ArrayList,String> subjectCode = new TableColumn<>("Subject Code");
           subjectCode.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(0).toString()));
           TableColumn<ArrayList,String> subjectName = new TableColumn<>("Subject Name");
           subjectName.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(1).toString()));
           TableColumn<ArrayList,String> time = new TableColumn<>("Time");
           time.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(2).toString()));
           TableColumn<ArrayList,String> room = new TableColumn<>("Room");
           room.setCellValueFactory((p) -> new ReadOnlyStringWrapper(p.getValue().get(3).toString()));

           tbView.getColumns().addAll(subjectCode,subjectName,time,room);
           subjectCode.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           subjectName.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           room.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           time.setPrefWidth(tbView.getWidth()*(1.0/tbView.getColumns().size()));
           ObservableList<ArrayList> data = FXCollections.observableArrayList();
           for(TestNode tn: scheduling.getFinalResult())
           {
               if((tn.getDate() + " " + tn.getTime()).equals(cbViewAs.getValue()))
               {
                   data.add(new ArrayList<>(Arrays.asList(new String[]{tn.getSubjectCode(),tn.getSubjectName(),tn.getTime(),tn.getRoom()})));
               }
           }
           tbView.setItems(data);
       }
   }
    private void getContentFormFiles()  {
        XlsxFileAccess xlsxFileAccess1 = new XlsxFileAccess(txtStudentList.getText());
        registeredList = xlsxFileAccess1.readData();
        registeredList.remove(0);
        xlsxFileAccess1.close();
        writeLog("Getting data from "+ txtStudentList.getText()+ " successfully");
        XlsxFileAccess xlsxFileAccess2 = new XlsxFileAccess(txtRoomList.getText());
        roomList = xlsxFileAccess2.readData();
        roomList.remove(0);
        xlsxFileAccess2.close();
        writeLog("Getting data from "+ txtRoomList.getText()+ " successfully");

    }
    private boolean checkValid() {
        if (txtStudentList.getText().trim().equals("")) {
            writeLog("Choose student list xlsx file before process task");
            txtStudentList.requestFocus();
            return false;
        } else {
            if (txtRoomList.getText().trim().equals("")) {
                txtRoomList.requestFocus();
                writeLog("Choose room list xlsx file before process task");
                return false;
            } else {
                if (txtExportLocation.getText().trim().equals("")) {
                    txtExportLocation.requestFocus();
                    writeLog("Choose export location before process task");
                    return false;
                }
                else {
                    if(dtpicker.getValue()==null)
                    {
                        dtpicker.requestFocus();
                        writeLog("Choose a start date before process task");
                        return false;
                    }
                }
            }
        }
        return true;
    }


}
