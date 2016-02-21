<%@ page language="java" import="java.util.*" import="com.entity.Edge" import="com.dao.NodeDao" import="com.entity.Node" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="head.jsp"%>
<%String error = "";
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
		var x =  form2.nid.value;
		if (x == null || x == "") {
			alert("The node can't be the null!");
			return false;
		} 
			return true;
	}
</script>
			<div class="span10" id="datacontent">

				<form id="form2" action="deleteNode" method="post" onsubmit ="return check()">
					<table class="table">
						<thead>
							<tr>
								<th colspan="5">Delete Node</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="input"><input class="input-small" name="nid"
									size="10" type="text" value=""></input></td>
								<td>
									<button class="btn btn-primary" type="submit">Delete</button>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
				<%@ include file="footer.jsp"%>