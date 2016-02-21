<%@ page language="java" import="java.util.*" import="com.entity.Edge" import="com.dao.NodeDao" import="com.entity.Node" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="head.jsp"%>
<%String error = "hi";
	NodeDao nd = new NodeDao();
	ArrayList<Integer> res = nd.getAllC();
	List<Node> allnodes = nd.getAllNodes();
	List<Edge> edge = nd.getCEdges();
	List<Edge> edgeC = nd.getEdgesForC();
	List<Edge> edgeN = nd.getEdgesForN();
	edgeN.addAll(edge);
	edge.addAll(nd.getNEdges());
	List<Integer> allPatterns = nd.getAllC();%>
<link type="text/css" rel="stylesheet" href="css/table.css" />
<script type="text/javascript" src="js/exampleUtil.js"></script>
<script type="text/javascript" src="js/vis.js"></script>
<link href="js/vis.css" rel="stylesheet" type="text/css" />
<% 
if (allnodes.size() == 0){%>
   <script>
    alert("Please add a pattern first");
    window.location.href = "addConnector.jsp";</script>
   <% }
%>
<% 
if (request.getAttribute("error") == null) {
	} else {
			 error = (String) request.getAttribute("error");
				%>
   <script>
    alert("<%=error%>");</script>
   <% }
%>
<script type="text/javascript">
    var nodes = null;
    var edges = null;
    var network = null;
    var DIR = '<%=path%>/img/';
	var EDGE_LENGTH_MAIN = 300;
	var EDGE_LENGTH_SUB = 80;

	function draw() {
		nodes = [];
		edges = [];
<%
  		if(allnodes != null) { 
  			for (int i=0;i<allnodes.size();i++){%>
	
				<%if (allnodes.get(i).getType().equals("c")){%>
				nodes.push({id :<%=allnodes.get(i).getnID()%>, label : 'Pattern' +<%=allnodes.get(i).getnID()%>,image : DIR + 'Network-Pipe-icon.png',shape : 'image'
				});
				<%}else {%>
				nodes.push({id :<%=allnodes.get(i).getnID()%>,label : 'Node' +<%=allnodes.get(i).getnID()%>,image : DIR + 'Hardware-My-Computer-3-icon.png',shape : 'image'});
<%}%>
	
<%}
  		}%>
	
		<%
  		if(edge != null) { // 有信息返
  			for (int i=0;i<edgeN.size();i++){%>
  			
			edges.push({from :<%=edgeN.get(i).getNode1()%>, to :<%=edgeN.get(i).getNode2()%>,length : EDGE_LENGTH_SUB});
			<%}
			for (int i=0;i<edgeC.size();i++){%>
  			
			edges.push({from :<%=edgeC.get(i).getNode1()%>, to :<%=edgeC.get(i).getNode2()%>,dashes:true,length : EDGE_LENGTH_MAIN});
			<%}
  		}%>
	// create a network
		var container = document.getElementById('mynetwork');
		var data = {
			nodes : nodes,
			edges : edges
		};
		var options = {};
		network = new vis.Network(container, data, options);
	}
</script>
<script src="<%=path%>/js/googleAnalytics.js"></script>
<script type="text/javascript">
	//考虑到当pattern中只有一个点的情况，故并不一定需要左右节点，当pattern节点大于2时才需要，交由后台判断并返回错误信息。
	function check() {
		if (form2.gid.value == null || form2.gid.value =="") {
			return true;
		}
		if (form2.gid.value == form2.n1.value) {
			alert("The node1 can't be the connector!");
			return false;
		}if (form2.gid.value == form2.n2.value) {
			alert("The node2 can't be the connector!");
			return false;
		}if (form2.n1.value == form2.n2.value && form2.n1.value != "" && form2.n2.value != "") {
			alert("The node1 and node2 can't be the same!");
			return false;
		}
		return true;
	}
</script>
			<div class="span10" id="datacontent">

				<form id="form2" action="AddNode" method="post" onsubmit ="return check()">
					<table class="table">
						<thead>
							<tr>
								<th colspan="5">Add Non-connector Node</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>Pattern:</td>
								<td><select name="gid">
										<%
											if (allPatterns != null && allPatterns.size() != 0) {
												for (int i = 0; i < allPatterns.size(); i++) {
													int pattern = allPatterns.get(i);
													out.println("<option value = " +  pattern + ">" +  pattern
															+ "</option>");
												}
											}
										%>
								</select></td>
								<td>NonConnector Node1：</td>
								<td class="input"><input name="n1" class="input-small"
									size="10" type="text" value=""></input></td>
								<td>NonConnector Node2：</td>
								<td class="input"><input class="input-small" name="n2"
									size="10" type="text" value=""></input></td>
								<td class="input"><input name="flag" type="checkbox" value="0">Connect to the connector</input></td>
								<td>
									<button class="btn btn-primary" type="submit">Add</button>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<%@ include file="footer.jsp"%>