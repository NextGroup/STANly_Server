function createRequst(){
	var request;
	try{
		request = new XMLHttpRequest();
		
	}catch(exception){
		try{
			request = new ActiveXObject('Msxml2.XMLHTTP');
		}catch(innerexception)
		{
			request = new ActiveXObject('Microsoft.XMLHTTP');
		}
		
	}
	return request;
	
	
}

var MainGraph;
var clickNodeList = new Array();

Graph = function(svg, svgGroup, GraphId, graphML,nodeevent, edgeEvent){

	this.nodes;
	this.edges;
	this.id = GraphId;
	var self = this;
	self.svgGroup = svgGroup;
	self.svg = svg;
	this.request = createRequst();
	this.request.open('GET',graphML,false);
	var subGraphList = new Array();
	this.request.send(null);
	

	if(nodeevent!==null)
		this.nodeevent = nodeevent;
    if(edgeEvent!==null)
    this.EdgeEv = edgeEvent;

		self.GMLData = eval('('+this.request.responseText+')').dot;





		self.tryDraw();
		if(self.id=="Main")
		{
			MainGraph = this;
			  self.svg.call(d3.behavior.zoom().on("zoom", function redraw() {
				  self.svgGroup.attr("transform",
				  "translate(" + d3.event.translate + ")"
				  + " scale(" + d3.event.scale + ")");
          }));
         }
	


}

Graph.prototype.tryDraw = function() {
 	
 	 var result;
 

      result = dagre.dot.toObjects(this.GMLData);
      result.edges.forEach(function(e) { if (!e.label) { e.label = ""; } });


    if (result) {
      result.nodes.forEach(function(node) {
        node.inEdges = [];
        node.outEdges = [];
      });

      result.edges.forEach(function(edge) {
        edge.source.outEdges.push(edge);
        edge.target.inEdges.push(edge);
      });


      this.draw(result.nodes, result.edges);
    
  }
}

Graph.prototype.draw = function(nodeData, edgeData) {
  // D3 doesn't appear to like rebinding with the same id but a new object,
  // so for now we remove everything.
  
  var self=this;

  
  this.svgGroup.selectAll("*").remove();
      this.subGraphList=[];// 모두 초기화
      
  this.nodes = this.svgGroup
    .selectAll("g ."+this.id+"-node")
    .data(nodeData, function(d) {  return d.id; });
   
 
  var nodeEnter = this.nodes
    .enter()
    .append("g")
      .attr("class",this.id+"-node")
      .each(function(d) { d.nodePadding = 10; });
      
  nodeEnter.append("rect").attr("id",function(d){return d.type;});
  this.addLabels(nodeEnter);
  	

  this.nodes
   		.filter(function(d) { 
   		     var chk=true;
      
      		for(var i=0;i<self.subGraphList.length;i++)
      		{	
       			if(self.subGraphList[i].id.split("-")[1] == d.id)
      			{
	      			chk = false;
	      			break;		
      			}
      		}
   		return chk; })
   		.on("click", function(d) {
	 	
		 	if(!d.subgraph)
		 		return;
		 		
		 		if(!clickNodeList[d.id])
		 		{
			
					clickNodeList[d.id]= true;
					self.nodes.filter(function(data){return data.id == d.id;})
						.on("click",function(d){});
					MainGraph.tryDraw();
					if(self.nodeevent)
						self.nodeevent(d.id,$(d.label).text());
			//이벤트 중첩 발생을 막기 위한 코드 임시적으로 클릭 이벤트를 없앤다.
				}	
				
			  });

  nodeEnter.attr("id", function(d){ 
      	var chk=false;
      
      		for(var i=0;i<self.subGraphList.length;i++)
      		{	
       			if(self.subGraphList[i].id.split("-")[1] == d.id)
      			{
	      			chk = true;
	      			break;		
      			}
      		}
      return chk ? "setnode":"node";});
      
  this.nodes.exit().remove();

  this.edges = this.svgGroup
    .selectAll("g ."+this.id+"-edge")
    .data(edgeData, function(d) {  return d.id; });

  var edgeEnter = this.edges
    .enter()
    .append("g")
      .attr("class", this.id+"-edge")
      .attr("id", "edge")
      .each(function(d) { d.nodePadding = 0; })
 
  edgeEnter
    .append("path")
      .attr("marker-end", "url(#arrowhead)");


  this.addLabels(edgeEnter);
  

  this.edges.on("click", function(d) {
                     if(self.EdgeEv !==null)
                        self.EdgeEv(d);
  })
	  
  this.edges.exit().remove();

  this.recalcLabels();


  	// Run the actual layout
  	var layout = dagre.layout()
  	    .nodeSep(50)
    .edgeSep(10)
    .rankSep(30)
    .nodes(nodeData)
    .edges(edgeData)
    .run();

    // Ensure that we have at least two points between source and target
  	this.edges.each(function(d) { self.ensureTwoControlPoints(d); });
   

   // Re-render
  	this.update();
}

Graph.prototype.addLabels = function(selection) {
  var self= this;
  var labelGroup = selection
    .append("g")
      .attr("class", this.id+"-label");

   labelGroup.append("rect").attr("id",function(d){return d.type;})
   		.attr("style", "fill:#EAEAEA");
      	//foLabel이 안나오는 경우 값이 정상 출력됨
  var foLabel = labelGroup
    .filter(function(d) { return (d.label[0] === "<")&&(!clickNodeList[d.id]); })
    .append("foreignObject")
    .attr("class", "htmllabel").attr('requiredFeatures','http://www.w3.org/TR/SVG11/feature#Extensibility').append("xhtml:div")
    .style("float", "left");



    labelGroup
    .filter(function(d) { 
        return (d.label[0] !== "<")&&(!clickNodeList[d.id]); }).append("text");

    
   
   //SubGraphLabel 추가 
   	var SubArray = new Array();
 
   labelGroup
    .filter(function(d) { 
        return (d.label[0] !== "<")&&(d.subgraph)&&(clickNodeList[d.id]); })
    .append("text")
    .attr("id",function(d) {return "subgraph-"+d.id;}).on("click",function(d){
		 			clickNodeList[d.id]= false;
					MainGraph.tryDraw();
	 			});
	labelGroup
    .filter(function(d) { 
        return (d.label[0] === "<")&&(d.subgraph)&&(clickNodeList[d.id]); })
     .append("foreignObject")
     .attr("id",function(d) {return "subgraph-"+d.id;})
     .attr("class", "htmllabel").attr('requiredFeatures','http://www.w3.org/TR/SVG11/feature#Extensibility').attr("y",-10)
     .append("xhtml:div").style("float", "left")
     .on("click",function(d){
		 			clickNodeList[d.id]= false;
					MainGraph.tryDraw();
						if(self.nodeevent)
						self.nodeevent(d.parent);
	 			});		

    labelGroup
    .filter(function(d) { 
        return (d.subgraph)&&(clickNodeList[d.id]); }) 
    .append("svg")
    .attr("class",function(d) { 
    	SubArray.push("subgraph-"+d.id);
    return "subgraph-"+d.id; })
    .attr("y",40);
  
     //있는 놈들 서브 그래프 수집

    for(var i=0;i<SubArray.length;i++)
    {
	 	var subSvg = d3.select("."+SubArray[i]);
	 	
	 	var subgraphData;
		var subGroup = subSvg.append("g").attr("id",SubArray[i]).each(function(d){
				subgraphData = d.subgraph;
		});
	 self.subGraphList.push(new Graph(subSvg, subGroup, SubArray[i],subgraphData, self.nodeevent,self.EdgeEv));
	 	
	 	subSvg.attr("width",subGroup.node().getBBox().width+10);	
	 	//svg 테그가 자동적으로 크기를 리사이징 해주는줄 알았는데 그게 아님 이거 없으면 에러 남
	 	subSvg.attr("height",subGroup.node().getBBox().height+10);

	 	
	}
  

 
}    

Graph.prototype.recalcLabels = function() {
	var self = this;
  var labelGroup = this.svgGroup.selectAll("g ."+this.id+"-label"); //class가 라벨인  녀석들 모두 선택 

  var foLabel = labelGroup
    .selectAll(".htmllabel")
    .attr("width", "10000");	//Web 객체의 사이즈를 알지 못하므로 width를 크게 설정

  foLabel
    .select("div").filter(function(d) { return (!clickNodeList[d.id]); })
      .html(function(d) { 
      return d.label; })
      .each(function(d) {
        d.width = this.clientWidth;
        d.height = this.clientHeight;
        d.nodePadding = 0;
      });

  foLabel
    .attr("width", function(d) {  return d.width; })
    .attr("height", function(d) { return d.height; });

  var textLabel = labelGroup
    .filter(function(d) { return (d.label[0] !== "<")&&(!clickNodeList[d.id]); });

  textLabel
    .select("text")
      .attr("text-anchor", "left")
        .append("tspan")
        .attr("dy", "1em")
        .text(function(d) { return d.label || " "; });

  // 서브 그래프 라벨
    for(var i=0;i<this.subGraphList.length;i++)
  	{	  	  
	 	this.nodes.select("#"+this.subGraphList[i].id).filter(function(d) { return d.label[0] !== "<"; })
	 	.attr("text-anchor", "left")
        .append("tspan")
        .attr("dy", "1em")
        .text(function(d) { return d.label || " "; }); 	
     
      var subfoLabel = this.nodes.select("#"+this.subGraphList[i].id).filter(function(d) { return d.label[0] === "<"; })
          .attr("width", "10000");
           
            subfoLabel
            .select("div")
            .html(function(d) { 
	            return d.label; })
	        .each(function(d) {
		        d.width = this.clientWidth;
		        d.height = this.clientHeight;
		        d.nodePadding = 10;
		        console.log("Hello");
		        
		      });
		      
            subfoLabel
            .attr("width", function(d) {  return d.width; })
            .attr("height", function(d) { return d.height; })
            .style("margin-bottom","10px");
   
         
     }	 

  labelGroup
    .each(function(d) {
      var bbox = this.getBBox();
      d.bbox = bbox;
   
      d.width = bbox.width + 2 * d.nodePadding;
      d.height = bbox.height + 2 * d.nodePadding;
    });
    
    
    
}

Graph.prototype.ensureTwoControlPoints = function(d) {
  var points = d.dagre.points;

  if (!points.length) {
    var s = d.source.dagre;
    var t = d.target.dagre;
    points.push({ x: (s.x + t.x) / 2, y: (s.y + t.y) / 2 });
  }

  if (points.length === 1) {
    points.push({ x: points[0].x, y: points[0].y });
  }

}

Graph.prototype.update = function() {
  

  	var self = this;
 
 
 
  	this.nodes 
    	.attr("transform", function(d) {
     	 return "translate(" + d.dagre.x + "," + d.dagre.y +")"; })
      .selectAll("g ."+this.id+"-node rect")
      .attr("x", function(d) { return -(d.bbox.width / 2 + d.nodePadding); })
      .attr("y", function(d) { return -(d.bbox.height / 2 + d.nodePadding); })
      .attr("width", function(d) {  return d.width; })
      .attr("height", function(d) { return d.height; });

      this.edges
    	.selectAll("path")
    	.attr("d", function(d) {
      	var points = d.dagre.points.slice(0);
      	var source = dagre.util.intersectRect(d.source.dagre, points[0]);
      	var target = dagre.util.intersectRect(d.target.dagre, points[points.length - 1]);
      	points.unshift(source);
      	points.push(target);
      	return d3.svg.line()
       	 .x(function(e) { return e.x; })
       	 .y(function(e) { return e.y; })
       	 .interpolate("linear")
       	 (points);
        }).attr("stroke",function(d) {

      return (d.source.dagre.y>d.target.dagre.y) ? "#f00":"#000" });

    this.svgGroup
 	   .selectAll("g."+this.id+"-label rect")
  	  .attr("x", function(d) { return -d.nodePadding; })
  	  .attr("y", function(d) { return -d.nodePadding; })
   	 .attr("width", function(d) { return d.width; })
   	 .attr("height", function(d) { return d.height; });


   	 

    this.nodes
   	 .selectAll("g ."+this.id+"-label")
   	 .attr("transform", 
   	 function(d) { return "translate(" + (-d.bbox.width / 2) + "," + (-d.bbox.height / 2) + ")"; });

    	 
    this.edges
    .selectAll("g ."+this.id+"-label")
    .attr("transform", function(d) {
      var points = d.dagre.points;
      var x = (points[0].x + points[1].x) / 2;
      var y = (points[0].y + points[1].y) / 2;
      return "translate(" + (-d.bbox.width / 2 + x) + "," + (-d.bbox.height / 2 + y) + ")";
      });
      //함수의 d는 우리가 데이터로 넘겨준 엣지와 노드 데이터들
      //서브 그래프 갱신 
     for(var i=0;i<this.subGraphList.length;i++)
  		this.subGraphList[i].update();
}