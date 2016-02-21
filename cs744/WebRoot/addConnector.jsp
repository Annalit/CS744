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
<style type="text/css">
    #mynetwork {
      width: 600px;
      height: 600px;
      border: 1px solid lightgray;
    }
  </style>

  <script type="text/javascript" src="js/vis.js"></script>
  <link href="js/vis.css" rel="stylesheet" type="text/css" />

  <script type="text/javascript">
    var nodes = null;
    var edges = null;
    var network = null;

    var DIR = '<%=path%>/img/';
    var EDGE_LENGTH_MAIN = 250;
    var EDGE_LENGTH_SUB = 50;

    // Called when the Visualization API is loaded.
    function draw() {
      // Create a data table with nodes.
      nodes = [];

      // Create a data table with links.
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
        nodes: nodes,
        edges: edges
      };
      var options = {};
      network = new vis.Network(container, data, options);
    }
  </script>
<script type="text/javascript">
function check() {
	var len = document.querySelectorAll('input[type="checkbox"]:checked').length;
	var x = <%=res%>.length;
	if (x != 0 && len == 0 ) {
		alert("You must select at least one pattern!");
		return false;
	}
	return true;
}
</script>
<div class="span10" id="datacontent">
		<form id="form2" action="AddConnector" method="post"
		method="post" onsubmit ="return check()">
		<table class="table">
						<thead>
							<tr>
								<th colspan="5">Add Pattern</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><% 
    	if (res.size() != 0) {
    	 out.println("Please choose the neighbor patterns");}
    	for (int i = 0; i < res.size(); i++) {
    		out.print("<input type=\"checkbox\" name= \"checkedC\" value="+res.get(i)+">" + res.get(i));
    	} %></td>
						
								<td>
									<button class="btn btn-primary" type="submit">Generate Pattern</button>
								</td>
							</tr>
						</tbody>
					</table>
		</form>
<%@ include file="footer.jsp"%>
