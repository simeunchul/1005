package com.example.teamproject_main_editing.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ScriptUtils {
	public static void alert(HttpServletResponse res, String alertText, String nextPage) {
		res.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out = res.getWriter();
			out.println("<script>alert('" + alertText + "'); location.href='" + nextPage + "';</script> ");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void alertJust(HttpServletResponse res, String alertText) {
		res.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out = res.getWriter();
			out.println("<script>alert('" + alertText + "');</script> ");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
