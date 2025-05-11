package vn.hsu.StudentInformationSystem.config;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.StudentService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Configuration
public class DataInitializer {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final DataSource dataSource;
    private final StudentService studentService;

    public DataInitializer(DataSource dataSource, StudentService studentService) {
        this.dataSource = dataSource;
        this.studentService = studentService;
    }

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner() {
        return args -> {
            // Đoạn code sẽ được chạy khi ứng dụng khởi động
            log.info("Application has started!");
            log.info("Starting database initialization via DataInitializer.sql");

            // Ví dụ: Thực hiện khởi tạo dữ liệu
            try (Connection conn = dataSource.getConnection()) {
                // Check if any students exist; if so, skip seeding
                try (Statement checkStmt = conn.createStatement();
                     ResultSet rs = checkStmt.executeQuery("SELECT COUNT(*) FROM students")) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        log.info("Existing students found, skipping SQL seed.");
                        return;
                    }
                }

                // No existing students, proceed with script
                log.info("No students found; Init Student");
                initialDefaultStudents();
                log.info("Executing SQL seed script DataInitializer.sql");
                ScriptUtils.executeSqlScript(conn, new ClassPathResource("DataInitializer.sql"));
                log.info("SQL seed script executed successfully.");
            } catch (Exception e) {
                log.error("Error executing SQL seed script.", e);
                throw e;
            }

            log.info("Application Still running at: ");
            log.info("localhost:8080/api/v1");
        };
    }

    private void initialDefaultStudents() {
        // Generate dummy data
        this.studentService.handleCreateStudent(new Student(22002579, "Phạm Minh Hùng", "user", "123456"));
        this.studentService.handleCreateStudent(new Student(22002580, "Trịnh Văn Bình", "student", "123456"));
        // Generate sample students
        this.studentService.handleCreateStudent(new Student(22002575, "Trần Gia Nguyên Phong", "phong.tgn02575", "123456"));
        this.studentService.handleCreateStudent(new Student(22002581, "Võ Thị Kim Ngân", "ngan.vtk02581", "123456"));
        this.studentService.handleCreateStudent(new Student(22002576, "Nguyễn Văn An", "an.nv02576", "123456"));
        
        log.info("Add default users complete!");
    }

    /*
    private final StudentService studentService;
    private final CourseService courseService;
    private final SemesterService semesterService;

    public DataInitializer(StudentService studentService, CourseService courseService, SemesterService semesterService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.semesterService = semesterService;
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            // Đoạn code sẽ được chạy khi ứng dụng khởi động
            System.out.println("Application has started!");

            // Ví dụ: Thực hiện khởi tạo dữ liệu
            initialDefaultUser();

            System.out.println("Application Still running at: ");
            System.out.println("localhost:8080/api");
        };
    }

    private void initialDefaultUser() {
        Optional<Student> studentOptional = this.studentService.handleFetchStudentOptionalByUsername("admin");

        if (studentOptional.isEmpty()) {
            this.studentService.handleCreateStudent(
                    new Student(00000001, "Admin", "admin", "123456")
            );

            System.out.println("Add default users complete!");
        } else {
            System.out.println("Already have default users!");
        }
    }

    private void initialDefaultSemester() {
        long dbSemesterQuantity = this.semesterService.handleCheckSemesterQuantity();
        if (dbSemesterQuantity == 0) {
            this.semesterService.handleCreateSemester(new Semester(2431, 2024, "Học kỳ 1 năm 2024"));

            System.out.println("Add default semester complete!");
        } else {
            System.out.println("Already have semester courses!");
        }
    }

    private void initialDefaultStudents() {
        long dbUserQuantity = this.studentService.handleCheckUserQuantity();
        if (dbUserQuantity == 0) {
            // Generate dummy data
//            this.studentService.handleCreateStudent(new Student(22002579, "Phạm Minh Hùng", "user", "123456"));
//            this.studentService.handleCreateStudent(new Student(22002580, "Trịnh Văn Bình", "student", "123456"));

            // Generate sample students
            this.studentService.handleCreateStudent(new Student(22002575, "Trần Gia Nguyên Phong", "phong.tgn02575", "123456"));
            this.studentService.handleCreateStudent(new Student(22002581, "Võ Thị Kim Ngân", "ngan.vtkn02581", "123456"));
            this.studentService.handleCreateStudent(new Student(22002576, "Nguyễn Văn An", "an.nva02576", "123456"));

            System.out.println("Add default users complete!");
        } else {
            System.out.println("Already have default users!");
        }
    }

    private void initialDefaultCourse() {
        long dbCourseQuantity = this.courseService.handleCheckCourseQuantity();
        if (dbCourseQuantity == 0) {
            this.courseService.handleCreateCourse(new Course("KHTQ 105DV01", "Toán Rời rạc", 3, 4383000));
            this.courseService.handleCreateCourse(new Course("PSY 107DV01", "Tâm lý học - Khái niệm và UD", 3, 4464000));
            this.courseService.handleCreateCourse(new Course("DC 140DV01", "Triết học Mác-Lênin", 3, 3465000));
            this.courseService.handleCreateCourse(new Course("CN 103DV01", "Mạng máy tính cơ sở", 3, 5653000));
            this.courseService.handleCreateCourse(new Course("CN 104DV01", "Hệ thống Máy tính", 3, 5653000));
            this.courseService.handleCreateCourse(new Course("DC 144DV01", "Lịch sử Đảng Cộng sản Việt Nam", 3, 2310000));
            this.courseService.handleCreateCourse(new Course("SW 103DV01", "Lập trình Hướng đối tượng", 3, 5653000));
            this.courseService.handleCreateCourse(new Course("SW 402DE01", "Kiến trúc Phần mềm", 3, 8260000));
            this.courseService.handleCreateCourse(new Course("PHI 117DV01", "Triết học trong Cuộc sống", 3, 4464000));
            this.courseService.handleCreateCourse(new Course("SW 210DE01", "Công nghệ Phần mềm", 3, 6883000));
            this.courseService.handleCreateCourse(new Course("SW 306DV01", "Phát triển Web Front-End", 3, 5653000));
            this.courseService.handleCreateCourse(new Course("SW 310DV01", "Phát triển ứng dụng trên TB di động", 3, 6218000));

            System.out.println("Add default courses complete!");
        } else {
            System.out.println("Already have default courses!");
        }
    }

    // Todo
//    private void addCoursesToSemester() {
//
//    }

    // Todo
//    private void addCoursesToStudent() {
//    }

    // Todo
//    private void addCourseGradeToStudent() {
////        Random random = new Random();
//    }


     */
}
