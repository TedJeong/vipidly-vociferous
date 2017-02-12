/**
 * Created by joo on 17. 2. 12.
 */



    $(document).ready(function() {


        $('#task-table1').DataTable( {
            columnDefs: [ {
                orderable: false,
                className: 'select-checkbox',
                targets:   0
            } ],
            select: {
                style:    'os',
                selector: 'td:first-child'
            },
            order: [[ 1, 'asc' ]]
        } );


        $('#delete_yes_button1').click( function () {
            alert('function called!');
            table.row('.selected').remove().draw( false );
        } );



    $('.delete_yes_button').on('click', function(){
        //alert('delete call');
        //var table = $(this).closest('div > .task-table-wrapper')
        var selected_row = $('.btn-task-delete').parent().siblings().children().find('tr.selected');
        var selected_task_pk = selected_row.find('td.task-pk');
        var ajax_delete_differer = $(this).val();
//        console.log(selected_row);
//       alert(selected_row.index());
//        alert(selected_task_pk);
//        alert(ajax_delete_differer);
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


                alert('ajax before send!');
                // this code moved to index.html
                /*
                var table = $('#task-table1').DataTable();

                $('#delete_yes_button1').click( function () {
                    alert('function called!');
                    table.row('.selected').remove().draw( false );
                } );
                */
            }
        });


        $.ajax({
            type: 'POST',
            url: '/projectmanager/',
            data: {'task_pks[]': task_pk_array, 'ajax_differer': ajax_delete_differer},
            success: function(data){
               alert('DB deleted! and ' + data);
            },
        });
    });





    } );


