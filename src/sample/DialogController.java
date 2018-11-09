package sample;

import javafx.scene.control.TextArea;

/**
 * Created by nguyennghi on 9/23/17.
 */
public class DialogController {
    public TextArea txtReadme;

    public static final String Content =
            "    Do tính chất phức tạp của dữ liệu đầu vào là tập hợp các môn học với các hình thức thi và thời lượng khác nhau nên trong Phần mềm này thực hiện một số qui ước đảm bảo tính thất thoát dữ liệu do đầu vào hạn chế như sau:\n" +
            "       + Nhà trường gồm các phòng máy, phòng lý thuyết, phòng hoạ, và khu thể thao dành cho khối ngành thể dục và các môn thể chất.\n" +
            "       + Những môn học chung cho tất cả các ngành (bao gồm kỹ năng, những môn về Đảng và Nhà nước)sẽ thi theo hình thức chung cho tất cả các ngành.\n" +
            "       + Các môn có chia tổ lý thuyết và thực hành sẽ có hình thức thi giữ kỳ là trắc nghiệm trên máy và thi giấy cuối kỳ.\n" +
            "       + Các môn có tên thực hành nhưng không chia tổ sẽ thi thực hành cho cả hai kỳ thi. Vd: Môn thực hành toán cho tin học.\n" +
            "       + Thời lượng thi là 45 phút cho giữa kỳ và 60 phút cho cuối kỳ bất kể hình thức thi.\n" +
            "        +Thời gian bắt đầu các ca thi như sau: Giữa kì: Ca 1 (8:00), Ca 2 (9:00), Ca 3 (10:00), Ca 4 (11:00), Ca 5 (13:00), Ca 6 (14:00), Ca 7 (15:00), Ca 8 (14:00) Ca 9 (17:00), Ca 10 (18:00); Cuối Kỳ Ca 1 (8:00), Ca 2 (9:45), Ca 3 (13:00), Ca 4 (15:45) \n"+
            "       + Những môn không có chia tổ thực hành sẽ thi 100% lý thuyết.\n" +
            "       + Đồ án không thi.\n";
    public void initialize() {

        txtReadme.setWrapText(true);
        txtReadme.setText(Content);
    }

}
