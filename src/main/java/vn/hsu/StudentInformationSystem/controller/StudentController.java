package vn.hsu.StudentInformationSystem.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hsu.StudentInformationSystem.model.*;
import vn.hsu.StudentInformationSystem.service.*;
import vn.hsu.StudentInformationSystem.service.dto.*;
import vn.hsu.StudentInformationSystem.service.mapper.*;
import vn.hsu.StudentInformationSystem.util.SecurityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for all student-facing operations under /api/v1/students/me:
 * <ul>
 *   <li>Profile lookup</li>
 *   <li>Password change</li>
 *   <li>Course grades retrieval by semester</li>
 *   <li>Exam schedule retrieval by semester</li>
 *   <li>Tuition details retrieval</li>
 *   <li>Available semesters retrieval</li>
 * </ul>
 */
@RestController
@RequestMapping("api/v1/students/me")
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final TuitionService tuitionService;
    private final SemesterService semesterService;
    private final PhotocopyTransactionService photocopyTransactionService;
    private final AnnouncementService announcementService;

    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final CourseExamMapper courseExamMapper;
    private final TuitionMapper tuitionMapper;
    private final SemesterMapper semesterMapper;
    private final PhotocopyMapper photocopyMapper;
    private final AnnouncementMapper announcementMapper;

    public StudentController(StudentService studentService, CourseService courseService, TuitionService tuitionService, SemesterService semesterService, PhotocopyTransactionService photocopyTransactionService, AnnouncementService announcementService, StudentMapper studentMapper, CourseMapper courseMapper, CourseExamMapper courseExamMapper, TuitionMapper tuitionMapper, SemesterMapper semesterMapper, PhotocopyMapper photocopyMapper, AnnouncementMapper announcementMapper) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.tuitionService = tuitionService;
        this.semesterService = semesterService;
        this.photocopyTransactionService = photocopyTransactionService;
        this.announcementService = announcementService;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
        this.courseExamMapper = courseExamMapper;
        this.tuitionMapper = tuitionMapper;
        this.semesterMapper = semesterMapper;
        this.photocopyMapper = photocopyMapper;
        this.announcementMapper = announcementMapper;
    }

    /**
     * GET  /api/v1/students/me
     * <p>
     * Fetch the profile of the currently authenticated student.
     *
     * @return 200 OK with a StudentProfileResponse JSON body containing:
     * <ul>
     *   <li>id</li>
     *   <li>code</li>
     *   <li>fullName</li>
     *   <li>username</li>
     * </ul>
     * @throws EntityNotFoundException if the token is missing or invalid
     */
    @GetMapping("")
    public ResponseEntity<StudentProfileResponse> fetchAccount() {
        // 1. Extract username from JWT
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new EntityNotFoundException("Student with username not found"));

        // 2. Fetch student record
        Student dbStudent = this.studentService.handleFetchStudentByUsername(username);

        // 3. Convert to DTO
        StudentProfileResponse studentProfileResponse = this.studentMapper.toProfile(dbStudent);

        // 4. Return DTO
        return ResponseEntity.status(HttpStatus.OK).body(studentProfileResponse);
    }

    /**
     * PATCH  /api/v1/students/me/pwd
     * <p>
     * Change the password of the currently authenticated student.
     *
     * @param request JSON body containing: { "newPassword": "..." }
     * @return 200 OK with plain-text confirmation
     * @throws EntityNotFoundException if the token is missing or invalid
     */
    @PatchMapping("pwd")
    public ResponseEntity<String> updateStudentPassword(@RequestBody PasswordChangeRequest request) {
        // 1. Validate authentication
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new EntityNotFoundException("Student with username not found"));

        // 2. Fetch student
        Student dbStudent = this.studentService.handleFetchStudentByUsername(username);

        // 3. Update password (service handles hashing)
        this.studentService.handleUpdateStudentPassword(dbStudent.getId(), request.getNewPassword());

        // 4. Return success message
        return ResponseEntity.status(HttpStatus.OK).body("Password Updated!");
    }

    /**
     * GET  /api/v1/students/me/grade/{semesterCode}
     * <p>
     * Retrieve all courses and their grades for the authenticated student
     * in the given semester.
     *
     * @param semesterCode business identifier of the semester (e.g., 2431)
     * @return 200 OK with a JSON array of CourseGradeResponse, each containing:
     * <ul>
     *   <li>courseCode</li>
     *   <li>courseName</li>
     *   <li>credit</li>
     *   <li>grade</li>
     * </ul>
     * @throws EntityNotFoundException if authentication fails
     */
    @GetMapping("grade/{semesterCode}")
    public ResponseEntity<List<CourseGradeResponse>> fetchStudentCourseGrade(@PathVariable("semesterCode") long semesterCode) {
        // Extract and validate user
        String username = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new EntityNotFoundException("User not authenticated!"));

        // Fetch student entity
        Student student = this.studentService.handleFetchStudentByUsername(username);

        // Fetch courses + grades
        List<Course> courseList = this.courseService.handleFetchCoursesByStudentAndSemesterCode(student.getId(), semesterCode);

        // Map to DTOs
        List<CourseGradeResponse> courseGradeResponseList = new ArrayList<>();
        for (Course course : courseList) {
            courseGradeResponseList.add(this.courseMapper.toDto(course));
        }

        // 5. Return 200 OK with the DTO list
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseGradeResponseList);
    }

    /**
     * GET  /api/v1/students/me/exam/{semesterCode}
     * <p>
     * Retrieve the exam schedule for the authenticated student
     * in the given semester.
     *
     * @param semesterCode business identifier of the semester
     * @return 200 OK with a JSON array of CourseExamResponse, each containing:
     * <ul>
     *   <li>courseCode</li>
     *   <li>courseName</li>
     *   <li>examDate</li>
     *   <li>examTime</li>
     * </ul>
     */
    @GetMapping("exam/{semesterCode}")
    public ResponseEntity<List<CourseExamResponse>> fetchStudentCourseExam(@PathVariable("semesterCode") long semesterCode) {
        // 1. Validate and fetch current user
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new EntityNotFoundException("User not authenticated!")
        );

        // 2. Fetch exam-scheduled courses
        Student me = studentService.handleFetchStudentByUsername(username);

        // 3. Convert to DTOs
        List<Course> courseList = courseService.handleFetchExamScheduleByStudentAndSemesterCode(me.getId(), semesterCode);

        // 4. Return exam schedule
        List<CourseExamResponse> courseExamResponseList = new ArrayList<>();
        for (Course course : courseList) {
            // Map Course entity to CourseExamResponse DTO
            CourseExamResponse courseExamResponse = this.courseExamMapper.toDto(course);
            courseExamResponseList.add(courseExamResponse);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseExamResponseList);
    }

    /**
     * GET  /api/v1/students/me/tuition
     * <p>
     * Retrieve the tuition details for the authenticated student
     * across all semesters.
     *
     * @return 200 OK with a JSON array of TuitionResponse, each containing:
     * <ul>
     *   <li>semesterCode</li>
     *   <li>total</li>
     *   <li>paid</li>
     *   <li>refund</li>
     *   <li>balance</li>
     *   <li>isPaid</li>
     * </ul>
     */
    @GetMapping("tuition")
    public ResponseEntity<List<TuitionResponse>> fetchStudentTuition() {
        // 1. Validate user
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new EntityNotFoundException("User not authenticated!")
        );

        // 2. Fetch tuition records
        Student me = studentService.handleFetchStudentByUsername(username);

        // 3. Map to DTOs
        List<Tuition> tuitionList = this.tuitionService.handleFetchAllTuitionByStudent(me);
        List<TuitionResponse> tuitionResponseList = new ArrayList<>();
        for (Tuition tuition : tuitionList) {
            tuitionResponseList.add(this.tuitionMapper.toDto(tuition));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tuitionResponseList);
    }

    /**
     * GET  /api/v1/students/me/semester
     * <p>
     * Retrieve all semesters in which the authenticated student has enrolled.
     *
     * @return 200 OK with a JSON array of SemesterResponse, each containing:
     * <ul>
     *   <li>semesterCode</li>
     *   <li>year</li>
     *   <li>description</li>
     * </ul>
     */
    @GetMapping("semester")
    public ResponseEntity<List<SemesterResponse>> fetchStudentSemester() {
        // 1. Validate and fetch user
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new EntityNotFoundException("User not authenticated!")
        );

        // 2. Fetch semesters via service
        Student me = studentService.handleFetchStudentByUsername(username);

        // 3. Map to DTOs
        List<Semester> semesterList = this.semesterService.handleFetchSemesterByStudent(me);
        List<SemesterResponse> semesterResponseList = new ArrayList<>();
        for (Semester semester : semesterList) {
            semesterResponseList.add(this.semesterMapper.toDto(semester));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(semesterResponseList);
    }

    /**
     * GET  /api/v1/students/me/photocopy
     * <p>
     * Returns the authenticated student's current photocopy balance
     * and full transaction history.
     * <p>
     * Steps:
     * 1. Extract the username from the JWT.
     * 2. Load the Student entity by username.
     * 3. Fetch all PhotocopyTransaction entries for that student.
     * 4. Map the Student and transactions into a PhotocopyResponse DTO.
     *
     * @return 200 OK with a PhotocopyResponse JSON body containing:
     * <ul>
     *   <li>photocopyBalance: current remaining balance</li>
     *   <li>transactions: list of transactions each with date and amount</li>
     * </ul>
     * @throws EntityNotFoundException if the user is not authenticated
     */
    @GetMapping("photocopy")
    public ResponseEntity<PhotocopyResponse> fetchStudentPhotocopyBalance() {
        // 1. Validate and fetch user
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new EntityNotFoundException("User not authenticated!")
        );

        // 2. Fetch semesters via service
        Student me = studentService.handleFetchStudentByUsername(username);

        List<PhotocopyTransaction> photocopyTransactionList = this.photocopyTransactionService.handleFetchAllByStudent(me);

        PhotocopyResponse photocopyResponse = this.photocopyMapper.toDto(me, photocopyTransactionList);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(photocopyResponse);
    }

    /**
     * GET  /api/v1/students/me/announcement
     * <p>
     * Retrieves all announcements visible to the authenticated student.
     * <p>
     * Steps:
     * 1. Extract the username from the JWT.
     * 2. Load the Student entity by username (to ensure valid session).
     * 3. Fetch all Announcement entities (no filtering by student).
     * 4. Map each Announcement to an AnnouncementResponse DTO.
     *
     * @return 200 OK with a JSON array of AnnouncementResponse, each containing:
     * <ul>
     *   <li>title</li>
     *   <li>linkUrl</li>
     *   <li>imageLinkUrl (nullable)</li>
     *   <li>category</li>
     * </ul>
     * @throws EntityNotFoundException if the user is not authenticated
     */
    @GetMapping("announcement")
    public ResponseEntity<List<AnnouncementResponse>> fetchAllAnnouncement() {
        // 1. Validate and fetch user
        String username = SecurityUtils.getCurrentUserLogin().orElseThrow(
                () -> new EntityNotFoundException("User not authenticated!")
        );

        // 2. Fetch semesters via service
        Student me = studentService.handleFetchStudentByUsername(username);

        List<Announcement> announcementList = this.announcementService.handleFetchAllAnnouncement();

        List<AnnouncementResponse> announcementResponseList = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            announcementResponseList.add(
                    this.announcementMapper.toDto(announcement)
            );
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(announcementResponseList);
    }
}
