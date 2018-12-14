//recursive function to create child inside parent
function createChildren(childDoc) {
	$.each(childDoc, function(index1, currentChildDoc) {
		tr = $("<tr data-tt-id='" +currentChildDoc.DocId + "'data-tt-parent-id='" + currentChildDoc.parentID + "'>");
		tr.append("<td> <span class='file' id ="+ currentChildDoc.DocId+">" + currentChildDoc.fileName + "</span></td>");
		tr.append("<td>" + currentChildDoc.docType + "</td>");
		tr.append("<td>" + ((currentChildDoc.iconCls=="fileName-folder") ? '--' : currentChildDoc.fileSize) + "</td>");
		tr.append("<td> <span class='icon-mini-edit' id =add-"+ currentChildDoc.DocId+"/><span class='icon-mini-remove' id =remove-"+ currentChildDoc.DocId+"/><span class='icon-mini-download' id =upload-"+ currentChildDoc.DocId+"/></td>");
		tr.append("</tr>");
		
		$('#example-advanced').append(tr);
		if(currentChildDoc.iconCls=="fileName")
		{
	    $("#add-"+currentChildDoc.DocId).removeClass('icon-mini-edit');
//		$("#remove-"+currentChildDoc.DocId).removeClass('icon-mini-remove');
//		$("#upload-"+currentChildDoc.DocId).removeClass('icon-mini-remove');
		}
		if(currentChildDoc.iconCls=="fileName-folder")
		{
		$("#"+currentChildDoc.DocId).addClass('folder');
		$("#"+currentChildDoc.DocId).removeClass('file');
		$("#upload-"+currentChildDoc.DocId).removeClass('icon-mini-download');
		$("#upload-"+currentChildDoc.DocId).addClass('icon-mini-upload');
		}
		
		
		if(currentChildDoc.children !=null)
			{
		var childDocChild = currentChildDoc.children;
		createChildren (childDocChild);
			}
		
	});
}
var deletedRowId;
$(document).ready(
		function() {
			$.ajax({
				type : "POST",
				url : "/JqueryTreeViewExample/ConnectDBServlet",
				success : function(data) {
					var documents = data.children;
					var tr;
					$.each(documents, function(index, currentDocument) {
						tr = $("<tr data-tt-id='" +currentDocument.DocId + "'>");		
						tr.append("<td> <span class='file' id ="+ currentDocument.DocId+">" + currentDocument.fileName + "</span></td>");
						tr.append("<td>" + currentDocument.docType + "</td>");
						tr.append("<td>" + ((currentDocument.iconCls=="fileName-folder") ? '--' : currentDocument.fileSize) + "</td>");
						tr.append("<td> <span class='icon-mini-edit' id =add-"+ currentDocument.DocId+"/><span class='icon-mini-remove'id=remove-"+ currentDocument.DocId+"/><span class='icon-mini-upload' id =upload-"+ currentDocument.DocId+"/></td>");
						tr.append("</tr>");
						$('#example-advanced').append(tr);
						if (currentDocument.children !=null)
							{
						var childDoc = currentDocument.children;
						createChildren (childDoc);}
							if(currentDocument.iconCls=="fileName-folder")
						{
						$("#"+currentDocument.DocId).addClass('folder');
						$("#"+currentDocument.DocId).removeClass('file');
						}
					});
					 //loadtreeview();
									},
				error : function(e) {
					alert("Invalid Request !");
				}
			});
			
			$('#Save').click( function() {
				  var table = $('#example-advanced').tableToJSON(); // Convert the table into a javascript object
				  console.log(table);
				  var jsonData=JSON.stringify(table)+deletedRowId;
				  alert(jsonData);
				  $.ajax({
						type : "POST",
						url : "/JqueryTreeViewExample/UpdateServlet",
						data:{
							jsonData:jsonData
							},
				  success : function(data) {
					  console.log("Data is saved Successfully");
				  }
						});
				});
			
		});
function loadtreeview() {
	$("#example-advanced").treetable({
		expandable : true
	});
//	$("tr:even").addClass("even");
	$("#example-advanced .folder").each(
			function() {
				$(this).parents("tr").droppable(
						{
							accept : ".file, .folder",
							drop : function(e, ui) {
								var droppedEl = ui.draggable
										.parents("tr");
								$("#example-advanced").treetable(
										"move", droppedEl.data("ttId"),
										$(this).data("ttId"));
							},
							hoverClass : "accept",
							over : function(e, ui) {
								var droppedEl = ui.draggable
										.parents("tr");
								if (this != droppedEl[0]
										&& !$(this).is(".expanded")) {
									$("#example-advanced").treetable(
											"expandNode",
											$(this).data("ttId"));
								}
							}
						});
			});
	
	// Drag & Drop Example Code
	$("#example-advanced .file, #example-advanced .folder").draggable({
		helper : "clone",
		opacity : .75,
		refreshPositions : true, // Performance?
		revert : "invalid",
		revertDuration : 300,
		scroll : true
	});
	// Highlight selected row
	$("#example-advanced tbody").on("mousedown", "tr", function() {
		$(".selected").not(this).removeClass("selected");
		$(this).toggleClass("selected");
	});
	// fucntion to create new child inside parent on click on + button
	 $("[id*='add']").click(function(){
		 alert('clicked');
		var eventID = $(this).attr("id");
		var id =eventID.substr(eventID.indexOf("-")+1);
		 var subject = jQuery('#example-advanced').treetable().data("treetable").tree[id];
		 var folder = subject.isBranchNode();
		 var trclass = $($(this).parent().parent().children("td").find("span")[1]).attr("class");
		 if(folder || trclass.indexOf("folder")>=0) {
		var fileName=prompt("Please provide the folder name","");
		var foldersize="--";
		if(fileName!=null && fileName!='')
			{ 
			var DocType;
				$(this).parent().parent().children("td").each(function(cellIndex, cell) {
			        if(cellIndex==1) {
			        	DocType = $.trim($(cell).text());
			          }
			        });
			var tr = "<tr class=\"modified\" data-tt-id=\""+id+ "-1\" data-tt-parent-id=\"" +id+ "\">"
			+ "<td> <span class='folder'>" +fileName+ "</span></td>"
			+ "<td>" + DocType + "</td>"
			+ "<td>" +foldersize+ "</td>"
			+ "<td> <span class='icon-mini-edit' id ='add-"+ id + "-1'/><span class='icon-mini-remove' id ='remove-"+id+"-1'/><span class='icon-mini-upload' id =upload-"+id+"-1'/></td>"
					+ "</tr>";
			var streamsRow = jQuery(tr);
			cquoi = jQuery(streamsRow);
			var newNode = jQuery('#example-advanced').treetable("node", id);
		jQuery("#example-advanced").treetable("loadBranch", newNode, cquoi);
		newNode.render();
		jQuery("#example-advanced").treetable("expandNode", id);
		//loadtreeview();
			}
		 }
	});
	 // To remove the child nodes
	 $("[id*='remove']").click(function(){
			var eventID = $(this).attr("id");
			var id=eventID.substr(eventID.indexOf("-")+1);
    		 var node = jQuery('#example-advanced').treetable("node", id);
    		 var subject = jQuery('#example-advanced').treetable().data("treetable").tree[id];
    		 var folder = subject.isBranchNode();
 		 	var isRemove = confirm("Do you really want to delete this " + (folder ? 'folder and all its contents?' : 'file?'));
 		 	if(isRemove) {
    		 // ------------------------------------------------To remove the child only----------------------
//			 jQuery("#example-advanced").treetable("unloadBranch", newNode);
//			 jQuery("#example-advanced").treetable("expandNode", id);
    		 
    		 //--------------------------------To remove parent and child both-------------------
			jQuery('#example-advanced').treetable("unloadBranch", node);
			var tree = $("#example-advanced").data("treetable");
			   node.row.remove();
		  deletedRowId=$(node.row).attr('data-tt-id');
		  $("#hiddendiv").append(":"+deletedRowId);
//			   deletedRowId= deletedRowId.append($(node.row).attr('data-tt-id'));
			 //  alert("Hi"+$(node.row).attr('data-tt-id'));
			   
			   // tree = node.tree;
//			   delete tree.tree[node.id];
//			   tree.nodes.splice(jQuery.inArray(node, tree.nodes), 1);
 		 	}
		});
	 
	 $("[id*='upload']").click(function(){
			var eventID = $(this).attr("id");
			var id =eventID.substr(eventID.indexOf("-")+1);
			var fileName=prompt("Please provide the file name","New file");;
			if(fileName!='')
				{ 
				var DocType;
				$(this).parent().parent().children("td").each(function(cellIndex, cell) {
			        if(cellIndex==1) {
			        	DocType = $.trim($(cell).text());
			          }
			        });
				var tr = "<tr class=\"modified\" data-tt-id=\""+id+ "-1\" data-tt-parent-id=\"" +id+ "\">"
				+ "<td> <span class='file'>" +fileName+ "</span></td>"
				+ "<td>" + DocType + "</td>"
				+ "<td>0</td>"
				+ "<td> <span id ='file-"+ id + "-1'/><span class='icon-mini-remove' id ='remove-"+id+"-1'/><span class='icon-mini-download' id =download-"+id+"-1'/></td>"
						+ "</tr>";
				var streamsRow = jQuery(tr);
				cquoi = jQuery(streamsRow);
				var newNode = jQuery('#example-advanced').treetable("node", id);
			jQuery("#example-advanced").treetable("loadBranch", newNode, cquoi);
			newNode.render();
			jQuery("#example-advanced").treetable("expandNode", id);
			loadtreeview();
				}
	 });
}