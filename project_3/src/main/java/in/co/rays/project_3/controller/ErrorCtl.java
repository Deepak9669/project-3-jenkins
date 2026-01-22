package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ErrorCtl", urlPatterns = { "/ErrorCtl" })
public class ErrorCtl extends BaseCtl {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // 1) container attribute (standard)
        String lastCtl = (String) request.getAttribute("javax.servlet.error.request_uri");

        // 2) fallback from FrontController
        if (lastCtl == null) {
            lastCtl = (String) request.getAttribute("lastCtl");
        }

        // actual exception
        Throwable ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

        System.out.println("Error on controller: " + lastCtl);
        if (ex != null) {
            ex.printStackTrace();
        }

        // ✅ Decide which JSP to show
        String view = getViewFromCtl(lastCtl);

        // ✅ Decide message (DB down or general)
        String msg = getMessage(ex);
        ServletUtility.setErrorMessage(msg, request);

        // ===== LIST PAGE SAFETY =====
        // If any list controller failed due to DB down, avoid JSP crash
        if (lastCtl != null && lastCtl.contains("ListCtl")) {

            // Many JSPs expect "list" attribute
            if (request.getAttribute("list") == null) {
                request.setAttribute("list", new ArrayList());
            }

            // Sometimes list is set using ServletUtility.setList()
            if (ServletUtility.getList(request) == null) {
                ServletUtility.setList(new ArrayList(), request);
            }

            // paging variables
            request.setAttribute("pageNo", 1);
            request.setAttribute("pageSize", 10);
            request.setAttribute("nextListSize", 0);
        }

        // ✅ ALWAYS JSP forward
        ServletUtility.forward(view, request, response);
    }

    /**
     * Detect DB down exceptions and return message
     */
    private String getMessage(Throwable ex) {

        if (ex == null) {
            return "Database Down!!!";
        }

        // Walk cause chain
        Throwable t = ex;
        while (t != null) {
            String cn = t.getClass().getName();

            // Hibernate / JDBC connection problems
            if (cn.contains("JDBCConnectionException")
                    || cn.contains("CommunicationsException")
                    || cn.contains("CJCommunicationsException")
                    || cn.contains("ConnectionIsClosedException")
                    || cn.contains("SQLNonTransientConnectionException")) {
                return "Database server down!!! Please start MySQL container.";
            }

            t = t.getCause();
        }

        return "Database down!!!";
    }

    /**
     * Return JSP view according to controller path
     */
    private String getViewFromCtl(String ctl) {

        if (ctl == null)
            return ORSView.ERROR_VIEW;

        // ===== MASTER FORMS =====
        if (ctl.endsWith(ORSView.USER_CTL))
            return ORSView.USER_VIEW;

        if (ctl.endsWith(ORSView.ROLE_CTL))
            return ORSView.ROLE_VIEW;

        if (ctl.endsWith(ORSView.COLLEGE_CTL))
            return ORSView.COLLEGE_VIEW;

        if (ctl.endsWith(ORSView.STUDENT_CTL))
            return ORSView.STUDENT_VIEW;

        if (ctl.endsWith(ORSView.FACULTY_CTL))
            return ORSView.FACULTY_VIEW;

        if (ctl.endsWith(ORSView.COURSE_CTL))
            return ORSView.COURSE_VIEW;

        if (ctl.endsWith(ORSView.SUBJECT_CTL))
            return ORSView.SUBJECT_VIEW;

        if (ctl.endsWith(ORSView.TIMETABLE_CTL))
            return ORSView.TIMETABLE_VIEW;

        if (ctl.endsWith(ORSView.BUS_CTL))
            return ORSView.BUS_VIEW;

        if (ctl.endsWith(ORSView.MARKSHEET_CTL))
            return ORSView.MARKSHEET_VIEW;

        if (ctl.endsWith(ORSView.GET_MARKSHEET_CTL))
            return ORSView.GET_MARKSHEET_VIEW;

        if (ctl.endsWith(ORSView.CHANGE_PASSWORD_CTL))
            return ORSView.CHANGE_PASSWORD_VIEW;

        if (ctl.endsWith(ORSView.MY_PROFILE_CTL))
            return ORSView.MY_PROFILE_VIEW;

        if (ctl.endsWith(ORSView.FORGET_PASSWORD_CTL))
            return ORSView.FORGET_PASSWORD_VIEW;

        if (ctl.endsWith(ORSView.LOGIN_CTL))
            return ORSView.LOGIN_VIEW;

        if (ctl.endsWith(ORSView.WELCOME_CTL))
            return ORSView.WELCOME_VIEW;

        if (ctl.endsWith(ORSView.USER_REGISTRATION_CTL))
            return ORSView.USER_REGISTRATION_VIEW;

        // ===== LIST PAGES =====
        if (ctl.endsWith(ORSView.USER_LIST_CTL))
            return ORSView.USER_LIST_VIEW;

        if (ctl.endsWith(ORSView.ROLE_LIST_CTL))
            return ORSView.ROLE_LIST_VIEW;

        if (ctl.endsWith(ORSView.COLLEGE_LIST_CTL))
            return ORSView.COLLEGE_LIST_VIEW;

        if (ctl.endsWith(ORSView.STUDENT_LIST_CTL))
            return ORSView.STUDENT_LIST_VIEW;

        if (ctl.endsWith(ORSView.FACULTY_LIST_CTL))
            return ORSView.FACULTY_LIST_VIEW;

        if (ctl.endsWith(ORSView.COURSE_LIST_CTL))
            return ORSView.COURSE_LIST_VIEW;

        if (ctl.endsWith(ORSView.SUBJECT_LIST_CTL))
            return ORSView.SUBJECT_LIST_VIEW;

        if (ctl.endsWith(ORSView.TIMETABLE_LIST_CTL))
            return ORSView.TIMETABLE_LIST_VIEW;

        if (ctl.endsWith(ORSView.BUS_LIST_CTL))
            return ORSView.BUS_LIST_VIEW;

        if (ctl.endsWith(ORSView.MARKSHEET_LIST_CTL))
            return ORSView.MARKSHEET_LIST_VIEW;

        if (ctl.endsWith(ORSView.MARKSHEET_MERIT_LIST_CTL))
            return ORSView.MARKSHEET_MERIT_LIST_VIEW;

        return ORSView.ERROR_VIEW;
    }

    @Override
    protected String getView() {
        return ORSView.LOGIN_VIEW;
    }
}
