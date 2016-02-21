<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.domain.Question"%>
<%@ page import="com.dao.QuestionDao"%>
<%@ page import="com.annabel.UserDao"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	QuestionDao qd = new QuestionDao();
	UserDao ud = new UserDao();
	String first_name = (String) session.getAttribute("first_name");
	String uid = (String) session.getAttribute("uid");
	ArrayList<Integer> questionId = ud.getQuestionsByUser(uid);
	int count = 0;
	if (session.getAttribute("count") != null) {
		count = (Integer) session.getAttribute("count");
		if (count >= 3) {
			response.sendRedirect("login.jsp");
		}
	}
	Random rand = new Random();
	int qId = questionId.get(rand.nextInt(3));
	if (count < 3 && session.getAttribute("answered") != null) {
		HashSet<Integer> hs = (HashSet) session
				.getAttribute("answered");
		while (hs.contains(qId)) {
			rand = new Random();
			qId = questionId.get(rand.nextInt(3));
		}
	}
	Question cur = qd.getQuestionById(qId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<link type="text/css" rel="stylesheet" href="css/setQues.css" />
<body>
	<div id="wrapper">
		<form name="login-form" class="login-form" action="ansQuestion"
			method="post" onsubmit="validate(this)">
			<div class="header">
				<h1>Security Questions</h1>
				<div id="demo" class="error"></div>
				<%
					if (session.getAttribute("count") != null) {
						count = (Integer) session.getAttribute("count");
						if (count < 3 && count > 0) {
							out.println("<script type =\"text/javascript\">document.getElementById(\"demo\").innerHTML = \"Your answer is incorrect, you have"
									+ (3 - count) + " more chances, then you will return to login page\"</script>");

						}
					}
				%>
			</div>
			<div class="content">
				<%=cur.getContent()%>
				<input type="text" class="input username" name="ans"></input> <input
					type="hidden" name="qId" value=<%=qId%>></input>
			</div>
			<div class="footer">
				<input type="submit" name="submit" value="Submit" class="button">
			</div>
		</form>
	</div>
</body>
</html>
