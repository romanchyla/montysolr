/**
 * This mixin (only has a single function) will reset the collection if numFound doesn't exist,
 * or else add  to the collection. Make sure the model for the collection has idAttribute : "resultsIndex"
 * to allow for the collection to add records properly.
 * 
 *  
 */
define(['underscore'], function (_) {

  var WidgetPaginator = {

    //returns correctly zero-indexed start val
    getStartVal        : function (page, rows) {
      return (rows * page) - rows

    },
    //returns final index needed for constructing a page (inclusive)
    getEndVal : function(page,rows, numFound){
      var endVal =  this.getStartVal(page, rows) + rows - 1;

      var finalIndex = numFound - 1;

      return (finalIndex <= endVal) ? finalIndex : endVal;

    },


    //returns current page number
    getPageVal            : function (start, rows) {

      if (start  % rows !== 0){

        throw new Error("start and rows values will not yield a full page")

      }
      else {
        //also could do (start + rows) / rows
        return start/rows + 1

      }
    },

    //handing docs and apiResponse seperately because there may have already
    //been some pre-processing on docs

    addPaginationToDocs: function (docs, apiResponse) {

      var start = apiResponse.get("response.start");

      var docs = _.map(docs, function (d) {
        d.resultsIndex = start
        start++;
        return d
      });

      return docs
    }
  }

  return WidgetPaginator

});