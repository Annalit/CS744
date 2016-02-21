
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
<meta name="title" content="University of Wisconsin-La Crosse"></meta>
<meta name="apple-mobile-web-app-capable" content="yes"></meta>
<title>University of Wisconsin-La Crosse</title>
<script src="js/googleAnalytics.js"></script>
<link href="css/bootstrap-combined.min.css" rel="stylesheet"></link>
<link href="css/layoutit.css" rel="stylesheet"></link>
<link href="css/plugin.css" rel="stylesheet"></link>
<link href="css/datepicker.css" rel="stylesheet"></link>
<link href="css/mathquill.css" rel="stylesheet"></link>
<%String path = request.getContextPath(); %>
</head>
<body onload="draw();">
	<div class="navbar navbar-inverse">
		<div class="navbar-inner">
			<div class="container-fluid">
				<button class="btn btn-navbar" type="button">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a target="_blank" class="brand">Concentric Ring Network</a>
				<div class="nav-collapse navbar-responsive-collapse collapse">
					<ul class="nav pull-right">
						<li><a ><%=request.getSession().getAttribute("firstname")%></a></li>
						<li><a href="Logout">log out</a></li>
						
					</ul>
				</div>

			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<ul class="nav nav-tabs nav-stacked">
					<li><s:a href="addUser.jsp">Add a User</s:a>
					</li>
					<li><s:a href="listUser.action">Check Users</s:a></li>
					<li class="dropdown-submenu">
    					<a tabindex="-1" href="#">Add Node</a>
    					<ul class="dropdown-menu">
      						<li><s:a href="addNode.jsp">Add Non-Connector Node</s:a></li>
							<li class="active"><s:a href="addConnector.jsp">Add Pattern</s:a></li>
    					</ul>
  					</li>
  					<li><s:a href="deleteNode.jsp">Delete Node</s:a></li>
				</ul>
			</div>