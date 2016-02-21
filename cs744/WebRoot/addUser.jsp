<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="head.jsp"%>
<script type="text/javascript">
function check() {
		if (form2.uid.value == null || form2.uid.value == "" ) {
			alert ("The ID of user can't be null!");
			return false;
		}if (form2.password.value == null || form2.password.value=="" ) {
			alert ("The password can't be null!!");
			return false;
		}if (form2.firstname.value == null || form2.firstname.value == "" ) {
			alert ("The first name of user can't be null!");
			return false;
		}if (form2.lastname.value == null || form2.lastname.value == "" ) {
			alert ("The last name of user can't be null!");
			return false;
		}
		return true;
}
</script>
			<div class="span10" id="datacontent">
				<ul class="nav nav-tabs">
					<li class="active"><a>Add a User</a></li>
					<li class="pull-right"><s:a
							href="listUser.action">Check Users</s:a>
					</li>
				</ul>
				<s:fielderror cssClass="error"></s:fielderror>
				<s:form id="form2" action="addUser" method="post">
					<fieldset>
						<div class="control-group">
							<div class="controls">
								<label class="control-label">User ID:</label>
								<s:textfield name="uid"></s:textfield>
								<span class="help-block">(Please enter the ID of the
									user!)</span>
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<label class="control-label">First Name:</label>
								<s:textfield name="firstname"></s:textfield>
								<span class="help-block">(Please enter the first name of the
									user!)</span>
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<label class="control-label">Last Name:</label>
								<s:textfield name="lastname"></s:textfield>
								<span class="help-block">(Please enter the last name of the
									user!)</span>
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<label class="control-label">Password :</label>
								<s:password name="password"></s:password>
								<span class="help-block">(Please enter the password of the professor!)</span>
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<button class="btn btn-primary" type="submit" onclick="return check();">Submit</button>
								<button class="btn btn-primary" type="button"
									onclick="location.href='index.jsp'">Cancel</button>
							</div>
						</div>
					</fieldset>
				</s:form>
			</div>
		</div>
	</div>
</body>
</html>
