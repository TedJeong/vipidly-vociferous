$(document).ready(function(){

    $('#datatable').dataTable();

    $('#dattable-keytable').DataTable({
      keys: true
    });

    $('#datatable-responsive').DataTable();


    var $datatable = $('.datatable-checkbox');

    $datatable.dataTable({
      'order': [[ 1, 'asc' ]],
      'columnDefs': [
        { orderable: false, targets: [0] }
      ]
    });
    $datatable.on('draw.dt', function() {
      $('input').iCheck({
        checkboxClass: 'icheckbox_flat-green'
      });
    });

    $('input[type="checkbox"]').on('ifChecked', function(event){

        alert(event.type + ' callback');
//        var table = $(this).closest("table");
//        console.log(typeof(table));
//        console.log(typeof($datatable));
//        console.log(table == $datatable); // false
//
//        console.log($datatable.fnGetNodes());
//        console.log(table.fnGetNodes());
        //$(this).closest("table");

    });

    $('.delete_yes_button').on('click', function(){
        //alert('delete call');
        //var table = $(this).closest('div > .task-table-wrapper')
        var selected_task_pk = $('.btn-task-delete').parent().siblings().children().find('tr.selected').find('td.task-pk');
        var task_pk_array = [];
        for(var i=0;i<selected_task_pk.length;i++){
            console.log(selected_task_pk[i].innerHTML);
            task_pk_array.push(selected_task_pk[i].innerHTML);
        }

        var csrftoken = $.cookie('csrftoken');

        function csrfSafeMethod(method) {
            // these HTTP methods do not require CSRF protection
            return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
        }

        $.ajaxSetup({
            beforeSend: function(xhr, settings) {
                if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
                    xhr.setRequestHeader("X-CSRFToken", csrftoken);
                }
            }
        });


        $.ajax({
            type: 'POST',
            url: '/projectmanager/',
            data: {'task_pks[]': task_pk_array},
            success: function(){
               alert('saved!');

               //not working
               //$('.btn-task-delete').parent().siblings().html();
            },
        });
    });


});


$('#top-padding').css('padding-top',30)




$(document).ready(function(){
  //var svg = d3.select("svg"),
  var svg = d3.select("#task-graph").append("svg").attr("width",700).attr("height",700),
      width = +svg.attr("width"),
      height = +svg.attr("height");

  var color = d3.scaleOrdinal(d3.schemeCategory20);

  var simulation = d3.forceSimulation()
      .force("link", d3.forceLink().id(function(d) { return d.id; }))
      .force("charge", d3.forceManyBody())
      .force("center", d3.forceCenter(width / 2, height / 2));

  d3.json("/static/jsSumma/miserables.json", function(error, graph) {
    if (error) throw error;
    var pretty = JSON.stringify(graph, null, 2);
    //console.log(pretty);
    var test = {"nodes":[1,2,3],"b":[3,4,5]}
    //console.log(graph["nodes"])
    //console.log(graph["links"])


    var link = svg.append("g")
        .attr("class", "links")
      .selectAll("line")
      .data(graph.links)
      .enter().append("line")
        .attr("stroke-width", function(d) { return Math.sqrt(d.value); });

    var node = svg.append("g")
        .attr("class", "nodes")
      .selectAll("circle")
      .data(graph.nodes)
      .enter().append("circle")
        .attr("r", 5)
        .attr("fill", function(d) { return color(d.group); })
        .call(d3.drag()
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended));

    node.append("title")
        .text(function(d) { return d.id; });

    simulation
        .nodes(graph.nodes)
        .on("tick", ticked);

    simulation.force("link")
        .links(graph.links);

    function ticked() {
      link
          .attr("x1", function(d) { return d.source.x; })
          .attr("y1", function(d) { return d.source.y; })
          .attr("x2", function(d) { return d.target.x; })
          .attr("y2", function(d) { return d.target.y; });

      node
          .attr("cx", function(d) { return d.x; })
          .attr("cy", function(d) { return d.y; });
    }
  });

  function dragstarted(d) {
    if (!d3.event.active) simulation.alphaTarget(0.3).restart();
    d.fx = d.x;
    d.fy = d.y;
  }

  function dragged(d) {
    d.fx = d3.event.x;
    d.fy = d3.event.y;
  }

  function dragended(d) {
    if (!d3.event.active) simulation.alphaTarget(0);
    d.fx = null;
    d.fy = null;
  }
});

$(document).ready(function(){
  //var svg = d3.select("svg[id=category-tree]"),
  var svg = d3.select("#category-tree").append("svg").attr("width",700).attr("height",700),
      width = +svg.attr("width"),
      height = +svg.attr("height"),
      g = svg.append("g").attr("transform", "translate(40,0)");

  var tree = d3.cluster()
      .size([height, width - 160]);

  var stratify = d3.stratify()
      .parentId(function(d) { return d.id.substring(0, d.id.lastIndexOf(".")); });

  d3.csv("/static/jsSumma/flare.csv", function(error, data) {
    if (error) throw error;
    //console.log(data)
    var root = stratify(data)
        .sort(function(a, b) { return (a.height - b.height) || a.id.localeCompare(b.id); });

    tree(root);

    var link = g.selectAll(".link")
        .data(root.descendants().slice(1))
      .enter().append("path")
        .attr("class", "link")
        .attr("d", function(d) {
          return "M" + d.y + "," + d.x
              + "C" + (d.parent.y + 100) + "," + d.x
              + " " + (d.parent.y + 100) + "," + d.parent.x
              + " " + d.parent.y + "," + d.parent.x;
        });

    var node = g.selectAll(".node")
        .data(root.descendants())
      .enter().append("g")
        .attr("class", function(d) { return "node" + (d.children ? " node--internal" : " node--leaf"); })
        .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; })

    node.append("circle")
        .attr("r", 2.5);

    node.append("text")
        .attr("dy", 3)
        .attr("x", function(d) { return d.children ? -8 : 8; })
        .style("text-anchor", function(d) { return d.children ? "end" : "start"; })
        .text(function(d) { return d.id.substring(d.id.lastIndexOf(".") + 1); });
  });
});

    $(document).ready(function(){
        $('#controller-task-post-form').on('submit', function(e){
            e.preventDefault();
            $.ajax({
                url: "/projectmanager/", //this is the submit URL
                type: "POST", //or POST
                data: $('#controller-task-post-form').serialize(),
                csrfmiddlewaretoken: $('input[name=csrfmiddlewaretoken]').val(),
                success: function(data){
                     alert('successfully submitted');
                }
            });
        });
    });
