package in.co.rays.project_3.controller;

/**
 * ORS View Provide Loose Coupling
 * 
 * @author Deepak Verma
 *
 */
public interface ORSView {

	public String APP_CONTEXT = "/project_3";

	public String PAGE_FOLDER = "/jsp";

	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView404.jsp";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";

	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";

	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";

	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";

	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";

	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";

	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";

	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";

	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";

	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";

	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";

	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";

	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";

	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";

	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";

	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";

	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";

	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";

	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";

	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";

	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";

	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimeTableView.jsp";

	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimeTableListView.jsp";

	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";

	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";

	public String PRODUCT_VIEW = PAGE_FOLDER + "/ProductView.jsp";

	public String PRODUCT_LIST_VIEW = PAGE_FOLDER + "/ProductListView.jsp";

	public String BUS_VIEW = PAGE_FOLDER + "/BusView.jsp";

	public String BUS_LIST_VIEW = PAGE_FOLDER + "/BusListView.jsp";

	public String AUDIT_VIEW = PAGE_FOLDER + "/AuditView.jsp";

	public String AUDIT_LIST_VIEW = PAGE_FOLDER + "/AuditListView.jsp";

	public String DOCTOR_VIEW = PAGE_FOLDER + "/DoctorView.jsp";

	public String DOCTOR_LIST_VIEW = PAGE_FOLDER + "/DoctorListView.jsp";

	public String EXPENSE_VIEW = PAGE_FOLDER + "/ExpenseView.jsp";

	public String EXPENSE_LIST_VIEW = PAGE_FOLDER + "/ExpenseListView.jsp";

	public String JOB_VIEW = PAGE_FOLDER + "/JobView.jsp";

	public String JOB_LIST_VIEW = PAGE_FOLDER + "/JobListView.jsp";

	public String BRANCH_MANAGER_VIEW = PAGE_FOLDER + "/BranchManagerView.jsp";

	public String BRANCH_MANAGER_LIST_VIEW = PAGE_FOLDER + "/BranchManagerListView.jsp";

	public String CERTIFICATE_TEMPLATE_VIEW = PAGE_FOLDER + "/CertificateTemplateView.jsp";

	public String CERTIFICATE_TEMPLATE_LIST_VIEW = PAGE_FOLDER + "/CertificateTemplateListView.jsp";

	public String VENDOR_VIEW = PAGE_FOLDER + "/VendorView.jsp";

	public String VENDOR_LIST_VIEW = PAGE_FOLDER + "/VendorListView.jsp";

	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";

	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";

	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";

	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";

	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";

	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";

	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";

	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";

	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";

	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";

	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";

	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";

	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";

	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";

	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";

	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";

	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";

	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";

	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";

	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimeTableCtl";

	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimeTableListCtl";

	public String PRODUCT_CTL = APP_CONTEXT + "/ctl/ProductCtl";

	public String PRODUCT_LIST_CTL = APP_CONTEXT + "/ctl/ProductListCtl";

	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";

	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";

	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";

	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";

	public String BUS_CTL = APP_CONTEXT + "/ctl/BusCtl";

	public String BUS_LIST_CTL = APP_CONTEXT + "/ctl/BusListCtl";

	public String AUDIT_CTL = APP_CONTEXT + "/ctl/AuditCtl";

	public String AUDIT_LIST_CTL = APP_CONTEXT + "/ctl/AuditListCtl";

	public String DOCTOR_CTL = APP_CONTEXT + "/ctl/DoctorCtl";

	public String DOCTOR_LIST_CTL = APP_CONTEXT + "/ctl/DoctorListCtl";

	public String EXPENSE_CTL = APP_CONTEXT + "/ctl/ExpenseCtl";

	public String EXPENSE_LIST_CTL = APP_CONTEXT + "/ctl/ExpenseListCtl";

	public String JOB_CTL = APP_CONTEXT + "/ctl/JobCtl";

	public String JOB_LIST_CTL = APP_CONTEXT + "/ctl/JobListCtl";

	public String BRANCH_MANAGER_CTL = APP_CONTEXT + "/ctl/BranchManagerCtl";

	public String BRANCH_MANAGER_LIST_CTL = APP_CONTEXT + "/ctl/BranchManagerListCtl";

	public String CERTIFICATE_TEMPLATE_CTL = APP_CONTEXT + "/ctl/CertificateTemplateCtl";

	public String CERTIFICATE_TEMPLATE_LIST_CTL = APP_CONTEXT + "/ctl/CertificateTemplateListCtl";

	public String VENDOR_CTL = APP_CONTEXT + "/ctl/VendorCtl";

	public String VENDOR_LIST_CTL = APP_CONTEXT + "/ctl/VendorListCtl";

}
