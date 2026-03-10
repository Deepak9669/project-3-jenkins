package in.co.rays.project_3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * ModelFactory decides which model implementation run
 * 
 * @author Deepak Verma
 * 
 * 
 *
 * 
 */
public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	private ModelFactory() {

	}

	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	public ProductModelInt getProductModel() {
		ProductModelInt productModel = (ProductModelInt) modelCache.get("productModel");
		if (productModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				productModel = new ProductModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				productModel = new ProductModelHibImp();
			}
			modelCache.put("productModel", productModel);
		}
		return productModel;
	}

	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}

	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}

	public UserModelInt getUserModel() {

		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}

	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				courseModel = new CourseModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}

		return courseModel;
	}

	public TimetableModelInt getTimetableModel() {

		TimetableModelInt timetableModel = (TimetableModelInt) modelCache.get("timetableModel");

		if (timetableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				timetableModel = new TimetableModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				timetableModel = new TimetableModelJDBCImpl();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}

	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}

	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}

	public BusModelInt getBusModel() {

		BusModelInt busModel = (BusModelInt) modelCache.get("busModel");

		if (busModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				busModel = new BusModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				busModel = new BusModelJDBCImpl();
			}

			modelCache.put("busModel", busModel);
		}

		return busModel;
	}

	public AuditModelInt getAuditModel() {

		AuditModelInt auditModel = (AuditModelInt) modelCache.get("auditModel");

		if (auditModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				auditModel = new AuditModelHibImpl();
			}

			modelCache.put("auditModel", auditModel);
		}

		return auditModel;
	}
	public DoctorModelInt getDoctorModel() {

		DoctorModelInt doctorModel = (DoctorModelInt) modelCache.get("doctorModel");

		if (doctorModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				doctorModel = new DoctorModelHibImpl();
			}

			modelCache.put("doctorModel", doctorModel);
		}

		return doctorModel;
	}
	public ExpenseModelInt getExpenseModel() {

		ExpenseModelInt expenseModel = (ExpenseModelInt) modelCache.get("expenseModel");

		if (expenseModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				expenseModel = new ExpenseModelHibImpl();
			}

			modelCache.put("expenseModel", expenseModel);
		}

		return expenseModel;
	}
	public JobModelInt getJobModel() {

		JobModelInt jobModel = (JobModelInt) modelCache.get("jobModel");

		if (jobModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				jobModel = new JobModelHibImpl();
			}

			modelCache.put("jobModel", jobModel);
		}

		return jobModel;
	}
	
	public BranchManagerModelInt getBranchManagerModel() {

	    BranchManagerModelInt branchManagerModel = 
	        (BranchManagerModelInt) modelCache.get("branchManagerModel");

	    if (branchManagerModel == null) {

	        if ("Hibernate".equals(DATABASE)) {
	            branchManagerModel = new BranchManagerModelHibImpl();
	        }

	        modelCache.put("branchManagerModel", branchManagerModel);
	    }

	    return branchManagerModel;
	}
	
	
	public CertificateTemplateModelInt getCertificateTemplateModel() {

		CertificateTemplateModelInt certificateTemplateModel =
				(CertificateTemplateModelInt) modelCache.get("certificateTemplateModel");

		if (certificateTemplateModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				certificateTemplateModel = new CertificateTemplateModelHibImpl();
			}

			modelCache.put("certificateTemplateModel", certificateTemplateModel);
		}

		return certificateTemplateModel;
	}
	
	
	public VendorModelInt getVendorModel() {

		VendorModelInt vendorModel =
				(VendorModelInt) modelCache.get("vendorModel");

		if (vendorModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				vendorModel = new VendorModelHibImpl();
			}

			modelCache.put("vendorModel", vendorModel);
		}

		return vendorModel;
	}
	

	


}
