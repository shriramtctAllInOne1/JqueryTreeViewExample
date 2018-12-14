(function( $ ) {
  $.fn.tableToJSON = function(opts) {

    // Set options
    var defaults = {
      ignoreColumns: ['Action'],
      onlyColumns: null,
      ignoreHiddenRows: false,
      headings: null
    };
    opts = $.extend(defaults, opts);

    var notNull = function(value) {
      if(value !== undefined && value !== null) {
        return true;
      }
      return false;
    };

    var ignoredColumn = function(index) {
      if( notNull(opts.onlyColumns) ) {
        return $.inArray(index, opts.onlyColumns) === -1;
      }
      return $.inArray(index, opts.ignoreColumns) !== -1;
    };

    var arraysToHash = function(keys, values) {
      var result = {};
      $.each(values, function(index, value) {
        if( index < keys.length ) {
          result[ keys[index] ] = value;
        }
      });
      return result;
    };

    var rowValues = function(row) {
      var result = [];
      $(row).children("td,th").each(function(cellIndex, cell) {
        if( !ignoredColumn(cellIndex) ) {
          var override = $(cell).data("override");
          var value = $.trim($(cell).text());
          result[ result.length ] = notNull(override) ? override : value;
        }
      });
      return result;
    };

    var getHeadings = function(table) {
    	var result = [];
      var firstRow = table.find("tr:first").first();
      result = notNull(opts.headings) ? opts.headings : rowValues(firstRow);
      result[result.length] = 'Document Id';
      result[result.length] = 'ParentID';
      result[result.length] = 'Folder';
      result[result.length] = 'action';
      return result;
    };

    var construct = function(table, headings) {
      var result = [];
      table.children("tbody,*").children("tr").each(function(rowIndex, row) {
    	  var goAhead = false;
    	  var action = row.className;
    	  if (action.indexOf("modified")>=0 ||
    			  action.indexOf("added")>=0 ||
    			  action.indexOf("deleted")>=0) {
    		  goAhead = true;
    	  }
    	  console.log(goAhead);
        if( goAhead && (rowIndex !== 0 || notNull(opts.headings)) ) {
        	var data = [];
        	data = rowValues(row);
        	data[data.length] = $(row).attr('data-tt-id');
        	data[data.length] = $(row).attr('data-tt-parent-id');
        	var trclass = $($(row).children("td").find("span")[1]).attr("class");
        	data[data.length] = (trclass.indexOf("folder") >= 0) ? true : false;
        	data[data.length] = action;
            result[result.length] = arraysToHash(headings, data);
        }
      });
      return result;
    };

    // Run
    var headings = getHeadings(this);
    return construct(this, headings);
  };
})( jQuery );